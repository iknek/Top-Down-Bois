package com.mygdx.game.desktop.weapons;

import com.mygdx.game.desktop.Projectile;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.Timer;
import java.util.TimerTask;

public class AutoRifle implements Firearm {

    int projectileSpeed = 1800;
    int damage = 3;
    String texture = "bullet.png";
    int AmmoInMagazine = 25;
    //rounds per minute
    float rateOfFire = 600;
    private Timer timer;
    private boolean readyToFire = true;
    private int reloadSpeed = 2000;

    @Override
    public void fire(int angle, float x, float y) {
        if (AmmoInMagazine > 0 && readyToFire) {
            timer = new Timer();
            new Projectile(projectileSpeed, angle, x,y, damage, texture);
            AmmoInMagazine = AmmoInMagazine - 1;
            timer.schedule(new RemindTask(), (int)(60/rateOfFire*1000));
            readyToFire = false;
        }
    }

    class RemindTask extends TimerTask {
        public void run(){
            timer.cancel(); //Terminate the timer thread
            readyToFire = true;
        }
    }

    @Override
    public void reloadFirearm() {
        timer.cancel();
        readyToFire = false;
        timer = new Timer();
        timer.schedule(new RemindTask(), reloadSpeed);
        AmmoInMagazine = 25;
    }
}

