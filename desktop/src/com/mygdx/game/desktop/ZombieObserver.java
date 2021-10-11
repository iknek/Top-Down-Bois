package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ZombieObserver {
    private List<Zombies> observers = new CopyOnWriteArrayList<>();

    private static ZombieObserver single_instance = null;

    /**
     * Singleton pattern. same as in MovableSubject.java.
     * @return the single instance of the ZombieObserver
     */
    public static ZombieObserver getInstance() {
        if (single_instance == null)
            single_instance = new ZombieObserver();
        return single_instance;
    }

    /**
     * adds the zombie to the list
     * @param o is the zombie which should be added to the list
     */
    public void attach(Zombies o) {
        observers.add(o);
    }

    /**
     * removes a specific zombie from the list
     * @param o is the zombie which we want removed
     */
    public void detach(Zombies o) {
        observers.remove(o);
    }

    /**
     * Calls the playerLocation method which tells the zombie where the player is.
     * This is called in all zombies.
     * @param x is the X-coordinate of the player
     * @param y is the Y-coordinate of the player
     */
    public void playerLocation(int x, int y) {
        for(Zombies o: observers) {
            o.playerLocation(x, y);
        }
    }

    public int playerHit(){
        int counter = 0;
        for(Zombies zombie : observers){
            if(zombie.isHitPlayer()){
                counter++;
                zombie.setHitPlayer(false);
            }
        }
        return counter;
    }
}
