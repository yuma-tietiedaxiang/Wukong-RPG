package comp2120.ass3;

public class Armor extends Item {
    private int defense;

    public Armor(String name, int price, int defense) {
        super(name, price, "armor");
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }
}
