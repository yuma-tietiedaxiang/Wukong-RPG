package comp2120.ass3;

/**
 * Represents a Mythical Beast in the game, which can be equipped by the player to gain additional
 * attack and defense bonuses during battles.
 *
 * <p>This class encapsulates the name and attributes of a Mythical Beast, including the attack
 * bonus and defense bonus it provides to the player. Mythical Beasts enhance the player's
 * combat abilities by increasing their attack and defense values when equipped.</p>
 *
 * <p>Example usage:</p>
 * <pre>
 *     MythicalBeast dragon = new MythicalBeast("Dragon", 15, 10);
 *     System.out.println(dragon.getName()); // Output: Dragon
 *     System.out.println(dragon.getAttackBonus()); // Output: 15
 *     System.out.println(dragon.getDefenseBonus()); // Output: 10
 * </pre>
 *
 * @see Player
 * @see Inventory
 * @see Game
 * @see IPlayer
 *
 * @author Jun Zhu
 */
public class MythicalBeast {
    // The name of the mythical beast, e.g., "Dragon", "Phoenix".
    private String name;

    // The attack bonus provided by this mythical beast.
    private int attackBonus;

    // The defense bonus provided by this mythical beast.
    private int defenseBonus;

    /**
     * Constructs a MythicalBeast object with the specified name, attack bonus, and defense bonus.
     *
     * @param name the name of the mythical beast
     * @param attackBonus the additional attack value this beast provides
     * @param defenseBonus the additional defense value this beast provides
     */
    public MythicalBeast(String name, int attackBonus, int defenseBonus) {
        this.name = name; // Set the name of the mythical beast
        this.attackBonus = attackBonus; // Set the attack bonus value
        this.defenseBonus = defenseBonus; // Set the defense bonus value
    }

    /**
     * Returns the name of the mythical beast.
     *
     * @return the name of the beast
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the attack bonus provided by the mythical beast.
     *
     * @return the attack bonus value
     */
    public int getAttackBonus() {
        return attackBonus;
    }

    /**
     * Returns the defense bonus provided by the mythical beast.
     *
     * @return the defense bonus value
     */
    public int getDefenseBonus() {
        return defenseBonus;
    }
}
