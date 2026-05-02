public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("a = 10 add 5; b = a min 3;");
        Parser parser = new Parser(lexer);
        ProgramNode program = parser.parseProgram();

        // parse program and evaluate runtime values
        Evaluator evaluator = new Evaluator();
        System.out.println(evaluator.evaluate(program));
    }
}