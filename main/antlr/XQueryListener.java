// Generated from main/antlr/XQuery.g4 by ANTLR 4.13.2
package main.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XQueryParser}.
 */
public interface XQueryListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link XQueryParser#xqMain}.
	 * @param ctx the parse tree
	 */
	void enterXqMain(XQueryParser.XqMainContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#xqMain}.
	 * @param ctx the parse tree
	 */
	void exitXqMain(XQueryParser.XqMainContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterAp(XQueryParser.ApContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitAp(XQueryParser.ApContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRp(XQueryParser.RpContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRp(XQueryParser.RpContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void enterF(XQueryParser.FContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#f}.
	 * @param ctx the parse tree
	 */
	void exitF(XQueryParser.FContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXq(XQueryParser.XqContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXq(XQueryParser.XqContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#xqValue}.
	 * @param ctx the parse tree
	 */
	void enterXqValue(XQueryParser.XqValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#xqValue}.
	 * @param ctx the parse tree
	 */
	void exitXqValue(XQueryParser.XqValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XQueryParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XQueryParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XQueryParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XQueryParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(XQueryParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(XQueryParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XQueryParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XQueryParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQueryParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XQueryParser.AttNameContext ctx);
}