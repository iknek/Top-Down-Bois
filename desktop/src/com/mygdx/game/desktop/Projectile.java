package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Projectile extends Sprite {
    TextureAtlas textureAtlas;
    TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
    int projectileSpeed;
    float angleOfDirection;
    float xPos;
    float yPos;

    static float xSpeed;
    static float ySpeed;

    public Projectile(double projectileSpeed, float angleOfDirection, float posX, float posY) {
        textureAtlas = atlas;
        setRegion(textureAtlas.findRegion("bullet"));
        this.setPosition(posX, posY);
        spawnProjectile();
        xSpeed = (float) Math.cos(projectileSpeed);
        ySpeed = (float) Math.sin(projectileSpeed);
    }

    private void spawnProjectile() {

    }

    public void updateMotion(){
        xPos += xSpeed * Gdx.graphics.getDeltaTime();
        yPos += ySpeed * Gdx.graphics.getDeltaTime();
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
}
