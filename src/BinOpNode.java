// BinOp short for Binary Operations: holds binary operations (left,right), etc
public class BinOpNode extends ASTNode {
    public final Type op;
    public final ASTNode left;
    public final ASTNode right;

    public BinOpNode(Type op, ASTNode left, ASTNode right) {
    this.op = op;
    this.left = left;
    this.right = right;
}

    public String toString() {
        return "BinOp ( " + op + ", " + left + ", " + right + ")";
    }

}
