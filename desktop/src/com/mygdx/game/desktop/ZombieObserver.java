package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ZombieObserver {
    private List<Zombies> observers = new CopyOnWriteArrayList<>();

    private static ZombieObserver single_instance = null;

    public static ZombieObserver getInstance() {
        if (single_instance == null)
            single_instance = new ZombieObserver();
        return single_instance;
    }

    public List<Zombies> getObservers(){
        return observers;
    }

    public void attach(Zombies o) {
        observers.add(o);
    }
    public void detach(Zombies o) {
        observers.remove(o);
    }

    public void playerLocation(float x, float y) {
        for(Zombies o: observers) {
            o.playerLocation(x,y);
        }
    }

    public void updatePath() {
        for(Zombies o: observers) {
            o.updatePath();
        }
    }
}
