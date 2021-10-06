package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.Random;

public class Zombie extends Sapien implements Zombies{

    private int playerX;
    private int playerY;
    private int damage = 1;

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

    protected void updateAngle() {
        angle = (int) Math.toDegrees(Math.atan2(playerY - getY(), getX()-playerX));
        angle -= 90;
        if(angle < 0){
            angle += 360;
        }
    }

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

    @Override
    public void playerLocation(int x, int y) {
        playerX = x;
        playerY = y;
    }

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