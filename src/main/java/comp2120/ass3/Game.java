package comp2120.ass3;

import java.util.*;

public class Game {
    private Scanner scanner;
    private Player player;
    private int wave = 1; // 当前波次
    private Random random;
    private boolean slept = false; // 标记玩家是否已经休息

    private GameEngine gameEngine;

    // 地图属性
    private char[][] map;
    public static int playerX;
    public static int playerY;

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
        System.out.println("Press 'Enter' to continue...'");
        scanner.nextLine();
        chooseProfession();
        initializeMap();
        gameLoop();
    }

    /**
     * 允许玩家选择职业
     *
     */
    private void chooseProfession() {
        System.out.println("Please choose your role: ");
        System.out.println("1. Monkey King");
        System.out.println("2. Pigsy");
        System.out.println("3. Sandy");
        System.out.println("4. Tang Monk");
        int choice = scanner.nextInt();
        scanner.nextLine(); // 消除换行符
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
     * 初始化地图
     */
    private void initializeMap() {
        map = gameEngine.getMap(); // 从 GameEngine 获取地图
        playerX = gameEngine.getPlayerX(); // 获取玩家的起始 X 位置
        playerY = gameEngine.getPlayerY(); // 获取玩家的起始 Y 位置
    }

    /**
     * 游戏主循环
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
     * 显示地图
     */
    private void displayMap() {
        // 清屏
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // 显示地图
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (i == playerY && j == playerX) {
                    System.out.print("Y"); // 显示玩家为 'Y'
                } else {
                    System.out.print(map[i][j]); // 显示地图字符
                }
            }
            System.out.println();
        }

        // 显示位置键
        System.out.println("A: Armor Shop, C: Clinic, G: Gate Between Village & Battlefield, H: Home, P: Pet Shop, W: Weapon Shop, Y: You");

        // 显示当前玩家状态
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * 移动玩家
     */
    private void movePlayer(String direction) {
        int newX = playerX;
        int newY = playerY;
        switch (direction) {
            case "W":
                newY--; // 上
                break;
            case "A":
                newX--; // 左
                break;
            case "S":
                newY++; // 下
                break;
            case "D":
                newX++; // 右
                break;
        }

        // 检查边界和墙壁
        if (newX >= 0 && newX < map[0].length && newY >= 0 && newY < map.length) {
            if (map[newY][newX] != '■') {
                // 更新玩家位置
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
     * 处理当前位置的交互
     */
    private void handlePosition() {
        char location = map[playerY][playerX];
        switch (location) {
            case 'H': // 家
                restAtHome();
                break;
            case 'W': // 武器店
                weaponShop();
                break;
            case 'A': // 盔甲店
                armorShop();
                break;
            case 'P': // 宠物店
                petShop();
                break;
            case 'C': // 医院
                hospital();
                break;
            case 'G': // 村庄门
                battleField();
                break;
            default:
                // 其他位置不处理
                break;
        }
    }

    /**
     * 在家休息
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
     * 武器店
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
            scanner.nextLine(); // 消除换行符
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
     * 盔甲店
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
            scanner.nextLine(); // 消除换行符
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
     * 宠物店
     */
    private void petShop() {
        System.out.println("You arrive at the pet shop.");
        System.out.println("Temporarily unavailable.");
        System.out.println("Press 'Enter' to return...");
        scanner.nextLine();
    }

    /**
     * 医院
     */
    private void hospital() {
        System.out.println("You have arrived at the clinic.");
        System.out.println("1. Minor Healing (Restores 20 HP, costs 20 gems)");
        System.out.println("2. Major Healing (Restores 40 HP, costs 50 gems)");
        System.out.println("3. Stamina Recovery (Restores 110 stamina, costs 50 gems)");
        System.out.println("0. Leave the clinic");

        int choice = scanner.nextInt();
        scanner.nextLine(); // 消除换行符
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
     * 战场
     */
    private void battleField() {
        System.out.println("You have left the village and entered the battlefield.");

        // 初始化战场地图
        char[][] battlefield = new char[15][20];

        // 填充战场地图
        for (int i = 0; i < battlefield.length; i++) {
            Arrays.fill(battlefield[i], ' ');
        }

        // 设置边界
        for (int i = 0; i < battlefield.length; i++) {
            battlefield[i][0] = '■'; // 左墙
            battlefield[i][battlefield[0].length - 1] = '■'; // 右墙
        }
        for (int j = 0; j < battlefield[0].length; j++) {
            battlefield[0][j] = '■'; // 上墙
            battlefield[battlefield.length - 1][j] = '■'; // 下墙
        }

        // 设置返回村庄的门
        battlefield[7][0] = 'G'; // 村庄入口

        // 设置玩家的初始位置
        int battlePlayerX = battlefield[0].length - 2; // 列位置
        int battlePlayerY = 7; // 行位置

        // 初始化怪物并设置它们的位置
        List<Monster> monsters = gameEngine.initializeMonsters();
        for (Monster monster : monsters) {
            int monsterX = monster.getX();
            int monsterY = monster.getY();
            battlefield[monsterY][monsterX] = 'M';
        }

        // 玩家在战场中行动
        boolean inBattlefield = true;
        while (inBattlefield) {
            displayBattlefieldMap(battlefield, battlePlayerX, battlePlayerY); // 显示战场地图

            // 提示玩家输入移动或退出指令
            System.out.println("G: Gate Between Village & Battlefield, M: Monster, Y: You");
            System.out.println("Please enter a command (WASD to move, Q to leave the battlefield)");
            String input = scanner.nextLine().toUpperCase();
            if (input.equals("Q")) {
                inBattlefield = false; // 退出战场
                System.out.println("You have returned to the village.");
            } else if (input.equals("W") || input.equals("A") || input.equals("S") || input.equals("D")) {
                // 移动玩家
                int newX = battlePlayerX;
                int newY = battlePlayerY;
                switch (input) {
                    case "W":
                        newY--;
                        break; // 上
                    case "A":
                        newX--;
                        break; // 左
                    case "S":
                        newY++;
                        break; // 下
                    case "D":
                        newX++;
                        break; // 右
                }
                // 检查移动是否有效
                if (newX >= 0 && newX < battlefield[0].length && newY >= 0 && newY < battlefield.length) {
                    if (battlefield[newY][newX] != '■') {
                        battlePlayerX = newX;
                        battlePlayerY = newY;

                        // 检查玩家当前位置
                        if (battlefield[newY][newX] == 'M') { // 遇到怪物
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

                            // 移除怪物
                            battlefield[newY][newX] = ' ';

                            // 检查是否所有怪物都被击败
                            if (monsters.isEmpty()) {
                                System.out.println("You have defeated all the monsters and returned to the village.");
                                inBattlefield = false; // 返回村庄
                            }
                        } else if (battlefield[newY][newX] == 'G') {
                            // 玩家返回村庄
                            System.out.println("You have returned to the village.");
                            inBattlefield = false;
                        }

                        // 移动怪物
                        moveMonsters(battlefield, monsters, battlePlayerX, battlePlayerY);
                    } else {
                        System.out.println("Bumped into a wall, cannot move!");
                    }
                } else {
                    System.out.println("Cannot move there!");
                }
            } else {
                System.out.println("Invalid input, please re-enter.");
            }

            // 检查玩家是否被击败
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
        slept = false; // 玩家可以再次休息
    }

    /**
     * 显示战场地图
     */
    private void displayBattlefieldMap(char[][] battlefield, int battlePlayerX, int battlePlayerY) {
        // 清屏
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // 显示战场地图
        for (int i = 0; i < battlefield.length; i++) {
            for (int j = 0; j < battlefield[0].length; j++) {
                if (i == battlePlayerY && j == battlePlayerX) {
                    System.out.print("Y"); // 玩家
                } else {
                    System.out.print(battlefield[i][j]); // 其他元素
                }
            }
            System.out.println();
        }
        // 显示玩家状态
        System.out.println("Current health points: " + player.getHealth() + "/" + player.getMaxHealth());
        System.out.println("Current stamina: " + player.getStamina() + "/" + player.getMaxStamina());
        System.out.println("Gems: " + player.getGold());
    }

    /**
     * 移动怪物
     */
    private void moveMonsters(char[][] battlefield, List<Monster> monsters, int playerX, int playerY) {
        Iterator<Monster> iterator = monsters.iterator();
        while (iterator.hasNext()) {
            Monster monster = iterator.next();
            int x = monster.getX();
            int y = monster.getY();

            // 清除旧位置
            battlefield[y][x] = ' ';

            // 随机移动怪物
            int newX = x;
            int newY = y;
            int dir = random.nextInt(4);
            switch (dir) {
                case 0:
                    newY--;
                    break; // 上
                case 1:
                    newY++;
                    break; // 下
                case 2:
                    newX--;
                    break; // 左
                case 3:
                    newX++;
                    break; // 右
            }

            // 检查新位置是否有效
            if (newX >= 1 && newX < battlefield[0].length - 1 && newY >= 1 && newY < battlefield.length - 1 && battlefield[newY][newX] == ' ') {
                monster.setMonsterX(newX);
                monster.setMonsterY(newY);
            }

            // 放置怪物在新位置
            x = monster.getX();
            y = monster.getY();
            battlefield[y][x] = 'M';

            // 检查怪物是否遇到玩家
            if (x == playerX && y == playerY) {
                System.out.println("A monster has encountered you!");
                battle(monster);

                // 移除怪物
                battlefield[y][x] = ' ';
                iterator.remove();
                break;
            }
        }
    }

    /**
     * 生成怪物
     */
    private Monster generateMonster(String name) {
        Monster monster = gameEngine.generateMonster(name);
        if (monster == null) {
            System.out.println("Monster not found in configuration. Using default values.");
            monster = new Monster("Default Monster", 100, 10, 5, 5, 5, 20);
        }
        return monster;
    }

    /**
     * 战斗逻辑
     */
    private void battle(Monster monster) {
        System.out.println("You encountered a " + monster.getName() + "!");
        boolean inBattle = true;

        // 循环直到战斗结束
        while (inBattle) {
            // 玩家动作选择
            System.out.println("\nPlease choose your action:");
            System.out.println("1. Light Attack (Damage +25, consumes 2 stamina)");
            System.out.println("2. Normal Attack (Damage +80, consumes 8 stamina)");
            System.out.println("3. Heavy Attack (Damage +160, consumes 20 stamina)");
            System.out.println("4. Escape (Consumes 10 stamina)");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消除换行符

            switch (choice) {
                case 1:
                    // 轻攻击
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
                    // 普通攻击
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
                    // 重攻击
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
                    // 逃跑
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

            // 检查玩家是否被击败
            if (player.getHealth() <= 0) {
                System.out.println("You were defeated, game over.");
                System.exit(0);
            }
        }
    }

    /**
     * 攻击怪物
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
     * 怪物攻击
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
     * 显示玩家状态
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
     * 管理库存
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
            scanner.nextLine(); // 消除换行符
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
     * 装备武器
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
        scanner.nextLine(); // 消除换行符
        if (choice > 0 && choice <= weapons.size()) {
            Weapon selectedWeapon = weapons.get(choice - 1);
            inventory.equipWeapon(selectedWeapon);
            System.out.println("You equipped: " + selectedWeapon.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }

    /**
     * 装备盔甲
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
        scanner.nextLine(); // 消除换行符
        if (choice > 0 && choice <= armors.size()) {
            Armor selectedArmor = armors.get(choice - 1);
            inventory.equipArmor(selectedArmor);
            System.out.println("You equipped: " + selectedArmor.getName());
        } else {
            System.out.println("Invalid choice.");
        }
    }
}
