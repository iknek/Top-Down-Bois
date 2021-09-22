package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionController {

    public void checkCollisionRectangle(View view, Player player, int scale){
        MovableSubject movableSubject = MovableSubject.getInstance();
        //int objectLayerId = 2;

        MapLayer collisionObjectLayer = view.getMap().getLayers().get("collision");
        MapObjects objects = collisionObjectLayer.getObjects();

        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            rectangle = scaleRectangle(rectangle, scale);

            for (Movable observer : movableSubject.getObservers()){
                if (Intersector.overlaps(rectangle, observer.getBoundingRectangle())){
                    observer.collide(rectangle);
                }

                if(observer instanceof Zombie){
                    checkZombieCollisions(player, (Zombie)observer);
                }
            }

            movableSubject.removeDeleted();

            rectangle = scaleBackRectangle(rectangle, scale);
        }
    }

    private Rectangle scaleRectangle(Rectangle rect, int scale){
        rect.x = rect.x*scale;
        rect.y = rect.y*scale;
        rect.width = rect.width*scale;
        rect.height = rect.height*scale;
        return rect;
    }

    private Rectangle scaleBackRectangle(Rectangle rect, int scale){
        rect.x = rect.x/scale;
        rect.y = rect.y/scale;
        rect.width = rect.width/scale;
        rect.height = rect.height/scale;
        return rect;
    }

    private void checkZombieCollisions(Player player, Zombie zombie){
        for (Movable bullet: MovableSubject.getInstance().getObservers()) {
            if(bullet instanceof Projectile && View.getInstance().getSprites().contains(bullet)){
                if(Intersector.overlaps(bullet.getBoundingRectangle(), zombie.getBoundingRectangle())) {
                    zombie.getHit();
                    bullet.collide(zombie.getBoundingRectangle());
                }
            }
        }

        if(Intersector.overlaps(player.getBoundingRectangle(), zombie.getBoundingRectangle())){
            player.getHit();
        }
    }
}
