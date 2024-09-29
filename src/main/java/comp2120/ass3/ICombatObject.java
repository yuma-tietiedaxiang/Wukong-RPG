package comp2120.ass3;

/**
 * Represents an object that can participate in combat scenarios.
 * This interface defines the basic attributes and actions for any combat-ready object,
 * such as a player or a monster, including the ability to move, attack, and defend.
 *
 * Classes that implement this interface are expected to provide specific implementations
 * for each of these methods based on their unique characteristics.
 *
 * Methods include getting the object's position, health, and combat attributes,
 * as well as actions like moving and taking damage.
 *
 * This interface serves as a common blueprint for all combat entities in the game,
 * ensuring a consistent API for combat-related interactions.
 *
 * Implementing classes might include {@link Player} and {@link Monster}.
 *
 * @author Jun Zhu
 * @author Fan Yu
 */
public interface ICombatObject {

    /**
     * Returns the X-coordinate of the object on the map.
     *
     * @return the X-coordinate of the object.
     */
    int getX();

    /**
     * Returns the Y-coordinate of the object on the map.
     *
     * @return the Y-coordinate of the object.
     */
    int getY();

    /**
     * Moves the object by the specified offset in the X and Y directions.
     *
     * @param offsetX the offset in the X direction to move the object.
     * @param offsetY the offset in the Y direction to move the object.
     */
    void move(int offsetX, int offsetY);

    /**
     * Returns the maximum health of the object.
     *
     * @return the maximum health value.
     */
    int getMaxHealth();

    /**
     * Returns the current health of the object.
     *
     * @return the current health value.
     */
    int getHealth();

    /**
     * Reduces the health of the object by the specified amount.
     * The object's health cannot go below zero.
     *
     * @param amount the amount to reduce the health by.
     */
    void reduceHealth(int amount);

    /**
     * Performs an attack and returns the base attack damage of the object.
     *
     * @return the base attack damage value.
     */
    int attack();

    /**
     * Returns the damage value of the object, including any equipped weapons.
     *
     * @return the total damage value.
     */
    int getDamage();

    /**
     * Returns the defense value of the object, including any equipped armor.
     *
     * @return the total defense value.
     */
    int getDefense();
}
