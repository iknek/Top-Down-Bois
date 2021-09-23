package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.objects.RectangleMapObject;

public class Spawnpoint {
    private float x;
    private float y;

    public Spawnpoint(RectangleMapObject rectangle, int scale){
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