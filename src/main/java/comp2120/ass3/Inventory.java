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
     * @auther Yingxuan Tang
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
     *
     * @param weapon The weapon to be equipped.
     * @author Yingxuan Tang
     */
    public void equipWeapon(Weapon weapon) {
        equippedWeapon = weapon;
    }



    /**
     * Equips the specified armor.
     *
     * @param armor The armor to be equipped.
     * @author Yingxuan Tang
     */
    public void equipArmor(Armor armor) {
        equippedArmor = armor;
    }


    /**
     * Returns the list of items in the inventory.
     *
     * @return A list of items.
     * @author Yingxuan Tang
     */
    public List<Item> getItems() {
        return items;
    }
}
