package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Firearm {
    protected int projectileSpeed;
    protected int damage;
    private String texture = "bullet.png";
    protected int ammoInMagazine;
    //rounds per minute
    protected float rateOfFire;
    private Timer timer;
    protected boolean readyToFire = true;
    protected int reloadSpeed;

    public Firearm(int projectileSpeed, int damage, int ammoInMagazine, float rateOfFire, int reloadSpeed){
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.ammoInMagazine = ammoInMagazine;
        this.rateOfFire = rateOfFire;
        this.reloadSpeed = reloadSpeed;
    }

    public abstract void createBullet(int angle, float x, float y);

    public void fire(int angle, float x, float y) {
        if (readyToFire && ammoInMagazine > 0) {
            readyToFire = false;
            timer = new Timer();
            createBullet(angle, x, y);
            timer.schedule(new RemindTask(), (int)(60/rateOfFire*1000));
        }
    }

    class RemindTask extends TimerTask {
        public void run(){
            readyToFire = true;
            timer.cancel(); //Terminate the timer thread
        }
    }


    public void reloadFirearm() {
        timer.cancel();
        readyToFire = false;
        timer = new Timer();
        timer.schedule(new RemindTask(), reloadSpeed);
        ammoInMagazine = 6;
    }
}
