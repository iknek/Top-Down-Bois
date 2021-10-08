package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

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
    public void checkCollisions(View view, Player player, float scale){
        boolean playerHit = false;

        MovableSubject movableSubject = MovableSubject.getInstance();

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
                    if(checkZombieCollisions(player, (Zombie)observer)){playerHit = true;}
                }

                if (observer instanceof Coin) {
                    if(Intersector.overlaps(player.getBoundingRectangle(), observer.getBoundingRectangle())){
                        player.coinGained();
                        ((Coin) observer).remove();
                    }
                }
            }
            rectangle = scaleBackRectangle(rectangle, scale);
        }
        player.setPlayerHit(playerHit);
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
     * @param player the instance of player in the program
     * @param zombie the zombie instance which is being checked
     */
    private boolean checkZombieCollisions(Player player, Zombie zombie){
        for (Movable o: MovableSubject.getInstance().getObservers()) {
            if(o instanceof Projectile && View.getInstance().getSprites().contains(o)){
                zombieGetShot(o, zombie);
            }
            if(o instanceof Zombie && o != zombie){
                zombieCollideZombie(o, zombie);
            }
        }
        if(Intersector.overlaps(player.getBoundingRectangle(), zombie.getBoundingRectangle())){
            player.getHit(zombie.getDamage());
            return true;
        }
        return false;
    }

    /**
     * Checks if a specific zombie is colliding with the player
     * @param player the instance of player
     * @param zombie the zombie which is being checked
     * @return a boolean of whether there is a collision
     */
    private boolean checkPlayerZombieCollision(Player player, Zombie zombie){
        boolean hit = false;
        if(Intersector.overlaps(player.getBoundingRectangle(), zombie.getBoundingRectangle())){
            hit = true;
        }
        return hit;
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

    /**
     * Handles logic of when zombies collide with one another. (They are pushed apart)
     * @param o a zombie
     * @param zombie another zombie
     */
    private void zombieCollideZombie(Movable o, Zombie zombie){
        if(Math.pow((o.getX() - zombie.getX()),2) < 9 && Math.pow((o.getY() - zombie.getY()),2) < 9){
            Random random = new Random();
            int direction = random.nextInt(4);
            switch(direction){
                case 1: zombie.translateX(1);
                case 2: zombie.translateX(-1);
                case 3: zombie.translateY(1);
                case 4: zombie.translateY(-1);
            }
        }
    }
}