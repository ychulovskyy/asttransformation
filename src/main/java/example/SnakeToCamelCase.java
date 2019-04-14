package example;

import com.ast.transformation.Java8Lexer;
import com.ast.transformation.Java8Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

public class SnakeToCamelCase {
    public static void main(String[] args) {
        String sourceCode = "class A { int snake_variable_name = 1; }";

        CharStream charStream = new ANTLRInputStream(sourceCode);
        Java8Lexer lexer = new Java8Lexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokenStream);
        parser.addErrorListener(new ErrorListener());
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokenStream);
        parser.addParseListener(new NameListener(rewriter));

        parser.compilationUnit();
        System.out.println(rewriter.getText());
    }
}
