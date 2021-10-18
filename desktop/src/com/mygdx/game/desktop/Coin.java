package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.sapiens.Player;
import com.mygdx.game.desktop.sapiens.Zombie;
import com.mygdx.game.desktop.views.View;


/**
 * A Coin drops from killed {@link Zombie} objects, and slowly move towards the {@link Player}.
 * Upon collision with the player, this object disappears and increases the player's currency counter.
 * The Coins movement towards the player is referred to as the Coins "magnet".
 */
public class Coin extends Sprite implements Movable, FollowsPlayers{
    /**
     * Angle for this objects directional movement.
     */
    private int angle;
    /**
     * {@link Player} objects current x-coordinate.
     */
    private int playerX;
    /**
     * {@link Player} objects current x-coordinate.
     */
    private int playerY;
    /**
     * Distance for this object to move towards the {@link Player}.
     */
    private int magnetDistance = 25;
    /**
     * Speed for this object.
     */
    private int speed;


    /**
     * Constructor for the {@link Coin} class.
     * Sets the {@link Texture} and initial position for this object.
     * @param posX initial X-coordinate of this object
     * @param posY initial Y-coordinate of this object
     * @param scale program scale so that this object has the correct size relative to everything else
     */
    public Coin(float posX, float posY, float scale) {
        super(new Texture(Gdx.files.internal("editedCoin.png")));
        this.setPosition(posX,posY);
        this.setScale(scale/2);
        this.magnetDistance *= scale;

        this.speed = 100;

        View.getInstance().addSprite(this);
        MovableSubject.getInstance().attach(this);
        FollowerSubject.getInstance().attach(this);
    }

    /**
     * Updates the angle towards the {@link Player} objects position.
     */
    private void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

    /**
     * Moves this object towards the {@link Player}.
     */
    @Override
    public void update() {
        updateAngle();
        if (distanceToPlayer() < magnetDistance) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    /**
     * Calculates the distance between the {@link Player} and this object for the magnet.
     * @return distance between player and this object
     */
    private int distanceToPlayer() {
        return (int) Math.sqrt(Math.pow((this.getX()-playerX),2) + Math.pow((this.getY()-playerY),2));
    }

    /**
     * Sets the strength of the magnet.
     * @param distance desired strength of the magnet
     */
    // Kan användas för "enhanced coin magnet" perk
    // Döpa om till "setMagnetStrength"?
    public void setMagnetDistance(int distance){
        this.magnetDistance = distance;
    }

    /**
     * Handles collision between this object and other collision objects on the map.
     * @param rectangle object colliding with this object.
     */
    @Override
    public void collide(Rectangle rectangle) {
        translateX(((float)-(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        translateY(((float)-(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));

        boolean insideX = !((getX()+getWidth())<rectangle.getX()||(rectangle.getX()+rectangle.getWidth())<getX());
        boolean insideY = !(getY()+(getHeight()/2)<rectangle.getY()||rectangle.getY()+rectangle.getHeight()<getY());

        if(insideY && !insideX){
            translateY((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime());
        } else if(insideX && !insideY){
            translateX((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime());
        }
        if(!insideX && !insideY){
            if((getX()+getWidth()) < rectangle.getX()){ translateX(-1); }
            if((rectangle.getX()+rectangle.getWidth()) < getX()){ translateX(1); }
            if(getY()+(getHeight()/2) < rectangle.getY()){ translateY(-1); }
            if(rectangle.getY()+rectangle.getHeight() < getY()){ translateY(1); }
        }
    }

    /**
     * Removes the instance of this object from the program by removing it from all lists, stopping it from rendering or moving.
     */
    public void remove(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
        FollowerSubject.getInstance().detach(this);
    }

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    /**
     * Gives this object the {@link Player} location for pathfinding.
     * @param x x-coordinate of the player
     * @param y y-coordinate of the player
     */
    @Override
    public void playerLocation(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }
}
