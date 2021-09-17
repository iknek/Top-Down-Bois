package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionController {

    public void checkCollisionRectangle(Renderer renderer){
        MovableSubject movableSubject = MovableSubject.getInstance();
        int objectLayerId = 2;
        MapLayer collisionObjectLayer = renderer.getMap().getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();
        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            //System.out.println(rectangle.y);
            //System.out.println(player.getBoundingRectangle().y);
            rectangle = scaleRectangle(rectangle);
            for (Movable observer : movableSubject.getObservers()){
                if (Intersector.overlaps(rectangle, observer.getBoundingRectangle())){
                    observer.collide(rectangle);
                }
            }
            rectangle = scaleBackRectangle(rectangle);
        }
    }

    private Rectangle scaleRectangle(Rectangle rect){
        rect.x = rect.x*2;
        rect.y = rect.y*2;
        rect.width = rect.width*2;
        rect.height = rect.height*2;
        return rect;
    }

    private Rectangle scaleBackRectangle(Rectangle rect){
        rect.x = rect.x/2;
        rect.y = rect.y/2;
        rect.width = rect.width/2;
        rect.height = rect.height/2;
        return rect;
    }
}
