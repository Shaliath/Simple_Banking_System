package banking;

import banking.databases.DBManager;

import java.util.Arrays;
import java.util.Random;

public class CardNPinGenerator {
    public static String generateNewCardNumber() {
        Random random = new Random();
        int[] sequence = new int[15];
        System.arraycopy(Constants.BIN, 0, sequence, 0, 6);
        for (int i = 0; i < 9; i++) {
            sequence[i + 6] = random.nextInt(10);
        }

        StringBuilder cardNumber = new StringBuilder();
        for (int number : sequence) {
            cardNumber.append(number);
        }
        cardNumber.append(calculateCheckSum(sequence));
        return cardNumber.toString();
    }

    private static int calculateCheckSum(int[] sequence) {
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                sequence[i] *= 2;
            }
        }

        int sum = Arrays.stream(sequence)
                .map(number -> number > 9 ? number - 9 : number)
                .sum();
        int checkSum = 10 - (sum % 10);
        return checkSum != 10 ? checkSum : 0;
    }

    public static String generatePIN() {
        Random random = new Random();
        StringBuilder pinCode = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            pinCode.append(random.nextInt(10));
        }
        return pinCode.toString();
    }

    public static boolean checkCard(String cardNumber) {
        if (Cards.getInstance().getActiveUser().equals(cardNumber)) {
            System.out.println("\nYou can't transfer money to the same account!\n");
            return false;
        } else if (!validateCheckSum(cardNumber)) {
            System.out.println("\nProbably you made mistake in the card number. Please try again!\n");
            return false;
        } else if (!cardExists(cardNumber)) {
            System.out.println("\nSuch a card does not exist.\n");
            return false;
        }
        return true;
    }

    private static boolean cardExists(String cardNumber) {
        return DBManager.findCard(cardNumber);
    }

    private static boolean validateCheckSum(String cardNumber) {
        int checkSum = Integer.parseInt(String.valueOf(cardNumber.charAt(cardNumber.length() - 1)));
        int[] sequence = Arrays.stream(cardNumber.substring(0, cardNumber.length() - 1).split(""))
                .mapToInt(Integer::parseInt)
                .toArray();
        int validCheckSum = calculateCheckSum(sequence);
        return checkSum == validCheckSum;
    }
}
