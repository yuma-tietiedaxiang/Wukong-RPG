package comp2120.ass3;

import java.util.Scanner;

/**
 * The RPGGame class contains the main method to start the RPG (Role-Playing Game).
 * This is the entry point of the game application, where the game is initialized and started.
 *
 * Example usage:
 * <pre>
 *     public static void main(String[] args) {
 *         RPGGame.main(args); // Starts the game
 *     }
 * </pre>
 *
 * The main method creates an instance of the {@link Game} class and calls the {@code startGame()} method
 * to initiate the game. This class serves as a simple launcher for the game, and does not contain
 * any game logic itself.
 *
 * @author Jun Zhu
 * @author Yu Ma
 */
public class RPGGame {

    /**
     * The main method serves as the entry point for the game application. It creates an instance of the {@link Game}
     * class and starts the game by calling the {@code startGame()} method.
     *
     * @param args Command-line arguments passed to the program (not used in this application).
     */
    public static void main(String[] args) {
        // Create an instance of the Game class
        Game game = new Game(new Scanner(System.in));

        // Start the game by calling the startGame() method
        game.startGame();
    }
}
