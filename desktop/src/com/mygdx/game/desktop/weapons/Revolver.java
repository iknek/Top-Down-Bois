package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class Revolver extends Firearm {

    String texture = "bullet.png";

    public Revolver(){
        super(1000, 1, 6, 200, 2000, 6);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture);
        ammoInMagazine -= 1;
    }
}
