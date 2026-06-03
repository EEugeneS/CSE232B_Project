package main;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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

import main.antlr.XQueryLexer;
import main.antlr.XQueryParser;

public class Main {

    /**
     * The Document used to construct new element/text nodes via element constructors.
     * Newly built nodes are owned by this Document so we can serialize them out.
     */
    private static Document outputDoc;

    /**
     * Cache of XML files loaded via doc()/document() calls (filename -> Document).
     */
    private static final Map<String, Document> docCache = new HashMap<>();

    /**
     * Fallback input Document used when a doc()/document() argument cannot be
     * resolved on disk. Provides backward compatibility with the Milestone 1
     * harness which passes the XML file separately from the query.
     */
    private static Document fallbackInput;

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            throw new IllegalArgumentException(
                "Usage: java -cp lib/* main.Main <input xml> <input query> <rewrite output> <result output>");
        }
        // Read the input XML once. It is used as the fallback for doc()/document()
        // calls whose filename does not resolve to a file on disk.
        fallbackInput = readXml(args[0]);

        // Output Document for nodes built by element constructors.
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        outputDoc = builder.newDocument();

        String query = readQuery(args[1]);
        XQueryParser.XqMainContext original = parseQuery(query);
        String rewrittenQuery = rewriteToJoinQuery(original);
        Files.write(Paths.get(args[2]), rewrittenQuery.getBytes(StandardCharsets.UTF_8));

        XQueryParser.XqMainContext root = parseQuery(rewrittenQuery);

        // Evaluate.
        List<Node> answer = evalXq(root.xq(), new Context());

        // Serialize.
        writeResult(answer, args[3]);
    }

    // ====================================================================
    // I/O
    // ====================================================================

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

    private static String readQuery(String path) throws Exception {
        String query = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8).trim();
        if (query.startsWith("\uFEFF")) {
            query = query.substring(1);
        }
        return query;
    }

    private static XQueryParser.XqMainContext parseQuery(String query) {
        XQueryLexer lexer = new XQueryLexer(CharStreams.fromString(query));
        lexer.removeErrorListeners();
        lexer.addErrorListener(ThrowingErrorListener.INSTANCE);

        XQueryParser parser = new XQueryParser(new CommonTokenStream(lexer));
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);
        return parser.xqMain();
    }

    /**
     * Resolve a filename appearing in doc()/document() to a Document. Tries
     * the filename as given, then relative to the cwd, then falls back to the
     * input XML provided as args[0] (Milestone 1 compatibility).
     */
    private static Document loadDoc(String filename) {
        Document cached = docCache.get(filename);
        if (cached != null) {
            return cached;
        }
        File f = new File(filename);
        if (f.exists()) {
            try {
                Document d = readXml(filename);
                docCache.put(filename, d);
                return d;
            } catch (Exception ignored) {
                // fall through to fallback
            }
        }
        // Fall back to the input XML provided on the command line.
        docCache.put(filename, fallbackInput);
        return fallbackInput;
    }

    // ====================================================================
    // Context: variable bindings.
    // Maps a variable name (including the leading '$') to a list of nodes.
    // Immutable: extend() returns a fresh context with the new binding.
    // ====================================================================
    static final class Context {
        private final Map<String, List<Node>> bindings;

        Context() {
            this.bindings = new HashMap<>();
        }

        private Context(Map<String, List<Node>> b) {
            this.bindings = b;
        }

        Context extend(String var, List<Node> value) {
            Map<String, List<Node>> nb = new HashMap<>(this.bindings);
            nb.put(var, value);
            return new Context(nb);
        }

        List<Node> lookup(String var) {
            List<Node> v = bindings.get(var);
            return v == null ? Collections.<Node>emptyList() : v;
        }
    }

    // ====================================================================
    // XQuery evaluation (Milestone 2)
    // ====================================================================

    /**
     * Rule 26: [[xq, xq]]_X(C) = [[xq1]]_X(C), [[xq2]]_X(C)
     * Top-level comma operator: list concatenation, NO unique().
     */
    private static List<Node> evalXq(XQueryParser.XqContext xq, Context ctx) {
        List<Node> result = new ArrayList<>();
        for (XQueryParser.XqValueContext v : xq.xqValue()) {
            result.addAll(evalXqValue(v, ctx));
        }
        return result;
    }

    /**
     * Evaluate an xqValue. Dispatches on the structure of the parse tree.
     */
    private static List<Node> evalXqValue(XQueryParser.XqValueContext xv, Context ctx) {
        // Rule 22: Var lookup
        if (xv.Var() != null && xv.xqValue().isEmpty() && xv.forClause() == null && xv.letClause() == null) {
            return ctx.lookup(xv.Var().getText());
        }
        // Rule 23: StringConstant -> text node
        if (xv.StringConstant() != null && xv.xqValue().isEmpty()) {
            String s = stripQuotes(xv.StringConstant().getText());
            return Collections.<Node>singletonList(outputDoc.createTextNode(s));
        }
        // Rule 24: ap (absolute path) - reuse Milestone 1 logic.
        if (xv.ap() != null) {
            return evalAp(xv.ap());
        }

        int n = xv.getChildCount();
        String first = xv.getChild(0).getText();

        if ("join".equals(first)) {
            return evalJoin(xv, ctx);
        }

        // Rule 25: '(' xq ')'
        if (n == 3 && "(".equals(first)) {
            return evalXq(xv.xq(), ctx);
        }

        // Rule 29: element constructor '<' tagName '>' '{' xq '}' '</' tagName '>'
        if (xv.tagName() != null && xv.tagName().size() == 2 && "<".equals(first)) {
            String tag = xv.tagName(0).getText();
            List<Node> body = evalXq(xv.xq(), ctx);
            return Collections.<Node>singletonList(makeElem(tag, body));
        }

        // FLWR expression: forClause letClause? whereClause? returnClause
        if (xv.forClause() != null) {
            return evalFLWR(xv, ctx);
        }

        // letClause xqValue
        if (xv.letClause() != null && !xv.xqValue().isEmpty() && xv.rp() == null) {
            return evalLetExpr(xv.letClause(), xv.xqValue(0), ctx);
        }

        // Rules 27, 28: xqValue '/' rp  and  xqValue '//' rp
        if (!xv.xqValue().isEmpty() && xv.rp() != null) {
            String sep = xv.getChild(1).getText();
            List<Node> left = evalXqValue(xv.xqValue(0), ctx);
            if ("/".equals(sep)) {
                // Rule 27
                List<Node> out = new ArrayList<>();
                for (Node m : left) {
                    out.addAll(evalRp(xv.rp(), m));
                }
                return unique(out);
            }
            if ("//".equals(sep)) {
                // Rule 28: [[XQ1//rp]] = unique(<m | n <- XQ1, m <- .//rp at n>)
                return evalDescendantSlash(left, xv.rp());
            }
        }

        throw new IllegalArgumentException("Unsupported xqValue: " + xv.getText());
    }

    private static List<Node> evalJoin(XQueryParser.XqValueContext xv, Context ctx) {
        List<Node> left = evalXqValue(xv.xqValue(0), ctx);
        List<Node> right = evalXqValue(xv.xqValue(1), ctx);
        List<String> leftAttrs = attrNames(xv.attrList(0));
        List<String> rightAttrs = attrNames(xv.attrList(1));

        Map<List<String>, List<Element>> index = new HashMap<>();
        for (Node node : left) {
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element tuple = (Element) node;
            List<String> key = joinKey(tuple, leftAttrs);
            List<Element> bucket = index.get(key);
            if (bucket == null) {
                bucket = new ArrayList<>();
                index.put(key, bucket);
            }
            bucket.add(tuple);
        }

        List<Node> result = new ArrayList<>();
        for (Node node : right) {
            if (node.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Element rightTuple = (Element) node;
            List<Element> matches = index.get(joinKey(rightTuple, rightAttrs));
            if (matches == null) {
                continue;
            }
            for (Element leftTuple : matches) {
                result.add(mergeTuples(leftTuple, rightTuple));
            }
        }
        return result;
    }

    private static List<String> attrNames(XQueryParser.AttrListContext attrList) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < attrList.Name().size(); i++) {
            result.add(attrList.Name(i).getText());
        }
        return result;
    }

    private static List<String> joinKey(Element tuple, List<String> attrs) {
        List<String> key = new ArrayList<>();
        for (String attr : attrs) {
            key.add(tupleAttributeValue(tuple, attr));
        }
        return key;
    }

    private static String tupleAttributeValue(Element tuple, String attrName) {
        Element attr = tupleAttribute(tuple, attrName);
        if (attr == null) {
            return "";
        }
        List<String> values = new ArrayList<>();
        NodeList children = attr.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE || child.getNodeType() == Node.TEXT_NODE) {
                values.add(child.getNodeName() + ":" + nodeStringValue(child));
            }
        }
        return String.join("\u001F", values);
    }

    private static Element tupleAttribute(Element tuple, String attrName) {
        NodeList children = tuple.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE && attrName.equals(child.getNodeName())) {
                return (Element) child;
            }
        }
        return null;
    }

    private static Element mergeTuples(Element left, Element right) {
        Element tuple = outputDoc.createElement("tuple");
        appendTupleAttributes(tuple, left);
        appendTupleAttributes(tuple, right);
        return tuple;
    }

    private static void appendTupleAttributes(Element target, Element source) {
        NodeList children = source.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                target.appendChild(outputDoc.importNode(child, true));
            }
        }
    }

    private static String rewriteToJoinQuery(XQueryParser.XqMainContext root) {
        XQueryParser.XqContext xq = root.xq();
        if (xq.xqValue().size() != 1) {
            return xq.getText();
        }

        XQueryParser.XqValueContext flwr = xq.xqValue(0);
        if (flwr.forClause() == null || flwr.whereClause() == null || flwr.returnClause() == null) {
            return xq.getText();
        }

        List<ForBinding> bindings = collectForBindings(flwr.forClause());
        List<RewriteGroup> groups = buildRewriteGroups(bindings);
        List<RewriteCond> conditions = splitConditions(flwr.whereClause().cond());
        assignConditions(groups, conditions);

        if (groups.size() < 2 || !hasJoinCondition(groups)) {
            return xq.getText();
        }

        String joinExpr = buildJoinExpression(groups);
        String rewrittenReturn = rewriteReturn(flwr.returnClause().xq().getText(), bindings);
        return "for $tuple in " + joinExpr + "\nreturn " + rewrittenReturn;
    }

    private static List<ForBinding> collectForBindings(XQueryParser.ForClauseContext forClause) {
        List<String> vars = directChildVars(forClause);
        List<XQueryParser.XqValueContext> exprs = forClause.xqValue();
        List<ForBinding> bindings = new ArrayList<>();
        for (int i = 0; i < vars.size(); i++) {
            bindings.add(new ForBinding(vars.get(i), exprs.get(i).getText()));
        }
        return bindings;
    }

    private static List<RewriteGroup> buildRewriteGroups(List<ForBinding> bindings) {
        List<RewriteGroup> groups = new ArrayList<>();
        Map<String, RewriteGroup> varToGroup = new HashMap<>();
        for (ForBinding binding : bindings) {
            String dependency = leadingVar(binding.expr);
            RewriteGroup group = dependency == null ? null : varToGroup.get(dependency);
            if (group == null) {
                group = new RewriteGroup();
                groups.add(group);
            }
            group.bindings.add(binding);
            varToGroup.put(binding.var, group);
        }
        return groups;
    }

    private static String leadingVar(String expr) {
        if (!expr.startsWith("$")) {
            return null;
        }
        int end = 1;
        while (end < expr.length()) {
            char c = expr.charAt(end);
            if (Character.isLetterOrDigit(c) || c == '_' || c == '.' || c == '-') {
                end++;
            } else {
                break;
            }
        }
        return expr.substring(0, end);
    }

    private static List<RewriteCond> splitConditions(XQueryParser.CondContext cond) {
        List<RewriteCond> result = new ArrayList<>();
        collectConditions(cond, result);
        return result;
    }

    private static void collectConditions(XQueryParser.CondContext cond, List<RewriteCond> result) {
        if (cond.getChildCount() == 3 && "and".equals(cond.getChild(1).getText())) {
            collectConditions(cond.cond(0), result);
            collectConditions(cond.cond(1), result);
            return;
        }
        if (cond.getChildCount() == 3 && "(".equals(cond.getChild(0).getText())) {
            collectConditions(cond.cond(0), result);
            return;
        }
        if (cond.getChildCount() == 3 && "eq".equals(cond.getChild(1).getText())) {
            result.add(new RewriteCond(cond.xqValue(0).getText(), cond.xqValue(1).getText()));
        }
    }

    private static void assignConditions(List<RewriteGroup> groups, List<RewriteCond> conditions) {
        Map<String, RewriteGroup> varToGroup = new HashMap<>();
        for (RewriteGroup group : groups) {
            for (ForBinding binding : group.bindings) {
                varToGroup.put(binding.var, group);
            }
        }

        for (RewriteCond condition : conditions) {
            String leftVar = condition.leftVar();
            String rightVar = condition.rightVar();
            RewriteGroup leftGroup = varToGroup.get(leftVar);
            RewriteGroup rightGroup = varToGroup.get(rightVar);

            if (leftGroup != null && rightGroup != null && leftGroup != rightGroup) {
                leftGroup.joinConditions.add(condition);
                rightGroup.joinConditions.add(condition);
            } else if (leftGroup != null) {
                leftGroup.localConditions.add(condition);
            } else if (rightGroup != null) {
                rightGroup.localConditions.add(condition);
            }
        }
    }

    private static boolean hasJoinCondition(List<RewriteGroup> groups) {
        for (RewriteGroup group : groups) {
            if (!group.joinConditions.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private static String buildJoinExpression(List<RewriteGroup> groups) {
        String current = buildTupleQuery(groups.get(0));
        Set<RewriteGroup> joined = new LinkedHashSet<>();
        joined.add(groups.get(0));

        for (int i = 1; i < groups.size(); i++) {
            RewriteGroup next = groups.get(i);
            List<RewriteCond> joinConds = joinConditionsBetween(joined, next);
            if (joinConds.isEmpty()) {
                current = buildTupleQuery(next);
                joined.clear();
                joined.add(next);
                continue;
            }
            List<String> leftAttrs = new ArrayList<>();
            List<String> rightAttrs = new ArrayList<>();
            for (RewriteCond cond : joinConds) {
                if (containsVar(joined, cond.leftVar())) {
                    leftAttrs.add(stripDollar(cond.leftVar()));
                    rightAttrs.add(stripDollar(cond.rightVar()));
                } else {
                    leftAttrs.add(stripDollar(cond.rightVar()));
                    rightAttrs.add(stripDollar(cond.leftVar()));
                }
            }
            current = "join(\n" + current + ",\n" + buildTupleQuery(next) + ",\n"
                + attrListText(leftAttrs) + ", " + attrListText(rightAttrs) + "\n)";
            joined.add(next);
        }
        return current;
    }

    private static List<RewriteCond> joinConditionsBetween(Set<RewriteGroup> joined, RewriteGroup next) {
        List<RewriteCond> result = new ArrayList<>();
        for (RewriteCond cond : next.joinConditions) {
            boolean leftJoined = containsVar(joined, cond.leftVar());
            boolean rightJoined = containsVar(joined, cond.rightVar());
            boolean leftNext = next.hasVar(cond.leftVar());
            boolean rightNext = next.hasVar(cond.rightVar());
            if ((leftJoined && rightNext) || (rightJoined && leftNext)) {
                result.add(cond);
            }
        }
        return result;
    }

    private static boolean containsVar(Set<RewriteGroup> groups, String var) {
        for (RewriteGroup group : groups) {
            if (group.hasVar(var)) {
                return true;
            }
        }
        return false;
    }

    private static String buildTupleQuery(RewriteGroup group) {
        StringBuilder query = new StringBuilder();
        query.append("for ");
        for (int i = 0; i < group.bindings.size(); i++) {
            ForBinding binding = group.bindings.get(i);
            if (i > 0) {
                query.append(", ");
            }
            query.append(binding.var).append(" in ").append(binding.expr);
        }
        if (!group.localConditions.isEmpty()) {
            query.append("\nwhere ");
            for (int i = 0; i < group.localConditions.size(); i++) {
                if (i > 0) {
                    query.append(" and ");
                }
                query.append(group.localConditions.get(i).toQuery());
            }
        }
        query.append("\nreturn <tuple>{");
        for (int i = 0; i < group.bindings.size(); i++) {
            ForBinding binding = group.bindings.get(i);
            if (i > 0) {
                query.append(", ");
            }
            String name = stripDollar(binding.var);
            query.append("<").append(name).append(">{").append(binding.var).append("}</").append(name).append(">");
        }
        query.append("}</tuple>");
        return query.toString();
    }

    private static String attrListText(List<String> attrs) {
        return "[" + String.join(", ", attrs) + "]";
    }

    private static String rewriteReturn(String returnText, List<ForBinding> bindings) {
        List<ForBinding> sorted = new ArrayList<>(bindings);
        Collections.sort(sorted, (a, b) -> b.var.length() - a.var.length());
        String rewritten = returnText;
        for (ForBinding binding : sorted) {
            String var = Pattern.quote(binding.var);
            String replacement = "\\$tuple/" + stripDollar(binding.var) + "/*";
            rewritten = rewritten.replaceAll(var + "(?![A-Za-z0-9_.-])", replacement);
        }
        return rewritten;
    }

    private static String stripDollar(String var) {
        return var.startsWith("$") ? var.substring(1) : var;
    }

    private static final class ForBinding {
        final String var;
        final String expr;

        ForBinding(String var, String expr) {
            this.var = var;
            this.expr = expr;
        }
    }

    private static final class RewriteGroup {
        final List<ForBinding> bindings = new ArrayList<>();
        final List<RewriteCond> localConditions = new ArrayList<>();
        final List<RewriteCond> joinConditions = new ArrayList<>();

        boolean hasVar(String var) {
            for (ForBinding binding : bindings) {
                if (binding.var.equals(var)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static final class RewriteCond {
        final String left;
        final String right;

        RewriteCond(String left, String right) {
            this.left = left;
            this.right = right;
        }

        String leftVar() {
            return left.startsWith("$") ? left : null;
        }

        String rightVar() {
            return right.startsWith("$") ? right : null;
        }

        String toQuery() {
            return left + " eq " + right;
        }
    }

    /**
     * Collect the direct-child Var terminal node texts of a rule context, in
     * left-to-right order. Distinguishes between Var tokens and child subrules
     * whose text happens to start with '$'.
     */
    private static List<String> directChildVars(org.antlr.v4.runtime.ParserRuleContext ctx) {
        List<String> vars = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            org.antlr.v4.runtime.tree.ParseTree child = ctx.getChild(i);
            if (child instanceof org.antlr.v4.runtime.tree.TerminalNode) {
                org.antlr.v4.runtime.tree.TerminalNode tn =
                    (org.antlr.v4.runtime.tree.TerminalNode) child;
                if (tn.getSymbol().getType() == XQueryParser.Var) {
                    vars.add(tn.getText());
                }
            }
        }
        return vars;
    }

    /**
     * Rule 40: FLWR evaluation.
     * Iterates over for-bindings as nested loops, applies let-bindings,
     * filters by the where-condition, and accumulates the return body.
     * No unique() on the final result.
     */
    private static List<Node> evalFLWR(XQueryParser.XqValueContext xv, Context ctx) {
        XQueryParser.ForClauseContext fc = xv.forClause();
        XQueryParser.LetClauseContext lc = xv.letClause();
        XQueryParser.WhereClauseContext wc = xv.whereClause();
        XQueryParser.ReturnClauseContext rc = xv.returnClause();

        // Grammar: 'for' Var 'in' xqValue (',' Var 'in' xqValue)*
        List<String> forVars = directChildVars(fc);
        List<XQueryParser.XqValueContext> forExprs = fc.xqValue();

        List<Node> result = new ArrayList<>();
        flwrLoop(forVars, forExprs, 0, ctx, lc, wc, rc, result);
        return result;
    }

    private static void flwrLoop(
            List<String> forVars,
            List<XQueryParser.XqValueContext> forExprs,
            int i,
            Context ctx,
            XQueryParser.LetClauseContext lc,
            XQueryParser.WhereClauseContext wc,
            XQueryParser.ReturnClauseContext rc,
            List<Node> result) {
        if (i == forVars.size()) {
            // All for variables have been bound. Apply let bindings.
            Context inner = ctx;
            if (lc != null) {
                inner = applyLet(lc, inner);
            }
            // Apply where filter.
            if (wc != null && !evalCond(wc.cond(), inner)) {
                return;
            }
            // Append return.
            result.addAll(evalXq(rc.xq(), inner));
            return;
        }
        // Iterate over the i-th for binding.
        List<Node> values = evalXqValue(forExprs.get(i), ctx);
        for (Node v : values) {
            Context extended = ctx.extend(forVars.get(i), Collections.<Node>singletonList(v));
            flwrLoop(forVars, forExprs, i + 1, extended, lc, wc, rc, result);
        }
    }

    /**
     * Rules 38-39: let-expression (letClause xqValue).
     * Bindings are applied left-to-right, each in the context extended by
     * the previous ones.
     */
    private static List<Node> evalLetExpr(
            XQueryParser.LetClauseContext lc,
            XQueryParser.XqValueContext body,
            Context ctx) {
        Context extended = applyLet(lc, ctx);
        return evalXqValue(body, extended);
    }

    /**
     * Apply a let clause to a context, returning a new context with the
     * bindings added sequentially.
     * Grammar: 'let' Var ':=' xqValue (',' Var ':=' xqValue)*
     */
    private static Context applyLet(XQueryParser.LetClauseContext lc, Context ctx) {
        List<String> vars = directChildVars(lc);
        List<XQueryParser.XqValueContext> exprs = lc.xqValue();
        Context extended = ctx;
        for (int i = 0; i < vars.size(); i++) {
            List<Node> value = evalXqValue(exprs.get(i), extended);
            extended = extended.extend(vars.get(i), value);
        }
        return extended;
    }

    /**
     * Element constructor (rule 29):
     *   makeElem(t, l) creates a new element with tag t and deep copies of l
     *   as children.
     */
    private static Element makeElem(String tag, List<Node> children) {
        Element el = outputDoc.createElement(tag);
        for (Node child : children) {
            Node copy;
            if (child.getNodeType() == Node.ATTRIBUTE_NODE) {
                // Attributes can't be appended as children; render as a text
                // node containing the attribute value (defensive fallback).
                copy = outputDoc.createTextNode(((Attr) child).getValue());
            } else if (child.getOwnerDocument() == outputDoc && child.getParentNode() == null) {
                // Freshly-built node, owned by outputDoc, not yet attached:
                // use as-is (no clone) to avoid wasted work.
                copy = child;
            } else {
                // Deep import (clone) of an input-doc node, or detach via clone
                // from another parent.
                copy = outputDoc.importNode(child, true);
            }
            el.appendChild(copy);
        }
        return el;
    }

    // ====================================================================
    // Condition evaluation (Cond)
    // ====================================================================

    private static boolean evalCond(XQueryParser.CondContext cond, Context ctx) {
        int n = cond.getChildCount();
        String first = cond.getChild(0).getText();

        // Rule 32: empty(xq)
        if ("empty".equals(first)) {
            return evalXq(cond.xq(), ctx).isEmpty();
        }

        // Rule 33: some Var in xqValue (, Var in xqValue)* satisfies cond
        if ("some".equals(first)) {
            return evalSome(cond, ctx);
        }

        // Rule 34: (cond)
        if (n == 3 && "(".equals(first)) {
            return evalCond(cond.cond(0), ctx);
        }

        // Rule 37: not cond
        if ("not".equals(first)) {
            return !evalCond(cond.cond(0), ctx);
        }

        if (n == 3) {
            String op = cond.getChild(1).getText();
            // Rules 35, 36: and / or
            if ("and".equals(op)) {
                return evalCond(cond.cond(0), ctx) && evalCond(cond.cond(1), ctx);
            }
            if ("or".equals(op)) {
                return evalCond(cond.cond(0), ctx) || evalCond(cond.cond(1), ctx);
            }
            // Rules 30, 31: value / identity equality
            if ("=".equals(op) || "eq".equals(op)) {
                return existsEq(
                    evalXqValue(cond.xqValue(0), ctx),
                    evalXqValue(cond.xqValue(1), ctx),
                    false);
            }
            if ("==".equals(op) || "is".equals(op)) {
                return existsEq(
                    evalXqValue(cond.xqValue(0), ctx),
                    evalXqValue(cond.xqValue(1), ctx),
                    true);
            }
        }

        throw new IllegalArgumentException("Unsupported cond: " + cond.getText());
    }

    /**
     * Rule 33: some Var1 in XQ1, ..., Varn in XQn satisfies Cond.
     */
    private static boolean evalSome(XQueryParser.CondContext cond, Context ctx) {
        List<String> vars = directChildVars(cond);
        List<XQueryParser.XqValueContext> exprs = cond.xqValue();
        XQueryParser.CondContext body = cond.cond(0);
        return someLoop(vars, exprs, 0, ctx, body);
    }

    private static boolean someLoop(
            List<String> vars,
            List<XQueryParser.XqValueContext> exprs,
            int i,
            Context ctx,
            XQueryParser.CondContext body) {
        if (i == vars.size()) {
            return evalCond(body, ctx);
        }
        List<Node> values = evalXqValue(exprs.get(i), ctx);
        for (Node v : values) {
            Context extended = ctx.extend(vars.get(i), Collections.<Node>singletonList(v));
            if (someLoop(vars, exprs, i + 1, extended, body)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Existential equality check: returns true iff there is an x in left and
     * y in right such that x and y are equal (by identity or value).
     */
    private static boolean existsEq(List<Node> left, List<Node> right, boolean identity) {
        for (Node a : left) {
            for (Node b : right) {
                if (identity ? a == b : valueEqual(a, b)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Value equality: tree isomorphism for elements; node value equality for
     * text/attribute nodes.
     */
    private static boolean valueEqual(Node a, Node b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        short ta = a.getNodeType();
        short tb = b.getNodeType();
        if (ta != tb) return false;
        if (ta == Node.TEXT_NODE) {
            String va = a.getNodeValue();
            String vb = b.getNodeValue();
            return va == null ? vb == null : va.equals(vb);
        }
        if (ta == Node.ATTRIBUTE_NODE) {
            return a.getNodeName().equals(b.getNodeName())
                && a.getNodeValue().equals(b.getNodeValue());
        }
        if (ta == Node.ELEMENT_NODE) {
            if (!a.getNodeName().equals(b.getNodeName())) return false;
            NodeList ac = a.getChildNodes();
            NodeList bc = b.getChildNodes();
            if (ac.getLength() != bc.getLength()) return false;
            for (int i = 0; i < ac.getLength(); i++) {
                if (!valueEqual(ac.item(i), bc.item(i))) return false;
            }
            return true;
        }
        return false;
    }

    // ====================================================================
    // XPath (Milestone 1) - unchanged semantics, retargeted to XQuery types.
    // ====================================================================

    // Rule 1, 2
    private static List<Node> evalAp(XQueryParser.ApContext ap) {
        // Tokens: ('doc'|'document') '(' StringConstant ')' ('/'|'//') rp
        String filename = stripQuotes(ap.StringConstant().getText());
        Document document = loadDoc(filename);
        // The separator is the 5th child (index 4).
        String separator = ap.getChild(4).getText();
        XQueryParser.RpContext rp = ap.rp();
        if ("//".equals(separator)) {
            return evalDescendantSlash(
                Collections.<Node>singletonList((Node) document), rp);
        }
        return evalRp(rp, document);
    }

    private static List<Node> evalRp(XQueryParser.RpContext rp, Node node) {
        // Rule 3: tagName
        if (rp.tagName() != null) {
            return evalTagName(rp.tagName().getText(), node);
        }

        int childCount = rp.getChildCount();
        String first = rp.getChild(0).getText();

        if (childCount == 1) {
            // Rule 4
            if ("*".equals(first)) {
                return elementChildren(node);
            }
            // Rule 5
            if (".".equals(first)) {
                return Collections.singletonList(node);
            }
            // Rule 6
            if ("..".equals(first)) {
                return parent(node);
            }
        }
        // Rule 8
        if (childCount == 2 && "@".equals(first)) {
            return attribute(node, rp.attName().getText());
        }

        if (childCount == 3) {
            // Rule 7
            if ("text".equals(first)) {
                return textChildren(node);
            }
            // Rule 9
            if ("(".equals(first)) {
                return evalRp(rp.rp(0), node);
            }
            // Rules 10, 11, 13
            return evalBinaryRp(rp, node);
        }
        // Rule 12
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

    private static List<Node> evalBinaryRp(XQueryParser.RpContext rp, Node node) {
        String op = rp.getChild(1).getText();
        // Rule 10
        if ("/".equals(op)) {
            List<Node> result = new ArrayList<>();
            for (Node left : evalRp(rp.rp(0), node)) {
                result.addAll(evalRp(rp.rp(1), left));
            }
            return unique(result);
        }
        // Rule 11
        if ("//".equals(op)) {
            return evalDescendantSlash(evalRp(rp.rp(0), node), rp.rp(1));
        }
        // Rule 13
        if (",".equals(op)) {
            List<Node> result = new ArrayList<>(evalRp(rp.rp(0), node));
            result.addAll(evalRp(rp.rp(1), node));
            return unique(result);
        }
        throw new IllegalArgumentException("Unsupported relative XPath operator: " + op);
    }

    private static List<Node> evalDescendantSlash(List<Node> starts, XQueryParser.RpContext right) {
        List<Node> result = new ArrayList<>();
        for (Node current : starts) {
            result.addAll(evalRp(right, current));
            for (Node descendant : descendants(current)) {
                result.addAll(evalRp(right, descendant));
            }
        }
        return unique(result);
    }

    private static boolean evalFilter(XQueryParser.FContext filter, Node node) {
        int childCount = filter.getChildCount();
        String first = filter.getChild(0).getText();
        // Rule 14
        if (childCount == 1) {
            return !evalRp(filter.rp(0), node).isEmpty();
        }
        // Rule 21
        if (childCount == 2 && "not".equals(first)) {
            return !evalFilter(filter.f(0), node);
        }

        if (childCount == 3) {
            String op = filter.getChild(1).getText();
            // Rule 18
            if ("(".equals(first)) {
                return evalFilter(filter.f(0), node);
            }
            // Rule 19
            if ("and".equals(op)) {
                return evalFilter(filter.f(0), node) && evalFilter(filter.f(1), node);
            }
            // Rule 20
            if ("or".equals(op)) {
                return evalFilter(filter.f(0), node) || evalFilter(filter.f(1), node);
            }
            // Rule 15
            if ("=".equals(op) || "eq".equals(op)) {
                if (filter.StringConstant() != null) {
                    return compareWithString(filter.rp(0),
                        stripQuotes(filter.StringConstant().getText()), node);
                }
                return compareNodes(filter.rp(0), filter.rp(1), node, false);
            }
            // Rule 16
            if ("==".equals(op) || "is".equals(op)) {
                return compareNodes(filter.rp(0), filter.rp(1), node, true);
            }
        }

        throw new IllegalArgumentException("Unsupported filter: " + filter.getText());
    }

    private static boolean compareNodes(
            XQueryParser.RpContext left,
            XQueryParser.RpContext right,
            Node node,
            boolean identity) {
        return existsEq(evalRp(left, node), evalRp(right, node), identity);
    }

    private static boolean compareWithString(XQueryParser.RpContext rp, String value, Node node) {
        for (Node candidate : evalRp(rp, node)) {
            if (nodeStringValue(candidate).equals(value)) {
                return true;
            }
        }
        return false;
    }

    // ====================================================================
    // Helpers
    // ====================================================================

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
        return Collections.singletonList((Node) attr);
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

    private static String nodeStringValue(Node node) {
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

    // ====================================================================
    // Serialization
    // ====================================================================

    private static void writeResult(List<Node> nodes, String outputPath) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document result = builder.newDocument();

        // If the result is exactly one element node, emit it as the root. This
        // is the case for typical Milestone 2 queries that wrap their output
        // in a single element constructor such as <result>{...}</result>.
        // Otherwise (Milestone 1 XPath queries or multi-node results), wrap
        // everything in a <RESULT> root.
        if (nodes.size() == 1 && nodes.get(0).getNodeType() == Node.ELEMENT_NODE) {
            result.appendChild(result.importNode(nodes.get(0), true));
        } else {
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
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.transform(new DOMSource(result), new StreamResult(new File(outputPath)));
    }

    // ====================================================================
    // Error listener that throws on syntax errors
    // ====================================================================

    private static final class ThrowingErrorListener extends BaseErrorListener {
        static final ThrowingErrorListener INSTANCE = new ThrowingErrorListener();

        @Override
        public void syntaxError(
                Recognizer<?, ?> recognizer,
                Object offendingSymbol,
                int line,
                int charPositionInLine,
                String message,
                RecognitionException e) {
            throw new IllegalArgumentException(
                "XQuery syntax error at " + line + ":" + charPositionInLine + " " + message, e);
        }
    }
}
