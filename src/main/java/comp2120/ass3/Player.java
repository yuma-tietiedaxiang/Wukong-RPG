package comp2120.ass3;

/**
 * The Player class represents the player's character in the game, which includes attributes such as health, stamina,
 * damage, defense, critical chance, dodge chance, and an inventory for managing items. The player can perform actions
 * such as attacking, healing, and resting. This class implements the {@link IPlayer} interface, inheriting combat-related
 * functionalities. The player can also interact with shops and use items from the inventory.
 *
 * Example usage:
 * <pre>
 *     Player player = new Player("Monkey King", 200, 155, 30, 16, 20, 15);
 *     player.rest();
 *     player.reduceHealth(50);
 *     System.out.println(player.getHealth()); // Output: 150
 *     player.addGold(100);
 *     System.out.println(player.getGold()); // Output: 300
 * </pre>
 *
 * @see IPlayer
 * @see ICombatObject
 * @see Inventory
 *
 * @author Jun Zhu
 */
public class Player implements IPlayer {
    // The name of the player character, such as "Monkey King" or "Pigsy".
    private String name;

    // The current health of the player. Decreases as the player takes damage.
    private int health;

    // The maximum health the player can have.
    private int maxHealth;

    // The current stamina of the player. Decreases when the player performs actions that consume stamina.
    private int stamina;

    // The maximum stamina the player can have.
    private int maxStamina;

    // The base damage dealt by the player during attacks.
    private int damage;

    // The player's base defense points, reducing incoming damage.
    private int defense;

    // The player's critical hit chance, represented as a percentage.
    private int criticalChance;

    // The player's dodge chance, represented as a percentage.
    private int dodgeChance;

    // The player's inventory, used to store and manage items such as weapons and armor.
    private Inventory inventory;

    // The amount of gold (gems) the player has, used as in-game currency.
    private int gold;

    private int x;
    private int y;

