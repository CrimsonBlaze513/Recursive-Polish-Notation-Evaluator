package Recursion.parser;

public class Tokenizer {
    private String input; // The input string to tokenizer
    private int pos = 0; // Current position in the input

    public Tokenizer(String input) {
        this.input = input.replaceAll("\\s+", ""); // Remove all whitespace
    }

    public Token nextToken() {
        // Reached the end of input
        if (pos >= input.length()) return new Token(TokenType.EOF, "");

        // Look at current character
        char current = input.charAt(pos);

        // Start of a number
        if (Character.isDigit(current) || current == '.') {
            int start = pos;
            // Scan for full number
            while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.'))
                pos++;

            // Return full number token
            return new Token(TokenType.NUMBER, input.substring(start, pos));
        }

        if (Character.isLetter(current)) {
            int start = pos;
            while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) pos++;
            return new Token(TokenType.IDENTIFIER, input.substring(start, pos));
        }

        // Every other token
        switch (current) {
            case '+':
                pos++;
                return new Token(TokenType.PLUS, "+");
            case '-':
                pos++;
                return new Token(TokenType.MINUS, "-");
            case '*':
                pos++;
                return new Token(TokenType.MUL, "*");
            case '/':
                pos++;
                return new Token(TokenType.DIV, "/");
            case '(':
                pos++;
                return new Token(TokenType.LPAREN, "(");
            case ')':
                pos++;
                return new Token(TokenType.RPAREN, ")");
            case '^':
                pos++;
                return new Token(TokenType.CARET, "^");
            case '=':
                pos++;
                return new Token(TokenType.EQUALS, "=");
            default:
                throw new RuntimeException("Unknown character: '" + current + "'");
        }
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
