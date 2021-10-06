package com.mygdx.game.desktop;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;

public class CollisionController {

    public void checkCollisions(View view, Player player, float scale){
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
                    checkZombieCollisions(player, (Zombie)observer);
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
    }

    private Rectangle scaleRectangle(Rectangle rect, float scale){
        rect.x = rect.x*scale;
        rect.y = rect.y*scale;
        rect.width = rect.width*scale;
        rect.height = rect.height*scale;
        return rect;
    }

    private Rectangle scaleBackRectangle(Rectangle rect, float scale){
        rect.x = rect.x/scale;
        rect.y = rect.y/scale;
        rect.width = rect.width/scale;
        rect.height = rect.height/scale;
        return rect;
    }

    private void checkZombieCollisions(Player player, Zombie zombie){
        for (Movable o: MovableSubject.getInstance().getObservers()) {
            if(o instanceof Projectile && View.getInstance().getSprites().contains(o)){
                zombieGetShot(o, zombie, ((Projectile) o).getDamage());
            }

            if(o instanceof Zombie && o != zombie){
                zombieCollideZombie(o, zombie);
            }
        }

        if(Intersector.overlaps(player.getBoundingRectangle(), zombie.getBoundingRectangle())){
            player.getHit(zombie.getDamage());
        }
    }

    public boolean playerZombieCollision(Player player) {
        boolean hit = false;
        MovableSubject movableSubject = MovableSubject.getInstance();
        for (Movable observer : movableSubject.getObservers()) {
            if (observer instanceof Zombie) {
                if(checkPlayerZombieCollision(player, (Zombie) observer)){
                    hit = true;
                }
            }
        }
        return hit;
    }

    private boolean checkPlayerZombieCollision(Player player, Zombie zombie){
        boolean hit = false;
        if(Intersector.overlaps(player.getBoundingRectangle(), zombie.getBoundingRectangle())){
            hit = true;
        }
        return hit;
    }

    private void zombieGetShot(Movable o, Zombie zombie, int damage){
        if(Intersector.overlaps(o.getBoundingRectangle(), zombie.getBoundingRectangle())) {
            zombie.getHit(damage);
            o.collide(zombie.getBoundingRectangle());
        }
    }

    private void zombieCollideZombie(Movable o, Zombie zombie){
        if(Math.pow((o.getX() - zombie.getX()),2) < 9 && Math.pow((o.getY() - zombie.getY()),2) < 9){
            Random random = new Random();
            int direction = random.nextInt(4);
            switch(direction){
                case 1: zombie.translateX(4);
                case 2: zombie.translateX(-4);
                case 3: zombie.translateY(4);
                case 4: zombie.translateY(-4);
            }
        }
    }
}