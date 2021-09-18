package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public class Player extends Sprite implements Movable{
    private int angle;
    private TextureAtlas textureAtlas;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Weapon weapon;
    private int health;
    private boolean invincible;
    private Timer timer;

    private int speed = 130;


    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        this.setPosition(posX, posY);
        this.setScale(scale);
        this.setX(posX);
        this.setY(posY);
        this.weapon = new Weapon();
        setRegion(textureAtlas.findRegion("Adam_back"));
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);
        health = 3;
    }

    public Weapon getWeapon() {return this.weapon;}

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

    public void changePlayerSprite() {
        if (315 <= angle && angle <= 360 || 0 <= angle && angle < 45) {
        this.setRegion(this.textureAtlas.findRegion("Adam_forward"));
        }
        if (45 <= angle && angle < 135) {
            this.setRegion(this.textureAtlas.findRegion("Adam_right"));
        }
        if (135 <= angle && angle < 225) {
            this.setRegion(this.textureAtlas.findRegion("Adam_back"));
        }
        if (225 <= angle && angle < 315) {
            this.setRegion(this.textureAtlas.findRegion("Adam_left"));
        }
    }

    private void updateAngle() {
        if (up&&!right&&!left&&!down) {
            angle = 0;
        }
        if (up&&right&&!left&&!down) {
            angle = 45;
        }
        if (!up&&right&&!left&&!down) {
            angle = 90;
        }
        if (!up&&right&&!left&&down) {
            angle = 135;
        }
        if (!up&&!right&&!left&&down) {
            angle = 180;
        }
        if (!up&&!right&&left&&down) {
            angle = 225;
        }
        if (!up&&!right&&left&&!down) {
            angle = 270;
        }
        if (up&&!right&&left&&!down) {
            angle = 315;
        }
    }

    private void Shoot() {
        weapon.fireWeapon(angle, getX(), getY());
    }

    public InputProcessor getInputProcessor(){
        return inputProcessor;
    }

    @Override
    public void update() {
        updateAngle();
        changePlayerSprite();
        if (left||right||up||down) {
            translateX(((float)(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
            translateY(((float)(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        }
    }

    @Override
    public void collide(Rectangle rectangle) {
        translateX(((float)-(Math.sin(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
        translateY(((float)-(Math.cos(Math.toRadians(angle)) * speed) * Gdx.graphics.getDeltaTime()));
    }

    @Override
    public void playerLocation(int x, int y) {

    }

    public void getHit(){
        if(!invincible) {
            timer = new Timer();
            health -= 1;
            invincible = true;
            timer.schedule(new RemindTask(), 5*1000);
            System.out.println("oof");
        }
    }

    class RemindTask extends TimerTask {
        public void run() {
            invincible = false;
            timer.cancel(); //Terminate the timer thread
        }
    }

    public int getHealth(){
        return health;
    }

    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    left = true;
                    break;
                case Input.Keys.RIGHT:
                    right = true;
                    break;
                case Input.Keys.UP:
                    up = true;
                    break;
                case Input.Keys.DOWN:
                    down = true;
                    break;
                case Input.Keys.G:
                    Shoot();
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.LEFT:
                    left = false;
                    break;
                case Input.Keys.RIGHT:
                    right = false;
                    break;
                case Input.Keys.UP:
                    up = false;
                    break;
                case Input.Keys.DOWN:
                    down = false;
                    break;
            }
            return true;
        }
    };
}
