package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implementation of the observer pattern for {@link Zombies} interface.
 */
public class ZombieSubject {
    private List<Zombies> observers = new CopyOnWriteArrayList<>();

    private static ZombieSubject single_instance = null;

    /**
     * Singleton pattern. same as in MovableSubject.java.
     * @return the single instance of the ZombieObserver
     */
    public static ZombieSubject getInstance() {
        if (single_instance == null)
            single_instance = new ZombieSubject();
        return single_instance;
    }

    public List<Zombies> getObservers() {
        return new CopyOnWriteArrayList<>(observers);
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
     * Returns count of how many times zombie was hit, for calculating damage/death.
     * @return int hit counter
     */
    public int playerHit(){
        int counter = 0;
        for(Zombies zombie : observers){
            if(zombie.isHitPlayer()){
                counter+= zombie.getDamage();
                zombie.setHitPlayer(false);
            }
        }
        return counter;
    }
}
