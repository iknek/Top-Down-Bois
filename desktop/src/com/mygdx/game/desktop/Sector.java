package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;


public class Sector implements Sectors{
    private int X;
    private int Y;

    private int width;
    private int height;

    private boolean movable;

    public Sector(int x, int y, int w, int h){
        this.X = x;
        this.Y = y;

        this.width = w/16;
        this.height = h/16;

        this.movable = true;
    }

    public void checkMovable(float scale){
        Rectangle boundingRectangle = new Rectangle(X,Y,width,height);

        MapLayer collisionObjectLayer = View.getInstance().getMap().getLayers().get("collision");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            com.badlogic.gdx.math.Rectangle rectangle = rectangleObject.getRectangle();
            rectangle = CollisionController.scaleRectangle(rectangle, scale);

            if(Intersector.overlaps(boundingRectangle, rectangle)){
                setMovable(false);
            }

            rectangle = CollisionController.scaleBackRectangle(rectangle, scale);
        }
    }

    public void setMovable(boolean movable){
        this.movable = movable;
    }

    public boolean getMovable(){
        return movable;
    }

    @Override
    public List<Sector> getNeighbours() {
        return null;
    }

    public int getX(){
        return X;
    }

    public int getY(){
        return Y;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}
