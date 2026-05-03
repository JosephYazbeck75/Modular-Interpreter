import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String text;
    public int pos;
    private char curr;
    private int line;
    private int col;

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.curr = text.isEmpty() ? '\0' : text.charAt(0);
        this.line = 1;
        this.col = 1;
    }

    private void advance() {
        if (curr == '\n') {
            line++;
            col = 1;
        } else {
            col++;
        }
        pos++;
        curr = pos < text.length() ? text.charAt(pos) : '\0';
    }

    private void skipWhitespace() {
        while (curr != '\0' && Character.isWhitespace(curr)) {
            advance();
        }
    }

    private Token number() {
        int startCol = col;
        int startLine = line;
        StringBuilder result = new StringBuilder();
        while (curr != '\0' && (Character.isDigit(curr) || curr == '.')) {
            result.append(curr);
            advance();
        }
        return new Token(Type.NUMBER, result.toString(), startLine, startCol);
    }

    private Token identifier() {
        int startCol = col;
        int startLine = line;
        StringBuilder result = new StringBuilder();
        while (curr != '\0' && Character.isLetterOrDigit(curr)) {
            result.append(curr);
            advance();
        }
        String word = result.toString().toLowerCase();
        if (word.isEmpty()) throw new RuntimeException("Empty identifier at line " + line + ":" + col);

        switch (word) {
            case "add":   return new Token(Type.ADD,  word, startLine, startCol);
            case "min":
            case "sub":   return new Token(Type.MIN,  word, startLine, startCol);
            case "mult":  return new Token(Type.MULT, word, startLine, startCol);
            case "div":   return new Token(Type.DIV,  word, startLine, startCol);
            case "print": return new Token(Type.PRINT, word, startLine, startCol);
            case "if":  return new Token(Type.IF, word, startLine, startCol);
            case "then": return new Token(Type.THEN, word, startLine, startCol);
            case "else": return new Token(Type.ELSE, word, startLine, startCol);
            case "gt": return new Token(Type.GT, word, startLine, startCol);
            case "lt": return new Token(Type.LT, word, startLine, startCol);
            case "neq": return new Token(Type.NEQ, word, startLine, startCol);
            case "eq": return new Token(Type.EQ, word, startLine, startCol);
            case "gte": return new Token(Type.GTE, word, startLine, startCol);
            case "lte": return new Token(Type.LTE, word, startLine, startCol);
            default:      return new Token(Type.VAR,  word, startLine, startCol);
        }
    }

    public Token getNextToken() {
        while (curr != '\0') {
            if (Character.isWhitespace(curr)) { skipWhitespace(); continue; }

            int startLine = line;
            int startCol  = col;

            if (Character.isDigit(curr))  return number();
            if (Character.isLetter(curr)) return identifier();

            if (curr == '=') { advance(); return new Token(Type.ASSIGN,    "=", startLine, startCol); }
            if (curr == ';') { advance(); return new Token(Type.SEMICOLON, ";", startLine, startCol); }
            if (curr == '(') { advance(); return new Token(Type.LEFTPAR,   "(", startLine, startCol); }
            if (curr == ')') { advance(); return new Token(Type.RIGHTPAR,  ")", startLine, startCol); }

            throw new RuntimeException("Unknown character '" + curr + "' at line " + line + ":" + col);
        }
        return new Token(Type.END, null, line, col);
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Token token;
        do {
            token = getNextToken();
            tokens.add(token);
        } while (token.type != Type.END);
        return tokens;
    }
}