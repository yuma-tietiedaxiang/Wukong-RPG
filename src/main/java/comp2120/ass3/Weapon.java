package comp2120.ass3;

public class Weapon extends Item {
    private int damage;

    public Weapon(String name, int price, int damage) {
        super(name, price, "weapon");
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
