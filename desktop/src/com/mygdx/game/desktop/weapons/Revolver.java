package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

/**
 * Class for the specific firearm Revolver
 * Instances of this class are created in {@link Store}.
 * This class creates {@link Projectile}.
 * @author Anders
 */
public class Revolver extends Firearm {

    /**
     * Constructor for the Revolver class. It calls the constructor of the superclass with its specific values
     * @param scale is the scale of the program
     */
    public Revolver(float scale){
        super(1000, 1, 6, 200, 2000, 6, scale, "REVOLVER", 54);
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
