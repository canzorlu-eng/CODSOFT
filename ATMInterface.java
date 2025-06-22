package CODSOFT;

import java.util.Scanner;

// User class
class UserAccount {
    private int balance;

    public UserAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean withdraw(int amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            return true;
        } else {
            return false;
        }
    }
}

// ATM
class ATMMachine {
    private UserAccount account;

    public ATMMachine(UserAccount account) {
        this.account = account;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== ATM Menu ===");
            System.out.println("Enter 1 : Check Balance");
            System.out.println("Enter 2 : Deposit");
            System.out.println("Enter 3 : Withdraw");
            System.out.println("Enter 4 : Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nYour balance: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ");
                    int depositAmount = scanner.nextInt();
                    if (account.deposit(depositAmount)) {
                        System.out.println("\nDeposit successful.");
                    } else {
                        System.out.println("\nInvalid amount. Please try again.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    int withdrawAmount = scanner.nextInt();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("\nWithdrawal successful.");
                    } else {
                        System.out.println("\nInsufficient balance or invalid amount.");
                    }
                    break;
                case 4:
                    running = false;
                    System.out.println("\nThank you for using the ATM!");
                    break;
                default:
                    System.out.println("\nInvalid option. Please try again.");
            }
        }

        scanner.close();
    }
}

// Main Class
public class ATMInterface {
    public static void main(String[] args) {
        // initial balance
        UserAccount userAccount = new UserAccount(1000);

        // start machine
        ATMMachine atm = new ATMMachine(userAccount);
        atm.start();
    }
}
