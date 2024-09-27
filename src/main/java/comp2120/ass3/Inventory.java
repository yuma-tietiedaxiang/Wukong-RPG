package comp2120.ass3;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public Inventory() {
        items = new ArrayList<>();
        equippedWeapon = null;
        equippedArmor = null;
    }

    /**
     * Displays the player's current inventory of items, including equipped weapon and armor.
     */
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("Your bag is empty!");
        } else {
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
            if (equippedWeapon != null) {
                System.out.println("Weapon equipped: " + equippedWeapon.getName());
            }
            if (equippedArmor != null) {
                System.out.println("Armor equipped: " + equippedArmor.getName());
            }
        }
    }

    /**
     * Equips the specified weapon.
     */
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }

    /**
     * Equips the specified armor.
     */
    public void equipArmor(Armor armor) {
        equippedArmor = armor;
    }

    /**
     * Adds an item to the inventory.
     */
    public void addItem(Item item) {
        items.add(item);
    }

    /**
     * Returns the list of items in the inventory.
     */
    public List<Item> getItems() {
        return items;
    }

    // Getters
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }
}
