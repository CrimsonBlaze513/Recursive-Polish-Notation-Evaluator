import java.util.HashMap;
import java.util.Scanner;

public class ExpressionParser {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashMap<String, Double> globalVariables = new HashMap<>();
        System.out.print("Enter an arithmetic expression or type 'exit' to quit: ");



        // REPL loop
        while (true) {
            System.out.println("expr> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                Tokenizer tokenizer = new Tokenizer(input);
                Parser parser = new Parser(tokenizer, globalVariables);
                double result = parser.parse();
                System.out.println("Result: " +result);
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
