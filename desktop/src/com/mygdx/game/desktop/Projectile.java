package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Sprite implements Movable {
    private TextureAtlas textureAtlas;
    private int xSpeed;
    private int ySpeed;

    public Projectile(int projectileSpeed, int angle, float posX, float posY) {
        super(new Texture(Gdx.files.internal("bullet.png")));
        rotate(angle);
        this.setX(posX);
        this.setY(posY);
        xSpeed = (int) (Math.sin(Math.toRadians(angle)) * projectileSpeed);
        ySpeed = (int) (Math.cos(Math.toRadians(angle)) * projectileSpeed);
        Renderer renderer = Renderer.getInstance();
        renderer.addSprite(this);
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);
    }

    public float getX() {
        return super.getX();
    }
    public float getY(){
        return super.getY();
    }

    @Override
    public void update() {
        setX(getX() + xSpeed * Gdx.graphics.getDeltaTime());
        setY(getY() + ySpeed * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void collide(Rectangle rectangle) {
        Renderer.getInstance().removeSprite(this);
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
