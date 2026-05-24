// Generated from main/antlr/XQuery.g4 by ANTLR 4.13.2
package main.antlr;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class XQueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, Var=36, StringConstant=37, Name=38, 
		WS=39;
	public static final int
		RULE_xqMain = 0, RULE_ap = 1, RULE_rp = 2, RULE_f = 3, RULE_xq = 4, RULE_xqValue = 5, 
		RULE_forClause = 6, RULE_letClause = 7, RULE_whereClause = 8, RULE_returnClause = 9, 
		RULE_cond = 10, RULE_tagName = 11, RULE_attName = 12;
	private static String[] makeRuleNames() {
		return new String[] {
			"xqMain", "ap", "rp", "f", "xq", "xqValue", "forClause", "letClause", 
			"whereClause", "returnClause", "cond", "tagName", "attName"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'doc'", "'document'", "'('", "')'", "'/'", "'//'", "'['", "']'", 
			"','", "'*'", "'.'", "'..'", "'text'", "'@'", "'='", "'eq'", "'=='", 
			"'is'", "'and'", "'or'", "'not'", "'<'", "'>'", "'{'", "'}'", "'</'", 
			"'for'", "'in'", "'let'", "':='", "'where'", "'return'", "'empty'", "'some'", 
			"'satisfies'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"Var", "StringConstant", "Name", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "XQuery.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public XQueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class XqMainContext extends ParserRuleContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public TerminalNode EOF() { return getToken(XQueryParser.EOF, 0); }
		public XqMainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xqMain; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterXqMain(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitXqMain(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqMain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XqMainContext xqMain() throws RecognitionException {
		XqMainContext _localctx = new XqMainContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_xqMain);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(26);
			xq();
			setState(27);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ApContext extends ParserRuleContext {
		public TerminalNode StringConstant() { return getToken(XQueryParser.StringConstant, 0); }
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public ApContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ap; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterAp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitAp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitAp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ApContext ap() throws RecognitionException {
		ApContext _localctx = new ApContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ap);
		int _la;
		try {
			setState(41);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				_la = _input.LA(1);
				if ( !(_la==T__0 || _la==T__1) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(30);
				match(T__2);
				setState(31);
				match(StringConstant);
				setState(32);
				match(T__3);
				setState(33);
				match(T__4);
				setState(34);
				rp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(35);
				_la = _input.LA(1);
				if ( !(_la==T__0 || _la==T__1) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(36);
				match(T__2);
				setState(37);
				match(StringConstant);
				setState(38);
				match(T__3);
				setState(39);
				match(T__5);
				setState(40);
				rp(0);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RpContext extends ParserRuleContext {
		public TagNameContext tagName() {
			return getRuleContext(TagNameContext.class,0);
		}
		public AttNameContext attName() {
			return getRuleContext(AttNameContext.class,0);
		}
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public FContext f() {
			return getRuleContext(FContext.class,0);
		}
		public RpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterRp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitRp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitRp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RpContext rp() throws RecognitionException {
		return rp(0);
	}

	private RpContext rp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		RpContext _localctx = new RpContext(_ctx, _parentState);
		RpContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_rp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Name:
				{
				setState(44);
				tagName();
				}
				break;
			case T__9:
				{
				setState(45);
				match(T__9);
				}
				break;
			case T__10:
				{
				setState(46);
				match(T__10);
				}
				break;
			case T__11:
				{
				setState(47);
				match(T__11);
				}
				break;
			case T__12:
				{
				setState(48);
				match(T__12);
				setState(49);
				match(T__2);
				setState(50);
				match(T__3);
				}
				break;
			case T__13:
				{
				setState(51);
				match(T__13);
				setState(52);
				attName();
				}
				break;
			case T__2:
				{
				setState(53);
				match(T__2);
				setState(54);
				rp(0);
				setState(55);
				match(T__3);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(75);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(73);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(59);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(60);
						match(T__4);
						setState(61);
						rp(12);
						}
						break;
					case 2:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(62);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(63);
						match(T__5);
						setState(64);
						rp(11);
						}
						break;
					case 3:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(65);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(66);
						match(T__8);
						setState(67);
						rp(9);
						}
						break;
					case 4:
						{
						_localctx = new RpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_rp);
						setState(68);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(69);
						match(T__6);
						setState(70);
						f(0);
						setState(71);
						match(T__7);
						}
						break;
					}
					} 
				}
				setState(77);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FContext extends ParserRuleContext {
		public List<RpContext> rp() {
			return getRuleContexts(RpContext.class);
		}
		public RpContext rp(int i) {
			return getRuleContext(RpContext.class,i);
		}
		public TerminalNode StringConstant() { return getToken(XQueryParser.StringConstant, 0); }
		public List<FContext> f() {
			return getRuleContexts(FContext.class);
		}
		public FContext f(int i) {
			return getRuleContext(FContext.class,i);
		}
		public FContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_f; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterF(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitF(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitF(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FContext f() throws RecognitionException {
		return f(0);
	}

	private FContext f(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		FContext _localctx = new FContext(_ctx, _parentState);
		FContext _prevctx = _localctx;
		int _startState = 6;
		enterRecursionRule(_localctx, 6, RULE_f, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(79);
				rp(0);
				}
				break;
			case 2:
				{
				setState(80);
				rp(0);
				setState(81);
				match(T__14);
				setState(82);
				rp(0);
				}
				break;
			case 3:
				{
				setState(84);
				rp(0);
				setState(85);
				match(T__15);
				setState(86);
				rp(0);
				}
				break;
			case 4:
				{
				setState(88);
				rp(0);
				setState(89);
				match(T__16);
				setState(90);
				rp(0);
				}
				break;
			case 5:
				{
				setState(92);
				rp(0);
				setState(93);
				match(T__17);
				setState(94);
				rp(0);
				}
				break;
			case 6:
				{
				setState(96);
				rp(0);
				setState(97);
				match(T__14);
				setState(98);
				match(StringConstant);
				}
				break;
			case 7:
				{
				setState(100);
				match(T__2);
				setState(101);
				f(0);
				setState(102);
				match(T__3);
				}
				break;
			case 8:
				{
				setState(104);
				match(T__20);
				setState(105);
				f(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(116);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(114);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
					case 1:
						{
						_localctx = new FContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(108);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(109);
						match(T__18);
						setState(110);
						f(4);
						}
						break;
					case 2:
						{
						_localctx = new FContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_f);
						setState(111);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(112);
						match(T__19);
						setState(113);
						f(3);
						}
						break;
					}
					} 
				}
				setState(118);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,6,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class XqContext extends ParserRuleContext {
		public List<XqValueContext> xqValue() {
			return getRuleContexts(XqValueContext.class);
		}
		public XqValueContext xqValue(int i) {
			return getRuleContext(XqValueContext.class,i);
		}
		public XqContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xq; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterXq(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitXq(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXq(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XqContext xq() throws RecognitionException {
		XqContext _localctx = new XqContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_xq);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			xqValue(0);
			setState(124);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(120);
					match(T__8);
					setState(121);
					xqValue(0);
					}
					} 
				}
				setState(126);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class XqValueContext extends ParserRuleContext {
		public TerminalNode Var() { return getToken(XQueryParser.Var, 0); }
		public TerminalNode StringConstant() { return getToken(XQueryParser.StringConstant, 0); }
		public ApContext ap() {
			return getRuleContext(ApContext.class,0);
		}
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public List<TagNameContext> tagName() {
			return getRuleContexts(TagNameContext.class);
		}
		public TagNameContext tagName(int i) {
			return getRuleContext(TagNameContext.class,i);
		}
		public ForClauseContext forClause() {
			return getRuleContext(ForClauseContext.class,0);
		}
		public ReturnClauseContext returnClause() {
			return getRuleContext(ReturnClauseContext.class,0);
		}
		public LetClauseContext letClause() {
			return getRuleContext(LetClauseContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public XqValueContext xqValue() {
			return getRuleContext(XqValueContext.class,0);
		}
		public RpContext rp() {
			return getRuleContext(RpContext.class,0);
		}
		public XqValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xqValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterXqValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitXqValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitXqValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final XqValueContext xqValue() throws RecognitionException {
		return xqValue(0);
	}

	private XqValueContext xqValue(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		XqValueContext _localctx = new XqValueContext(_ctx, _parentState);
		XqValueContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_xqValue, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Var:
				{
				setState(128);
				match(Var);
				}
				break;
			case StringConstant:
				{
				setState(129);
				match(StringConstant);
				}
				break;
			case T__0:
			case T__1:
				{
				setState(130);
				ap();
				}
				break;
			case T__2:
				{
				setState(131);
				match(T__2);
				setState(132);
				xq();
				setState(133);
				match(T__3);
				}
				break;
			case T__21:
				{
				setState(135);
				match(T__21);
				setState(136);
				tagName();
				setState(137);
				match(T__22);
				setState(138);
				match(T__23);
				setState(139);
				xq();
				setState(140);
				match(T__24);
				setState(141);
				match(T__25);
				setState(142);
				tagName();
				setState(143);
				match(T__22);
				}
				break;
			case T__26:
				{
				setState(145);
				forClause();
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__28) {
					{
					setState(146);
					letClause();
					}
				}

				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__30) {
					{
					setState(149);
					whereClause();
					}
				}

				setState(152);
				returnClause();
				}
				break;
			case T__28:
				{
				setState(154);
				letClause();
				setState(155);
				xqValue(1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(167);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(165);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new XqValueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_xqValue);
						setState(159);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(160);
						match(T__4);
						setState(161);
						rp(0);
						}
						break;
					case 2:
						{
						_localctx = new XqValueContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_xqValue);
						setState(162);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(163);
						match(T__5);
						setState(164);
						rp(0);
						}
						break;
					}
					} 
				}
				setState(169);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForClauseContext extends ParserRuleContext {
		public List<TerminalNode> Var() { return getTokens(XQueryParser.Var); }
		public TerminalNode Var(int i) {
			return getToken(XQueryParser.Var, i);
		}
		public List<XqValueContext> xqValue() {
			return getRuleContexts(XqValueContext.class);
		}
		public XqValueContext xqValue(int i) {
			return getRuleContext(XqValueContext.class,i);
		}
		public ForClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterForClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitForClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitForClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForClauseContext forClause() throws RecognitionException {
		ForClauseContext _localctx = new ForClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_forClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			match(T__26);
			setState(171);
			match(Var);
			setState(172);
			match(T__27);
			setState(173);
			xqValue(0);
			setState(180);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(174);
				match(T__8);
				setState(175);
				match(Var);
				setState(176);
				match(T__27);
				setState(177);
				xqValue(0);
				}
				}
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LetClauseContext extends ParserRuleContext {
		public List<TerminalNode> Var() { return getTokens(XQueryParser.Var); }
		public TerminalNode Var(int i) {
			return getToken(XQueryParser.Var, i);
		}
		public List<XqValueContext> xqValue() {
			return getRuleContexts(XqValueContext.class);
		}
		public XqValueContext xqValue(int i) {
			return getRuleContext(XqValueContext.class,i);
		}
		public LetClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_letClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterLetClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitLetClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitLetClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LetClauseContext letClause() throws RecognitionException {
		LetClauseContext _localctx = new LetClauseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_letClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(T__28);
			setState(184);
			match(Var);
			setState(185);
			match(T__29);
			setState(186);
			xqValue(0);
			setState(193);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(187);
				match(T__8);
				setState(188);
				match(Var);
				setState(189);
				match(T__29);
				setState(190);
				xqValue(0);
				}
				}
				setState(195);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhereClauseContext extends ParserRuleContext {
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterWhereClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitWhereClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(T__30);
			setState(197);
			cond(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnClauseContext extends ParserRuleContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public ReturnClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterReturnClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitReturnClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitReturnClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnClauseContext returnClause() throws RecognitionException {
		ReturnClauseContext _localctx = new ReturnClauseContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_returnClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			match(T__31);
			setState(200);
			xq();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CondContext extends ParserRuleContext {
		public XqContext xq() {
			return getRuleContext(XqContext.class,0);
		}
		public List<TerminalNode> Var() { return getTokens(XQueryParser.Var); }
		public TerminalNode Var(int i) {
			return getToken(XQueryParser.Var, i);
		}
		public List<XqValueContext> xqValue() {
			return getRuleContexts(XqValueContext.class);
		}
		public XqValueContext xqValue(int i) {
			return getRuleContext(XqValueContext.class,i);
		}
		public List<CondContext> cond() {
			return getRuleContexts(CondContext.class);
		}
		public CondContext cond(int i) {
			return getRuleContext(CondContext.class,i);
		}
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitCond(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		return cond(0);
	}

	private CondContext cond(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		CondContext _localctx = new CondContext(_ctx, _parentState);
		CondContext _prevctx = _localctx;
		int _startState = 20;
		enterRecursionRule(_localctx, 20, RULE_cond, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(203);
				match(T__32);
				setState(204);
				match(T__2);
				setState(205);
				xq();
				setState(206);
				match(T__3);
				}
				break;
			case 2:
				{
				setState(208);
				match(T__33);
				setState(209);
				match(Var);
				setState(210);
				match(T__27);
				setState(211);
				xqValue(0);
				setState(218);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(212);
					match(T__8);
					setState(213);
					match(Var);
					setState(214);
					match(T__27);
					setState(215);
					xqValue(0);
					}
					}
					setState(220);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(221);
				match(T__34);
				setState(222);
				cond(9);
				}
				break;
			case 3:
				{
				setState(224);
				xqValue(0);
				setState(225);
				match(T__14);
				setState(226);
				xqValue(0);
				}
				break;
			case 4:
				{
				setState(228);
				xqValue(0);
				setState(229);
				match(T__15);
				setState(230);
				xqValue(0);
				}
				break;
			case 5:
				{
				setState(232);
				xqValue(0);
				setState(233);
				match(T__16);
				setState(234);
				xqValue(0);
				}
				break;
			case 6:
				{
				setState(236);
				xqValue(0);
				setState(237);
				match(T__17);
				setState(238);
				xqValue(0);
				}
				break;
			case 7:
				{
				setState(240);
				match(T__2);
				setState(241);
				cond(0);
				setState(242);
				match(T__3);
				}
				break;
			case 8:
				{
				setState(244);
				match(T__20);
				setState(245);
				cond(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(256);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(254);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
					case 1:
						{
						_localctx = new CondContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_cond);
						setState(248);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(249);
						match(T__18);
						setState(250);
						cond(4);
						}
						break;
					case 2:
						{
						_localctx = new CondContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_cond);
						setState(251);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(252);
						match(T__19);
						setState(253);
						cond(3);
						}
						break;
					}
					} 
				}
				setState(258);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TagNameContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(XQueryParser.Name, 0); }
		public TagNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterTagName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitTagName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitTagName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TagNameContext tagName() throws RecognitionException {
		TagNameContext _localctx = new TagNameContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_tagName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
			match(Name);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttNameContext extends ParserRuleContext {
		public TerminalNode Name() { return getToken(XQueryParser.Name, 0); }
		public AttNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).enterAttName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof XQueryListener ) ((XQueryListener)listener).exitAttName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof XQueryVisitor ) return ((XQueryVisitor<? extends T>)visitor).visitAttName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttNameContext attName() throws RecognitionException {
		AttNameContext _localctx = new AttNameContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_attName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			match(Name);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return rp_sempred((RpContext)_localctx, predIndex);
		case 3:
			return f_sempred((FContext)_localctx, predIndex);
		case 5:
			return xqValue_sempred((XqValueContext)_localctx, predIndex);
		case 10:
			return cond_sempred((CondContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean rp_sempred(RpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 11);
		case 1:
			return precpred(_ctx, 10);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 9);
		}
		return true;
	}
	private boolean f_sempred(FContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 3);
		case 5:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean xqValue_sempred(XqValueContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 8);
		}
		return true;
	}
	private boolean cond_sempred(CondContext _localctx, int predIndex) {
		switch (predIndex) {
		case 8:
			return precpred(_ctx, 3);
		case 9:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u0108\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001*\b\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0003\u0002:\b\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002J\b\u0002\n\u0002\f\u0002M\t\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0003\u0003k\b\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003s\b\u0003"+
		"\n\u0003\f\u0003v\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"{\b\u0004\n\u0004\f\u0004~\t\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u0094\b\u0005\u0001\u0005\u0003\u0005\u0097\b\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005\u009e\b\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005"+
		"\u0005\u00a6\b\u0005\n\u0005\f\u0005\u00a9\t\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0005\u0006\u00b3\b\u0006\n\u0006\f\u0006\u00b6\t\u0006\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0005\u0007\u00c0\b\u0007\n\u0007\f\u0007\u00c3\t\u0007\u0001\b"+
		"\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0005\n\u00d9\b\n\n\n\f\n\u00dc\t\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u00f7\b\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0005\n\u00ff\b\n\n\n\f\n\u0102\t\n\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0000\u0004\u0004\u0006\n\u0014\r"+
		"\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u0000"+
		"\u0001\u0001\u0000\u0001\u0002\u0125\u0000\u001a\u0001\u0000\u0000\u0000"+
		"\u0002)\u0001\u0000\u0000\u0000\u00049\u0001\u0000\u0000\u0000\u0006j"+
		"\u0001\u0000\u0000\u0000\bw\u0001\u0000\u0000\u0000\n\u009d\u0001\u0000"+
		"\u0000\u0000\f\u00aa\u0001\u0000\u0000\u0000\u000e\u00b7\u0001\u0000\u0000"+
		"\u0000\u0010\u00c4\u0001\u0000\u0000\u0000\u0012\u00c7\u0001\u0000\u0000"+
		"\u0000\u0014\u00f6\u0001\u0000\u0000\u0000\u0016\u0103\u0001\u0000\u0000"+
		"\u0000\u0018\u0105\u0001\u0000\u0000\u0000\u001a\u001b\u0003\b\u0004\u0000"+
		"\u001b\u001c\u0005\u0000\u0000\u0001\u001c\u0001\u0001\u0000\u0000\u0000"+
		"\u001d\u001e\u0007\u0000\u0000\u0000\u001e\u001f\u0005\u0003\u0000\u0000"+
		"\u001f \u0005%\u0000\u0000 !\u0005\u0004\u0000\u0000!\"\u0005\u0005\u0000"+
		"\u0000\"*\u0003\u0004\u0002\u0000#$\u0007\u0000\u0000\u0000$%\u0005\u0003"+
		"\u0000\u0000%&\u0005%\u0000\u0000&\'\u0005\u0004\u0000\u0000\'(\u0005"+
		"\u0006\u0000\u0000(*\u0003\u0004\u0002\u0000)\u001d\u0001\u0000\u0000"+
		"\u0000)#\u0001\u0000\u0000\u0000*\u0003\u0001\u0000\u0000\u0000+,\u0006"+
		"\u0002\uffff\uffff\u0000,:\u0003\u0016\u000b\u0000-:\u0005\n\u0000\u0000"+
		".:\u0005\u000b\u0000\u0000/:\u0005\f\u0000\u000001\u0005\r\u0000\u0000"+
		"12\u0005\u0003\u0000\u00002:\u0005\u0004\u0000\u000034\u0005\u000e\u0000"+
		"\u00004:\u0003\u0018\f\u000056\u0005\u0003\u0000\u000067\u0003\u0004\u0002"+
		"\u000078\u0005\u0004\u0000\u00008:\u0001\u0000\u0000\u00009+\u0001\u0000"+
		"\u0000\u00009-\u0001\u0000\u0000\u00009.\u0001\u0000\u0000\u00009/\u0001"+
		"\u0000\u0000\u000090\u0001\u0000\u0000\u000093\u0001\u0000\u0000\u0000"+
		"95\u0001\u0000\u0000\u0000:K\u0001\u0000\u0000\u0000;<\n\u000b\u0000\u0000"+
		"<=\u0005\u0005\u0000\u0000=J\u0003\u0004\u0002\f>?\n\n\u0000\u0000?@\u0005"+
		"\u0006\u0000\u0000@J\u0003\u0004\u0002\u000bAB\n\b\u0000\u0000BC\u0005"+
		"\t\u0000\u0000CJ\u0003\u0004\u0002\tDE\n\t\u0000\u0000EF\u0005\u0007\u0000"+
		"\u0000FG\u0003\u0006\u0003\u0000GH\u0005\b\u0000\u0000HJ\u0001\u0000\u0000"+
		"\u0000I;\u0001\u0000\u0000\u0000I>\u0001\u0000\u0000\u0000IA\u0001\u0000"+
		"\u0000\u0000ID\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001"+
		"\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000L\u0005\u0001\u0000\u0000"+
		"\u0000MK\u0001\u0000\u0000\u0000NO\u0006\u0003\uffff\uffff\u0000Ok\u0003"+
		"\u0004\u0002\u0000PQ\u0003\u0004\u0002\u0000QR\u0005\u000f\u0000\u0000"+
		"RS\u0003\u0004\u0002\u0000Sk\u0001\u0000\u0000\u0000TU\u0003\u0004\u0002"+
		"\u0000UV\u0005\u0010\u0000\u0000VW\u0003\u0004\u0002\u0000Wk\u0001\u0000"+
		"\u0000\u0000XY\u0003\u0004\u0002\u0000YZ\u0005\u0011\u0000\u0000Z[\u0003"+
		"\u0004\u0002\u0000[k\u0001\u0000\u0000\u0000\\]\u0003\u0004\u0002\u0000"+
		"]^\u0005\u0012\u0000\u0000^_\u0003\u0004\u0002\u0000_k\u0001\u0000\u0000"+
		"\u0000`a\u0003\u0004\u0002\u0000ab\u0005\u000f\u0000\u0000bc\u0005%\u0000"+
		"\u0000ck\u0001\u0000\u0000\u0000de\u0005\u0003\u0000\u0000ef\u0003\u0006"+
		"\u0003\u0000fg\u0005\u0004\u0000\u0000gk\u0001\u0000\u0000\u0000hi\u0005"+
		"\u0015\u0000\u0000ik\u0003\u0006\u0003\u0001jN\u0001\u0000\u0000\u0000"+
		"jP\u0001\u0000\u0000\u0000jT\u0001\u0000\u0000\u0000jX\u0001\u0000\u0000"+
		"\u0000j\\\u0001\u0000\u0000\u0000j`\u0001\u0000\u0000\u0000jd\u0001\u0000"+
		"\u0000\u0000jh\u0001\u0000\u0000\u0000kt\u0001\u0000\u0000\u0000lm\n\u0003"+
		"\u0000\u0000mn\u0005\u0013\u0000\u0000ns\u0003\u0006\u0003\u0004op\n\u0002"+
		"\u0000\u0000pq\u0005\u0014\u0000\u0000qs\u0003\u0006\u0003\u0003rl\u0001"+
		"\u0000\u0000\u0000ro\u0001\u0000\u0000\u0000sv\u0001\u0000\u0000\u0000"+
		"tr\u0001\u0000\u0000\u0000tu\u0001\u0000\u0000\u0000u\u0007\u0001\u0000"+
		"\u0000\u0000vt\u0001\u0000\u0000\u0000w|\u0003\n\u0005\u0000xy\u0005\t"+
		"\u0000\u0000y{\u0003\n\u0005\u0000zx\u0001\u0000\u0000\u0000{~\u0001\u0000"+
		"\u0000\u0000|z\u0001\u0000\u0000\u0000|}\u0001\u0000\u0000\u0000}\t\u0001"+
		"\u0000\u0000\u0000~|\u0001\u0000\u0000\u0000\u007f\u0080\u0006\u0005\uffff"+
		"\uffff\u0000\u0080\u009e\u0005$\u0000\u0000\u0081\u009e\u0005%\u0000\u0000"+
		"\u0082\u009e\u0003\u0002\u0001\u0000\u0083\u0084\u0005\u0003\u0000\u0000"+
		"\u0084\u0085\u0003\b\u0004\u0000\u0085\u0086\u0005\u0004\u0000\u0000\u0086"+
		"\u009e\u0001\u0000\u0000\u0000\u0087\u0088\u0005\u0016\u0000\u0000\u0088"+
		"\u0089\u0003\u0016\u000b\u0000\u0089\u008a\u0005\u0017\u0000\u0000\u008a"+
		"\u008b\u0005\u0018\u0000\u0000\u008b\u008c\u0003\b\u0004\u0000\u008c\u008d"+
		"\u0005\u0019\u0000\u0000\u008d\u008e\u0005\u001a\u0000\u0000\u008e\u008f"+
		"\u0003\u0016\u000b\u0000\u008f\u0090\u0005\u0017\u0000\u0000\u0090\u009e"+
		"\u0001\u0000\u0000\u0000\u0091\u0093\u0003\f\u0006\u0000\u0092\u0094\u0003"+
		"\u000e\u0007\u0000\u0093\u0092\u0001\u0000\u0000\u0000\u0093\u0094\u0001"+
		"\u0000\u0000\u0000\u0094\u0096\u0001\u0000\u0000\u0000\u0095\u0097\u0003"+
		"\u0010\b\u0000\u0096\u0095\u0001\u0000\u0000\u0000\u0096\u0097\u0001\u0000"+
		"\u0000\u0000\u0097\u0098\u0001\u0000\u0000\u0000\u0098\u0099\u0003\u0012"+
		"\t\u0000\u0099\u009e\u0001\u0000\u0000\u0000\u009a\u009b\u0003\u000e\u0007"+
		"\u0000\u009b\u009c\u0003\n\u0005\u0001\u009c\u009e\u0001\u0000\u0000\u0000"+
		"\u009d\u007f\u0001\u0000\u0000\u0000\u009d\u0081\u0001\u0000\u0000\u0000"+
		"\u009d\u0082\u0001\u0000\u0000\u0000\u009d\u0083\u0001\u0000\u0000\u0000"+
		"\u009d\u0087\u0001\u0000\u0000\u0000\u009d\u0091\u0001\u0000\u0000\u0000"+
		"\u009d\u009a\u0001\u0000\u0000\u0000\u009e\u00a7\u0001\u0000\u0000\u0000"+
		"\u009f\u00a0\n\t\u0000\u0000\u00a0\u00a1\u0005\u0005\u0000\u0000\u00a1"+
		"\u00a6\u0003\u0004\u0002\u0000\u00a2\u00a3\n\b\u0000\u0000\u00a3\u00a4"+
		"\u0005\u0006\u0000\u0000\u00a4\u00a6\u0003\u0004\u0002\u0000\u00a5\u009f"+
		"\u0001\u0000\u0000\u0000\u00a5\u00a2\u0001\u0000\u0000\u0000\u00a6\u00a9"+
		"\u0001\u0000\u0000\u0000\u00a7\u00a5\u0001\u0000\u0000\u0000\u00a7\u00a8"+
		"\u0001\u0000\u0000\u0000\u00a8\u000b\u0001\u0000\u0000\u0000\u00a9\u00a7"+
		"\u0001\u0000\u0000\u0000\u00aa\u00ab\u0005\u001b\u0000\u0000\u00ab\u00ac"+
		"\u0005$\u0000\u0000\u00ac\u00ad\u0005\u001c\u0000\u0000\u00ad\u00b4\u0003"+
		"\n\u0005\u0000\u00ae\u00af\u0005\t\u0000\u0000\u00af\u00b0\u0005$\u0000"+
		"\u0000\u00b0\u00b1\u0005\u001c\u0000\u0000\u00b1\u00b3\u0003\n\u0005\u0000"+
		"\u00b2\u00ae\u0001\u0000\u0000\u0000\u00b3\u00b6\u0001\u0000\u0000\u0000"+
		"\u00b4\u00b2\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001\u0000\u0000\u0000"+
		"\u00b5\r\u0001\u0000\u0000\u0000\u00b6\u00b4\u0001\u0000\u0000\u0000\u00b7"+
		"\u00b8\u0005\u001d\u0000\u0000\u00b8\u00b9\u0005$\u0000\u0000\u00b9\u00ba"+
		"\u0005\u001e\u0000\u0000\u00ba\u00c1\u0003\n\u0005\u0000\u00bb\u00bc\u0005"+
		"\t\u0000\u0000\u00bc\u00bd\u0005$\u0000\u0000\u00bd\u00be\u0005\u001e"+
		"\u0000\u0000\u00be\u00c0\u0003\n\u0005\u0000\u00bf\u00bb\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c3\u0001\u0000\u0000\u0000\u00c1\u00bf\u0001\u0000\u0000"+
		"\u0000\u00c1\u00c2\u0001\u0000\u0000\u0000\u00c2\u000f\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c4\u00c5\u0005\u001f\u0000"+
		"\u0000\u00c5\u00c6\u0003\u0014\n\u0000\u00c6\u0011\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c8\u0005 \u0000\u0000\u00c8\u00c9\u0003\b\u0004\u0000\u00c9"+
		"\u0013\u0001\u0000\u0000\u0000\u00ca\u00cb\u0006\n\uffff\uffff\u0000\u00cb"+
		"\u00cc\u0005!\u0000\u0000\u00cc\u00cd\u0005\u0003\u0000\u0000\u00cd\u00ce"+
		"\u0003\b\u0004\u0000\u00ce\u00cf\u0005\u0004\u0000\u0000\u00cf\u00f7\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d1\u0005\"\u0000\u0000\u00d1\u00d2\u0005$"+
		"\u0000\u0000\u00d2\u00d3\u0005\u001c\u0000\u0000\u00d3\u00da\u0003\n\u0005"+
		"\u0000\u00d4\u00d5\u0005\t\u0000\u0000\u00d5\u00d6\u0005$\u0000\u0000"+
		"\u00d6\u00d7\u0005\u001c\u0000\u0000\u00d7\u00d9\u0003\n\u0005\u0000\u00d8"+
		"\u00d4\u0001\u0000\u0000\u0000\u00d9\u00dc\u0001\u0000\u0000\u0000\u00da"+
		"\u00d8\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000\u00db"+
		"\u00dd\u0001\u0000\u0000\u0000\u00dc\u00da\u0001\u0000\u0000\u0000\u00dd"+
		"\u00de\u0005#\u0000\u0000\u00de\u00df\u0003\u0014\n\t\u00df\u00f7\u0001"+
		"\u0000\u0000\u0000\u00e0\u00e1\u0003\n\u0005\u0000\u00e1\u00e2\u0005\u000f"+
		"\u0000\u0000\u00e2\u00e3\u0003\n\u0005\u0000\u00e3\u00f7\u0001\u0000\u0000"+
		"\u0000\u00e4\u00e5\u0003\n\u0005\u0000\u00e5\u00e6\u0005\u0010\u0000\u0000"+
		"\u00e6\u00e7\u0003\n\u0005\u0000\u00e7\u00f7\u0001\u0000\u0000\u0000\u00e8"+
		"\u00e9\u0003\n\u0005\u0000\u00e9\u00ea\u0005\u0011\u0000\u0000\u00ea\u00eb"+
		"\u0003\n\u0005\u0000\u00eb\u00f7\u0001\u0000\u0000\u0000\u00ec\u00ed\u0003"+
		"\n\u0005\u0000\u00ed\u00ee\u0005\u0012\u0000\u0000\u00ee\u00ef\u0003\n"+
		"\u0005\u0000\u00ef\u00f7\u0001\u0000\u0000\u0000\u00f0\u00f1\u0005\u0003"+
		"\u0000\u0000\u00f1\u00f2\u0003\u0014\n\u0000\u00f2\u00f3\u0005\u0004\u0000"+
		"\u0000\u00f3\u00f7\u0001\u0000\u0000\u0000\u00f4\u00f5\u0005\u0015\u0000"+
		"\u0000\u00f5\u00f7\u0003\u0014\n\u0001\u00f6\u00ca\u0001\u0000\u0000\u0000"+
		"\u00f6\u00d0\u0001\u0000\u0000\u0000\u00f6\u00e0\u0001\u0000\u0000\u0000"+
		"\u00f6\u00e4\u0001\u0000\u0000\u0000\u00f6\u00e8\u0001\u0000\u0000\u0000"+
		"\u00f6\u00ec\u0001\u0000\u0000\u0000\u00f6\u00f0\u0001\u0000\u0000\u0000"+
		"\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f7\u0100\u0001\u0000\u0000\u0000"+
		"\u00f8\u00f9\n\u0003\u0000\u0000\u00f9\u00fa\u0005\u0013\u0000\u0000\u00fa"+
		"\u00ff\u0003\u0014\n\u0004\u00fb\u00fc\n\u0002\u0000\u0000\u00fc\u00fd"+
		"\u0005\u0014\u0000\u0000\u00fd\u00ff\u0003\u0014\n\u0003\u00fe\u00f8\u0001"+
		"\u0000\u0000\u0000\u00fe\u00fb\u0001\u0000\u0000\u0000\u00ff\u0102\u0001"+
		"\u0000\u0000\u0000\u0100\u00fe\u0001\u0000\u0000\u0000\u0100\u0101\u0001"+
		"\u0000\u0000\u0000\u0101\u0015\u0001\u0000\u0000\u0000\u0102\u0100\u0001"+
		"\u0000\u0000\u0000\u0103\u0104\u0005&\u0000\u0000\u0104\u0017\u0001\u0000"+
		"\u0000\u0000\u0105\u0106\u0005&\u0000\u0000\u0106\u0019\u0001\u0000\u0000"+
		"\u0000\u0013)9IKjrt|\u0093\u0096\u009d\u00a5\u00a7\u00b4\u00c1\u00da\u00f6"+
		"\u00fe\u0100";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}