package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class CollisionController {

    public void checkCollisionRectangle(View view, Player player){
        MovableSubject movableSubject = MovableSubject.getInstance();
        int objectLayerId = 2;

        MapLayer collisionObjectLayer = view.getMap().getLayers().get(objectLayerId);
        MapObjects objects = collisionObjectLayer.getObjects();

        // there are several other types, Rectangle is probably the most common one
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            rectangle = scaleRectangle(rectangle);

            for (Movable observer : movableSubject.getObservers()){
                if (Intersector.overlaps(rectangle, observer.getBoundingRectangle())){
                    observer.collide(rectangle);
                }

                if(observer instanceof Zombie){
                    checkZombieCollisions(player, (Zombie)observer);
                }
            }

            movableSubject.removeDeleted();

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

    private void checkZombieCollisions(Player player, Zombie zombie){
        for (Movable bullet: MovableSubject.getInstance().getObservers()) {
            if(bullet instanceof Projectile){
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
