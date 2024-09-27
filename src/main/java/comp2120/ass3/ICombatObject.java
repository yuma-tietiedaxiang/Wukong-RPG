package comp2120.ass3;

/**
 * Represent an object which can fight in combats
 */
public interface ICombatObject {
    int getX();
    int getY();
    void move(int offsetX, int offsetY);
    int getMaxHealth();
    int getHealth();
    void reduceHealth(int amount);
    int attack();
    int getDamage();
    int getDefense();
}
