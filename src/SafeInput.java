import java.util.Scanner;

public class SafeInput {

    public static String getNonZeroLenString(Scanner console, String prompt) {
        String input;
        do {
            System.out.print(prompt + ": ");
            input = console.nextLine().trim();
        } while (input.length() == 0);
        return input;
    }

    public static String getRegExString(Scanner console, String prompt, String regEx) {
        String input;
        do {
            System.out.print(prompt);
            input = console.nextLine();
        } while (!input.matches(regEx));
        return input;
    }

    public static int getRangedInt(Scanner console, String prompt, int low, int high) {
        int input;
        do {
            System.out.print(prompt);
            while (!console.hasNextInt()) {
                System.out.print("Invalid input! " + prompt);
                console.next();
            }
            input = console.nextInt();
            console.nextLine(); // clear buffer
        } while (input < low || input > high);
        return input;
    }

    public static boolean getYNConfirm(Scanner console, String prompt) {
        String input;
        do {
            System.out.print(prompt + " (Y/N): ");
            input = console.nextLine().trim().toUpperCase();
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}
