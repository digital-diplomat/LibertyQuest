
// LibertyQuest: Main program for LibertyQuest game.
// Copyright Brennon Miller
// Available under Creative Commons Attribution-ShareAlike 4.0 License

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

class LibertyQuest {

    public static void main(String[] args) throws Exception {
        File currentFile = new File("intro.txt");
        Scanner fileReader = new Scanner(currentFile);

        byte strength = 0; // Player stats
        byte stealth = 0;
        byte charisma = 0;

        int selection = 0;
        boolean validSelection = false; // Checks that selection is valid.
        Scanner input = new Scanner(System.in);

        typeWrite(fileReader, 0); // Title Screen

        input.nextLine(); // User presses Enter.

        fileReader = switchTo("prologue1.txt", currentFile);
        typeWrite(fileReader, 24); // ...political turmoil...
        input.nextLine(); // Enter to continue

        fileReader = switchTo("prologue2.txt", currentFile);
        typeWrite(fileReader, 24); // ...Daniel Salvador...
        input.nextLine(); // Enter to continue

        // Start Gameplay!
        for (int i = 1; i <= 3; i++) {
            fileReader = switchTo(String.format("encounter%d.txt", i), currentFile);
            typeWrite(fileReader, 16);

            while (!validSelection) {
                while (!input.hasNextInt()) {
                    if (input.hasNext()) {
                        input.next();
                    } // Toss invalid value.
                    System.out.print("Please make a valid selection.\n> ");
                }
                selection = input.nextInt();
                if (selection < 4 && selection > 0) {
                    validSelection = true;
                } else {
                    System.out.print("Please make a valid selection.\n> ");
                }
                input.nextLine();   // Clears previous input.
            }
            switch (selection) {
            case 1:
                fileReader = switchTo(String.format("result/strength%d.txt", i), currentFile);
                typeWrite(fileReader, 16);
                strength++;
                break;
            case 2:
                fileReader = switchTo(String.format("result/charisma%d.txt", i), currentFile);
                typeWrite(fileReader, 16);
                charisma++;
                break;
            case 3:
                fileReader = switchTo(String.format("result/stealth%d.txt", i), currentFile);
                typeWrite(fileReader, 16);
                stealth++;
            }
            input.nextLine();   // Enter to countinue
            validSelection = false;
        }

        // Boss Fight
        fileReader = switchTo("boss/fight.txt", currentFile);
        typeWrite(fileReader, 16);
        while (!validSelection) {
            while (!input.hasNextInt()) {
                if (input.hasNext()) {
                    input.next();
                } // Toss invalid value.
                System.out.print("Please make a valid selection.\n> ");
            }
            selection = input.nextInt();
            if (selection < 4 && selection > 0) {
                validSelection = true;
            } else {
                System.out.print("Please make a valid selection.\n> ");
            }
        }
        switch (selection) {
        case 1:
            if (strength >= 2) {
                fileReader = switchTo("boss/strengthWin.txt", currentFile);
            } else {
                fileReader = switchTo("boss/strengthFail.txt", currentFile);
            }
        case 2:
            if (strength >= 2) {
                fileReader = switchTo("boss/charismaWin.txt", currentFile);
            } else {
                fileReader = switchTo("boss/charismaFail.txt", currentFile);
            }
        case 3:
            if (strength >= 2) {
                fileReader = switchTo("boss/stealthWin.txt", currentFile);
            } else {
                fileReader = switchTo("boss/stealthFail.txt", currentFile);
            }
        }

        /* ========================================================================== */

        // When done with the program.
        System.out.println("Strength: " + strength);
        System.out.println("Charisma: " + charisma);
        System.out.println("Stealth:  " + stealth);

        System.out.print("\nPlay again for more unique results! ");
        System.out.println("You can even try a new playstyle!");

        fileReader.close();
        input.close();
    }

    static void typeWrite(Scanner from, int delay) throws InterruptedException {
        delay = 0;    // DEBUG use only.
        from.useDelimiter("");
        while (from.hasNext()) {
            if (from.hasNext("(\u0008)")) {
                Thread.sleep(delay * 4);
            }
            if (from.hasNext("\\.")) {
                System.out.print(from.next());
                Thread.sleep(delay * 16);
                continue;
            }
            System.out.print(from.next());
            Thread.sleep(delay);
        }
    }

    static Scanner switchTo(String name, File file) {
        try {
            file = new File(name);
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.print("ERROR: A game file is missing. Have you edited");
            System.out.println(" the folder or its files?");
            Runtime.getRuntime().exit(2);
        }
        return null;
    }
}
