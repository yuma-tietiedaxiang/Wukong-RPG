package comp2120.ass3;

import java.util.*;


/**
 * The Game class handles the main game logic, including initializing the game, managing the player,
 * controlling the game loop, and handling interactions within the game world.
 *
 * <p>This class allows the player to choose a profession, move around the map, and interact with various
 * elements such as shops, clinics, and the battlefield. It also manages the game's main loop,
 * displaying the game state and processing user inputs.</p>
 *
 * @author Jun Zhu
 */
public class Game {
    /**
     * Scanner for reading user inputs.
     */
    private Scanner scanner;

    /**
     * The player character in the game.
     */
    private Player player;

    /**
     * The current wave of enemies in the battlefield.
     */
    private int wave = 1; // Current wave

    /**
     * Random object for generating random events and values.
     */
    private Random random;

    /**
     * A flag to check if the player has already rested.
     */
    private boolean slept = false; // Flag indicating whether the player has rested

    /**
     * The game engine instance responsible for loading configurations and managing the game state.
     */
    private GameEngine gameEngine;

    /**
     * The map representation of the game world.
     */
    private char[][] map;

    /**
     * The player's current X coordinate on the map.
     */
    public static int playerX;

    /**
     * The player's current Y coordinate on the map.
     */
    public static int playerY;

    /**
     * A list to keep track of the monsters currently on the battlefield.
     */
    private List<Monster> currentMonsters;

    /**
     * Constructor to initialize the game, loading configurations and setting up the game engine.
     */
    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();

        // Initialize the game engine with configuration files
        gameEngine = new GameEngine(
                "src/main/java/comp2120/ass3/resources/config/map.json",
                "src/main/java/comp2120/ass3/resources/config/monsterConfig.json",
                "src/main/java/comp2120/ass3/resources/config/levelConfig.json"
        );

