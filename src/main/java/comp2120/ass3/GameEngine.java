package comp2120.ass3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The GameEngine class is responsible for handling the game's mechanics such as loading configurations,
 * initializing the map, moving the player, and managing monsters.
 *
 * @author Yu Ma
 */
public class GameEngine {
    private Map<String, MonsterConfig> monsterConfigs; // Monster configurations
    private LevelConfig currentLevelConfig; // Current level configuration
    private char[][] map;
    private int playerX;
    private int playerY;

    /**
     * Constructs a GameEngine and loads the map and monster configurations.
     *
     * @param mapConfigFile The file path to the map configuration file
     * @param monsterConfigFile The file path to the monster configuration file
     */
    public GameEngine(String mapConfigFile, String monsterConfigFile) {
        loadMonsterConfig(monsterConfigFile);  // Load monster configurations
        loadMapConfig(mapConfigFile);          // Load map configurations
    }

    /**
     * Loads monster configurations from the specified file.
     *
     * @param monsterConfigFile The file path to the monster configuration file
     */
    private void loadMonsterConfig(String monsterConfigFile) {
        try {
            Gson gson = new Gson();
            monsterConfigs = gson.fromJson(new FileReader(monsterConfigFile), new TypeToken<Map<String, MonsterConfig>>(){}.getType());
        } catch (IOException e) {
            System.out.println("Failed to load monster configuration file.");
            e.printStackTrace();
        }
    }

    /**
     * Loads map configurations from the specified file.
     *
     * @param mapConfigFile The file path to the map configuration file
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
     * Initializes the map using the given MapConfig object.
     *
     * @param mapConfig The map configuration object
     */
    private void initializeMap(MapConfig mapConfig) {
        int width = mapConfig.getWidth();
        int height = mapConfig.getHeight();
        map = new char[height][width];

        // Initialize the map layout
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = mapConfig.getLayout()[i][j];
            }
        }

        // Set player's starting position
        playerX = mapConfig.getPlayerStart().get("x");
        playerY = mapConfig.getPlayerStart().get("y");
    }

    /**
     * Displays the current map, including the player's position.
     */
    public void displayMap() {
        // Iterate through the map and print each cell
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("Y"); // Representing the player
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
    }

    /**
     * Generates a Monster object based on the given monster name.
     *
     * @param name The name of the monster to generate
     * @return The generated Monster object, or null if the monster configuration is not found
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

    /**
     * Loads a level configuration from the specified file.
     *
     * @param levelConfigFile The file path to the level configuration file
     */
    public void loadLevelConfig(String levelConfigFile) {
        try {
            Gson gson = new Gson();
            currentLevelConfig = gson.fromJson(new FileReader(levelConfigFile), LevelConfig.class);
        } catch (IOException e) {
            System.out.println("Failed to load level configuration.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes monsters for the current level based on the configuration.
     *
     * @return A list of initialized monsters
     */
    public List<Monster> initializeMonsters() {
        List<Monster> monsters = new ArrayList<>();
        for (MonsterLevelConfig mlc : currentLevelConfig.getMonsters()) {
            Monster monster = generateMonster(mlc.getName());
            monsters.add(monster);
            map[mlc.getInitialPosition().get("y")][mlc.getInitialPosition().get("x")] = 'M'; // Place monster on the map
        }
        return monsters;
    }

    /**
     * Moves the player on the map based on the specified direction.
     * Valid directions are W (up), A (left), S (down), D (right).
     *
     * @param direction The direction in which to move the player
     */
    public void movePlayer(String direction) {
        int newX = playerX;
        int newY = playerY;

        // Move the player based on the direction
        switch (direction.toUpperCase()) {
            case "W":
                newY--;
                break;
            case "A":
                newX--;
                break;
            case "S":
                newY++;
                break;
            case "D":
                newX++;
                break;
        }

        // Check if the new position is within bounds and is empty
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length && map[newY][newX] == ' ') {
            playerX = newX;
            playerY = newY;
        } else {
            System.out.println("Cannot move there.");
        }
    }

    /**
     * Moves the monsters on the map. This method should handle random monster movement
     * and interaction with the player if they encounter each other.
     *
     * @param monsters A list of monsters currently on the map
     * @param monsterPositions The positions of the monsters
     * @param playerX The player's X-coordinate
     * @param playerY The player's Y-coordinate
     */
    public void moveMonsters(List<Monster> monsters, List<int[]> monsterPositions, int playerX, int playerY) {
        // Copy the existing logic for moving monsters randomly
    }
}

/**
 * A configuration class representing the map's structure and initial settings.
 *
 * @author Yu Ma
 */
class MapConfig {
    private int width;
    private int height;
    private char[][] layout;
    private Map<String, Integer> playerStart;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char[][] getLayout() {
        return layout;
    }

    public Map<String, Integer> getPlayerStart() {
        return playerStart;
    }
}

/**
 * A configuration class representing a monster's attributes.
 *
 * @author Yu Ma
 */
class MonsterConfig {
    private String name;
    private int health;
    private int damage;
    private int defense;
    private int criticalChance;
    private int dodgeChance;
    private int goldReward;

    // Getters
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getDamage() { return damage; }
    public int getDefense() { return defense; }
    public int getCriticalChance() { return criticalChance; }
    public int getDodgeChance() { return dodgeChance; }
    public int getGoldReward() { return goldReward; }
}

/**
 * A configuration class representing the level settings, including monsters and their positions.
 *
 * @author Yu Ma
 */
class LevelConfig {
    private int levelNumber;
    private List<MonsterLevelConfig> monsters;

    public int getLevelNumber() {
        return levelNumber;
    }

    public List<MonsterLevelConfig> getMonsters() {
        return monsters;
    }
}

/**
 * A class representing a monster's configuration within a specific level, including its starting position.
 *
 * @author Yu Ma
 */
class MonsterLevelConfig {
    private String name;
    private Map<String, Integer> initialPosition;

    public String getName() {
        return name;
    }

    public Map<String, Integer> getInitialPosition() {
        return initialPosition;
    }
}
