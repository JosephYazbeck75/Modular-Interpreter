public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer("a = 10 add 5; b = a min 3;");
        Parser parser = new Parser(lexer);
        ProgramNode program = parser.parseProgram();
        System.out.println(program);
    }
}