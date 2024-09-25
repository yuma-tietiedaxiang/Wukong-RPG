package comp2120.ass3.mock;

import comp2120.ass3.IMonster;

/**
 * Mock of IMonster for test.
 * @author Fan Yu
 */
public class MockMonster implements IMonster {
    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void move(int offsetX, int offsetY) {

    }

    @Override
    public int getMaxHealth() {
        return 0;
    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public void reduceHealth(int amount) {

    }

    @Override
    public int attack() {
        return 0;
    }

    @Override
    public int getDamage() {
        return 0;
    }

    @Override
    public int getDefense() {
        return 0;
    }
}
