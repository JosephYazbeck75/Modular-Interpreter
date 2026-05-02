public class ASTPrinter {
    public static String treeToString(ASTNode node) {
        StringBuilder sb = new StringBuilder();
        nodeToString(node, 0, sb);
        return sb.toString();
    }

    private static void nodeToString(ASTNode node, int indent, StringBuilder sb) {
        String indentation = getIndentation(indent);
        if (node instanceof ProgramNode) {
            ProgramNode program = (ProgramNode) node;
            sb.append(indentation).append("Program\n");
            for (ASTNode statement : program.statements) {
                nodeToString(statement, indent + 1, sb);
            }
        } else if (node instanceof AssignNode) {
            AssignNode assign = (AssignNode) node;
            sb.append(indentation).append("Assign: ").append(assign.name).append("\n");
            nodeToString(assign.value, indent + 1, sb);
        } else if (node instanceof BinOpNode) {
            BinOpNode bin = (BinOpNode) node;
            sb.append(indentation).append("BinOp: ").append(bin.op).append("\n");
            sb.append(indentation).append("  Left:\n");
            nodeToString(bin.left, indent + 2, sb);
            sb.append(indentation).append("  Right:\n");
            nodeToString(bin.right, indent + 2, sb);
        } else if (node instanceof NumberNode) {
            NumberNode number = (NumberNode) node;
            sb.append(indentation).append("Number: ").append(number.value).append("\n");
        } else if (node instanceof VarNode) {
            VarNode var = (VarNode) node;
            sb.append(indentation).append("Var: ").append(var.name).append("\n");
        }
        else if (node instanceof PrintNode){
            PrintNode print = (PrintNode) node;
            sb.append(indentation).append("Print\n");
            nodeToString(print.expression, indent+ 1, sb);

        } else {
            sb.append(indentation).append(node.getClass().getSimpleName()).append("\n");
        }
    }

    private static String getIndentation(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}