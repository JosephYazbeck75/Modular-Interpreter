public class Main {
    public static void Main(String[] args) {
Lexer lexer = new Lexer("a = 10 ADD 5");

Token token;
    
    do {
        token = lexer.getNextToken();
System.out.println(token);
    } while (token.type != Type.END);
}
}
