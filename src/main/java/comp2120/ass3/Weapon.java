package comp2120.ass3;

/**
 * The Weapon class represents a weapon item that can be equipped by the player in the game.
 * Each weapon has a name, price, and a specific amount of damage it can deal.
 * This class extends the {@link Item} class and adds a damage attribute specific to weapons.
 *
 * <p>Weapons can be purchased in shops and equipped by the player to increase their attack power
 * during battles. The damage attribute of a weapon contributes to the total attack damage of the player.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     Weapon sword = new Weapon("Sword", 100, 25);
 *     int weaponDamage = sword.getDamage();
 *     System.out.println("The sword deals " + weaponDamage + " damage.");
 * </pre>
 *
 * @author Jun Zhu
 * @author Yu Ma
 */
public class Weapon extends Item {

    /** The amount of damage this weapon deals. */
    private int damage;

    /**
     * Constructs a new Weapon object with the specified name, price, and damage value.
     *
     * @param name The name of the weapon (e.g., "Sword").
     * @param price The price of the weapon in gems or currency.
     * @param damage The damage value that this weapon can inflict.
     */
    public Weapon(String name, int price, int damage) {
        super(name, price, "weapon"); // Calls the constructor of the superclass Item
        this.damage = damage;
    }

    /**
     * Returns the amount of damage that this weapon can deal.
     *
     * @return The damage value of the weapon.
     */
    public int getDamage() {
        return damage;
    }
}
