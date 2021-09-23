package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Sprite implements Movable {
    private double xSpeed;
    private double ySpeed;
    private int damage;

    public Projectile(int projectileSpeed, int angle, float posX, float posY, int damage, String texturePath) {
        super(new Texture(Gdx.files.internal(texturePath)));
        rotate(180-angle);
        this.setPosition(posX, posY);
        this.damage = damage;
        xSpeed = Math.sin(Math.toRadians(angle)) * projectileSpeed;
        ySpeed = Math.cos(Math.toRadians(angle)) * projectileSpeed;
        View.getInstance().addSprite(this);
        MovableSubject.getInstance().attach(this);
    }

    public float getX() {
        return super.getX();
    }
    public float getY(){
        return super.getY();
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public void update() {
        translateX((float) (xSpeed * Gdx.graphics.getDeltaTime()));
        translateY((float) (ySpeed * Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void collide(Rectangle rectangle) {
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().delete(this);
    }

    @Override
    public void playerLocation(int x, int y) {

    }

    @Override
    public Rectangle getBoundingRectangle() {
        return super.getBoundingRectangle();
    }
}