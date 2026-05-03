public class IfNode extends ASTNode {
    public final ASTNode condition;
    public final ASTNode thenBranch;
    public final ASTNode elseBranch;

    public IfNode (ASTNode condition, ASTNode thenBranch, ASTNode elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    @Override
    public String toString() {
        return "If ( " + condition + " " + thenBranch + (elseBranch !=null ? ", " + elseBranch : "") + ")";
    }
}