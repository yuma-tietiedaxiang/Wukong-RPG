package comp2120.ass3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * To test Item.
 * @author Fan Yu
 */
public class ItemTest {

    private Item item;

    @Before
    public void setUp() {
        item = new Item("Sword", 100, "weapon");
    }

    @Test
    public void testConstructor() {
        Assert.assertEquals("Sword", item.getName());
        Assert.assertEquals(100, item.getPrice());
        Assert.assertEquals("weapon", item.getType());
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Sword", item.getName());
    }

    @Test
    public void testGetPrice() {
        Assert.assertEquals(100, item.getPrice());
    }

    @Test
    public void testGetType() {
        Assert.assertEquals("weapon", item.getType());
    }
}