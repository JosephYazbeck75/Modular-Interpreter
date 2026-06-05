
public class VarNode extends ASTNode {
        public final String name;

    public VarNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Var(" + name + ")";
    }

}
