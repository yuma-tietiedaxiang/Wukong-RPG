package comp2120.ass3;

/**
 * The Monster class represents an enemy character in the game that the player can encounter and battle with.
 * Each monster has specific attributes such as health, damage, defense, critical chance, dodge chance, and the amount of gold
 * it rewards the player upon defeat. Monsters are positioned on the game map and can move and engage in combat.
 *
 * This class implements the {@link IMonster} interface, inheriting the basic combat functionalities and additional attributes
 * specific to a monster. It provides methods to handle attacking, taking damage, and managing its position on the game map.
 *
 * Example usage:
 * <pre>
 *     Monster goblin = new Monster("Goblin", 100, 15, 5, 10, 5, 20);
 *     goblin.setMonsterX(5);
 *     goblin.setMonsterY(7);
 *     System.out.println(goblin.getName()); // Output: Goblin
 *     System.out.println(goblin.attack()); // Output: 15
 *     goblin.reduceHealth(10);
 *     System.out.println(goblin.getHealth()); // Output: 90
 * </pre>
 *
 * @see IMonster
 * @see ICombatObject
 * @see Player
 *
 * @author Jun Zhu
 * @author Yu Ma
 */
public class Monster implements IMonster {
    // The name of the monster, such as "Goblin" or "Orc".
    private String name;

    // The current health of the monster. It decreases as the monster takes damage.
    private int health;

    // The base damage dealt by the monster during attacks.
    private int damage;

    // The monster's defense points, reducing incoming damage.
    private int defense;

    // The critical hit chance of the monster, represented as a percentage.
    private int criticalChance;

    // The chance of the monster dodging an attack, represented as a percentage.
    private int dodgeChance;

    // The amount of gold rewarded to the player upon defeating the monster.
    private int goldReward;

    // The x-coordinate position of the monster on the game map.
    private int monsterX;

    // The y-coordinate position of the monster on the game map.
    private int monsterY;

    /**
     * Constructs a Monster object with the specified attributes.
     *
     * @param name          the name of the monster, such as "Goblin" or "Orc".
     * @param health        the total health of the monster.
     * @param damage        the base damage dealt by the monster during attacks.
     * @param defense       the defense points of the monster, reducing incoming damage.
     * @param criticalChance the chance of the monster landing a critical hit, represented as a percentage.
     * @param dodgeChance   the chance of the monster dodging an attack, represented as a percentage.
     * @param goldReward    the amount of gold rewarded to the player upon defeating the monster.
     */
    public Monster(String name, int health, int damage, int defense, int criticalChance, int dodgeChance, int goldReward) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.defense = defense;
        this.criticalChance = criticalChance;
        this.dodgeChance = dodgeChance;
        this.goldReward = goldReward;
    }

    /**
     * Returns the base attack damage of the monster.
     *
     * @return the base damage dealt by the monster.
     */
    public int attack() {
        return this.damage;
    }

    /**
     * Reduces the monster's health by the specified amount.
     *
     * @param amount the amount of damage taken by the monster.
     */
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    /**
     * Returns the name of the monster.
     *
     * @return the name of the monster.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current health of the monster.
     *
     * @return the current health of the monster.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the amount of gold rewarded to the player upon defeating the monster.
     *
     * @return the gold reward of the monster.
     */
    public int getGoldReward() {
        return goldReward;
    }

    /**
     * Returns the defense points of the monster, reducing incoming damage.
     *
     * @return the defense points of the monster.
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Returns the chance of the monster dodging an attack, represented as a percentage.
     *
     * @return the dodge chance of the monster.
     */
    public int getDodgeChance() {
        return dodgeChance;
    }

    /**
     * Returns the critical hit chance of the monster, represented as a percentage.
     *
     * @return the critical hit chance of the monster.
     */
    public int getCriticalChance() {
        return criticalChance;
    }

    /**
     * Sets the x-coordinate of the monster on the game map.
     *
     * @param x the new x-coordinate position of the monster.
     */
    public void setMonsterX(int x) {
        this.monsterX = x;
    }

    /**
     * Sets the y-coordinate of the monster on the game map.
     *
     * @param y the new y-coordinate position of the monster.
     */
    public void setMonsterY(int y) {
        this.monsterY = y;
    }

    /**
     * Returns the x-coordinate of the monster on the game map.
     *
     * @return the x-coordinate position of the monster.
     */
    @Override
    public int getX() {
        return monsterX;
    }

    /**
     * Returns the y-coordinate of the monster on the game map.
     *
     * @return the y-coordinate position of the monster.
     */
    @Override
    public int getY() {
        return monsterY;
    }

    /**
     * Moves the monster by the specified offsets in the x and y directions.
     *
     * @param offsetX the offset to move the monster in the x direction.
     * @param offsetY the offset to move the monster in the y direction.
     */
    @Override
    public void move(int offsetX, int offsetY) {
        monsterX += offsetX;
        monsterY += offsetY;
    }

    /**
     * Returns the base damage of the monster.
     * This method overrides the default getDamage() method in the {@link ICombatObject} interface.
     *
     * @return the base damage dealt by the monster.
     */
    @Override
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the maximum health of the monster.
     * This method is currently implemented to return the current health as there is no distinct max health property.
     *
     * @return the current health of the monster.
     */
    @Override
    public int getMaxHealth() {
        return health;
    }
}
