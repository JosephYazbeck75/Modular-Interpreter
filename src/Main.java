import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String source = "a = 10 add 5; b = a min 3;";
        Lexer lexerForTokens = new Lexer(source);
        List<Token> tokens = lexerForTokens.tokenize();

        System.out.println("Tokens:");
        for (Token token : tokens) {
            System.out.println("  " + token);
        }

        Parser parser = new Parser(new Lexer(source));
        ProgramNode program = parser.parseProgram();

        System.out.println("\nAST:");
        System.out.print(ASTPrinter.treeToString(program));

        System.out.println("\nEvaluation:");
        Evaluator evaluator = new Evaluator();
        Map<String, Double> result = evaluator.evaluate(program);

        System.out.println("\nFinal values: " + result);
    }
}