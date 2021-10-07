package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public abstract class Sapien extends Sprite implements Movable{
    protected TextureAtlas textureAtlas;

    protected int angle;
    protected float speed;
    protected int health;
    protected String name;
    protected float scale;

    /**
     * @param atlas = textureatlas of sapien.
     * @param posX = X coordinate to spawn it in on.
     * @param posY = Y coordinate to spawn it in on.
     * @param scale = Scale of atlas.
     * Superconstructor for sapiens. */

    public Sapien(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));

        this.setPosition(posX, posY);
        this.setScale(scale);
        this.scale = scale;

        MovableSubject.getInstance().attach(this);
    }

    public float getX() {
        return super.getX();
    }

    public float getY(){
        return super.getY();
    }

    public abstract boolean moving();

    protected abstract void updateAngle();

    public abstract void getHit(int damage);

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

    public void setPosition(float posX, float posY){
        this.setX(posX);
        this.setY(posY);
    }

    /**
     * Checks for collision between sapien and collision map object layer
     * @param rectangle
     */
    @Override
    public void collide(Rectangle rectangle) {
        if(!(rectangle.getY() > this.getY()+(this.getHeight()/2))){
            /*Rectangle bounding = getBoundingRectangle();

            float x = bounding.getX();
            float y = bounding.getX();
            float width = bounding.getX();
            float height = bounding.getX();
            */

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
}
