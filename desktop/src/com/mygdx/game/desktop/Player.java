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


public class Player extends Sapien{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Weapon weapon;

    private boolean invincible;
    private Timer timer;

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        textureAtlas = atlas;
        setRegion(atlas.findRegion("Adam_back"));

        this.speed = 110;

        this.weapon = new Weapon();

        health = 3;
    }

    //PLS FIX måste finnas bättre sätt, som inte får imad's huvud att göra ont
    public void changeSprite() {
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

    private void Shoot() {
        weapon.fireWeapon(angle, getX(), getY());
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
                case Input.Keys.RIGHT:
                    right = false;
                    break;
                case Input.Keys.UP:
                    up = false;
                case Input.Keys.DOWN:
                    down = false;
                    break;
            }
            return true;
        }
    };
}
