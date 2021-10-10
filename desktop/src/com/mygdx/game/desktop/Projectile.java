package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Projectile extends Sprite implements Movable {
    private double xSpeed;
    private double ySpeed;
    private int damage;

    /**
     * Constrcutor for projectiles (bullets)
     * @param projectileSpeed = speed of projectile
     * @param angle = angle it's fired at.
     * @param posX = its X position
     * @param posY = its Y position
     * @param damage = amount of damage it deals
     * @param texturePath = path to textureatlas (png)
     * @param scale = scale of image/projectile
     */
    public Projectile(int projectileSpeed, int angle, float posX, float posY, int damage, String texturePath, float scale) {
        super(new Texture(Gdx.files.internal(texturePath)));
        rotate(180-angle);
        this.setScale(scale);

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

    /**
     * Moves projectile
     */
    @Override
    public void update() {
        translateX((float) (xSpeed * Gdx.graphics.getDeltaTime()));
        translateY((float) (ySpeed * Gdx.graphics.getDeltaTime()));
    }

    /**
     * removes instance of projectile when it collides with something.
     * @param rectangle = rectangle projectile collides with
     */
    @Override
    public void collide(Rectangle rectangle) {
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
    }


    @Override
    public Rectangle getBoundingRectangle() {
        return super.getBoundingRectangle();
    }
}