/*File: Unit 4 Problem Set
Author: Kaveeshan Sathasivam
Date Created: Apr 26, 2026
Date Last Modified: Apr 27, 2026 */

import java.util.Scanner;  // Used to get user input
import java.util.Random;   // Used to generate random numbers

public class ProblemSet {
    public static void main(String[] args) {

        // Create Scanner object to read keyboard input
        Scanner input = new Scanner(System.in);

        // Create Random object to generate random numbers
        Random rand = new Random();

        // Display welcome message
        System.out.println("Welcome to the High Low Guessing Game.");

      
        // GET NUMBER OF ROUNDS
        int rounds = 0; // Stores number of rounds user wants

        // Loop continues until user enters a valid number (> 0)
        while (rounds <= 0) {

            System.out.print("\nInput a number of rounds to play: ");

            // Check if user typed an integer
            if (input.hasNextInt()) {

                rounds = input.nextInt(); // store the value

                // If number is 0 or negative = invalid
                if (rounds <= 0) {
                    System.out.println("Invalid Input!");
                }

            } else {
                // If user typed something like "abc"
                System.out.println("Invalid Input!");

                // Remove the invalid input
                input.next();
            }
        }
        // GET RANGE FROM USER
        int lowRange = 0;    // lowest number in range
        int highRange = 0;   // highest number in range
        boolean validRange = false; // controls loop

        // Keep asking until a valid range is entered
        while (!validRange) {

            System.out.println("\nWhat Range would you like to play between (#-#)?");

            // Read input as a String
            String rangeInput = input.next();

            // Store the position of the separator dash
            int dashIndex = -1;

            // Loop through the string to find the separator dash
            // Start at index 1 so a negative sign isnt used for a separator
            for (int i = 1; i < rangeInput.length(); i++) {

                // Check if current character is '-'
                if (rangeInput.charAt(i) == '-') {

                    // Only store the first valid separator dash
                    
                    if (dashIndex == -1) {
                        dashIndex = i;
                    }
                }
            }

            // If no separator dash was found = invalid input
            if (dashIndex == -1) {
                System.out.println("Invalid Input!");
                continue; // restart loop
            }

            // Split the input into two parts using substring
            // "15--8" - firstPart="15", secondPart="-8"
            String firstPart = rangeInput.substring(0, dashIndex);
            String secondPart = rangeInput.substring(dashIndex + 1);

            boolean validNumbers = true; // used to check if both parts are valid numbers

       
            // CHECK FIRST NUMBER (firstPart)
            for (int i = 0; i < firstPart.length(); i++) {

                // Allow negative sign ONLY at the first position
                if (i == 0 && firstPart.charAt(i) == '-') {
                    continue;
                }

                // If any character is not a digit its invalid
                if (!Character.isDigit(firstPart.charAt(i))) {
                    validNumbers = false;
                }
            }

         
            // CHECK SECOND NUMBER (secondPart)
            for (int i = 0; i < secondPart.length(); i++) {

                // Allow negative sign only at the first position
                if (i == 0 && secondPart.charAt(i) == '-') {
                    continue;
                }

                // If any character is not a digit - invalid
                if (!Character.isDigit(secondPart.charAt(i))) {
                    validNumbers = false;
                }
            }

            // If either part is empty or invalid, it will reject input
            if (!validNumbers || firstPart.equals("") || secondPart.equals("")) {
                System.out.println("Invalid Input!");
                continue;
            }

            // Convert the valid strings into integers
            lowRange = Integer.parseInt(firstPart);
            highRange = Integer.parseInt(secondPart);

      
            // HANDLE REVERSED INPUT
            // User enters "15--8" This means lowRange = 15, highRange = -8, but the is incorrect, so program swaps these values so its correct
            if (lowRange > highRange) {

                // Swap values using a temporary variable
                // swap temporarily stores one value so that I can swap it
                int swap = lowRange;

                lowRange = highRange; // move high into low
                highRange = swap;     // move low into high
            }

 
            // CHECK RANGE SIZE
            // Must have at least 3 numbers in the range, otherwise their cant be a high low and even
            // Example: 1 to 3 → valid (1,2,3)
            if (highRange - lowRange < 2) {
                System.out.println("Invalid Input!");
            } else {
                validRange = true; // valid → exit loop
            }
        }

        // CALCULATE THE MIDDLE VALUE

        // Middle can be a decimal (example: 2.5)
        double middle = (lowRange + highRange) / 2.0;

        int score = 0; // tracks correct guesses


        // MAIN GAME LOOP
        for (int i = 1; i <= rounds; i++) {

            // Display current round
            System.out.println("\nRound " + i + ":");

            int choice = 0; // stores user's menu choice

            // Keep asking until valid choice (1–3)
            while (choice < 1 || choice > 3) {

                System.out.println("\nPlease select High, Low or Even:");

                // HIGH = numbers greater than middle
                System.out.println("1. High (" + (int)Math.ceil(middle) + " to " + highRange + ")");

                // LOW = numbers less than middle
                System.out.println("2. Low (" + lowRange + " to " + ((int)Math.floor(middle) - 1) + ")");

                // EVEN = middle value or values
                if (middle % 1 == 0) {
                    // Whole number case
                    System.out.println("3. Even(" + (int)middle + ")");
                } else {
                    // Decimal case 2 middle numbers 5/2 = 2.5, rounds up to 3 and down to 2
                    System.out.println("3. Even(" + (int)Math.floor(middle) + " and " + (int)Math.ceil(middle) + ")");
                }

                // Validate input - cant be letters, and can't be something other than 1,2 or 3
                if (input.hasNextInt()) {
                    choice = input.nextInt();

                    if (choice < 1 || choice > 3) {
                        System.out.println("Invalid Input!");
                    }

                } else {
                    System.out.println("Invalid Input!");
                    input.next(); // clears and removes the invalid inputs
                }
            }

            // Generate a random number in the range
            int number = rand.nextInt(highRange - lowRange + 1) + lowRange;

            boolean correct = false; //tracks is the user guessed correctly


            // CHECK THE USER'S GUESS
            // HIGH
            if (choice == 1 && number > middle) {
                correct = true;
            }

            // LOW
            else if (choice == 2 && number < middle) {
                correct = true;
            }

            // EVEN
            else if (choice == 3) {

                // If middle is a whole number 
                if (middle % 1 == 0) {
                    if (number == (int)middle) {
                        correct = true;
                    }
                }

                // If middle is decimal (2 values) ex range is 5, 5/2 = 2.5, so middle is 2 and 3
                else {
                    if (number == (int)Math.floor(middle) ||
                        number == (int)Math.ceil(middle)) {
                        correct = true;
                    }
                }
            }

            // OUTPUT RESULT
            if (correct) {
                score++; // adds 1 point
                //tells user if their answer was right or wrong
                System.out.println("\nThe number was " + number + ". You were correct.");
            } else {
                System.out.println("\nThe number was " + number + ". You were incorrect.");
            }

            // Show the score after each round
            System.out.println("Current Score: " + score);
        }

        
        // FINAL RESULTS
        System.out.println("\nTotal Score: " + score);

        // If player got at least 50% correct - its a win, otherwise, loss
        if (score >= rounds / 2.0) {
            System.out.println("Congratulations you got " + score + " out of " + rounds + " rounds right!");
        } else {
            System.out.println("You got " + score + " out of " + rounds + " correct. Better Luck next time.");
        }

        // Close scanner 
        input.close();
    }
}