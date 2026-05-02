public class Token {
    public Type type;
    public String value;
    public int line;
    public int col;


    public Token(Type type, String value, int line, int col) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.col = col;
    }

    @Override
    public String toString() {
        return type + "(" + value + ") [" + line + ":" + col + "]";
    }
}