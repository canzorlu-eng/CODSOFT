package CODSOFT;

import java.util.Random;
import java.util.Scanner;

public class NumberGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int roundsWon = 0;
        boolean playAgain = true;

        while (playAgain) {
            int randomInt = random.nextInt(1, 101); // 1-100 arasında sayı
            int lives = 5;
            boolean hasWon = false;

            System.out.println("=== New Round Started ===");

            while (lives > 0) {
                System.out.println("Your lives: " + lives);
                System.out.print("Enter your guess (1-100): ");
                int guess = scanner.nextInt();

                if (guess < randomInt) {
                    System.out.println("Try bigger!");
                    lives--;
                } else if (guess > randomInt) {
                    System.out.println("Try smaller!");
                    lives--;
                } else {
                    System.out.println("WIN! You guessed the number: " + randomInt);
                    hasWon = true;
                    roundsWon++;
                    break;
                }
            }

            if (!hasWon) {
                System.out.println("You lost! The correct number was: " + randomInt);
            }

            System.out.println("Your score: " + roundsWon + " win(s)");

            System.out.print("Do you want to play again? (yes/no): ");
            scanner.nextLine();
            String response = scanner.nextLine().trim().toLowerCase();

            if (!response.equals("yes")) {
                playAgain = false;
                System.out.println("Thanks for playing! Final score: " + roundsWon + " win(s)");
            }
        }

        scanner.close();
    }
}
