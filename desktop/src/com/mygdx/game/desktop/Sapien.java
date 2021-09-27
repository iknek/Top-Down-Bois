package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public abstract class Sapien extends Sprite implements Movable{
    protected TextureAtlas textureAtlas;

    protected int angle;

    protected float speed;
    protected int health;
    protected String name;

    public Sapien(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));

        this.setPosition(posX, posY);
        this.setScale(scale);

        MovableSubject.getInstance().attach(this);
        View.getInstance().addSprite(this);
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

    @Override
    public void update() {
        updateAngle();
        changeSprite();

        if (moving()) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    public void setPosition(float posX, float posY){
        this.setX(posX);
        this.setY(posY);
    }


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


    public void changeSprite() {
        if (315 <= angle && angle <= 360 || 0 <= angle && angle < 45) {
            this.setRegion(this.textureAtlas.findRegion(name + "_forward"));
        }
        if (45 <= angle && angle < 135) {
            this.setRegion(this.textureAtlas.findRegion(name + "_right"));
        }
        if (135 <= angle && angle < 225) {
            this.setRegion(this.textureAtlas.findRegion(name + "_back"));
        }
        if (225 <= angle && angle < 315) {
            this.setRegion(this.textureAtlas.findRegion(name + "_left"));
        }
    }
}
