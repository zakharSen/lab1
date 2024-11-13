import java.util.List;
import java.util.Scanner;

public class BankTransactions {

    public static void main(String[] args) {

        Bank bank1 = new Bank("MonoBank", "UAH", 1.0);
        Bank bank2 = new Bank("European Bank", "EUR", 40.0);
        Bank bank3 = new Bank("American Bank", "USD", 38.0);

        BankAccount bankAccount1 = new BankAccount(4000, bank1);
        BankAccount bankAccount2 = new BankAccount(5000, bank2);
        BankAccount bankAccount3 = new BankAccount(6000, bank3);

        User user1 = new User("Zakhar", List.of(bankAccount1, bankAccount2, bankAccount3));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Available accounts for user " + user1.getUser_name() + ":");
        System.out.println("1. Account 1 (MonoBank) balance: " + bankAccount1.getBalance() + " UAH");
        System.out.println("2. Account 2 (European Bank) balance: " + bankAccount2.getBalance() + " EUR");
        System.out.println("3. Account 3 (American Bank) balance: " + bankAccount3.getBalance() + " USD");

        System.out.print("Enter the number of the account to transfer FROM (1-3): ");
        int fromAccountIndex = scanner.nextInt();

        System.out.print("Enter the number of the account to transfer TO (1-3): ");
        int toAccountIndex = scanner.nextInt();

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        BankAccount fromAccount = getAccountByIndex(user1, fromAccountIndex);
        BankAccount toAccount = getAccountByIndex(user1, toAccountIndex);

        if (fromAccount != null && toAccount != null) {
            user1.transfer(fromAccount, toAccount, amount);
        } else {
            System.out.println("Invalid account selection.");
        }

        System.out.println("After transfer:");
        System.out.println("Account 1 balance: " + bankAccount1.getBalance() + " UAH");
        System.out.println("Account 2 balance: " + bankAccount2.getBalance() + " EUR");
        System.out.println("Account 3 balance: " + bankAccount3.getBalance() + " USD");
    }

    private static BankAccount getAccountByIndex(User user, int index) {
        if (index >= 1 && index <= user.accounts.size()) {
            return user.accounts.get(index - 1);
        }
        return null;
    }

    public static class Bank {
        private String bankName = " ";
        private String bankCurrency = " ";
        private double exchangeRateToUAH;

        public Bank(String bankName, String bankCurrency, double exchangeRateToUAH) {
            this.bankName = bankName;
            this.bankCurrency = bankCurrency;
            this.exchangeRateToUAH = exchangeRateToUAH;
        }

        public String getBankname() {
            return bankName;
        }

        public String getBankcurrency() {
            return bankCurrency;
        }

        public double getExchangeRateToUAH() {
            return exchangeRateToUAH;
        }
    }

    public static class BankAccount {
        private double balance = 0;
        private final Bank bank;

        public BankAccount(int balance, Bank bank) {
            this.balance = balance;
            this.bank = bank;
        }

        public double getBalance() {
            return balance;
        }

        public void deposit(double amount) {
            this.balance += amount;
        }

        public String getCurrency() {
            return bank.getBankcurrency();
        }

        public boolean withdraw(double amount) {
            if (this.balance >= amount) {
                this.balance -= amount;
                return true;
            }
            return false;
        }

        public Bank getBank() {
            return bank;
        }

    }

    public static class User {
        private String userName = " ";
        private List<BankAccount> accounts;

        public User(String userName, List<BankAccount> accounts) {
            this.userName = userName;
            this.accounts = accounts;
        }

        public String getUser_name() {
            return userName;
        }

        public List<BankAccount> getAccounts() {
            return accounts;
        }

        public void transfer(BankAccount fromAccount, BankAccount toAccount, double amount) {
            if (fromAccount.withdraw(amount)) {
                double convertedAmount = convertCurrency(amount, fromAccount.getBank(), toAccount.getBank());
                double commission = calculateCommission(fromAccount, toAccount, amount);

                double commissionInTargetCurrency = convertCurrency(commission, fromAccount.getBank(), toAccount.getBank());

                toAccount.deposit(convertedAmount - commissionInTargetCurrency);

                System.out.println("Transfer successful with commission: " + commissionInTargetCurrency);
            } else {
                System.out.println("Not enough money.");
            }
        }

        private double convertCurrency(double amount, Bank fromBank, Bank toBank) {
            if (fromBank.getBankcurrency().equals(toBank.getBankcurrency())) {
                return amount;
            }
            double amountInUAH = amount * fromBank.getExchangeRateToUAH();
            return amountInUAH / toBank.getExchangeRateToUAH();
        }

        private double calculateCommission(BankAccount fromAccount, BankAccount toAccount, double amount) {
            boolean sameBank = fromAccount.getBank() == toAccount.getBank();
            boolean sameUser = this.accounts.contains(fromAccount) && this.accounts.contains(toAccount);

            if (sameUser && sameBank) {
                return 0;
            } else if (sameUser && !sameBank) {
                return amount * 0.02;
            } else if (!sameUser && sameBank) {
                return amount * 0.03;
            } else {
                return amount * 0.06;
            }
        }
    }
}
