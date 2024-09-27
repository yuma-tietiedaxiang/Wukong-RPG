package comp2120.ass3;

import java.util.*;

public class Game {
    private Scanner scanner;
    private Player player;
    private int wave = 1; // Current wave
    private Random random;
    private boolean slept = false; // Flags whether the player has rested

    private GameEngine gameEngine;

    // Map attributes
    private char[][] map;
    private int playerX;
    private int playerY;

    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();

        gameEngine = new GameEngine(
                "src/main/java/comp2120/ass3/resources/config/map.json",
                "src/main/java/comp2120/ass3/resources/config/monsterConfig.json",
                "src/main/java/comp2120/ass3/resources/config/levelConfig.json"
        );
    }

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
     * Allow the user to choose a profession in the beginning
     */
    private void chooseProfession() {
        System.out.println("Please choose your profession: ");
        System.out.println("1. Monkey King");
        System.out.println("2. Pigsy");
        System.out.println("3. Sandy");
        System.out.println("4. Tang Monk");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
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
     * Initialize the game map. Including map size, location of player, location of special places
     */
    private void initializeMap() {
        map = gameEngine.getMap(); // Get the map from GameEngine
        playerX = gameEngine.getPlayerX(); // Get player's starting X position
        playerY = gameEngine.getPlayerY(); // Get player's starting Y position
    }

    /**
     * The main game loop that continuously runs while the game is active.
     * It handles user input for movement, inventory management, status display, and quitting the game.
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
     * Displays the current state of the game map and player status.
     */
    private void displayMap() {
        // Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Display the map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("Y"); // Display player as 'Y'
                } else {
                    System.out.print(map[i][j]); // Display the map character
                }
            }
            System.out.println();
        }

        // Display location keys
        System.out.println("H:home, W:weapon shop, A:armor shop, P:pet shop, C:clinic, G:village gate");

        // Show current player status
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * Moves the player in the specified direction if the movement is valid.
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
                // Update player's position if valid
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
            case 'G': // Village gate
                battleField();
                break;
            default:
                // Do nothing for other locations
                break;
        }
    }

    /**
     * Handles resting at home to recover health and stamina points.
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
     * Manages interactions within the weapon shop.
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
            scanner.nextLine(); // Consume newline
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
     * Manages interactions within the armor shop.
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
            scanner.nextLine(); // Consume newline
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
     * Displays a message indicating that the pet shop is currently unavailable.
     */
    private void petShop() {
        System.out.println("You arrive at the pet shop.");
        System.out.println("Temporarily unavailable.");
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * Manages interactions at the hospital, allowing the player to heal or recover stamina.
     */
    private void hospital() {
        System.out.println("You have arrived at the clinic.");
        System.out.println("1. Minor Healing (Restores 20 HP, costs 20 gems)");
        System.out.println("2. Major Healing (Restores 40 HP, costs 50 gems)");
        System.out.println("3. Stamina Recovery (Restores 110 stamina, costs 50 gems)");
        System.out.println("0. Leave the clinic");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
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
     * Simulates the battlefield scene where the player can move around, encounter monsters, and battle them.
     */
    private void battleField() {
        System.out.println("You have left the village and entered the battlefield.");

        // Initialize the battlefield map
        char[][] battlefield = new char[15][20];

        // Fill the battlefield map with spaces
        for (int i = 0; i < battlefield.length; i++) {
            Arrays.fill(battlefield[i], ' ');
        }

        // Set the boundary (walls)
        for (int i = 0; i < battlefield.length; i++) {
            battlefield[i][0] = '■'; // Left wall
            battlefield[i][battlefield[0].length - 1] = '■'; // Right wall
        }
        for (int j = 0; j < battlefield[0].length; j++) {
            battlefield[0][j] = '■'; // Top wall
            battlefield[battlefield.length - 1][j] = '■'; // Bottom wall
        }

        // Set the gate for returning to the village
        battlefield[7][0] = 'G'; // Village entrance

        // Set the player's initial position
        int battlePlayerX = battlefield[0].length - 2; // Column position
        int battlePlayerY = 7; // Row position

        // Place monsters on the battlefield using GameEngine
        List<Monster> monsters = gameEngine.initializeMonsters();
        List<int[]> monsterPositions = new ArrayList<>();
        for (Monster monster : monsters) {
            int monsterX = monster.getX();
            int monsterY = monster.getY();
            battlefield[monsterY][monsterX] = 'M';
            monsterPositions.add(new int[]{monsterX, monsterY});
        }

        // Player stays in the battlefield until they decide to leave
        boolean inBattlefield = true;
        while (inBattlefield) {
            displayBattlefieldMap(battlefield, battlePlayerX, battlePlayerY); // Display battlefield map

            // Prompt the player to enter a command for movement or exit
            System.out.println("Please enter a command (WASD to move, Q to leave the battlefield)");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                inBattlefield = false; // Exit the battlefield
                System.out.println("You have returned to the village.");
            } else if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D")) {
                // Move the player based on input
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
                // Check if the movement is within the battlefield boundary
                if (newX >= 0 && newX < battlefield[0].length && newY >= 0 && newY < battlefield.length) {
                    if (battlefield[newY][newX] != '■') { // Check if the player hits a wall
                        battlePlayerX = newX;
                        battlePlayerY = newY;

                        // Check what the player encounters at the new position
                        if (battlefield[newY][newX] == 'M') { // Encounter a monster
                            // Start the battle with the monster
                            System.out.println("You encountered a monster!");
                            Monster monster = null;
                            Iterator<Monster> iterator = monsters.iterator();
                            while (iterator.hasNext()) {
                                Monster m = iterator.next();
                                if (m.getX() == newX && m.getY() == newY) {
                                    monster = m;
                                    iterator.remove();
                                    break;
                                }
                            }
                            if (monster != null) {
                                battle(monster);
                            }

                            // Remove the monster from the battlefield after defeat
                            battlefield[newY][newX] = ' ';
                            int finalNewX = newX;
                            int finalNewY = newY;
                            // Remove the monster position from the list
                            monsterPositions.removeIf(pos -> pos[0] == finalNewX && pos[1] == finalNewY);
                            // Check if all monsters are defeated
                            if (monsterPositions.isEmpty()) {
                                System.out.println("You have defeated all the monsters and returned to the village.");
                                inBattlefield = false; // Return to the village after all monsters are defeated
                            }
                        } else if (battlefield[newY][newX] == 'G') {
                            // Player returns to the village
                            System.out.println("You have returned to the village.");
                            inBattlefield = false;
                        }

                        // Move the monsters after the player moves
                        moveMonsters(battlefield, monsterPositions, battlePlayerX, battlePlayerY);
                    } else {
                        System.out.println("Bumped into a wall, cannot move!"); // Hit a wall
                    }
                } else {
                    System.out.println("Cannot move there!"); // Out of bounds
                }
            } else {
                System.out.println("Invalid input, please re-enter."); // Handle invalid commands
            }

            // Check if the player's health has dropped to zero
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
        slept = false; // Player can rest again after the battle is over
    }

    /**
     * Displays the battlefield map with the player's current position.
     */
    private void displayBattlefieldMap(char[][] battlefield, int battlePlayerX, int battlePlayerY) {
        // Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Display the battlefield with the player's position
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[0].length; j++) {
                if (i == battlePlayerY && j == battlePlayerX) {
                    System.out.print("Y"); // Represents the player
                } else {
                    System.out.print(battlefield[i][j]); // Display other battlefield elements
                }
            }
            System.out.println();
        }
        // Display player's status (health, stamina, and gold)
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * Moves the monsters randomly on the battlefield and checks if any monster encounters the player.
     */
    private void moveMonsters(char[][] battlefield, List<int[]> monsterPositions, int playerX, int playerY) {
        // Move each monster
        Iterator<int[]> iterator = monsterPositions.iterator();
        while (iterator.hasNext()) {
            int[] pos = iterator.next();
            int x = pos[0];
            int y = pos[1];

            // Clear the old position
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

            // Check if the new position is within the boundaries and is empty
            if (newX >= 1 && newX < battlefield[0].length - 1 && newY >= 1 && newY < battlefield.length - 1 && battlefield[newY][newX] == ' ') {
                pos[0] = newX;
                pos[1] = newY;
            }

            // Place the monster at the new position
            battlefield[pos[1]][pos[0]] = 'M'; // Represents the monster

            // Check if the monster encounters the player
            if (pos[0] == playerX && pos[1] == playerY) {
                // Monster encounters the player, start a battle
                System.out.println("A monster has encountered you!");
                Monster monster = null;
                Iterator<Monster> monsterIterator = gameEngine.getCurrentMonsters().iterator();
                while (monsterIterator.hasNext()) {
                    Monster m = monsterIterator.next();
                    if (m.getX() == pos[0] && m.getY() == pos[1]) {
                        monster = m;
                        monsterIterator.remove();
                        break;
                    }
                }
                if (monster != null) {
                    battle(monster);
                }

                // Remove the monster after it is defeated
                battlefield[pos[1]][pos[0]] = ' ';
                iterator.remove(); // Exit the loop to avoid modifying the list during iteration
                break;
            }
        }
    }

    /**
     * Generates a new monster with attributes based on the current wave of battles.
     */
    private Monster generateMonster(String name) {
        Monster monster = gameEngine.generateMonster(name);
        if (monster == null) {
            // Handle the case where the monster is not found in the configuration
            System.out.println("Monster not found in configuration. Using default values.");
            monster = new Monster("Default Monster", 100, 10, 5, 5, 5, 20);
        }
        return monster;
    }

    /**
     * Simulates a battle between the player and a monster.
     */
    private void battle(Monster monster) {
        System.out.println("You encountered a " + monster.getName() + "!");
        boolean inBattle = true;

        // Loop until the battle ends
        while (inBattle) {
            // Player's action choices
            System.out.println("\nPlease choose your action:");
            System.out.println("1. Light Attack (Damage +25, consumes 2 stamina)");
            System.out.println("2. Normal Attack (Damage +80, consumes 8 stamina)");
            System.out.println("3. Heavy Attack (Damage +160, consumes 20 stamina)");
            System.out.println("4. Escape (Consumes 10 stamina)");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Light Attack
                    if (player.getStamina() >= 2) {
                        player.reduceStamina(2);
                        attackMonster(monster, 25);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            wave++;
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 2:
                    // Normal Attack
                    if (player.getStamina() >= 8) {
                        player.reduceStamina(8);
                        attackMonster(monster, 80);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            wave++;
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 3:
                    // Heavy Attack
                    if (player.getStamina() >= 20) {
                        player.reduceStamina(20);
                        attackMonster(monster, 160);
                        if (monster.getHealth() <= 0) {
                            System.out.println("You defeated the " + monster.getName() + "!");
                            player.addGold(monster.getGoldReward());
                            wave++;
                            inBattle = false;
                        } else {
                            monsterAttack(monster);
                        }
                    } else {
                        System.out.println("Not enough stamina to attack!");
                    }
                    break;
                case 4:
                    // Attempt to escape the battle
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

            // Check if the player was defeated
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
    }

    /**
     * Calculates and executes an attack on the monster.
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
     * Executes the monster's attack on the player.
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
     * Displays the player's current status.
     */
    private void showStatus() {
        System.out.println("\nStatus:");
        System.out.println("Profession: " + player.getName());
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
     * Manages the player's inventory, allowing them to equip weapons or armor.
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
            scanner.nextLine(); // Consume newline
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
     * Allows the player to select and equip a weapon from the inventory.
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
        scanner.nextLine(); // Consume newline
        if (choice > 0 && choice <= weapons.size()) {
            Weapon selectedWeapon = weapons.get(choice - 1);
            inventory.equipWeapon(selectedWeapon);
            System.out.println("You equipped: " + selectedWeapon.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /**
     * Allows the player to select and equip armor from the inventory.
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
        scanner.nextLine(); // Consume newline
        if (choice > 0 && choice <= armors.size()) {
            Armor selectedArmor = armors.get(choice - 1);
            inventory.equipArmor(selectedArmor);
            System.out.println("You equipped: " + selectedArmor.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
