import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseStr {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression: ");
        String expression = scanner.nextLine();

        try {
            double result = calcExpr(expression);
            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    public static double calcExpr(String expression) throws Exception {
        expression = expression.replace("=", "").replace("?", "").trim();

        expression = expression.replaceAll("\\-\\s+", "-");

        Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)\\s*([+\\-*/])\\s*(-?\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(expression);

        if (!matcher.matches()) {
            throw new Exception("Invalid expression format. Example: - 9 + 8");
        }

        double operand1 = Double.parseDouble(matcher.group(1));
        double operand2 = Double.parseDouble(matcher.group(3));
        String operator = matcher.group(2);

        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero.");
                }
                return operand1 / operand2;
            default:
                throw new Exception("Unknown operator: " + operator);
        }
    }
}

