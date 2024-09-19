package comp2120.ass3;

public class Monster {
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int criticalChance;
    private int dodgeChance;
    private int goldReward;

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
     * Returns the monstor's base attack damage.
     *
     * @return The player's base attack damage
     * @author Yu Ma
     */
    public int attack() {
        // Return the player's base damage
        return this.damage;
    }


    /**
     * Reduces the monstor's health by a specified amount.     *
     * @param amount The amount of health to reduce
     * @author Yu Ma
     */
    public void reduceHealth(int amount) {
        // Subtract the specified amount from the player's current health
        this.health -= amount;
    }


    // Getters
    public String getName() {
        return name;
    }
    public int getHealth() {
        return health;
    }
    public int getGoldReward() {
        return goldReward;
    }
    public int getDefense() {return defense;}
    public int getDodgeChance() {
        return dodgeChance;
    }
    public int getCriticalChance() {
        return criticalChance;
    }

}
