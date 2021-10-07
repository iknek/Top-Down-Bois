package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Coin extends Sprite implements Movable, Zombies{

    private int angle;
    private int playerX;
    private int playerY;
    private int magnetDistance = 50;
    private int speed;

    /**
     * Constructor for the Coin class
     * Sets the texture as well as intial position
     * @param posX is the initial X-coordinate of the coin
     * @param posY is the initial Y-coordinate of the coin
     * @param scale is the program scale so that the coin is the correct size relative to everything else
     */
    public Coin(float posX, float posY, float scale) {
        super(new Texture(Gdx.files.internal("editedCoin.png")));
        this.setPosition(posX,posY);
        this.setScale(scale/2);

        this.speed = 100;

        View.getInstance().addSprite(this);
        MovableSubject.getInstance().attach(this);
        //Döp om zombie observer och Zombies till något mer passande, Coin vill också veta var player finns
        //nej
        ZombieObserver.getInstance().attach(this);
    }

    /**
     * This method updates the angle so that it will move towards the player
     */
    private void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

    /**
     * This method moves the coin towards the player.
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
     * This method calculates the distance between the player and the coin so that there is a magnet distance
     * @return
     */
    private int distanceToPlayer() {
        return (int) Math.sqrt(Math.pow((this.getX()-playerX),2) + Math.pow((this.getY()-playerY),2));
    }

    /**
     * This method is a setter for the strength of the magnet
     * @param distance is the strength we want the magnet to be
     */
    //Kan användas för "enhanced coin magnet" perk
    public void setMagnetDistance(int distance){
        this.magnetDistance = distance;
    }

    /**
     * This method handles the logic when a coin collides with collision objects of the map
     * @param rectangle
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

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    /**
     * This method removes the instance of the coin from the program by removing it from all lists.
     * This means that it will not render or move anymore
     */
    public void remove(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
        ZombieObserver.getInstance().detach(this);
    }

    /**
     * Gives the coin player location for pathfinding.
     * @param x is the X-coordinate of the player
     * @param y is the Y-coordinate of the player
     */
    @Override
    public void playerLocation(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }
}
