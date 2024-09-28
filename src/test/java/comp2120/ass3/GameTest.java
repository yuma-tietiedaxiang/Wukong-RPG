package comp2120.ass3;

import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

/**
 * The automatic game tester
 * @author Fan Yu
 */
public class GameTest {
    @Test
    public void testSimpleMovement() {
        String input = "\n1\nd\nw\nw\ns\nd\na\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }

    @Test
    public void testHome() {
        String input = "\n1\nd\nd\nd\nw\nw\nw\nw\n\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();

        Assert.assertTrue(game.isSlept());
    }

    @Test
    public void testClinic() {
        String input = "\n1\nd\nd\nd\nd\nd\nd\nd\nd\nw\nw\nw\nw\n1\n2\n3\n0\n\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }

    @Test
    public void testWeaponShop() {
        String input = "\n1\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\ns\ns\ns\ns\n1\n2\n3\n0\n\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }

    @Test
    public void testArmorShop() {
        String input = "\n3\nd\nd\nd\nd\nd\nd\nd\ns\ns\ns\ns\n1\n2\n3\n0\n\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }

    @Test
    public void testPetShop() {
        String input = "\n2\nd\nd\nd\ns\ns\ns\ns\n1\n2\n3\n0\n\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }

    @Test
    public void testBattleField() {
        String input = "\n1\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\nd\n" +
                "d\nd\nd\nd\nd\ns\nq\nq\n";
        Scanner scanner = new Scanner(input);
        Game game = new Game(scanner);
        game.startGame();
    }
}
