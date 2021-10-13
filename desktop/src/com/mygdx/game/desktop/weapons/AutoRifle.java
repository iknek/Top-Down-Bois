package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class AutoRifle extends Firearm {
    private String texture = "bullet.png";

    /**
     * Constructor for the AutoRifle class. It calls the constructor of the superclass with its specific values
     * @param scale is the scale of the program
     */
    public AutoRifle(float scale){
        super(1800, 3, 50, 600, 2000, 50, scale, "AUTORIFLE", 200);
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

