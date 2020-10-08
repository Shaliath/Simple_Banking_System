package banking;

import banking.databases.DBManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if ("-fileName".equals(args[0])) {
            Constants.DB_NAME = args[1];
        }
        DBManager.checkDB();
        Scanner myScanner = new Scanner(System.in);
        boolean run = true;
        while (run) {
            showMenu();
            String command = myScanner.nextLine().toLowerCase();
            switch (command) {
                case "1":
                    processOne();
                    break;
                case "2":
                    processTwo(myScanner);
                    break;
                case "3":
                    processThree(myScanner);
                    break;
                case "4":
                    processFour();
                    break;
                case "5":
                    processFive();
                    break;
                case "0":
                    System.out.println("\nBye!");
                    run = false;
                    myScanner.close();
                    break;
                default:
                    System.out.println("Wrong operation!");
            }
        }
    }

    private static void processOne() {
        if (isLoggedIn()) {
            Operations.showBalance();
        } else {
            Operations.createAccount();
        }
    }

    private static void processTwo(Scanner myScanner) {
        if (isLoggedIn()) {
            Operations.addIncome(myScanner);
        } else {
            Operations.logIntoAccount(myScanner);
        }
    }

    private static void processThree(Scanner myScanner) {
        if (isLoggedIn()) {
            Operations.doTransfer(myScanner);
        } else {
            System.out.println("Wrong operation!");
        }
    }

    private static void processFour() {
        if (isLoggedIn()) {
            Operations.closeAccount();
        } else {
            System.out.println("Wrong operation!");
        }
    }

    private static void processFive() {
        if (isLoggedIn()) {
            Operations.logOut();
        } else {
            System.out.println("Wrong operation!");
        }
    }

    private static void showMenu() {
        if (Cards.getInstance().getActiveUser() == null) {
            System.out.println(Constants.MAIN_MENU);
        } else {
            System.out.println(Constants.ACCOUNT_MENU);
        }
    }

    private static boolean isLoggedIn() {
        return Cards.getInstance().getActiveUser() != null;
    }
}
