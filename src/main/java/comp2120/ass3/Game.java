package comp2120.ass3;

import java.util.*;

public class Game {
    private Scanner scanner;
    private Player player;
    private int wave = 1; // 当前波次
    private Random random;
    private boolean slept = false; // 是否已经休息过

    // 地图相关
    private char[][] map; // 村庄地图
    private int playerX; // 玩家X坐标
    private int playerY; // 玩家Y坐标

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

    private void chooseProfession() {}

    private void initializeMap() {}

    private void gameLoop() {}

}
