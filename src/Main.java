import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;

public class Main {
public static void main(String[] args) throws Exception {
    if (args.length > 0) {
        String path = args[0];
        String source = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(path)));
        runSource(source);
    } else {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Modular Interpreter ===");
        System.out.println("Type 'exit' to quit, 'history' to see past programs.\n");

        List<String> history = new ArrayList<>();

        while (true) {
            System.out.print(">> ");
            String source = sc.nextLine().trim();

            if (source.equals("exit")) break;
            if (source.isEmpty()) continue;

            if (source.equals("history")) {
                if (history.isEmpty()) {
                    System.out.println("No history yet.");
                } else {
                    for (int i = 0; i < history.size(); i++) {
                        System.out.println("[" + (i + 1) + "] " + history.get(i));
                    }
                }
                continue;
            }

            if (source.startsWith("!")) {
                try {
                    int idx = Integer.parseInt(source.substring(1)) - 1;
                    if (idx >= 0 && idx < history.size()) {
                        source = history.get(idx);
                        System.out.println("Running: " + source);
                    } else {
                        System.out.println("No history entry " + (idx + 1));
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Usage: !<number> to re-run a history entry");
                    continue;
                }
            }

            history.add(source);
            runSource(source);
            System.out.println();
        }

        sc.close();
        System.out.println("Goodbye!");
    }
}

private static void runSource(String source) {
    try {
        // Tokens
        Lexer lexer = new Lexer(source);
        List<Token> tokens = lexer.tokenize();
        System.out.println("\nTokens:");
        for (Token token : tokens) {
            System.out.println("  " + token);
        }

        Parser parser = new Parser(new Lexer(source));
        ProgramNode program = parser.parseProgram();
        System.out.println("\nAST:");
        System.out.print(ASTPrinter.treeToString(program));

        Evaluator evaluator = new Evaluator();
        Map<String, Double> result = evaluator.evaluate(program);

        System.out.println("\nResults:");
        result.forEach((name, value) -> {
            String formatted = (value == Math.floor(value) && !Double.isInfinite(value))
                ? String.valueOf((int)(double) value)
                : String.valueOf(value);
            System.out.print(name + "=" + formatted + "    ");
        });
        System.out.println();

        } catch (RuntimeException e) {
          System.out.println("==> Error: " + e.getMessage());
        }
    }
}