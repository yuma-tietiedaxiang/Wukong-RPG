package comp2120.ass3;

/**
 * Represent an object which can fight in combats
 * @author Fan Yu
 */
public interface ICombatObject {
    /**
     * Get the x component of position (for issue #5).
     * @author Fan Yu
     * @return The x component of position
     */
    int getX();
    /**
     * Get the y component of position (for issue #5).
     * @author Fan Yu
     * @return The y component of position
     */
    int getY();

    /**
     * Move this object by offsetX horizontally and by offsetY vertically (for issue #5).
     * Should do nothing if parameters are invalid.
     * @author Fan Yu
     * @param offsetX The movement distance in horizontal direction
     * @param offsetY The movement distance in vertical direction
     */
    void move(int offsetX, int offsetY);

    /**
     * Get the health of this object (for issue #6).
     * @author Fan Yu
     * @return The health of this object
     */
    int getHealth();

    /**
     * Reduce the health of this object by amount (for issue #6).
     * The health of this object should never be negative.
     * @author Fan Yu
     * @param amount The amount of health to be reduced
     */
    void reduceHealth(int amount);

    /**
     * Attack an enemy.
     * @author Fan Yu
     * @return The total damage of the attacking
     */
    int attack();
}
