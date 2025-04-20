public class Tokenizer {
    private String input; // The input string to tokenizer
    private int pos = 0; // Current position in the input
    private Token lastToken = null; // Track the last token to handle unary minus
    private int tracker = 0;

    public Tokenizer(String input) {
        this.input = input.trim().replaceAll("\\s+", ","); // Remove all whitespace at the beginning and end, and replaces the middle with ,
    }

    public Token nextToken() {
        // Skip over commas
        while (pos < input.length() && input.charAt(pos) == ',') {
            pos++;
        }

        // Reached the end of input
        if (pos >= input.length()) return new Token(TokenType.EOF, "");

        // Look at current character
        char current = input.charAt(pos);

        if (tracker != 2 && (current == '-' || current == '+'|| current == '*' || current == '/')) {
            tracker++;
        }

        if (current == '-' && tracker == 2) {
            int start = pos++;
            // Check if it's followed by a number
            if (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
                while (pos < input.length() &&
                        (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.')) {
                    pos++;
                }
                Token token = new Token(TokenType.NUMBER, input.substring(start, pos));
                lastToken = token;
                return token;
            } else {
                // Just a minus sign
                lastToken = new Token(TokenType.MINUS, "-");
                return lastToken;
            }
        }

        // Start of a number
        if (Character.isDigit(current) || current == '.') {
            int start = pos;
            // Scan for full number
            while (pos < input.length() && (Character.isDigit(input.charAt(pos)) || input.charAt(pos) == '.'))
                pos++;

            // Return full number token
            Token token = new Token(TokenType.NUMBER, input.substring(start, pos));
            lastToken = token;
            return token;
        }

        if (Character.isLetter(current)) {
            int start = pos;
            while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) pos++;
            Token token = new Token(TokenType.IDENTIFIER, input.substring(start, pos));
            lastToken = token;
            return token;
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