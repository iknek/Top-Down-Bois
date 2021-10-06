package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class AutoRifle extends Firearm {
    String texture = "bullet.png";

    public AutoRifle(float scale){
        super(1800, 3, 25, 600, 2000, 50, scale);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture, scale);
        ammoInMagazine -= 1;
    }
}

