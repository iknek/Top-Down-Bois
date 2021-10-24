package com.mygdx.game.desktop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Observer implementation for the {@link Movable} interface
 * This class is used in {@link Model}.
 * This class uses {@link Movable}.
 * @author Anders
 */
public class MovableSubject {
    //Copy on write list prevents exceptions when list is modified while being iterated on.
    private List<Movable> observers = new CopyOnWriteArrayList<>();

    private static MovableSubject single_instance = null;

    /**
     * Singleton pattern. Either creates a new MovableSubject or returns the single instance of the class.
     * This means that there is always only one instance of the class
     * @return the single instance of the class
     */
    public static MovableSubject getInstance() {
        if (single_instance == null)
            single_instance = new MovableSubject();
        return single_instance;
    }

    /**
     * a getter for the list of movable subjects
     * @return the list of observers
     */
    public List<Movable> getObservers(){
        return new CopyOnWriteArrayList<>(observers);
    }

    /**
     * Adds a movable subject to the list
     * @param o is the object that will be added
     */
    public void attach(Movable o) {
        observers.add(o);
    }

    /**
     * Removes a specific object from the observer list
     * @param o is the object which is going to be removed
     */
    public boolean detach(Movable o) {
        return observers.remove(o);
    }

    /**
     * This method implements the observer pattern. Because of the interface Movable all observers have an update method
     * which is called in render in TiledTestTwo
     */
    public void notifyUpdate() {
        for(Movable o: observers) {
            o.update();
        }
    }
}