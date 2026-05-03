// parser
import java.util.ArrayList;
import java.util.List;
public class Parser {
    private final Lexer lexer;
    private Token curr;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.curr = lexer.getNextToken();
    }
    private Token consume(Type expected) {
        if (curr.type != expected) {
            throw new RuntimeException(
                "Expected " + expected + "but got" + curr.type + "(" + curr.value + ")"
            );
        }
    Token eaten = curr;
    curr = lexer.getNextToken();
    return eaten;
    }
    private boolean isOperator(Type type) {
        return type == Type.ADD  ||
               type == Type.MIN  ||
               type == Type.MULT ||
               type == Type.DIV;
    }
    private boolean isReservedWord(Type type) {
    return type == Type.ADD  || type == Type.MIN  ||
           type == Type.MULT || type == Type.DIV  ||
           type == Type.GT   || type == Type.LT   ||
           type == Type.EQ   || type == Type.NEQ  ||
           type == Type.GTE  || type == Type.WHILE||
           type == Type.DO   || type == Type.FOR  ||
           type == Type.TO   || type == Type.LTE;
}
    private ASTNode parsePrimary() {
        Token token = curr;

        if (token.type == Type.NUMBER) {
            consume(Type.NUMBER);
            return new NumberNode(Double.parseDouble(token.value));
        }

        if (token.type == Type.VAR) {
            consume(Type.VAR);
            return new VarNode(token.value);
        }

        if (token.type == Type.LEFTPAR) {
            consume(Type.LEFTPAR);
            ASTNode node = parseExpr();
            consume(Type.RIGHTPAR);
            return node;
        }

        throw new RuntimeException("Unexpected token in expression: " + token);
    }
    private ASTNode parseExprFromNode(ASTNode left) {
        while (isOperator(curr.type)) {
            Type op = curr.type;
            consume(op);
            ASTNode right = parsePrimary();
            left = new BinOpNode(op, left, right);
        }
        return left;
    }

    private ASTNode parseExpr() {
        ASTNode left = parsePrimary();
        return parseExprFromNode(left);
    
    }
    private ASTNode parseCondition() {
        ASTNode left = parsePrimary();
        if (IsComparison(curr.type)) {
            Type op = curr.type;
            consume(op);
            ASTNode right = parsePrimary();
            return new CompareNode(left,op,right);
        }
        return left;
    }

    private ASTNode parseStatement() {
    if (curr.type == Type.PRINT) {
        consume(Type.PRINT);
        ASTNode expr = parseExpr();
        return new PrintNode(expr);
    }
            if (curr.type == Type.IF) {
            consume(Type.IF);
            ASTNode condition = parseCondition();
            consume(Type.THEN);
            ASTNode thenBranch = parseStatement();
            ASTNode elseBranch = null;
            if (curr.type == Type.ELSE) {
                consume(Type.ELSE);
                elseBranch = parseStatement();
            }
            return new IfNode(condition, thenBranch, elseBranch);
        }
            if (curr.type == Type.WHILE) {
                consume(Type.WHILE);
                ASTNode condition = parseCondition();
                consume(Type.DO);
                ASTNode body = parseStatement();
                return new WhileNode(condition,body);
            }
            if (curr.type == Type.FOR) {
                consume(Type.FOR);
                String varName = curr.value;
                consume(Type.VAR);
                consume(Type.ASSIGN);
                ASTNode start = parsePrimary();
                consume(Type.TO);
                ASTNode end = parsePrimary();
                consume(Type.DO);
                ASTNode body = parseStatement();
                return new ForNode(varName,start,end,body);
            }
        if (isReservedWord(curr.type)) {
            throw new RuntimeException("Error: " + curr.value + "is a system symbol");
        }

        if (curr.type == Type.VAR) {
            String name = curr.value;
            consume(Type.VAR);

            if (curr.type == Type.ASSIGN) {
                consume(Type.ASSIGN);
                ASTNode value = parseExpr();
                return new AssignNode(name, value);
            }

            return parseExprFromNode(new VarNode(name));
        }
        return parseExpr();
    }
    private boolean IsComparison(Type type) {
        return type == type.GT || type == type.LT || type == type.EQ || type == Type.NEQ || type == Type.GTE || type == Type.LTE;
    }

    public ProgramNode parseProgram() {
        List<ASTNode> statements = new ArrayList<>();
        while (curr.type != Type.END) {
            statements.add(parseStatement());
            if(curr.type == Type.SEMICOLON) {
                consume(Type.SEMICOLON);
            }
        }
        return new ProgramNode(statements);
    }
}
