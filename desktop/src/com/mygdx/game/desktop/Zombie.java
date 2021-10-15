package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.Random;

public class Zombie extends Sapien implements Zombies, FollowsPlayers{

    private int playerX;
    private int playerY;
    private int damage = 1;
    private int renderAngle;
    private boolean moving;
    private ZombieAnimations animations;
    private boolean hitPlayer;

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
        this.renderAngle = 0;

        moving = true;

        this.animations = new ZombieAnimations(View.getInstance().getBatch(), this);

        ZombieObserver.getInstance().attach(this);
        View.getInstance().addSprite(this);
        FollowerObserver.getInstance().attach(this);
    }

    @Override
    public void draw(Batch batch) {
        animations.render();
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

        if((angle > 315 && angle < 360) || (angle >= 0 && angle < 45)){ renderAngle = 0;}
        if(angle > 45 && angle < 135){ renderAngle = 90;}
        if(angle > 135 && angle < 225){ renderAngle = 180;}
        if(angle > 225 && angle < 315){ renderAngle = 270;}
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
            View.getInstance().removeSprite(Zombie.this);
            MovableSubject.getInstance().detach(Zombie.this);
            ZombieObserver.getInstance().detach(Zombie.this);
            new Coin(getX(),getY(), scale);
        }
    }

    /**
     * This method calculates the distance between the player and the zombie for animations
     * @return int
     */
    public int nearPlayer() {
        return (int) Math.sqrt(Math.pow((this.getX()-playerX),2) + Math.pow((this.getY()-playerY),2));
    }

    public int getDamage(){
        return damage;
    }
    public boolean isHitPlayer() {
        return hitPlayer;
    }
    public int getRenderAngle(){
        return renderAngle;
    }

    public void setHitPlayer(boolean hitPlayer) {
        this.hitPlayer = hitPlayer;
    }
    public void setMoving(boolean bool){
        this.moving = bool;
    }

    @Override
    public boolean moving(){
        return moving;
    }
}