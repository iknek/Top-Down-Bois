package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class AutoRifle extends Firearm {

    String texture = "bullet.png";

    public AutoRifle(){
        super(1800, 3, 25, 600, 2000, 50);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture);
        ammoInMagazine -= 1;
    }
}

