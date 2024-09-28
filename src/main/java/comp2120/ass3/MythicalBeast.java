package comp2120.ass3;

public class MythicalBeast {
    private String name;
    private int attackBonus;
    private int defenseBonus;

    public MythicalBeast(String name, int attackBonus, int defenseBonus) {
        this.name = name;
        this.attackBonus = attackBonus;
        this.defenseBonus = defenseBonus;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }
}
