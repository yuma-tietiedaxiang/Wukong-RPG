package comp2120.ass3;

/**
 * Represents an armor item in the game, which provides additional defense to the player.
 * This class extends the {@link Item} class and includes a specific defense value.
 *
 * <p>Instances of this class are equipped by the player to increase their defense
 * against enemy attacks. Each armor has a unique name, price, and defense value.</p>
 *
 * @author Jun Zhu
 * @author Yu Ma
 *
 * @see Item
 */
public class Armor extends Item {
    /**
     * The defense value provided by this armor.
     */
    private int defense;

    /**
     * Constructs an Armor object with the specified name, price, and defense value.
     *
     * @param name    the name of the armor
     * @param price   the price of the armor in gems
     * @param defense the defense value of the armor, which adds to the player's defense
     */
    public Armor(String name, int price, int defense) {
        super(name, price, "armor");
        this.defense = defense;
    }

    /**
     * Returns the defense value of this armor.
     *
     * @return the defense value
     */
    public int getDefense() {
        return defense;
    }
}
