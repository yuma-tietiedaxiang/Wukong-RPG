package comp2120.ass3;
public class Player {
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
     * Reduces the player's stamina by a specified amount. This method deducts the specified
     * value from the player's current stamina.
     *
     * @param amount The amount of stamina to reduce
     * @author Yu Ma
     */
    public void reduceStamina(int amount) {
        this.stamina -= amount;
    }


    /**
     * Adds the specified amount of gold to the player's current total.
     *
     * @param amount The amount of gold to add
     * @author Yu Ma
     */
    public void addGold(int amount) {
        // Add the specified amount of gold to the player's total gold
        gold += amount;
    }

    /**
     * Calculates the player's total attack damage, including base damage and any equipped weapon's damage.
     *
     * @return The total attack damage
     * @author Yu Ma
     */
    public int attack() {
        // Initialize total damage with the player's base damage
        int totalDamage = this.damage;

        // If a weapon is equipped, add its damage to the total
        //TODO 存货管理
//        if (inventory.getEquippedWeapon() != null) {
//            totalDamage += inventory.getEquippedWeapon().getDamage();
//        }

        return totalDamage;
    }

    /**
     * Calculates and returns the player's total defense.
     *
     * @return The total defense value of the player
     * @author Yu Ma
     */
    public int getDefense() {
        // Set the total defense to the player's base defense
        int totalDefense = this.defense;

        // If the player has equipped armor, add its defense value to the total defense
        //TODO 存货管理
//        if (inventory.getEquippedArmor() != null) {
//            totalDefense += inventory.getEquippedArmor().getDefense();
//        }

        // Return the calculated total defense
        return totalDefense;
    }

    /**
     * Reduces the player's health by a specified amount.      *
     * @param amount The amount of health to reduce
     * @author Yu Ma
     */
    public void reduceHealth(int amount) {
        // Subtract the specified amount from the player's current health
        this.health -= amount;
    }





    /**
     * Player recover health and stamina during rest. Can not exceed the max value.
     * Player rest when current player's location is the same as home 'H'.
     * @author Yu Ma
     */
//    public void rest() {//TODO
//        this.health += 5;
//        if (this.health > maxHealth) this.health = maxHealth;
//
//        this.stamina += 120;
//        if (this.stamina > maxStamina) this.stamina = maxStamina;
//    }

    /**
     * Player heal at hospital-clinic and recover health point. Can not exceed the max value.
     * @author Yu Ma
     */
//    public void heal(int amount) {//TODO
//        this.health += amount;
//        if (this.health > maxHealth) this.health = maxHealth;
//    }


    /**
     * Player restore stamina at hospital-clinic and recover stamina point. Can not exceed the max value.
     * @author Yu Ma
     */
//    public void restoreStamina(int amount) {//TODO
//        this.stamina += amount;
//        if (this.stamina > maxStamina) this.stamina = maxStamina;
//    }

    /**
     * Player spend gems for payment.
     * @author Yingxuan Tang
     */
    public boolean spendGold(int amount) {//TODO
        if (gold >= amount) {
            gold -= amount;
            return true;
        } else {
            return false;
        }
    }

    /**
    * Get the items what the player have.
    * @author Yingxuan Tang
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
    public int getGold() { return gold; }
    public int getCriticalChance() {
        return criticalChance;
    }
    public int getDodgeChance() {
        return dodgeChance;
    }

}
