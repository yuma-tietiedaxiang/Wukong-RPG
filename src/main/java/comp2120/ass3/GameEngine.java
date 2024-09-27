package comp2120.ass3;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * The GameEngine class is responsible for handling the game's mechanics such as loading configurations,
 * initializing the map, moving the player, and managing monsters.
 */
public class GameEngine {
    private Map<String, MonsterConfig> monsterConfigs; // Monster configurations
    private List<LevelConfig> levels; // List of levels
    private LevelConfig currentLevelConfig; // Current level configuration
    private char[][] map;
    private int playerX;
    private int playerY;
    private List<Monster> currentMonsters = new ArrayList<>();

    /**
     * Constructs a GameEngine and loads the map and monster configurations.
     */
    public GameEngine(String mapConfigFile, String monsterConfigFile, String levelConfigFile) {
        loadMonsterConfig(monsterConfigFile);  // Load monster configurations
        loadMapConfig(mapConfigFile);          // Load map configurations
        loadLevelConfig(levelConfigFile);      // Load level configurations
    }

    /**
     * Loads monster configurations from the specified file.
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
     * Loads map configurations from the specified file.
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
     */
    private void initializeMap(MapConfig mapConfig) {
        int width = mapConfig.getWidth();
        int height = mapConfig.getHeight();
        map = new char[height][width];

        // Initialize the map layout
        for (int i = 0; i < height; i++) {
            System.arraycopy(mapConfig.getLayout()[i], 0, map[i], 0, width);
        }

        // Set player's starting position
        playerX = mapConfig.getPlayerStart().get("x");
        playerY = mapConfig.getPlayerStart().get("y");
    }

    /**
     * Loads level configurations from the specified file.
     */
    public void loadLevelConfig(String levelConfigFile) {
        try {
            Gson gson = new Gson();
            LevelData levelData = gson.fromJson(new FileReader(levelConfigFile), LevelData.class);
            levels = levelData.getLevels();
            // For simplicity, we'll use the first level
            if (levels != null && !levels.isEmpty()) {
                currentLevelConfig = levels.get(0);
            }
        } catch (IOException e) {
            System.out.println("Failed to load level configuration.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes monsters for the current level based on the configuration.
     */
    public List<Monster> initializeMonsters() {
        currentMonsters.clear();
        if (currentLevelConfig != null) {
            for (MonsterLevelConfig mlc : currentLevelConfig.getMonsters()) {
                Monster monster = generateMonster(mlc.getName());
                if (monster != null) {
                    monster.setMonsterX(mlc.getPosition().get("x"));
                    monster.setMonsterY(mlc.getPosition().get("y"));
                    currentMonsters.add(monster);
                }
            }
        }
        return currentMonsters;
    }

    /**
     * Generates a Monster object based on the given monster name.
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
    public char[][] getMap() {
        return this.map;
    }

    public int getPlayerX() {
        return this.playerX;
    }

    public int getPlayerY() {
        return this.playerY;
    }

    public List<Monster> getCurrentMonsters() {
        return currentMonsters;
    }
}

/**
 * A configuration class representing the map's structure and initial settings.
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
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getCriticalChance() {
        return criticalChance;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }

    public int getGoldReward() {
        return goldReward;
    }
}

/**
 * A class representing a monster's configuration within a specific level, including its starting position.
 */
class MonsterLevelConfig {
    private String name;
    private Map<String, Integer> position;

    public String getName() {
        return name;
    }

    public Map<String, Integer> getPosition() {
        return position;
    }
}

/**
 * A configuration class representing the level settings, including monsters and their positions.
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
 * A class to hold all level data
 */
class LevelData {
    private List<LevelConfig> levels;

    public List<LevelConfig> getLevels() {
        return levels;
    }
}
