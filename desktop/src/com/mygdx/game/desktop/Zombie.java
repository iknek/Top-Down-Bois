package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Zombie extends Sapien{

    private int playerX;
    private int playerY;


    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        textureAtlas = atlas;
        setRegion(atlas.findRegion("Eric_back"));

        this.speed = 100;

        health = 2;
    }

    protected void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

    @Override
    public void changeSprite() {
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
    public boolean moving(){
        return true;
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
