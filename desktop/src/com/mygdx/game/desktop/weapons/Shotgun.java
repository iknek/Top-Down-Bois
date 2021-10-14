package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class Shotgun extends Firearm {

    /**
     * Constructor for the Shotgun class. It calls the constructor of the superclass with its specific values
     * @param scale is the scale of the program
     */
    public Shotgun(float scale){
        super(1000, 1, 5, 90, 2000, 10, scale, "SHOTGUN", 50);
    }

    /**
     * Creates a new bullet and decreases the number of ammo in the magazine by 5 as 5 bullets are shot at once
     * @param angle is the angle at which the bullet should fly
     * @param x is the start point for the bullet
     * @param y is the start point for the bullet
     */
    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle + 4, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle, x,y + 6, damage, texture, scale);
        new Projectile(projectileSpeed, angle - 4, x,y, damage, texture, scale);
        new Projectile(projectileSpeed, angle - 6, x,y, damage, texture, scale);
        ammoInMagazine -= 5;
    }
    
}

