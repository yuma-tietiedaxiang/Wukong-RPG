package comp2120.ass3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test Player and related classes and methods.
 * @author Fan Yu
 */
public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("TestPlayer", 100, 50, 10, 5, 20, 15);
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals("TestPlayer", player.getName());
        Assert.assertEquals(100, player.getHealth());
        Assert.assertEquals(50, player.getStamina());
        Assert.assertEquals(10, player.attack());
        Assert.assertEquals(5, player.getDefense());
        Assert.assertEquals(20, player.getCriticalChance());
        Assert.assertEquals(15, player.getDodgeChance());
        Assert.assertEquals(200, player.getGold());
    }

    @Test
    public void testReduceStamina() {
        player.reduceStamina(10);
        Assert.assertEquals(40, player.getStamina());
    }

    @Test
    public void testAddGold() {
        player.addGold(50);
        Assert.assertEquals(250, player.getGold());
    }

    @Test
    public void testAttackWithWeapon() {
        Weapon weapon = new Weapon("Sword", 0, 15);
        player.getInventory().equipWeapon(weapon);
        Assert.assertEquals(25, player.attack());
    }

    @Test
    public void testGetDefenseWithArmor() {
        Armor armor = new Armor("Shield", 0, 10);
        player.getInventory().equipArmor(armor);
        Assert.assertEquals(15, player.getDefense());
    }

    @Test
    public void testReduceHealth() {
        player.reduceHealth(20);
        Assert.assertEquals(80, player.getHealth());
    }

    @Test
    public void testSpendGoldSuccess() {
        Assert.assertTrue(player.spendGold(50));
        Assert.assertEquals(150, player.getGold());
    }

    @Test
    public void testSpendGoldFailure() {
        Assert.assertFalse(player.spendGold(300));
        Assert.assertEquals(200, player.getGold());
    }

    @Test
    public void testMovement() {
        int initialX = player.getX();
        int initialY = player.getY();
        player.move(5, -3);
        Assert.assertEquals(initialX + 5, player.getX());
        Assert.assertEquals(initialY - 3, player.getY());
    }
}