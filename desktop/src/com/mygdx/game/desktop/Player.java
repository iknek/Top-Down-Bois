package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.weapons.AutoRifle;
import com.mygdx.game.desktop.weapons.Firearm;

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

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Adam";

        textureAtlas = atlas;
        //setRegion(atlas.findRegion(name + "_back"));

        this.speed = 55*scale;

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
        updateAction();
    }

    @Override
    public boolean moving(){
        return up || down || right || left;
    }

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
        MovableSubject.getInstance().detach(this);
    }

    public int getHealth(){
        return health;
    }

    public void addHealth(int x){
        health += x;
        if(health > maxHealth){ health = maxHealth; }
    }

    private void updateAction() {
        if (triggerPulled) {
            firearm.fire(aimAngle, getX(), getY());
        }
    }

    public void setLeft(boolean bool){
        left = bool;
    }

    public void setRight(boolean bool){
        right = bool;
    }

    public void setUp(boolean bool){
        up = bool;
    }

    public void setDown(boolean bool){
        down = bool;
    }

    public void setTriggerPulled(boolean bool){
        triggerPulled = bool;
    }

    public void setAimAngle(int angle){
        aimAngle = angle;
    }

    public void setFirearm(Firearm firearm){
        this.firearm = firearm;
    }

    public void reload(){
        firearm.reloadFirearm();
    }
}