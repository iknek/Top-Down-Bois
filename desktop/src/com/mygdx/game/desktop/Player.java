package com.mygdx.game.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Timer;
import java.util.TimerTask;


public class Player extends Sapien{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Firearm firearm;
    private boolean triggerPulled;
    private boolean invincible;
    private Timer timer;

    private int maxHealth;

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Adam";

        textureAtlas = atlas;
        setRegion(atlas.findRegion(name + "_back"));

        this.speed = 110;

        this.firearm = new AutoRifle();

        health = 100;
        maxHealth = 100;
    }

    protected void updateAngle() {
        if (up && !down && !right && !left) {
            angle = 0;
        }
        if (up && !down && right && !left) {
            angle = 45;
        }
        if (!up && !down && right && !left) {
            angle = 90;
        }
        if (!up && down && right && !left) {
            angle = 135;
        }
        if (!up && down && !right && !left) {
            angle = 180;
        }
        if (!up && down && !right && left) {
            angle = 225;
        }
        if (!up && !down && !right && left) {
            angle = 270;
        }
        if (up && !down && !right && left) {
            angle = 315;
        }
    }

    public void Shoot() {
        firearm.fire(angle, getX(), getY());
    }

    public InputProcessor getInputProcessor(){
        return inputProcessor;
    }

    @Override
    public boolean moving(){
        return up || down || right || left;
    }

    @Override
    public void playerLocation(int x, int y) {    }

    public void getHit(int damage){
        if(!invincible) {
            timer = new Timer();
            health = health - damage;
            invincible = true;
            timer.schedule(new RemindTask(), 5*1000);
            System.out.println("oof");
        }

        if(health == 0){
            die();
        }
    }

    class RemindTask extends TimerTask {
        public void run() {
            invincible = false;
            timer.cancel(); //Terminate the timer thread
        }
    }

    private void die(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().delete(this);
    }

    public int getHealth(){
        return health;
    }

    public void addHealth(int x){
        health += x;
        if(health > maxHealth){ health = maxHealth; }
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
                    triggerPulled = true;
                    Shoot();
                    break;
                case Input.Keys.R:
                    firearm.reloadFirearm();
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
                case Input.Keys.G:
                    Shoot();
                    triggerPulled = false;
                    break;
            }
            return true;
        }
    };
}