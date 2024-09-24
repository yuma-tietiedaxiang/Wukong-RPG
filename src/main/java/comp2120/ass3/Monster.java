package comp2120.ass3;

public class Monster implements IMonster{
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int criticalChance;
    private int dodgeChance;
    private int goldReward;
    private int monsterX;
    private int monsterY;

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

    @Override
    public int getDamage() {
        return 0;
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

    @Override
    public int getX() {
        return monsterX;
    }

    @Override
    public int getY() {
        return monsterY;
    }

    @Override
    public void move(int offsetX, int offsetY) {
        monsterX += offsetX;
        monsterY += offsetY;
    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    //getter and setter
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

    public void setMonsterX(int monsterX) {
        this.monsterX = monsterX;
    }
    public void setMonsterY(int monsterY) {
        this.monsterY = monsterY;
    }

}
