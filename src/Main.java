public class Main {
    public static void main(String[] args) {
Lexer lexer = new Lexer("a = 10 add 5;");

Token token;
    
    do {
        token = lexer.getNextToken();
System.out.println(token);
    } while (token.type != Type.END);
}
}
