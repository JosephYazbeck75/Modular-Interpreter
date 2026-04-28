// Node for Numbers (i.e 1,5,3.14,etc)
public class NumberNode extends ASTNode {
    public final double value;

    public NumberNode(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Number(" + value + ")";
    }
}
