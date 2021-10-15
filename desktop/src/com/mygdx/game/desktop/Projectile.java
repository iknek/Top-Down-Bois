package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.Text;

/**
 * The Projectile class handles projectiles being fired from {@link com.mygdx.game.desktop.weapons.Firearm} objects in the game.
 * Projectiles will upon creation travel at the aim angle given by the {@link Player} object. Once the Projectile object
 * collides with another object, it will deal damage to eventual enemies involved with the collision, and then be deleted.
 */

public class Projectile extends Sprite implements Movable {
    private double xSpeed;
    private double ySpeed;
    private int damage;

    /**
     * Constructor for the {@link Projectile} class.
     * @param projectileSpeed speed of this object
     * @param angle angle this object is fired at
     * @param posX x-coordinate of this object
     * @param posY y-coordinate of this object
     * @param damage amount of damage this object deals to enemies
     * @param scale scale of this objects image
     */
    public Projectile(int projectileSpeed, int angle, float posX, float posY, int damage, Texture texture, float scale) {
        super(texture);
        rotate(180-angle);
        this.setScale(scale);

        this.setPosition(posX, posY);
        this.damage = damage;
        xSpeed = Math.sin(Math.toRadians(angle)) * projectileSpeed;
        ySpeed = Math.cos(Math.toRadians(angle)) * projectileSpeed;
        View.getInstance().addSprite(this);
        MovableSubject.getInstance().attach(this);
    }

    /**
     * Updates this objects position on the screen.
     */
    @Override
    public void update() {
        translateX((float) (xSpeed * Gdx.graphics.getDeltaTime()));
        translateY((float) (ySpeed * Gdx.graphics.getDeltaTime()));
    }

    /**
     * Removes instance of this object when it collides with something.
     * @param rectangle rectangle object this object collides with
     */
    @Override
    public void collide(Rectangle rectangle) {
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
    }

    /**
     * Gets bounding rectangle of projectile for collisions.
     * @return Rectangle boundingRectangle
     */
    @Override
    public Rectangle getBoundingRectangle() {
        return super.getBoundingRectangle();
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
}