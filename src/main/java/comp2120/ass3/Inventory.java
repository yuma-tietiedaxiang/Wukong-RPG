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
     * Adds an item to the inventory.
     * @param item The item to be added.
     * @author Yu Ma
     */
    public void addItem(Item item) {
        items.add(item);
    }


    /**
     * Displays the contents of the inventory, including equipped weapon and armor.
     * If the inventory is empty, a message is shown to indicate this.
     *
     * @author Yu Ma
     */
    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("Your bag is empty!"); // Notify if inventory is empty
        } else {
            // Show all items in the inventory
            for (Item item : items) {
                System.out.println("- " + item.getName());
            }
            // Show equipped weapon if available
            if (equippedWeapon != null) {
                System.out.println("Weapon equipped: " + equippedWeapon.getName());
            }
            // Show equipped armor if available
            if (equippedArmor != null) {
                System.out.println("Armor equipped: " + equippedArmor.getName());
            }
        }
    }


    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }
    public void equipArmor(Armor armor) {
        equippedArmor = armor;
    }

    //getter
    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    public Armor getEquippedArmor() {
        return equippedArmor;
    }
    public List<Item> getItems() {
        return items;
    }


}
