package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;

public class Shotgun extends Firearm {

    String texture = "bullet.png";

    public Shotgun(){
        super(1000, 1, 12, 90, 2000);
    }

    @Override
    public void createBullet(int angle, float x, float y) {
        new Projectile(projectileSpeed, angle, x,y, damage, texture);
        new Projectile(projectileSpeed, angle + 4, x,y, damage, texture);
        new Projectile(projectileSpeed, angle, x,y + 6, damage, texture);
        new Projectile(projectileSpeed, angle - 4, x,y, damage, texture);
        new Projectile(projectileSpeed, angle - 6, x,y, damage, texture);
        ammoInMagazine -= 5;
    }
}

