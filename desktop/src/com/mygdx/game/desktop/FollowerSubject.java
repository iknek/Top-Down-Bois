package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer pattern implementation for the {@link FollowsPlayers} interface
 */
public class FollowerSubject {
    private List<FollowsPlayers> observers = new CopyOnWriteArrayList<>();

    private static FollowerSubject single_instance = null;

    /**
     * Singleton pattern. same as in MovableSubject.java.
     * @return the single instance of the ZombieObserver
     */
    public static FollowerSubject getInstance() {
        if (single_instance == null)
            single_instance = new FollowerSubject();
        return single_instance;
    }

    /**
     * adds the zombie to the list
     * @param o is the zombie which should be added to the list
     */
    public void attach(FollowsPlayers o) {
        observers.add(o);
    }

    /**
     * removes a specific zombie from the list
     * @param o is the zombie which we want removed
     */
    public void detach(FollowsPlayers o) {
        observers.remove(o);
    }

    /**
     * Calls the playerLocation method which tells the zombie where the player is.
     * This is called in all zombies.
     * @param x is the X-coordinate of the player
     * @param y is the Y-coordinate of the player
     */
    public void playerLocation(int x, int y) {
        for(FollowsPlayers o: observers) {
            o.playerLocation(x, y);
        }
    }
}
