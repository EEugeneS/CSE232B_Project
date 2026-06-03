grammar XQuery;

@header {
package main.antlr;
}

// Top-level entry: an XQuery expression terminated by EOF.
xqMain
    : xq EOF
    ;

// =============================================================
// XPath sub-language (Milestone 1)
// =============================================================

ap
    : ('doc' | 'document') '(' StringConstant ')' '/'  rp
    | ('doc' | 'document') '(' StringConstant ')' '//' rp
    ;

rp
    : rp '/'  rp
    | rp '//' rp
    | rp '['  f ']'
    | rp ','  rp
    | tagName
    | '*'
    | '.'
    | '..'
    | 'text' '(' ')'
    | '@' attName
    | '(' rp ')'
    ;

f
    : rp
    | rp '='  rp
    | rp 'eq' rp
    | rp '==' rp
    | rp 'is' rp
    | rp '='  StringConstant
    | '(' f ')'
    | f 'and' f
    | f 'or'  f
    | 'not' f
    ;

// =============================================================
// XQuery sub-language (Milestone 2)
// =============================================================

// An XQuery expression. The top-level comma operator is the loosest
// binding, so we factor it out from xqValue. xq is what appears in
// places where comma-sequences are allowed (parens, element bodies,
// return clauses).
xq
    : xqValue (',' xqValue)*
    ;

// An XQuery expression without a top-level comma operator. Used for
// binding values (after `in` or `:=`) so that the comma separating
// bindings is not greedily absorbed.
xqValue
    : 'join' '(' xqValue ',' xqValue ',' attrList ',' attrList ')'
    | xqValue '/'  rp
    | xqValue '//' rp
    | Var
    | StringConstant
    | ap
    | '(' xq ')'
    | '<' tagName '>' '{' xq '}' '</' tagName '>'
    | forClause letClause? whereClause? returnClause
    | letClause xqValue
    ;

forClause
    : 'for' Var 'in' xqValue (',' Var 'in' xqValue)*
    ;

letClause
    : 'let' Var ':=' xqValue (',' Var ':=' xqValue)*
    ;

whereClause
    : 'where' cond
    ;

returnClause
    : 'return' xq
    ;

cond
    : 'empty' '(' xq ')'
    | 'some' Var 'in' xqValue (',' Var 'in' xqValue)* 'satisfies' cond
    | xqValue '='  xqValue
    | xqValue 'eq' xqValue
    | xqValue '==' xqValue
    | xqValue 'is' xqValue
    | '(' cond ')'
    | cond 'and' cond
    | cond 'or'  cond
    | 'not' cond
    ;

attrList
    : '[' Name (',' Name)* ']'
    ;

// =============================================================
// Lexer
// =============================================================

tagName : Name ;
attName : Name ;

Var
    : '$' [a-zA-Z_][a-zA-Z0-9_.-]*
    ;

StringConstant
    : '"' (~["])* '"'
    | '\'' (~['])* '\''
    ;

Name
    : [a-zA-Z_][a-zA-Z0-9_.-]*
    ;

WS  : [ \t\r\n]+ -> skip ;
