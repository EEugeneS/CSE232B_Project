grammar XPath;

ap
    : 'doc' '(' StringConstant ')' '/' rp EOF
    | 'doc' '(' StringConstant ')' '//' rp EOF
    ;

rp
    : rp '/' rp
    | rp '//' rp
    | rp '[' f ']'
    | rp ',' rp
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
    | rp '=' rp
    | rp 'eq' rp
    | rp '==' rp
    | rp 'is' rp
    | rp '=' StringConstant
    | '(' f ')'
    | f 'and' f
    | f 'or' f
    | 'not' f
    ;

tagName
    : Name
    ;

attName
    : Name
    ;

StringConstant
    : '"' (~["])* '"'
    | '\'' (~['])* '\''
    ;

Name
    : [a-zA-Z_][a-zA-Z0-9_.-]*
    ;

WS
    : [ \t\r\n]+ -> skip
    ;
