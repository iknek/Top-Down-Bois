package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.List;

public class MovableSubject {
    private List<Movable> observers = new ArrayList<>();
    private List<Movable> toBeDeleted = new ArrayList<>();

    // Singleton mönster
    private static MovableSubject single_instance = null;

    public static MovableSubject getInstance() {
        if (single_instance == null)
            single_instance = new MovableSubject();
        return single_instance;
    }

    public List<Movable> getObservers(){
        return observers;
    }

    public void attach(Movable o) {
        observers.add(o);
    }
    private void detach(Movable o) {
        observers.remove(o);
    }

    public void delete(Movable o){
        toBeDeleted.add(o);
    }

    public void removeDeleted(){
        for (Movable o : toBeDeleted) {
            detach(o);
        }
    }

    public void notifyUpdate() {
        for(Movable o: observers) {
            o.update();
        }
    }

    public void playerLocation(int x, int y) {
        for(Movable o: observers) {
            o.playerLocation(x, y);
        }
    }
}