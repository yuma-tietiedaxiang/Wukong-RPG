package comp2120.ass3;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Item> items;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public Inventory() {
        items = new ArrayList<>();
        equippedWeapon = null;
        equippedArmor = null;
    }

}
