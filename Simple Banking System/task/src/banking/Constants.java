package banking;

public class Constants {
    public static final String MAIN_MENU = setMainMenu();
    public static final int[] BIN = setBIN();
    public static String DB_NAME;
    public static String DB_URL = "jdbc:sqlite:";


    private static int[] setBIN() {
        return new int[]{4, 0, 0, 0, 0, 0};
    }

    public static final String ACCOUNT_MENU = setLoggedInMenu();

    private static String setLoggedInMenu() {
        return new StringBuilder()
                .append("1. Balance\n")
                .append("2. Add income\n")
                .append("3. Do transfer\n")
                .append("4. Close account\n")
                .append("5. Log out\n")
                .append("0. Exit")
                .toString();
    }

    private static String setMainMenu() {
        return new StringBuilder()
                .append("1. Create an account\n")
                .append("2. Log into account\n")
                .append("0. Exit")
                .toString();
    }

    public static String getDatabaseURL() {
        return DB_URL + DB_NAME;
    }
}
