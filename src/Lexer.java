public class Lexer {
    private String text;
    public int pos;
    private char curr;

    public Lexer(String tex) {
        this.text = text;
        this.pos = 0;
        this.curr = text.charAt(pos);
    }
}
