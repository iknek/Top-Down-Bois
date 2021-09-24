package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.weapons.AutoRifle;
import com.mygdx.game.desktop.weapons.Firearm;
import com.mygdx.game.desktop.weapons.Revolver;
import com.mygdx.game.desktop.weapons.Shotgun;

import java.util.Timer;
import java.util.TimerTask;


public class Player extends Sapien{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Firearm firearm;
    private boolean triggerPulled = false;
    private boolean invincible;
    private Timer timer;
    private int aimAngle;
    private int maxHealth;

    public Player(TextureAtlas atlas, float posX, float posY, int scale) {
        super(atlas, posX, posY, scale);
        this.name = "Adam";

        textureAtlas = atlas;
        setRegion(atlas.findRegion(name + "_back"));

        this.speed = 55*scale;

        this.firearm = new AutoRifle();

        health = 2;
        maxHealth = 2;
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

    @Override
    public void updateAction() {
        if (triggerPulled) {
            firearm.fire(aimAngle, getX(), getY());
        }
    }

    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                    left = true;
                    break;
                case Input.Keys.D:
                    right = true;
                    break;
                case Input.Keys.W:
                    up = true;
                    break;
                case Input.Keys.S:
                    down = true;
                    break;
                case Input.Keys.G:
                    triggerPulled = true;
                    break;
                case Input.Keys.R:
                    firearm.reloadFirearm();
                    break;
                case Input.Keys.NUM_1 :
                    firearm = new Revolver();
                    break;
                case Input.Keys.NUM_2 :
                    firearm = new Shotgun();
                    break;
                case Input.Keys.NUM_3 :
                    firearm = new AutoRifle();
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode) {
                case Input.Keys.A:
                    left = false;
                    break;
                case Input.Keys.D:
                    right = false;
                    break;
                case Input.Keys.W:
                    up = false;
                    break;
                case Input.Keys.S:
                    down = false;
                    break;
                case Input.Keys.G:
                    triggerPulled = false;
                    break;
            }
            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY,int pointer) {
            //800 should be replaced by actual screen height

            aimAngle = (int) -Math.toDegrees(Math.atan2(Gdx.graphics.getHeight()-getY()-screenY, screenX-getX()));
            aimAngle += 90;
            if(aimAngle < 0){
                aimAngle += 360;
            }
            System.out.println(Gdx.graphics.getHeight());
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            triggerPulled = true;
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            triggerPulled = false;
            return true;
        }
    };
}