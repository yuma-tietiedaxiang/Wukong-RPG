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
                //TODO manageInventory();

            } else if (input.equals("P")) {// If the player wants to check their status, show the status
                //TODO showStatus();

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
        char location = map[playerY][playerX]; // Get the character at the player's position
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
     * Allows the player to rest only once per visit.
     * @author Yu Ma
     */
    private void restAtHome() {
        if (!slept) {// The player hasn't rested, here slept==false
            System.out.println("You rest at home, recovering stamina and health points.");
            player.rest(); // Recover player's health and stamina
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
     * Manages interactions within the weapon shop.
     * Allows the player to view and purchase weapons.
     */
    private void weaponShop() {
        System.out.println("You arrive at the weapon shop.");
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(new Weapon("Bamboo Arrow", 70, 25));
        weapons.add(new Weapon("Iron Arrow", 130, 35));
        weapons.add(new Weapon("Fire Arrow", 220, 55));

        boolean inShop = true;
        while (inShop) {
            System.out.println("Your gem: " + player.getGold());
            System.out.println("Please select a weapon to purchase: ");
            int index = 1;
            for (Weapon weapon : weapons) {
                System.out.println(index + ". " + weapon.getName() + " - price: " + weapon.getPrice() + " Gems, weapon damage: " + weapon.getDamage());
                index++;
            }
            System.out.println("0. Leave the shop");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
            if (choice == 0) {
                inShop = false; // Exit the shop
            } else if (choice > 0 && choice <= weapons.size()) {
                Weapon selectedWeapon = weapons.get(choice - 1);
                if (player.spendGold(selectedWeapon.getPrice())) {
                    player.getInventory().addItem(selectedWeapon); // Add weapon to inventory
                    System.out.println("You purchased: " + selectedWeapon.getName());
                } else {
                    System.out.println("Insufficient gems, unable to purchase the weapon.");
                }
            } else {
                System.out.println("Invalid selection, please re-enter.");
            }
        }
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine(); // Wait for user input
    }

    /**
     * Manages interactions within the armor shop.
     * Allows the player to view and purchase armor.
     */
    private void armorShop() {
        System.out.println("You arrive at the armor shop.");
        List<Armor> armors = new ArrayList<>();
        armors.add(new Armor("Wooden Shield", 80, 11));
        armors.add(new Armor("Iron Pants", 90, 7));
        armors.add(new Armor("Diamond Armor", 200, 16));

        boolean inShop = true;
        while (inShop) {
            System.out.println("Your Gem: " + player.getGold());
            System.out.println("Please select the armor you want to buy: ");
            int index = 1;
            for (Armor armor : armors) {
                System.out.println(index + ". " + armor.getName() + " - price: " + armor.getPrice() + " Gems, defense: " + armor.getDefense());
                index++;
            }
            System.out.println("0. Leave the shop");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character
            if (choice == 0) {
                inShop = false; // Exit the shop
            } else if (choice > 0 && choice <= armors.size()) {
                Armor selectedArmor = armors.get(choice - 1);
                if (player.spendGold(selectedArmor.getPrice())) {
                    player.getInventory().addItem(selectedArmor); // Add armor to inventory
                    System.out.println("You purchased: " + selectedArmor.getName());
                } else {
                    System.out.println("Insufficient gems, unable to purchase the armor.");
                }
            } else {
                System.out.println("Invalid choice, please enter again.");
            }
        }
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine(); // Wait for user input
    }

    /**
     * Displays a message indicating that the pet shop is currently unavailable.
     */
    private void petShop() {
        System.out.println("You arrive at the pet shop.");
        System.out.println("Temporarily unavailable.");
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine(); // Wait for user input
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
        scanner.nextLine(); // Clear the newline character
        switch (choice) {
            case 1:
                if (player.spendGold(20)) {
                    player.heal(20); // Heal the player
                    System.out.println("Healing successful! Current health points: " + player.getHealth());
                } else {
                    System.out.println("Insufficient gems, unable to heal.");
                }
                break;
            case 2:
                if (player.spendGold(50)) {
                    player.heal(40); // Heal the player
                    System.out.println("Healing successful! Current health points: " + player.getHealth());
                } else {
                    System.out.println("Insufficient gems, unable to heal.");
                }
                break;
            case 3:
                if (player.spendGold(50)) {
                    player.restoreStamina(110); // Restore stamina
                    System.out.println("Stamina recovery successful! Current stamina value: " + player.getStamina());
                } else {
                    System.out.println("Insufficient gems, unable to restore stamina.");
                }
                break;
            case 0:
                break; // Exit the clinic
            default:
                System.out.println("Invalid selection.");
                break;
        }
        System.out.println("Press Enter to return...");
        scanner.nextLine(); // Wait for user input
    }

    /**
     * Placeholder for managing interactions in the battlefield.
     * Currently does not contain any functionality.
     */
    private void battleField() {
        // Implementation will be added later
        //TODO battleField
    }




}
