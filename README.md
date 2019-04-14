# AST transformation is easy
This is source code for "AST transformation is easy" presentation

### Build steps
Run gradle build task

### Structure
Java8.g8 describes Java 8 syntax.  
Gradle [ANTLR](https://www.antlr.org/) plugin has been used to build Lexer and Parser for the grammar.  
SnakeToCamelCase - entry point.  
NameListener contains logic to rename variables from snake to camel case. 
 