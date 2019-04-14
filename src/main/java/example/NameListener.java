package example;

import com.ast.transformation.Java8BaseListener;
import com.ast.transformation.Java8Parser.VariableDeclaratorIdContext;
import org.antlr.v4.runtime.TokenStreamRewriter;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;

public class NameListener extends Java8BaseListener {
    private TokenStreamRewriter rewriter;

    public NameListener(TokenStreamRewriter tokenStreamRewriter) {
        rewriter = tokenStreamRewriter;
    }

    @Override
    public void exitVariableDeclaratorId(VariableDeclaratorIdContext ctx) {
        if (ctx.getText().contains("_")) {
            rewriter.replace(ctx.start, ctx.stop, convert(ctx.getText()));
        }
    }

    private String convert(String text) {
        String[] split = text.split("_");
        return split[0] + Arrays.stream(split, 1, split.length)
                .map(String::toLowerCase)
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                .collect(joining());
    }
}
