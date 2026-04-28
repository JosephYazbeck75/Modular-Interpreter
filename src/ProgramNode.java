import java.util.List;

public class ProgramNode extends ASTNode {
    public final List<ASTNode> statements;

    public ProgramNode(List <ASTNode> statements) {
        this.statements = statements;
    }

    @Override
    public String toString() {
        return "Program ( " + statements + ")";
    }

}
