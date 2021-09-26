package com.mygdx.game.desktop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MovableSubject {
    //Copy on write list prevents exceptions when list is modified while being iterated on.
    private List<Movable> observers = new CopyOnWriteArrayList<>();
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
    public void detach(Movable o) {
        observers.remove(o);
    }

    public void notifyUpdate() {
        for(Movable o: observers) {
            o.update();
        }
    }
}