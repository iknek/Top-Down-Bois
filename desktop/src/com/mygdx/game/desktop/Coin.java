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

    public Coin(float posX, float posY, float scale) {
        super(new Texture(Gdx.files.internal("editedCoin.png")));
        this.setPosition(posX,posY);
        this.setScale(scale/2);

        View.getInstance().addSprite(this);
        MovableSubject.getInstance().attach(this);
        //Döp om zombie observer och Zombies till något mer passande, Coin vill också veta var player finns
        //nej
        ZombieObserver.getInstance().attach(this);
    }

    private void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

    @Override
    public void update() {
        updateAngle();
        int speed = 100;
        if (distanceToPlayer() < magnetDistance) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    private int distanceToPlayer() {
        return (int) Math.sqrt(Math.pow((this.getX()-playerX),2) + Math.pow((this.getY()-playerY),2));
    }

    //Kan användas för "enhanced coin magnet" perk
    public void setMagnetDistance(int distance){
        this.magnetDistance = distance;
    }

    @Override
    public void collide(Rectangle rectangle) {
    }

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    public void remove(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
        ZombieObserver.getInstance().detach(this);
    }

    @Override
    public void playerLocation(int x, int y) {
        this.playerX = x;
        this.playerY = y;
    }
}
