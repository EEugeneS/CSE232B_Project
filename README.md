# CSE232B_Project

This project runs XPath/XQuery queries on an input XML file and writes the
result to an output XML file.

## Run

The compiled `.class` files are already included in `bin`, so the project can be
run directly from the project root:

```bash
java -cp "lib/antlr-4.13.2-complete.jar:bin" CSE232B_Project.main.Main input.xml query.txt output.xml
```

Arguments:

```text
input.xml    input XML file
query.txt    input XPath/XQuery file
output.xml   output XML file
```

## Recompile

If the Java source files or ANTLR generated files are changed, recompile before
running:

```bash
mkdir -p bin
javac -cp "lib/antlr-4.13.2-complete.jar" -d bin main/antlr/*.java main/Main.java
```
