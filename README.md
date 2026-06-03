# CSE232B_Project

This project runs XPath/XQuery queries on an input XML file and writes the
result to an output XML file.

## Run

Recompile after changing the Java source or ANTLR grammar, then run from the
project root:

```bash
java -cp "lib/antlr-4.13.2-complete.jar:bin" main.Main input.xml query.txt rewrite.xq output.xml
```

Arguments:

```text
input.xml     input XML file
query.txt     input XPath/XQuery file
rewrite.xq    rewritten join query output file
output.xml    XML result output file
```

## Recompile

If the Java source files or ANTLR generated files are changed, recompile before
running:

```bash
mkdir -p bin
javac -cp "lib/antlr-4.13.2-complete.jar" -d bin main/antlr/*.java main/Main.java
```
