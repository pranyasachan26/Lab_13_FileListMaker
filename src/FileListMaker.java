import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class FileListMaker {

    private static List<String> list = new ArrayList<>();
    private static Scanner in = new Scanner(System.in);
    private static boolean needsToBeSaved = false;
    private static String currentFile = null;

    public static void main(String[] args) {
        boolean done = false;

        while (!done) {
            displayList();
            String choice = SafeInput.getRegExString(in,
                    "Enter a command [A/D/I/M/V/O/S/C/Q]: ",
                    "[AaDdIiMmVvOoSsCcQq]").toUpperCase();

            switch (choice) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "M":
                    moveItem();
                    break;
                case "V":
                    printList();
                    break;
                case "O":
                    openFile();
                    break;
                case "S":
                    saveFile();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    quitProgram();
                    done = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        System.out.println("Goodbye!");
    }

    private static void addItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to add");
        list.add(item);
        needsToBeSaved = true;
    }

    private static void deleteItem() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        printList();
        int index = SafeInput.getRangedInt(in,
                "Enter item number to delete: ", 1, list.size());
        list.remove(index - 1);
        needsToBeSaved = true;
    }

    private static void insertItem() {
        String item = SafeInput.getNonZeroLenString(in, "Enter item to insert");
        int index = SafeInput.getRangedInt(in,
                "Insert at position (1 to " + (list.size() + 1) + "): ", 1, list.size() + 1);
        list.add(index - 1, item);
        needsToBeSaved = true;
    }

    private static void moveItem() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        printList();
        int from = SafeInput.getRangedInt(in, "Enter the number of the item to move: ", 1, list.size()) - 1;
        int to = SafeInput.getRangedInt(in, "Enter the new position: ", 1, list.size()) - 1;
        String temp = list.remove(from);
        list.add(to, temp);
        needsToBeSaved = true;
    }

    private static void printList() {
        if (list.isEmpty()) {
            System.out.println("List is empty.");
            return;
        }
        System.out.println("\n==== CURRENT LIST ====");
        for (int i = 0; i < list.size(); i++) {
            System.out.printf("%3d. %s\n", i + 1, list.get(i));
        }
        System.out.println("======================\n");
    }

    private static void displayList() {
        printList();
        System.out.println("Menu: [A]dd  [D]elete  [I]nsert  [M]ove  [V]iew  [O]pen  [S]ave  [C]lear  [Q]uit");
    }

    private static void openFile() {
        if (needsToBeSaved && SafeInput.getYNConfirm(in, "Unsaved changes! Save before opening?")) {
            saveFile();
        }

        String openFile = SafeInput.getNonZeroLenString(in, "Enter filename to open (without .txt)");
        if (!openFile.endsWith(".txt")) openFile += ".txt";
        try {
            list = FileHelper.loadFile(openFile);
            currentFile = openFile;
            needsToBeSaved = false;
            System.out.println("File loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveFile() {
        if (currentFile == null) {
            currentFile = SafeInput.getNonZeroLenString(in, "Enter filename to save (without .txt)");
            if (!currentFile.endsWith(".txt")) currentFile += ".txt";
        }
        try {
            FileHelper.saveFile(list, currentFile);
            needsToBeSaved = false;
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        if (SafeInput.getYNConfirm(in, "Are you sure you want to clear the list?")) {
            list.clear();
            needsToBeSaved = true;
            System.out.println("List cleared.");
        }
    }

    private static void quitProgram() {
        if (needsToBeSaved && SafeInput.getYNConfirm(in, "Unsaved changes! Save before quitting?")) {
            saveFile();
        }
    }
}
