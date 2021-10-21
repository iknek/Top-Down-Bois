package com.mygdx.game.desktop.coins;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer pattern implementation for coins.
 */
public class CoinSubject {
    /**
     * list of all coins currently in program.
     */
    private List<Coins> observers = new CopyOnWriteArrayList<>();
    /**
     * Singleton pattern, this is the single instance of this class.
     */
    private static CoinSubject single_instance = null;
    /**
     * the distance from {@link com.mygdx.game.desktop.sapiens.Player} where magnet functionality works
     */
    private int distance = 25;
    /**
     * speed at which coin would move
     */
    private int speed = 100;

    /**
     * Singleton pattern. same as in {@link com.mygdx.game.desktop.MovableSubject}.
     * @return the single instance of the CoinSubject
     */
    public static CoinSubject getInstance() {
        if (single_instance == null)
            single_instance = new CoinSubject();
        return single_instance;
    }

    /**
     * adds the {@link Coin} to the list
     * @param o is the Coin which should be added to the list
     */
    public void attach(Coins o) {
        observers.add(o);
    }

    /**
     * removes a specific {@link Coin} from the list
     * @param o is the Coin which we want removed
     */
    public void detach(Coins o) {
        observers.remove(o);
    }

    public int getDistance() {
        return distance;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Calls the updateMagnet method which changes the magnet distance and power.
     * This is called in all coins.
     * @param distance is the distance of the magnet
     * @param speed is the speed of which the magnet attracts
     */
    public void updateCoins(int distance, int speed) {
        this.distance = distance;
        this.speed = speed;
        for(Coins o: observers) {
            o.updateMagnet(distance, speed);
        }
    }
}