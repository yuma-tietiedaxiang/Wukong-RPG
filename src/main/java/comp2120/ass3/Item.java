package comp2120.ass3;

public class Item {
    private String name;
    private int price;
    private String type; // weapon and armor

    public Item(String name, int price, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
    }

    // Getters

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
