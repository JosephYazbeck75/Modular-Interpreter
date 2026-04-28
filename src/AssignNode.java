// Node for Assgin (a =)
public class AssignNode extends ASTNode{
    public final String name;
    public final ASTNode value;

    public AssignNode(String name, ASTNode value) {
        this.name = name;
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "Assign (" + name + "," + value + ")";
    }
}
