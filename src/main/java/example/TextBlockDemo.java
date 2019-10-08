package example;

import com.ast.transformation.Java8Lexer;
import com.ast.transformation.Java8Parser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TextBlockDemo {
    public static void main(String[] args) throws IOException {
        String sourceCode = Files.readString(Paths.get("transform/TextBlockSource.java"));

        CharStream charStream = new ANTLRInputStream(sourceCode);
        Java8Lexer lexer = new Java8Lexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokenStream);
        parser.addErrorListener(new ErrorListener());
        TokenStreamRewriter rewriter = new TokenStreamRewriter(tokenStream);
        parser.addParseListener(new TextBlockListener(rewriter));

        parser.compilationUnit();
        String newSourceCode = rewriter.getText();
        System.out.println(rewriter.getText());
        Files.writeString(Paths.get("transformed/TextBlockSource.java"), newSourceCode);
    }
}
