package comp2120.ass3;

/**
 * Represents a monster, which is an enemy of the player in the game.
 * This interface extends the {@link ICombatObject} interface,
 * indicating that a monster can participate in combat scenarios with the player
 * and other entities implementing the {@link ICombatObject} interface.
 *
 * A monster has basic combat capabilities, such as attacking, moving,
 * and taking damage, as defined in the {@link ICombatObject} interface.
 * Additional attributes and behaviors specific to monsters can be added
 * in classes that implement this interface.
 *
 * Implementing classes might include specific monster types like {@link Monster},
 * providing concrete details about their health, attack damage, defense,
 * and other combat-related attributes.
 *
 * By using this interface, the game engine can manage all types of monsters
 * in a uniform way, regardless of their specific implementations.
 *
 * This interface serves as a marker for all monster entities in the game.
 *
 * @see ICombatObject
 * @see Monster
 * @see Player
 *
 * @author Jun Zhu
 * @author Fan Yu
 */
public interface IMonster extends ICombatObject {
    // This interface inherits all methods from ICombatObject,
    // making it a specialized type of combat object.
}
