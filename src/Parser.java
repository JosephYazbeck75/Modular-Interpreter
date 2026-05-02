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
                "Unrecognized Token!"
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

    private ASTNode parseStatement() {
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
