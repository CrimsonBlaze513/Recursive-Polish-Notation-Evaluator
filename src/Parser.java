import java.util.HashMap;

public class Parser {
    private Tokenizer tokenizer;
    private Token currentToken;
    private HashMap<String, Double> variables;
    private int tracker = 0;

    public Parser(Tokenizer tokenizer, HashMap<String, Double> variables) {
        this.tokenizer = tokenizer;
        // String with the first token
        this.currentToken = tokenizer.nextToken();
        this.variables = variables;
    }

    public double parse() {
        return parseExpr();
    }

    /*private double parseAssignments() {
        //Handle assignments like x = 5 + 2, or pass to expression parser
        if (currentToken.type == TokenType.IDENTIFIER) {
            String name = currentToken.text;
            int savedPos = tokenizer.getPos();
            eat(TokenType.IDENTIFIER);
            if (currentToken.type == TokenType.EQUALS) {
                eat(TokenType.EQUALS);
                double value = parseExpr();
                variables.put(name, value);
                return value;
            } else {
                tokenizer.setPos(savedPos);
                currentToken = new Token(TokenType.IDENTIFIER, name);
            }
        }
        return parseExpr();
    }*/

    // Consume a token if it matches what we expect
    private void eat(TokenType expected) {
        if (currentToken.type == expected) {
            // Advance to the next token
            currentToken = tokenizer.nextToken();
        } else {
            // Barked
            throw new RuntimeException("Expected " + expected + " but got " + currentToken.type);
        }
    }

    /*private double parseFactor() {
        if (currentToken.type == TokenType.MINUS) {
            eat(TokenType.MINUS);
            return -parseFactor();
        }

        if (currentToken.type == TokenType.NUMBER) {
            // If the token was a number, convert it to an int
            double value = Double.parseDouble(currentToken.text);
            eat(TokenType.NUMBER);
            return value;
        } else if (currentToken.type == TokenType.LPAREN) {
            // If it is a '(' consume ot, parse the expression inside, end consume the closing ')'
            eat(TokenType.LPAREN);
            double value = parseExpr();
            eat(TokenType.RPAREN);
            return value;
        } else if (currentToken.type == TokenType.IDENTIFIER) {
            String name = currentToken.text;
            eat(TokenType.IDENTIFIER);
            // If we don't have the variable being referenced
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined Variable: " + name);
            }
            // If we do have the variable being referenced
            return variables.get(name);
        } else {
            // Oy vey
            throw new RuntimeException("Unexpected token: " + currentToken.type);
        }
    }*/

    /*private double parseTerm() {
        // Parse the first factor
        double result = parsePower();

        // While the next token is * or /, continue parsing additional factors
        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            // Store the operator
            TokenType op = currentToken.type;
            // Consume the operator
            eat(op);
            // Parse the next factor
            double rhs = parsePower();

            // Apply the operator to the running result
            if (op == TokenType.MUL)
                result *= rhs;
            else
                result /= rhs;
        }
        return result;
    }*/

    public double parseExpr() {
        if (tracker != 2 && (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS
                || currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV)) {
            // Get the operator
            TokenType op = currentToken.type;
            eat(op); // consume the operator
            tracker++;
            double lhs = parseExpr(); // parse first operand
            double rhs = parseExpr(); // parse second operand
            return apply(op, lhs, rhs); // apply the operator
        } else if ((currentToken.type == TokenType.NUMBER) || (currentToken.type == TokenType.MINUS)) {

            if (currentToken.type == TokenType.MINUS) {
                eat(TokenType.MINUS);

                if (currentToken.type == TokenType.NUMBER) {
                    double value = -Double.parseDouble(currentToken.text);
                    eat(TokenType.NUMBER);
                    return value;
                } else {
                    throw new RuntimeException("Expected number after unary minus");
                }
            } else if (currentToken.type == TokenType.NUMBER) {
                double value = Double.parseDouble(currentToken.text);
                eat(TokenType.NUMBER);
                return value;
            } else {
                throw new RuntimeException("Expected number after unary minus");
            }

        } else if (currentToken.type == TokenType.IDENTIFIER) {
            String name = currentToken.text;
            eat(TokenType.IDENTIFIER);
            if (!variables.containsKey(name)) {
                throw new RuntimeException("Undefined variable: " + name);
            }
            return variables.get(name);
        } else {
            throw new RuntimeException("Unexpected token: " + currentToken);
        }
    }

    public double apply(TokenType op, double left, double right) {
        switch (op) {
            case PLUS:
                return left + right;
            case MINUS:
                return left - right;
            case MUL:
                return left * right;
            case DIV:
                if (right == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return left / right;
            default:
                throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    /*public double parsePower() {
        // Power -> factor (^ power)? right-associative
        double base = parseFactor();
        if (currentToken.type == TokenType.CARET) {
            eat(TokenType.CARET);
            double exponent = parsePower();
            return Math.pow(base, exponent);
        }
        return base;
    }*/
}