    /**
     * Constructs a Player object with the specified attributes.
     *
     * @param name           the name of the player character.
     * @param health         the initial health of the player.
     * @param stamina        the initial stamina of the player.
     * @param damage         the base damage dealt by the player.
     * @param defense        the base defense points of the player.
     * @param criticalChance the critical hit chance of the player, represented as a percentage.
     * @param dodgeChance    the dodge chance of the player, represented as a percentage.
     */
    public Player(String name, int health, int stamina, int damage, int defense, int criticalChance, int dodgeChance) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.stamina = stamina;
        this.maxStamina = stamina;
        this.damage = damage;
        this.defense = defense;
        this.criticalChance = criticalChance;
        this.dodgeChance = dodgeChance;
        this.inventory = new Inventory();
        this.gold = 200; // Initial amount of gold (gems) for the player.
        this.x = 0;
        this.y = 0;
    }

    /**
     * Player recovers health and stamina during rest. Cannot exceed the maximum value for both.
     * This method simulates resting at home or a safe place.
     */
    public void rest() {
        this.health += 5; // Recover 5 health points.
        if (this.health > maxHealth) this.health = maxHealth; // Ensure health does not exceed max health.
        this.stamina += 120; // Recover 120 stamina points.
        if (this.stamina > maxStamina) this.stamina = maxStamina; // Ensure stamina does not exceed max stamina.
    }

    /**
     * Player heals at hospital-clinic and recovers health points. Cannot exceed the maximum value.
     *
     * @param amount the amount of health to be recovered.
     */
    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) this.health = maxHealth; // Ensure health does not exceed max health.
    }

    /**
     * Player restores stamina at hospital-clinic and recovers stamina points. Cannot exceed the maximum value.
     *
     * @param amount the amount of stamina to be recovered.
     */
    public void restoreStamina(int amount) {
        this.stamina += amount;
        if (this.stamina > maxStamina) this.stamina = maxStamina; // Ensure stamina does not exceed max stamina.
    }

    /**
     * Reduces the player's stamina by a specified amount.
     * This method is used when the player performs actions that consume stamina, such as attacking or escaping.
     *
     * @param amount the amount of stamina to be reduced.
     */
    public void reduceStamina(int amount) {
        this.stamina -= amount;
    }

    /**
     * Adds the specified amount of gold to the player's current total.
     * This method is used to reward the player with gold for various achievements, such as defeating monsters.
     *
     * @param amount the amount of gold to be added.
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Calculates the player's total attack damage, including base damage and any equipped weapon's damage.
     * This method combines the player's base damage with the damage of the equipped weapon, if any.
     *
     * @return the total attack damage dealt by the player.
     */
    public int attack() {
        int totalDamage = this.damage;
        if (inventory.getEquippedWeapon() != null) {
            totalDamage += inventory.getEquippedWeapon().getDamage(); // Add equipped weapon's damage to base damage.
        }
        return totalDamage;
    }

    /**
     * Calculates and returns the player's total defense, including base defense and any equipped armor's defense.
     * This method combines the player's base defense with the defense of the equipped armor, if any.
     *
     * @return the total defense points of the player.
     */
    public int getDefense() {
        int totalDefense = this.defense;
        if (inventory.getEquippedArmor() != null) {
            totalDefense += inventory.getEquippedArmor().getDefense(); // Add equipped armor's defense to base defense.
        }
        return totalDefense;
    }

    /**
     * Reduces the player's health by a specified amount.
     * This method is used when the player takes damage during combat.
     *
     * @param amount the amount of health to be reduced.
     */
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    /**
     * Player spends gold (gems) for purchasing items or services.
     *
     * @param amount the amount of gold to be spent.
     * @return true if the player has enough gold and the transaction is successful, false otherwise.
     */
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true; // Transaction successful.
        } else {
            return false; // Not enough gold.
        }
    }

    /**
     * Returns the player's inventory, which contains items such as weapons and armor.
     *
     * @return the player's inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    // Getters and setters for player's attributes

    /**
     * Returns the name of the player character.
     *
     * @return the name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current health of the player.
     *
     * @return the current health of the player.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the maximum health of the player.
     *
     * @return the maximum health of the player.
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Returns the current stamina of the player.
     *
     * @return the current stamina of the player.
     */
    public int getStamina() {
        return stamina;
    }

    /**
     * Returns the maximum stamina of the player.
     *
     * @return the maximum stamina of the player.
     */
    public int getMaxStamina() {
        return maxStamina;
    }

    /**
     * Returns the current amount of gold the player has.
     *
     * @return the current amount of gold.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Returns the player's critical hit chance, represented as a percentage.
     *
     * @return the player's critical hit chance.
     */
    public int getCriticalChance() {
        return criticalChance;
    }

    /**
     * Returns the player's dodge chance, represented as a percentage.
     *
     * @return the player's dodge chance.
     */
    public int getDodgeChance() {
        return dodgeChance;
    }

    /**
     * This method is not used in the current context but is required by the {@link ICombatObject} interface.
     *
     * @return 0 as the default x-coordinate position.
     */
    @Override
    public int getX() {
        return x; // Not used in this context
    }

    /**
     * This method is not used in the current context but is required by the {@link ICombatObject} interface.
     *
     * @return 0 as the default y-coordinate position.
     */
    @Override
    public int getY() {
        return y; // Not used in this context
    }

    /**
     * This method is not used in the current context but is required by the {@link ICombatObject} interface.
     * It allows the player to move to a new position based on the specified offset.
     *
     * @param offsetX the offset to move the player in the x direction.
     * @param offsetY the offset to move the player in the y direction.
     */
    @Override
    public void move(int offsetX, int offsetY) {
        this.x += offsetX;
        this.y += offsetY;
    }

    /**
     * Returns the total attack damage of the player, including base damage and any equipped weapon's damage.
     * This method overrides the default getDamage() method in the {@link ICombatObject} interface.
     *
     * @return the total damage dealt by the player.
     */
    @Override
    public int getDamage() {
        return attack();
    }
}
