package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.Timer;
import java.util.TimerTask;

public class Revolver extends Firearm {

    private String texture = "bullet.png";

    /**
     * Constructor for the Revolver class. It calls the constructor of the superclass with its specific values
     * @param scale is the scale of the program
     */
    public Revolver(float scale){
        super(1000, 1, 6, 200, 2000, 6, scale, "REVOLVER", 36);
    }

    /**
     * Creates a new bullet and decreases the number of ammo in the magazine by one
     * @param angle is the angle at which the bullet should fly
     * @param x is the start point for the bullet
     * @param y is the start point for the bullet
     */
    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture, scale);
        ammoInMagazine -= 1;
    }
}
