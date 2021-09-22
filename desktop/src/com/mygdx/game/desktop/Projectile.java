package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Sprite implements Movable {
    //Projectile has fucky angles, when fired at angle then it faces wrong way
    private double xSpeed;
    private double ySpeed;

    public Projectile(int projectileSpeed, int angle, float posX, float posY) {
        super(new Texture(Gdx.files.internal("bullet.png")));
        rotate(180-angle);
        this.setPosition(posX, posY);
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
