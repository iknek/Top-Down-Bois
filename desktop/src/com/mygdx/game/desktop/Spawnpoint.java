package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.objects.RectangleMapObject;

/**
 * Class for spawnpoints, holds the position of where zombies can spawn.
 * This class is used by {@link Rounds} and does not use any other classes
 * @author david
 * @author imad
 */
public class Spawnpoint {
    private float x;
    private float y;

    /**
     * Defines spawnpoint position based on map object
     * @param rectangle = rectangle of map object
     * @param scale = scale of rectangle
     */
    public Spawnpoint(RectangleMapObject rectangle, float scale){
        this.x = (rectangle.getRectangle().getX() + rectangle.getRectangle().getWidth()/2)*scale;
        this.y = (rectangle.getRectangle().getY() + rectangle.getRectangle().getHeight()/2)*scale;
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
}