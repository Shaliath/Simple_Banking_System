package banking;

import banking.databases.DBManager;

import java.util.Scanner;

public class Operations {

    public static void createAccount() {
        String cardNumber = CardNPinGenerator.generateNewCardNumber();
        String pinCode = CardNPinGenerator.generatePIN();
        DBManager.addNewCard(cardNumber, pinCode);
        System.out.println("\nYour card has been created\n" +
                "Your card number:\n" +
                cardNumber + "\n" +
                "Your card PIN:\n" +
                pinCode + "\n");
    }

    public static void logIntoAccount(Scanner myScanner) {
        System.out.println("\nEnter your card number:");
        String cardNumber = myScanner.nextLine();
        System.out.println("Enter your PIN:");
        String pinCode = myScanner.nextLine();
        String storedPin = DBManager.getPIN(cardNumber);
        if (!pinCode.equals(storedPin)) {
            System.out.println("\nWrong card number or PIN!\n");
        } else {
            System.out.println("\nYou have successfully logged in!\n");
            Cards.getInstance().setActiveUser(cardNumber);
        }
    }

    public static void logOut() {
        Cards.getInstance().setActiveUser(null);
        System.out.println("\nYou have successfully logged out!\n");
    }

    public static void showBalance() {
        System.out.println("\nBalance: " + DBManager.getBalance(Cards.getInstance().getActiveUser()) + "\n");
    }

    public static void addIncome(Scanner myScanner) {
        System.out.println("\nEnter income:");
        int income = myScanner.nextInt();
        DBManager.updateBalance(Cards.getInstance().getActiveUser(), income);
        myScanner.nextLine();
        System.out.println("Income was added!\n");
    }

    public static void doTransfer(Scanner myScanner) {
        System.out.println("\nEnter card number:");
        String cardNumber = myScanner.nextLine();
        if (CardNPinGenerator.checkCard(cardNumber)) {
            System.out.println("Enter how much money you want to transfer:");
            int amount = myScanner.nextInt();
            myScanner.nextLine();
            DBManager.transferMoney(cardNumber, amount);
        }
    }

    public static void closeAccount() {
        DBManager.removeCard(Cards.getInstance().getActiveUser());
        Cards.getInstance().setActiveUser(null);
        System.out.println("\nThe account has been closed!\n");
    }
}
