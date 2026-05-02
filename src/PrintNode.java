public class PrintNode extends ASTNode{
    public final ASTNode expression;

    public PrintNode(ASTNode expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Print(" + expression + ")";
    }
}
