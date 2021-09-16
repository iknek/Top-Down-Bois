package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Projectile extends Sprite implements Movable {
    TextureAtlas textureAtlas;
    static float xSpeed;
    static float ySpeed;

    public Projectile(double projectileSpeed, float angleOfDirection, float posX, float posY, TextureAtlas atlas) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        setRegion(textureAtlas.findRegion("Adam_left"));
        this.setX(posX);
        this.setY(posY);
        xSpeed = (float) Math.cos(projectileSpeed);
        ySpeed = (float) Math.sin(projectileSpeed);
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
        setX(getX() + 100 * Gdx.graphics.getDeltaTime());
        setY(getY() + 100 * Gdx.graphics.getDeltaTime());
    }
}
