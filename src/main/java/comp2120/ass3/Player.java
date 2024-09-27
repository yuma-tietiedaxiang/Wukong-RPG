package comp2120.ass3;

public class Player implements IPlayer {
    private String name;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private int damage;
    private int defense;
    private int criticalChance;
    private int dodgeChance;
    private Inventory inventory;
    private int gold;

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
        this.gold = 200; // default gem number
    }

    /**
     * Player recovers health and stamina during rest. Cannot exceed the max value.
     */
    public void rest() {
        this.health += 5;
        if (this.health > maxHealth) this.health = maxHealth;
        this.stamina += 120;
        if (this.stamina > maxStamina) this.stamina = maxStamina;
    }

    /**
     * Player heals at hospital-clinic and recovers health points. Cannot exceed the max value.
     */
    public void heal(int amount) {
        this.health += amount;
        if (this.health > maxHealth) this.health = maxHealth;
    }

    /**
     * Player restores stamina at hospital-clinic and recovers stamina points. Cannot exceed the max value.
     */
    public void restoreStamina(int amount) {
        this.stamina += amount;
        if (this.stamina > maxStamina) this.stamina = maxStamina;
    }

    /**
     * Reduces the player's stamina by a specified amount.
     */
    public void reduceStamina(int amount) {
        this.stamina -= amount;
    }

    /**
     * Adds the specified amount of gold to the player's current total.
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /**
     * Calculates the player's total attack damage, including base damage and any equipped weapon's damage.
     */
    public int attack() {
        int totalDamage = this.damage;
        if (inventory.getEquippedWeapon() != null) {
            totalDamage += inventory.getEquippedWeapon().getDamage();
        }
        return totalDamage;
    }

    /**
     * Calculates and returns the player's total defense.
     */
    public int getDefense() {
        int totalDefense = this.defense;
        if (inventory.getEquippedArmor() != null) {
            totalDefense += inventory.getEquippedArmor().getDefense();
        }
        return totalDefense;
    }

    /**
     * Reduces the player's health by a specified amount.
     */
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    /**
     * Player spends gems for payment.
     */
    public boolean spendGold(int amount) {
        if (gold >= amount) {
            gold -= amount;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the items that the player has.
     */
    public Inventory getInventory() {
        return inventory;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }

    public int getGold() {
        return gold;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    @Override
    public int getX() {
        // Not used in this context
        return 0;
    }

    @Override
    public int getY() {
        // Not used in this context
        return 0;
    }

    @Override
    public void move(int offsetX, int offsetY) {
        // Not used in this context
    }

    @Override
    public int getDamage() {
        return attack();
    }
}
