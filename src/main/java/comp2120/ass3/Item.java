package comp2120.ass3;

/**
 * The Item class represents a general item in the game.
 * It serves as the base class for different types of items, such as weapons and armor.
 * Each item has a name, a price, and a type that indicates whether it is a weapon or armor.
 *
 * This class provides methods to retrieve the basic information of an item, such as its name, price, and type.
 *
 * Example usage:
 * <pre>
 *     Item sword = new Item("Sword", 100, "weapon");
 *     System.out.println(sword.getName()); // Output: Sword
 *     System.out.println(sword.getPrice()); // Output: 100
 *     System.out.println(sword.getType()); // Output: weapon
 * </pre>
 *
 * @see Weapon
 * @see Armor
 * @see Inventory
 * @see Player
 *
 * Author: Jun Zhu
 */
public class Item {
    // The name of the item.
    private String name;

    // The price of the item in the game's currency (e.g., gems).
    private int price;

    // The type of the item. It can be either "weapon" or "armor".
    private String type;

    /**
     * Constructs an Item with the specified name, price, and type.
     *
     * @param name  the name of the item, such as "Sword" or "Shield".
     * @param price the price of the item, representing its cost in the game's currency.
     * @param type  the type of the item, which should be either "weapon" or "armor".
     */
    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    /**
     * Returns the name of the item.
     *
     * @return the name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the price of the item in the game's currency.
     *
     * @return the price of the item.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Returns the type of the item, indicating whether it is a weapon or armor.
     *
     * @return the type of the item, either "weapon" or "armor".
     */
    public String getType() {
        return type;
    }
}
