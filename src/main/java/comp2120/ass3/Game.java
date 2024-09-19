package comp2120.ass3;

import java.util.*;

public class Game {
    private Scanner scanner;
    private Player player;
    private int wave = 1; // 当前波次
    private Random random;
    private boolean slept = false; // flags whether the player has rested

    // map attributes
    private char[][] map;
    private int playerX;
    private int playerY;

    public Game() {
        scanner = new Scanner(System.in);
        random = new Random();
    }

    public void startGame() {
        System.out.println("Welcome to the game!");
        System.out.println("Your keyboard should now be set to English.");
        System.out.println("Press 'Enter' to continue...'");
        scanner.nextLine();
        chooseProfession();
        initializeMap();
        gameLoop();
    }

    /**
     * Allow the user to choose a profession in the beginning
     * @author Yu Ma
     */
    private void chooseProfession() {
        System.out.println("Please choose your profession: ");
        System.out.println("1. Archer");
        System.out.println("2. Warrior");
        System.out.println("3. Samurai");
        System.out.println("4. Assassin");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 消除换行符
        switch (choice) {
            case 1:
                player = new Player("Archer", 200, 155, 30, 16, 20, 15);
                break;
            case 2:
                player = new Player("Warrior", 200, 110, 20, 22, 30, 10);
                break;
            case 3:
                player = new Player("Samurai", 300, 75, 15, 30, 25, 5);
                break;
            case 4:
                player = new Player("Assassin", 160, 155, 20, 20, 40, 25);
                break;
            default:
                System.out.println("Invalid selection, defaulting to Archer.");
                player = new Player("Archer", 200, 155, 30, 16, 20, 15);
                break;
        }
        System.out.println("You chose：" + player.getName());
    }


    /**
     * Initialize the game map(13*18). Including map size, location of player, location of special places
     * @author Yu Ma
     */
    private void initializeMap() {

        map = new char[15][20]; // row x column

        // fill the map with spaces
        for (int i = 0; i < map.length; i++) {
            Arrays.fill(map[i], ' ');
        }

        // set up map edge
        for (int i = 0; i < map.length; i++) {
            map[i][0] = '■';
            map[i][map[0].length - 1] = '■';
        }
        for (int j = 0; j < map[0].length; j++) {
            map[0][j] = '■';
            map[map.length - 1][j] = '■';
        }

        // special places
        map[3][5] = 'H'; // home
        map[3][10] = 'C'; // hosipital - clinic
        map[11][5] = 'P'; // pet shop
        map[11][10] = 'A'; // armor shop
        map[11][15] = 'W'; // weapon shop
        map[7][18] = 'G'; // village gate

        // initialize player's location
        playerX = 1; // column
        playerY = 7; // row
    }


