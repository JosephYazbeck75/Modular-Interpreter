import java.util.LinkedHashMap;
import java.util.Map;

public class Evaluator {
    private final Map<String, Double> variables = new LinkedHashMap<>();

    public Map<String, Double> evaluate(ProgramNode program) {
        for (ASTNode statement : program.statements) {
            evaluateNode(statement);
        }
        return variables;
    }

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
        if (node instanceof PrintNode) {
            PrintNode print = (PrintNode) node;
            double value = evaluateNode(print.expression);
            String formatted = (value == Math.floor(value) && !Double.isInfinite(value))
            ? String.valueOf((int)(double)value)
            : String.valueOf(value);
            System.out.println("==> "+ formatted);
            return value;
        }

        if (node instanceof AssignNode) {
            AssignNode assign = (AssignNode) node;
            double value = evaluateNode(assign.value);
            variables.put(assign.name, value);
            System.out.println("Assigned " + assign.name + " = " + value);
            return value;
        }

        if (node instanceof IfNode) {
            IfNode ifNode = (IfNode) node;
            if (evaluateCondition(ifNode.condition)) {
                return evaluateNode(ifNode.thenBranch);
            }
            else if (ifNode.elseBranch != null) {
                return evaluateNode(ifNode.elseBranch);
            }
            return 0;
        }
        if (node instanceof WhileNode) {
            WhileNode whileNode = (WhileNode) node;
            int limit = 10000;
            int count = 0;
            while(evaluateCondition(whileNode.condition)) {
                if (++count > limit) {
                    throw new RuntimeException("Infinite loop detected - went past " + limit + " repetitions");
                }
                evaluateNode(whileNode.body);
            }
            return 0;
        }
        if (node instanceof ForNode) {
            ForNode forNode = (ForNode) node;
            double start = evaluateNode(forNode.start);
            double end = evaluateNode(forNode.end);
            int limit = 999999;
            int count = 0;

            variables.put(forNode.var,start);

            while (variables.get(forNode.var) <= end) {
                if (++count > limit) {
                    throw new RuntimeException("Infinite loop detected - exceeded " + limit + " iterations");
                }
                evaluateNode(forNode.body);
                variables.put(forNode.var, variables.get(forNode.var) + 1);
            }
            return 0;
        }

        throw new RuntimeException("Unsupported AST node: " + node.getClass().getSimpleName());
    }

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
    private boolean evaluateCondition(ASTNode node) {
        if (node instanceof CompareNode) {
            CompareNode cmp = (CompareNode) node;
            double left = evaluateNode(cmp.left);
            double right = evaluateNode(cmp.right);
            switch(cmp.op) {
                case GT: return left > right;
                case LT: return left < right;
                case EQ: return left == right;
                case NEQ: return left != right;
                case GTE: return left >= right;
                case LTE: return left <= right;
                default: throw new RuntimeException("Unknown comparison: " + cmp.op);
            }
        }
        return evaluateNode(node) != 0;
    }
}
