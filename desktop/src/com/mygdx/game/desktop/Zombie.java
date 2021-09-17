package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.badlogic.gdx.Gdx.files;

public class Zombie extends Sprite implements Movable{
    private TextureAtlas textureAtlas;
    private boolean leftMove;
    private boolean rightMove;
    private boolean UpMove;
    private boolean DownMove;

    private int speed = 80;


    public Zombie(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        this.setPosition(posX, posY);
        this.setScale(scale);
        this.setX(posX);
        this.setY(posY);
        setRegion(textureAtlas.findRegion("Eric_back"));
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);
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


    public void changePlayerSprite(int ispresssed){
        if(ispresssed == 21){
            this.setRegion(this.textureAtlas.findRegion("Eric_left"));
        }
        if(ispresssed == 22){
            this.setRegion(this.textureAtlas.findRegion("Eric_right"));
        }
        if(ispresssed == 19){
            this.setRegion(this.textureAtlas.findRegion("Eric_forward"));
        }
        if(ispresssed == 20){
            this.setRegion(this.textureAtlas.findRegion("Eric_back"));
        }
    }


    @Override
    public void update() {
        if (leftMove)
            translateX(-speed * Gdx.graphics.getDeltaTime());
        if (rightMove)
            translateX(speed * Gdx.graphics.getDeltaTime());
        if (UpMove)
            translateY(speed * Gdx.graphics.getDeltaTime());
        if (DownMove)
            translateY(-speed * Gdx.graphics.getDeltaTime());
    }

    @Override
    public void collide(Rectangle rectangle) {
        if(leftMove){
            translateX(speed*Gdx.graphics.getDeltaTime());
        }
        if(rightMove){
            translateX(-speed*Gdx.graphics.getDeltaTime());
        }
        if(UpMove){
            translateY(-speed*Gdx.graphics.getDeltaTime());
        }
        if(DownMove){
            translateY(speed*Gdx.graphics.getDeltaTime());
        }
    }

}
