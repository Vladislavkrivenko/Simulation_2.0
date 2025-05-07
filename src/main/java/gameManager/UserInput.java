package gameManager;

import java.util.Scanner;

public class UserInput {
    private final Scanner scanner = new Scanner(System.in);

    public int inputNumbersFromUser(String message) {
        int number;
        while (true) {
            System.out.println(message);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number >= 4) {
                    return number;
                } else {
                    System.out.println("the number must not be lower than 4");
                }
            } else {
                System.out.println("This is not number");
                scanner.next();
            }
        }
    }

    public int gameMenu() {
        int num;
        while (true) {
            System.out.print("Enter a number for  \n1 START GAME\n 2 PAUSE\n3 RESUME\n4 1:ITERATION\n5 STOP GAME\n");
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                if (num >= 1 && num <= 5) {
                    return num;
                } else {
                    System.out.println("Incorrect choice! Enter a number from 1 to 4.");
                }
            } else {
                System.out.println("Incorrect choice! Enter a number from 1 to 4.");
                scanner.next();
            }
        }
    }
}

