public class CompareNode extends ASTNode {
    public final ASTNode left;
    public final Type op;
    public final ASTNode right;

    public CompareNode(ASTNode left, Type op, ASTNode right) {
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Compare (" + left + " " + op + " " + right + ")";
    }
}