package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CoinSubject {
    private List<Coins> observers = new CopyOnWriteArrayList<>();

    private static CoinSubject single_instance = null;
    private int distance = 25;
    private int speed = 100;

    /**
     * Singleton pattern. same as in MovableSubject.java.
     * @return the single instance of the ZombieObserver
     */
    public static CoinSubject getInstance() {
        if (single_instance == null)
            single_instance = new CoinSubject();
        return single_instance;
    }

    /**
     * adds the zombie to the list
     * @param o is the zombie which should be added to the list
     */
    public void attach(Coins o) {
        observers.add(o);
    }

    /**
     * removes a specific zombie from the list
     * @param o is the zombie which we want removed
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