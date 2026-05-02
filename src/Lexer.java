public class Lexer {
    private String text;
    public int pos;
    private char curr;

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.curr = text.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos < text.length()) {
            curr = text.charAt(pos);
        }
        else {
            curr = '\0';
        }
    }

    private void skipWhitespace() {
        while (curr !=  '\0' &&  Character.isWhitespace(curr)) {
            advance();
        }
    }

    private Token number() {
        StringBuilder result = new StringBuilder();
        while (curr != '\0' &&  Character.isDigit(curr) || curr == '.') {
            result.append(curr);
            advance();
        }
        return new Token(Type.NUMBER, result.toString());
    }

    private Token identifier() {
        StringBuilder result  = new StringBuilder();
        while (curr != '\0' &&  Character.isLetterOrDigit(curr)) {
            result.append(curr);
            advance();
        }
        String word = result.toString().toLowerCase();
        if (word.isEmpty()) {
            throw new RuntimeException("Empty identifier detected");
        }
        
        switch(word) {
            case "add":
                return new Token(Type.ADD, word);
            case "min":
                return new Token(Type.MIN, word);
            case "mult":
                return new Token(Type.MULT, word);
            case "div":
                return new Token(Type.DIV,word);
            default:
                return new Token(Type.VAR, word);
        }
    }

    public Token getNextToken() {
        System.out.println("Current char: [ " + curr + " ]");
        while (curr != '\0') {
            if (Character.isWhitespace(curr)) {
                skipWhitespace();
                continue;
            }
            if (Character.isDigit(curr)) {
                return number();
            }
            if (Character.isLetter(curr)) {
                return identifier();
            }
            if (curr == '=') {
                advance();
                return new Token(Type.ASSIGN, "=");
            }
                    if (curr == ';') {
                advance();
                return new Token(Type.SEMICOLON, ";");
            }
            if (curr == '(') {
                advance();
                return new Token(Type.LEFTPAR, "(");
            }
            if (curr == ')') {
                advance();
                return new Token(Type.RIGHTPAR, ")");
            }
            throw new RuntimeException("Unknown character: " + curr);
        }
        return new Token(Type.END, null);
    }
}
