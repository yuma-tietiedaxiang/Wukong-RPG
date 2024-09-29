package comp2120.ass3;

/**
 * Represents the player in the game, extending the {@link ICombatObject} interface,
 * indicating that the player can participate in combat scenarios.
 *
 * A player has the ability to fight, move, and take damage like other combat objects in the game,
 * but also possesses additional attributes and behaviors that are unique to the player character.
 *
 * The {@link IPlayer} interface adds a new method {@code getGold()} to retrieve the amount of gold
 * the player currently has, representing the player's currency or resources that can be used
 * for various in-game transactions like buying items or healing.
 *
 * This interface can be implemented by various player character classes, allowing for different
 * player types with potentially different combat abilities and resource management strategies.
 *
 * The game engine can utilize this interface to manage the player’s interactions, combat behavior,
 * and resource management in a consistent manner.
 *
 * @see ICombatObject
 * @see Player
 * @see Monster
 * @see GameEngine
 * @see IMonster
 * @see Inventory
 *
 * @author Jun Zhu
 * @author Fan Yu
 */
public interface IPlayer extends ICombatObject {
    /**
     * Retrieves the amount of gold the player currently possesses.
     * Gold can be used for purchasing items, healing, and other in-game activities.
     *
     * @return the current amount of gold the player has.
     */
    int getGold();
}