        // Initialize the current monsters list
        currentMonsters = new ArrayList<>();
    }

    /**
     * Starts the game by displaying a welcome message, allowing the player to choose a profession,
     * initializing the map, and entering the main game loop.
     */
    public void startGame() {
        System.out.println("Welcome to the game!");
        System.out.println("Your keyboard should now be set to English.");
        System.out.println("Press 'Enter' to continue...");
        scanner.nextLine();
        chooseProfession();
        initializeMap();
        gameLoop();
    }

    /**
     * Allows the player to choose their role or profession in the game.
     * Each profession has different attributes such as health, damage, and defense.
     */
    private void chooseProfession() {
        System.out.println("Please choose your role: ");
        System.out.println("1. Monkey King");
        System.out.println("2. Pigsy");
        System.out.println("3. Sandy");
        System.out.println("4. Tang Monk");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Set player attributes based on chosen profession
        switch (choice) {
            case 1:
                player = new Player("Monkey King", 200, 155, 30, 16, 20, 15);
                break;
            case 2:
                player = new Player("Pigsy", 200, 110, 20, 22, 30, 10);
                break;
            case 3:
                player = new Player("Sandy", 300, 75, 15, 30, 25, 5);
                break;
            case 4:
                player = new Player("Tang Monk", 160, 155, 20, 20, 40, 25);
                break;
            default:
                System.out.println("Invalid selection, defaulting to Monkey King.");
                player = new Player("Monkey King", 200, 155, 30, 16, 20, 15);
                break;
        }
        System.out.println("You chose: " + player.getName());
    }

    /**
     * Initializes the game map by retrieving it from the GameEngine and setting the player's initial position.
     */
    private void initializeMap() {
        map = gameEngine.getMap(); // Get the map from GameEngine
        playerX = gameEngine.getPlayerX(); // Get player's starting X position
        playerY = gameEngine.getPlayerY(); // Get player's starting Y position
    }

    /**
     * The main game loop that continuously updates the game state and processes user input.
     * The loop continues running until the player decides to quit.
     */
    private void gameLoop() {
        boolean isRunning = true;

        while (isRunning) {
            displayMap();
            System.out.println("Please enter command (WASD to move, I for inventory, P for status, Q to quit)");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                isRunning = false;
                System.out.println("Game over, thank you for playing!");
            } else if (input.equals("I")) {
                manageInventory();
            } else if (input.equals("P")) {
                showStatus();
            } else if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D")) {
                movePlayer(input);
                handlePosition();
            } else {
                System.out.println("Invalid input, please re-enter.");
            }
        }
    }

    /**
     * Displays the game map, including the player's position and other interactable elements.
     * It also shows the player's current status such as health, stamina, and gems.
     */
    private void displayMap() {
        // Clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Display the map with player position
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("Y"); // Display player as 'Y'
                } else {
                    System.out.print(map[i][j]); // Display map character
                }
            }
            System.out.println();
        }

        // Display the legend for map symbols
        System.out.println("A: Armor Shop, C: Clinic, G: Gate Between Village & Battlefield, H: Home, P: Pet Shop, W: Weapon Shop, Y: You");

        // Display player's current status
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * Moves the player on the map in the specified direction if the move is valid.
     * Valid moves are within the map boundaries and not blocked by walls.
     *
     * @param direction the direction to move the player ('W' for up, 'A' for left, 'S' for down, 'D' for right)
     */
    private void movePlayer(String direction) {
        int newX = playerX;
        int newY = playerY;
        switch (direction) {
            case "W":
                newY--; // Move up
                break;
            case "A":
                newX--; // Move left
                break;
            case "S":
                newY++; // Move down
                break;
            case "D":
                newX++; // Move right
                break;
        }

        // Check for boundaries and walls
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length) {
            if (map[newY][newX] != '■') {
                // Update player position
                playerX = newX;
                playerY = newY;
            } else {
                System.out.println("Bumped into a wall, cannot move there!");
            }
        } else {
            System.out.println("Cannot move out of boundary!");
        }
    }

    /**
     * Handles interactions based on the player's current position on the map.
     * Different positions correspond to different interactions such as shops or battlefield.
     */
    private void handlePosition() {
        char location = map[playerY][playerX];
        switch (location) {
            case 'H': // Home
                restAtHome();
                break;
            case 'W': // Weapon shop
                weaponShop();
                break;
            case 'A': // Armor shop
                armorShop();
                break;
            case 'P': // Pet shop
                petShop();
                break;
            case 'C': // Clinic
                hospital();
                break;
            case 'G': // Gate to battlefield
                battleField();
                break;
            default:
                // No specific interaction for other positions
                break;
        }
    }

    /**
     * Allows the player to rest at home, recovering health and stamina.
     * This action can only be performed once until the player returns from the battlefield.
     */
    private void restAtHome() {
        if (!slept) {
            System.out.println("You rest at home, recovering stamina and health points.");
            player.rest();
            System.out.println("Current health points: " + player.getHealth());
            System.out.println("Current stamina: " + player.getStamina());
            slept = true;
        } else {
            System.out.println("You have already rested, you cannot rest again.");
        }
        System.out.println("Press 'Enter' to continue...");
        scanner.nextLine();
    }

    /**
     * Allows the player to visit the weapon shop and purchase weapons.
     * The player can choose from a list of available weapons, provided they have enough gems.
     */
    private void weaponShop() {
        System.out.println("You arrive at the weapon shop.");
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(new Weapon("Bamboo Arrow", 70, 25));
        weapons.add(new Weapon("Iron Arrow", 130, 35));
        weapons.add(new Weapon("Fire Arrow", 220, 55));

        boolean inShop = true;
        while (inShop) {
            System.out.println("Your gems: " + player.getGold());
            System.out.println("Please select a weapon to purchase: ");

            int index = 1;
            for (Weapon weapon : weapons) {
                System.out.println(index + ". " + weapon.getName() + " - price: " + weapon.getPrice() + " Gems, weapon damage: " + weapon.getDamage());
                index++;
            }
            System.out.println("0. Leave the shop");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            if (choice == 0) {
                inShop = false;
            } else if (choice > 0 && choice <= weapons.size()) {
                Weapon selectedWeapon = weapons.get(choice - 1);
                if (player.spendGold(selectedWeapon.getPrice())) {
                    player.getInventory().addItem(selectedWeapon);
                    System.out.println("You purchased: " + selectedWeapon.getName());
                } else {
                    System.out.println("Insufficient gems, unable to purchase the weapon.");
                }
            } else {
                System.out.println("Invalid selection, please re-enter.");
            }
        }
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * Allows the player to visit the armor shop and purchase armor.
     * The player can choose from a list of available armor, provided they have enough gems.
     */
    private void armorShop() {
        System.out.println("You arrive at the armor shop.");
        List<Armor> armors = new ArrayList<>();
        armors.add(new Armor("Wooden Shield", 80, 11));
        armors.add(new Armor("Iron Pants", 90, 7));
        armors.add(new Armor("Diamond Armor", 200, 16));

        boolean inShop = true;
        while (inShop) {
            System.out.println("Your gems: " + player.getGold());
            System.out.println("Please select the armor you want to buy: ");

            int index = 1;
            for (Armor armor : armors) {
                System.out.println(index + ". " + armor.getName() + " - price: " + armor.getPrice() + " Gems, defense: " + armor.getDefense());
                index++;
            }
            System.out.println("0. Leave the shop");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            if (choice == 0) {
                inShop = false;
            } else if (choice > 0 && choice <= armors.size()) {
                Armor selectedArmor = armors.get(choice - 1);
                if (player.spendGold(selectedArmor.getPrice())) {
                    player.getInventory().addItem(selectedArmor);
                    System.out.println("You purchased: " + selectedArmor.getName());
                } else {
                    System.out.println("Insufficient gems, unable to purchase the armor.");
                }
            } else {
                System.out.println("Invalid choice, please enter again.");
            }
        }
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * Displays a message indicating that the pet shop is temporarily unavailable.
     * This method is a placeholder for future implementations of pet-related features.
     */
    private void petShop() {
        System.out.println("You arrive at the pet shop.");
        System.out.println("Temporarily unavailable.");
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * Allows the player to visit the clinic and recover health or stamina.
     * The player can choose from different healing options, provided they have enough gems.
     */
    private void hospital() {
        System.out.println("You have arrived at the clinic.");
        System.out.println("1. Minor Healing (Restores 20 HP, costs 20 gems)");
        System.out.println("2. Major Healing (Restores 40 HP, costs 50 gems)");
        System.out.println("3. Stamina Recovery (Restores 110 stamina, costs 50 gems)");
        System.out.println("0. Leave the clinic");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        switch (choice) {
            case 1:
                if (player.spendGold(20)) {
                    player.heal(20);
                    System.out.println("Healing successful! Current health points: " + player.getHealth());
                } else {
                    System.out.println("Insufficient gems, unable to heal.");
                }
                break;
            case 2:
                if (player.spendGold(50)) {
                    player.heal(40);
                    System.out.println("Healing successful! Current health points: " + player.getHealth());
                } else {
                    System.out.println("Insufficient gems, unable to heal.");
                }
                break;
            case 3:
                if (player.spendGold(50)) {
                    player.restoreStamina(110);
                    System.out.println("Stamina recovery successful! Current stamina value: " + player.getStamina());
                } else {
                    System.out.println("Insufficient gems, unable to restore stamina.");
                }
                break;
            case 0:
                break;
            default:
                System.out.println("Invalid selection.");
                break;
        }
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * Allows the player to enter the battlefield and fight monsters.
     * The player can move around the battlefield, encounter monsters, and engage in combat.
     * The number of monsters increases with each wave, and defeated monsters are removed.
     */
    private void battleField() {
        System.out.println("You have left the village and entered the battlefield.");

        // Initialize the battlefield map dimensions
        int battlefieldHeight = 15;
        int battlefieldWidth = 20;
        char[][] battlefield = new char[battlefieldHeight][battlefieldWidth];

        // Fill the battlefield with empty spaces
        for (int i = 0; i < battlefieldHeight; i++) {
            Arrays.fill(battlefield[i], ' ');
        }

        // Set the boundaries of the battlefield
        for (int i = 0; i < battlefieldHeight; i++) {
            battlefield[i][0] = '■'; // Left boundary wall
            battlefield[i][battlefieldWidth - 1] = '■'; // Right boundary wall
        }
        for (int j = 0; j < battlefieldWidth; j++) {
            battlefield[0][j] = '■'; // Top boundary wall
            battlefield[battlefieldHeight - 1][j] = '■'; // Bottom boundary wall
        }

        // Set the entrance to return to the village
        battlefield[7][0] = 'G'; // Village entrance

        // Set the initial position of the player on the battlefield
        int battlePlayerX = 2; // Player's starting X coordinate
        int battlePlayerY = 7; // Player's starting Y coordinate

        // Check if currentMonsters is empty
        if (currentMonsters.isEmpty()) {
            if (wave > 3) {
                // Game has been won, display victory message
                System.out.println("Congratulations! You have defeated all monsters and won the game!");
                System.out.println("Your total gems: " + player.getGold());
                System.exit(0); // End the game
            }

            // Generate monsters for the current wave
            int numMonsters = wave; // Number of monsters equals the wave number
            currentMonsters = new ArrayList<>();

            for (int i = 0; i < numMonsters; i++) {
                // Randomly select a monster type from available configurations
                List<String> monsterNames = new ArrayList<>(gameEngine.getMonsterConfigs().keySet());
                String randomMonsterName = monsterNames.get(random.nextInt(monsterNames.size()));

                Monster monster = gameEngine.generateMonster(randomMonsterName);
                if (monster != null) {
                    currentMonsters.add(monster);
                } else {
                    // If the monster configuration is not found, create a default monster
                    monster = new Monster("Default Monster", 100, 10, 5, 5, 5, 20);
                    currentMonsters.add(monster);
                }
            }

            // Assign random positions to each monster
            for (Monster monster : currentMonsters) {
                int x, y;
                do {
                    x = random.nextInt(battlefieldWidth - 2) + 1; // Avoid boundaries
                    y = random.nextInt(battlefieldHeight - 2) + 1;
                } while (battlefield[y][x] != ' ' || (x == battlePlayerX && y == battlePlayerY));
                monster.setMonsterX(x);
                monster.setMonsterY(y);
                battlefield[y][x] = 'M';
            }
        } else {
            // Place existing monsters on the battlefield
            for (Monster monster : currentMonsters) {
                int x = monster.getX();
                int y = monster.getY();
                battlefield[y][x] = 'M';
            }
        }

        // Player actions in the battlefield
        boolean inBattlefield = true;
        while (inBattlefield) {
            displayBattlefieldMap(battlefield, battlePlayerX, battlePlayerY); // Display battlefield map

            // Prompt player for movement or exit commands
            System.out.println("G: Gate Between Village & Battlefield, M: Monster, Y: You");
            System.out.println("Please enter a command (WASD to move, Q to leave the battlefield)");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                inBattlefield = false; // Exit battlefield
                System.out.println("You have returned to the village.");
            } else if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D")) {
                // Move the player on the battlefield
                int newX = battlePlayerX;
                int newY = battlePlayerY;
                switch (input) {
                    case "W":
                        newY--;
                        break; // Move up
                    case "A":
                        newX--;
                        break; // Move left
                    case "S":
                        newY++;
                        break; // Move down
                    case "D":
                        newX++;
                        break; // Move right
                }
                // Check if the move is valid
                if (newX >= 0 && newX < battlefield[0].length && newY >= 0 && newY < battlefield.length) {
                    if (battlefield[newY][newX] != '■') {
                        battlePlayerX = newX;
                        battlePlayerY = newY;

                        // Check player's current position
                        if (battlefield[newY][newX] == 'M') { // Encounter a monster
                            System.out.println("You encountered a monster!");
                            Monster monster = null;
                            Iterator<Monster> iterator = currentMonsters.iterator();
                            while (iterator.hasNext()) {
                                Monster m = iterator.next();
                                if (m.getX() == newX && m.getY() == newY) {
                                    monster = m;
                                    break;
                                }
                            }
                            if (monster != null) {
                                battle(monster);
                                if (monster.getHealth() <= 0) {
                                    battlefield[newY][newX] = ' ';
                                    iterator.remove();
                                }
                            }

                            // Check if all monsters are defeated
                            if (currentMonsters.isEmpty()) {
                                if (wave == 3) {
                                    // Display victory message and end the game
                                    System.out.println("Congratulations! You have defeated all monsters and won the game!");
                                    System.out.println("Your total gems: " + player.getGold());
                                    System.exit(0); // End the game
                                } else {
                                    System.out.println("You have defeated all the monsters and returned to the village.");
                                    wave++;
                                    inBattlefield = false; // Return to village
                                    slept = false; // Allow the player to rest again
                                }
                            }
                        } else if (battlefield[newY][newX] == 'G') {
                            // Player returns to village
                            System.out.println("You have returned to the village.");
                            inBattlefield = false;
                        }

                        // Move monsters
                        moveMonsters(battlefield, currentMonsters, battlePlayerX, battlePlayerY);
                    } else {
                        System.out.println("Bumped into a wall, cannot move!");
                    }
                } else {
                    System.out.println("Cannot move there!");
                }
            } else {
                System.out.println("Invalid input, please re-enter.");
            }

            // Check if player is defeated
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
    }

    /**
     * Displays the battlefield map, including the player's position and monster positions.
     * Also shows the player's current health, stamina, and gems.
     *
     * @param battlefield   the current state of the battlefield map
     * @param battlePlayerX the player's current X position on the battlefield
     * @param battlePlayerY the player's current Y position on the battlefield
     */
    private void displayBattlefieldMap(char[][] battlefield, int battlePlayerX, int battlePlayerY) {
        // Clear screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Display the battlefield map
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[0].length; j++) {
                if (i == battlePlayerY && j == battlePlayerX) {
                    System.out.print("Y"); // Display player
                } else {
                    System.out.print(battlefield[i][j]); // Display other elements
                }
            }
            System.out.println();
        }
        // Display player's status
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * Moves monsters around the battlefield randomly and checks if any monster encounters the player.
     * If a monster encounters the player, a battle ensues.
     *
     * @param battlefield the current state of the battlefield map
     * @param monsters    the list of monsters on the battlefield
     * @param playerX     the player's current X position
     * @param playerY     the player's current Y position
     */
    private void moveMonsters(char[][] battlefield, List<Monster> monsters, int playerX, int playerY) {
        Iterator<Monster> iterator = monsters.iterator();
        while (iterator.hasNext()) {
            Monster monster = iterator.next();
            int x = monster.getX();
            int y = monster.getY();

            // Clear old position
            battlefield[y][x] = ' ';

            // Randomly move the monster
            int newX = x;
            int newY = y;
            int dir = random.nextInt(4);
            switch (dir) {
                case 0:
                    newY--;
                    break; // Move up
                case 1:
                    newY++;
                    break; // Move down
                case 2:
                    newX--;
                    break; // Move left
                case 3:
                    newX++;
                    break; // Move right
            }

            // Check if the new position is within boundaries and unoccupied
            if (newX >= 1 && newX < battlefield[0].length - 1 && newY >= 1 && newY < battlefield.length - 1
                    && battlefield[newY][newX] == ' ' && (newX != playerX || newY != playerY)) {
                monster.setMonsterX(newX);
                monster.setMonsterY(newY);
            }

            // Update monster position on the battlefield map
            x = monster.getX();
            y = monster.getY();
            battlefield[y][x] = 'M';

            // Check if the monster has encountered the player
            if (x == playerX && y == playerY) {
                System.out.println("A monster has encountered you!");
                battle(monster);

                // Check if monster is defeated
                if (monster.getHealth() <= 0) {
                    // Remove monster from the map after battle
                    battlefield[y][x] = ' ';
                    iterator.remove();

                    // Check if all monsters are defeated
                    if (currentMonsters.isEmpty()) {
                        if (wave == 3) {
                            System.out.println("Congratulations! You have defeated all monsters and won the game!");
                            System.out.println("Your total gems: " + player.getGold());
                            System.exit(0);
                        } else {
                            System.out.println("You have defeated all the monsters and returned to the village.");
                            wave++;
                            slept = false;
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * Handles the battle logic between the player and a monster.
     * The player can choose different attack options or escape from the battle.
     *
     * @param monster the monster that the player is battling
     */
    private void battle(Monster monster) {
        System.out.println("You encountered a " + monster.getName() + "!");
        boolean inBattle = true;

        // Loop until the battle is over
        while (inBattle) {
            // Display player action choices
            System.out.println("\nPlease choose your action:");
            System.out.println("1. Light Attack (Damage +25, consumes 2 stamina)");
            System.out.println("2. Normal Attack (Damage +80, consumes 8 stamina)");
            System.out.println("3. Heavy Attack (Damage +160, consumes 20 stamina)");
            System.out.println("4. Escape (Consumes 10 stamina)");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Light attack
                    if (player.getStamina() >= 2) {
                        player.reduceStamina(2);
                        attackMonster(monster, 25);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 2:
                    // Normal attack
                    if (player.getStamina() >= 8) {
                        player.reduceStamina(8);
                        attackMonster(monster, 80);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 3:
                    // Heavy attack
                    if (player.getStamina() >= 20) {
                        player.reduceStamina(20);
                        attackMonster(monster, 160);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 4:
                    // Escape
                    if (player.getStamina() >= 10) {
                        player.reduceStamina(10);
                        System.out.println("You successfully escaped!");
                        inBattle = false;
                    } else {
                        System.out.println("Not enough stamina to escape!");
                    }
                    break;
                default:
                    System.out.println("Invalid selection, please re-enter.");
                    break;
            }

            // Check if player is defeated
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
    }

    /**
     * Performs an attack on the specified monster, taking into account player's damage,
     * monster's defense, and possible critical hits or dodges.
     *
     * @param monster the monster to attack
     * @param extraDamage additional damage to add to the player's base attack
     */
    private void attackMonster(Monster monster, int extraDamage) {
        int totalDamage = player.attack() + extraDamage - monster.getDefense();

        if (totalDamage < 0) totalDamage = 0;

        if (random.nextInt(100) < monster.getDodgeChance()) {
            System.out.println(monster.getName() + " dodged your attack!");
        } else {
            if (random.nextInt(100) < player.getCriticalChance()) {
                totalDamage *= 1.5;
                System.out.println("Critical hit! You dealt " + monster.getName() + " " + totalDamage + " points of damage.");
            } else {
                System.out.println("You dealt " + monster.getName() + " " + totalDamage + " points of damage.");
            }
            monster.reduceHealth(totalDamage);
        }
    }

    /**
     * Handles the monster's attack on the player, taking into account monster's damage,
     * player's defense, and possible critical hits or dodges.
     *
     * @param monster the monster attacking the player
     */
    private void monsterAttack(Monster monster) {
        int totalDamage = monster.attack() - player.getDefense();

        if (totalDamage < 0) totalDamage = 0;

        if (random.nextInt(100) < player.getDodgeChance()) {
            System.out.println("You dodged " + monster.getName() + "'s attack!");
        } else {
            if (random.nextInt(100) < monster.getCriticalChance()) {
                totalDamage *= 1.5;
                System.out.println(monster.getName() + " landed a critical hit on you, dealing " + totalDamage + " points of damage!");
            } else {
                System.out.println(monster.getName() + " dealt " + totalDamage + " points of damage to you.");
            }
            player.reduceHealth(totalDamage);
            System.out.println("Your remaining health points: " + player.getHealth());
        }
    }

    /**
     * Displays the player's current status, including role, health, stamina, damage, defense,
     * critical chance, dodge chance, and gems.
     */
    private void showStatus() {
        System.out.println("\nStatus:");
        System.out.println("Role: " + player.getName());
        System.out.println("Health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Damage: " + player.getDamage());
        System.out.println("Defense: " + player.getDefense());
        System.out.println("Critical Chance: " + player.getCriticalChance() + "%");
        System.out.println("Dodge Chance: " + player.getDodgeChance() + "%");
        System.out.println("Gems: " + player.getGold());
        System.out.println("Press 'Enter' to continue...");
        scanner.nextLine();
    }

    /**
     * Manages the player's inventory, allowing them to view, equip, and manage items
     * such as weapons and armor.
     */
    private void manageInventory() {
        Inventory inventory = player.getInventory();
        boolean inInventory = true;
        while (inInventory) {
            System.out.println("\nYour Bag:");
            inventory.showInventory();
            System.out.println("1. Equip Weapon");
            System.out.println("2. Equip Armor");
            System.out.println("0. Return");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    equipWeapon();
                    break;
                case 2:
                    equipArmor();
                    break;
                case 0:
                    inInventory = false;
                    break;
                default:
                    System.out.println("Invalid choice, please re-enter.");
                    break;
            }
        }
    }

    /**
     * Allows the player to equip a weapon from their inventory.
     * Displays a list of available weapons and equips the selected one.
     */
    private void equipWeapon() {
        Inventory inventory = player.getInventory();
        List<Item> items = inventory.getItems();
        List<Weapon> weapons = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Weapon) {
                weapons.add((Weapon) item);
            }
        }
        if (weapons.isEmpty()) {
            System.out.println("You have no weapon to equip.");
            return;
        }
        System.out.println("Please select a weapon to equip:");
        int index = 1;
        for (Weapon weapon : weapons) {
            System.out.println(index + ". " + weapon.getName() + " - damage: " + weapon.getDamage());
            index++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        if (choice > 0 && choice <= weapons.size()) {
            Weapon selectedWeapon = weapons.get(choice - 1);
            inventory.equipWeapon(selectedWeapon);
            System.out.println("You equipped: " + selectedWeapon.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /**
     * Allows the player to equip armor from their inventory.
     * Displays a list of available armor and equips the selected one.
     */
    private void equipArmor() {
        Inventory inventory = player.getInventory();
        List<Item> items = inventory.getItems();
        List<Armor> armors = new ArrayList<>();
        for (Item item : items) {
            if (item instanceof Armor) {
                armors.add((Armor) item);
            }
        }
        if (armors.isEmpty()) {
            System.out.println("You have no armor to equip.");
            return;
        }
        System.out.println("Please select armor to equip:");
        int index = 1;
        for (Armor armor : armors) {
            System.out.println(index + ". " + armor.getName() + " - defense: " + armor.getDefense());
            index++;
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        if (choice > 0 && choice <= armors.size()) {
            Armor selectedArmor = armors.get(choice - 1);
            inventory.equipArmor(selectedArmor);
            System.out.println("You equipped: " + selectedArmor.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
