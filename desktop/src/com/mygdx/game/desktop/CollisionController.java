package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.coins.Coin;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.views.View;

/**
 * This class controls collision logic between the map and different objects of the {@link Movable} interface
 * As well as between the {@link Movable} objects themselves
 */
public class CollisionController {

    /**
     * Checks for collision between the map objects and sprites, and between the sprites themselves
     * First it finds the collision layer of the map and extracts the objects from it.
     * For some unknown reason, the collision objects of the map are not scaled with the rest of the map and so are manually scaled here
     * Then it goes through all movable subjects and checks if they overlap with the collision objects(have collided with)
     * If the movableSubject is a Zombie it checks the specific collisions for the Zombie
     * And if it is a coin it checks if it is being picked up by the player
     * @param view the instance of View which contains the map
     * @param player the instance of player
     * @param scale the scale of the whole program
     */
    public void checkCollisions(View view, Player player, float scale) {

        MovableSubject movableSubject = MovableSubject.getInstance();

        MapLayer collisionObjectLayer = view.getMap().getLayers().get("collision");
        MapObjects objects = collisionObjectLayer.getObjects();

        for (Movable observer : movableSubject.getObservers()) {
            //Collision on map
            for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)){
                Rectangle rectangle = rectangleObject.getRectangle();
                rectangle = scaleRectangle(rectangle, scale);

                if (Intersector.overlaps(rectangle, observer.getBoundingRectangle())) {
                    observer.collide(rectangle);
                }

                rectangle = scaleBackRectangle(rectangle, scale);
            }

            //Collision between zombies and other moving objects
            if (observer instanceof Zombie) {
                checkZombieCollisions((Zombie) observer);
            }

            //Coin potentially being picked up
            if (observer instanceof Coin) {
                if (Intersector.overlaps(player.getBoundingRectangle(), observer.getBoundingRectangle())) {
                    player.coinGained();
                    ((Coin) observer).remove();
                }
            }
        }
    }

    /**
     * Scales the collision objects up to the correct size
     * @param rect the selected collision object
     * @param scale the scale of the whole program
     * @return the new collision object rectangle
     */
    private Rectangle scaleRectangle(Rectangle rect, float scale){
        rect.x = rect.x*scale;
        rect.y = rect.y*scale;
        rect.width = rect.width*scale;
        rect.height = rect.height*scale;
        return rect;
    }

    /**
     * Scales the collision object back to its original size
     * @param rect the collision object in question
     * @param scale the scale of the whole program
     * @return the updated collision object rectangle
     */
    private Rectangle scaleBackRectangle(Rectangle rect, float scale){
        rect.x = rect.x/scale;
        rect.y = rect.y/scale;
        rect.width = rect.width/scale;
        rect.height = rect.height/scale;
        return rect;
    }

    /**
     * This method checks all collisions which have to do with the Zombies such as getting shot by a bullet or colliding
     * with the player or other zombies
     * @param zombie the zombie instance which is being checked
     */
    private void checkZombieCollisions(Zombie zombie){
        for (Movable o: MovableSubject.getInstance().getObservers()) {
            if(o instanceof Projectile && View.getInstance().getSprites().contains(o)){
                zombieGetShot(o, zombie);
            }
        }
    }

    /**
     * Checks if a zombie has been shot and delegates to the instances what to do after
     * @param o a bullet
     * @param zombie a zombie instance
     */
    private void zombieGetShot(Movable o, Zombie zombie){
        if(Intersector.overlaps(o.getBoundingRectangle(), zombie.getBoundingRectangle())) {
            zombie.getHit(((Projectile) o).getDamage());
            o.collide(zombie.getBoundingRectangle());
        }
    }
}