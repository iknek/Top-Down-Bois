package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class  Zombie extends Sapien{

    private int playerX;
    private int playerY;


    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Eric";
        textureAtlas = atlas;
        setRegion(atlas.findRegion(name + "_back"));

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
