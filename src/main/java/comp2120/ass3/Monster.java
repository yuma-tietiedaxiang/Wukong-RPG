package comp2120.ass3;

public class Monster implements IMonster {
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
     * Returns the monster's base attack damage.
     */
    public int attack() {
        return this.damage;
    }

    /**
     * Reduces the monster's health by a specified amount.
     */
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getGoldReward() {
        return goldReward;
    }

    public int getDefense() {
        return defense;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public void setMonsterX(int x) {
        this.monsterX = x;
    }

    public void setMonsterY(int y) {
        this.monsterY = y;
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
    public int getDamage() {
        return damage;
    }

    @Override
    public int getMaxHealth() {
        return health;
    }
}
