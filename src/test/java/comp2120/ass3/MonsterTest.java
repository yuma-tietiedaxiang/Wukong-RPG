package comp2120.ass3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test Monster and related classes and methods.
 * @author Fan Yu
 */
public class MonsterTest {

    private Monster monster;

    @Before
    public void setUp() {
        monster = new Monster("Goblin", 100, 15, 5, 10, 20, 50);
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals("Goblin", monster.getName());
        Assert.assertEquals(100, monster.getHealth());
        Assert.assertEquals(15, monster.attack());
        Assert.assertEquals(5, monster.getDefense());
        Assert.assertEquals(10, monster.getCriticalChance());
        Assert.assertEquals(20, monster.getDodgeChance());
        Assert.assertEquals(50, monster.getGoldReward());
    }

    @Test
    public void testReduceHealth() {
        monster.reduceHealth(20);
        Assert.assertEquals(80, monster.getHealth());
    }

    @Test
    public void testAttack() {
        Assert.assertEquals(15, monster.attack());
    }

    @Test
    public void testMovement() {
        monster.setMonsterX(0);
        monster.setMonsterY(0);
        monster.move(5, -3);
        Assert.assertEquals(5, monster.getX());
        Assert.assertEquals(-3, monster.getY());
    }

    @Test
    public void testSetMonsterPosition() {
        monster.setMonsterX(10);
        monster.setMonsterY(15);
        Assert.assertEquals(10, monster.getX());
        Assert.assertEquals(15, monster.getY());
    }
}