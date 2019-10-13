package example;

import com.ast.transformation.Java8BaseListener;
import com.ast.transformation.Java8Parser;
import com.ast.transformation.Java8Parser.VariableDeclaratorIdContext;
import org.antlr.v4.runtime.TokenStreamRewriter;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class TextBlockListener extends Java8BaseListener {
    private TokenStreamRewriter rewriter;
    private String str;
    private boolean canConvertToTextBlock;

    public TextBlockListener(TokenStreamRewriter tokenStreamRewriter) {
        rewriter = tokenStreamRewriter;
    }

    @Override
    public void exitLiteral(Java8Parser.LiteralContext ctx) {
        if (canConvertToTextBlock && ctx.StringLiteral() != null) {
            String text = ctx.getText().substring(1, ctx.getText().length() - 1);
            if (text.endsWith("\\n")) {
                text = text.substring(0, text.length() - 2);
            }
            str += text + "\n";
        } else {
            canConvertToTextBlock = false;
        }
    }

    @Override
    public void enterVariableInitializer(Java8Parser.VariableInitializerContext ctx) {
        str = "\"\"\"\n";
        canConvertToTextBlock = true;
    }

    @Override
    public void exitVariableInitializer(Java8Parser.VariableInitializerContext ctx) {
        if (canConvertToTextBlock) {
            String initText = str.substring(0, str.length() - 1) + "\"\"\"";
            rewriter.replace(ctx.start, ctx.stop, initText);
        }
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (canConvertToTextBlock
                && !(isStringToken(node.getText()) || isPlusOperator(node.getText()))) {
            canConvertToTextBlock = false;
        }
    }

    private boolean isPlusOperator(String text) {
        return "+".equals(text);
    }

    private boolean isStringToken(String text) {
        return text.startsWith("\"") && text.endsWith("\"");
    }
}
