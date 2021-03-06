package com.mygdx.game.desktop.sapiens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.Movable;
import com.mygdx.game.desktop.MovableSubject;

/**
 * Superclass for {@link Player} and {@link Zombie} to hold common methods and attributes
 * This class is not used anywhere.
 * This class does not use any classes, except for Movable and Sprite.
 * @author david
 * @author imad
 * @author Anders
 */
public abstract class Sapien extends Sprite implements Movable {
    /**
     * Initial {@link TextureAtlas} which the sprite uses.
     */
    protected TextureAtlas textureAtlas;

    /**
     * The angle at which the sapien is moving
     */
    protected int angle;
    /**
     * The speed at which the sapien moves
     */
    protected float speed;
    /**
     * The health which the sapien has
     */
    protected int health;
    /**
     * The (size)scale which the sprite is
     */
    protected float scale;

    /**
     * Superconstructor for {@link Sapien} objects.
     * @param atlas = {@link TextureAtlas} of this object.
     * @param posX = x-coordinate to spawn this object on.
     * @param posY = y-coordinate to spawn this object on.
     * @param scale = scaling factor of {@link TextureAtlas}.
     */

    public Sapien(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));

        this.setPosition(posX, posY);
        this.setScale(scale);
        this.scale = scale;

        MovableSubject.getInstance().attach(this);
    }

    /**
     * Updates angle of sapien when it is moving (based on predifined speed)
     */
    @Override
    public void update() {
        updateAngle();

        if (moving()) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    /**
     * Checks for collision between sapien and collision map object layer
     * @param rectangle collision box for incoming collision object
     */
    @Override
    public void collide(Rectangle rectangle) {
        if(!(rectangle.getY() > this.getY()+(this.getHeight()/2))){
            translateX(((float)-(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)-(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));

            boolean insideX = !((getX()+getWidth())<rectangle.getX()||(rectangle.getX()+rectangle.getWidth())<getX());
            boolean insideY = !(getY()+(getHeight()/2)<rectangle.getY()||rectangle.getY()+rectangle.getHeight()<getY());

            if(insideY && !insideX){
                translateY((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime());
            } else if(insideX && !insideY){
                translateX((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime());
            }
            if(!insideX && !insideY){
                if((getX()+getWidth()) < rectangle.getX()){ translateX(-1); }
                if((rectangle.getX()+rectangle.getWidth()) < getX()){ translateX(1); }
                if(getY()+(getHeight()/2) < rectangle.getY()){ translateY(-1); }
                if(rectangle.getY()+rectangle.getHeight() < getY()){ translateY(1); }
            }
        }
    }

    public abstract void getHit(int damage);
    public float getScale(){
        return scale;
    }

    public abstract boolean moving();
    protected abstract void updateAngle();
}