    /**
     * The main game loop that continuously runs while the game is active.
     * It handles user input for movement, inventory management, status display, and quitting the game.
     * @author Yu Ma
     */
    private void gameLoop() {
        //Flag to control the loop execution
        boolean isRunning = true;

        while (isRunning) {
            displayMap();
            System.out.println("Please enter command (WASD to move, I for inventory, P for status, Q to quit)");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {// If the player wants to quit, exit the loop
                isRunning = false;
                System.out.println("Game over, thank you for playing!");

            } else if (input.equals("I")) {// If the player wants to manage their inventory, call the corresponding method
                //TODO 还没添加这个方法 manageInventory();

            } else if (input.equals("P")) {// If the player wants to check their status, show the status
                //TODO 还没添加这个方法 showStatus();

            } else if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D")) {
                movePlayer(input);// If the input is a movement command, move the player
                handlePosition();
            } else {
                System.out.println("Invalid input, please re-enter.");
            }
        }
    }

    /**
     * Displays the current state of the game map and player status.
     * Clears the screen, renders the map with the player's position,
     * and shows the player's current health, stamina, and gold.
     * @author Yu Ma
     */
    private void displayMap() {
        //Clear the screen
        System.out.print("\033[H\033[2J");
        System.out.flush();

        //Display the map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("Y");// Display player as 'Y'
                } else {
                    System.out.print(map[i][j]);// Display the map character
                }
            }
            System.out.println();
        }

        // Display location keys
        System.out.println("H:home, W:weapon shop, A:armor shop, P:pet shop, C:clinic, G:vilige gate");

        // Show current player status
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gem: " + player.getGold());
    }

    /**
     * Moves the player in the specified direction if the movement is valid.
     * It checks for boundaries and collisions with walls before updating the player's position.
     *
     * @param direction The keys on keyboard W A S D.
     * @author Yu Ma
     */
    private void movePlayer(String direction) {
        // Get current location of the player
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
     * It triggers the appropriate action depending on the location character.
     *
     * @author Yu Ma
     */
    private void handlePosition() {
        char location = map[playerY][playerX]; // Get the place character at current player's position
        switch (location) {
            case 'H': // Home
                restAtHome();
                break;
            case 'W': // Weapon shop
                //TODO weaponShop();
                break;
            case 'A': // Armor shop
                //TODO armorShop();
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
     * Allows the player to rest only once per visit.
     * @author Yu Ma
     */
    private void restAtHome() {
        if (!slept) {// The player hasn't rested, here slept==false
            System.out.println("You rest at home, recovering stamina and health points.");
            //TODO player.rest(); // Recover player's health and stamina
            System.out.println("Current health points: " + player.getHealth());
            System.out.println("Current stamina: " + player.getStamina());
            slept = true; // Mark that the player has rested
        } else {
            System.out.println("You have already rested, you cannot rest again.");
        }
        System.out.println("Press 'Enter' to continue...");
        scanner.nextLine(); // Wait for user input
    }


    /**
     * Manages interactions within the weapon shop. Allows the player to view and purchase weapons.
     * @author Yu Ma
     */
//    private void weaponShop() {//TODO
//        System.out.println("You arrive at the weapon shop.");
//        List<Weapon> weapons = new ArrayList<>();
//        weapons.add(new Weapon("Bamboo Arrow", 70, 25));
//        weapons.add(new Weapon("Iron Arrow", 130, 35));
//        weapons.add(new Weapon("Fire Arrow", 220, 55));
//
//        boolean inShop = true;
//        while (inShop) {
//            System.out.println("Your gems: " + player.getGold());
//            System.out.println("Please select a weapon to purchase: ");
//
//            // List out weapons available
//            int index = 1;
//            for (Weapon weapon : weapons) {
//                System.out.println(index + ". " + weapon.getName() + " - price: " + weapon.getPrice() + " Gems, weapon damage: " + weapon.getDamage());
//                index++;
//            }
//            System.out.println("0. Leave the shop");
//
//            // user choose a weapon
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Clear the newline character
//            if (choice == 0) {
//                inShop = false; // Exit the shop
//            } else if (choice > 0 && choice <= weapons.size()) {
//                Weapon selectedWeapon = weapons.get(choice - 1);
//                if (player.spendGold(selectedWeapon.getPrice())) {
//                    //TODO 存货管理要做 player.getInventory().addItem(selectedWeapon); // Add weapon to inventory
//                    System.out.println("You purchased: " + selectedWeapon.getName());
//                } else {
//                    System.out.println("Insufficient gems, unable to purchase the weapon.");
//                }
//            } else {
//                System.out.println("Invalid selection, please re-enter.");
//            }
//        }
//        System.out.println("Press 'Enter' to return...");
//        scanner.nextLine(); // Wait for user input
//    }

    /**
     * Manages interactions within the armor shop. Allows the player to view and purchase armor.
     * @author Yu Ma
     */
//    private void armorShop() {//TODO
//        System.out.println("You arrive at the armor shop.");
//        List<Armor> armors = new ArrayList<>();
//        armors.add(new Armor("Wooden Shield", 80, 11));
//        armors.add(new Armor("Iron Pants", 90, 7));
//        armors.add(new Armor("Diamond Armor", 200, 16));
//
//        boolean inShop = true;
//        while (inShop) {
//            System.out.println("Your Gem: " + player.getGold());
//            System.out.println("Please select the armor you want to buy: ");
//
//            // List out weapons available
//            int index = 1;
//            for (Armor armor : armors) {
//                System.out.println(index + ". " + armor.getName() + " - price: " + armor.getPrice() + " Gems, defense: " + armor.getDefense());
//                index++;
//            }
//            System.out.println("0. Leave the shop");
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // Clear the newline character
//            if (choice == 0) {
//                inShop = false; // Exit the shop
//            } else if (choice > 0 && choice <= armors.size()) {
//                Armor selectedArmor = armors.get(choice - 1);
//                if (player.spendGold(selectedArmor.getPrice())) {
//                    //TODO 存货管理部分 player.getInventory().addItem(selectedArmor); // Add armor to inventory
//                    System.out.println("You purchased: " + selectedArmor.getName());
//                } else {
//                    System.out.println("Insufficient gems, unable to purchase the armor.");
//                }
//            } else {
//                System.out.println("Invalid choice, please enter again.");
//            }
//        }
//        System.out.println("Press 'Enter' to return...");
//        scanner.nextLine(); // Wait for user input
//    }


    /**
     * Displays a message indicating that the pet shop is currently unavailable.
     * @author Yu Ma
     */
    private void petShop() {
        //TODO issue10
        System.out.println("You arrive at the pet shop.");
        System.out.println("Temporarily unavailable.");
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine(); // Wait for user input
    }


    /**
     * Manages interactions at the hospital, allowing the player to heal or recover stamina.
     * @author Yu Ma
     */
    private void hospital() {//TODO
//        System.out.println("You have arrived at the clinic.");
//        System.out.println("1. Minor Healing (Restores 20 HP, costs 20 gems)");
//        System.out.println("2. Major Healing (Restores 40 HP, costs 50 gems)");
//        System.out.println("3. Stamina Recovery (Restores 110 stamina, costs 50 gems)");
//        System.out.println("0. Leave the clinic");
//
//        // Player choose one option
//        int choice = scanner.nextInt();
//        scanner.nextLine(); // Clear the newline character
//        switch (choice) {
//            case 1:
//                if (player.spendGold(20)) {
//                    player.heal(20); // Heal the player
//                    System.out.println("Healing successful! Current health points: " + player.getHealth());
//                } else {
//                    System.out.println("Insufficient gems, unable to heal.");
//                }
//                break;
//            case 2:
//                if (player.spendGold(50)) {
//                    player.heal(40); // Heal the player
//                    System.out.println("Healing successful! Current health points: " + player.getHealth());
//                } else {
//                    System.out.println("Insufficient gems, unable to heal.");
//                }
//                break;
//            case 3:
//                if (player.spendGold(50)) {
//                    player.restoreStamina(110); // Restore stamina
//                    System.out.println("Stamina recovery successful! Current stamina value: " + player.getStamina());
//                } else {
//                    System.out.println("Insufficient gems, unable to restore stamina.");
//                }
//                break;
//            case 0:
//                break; // Exit the clinic
//            default:
//                System.out.println("Invalid selection.");
//                break;
//        }
//        System.out.println("Press Enter to return...");
//        scanner.nextLine(); // Wait for user input
    }

    /**
     * Simulates the battlefield scene where the player can move around, encounter monsters, and battle them.
     * The player can use WASD keys to move and can exit the battlefield by pressing 'Q'.
     * If all monsters are defeated, the player automatically returns to the village.
     *
     * @author Yu Ma
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

        // Place monsters on the battlefield
        List<Monster> monsters = new ArrayList<>();
        List<int[]> monsterPositions = new ArrayList<>();
        int numMonsters = 3; // Number of monsters
        for (int i = 0; i < numMonsters; i++) {
            // Randomly generate monster positions within the battlefield
            int monsterX = random.nextInt(battlefield[0].length - 2) + 1;
            int monsterY = random.nextInt(battlefield.length - 2) + 1;
            battlefield[monsterY][monsterX] = '怪'; // Representing the monster
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
                    case "W": newY--; break; // Move up
                    case "A": newX--; break; // Move left
                    case "S": newY++; break; // Move down
                    case "D": newX++; break; // Move right
                }
                // Check if the movement is within the battlefield boundary
                if (newX >= 0 && newX < battlefield[0].length && newY >= 0 && newY < battlefield.length) {
                    if (battlefield[newY][newX] != '■') { // Check if the player hits a wall
                        battlePlayerX = newX;
                        battlePlayerY = newY;

                        // Check what the player encounters at the new position
                        if (battlefield[newY][newX] == '怪') { // Encounter a monster
                            // Start the battle with the monster
                            System.out.println("You encountered a monster!");
                            Monster monster = generateMonster();
                            battle(monster);

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
                System.exit(0); // End the game if the player is defeated
            }
        }
        slept = false; // Player can rest again after the battle is over
    }


    /**
     * Displays the battlefield map with the player's current position.
     * The map includes walls, monsters, and the player, represented by 'Y'.
     * It also shows the player's health, stamina, and gold.
     *
     * @param battlefield The 2D char array representing the battlefield
     * @param battlePlayerX The X-coordinate of the player on the battlefield
     * @param battlePlayerY The Y-coordinate of the player on the battlefield
     * @author Yu Ma
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
        System.out.println("Gold: " + player.getGold());
    }


    /**
     * Moves the monsters randomly on the battlefield and checks if any monster encounters the player.
     * If a monster encounters the player, a battle begins.
     *
     * @param battlefield The 2D char array representing the battlefield
     * @param monsterPositions A list of monster positions represented by int arrays [x, y]
     * @param playerX The X-coordinate of the player on the battlefield
     * @param playerY The Y-coordinate of the player on the battlefield
     * @author Yu Ma
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
                case 0: newY--; break; // Move up
                case 1: newY++; break; // Move down
                case 2: newX--; break; // Move left
                case 3: newX++; break; // Move right
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
                Monster monster = generateMonster();
                battle(monster);

                // Remove the monster after it is defeated
                battlefield[pos[1]][pos[0]] = ' ';
                iterator.remove(); // Exit the loop to avoid modifying the list during iteration
                break;
            }
        }
    }


    /**
     * Generates a new monster with attributes based on the current wave of battles.
     * The monster's name, health, damage, defense, and other attributes are randomized.
     *
     * @return A new Monster object with randomized
     * @author Yu Ma
     */
    private Monster generateMonster() {
        // Array of monster names
        String[] monsterNames = {"Skeleton Archer", "Zombie", "Spider"};
        String name = monsterNames[random.nextInt(monsterNames.length)];

        // Set monster stats based on the current wave
        int health = 180 + wave * 10;
        int damage = 15 + wave * 5;
        int defense = 10 + wave * 2;
        int criticalChance = 10 + wave * 2;
        int dodgeChance = 5 + wave * 2;
        int goldReward = 30 + wave * 5;

        return new Monster(name, health, damage, defense, criticalChance, dodgeChance, goldReward);
    }


    /**
     * Simulates a battle between the player and a monster. The player can choose different attack types,
     * each consuming stamina, or attempt to escape. The battle ends when either the player or the monster
     * is defeated or if the player successfully escapes.
     *
     * @param monster The monster the player is battling
     * @author Yu Ma
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
                            System.out.println("You defeated the " + monster.getName() + "！");
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

            // Check if the player was defeated
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0); // End the game
            }
        }
    }

    /**
     * Calculates and executes an attack on the monster. The attack damage is based on the player's attack power,
     * additional bonus damage, and the monster's defense. It also considers the monster's dodge chance and the
     * player's critical hit chance.
     *
     * @param monster The monster that is being attacked
     * @param extraDamage Additional damage to be added to the player's base attack
     * @author Yu Ma
     */
    private void attackMonster(Monster monster, int extraDamage) {
        // Calculate the total damage considering player's base attack, extra damage, and monster's defense
        int totalDamage = player.attack() + extraDamage - monster.getDefense();

        // Ensure the total damage is not less than zero
        if (totalDamage < 0) totalDamage = 0;

        // Check if the monster dodges the attack
        if (random.nextInt(100) < monster.getDodgeChance()) {
            System.out.println(monster.getName() + " dodged your attack!");
        } else {
            // Check for a critical hit
            if (random.nextInt(100) < player.getCriticalChance()) {
                totalDamage *= 1.5; // Apply critical hit multiplier
                System.out.println("Critical hit! You dealt " + monster.getName() + " " + totalDamage + " points of damage.");
            } else {
                System.out.println("You dealt " + monster.getName() + " " + totalDamage + " points of damage.");
            }
            // Reduce the monster's health by the total damage dealt
            monster.reduceHealth(totalDamage);
        }
    }

    /**
     * Executes the monster's attack on the player. The damage is calculated based on the monster's
     * attack power and the player's defense. It also considers the player's dodge chance and the
     * monster's critical hit chance.
     *
     * @param monster The monster performing the attack
     * @author Yu Ma
     */
    private void monsterAttack(Monster monster) {
        // Calculate the total damage, subtracting player's defense from the monster's attack
        int totalDamage = monster.attack() - player.getDefense();

        // Ensure the total damage is not less than zero
        if (totalDamage < 0) totalDamage = 0;

        // Check if the player dodges the attack
        if (random.nextInt(100) < player.getDodgeChance()) {
            System.out.println("You dodged " + monster.getName() + "'s attack!");
        } else {
            // Check for a critical hit by the monster
            if (random.nextInt(100) < monster.getCriticalChance()) {
                totalDamage *= 1.5; // Apply critical hit multiplier
                System.out.println(monster.getName() + " landed a critical hit on you, dealing " + totalDamage + " points of damage!");
            } else {
                System.out.println(monster.getName() + " dealt " + totalDamage + " points of damage to you.");
            }

            // Reduce the player's health by the total damage dealt
            player.reduceHealth(totalDamage);

            // Display the player's remaining health points
            System.out.println("Your remaining health points: " + player.getHealth());
        }
    }






}
