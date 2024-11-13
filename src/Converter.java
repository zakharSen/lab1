
import java.util.Scanner;
import java.util.List;


public class Converter {
    public static void main(String[] args) {
        Currency UAH = new Currency("UAH", 1.0);
        Currency USD = new Currency("USD", 0.027);
        Currency EUR = new Currency("EUR", 0.036);
        Currency CAD = new Currency("CAD", 0.025);

        List<String> currenciesNames = List.of(UAH.getName(), USD.getName(), EUR.getName(), CAD.getName());
        List<Currency> currencies = List.of(UAH, USD, EUR, CAD);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter converting line, like: 100 USD into UAH ");
        String convertingArguments = scanner.nextLine();

        try {
            double result = getConvertingArguments(convertingArguments, currenciesNames, currencies);
            System.out.println("Result of conversation: " + result);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    public static class Currency {
        private String name = " ";
        private double rateToUAH = 0.0;

        public Currency(String name, double rateToUAH) {
            this.name = name;
            this.rateToUAH = rateToUAH;
        }

        public String getName() {
            return name;
        }

        public double getRateToUAH() {
            return rateToUAH;
        }

    }

    public static double getConvertingArguments(String convertingArgument, List <String> currenciesNames, List <Currency> currencies) throws Exception {
        convertingArgument = convertingArgument.trim();

        String[] arguments = convertingArgument.split("\\s+");
        double result = 0;

        String firstCurrency = arguments[1].toUpperCase();
        String secondCurrency = arguments[3].toUpperCase();

        if (!(currenciesNames.contains(firstCurrency) && currenciesNames.contains(secondCurrency))) {
            throw new Exception("Invalid input of Currencies!");
        }

        double amount = Double.parseDouble(arguments[0]);

        if (!(arguments[2].equals("into"))) {
            throw new Exception("Invalid input, your request must contain into!");
        }

        Currency firstCurrencyObject = null;
        Currency secondCurrencyObject = null;

        for (Currency currency: currencies) {
            if (currency.getName().equals(firstCurrency)) {
                firstCurrencyObject = currency;
            }
            if (currency.getName().equals(secondCurrency)) {
                secondCurrencyObject = currency;
            }
        }

        if (firstCurrencyObject != null && secondCurrencyObject != null) {
            double amountInUAH = amount * firstCurrencyObject.getRateToUAH();
            result = amountInUAH / secondCurrencyObject.getRateToUAH();
        }

        return result;
    }
}