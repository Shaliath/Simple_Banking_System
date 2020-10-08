package banking.databases;

import banking.Cards;
import banking.Constants;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    public static void checkDB() {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                try (Statement statement = con.createStatement()) {
                    statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                            "id INTEGER PRIMARY KEY," +
                            "number TEXT NOT NULL," +
                            "pin TEXT NOT NULL," +
                            "balance INTEGER DEFAULT 0)");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewCard(String number, String pin) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("INSERT INTO card (number, pin) VALUES (" +
                        number + "," + pin + ")");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPIN(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet pin = statement.executeQuery("SELECT pin FROM card WHERE number=" + cardNumber)) {
                    if (pin.next()) {
                        return pin.getString("pin");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBalance(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet balance = statement.executeQuery("SELECT balance FROM card WHERE number=" + cardNumber)) {
                    if (balance.next()) {
                        return balance.getString("balance");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateBalance(String cardNumber, int income) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("UPDATE card SET balance = balance +" + income +
                        " WHERE number = " + cardNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeCard(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("DELETE FROM card WHERE number = " + cardNumber);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean findCard(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet card = statement.executeQuery("SELECT number FROM card WHERE number=" + cardNumber)) {
                    if (card.next()) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void transferMoney(String cardTo, int amount) {
        String cardFrom = Cards.getInstance().getActiveUser();
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(Constants.getDatabaseURL());
        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                try (ResultSet card = statement.executeQuery("SELECT balance FROM card WHERE number=" + cardFrom)) {
                    if (card.next()) {
                        if (card.getInt("balance") >= amount) {
                            statement.executeUpdate("UPDATE card SET balance = balance +" + amount * -1 +
                                    " WHERE number = " + cardFrom);
                            statement.executeUpdate("UPDATE card SET balance = balance +" + amount +
                                    " WHERE number = " + cardTo);
                            System.out.println("Success!\n");
                        } else {
                            System.out.println("Not enough money!\n");
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
