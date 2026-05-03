public  class WhileNode extends ASTNode {
    public ASTNode condition;
    public final ASTNode body;
    public WhileNode (ASTNode condition, ASTNode body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
return "While (" + condition + ", " + body + ")";
    }
}
