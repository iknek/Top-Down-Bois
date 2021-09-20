package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Zombie extends Sprite implements Movable{
    private TextureAtlas textureAtlas;
    private int angle;
    private int playerX;
    private int playerY;
    private int health;

    private int speed = 100;


    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        setRegion(atlas.findRegion("Eric_back"));

        this.setPosition(posX, posY);
        this.setScale(scale);

        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);

        health = 2;
    }

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    public void setPosition(float posX, float posY){
        this.setX(posX);
        this.setY(posY);
    }

    private void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

    public void changeSpriteAngle() {
        if (315 <= angle && angle <= 360 || 0 <= angle && angle < 45) {
            this.setRegion(this.textureAtlas.findRegion("Eric_forward"));
        }
        if (45 <= angle && angle < 135) {
            this.setRegion(this.textureAtlas.findRegion("Eric_right"));
        }
        if (135 <= angle && angle < 225) {
            this.setRegion(this.textureAtlas.findRegion("Eric_back"));
        }
        if (225 <= angle && angle < 315) {
            this.setRegion(this.textureAtlas.findRegion("Eric_left"));
        }
    }

    @Override
    public void update() {
        updateAngle();
        changeSpriteAngle();
        translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void collide(Rectangle rectangle) {
        translateX(-((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        translateY(-((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void playerLocation(int x, int y) {
        playerX = x;
        playerY = y;
    }

    public void getHit(){
        health -=1;
        if (health == 0){
            View.getInstance().removeSprite(this);
            MovableSubject.getInstance().delete(this);
        }
    }
}
