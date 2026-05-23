package main;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import main.antlr.XPathLexer;
import main.antlr.XPathParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: java -cp lib/* main.Main <input xml> <input query> <output xml>");
        }
        // parse XML to DOM Tree
        Document input = readXml(args[0]);
        // read query to String
        String query = readQuery(args[1]);
        // parse query to AST
        XPathParser.ApContext ap = parseQuery(query); // the root of the AST
        // 
        List<Node> answer = evalAp(ap, input);
        // write result into output XML
        writeResult(answer, args[2]);
    }
    // XML --> DOM Parser
    private static Document readXml(String path) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setCoalescing(true);
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(path));
        document.getDocumentElement().normalize();
        return document;
    }
    // query --> String reader
    private static String readQuery(String path) throws Exception {
        String query = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8).trim();
        if (query.startsWith("\uFEFF")) {
            query = query.substring(1);
        }
        return query;
    }
    // query parser
    private static XPathParser.ApContext parseQuery(String query) {
        XPathLexer lexer = new XPathLexer(CharStreams.fromString(query));
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        XPathParser parser = new XPathParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        return parser.ap();
    }
    
    private static List<Node> evalAp(XPathParser.ApContext ap, Document document) {
        String separator = ap.getChild(4).getText();
        XPathParser.RpContext rp = ap.rp();
        // rule 2
        if ("//".equals(separator)) {
            return evalDescendantSlash(Collections.singletonList((Node) document), rp);
        }
        // rule 1
        return evalRp(rp, document);
    }

    private static List<Node> evalRp(XPathParser.RpContext rp, Node node) {
        // rule 3
        if (rp.tagName() != null) {
            return evalTagName(rp.tagName().getText(), node);
        }

        int childCount = rp.getChildCount();
        String first = rp.getChild(0).getText();

        if (childCount == 1) {
            // rule 4
            if ("*".equals(first)) {
                return elementChildren(node);
            }
            // rule 5
            if (".".equals(first)) {
                return Collections.singletonList(node);
            }
            // rule 6
            if ("..".equals(first)) {
                return parent(node);
            }
        }
        // rule 8
        if (childCount == 2 && "@".equals(first)) {
            return attribute(node, rp.attName().getText());
        }

        if (childCount == 3) {
            // rule 7
            if ("text".equals(first)) {
                return textChildren(node);
            }
            // rule 9
            if ("(".equals(first)) {
                return evalRp(rp.rp(0), node);
            }
            // rule 10, 11, 13
            return evalBinaryRp(rp, node);
        }
        // rule 12
        if (childCount == 4 && "[".equals(rp.getChild(1).getText())) {
            List<Node> base = evalRp(rp.rp(0), node);
            List<Node> result = new ArrayList<>();
            for (Node current : base) {
                if (evalFilter(rp.f(), current)) {
                    result.add(current);
                }
            }
            return result;
        }

        throw new IllegalArgumentException("Unsupported relative XPath: " + rp.getText());
    }

    private static List<Node> evalBinaryRp(XPathParser.RpContext rp, Node node) {
        String op = rp.getChild(1).getText();
        // rule 10
        if ("/".equals(op)) {
            List<Node> result = new ArrayList<>();
            for (Node left : evalRp(rp.rp(0), node)) {
                result.addAll(evalRp(rp.rp(1), left));
            }
            return unique(result);
        }
        // rule 11
        if ("//".equals(op)) {
            return evalDescendantSlash(evalRp(rp.rp(0), node), rp.rp(1));
        }
        // rule 13
        if (",".equals(op)) {
            List<Node> result = new ArrayList<>(evalRp(rp.rp(0), node));
            result.addAll(evalRp(rp.rp(1), node));
            return unique(result);
        }
        throw new IllegalArgumentException("Unsupported relative XPath operator: " + op);
    }

    private static List<Node> evalDescendantSlash(List<Node> starts, XPathParser.RpContext right) {
        List<Node> result = new ArrayList<>();
        for (Node current : starts) {
            result.addAll(evalRp(right, current));
            for (Node descendant : descendants(current)) {
                result.addAll(evalRp(right, descendant));
            }
        }
        return unique(result);
    }

    private static boolean evalFilter(XPathParser.FContext filter, Node node) {
        int childCount = filter.getChildCount();
        String first = filter.getChild(0).getText();

        if (childCount == 1) {
            return !evalRp(filter.rp(0), node).isEmpty();
        }

        if (childCount == 2 && "not".equals(first)) {
            return !evalFilter(filter.f(0), node);
        }

        if (childCount == 3) {
            String op = filter.getChild(1).getText();
            if ("(".equals(first)) {
                return evalFilter(filter.f(0), node);
            }
            if ("and".equals(op)) {
                return evalFilter(filter.f(0), node) && evalFilter(filter.f(1), node);
            }
            if ("or".equals(op)) {
                return evalFilter(filter.f(0), node) || evalFilter(filter.f(1), node);
            }
            if ("=".equals(op) || "eq".equals(op)) {
                if (filter.StringConstant() != null) {
                    return compareWithString(filter.rp(0), stripQuotes(filter.StringConstant().getText()), node);
                }
                return compareNodes(filter.rp(0), filter.rp(1), node, false);
            }
            if ("==".equals(op) || "is".equals(op)) {
                return compareNodes(filter.rp(0), filter.rp(1), node, true);
            }
        }

        throw new IllegalArgumentException("Unsupported filter: " + filter.getText());
    }

    private static boolean compareNodes(XPathParser.RpContext left, XPathParser.RpContext right, Node node, boolean identity) {
        for (Node a : evalRp(left, node)) {
            for (Node b : evalRp(right, node)) {
                if (identity ? a == b : nodeValue(a).equals(nodeValue(b))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean compareWithString(XPathParser.RpContext rp, String value, Node node) {
        for (Node candidate : evalRp(rp, node)) {
            if (nodeValue(candidate).equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static List<Node> evalTagName(String tagName, Node node) {
        List<Node> result = new ArrayList<>();
        for (Node child : elementChildren(node)) {
            if (tagName.equals(child.getNodeName())) {
                result.add(child);
            }
        }
        return result;
    }

    private static List<Node> elementChildren(Node node) {
        List<Node> result = new ArrayList<>();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.add(child);
            }
        }
        return result;
    }

    private static List<Node> textChildren(Node node) {
        List<Node> result = new ArrayList<>();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                result.add(child);
            }
        }
        return result;
    }

    private static List<Node> parent(Node node) {
        Node parent = node.getParentNode();
        if (parent == null || parent.getNodeType() == Node.DOCUMENT_NODE) {
            return Collections.emptyList();
        }
        return Collections.singletonList(parent);
    }

    private static List<Node> attribute(Node node, String name) {
        if (node.getNodeType() != Node.ELEMENT_NODE) {
            return Collections.emptyList();
        }
        Attr attr = ((Element) node).getAttributeNode(name);
        if (attr == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(attr);
    }

    private static List<Node> descendants(Node node) {
        List<Node> result = new ArrayList<>();
        collectDescendants(node, result);
        return result;
    }

    private static void collectDescendants(Node node, List<Node> result) {
        for (Node child : elementChildren(node)) {
            result.add(child);
            collectDescendants(child, result);
        }
    }

    private static List<Node> unique(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        Map<Node, Boolean> seen = new IdentityHashMap<>();
        for (Node node : nodes) {
            if (!seen.containsKey(node)) {
                seen.put(node, Boolean.TRUE);
                result.add(node);
            }
        }
        return result;
    }

    private static String nodeValue(Node node) {
        if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
            return ((Attr) node).getValue();
        }
        return node.getTextContent();
    }

    private static String stripQuotes(String value) {
        if (value.length() >= 2) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
    // serializing the DOM output to XML
    private static void writeResult(List<Node> nodes, String outputPath) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document result = builder.newDocument();
        Element root = result.createElement("RESULT");
        result.appendChild(root);

        for (Node node : nodes) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                root.appendChild(result.importNode(node, true));
            } else if (node.getNodeType() == Node.TEXT_NODE) {
                root.appendChild(result.createTextNode(node.getNodeValue()));
            } else if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                Element attr = result.createElement(((Attr) node).getName());
                attr.appendChild(result.createTextNode(((Attr) node).getValue()));
                root.appendChild(attr);
            }
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(result), new StreamResult(new File(outputPath)));
    }

    private static final class ThrowingErrorListener extends BaseErrorListener {
        static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

        public void syntaxError(
                Recognizer<?, ?> recognizer,
                Object offendingSymbol,
                int line,
                int charPositionInLine,
                String message,
                RecognitionException e) {
            throw new IllegalArgumentException("XPath syntax error at " + line + ":" + charPositionInLine + " " + message, e);
        }
    }
}
