package comp2120.ass3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The GameEngine class is responsible for handling the game's mechanics such as loading configurations,
 * initializing the map, moving the player, and managing monsters. It reads configurations from JSON files
 * for the map, monsters, and levels, and provides methods to initialize and manage these game elements.
 * This class serves as the core engine of the game, providing functionality for setting up and controlling
 * the game environment.
 *
 * @author Yu Ma
 * @author Jun Zhu
 */
public class GameEngine {
    private Map<String, MonsterConfig> monsterConfigs; // Stores monster configurations

    private char[][] map; // 2D array representing the game map
    private int playerX; // Player's initial X-coordinate on the map
    private int playerY; // Player's initial Y-coordinate on the map
    private List<Monster> currentMonsters = new ArrayList<>(); // List of monsters in the current level

    /**
     * Constructs a GameEngine instance and loads the map, monster, and level configurations from the specified files.
     *
     * @param mapConfigFile the file path to the map configuration JSON file
     * @param monsterConfigFile the file path to the monster configuration JSON file
     * @param levelConfigFile the file path to the level configuration JSON file
     */
    public GameEngine(String mapConfigFile, String monsterConfigFile, String levelConfigFile) {
        loadMonsterConfig(monsterConfigFile);  // Load monster configurations
        loadMapConfig(mapConfigFile);          // Load map configurations

    }

    /**
     * Loads monster configurations from the specified JSON file. The configurations define the attributes of different
     * types of monsters such as health, damage, defense, and other combat-related stats.
     *
     * @param monsterConfigFile the file path to the monster configuration JSON file
     */
    private void loadMonsterConfig(String monsterConfigFile) {
        try {
            Gson gson = new Gson();
            monsterConfigs = gson.fromJson(new FileReader(monsterConfigFile), new TypeToken<Map<String, MonsterConfig>>() {
            }.getType());
        } catch (IOException e) {
            System.out.println("Failed to load monster configuration file.");
            e.printStackTrace();
        }
    }

    /**
     * Loads map configurations from the specified JSON file. The map configuration includes the size of the map,
     * the layout of the terrain, and the player's starting position.
     *
     * @param mapConfigFile the file path to the map configuration JSON file
     */
    private void loadMapConfig(String mapConfigFile) {
        try {
            Gson gson = new Gson();
            MapConfig mapConfig = gson.fromJson(new FileReader(mapConfigFile), MapConfig.class);
            initializeMap(mapConfig);
        } catch (IOException e) {
            System.out.println("Failed to load map configuration file.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the map based on the provided MapConfig object. This method sets up the 2D map array and the player's
     * starting position using the data from the map configuration.
     *
     * @param mapConfig the MapConfig object containing the map's dimensions, layout, and player's starting position
     */
    private void initializeMap(MapConfig mapConfig) {
        int width = mapConfig.getWidth();
        int height = mapConfig.getHeight();
        map = new char[height][width];

        // Copy the layout from the configuration to the map array
        for (int i = 0; i < height; i++) {
            System.arraycopy(mapConfig.getLayout()[i], 0, map[i], 0, width);
        }

        // Set the player's initial position on the map
        playerX = mapConfig.getPlayerStart().get("x");
        playerY = mapConfig.getPlayerStart().get("y");
    }



    public Map<String, MonsterConfig> getMonsterConfigs() {
        return monsterConfigs;
    }


    /**
     * Generates a Monster object based on the specified monster name. If the monster's configuration is not found,
     * this method returns null.
     *
     * @param name the name of the monster to generate
     * @return a Monster object with attributes based on the configuration, or null if not found
     */
    public Monster generateMonster(String name) {
        MonsterConfig config = monsterConfigs.get(name);
        if (config != null) {
            return new Monster(
                    config.getName(),
                    config.getHealth(),
                    config.getDamage(),
                    config.getDefense(),
                    config.getCriticalChance(),
                    config.getDodgeChance(),
                    config.getGoldReward()
            );
        } else {
            System.out.println("Monster configuration not found for: " + name);
            return null;
        }
    }

    // Getter methods

    /**
     * Returns the current game map.
     *
     * @return a 2D character array representing the map
     */
    public char[][] getMap() {
        return this.map;
    }

    /**
     * Returns the player's initial X-coordinate on the map.
     *
     * @return the player's initial X-coordinate
     */
    public int getPlayerX() {
        return this.playerX;
    }

    /**
     * Returns the player's initial Y-coordinate on the map.
     *
     * @return the player's initial Y-coordinate
     */
    public int getPlayerY() {
        return this.playerY;
    }

    /**
     * Returns the list of monsters currently present in the game.
     *
     * @return a list of Monster objects representing the current monsters
     */
    public List<Monster> getCurrentMonsters() {
        return currentMonsters;
    }
}

/**
 * The MapConfig class represents the configuration of the game map, including the map's dimensions, layout,
 * and the player's starting position. It is used by the GameEngine class to initialize the game map.
 */
class MapConfig {
    private int width; // Width of the map
    private int height; // Height of the map
    private char[][] layout; // 2D array representing the map layout
    private Map<String, Integer> playerStart; // Map storing the player's starting X and Y coordinates

    // Getters

    /**
     * Returns the width of the map.
     *
     * @return the width of the map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map.
     *
     * @return the height of the map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the layout of the map.
     *
     * @return a 2D character array representing the layout of the map
     */
    public char[][] getLayout() {
        return layout;
    }

    /**
     * Returns the player's starting position as a map containing X and Y coordinates.
     *
     * @return a map with keys "x" and "y" representing the player's starting coordinates
     */
    public Map<String, Integer> getPlayerStart() {
        return playerStart;
    }
}

/**
 * The MonsterConfig class represents the configuration of a monster's attributes, including its health,
 * damage, defense, critical chance, dodge chance, and the reward for defeating it. This configuration is
 * used by the GameEngine to create Monster objects with the appropriate attributes.
 */
class MonsterConfig {
    private String name; // Name of the monster
    private int health; // Health points of the monster
    private int damage; // Damage points the monster can deal
    private int defense; // Defense points of the monster
    private int criticalChance; // Critical hit chance of the monster (percentage)
    private int dodgeChance; // Dodge chance of the monster (percentage)
    private int goldReward; // Reward in gold for defeating the monster

    // Getters

    /**
     * Returns the name of the monster.
     *
     * @return the name of the monster
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the health points of the monster.
     *
     * @return the health points of the monster
     */
    public int getHealth() {
        return health;
    }

    /**
     * Returns the damage points the monster can deal.
     *
     * @return the damage points the monster can deal
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the defense points of the monster.
     *
     * @return the defense points of the monster
     */
    public int getDefense() {
        return defense;
    }

    /**
     * Returns the critical hit chance of the monster (percentage).
     *
     * @return the critical hit chance of the monster
     */
    public int getCriticalChance() {
        return criticalChance;
    }

    /**
     * Returns the dodge chance of the monster (percentage).
     *
     * @return the dodge chance of the monster
     */
    public int getDodgeChance() {
        return dodgeChance;
    }

    /**
     * Returns the reward in gold for defeating the monster.
     *
     * @return the reward in gold for defeating the monster
     */
    public int getGoldReward() {
        return goldReward;
    }
}

