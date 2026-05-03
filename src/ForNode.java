public class ForNode extends ASTNode {
    public final String var;
    public final ASTNode start;
    public final ASTNode end;
    public final ASTNode body;

     public ForNode(String var, ASTNode start, ASTNode end, ASTNode body) {
        this.var   = var;
        this.start = start;
        this.end   = end;
        this.body  = body;
    }

    @Override
    public String toString() {
        return "For(" + var + ", " + start + ", " + end + ", " + body + ")";
    }
}
