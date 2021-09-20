package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public abstract class Sapien extends Sprite implements Movable{
    protected TextureAtlas textureAtlas;

    protected int angle;

    protected int speed;
    protected int health;

    public Sapien(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));
        //textureAtlas = atlas;
        //setRegion(atlas.findRegion("Eric_back"));

        this.setPosition(posX, posY);
        this.setScale(scale);

        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);
    }

    @Override
    public void update() {
        updateAngle();
        changeSprite();
        if (moving()) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    public abstract boolean moving();

    protected abstract void updateAngle();

    public abstract void changeSprite();

    @Override
    public void playerLocation(int x, int y) {

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

    @Override
    public void collide(Rectangle rectangle) {
        translateX(((float)-(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        translateY(((float)-(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
    }

    /*
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
    }*/

    public abstract void getHit();
}