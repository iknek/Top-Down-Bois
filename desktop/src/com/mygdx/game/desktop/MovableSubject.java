package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.List;

public class MovableSubject {

    // Singleton mönster
    private static MovableSubject single_instance = null;
    public static MovableSubject getInstance() {
        if (single_instance == null)
            single_instance = new MovableSubject();
        return single_instance;
    }
    private List<Movable> observers = new ArrayList<>();
    private List<Movable> toBeDeleted = new ArrayList<>();

    public List<Movable> getObservers(){
        return observers;
    }

    public void attach(Movable o) {
        observers.add(o);
    }

    public void delete(Movable o){
        toBeDeleted.add(o);
    }
    private void detach(Movable o) {
        observers.remove(o);
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

}
