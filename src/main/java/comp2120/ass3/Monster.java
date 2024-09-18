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

}
