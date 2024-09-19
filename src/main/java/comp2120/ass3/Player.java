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
     * @author Yu Ma
     */
//    public boolean spendGold(int amount) {//TODO
//        if (gold >= amount) {
//            gold -= amount;
//            return true;
//        } else {
//            return false;
//        }
//    }



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
