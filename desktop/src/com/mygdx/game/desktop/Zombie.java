package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.Random;

public class Zombie extends Sapien implements Zombies{

    private int playerX;
    private int playerY;
    private int damage = 1;

    /**
     * @param atlas = textureatlas of zombie.
     * @param posX = X coordinate to spawn it in on.
     * @param posY = Y coordinate to spawn it in on.
     * @param scale = Scale of atlas.
     * Constructor for ZombieFactory.
     */

    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Eric";

        textureAtlas = atlas;
        setRegion(atlas.findRegion(name + "_back"));

        Random random = new Random();
        int randomInt = random.nextInt(10);

        this.speed = (25 + randomInt*2)*scale;

        health = 2;

        ZombieObserver.getInstance().attach(this);
        addSprite();
    }

    /**
     * Updates what angle the zombie is moving in.
     */

    protected void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }
    /**
     * Adds sprite to instance of view.
     */
    public void addSprite(){
        View.getInstance().addSprite(this);
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public boolean moving(){
        return true;
    }
    /**
     * Gives zombie player location for pathfinding.
     * @param x
     * @param y
     */
    @Override
    public void playerLocation(int x, int y) {
        playerX = x;
        playerY = y;
    }

    /**
     * @param damage
     * Method for what happens when zombie gets hit. If damage excedes health, zombie is removed from instance
     * and drops a coin.
     */
    public void getHit(int damage){
        health = health - damage;
        if (health <= 0){
            View.getInstance().removeSprite(this);
            MovableSubject.getInstance().detach(this);
            ZombieObserver.getInstance().detach(this);
            new Coin(this.getX(),this.getY(), scale);
        }
    }
}