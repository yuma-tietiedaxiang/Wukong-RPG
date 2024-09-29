package comp2120.ass3;

import java.util.ArrayList;
import java.util.List;

/**
 * The Inventory class represents the player's collection of items in the game.
 * It manages the items that the player has acquired, as well as the currently equipped weapon and armor.
 *
 * This class provides methods to add items to the inventory, equip weapons and armors,
 * and display the contents of the inventory. It also allows retrieving the list of items
 * and the currently equipped weapon and armor.
 *
 * The Inventory plays a crucial role in the game, enabling the player to manage and utilize their resources
 * effectively during gameplay, impacting their combat abilities and overall performance.
 *
 * Example usage:
 * <pre>
 *     Inventory inventory = new Inventory();
 *     Weapon sword = new Weapon("Sword", 100, 20);
 *     Armor shield = new Armor("Shield", 50, 10);
 *     inventory.addItem(sword);
 *     inventory.addItem(shield);
 *     inventory.equipWeapon(sword);
 *     inventory.equipArmor(shield);
 * </pre>
 *
 * @see Item
 * @see Weapon
 * @see Armor
 * @see Player
 * @see Game
 *
 * @author Jun Zhu
 * @author Yu Ma
 */
public class Inventory {
    // A list to store all items in the inventory.
    private List<Item> items;

    // The currently equipped weapon. Null if no weapon is equipped.
    private Weapon equippedWeapon;

    // The currently equipped armor. Null if no armor is equipped.
    private Armor equippedArmor;

    /**
     * Constructs an empty inventory with no items, no equipped weapon, and no equipped armor.
     */
    public Inventory() {
        items = new ArrayList<>();
        equippedWeapon = null;
        equippedArmor = null;
    }

    /**
     * Displays the player's current inventory of items in the console,
     * including the names of the equipped weapon and armor, if any.
     *
     * If the inventory is empty, it displays a message indicating that the bag is empty.
     */
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("Your bag is empty!"); // Display message if inventory is empty
        } else {
            for (Item item : items) {
                System.out.println("- " + item.getName()); // Display each item's name
            }
            if (equippedWeapon != null) {
                System.out.println("Weapon equipped: " + equippedWeapon.getName()); // Display equipped weapon
            }
            if (equippedArmor != null) {
                System.out.println("Armor equipped: " + equippedArmor.getName()); // Display equipped armor
            }
        }
    }

    /**
     * Equips the specified weapon from the inventory.
     * If a weapon is already equipped, it will be replaced by the new weapon.
     *
     * @param weapon the weapon to equip.
     */
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon; // Set the equipped weapon to the specified weapon
    }

    /**
     * Equips the specified armor from the inventory.
     * If armor is already equipped, it will be replaced by the new armor.
     *
     * @param armor the armor to equip.
     */
    public void equipArmor(Armor armor) {
        equippedArmor = armor; // Set the equipped armor to the specified armor
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item the item to add to the inventory.
     */
    public void addItem(Item item) {
        items.add(item); // Add the specified item to the inventory list
    }

    /**
     * Returns the list of items currently in the inventory.
     *
     * @return a list of items in the inventory.
     */
    public List<Item> getItems() {
        return items; // Return the list of items in the inventory
    }

    /**
     * Returns the currently equipped weapon.
     *
     * @return the equipped weapon, or null if no weapon is equipped.
     */
    public Weapon getEquippedWeapon() {
        return equippedWeapon; // Return the currently equipped weapon
    }

    /**
     * Returns the currently equipped armor.
     *
     * @return the equipped armor, or null if no armor is equipped.
     */
    public Armor getEquippedArmor() {
        return equippedArmor; // Return the currently equipped armor
    }
}
