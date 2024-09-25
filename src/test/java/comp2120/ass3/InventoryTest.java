package comp2120.ass3;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * To test Inventory
 * @author Fan Yu
 */
public class InventoryTest {

    private Inventory inventory;
    private Weapon weapon;
    private Armor armor;
    private Item item;

    @Before
    public void setUp() {
        inventory = new Inventory();
        weapon = new Weapon("Sword", 15, 0);
        armor = new Armor("Shield", 10, 0);
        item = new Item("Potion", 50, "consumable");
    }

    @Test
    public void testAddItem() {
        inventory.addItem(item);
        List<Item> items = inventory.getItems();
        Assert.assertEquals(1, items.size());
        Assert.assertEquals("Potion", items.get(0).getName());
    }

    @Test
    public void testEquipWeapon() {
        inventory.equipWeapon(weapon);
        Assert.assertEquals("Sword", inventory.getEquippedWeapon().getName());
    }

    @Test
    public void testEquipArmor() {
        inventory.equipArmor(armor);
        Assert.assertEquals("Shield", inventory.getEquippedArmor().getName());
    }

    @Test
    public void testShowInventoryEmpty() {
        // This test checks if the method runs without errors when the inventory is empty
        inventory.showInventory();
    }

    @Test
    public void testShowInventoryWithItems() {
        inventory.addItem(item);
        inventory.equipWeapon(weapon);
        inventory.equipArmor(armor);

        // This test checks if the method runs without errors when there are items in the inventory
        inventory.showInventory();
    }
}