package com.mygdx.game.desktop.sapiens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.*;
import com.mygdx.game.desktop.animations.ZombieAnimations;
import com.mygdx.game.desktop.coins.Coin;
import com.mygdx.game.desktop.views.View;

import java.util.Random;

/**
 * Class for the all zombies, which follows the player
 * Instances of this class are created by ZombieFactory.
 * This class is used in {@link ZombieAnimations}.
 * This class uses {@link ZombieAnimations} and creates {@link Coin}
 * @author Imad
 * @author David
 */
public class Zombie extends Sapien implements Zombies, FollowsPlayers {
    /**
     * Current coordinates of the {@link Player}
     */
    private int playerX, playerY;
    /**
     * The damage inflicted onto player
     */
    private int damage = 1;
    /**
     * The angle of rendering, used for animations
     */
    private int renderAngle;
    /**
     * Boolean to determine whether the Zombie is moving or not
     */
    private boolean moving;
    /**
     * Instance of {@link ZombieAnimations} which handles rendering
     */
    private ZombieAnimations animations;
    /**
     * boolean to determine whether the zombie has hit player or not
     */
    private boolean hitPlayer;

    /**
     * @param atlas = textureatlas of zombie.
     * @param posX = X coordinate to spawn it in on.
     * @param posY = Y coordinate to spawn it in on.
     * @param scale = Scale of atlas.
     * Constructor for ZombieFactory.
     */
    public Zombie(TextureAtlas atlas, float posX, float posY, float scale, int roundNumber) {
        super(atlas, posX, posY, scale);

        textureAtlas = atlas;

        Random random = new Random();
        int randomInt = random.nextInt(10);

        this.speed = (25 + randomInt*2)*scale;

        health = 1 + roundNumber/3;

        this.renderAngle = 0;

        moving = true;

        this.animations = new ZombieAnimations(View.getInstance().getBatch(), this);

        ZombieSubject.getInstance().attach(this);
        View.getInstance().addSprite(this);
        FollowerSubject.getInstance().attach(this);
    }

    /**
     * Delegates the rendering to Zombie animations class
     * @param batch
     */
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
     * @param x = the players X-coordinate
     * @param y = the players Y-coordinate
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
            die();
        }
    }

    /**
     * Removes itself from the program.
     * Method is called when it has been hit enough times.
     */
    private void die(){
        Random rand = new Random();
        int random = rand.nextInt(4);

        View.getInstance().removeSprite(Zombie.this);
        MovableSubject.getInstance().detach(this);
        ZombieSubject.getInstance().detach(this);
        FollowerSubject.getInstance().detach(this);
        if (random != 0){
            new Coin(getX(),getY(), scale);
        }
    }

    /**
     * This method calculates the distance between the player and the zombie for animations
     * @return int the distance between zombie and player
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