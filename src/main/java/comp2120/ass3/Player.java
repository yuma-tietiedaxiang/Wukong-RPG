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

}
