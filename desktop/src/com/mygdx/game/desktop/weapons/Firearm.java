package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

import java.util.Timer;
import java.util.TimerTask;

public abstract class   Firearm {
    protected int projectileSpeed;
    protected int damage;
    private String texture = "bullet.png";
    protected int ammoInMagazine;
    private int maxAmmo;
    //rounds per minute
    protected float rateOfFire;
    private Timer timer;
    protected boolean readyToFire = true;
    protected int reloadSpeed;
    protected float scale;

    /**
     * Constructor for the superclass weapons.
     * @param projectileSpeed is the speed at which the bullet is fired
     * @param damage is the damage the bullets do to zombies
     * @param ammoInMagazine is the ammo which is currently in the magazine
     * @param rateOfFire is how quickly the bullets are produced
     * @param reloadSpeed is how long it takes to reload the gun
     * @param maxAmmo is the maximum ammo the gun can have in its magazine at one time
     * @param scale is the scale for the whole program. So that the bullets are the right size comparatively.
     */
    public Firearm(int projectileSpeed, int damage, int ammoInMagazine, float rateOfFire, int reloadSpeed, int maxAmmo, float scale){
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.ammoInMagazine = ammoInMagazine;
        this.rateOfFire = rateOfFire;
        this.reloadSpeed = reloadSpeed;
        this.maxAmmo = maxAmmo;
        this.scale = scale/2;
    }

    protected abstract void createBullet(int angle, float x, float y);

    /**
     * This method fires the weapon
     * @param angle is the angle at which the bullets should fire
     * @param x is the current X-coordinate of the player so the bullets come from the player
     * @param y is the current Y-coordinate of the player
     */
    public void fire(int angle, float x, float y) {
        if (readyToFire && ammoInMagazine > 0) {
            readyToFire = false;
            timer = new Timer();
            createBullet(angle, x, y);
            timer.schedule(new RemindTask(), (int)(60/rateOfFire*1000));
        }
    }

    /**
     * When the timer runs out the gun can fire again, this is how we control rate of fire
     * It is also used for a reload delay
     */
    class RemindTask extends TimerTask {
        public void run(){
            readyToFire = true;
            timer.cancel(); //Terminate the timer thread
        }
    }

    /**
     * This method is called when reload is called in player
     * It adds a delay and fills the ammo in the magazine of the gun
     */
    public void reloadFirearm() {
        timer.cancel();
        readyToFire = false;
        timer = new Timer();
        timer.schedule(new RemindTask(), reloadSpeed);
        ammoInMagazine = maxAmmo;
    }
}
