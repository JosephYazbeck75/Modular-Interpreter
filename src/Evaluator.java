import java.util.LinkedHashMap;
import java.util.Map;

public class Evaluator {
    private final Map<String, Double> variables = new LinkedHashMap<>();

    // Evaluate the whole program and return the final variable map
    public Map<String, Double> evaluate(ProgramNode program) {
        for (ASTNode statement : program.statements) {
            evaluateNode(statement);
        }
        return variables;
    }

    // Evaluate a single AST node and return its numeric value
    private double evaluateNode(ASTNode node) {
        if (node instanceof NumberNode) {
            return ((NumberNode) node).value;
        }

        if (node instanceof VarNode) {
            String name = ((VarNode) node).name;
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined variable: " + name);
            }
            return variables.get(name);
        }

        if (node instanceof BinOpNode) {
            BinOpNode bin = (BinOpNode) node;
            double leftValue = evaluateNode(bin.left);
            double rightValue = evaluateNode(bin.right);
            return evaluateBinaryOperation(bin.op, leftValue, rightValue);
        }

        if (node instanceof AssignNode) {
            AssignNode assign = (AssignNode) node;
            double value = evaluateNode(assign.value);
            variables.put(assign.name, value);
            System.out.println("Assigned " + assign.name + " = " + value);
            return value;
        }

        throw new RuntimeException("Unsupported AST node: " + node.getClass().getSimpleName());
    }

    // Compute binary operation values
    private double evaluateBinaryOperation(Type op, double left, double right) {
        switch (op) {
            case ADD:
                return left + right;
            case MIN:
                return left - right;
            case MULT:
                return left * right;
            case DIV:
                if (right == 0) {
                    throw new RuntimeException("Division by zero");
                }
                return left / right;
            default:
                throw new RuntimeException("Unknown binary operator: " + op);
        }
    }
}
