package com.mygdx.game.desktop;

import java.util.Timer;
import java.util.TimerTask;

public class Revolver implements Firearm {

    int projectileSpeed = 1000;
    int damage = 1;
    String texturePath = "bullet.png";
    int AmmoInMagazine = 6;
    //rounds per minute
    int rateOfFire = 200;
    private Timer timer;
    private boolean readyToFire = true;

    @Override
    public void fire(int angle, float x, float y) {
        if (AmmoInMagazine > 0 && readyToFire) {
            timer = new Timer();
            new Projectile(projectileSpeed, angle, x,y, damage, texturePath);
            AmmoInMagazine = AmmoInMagazine - 1;
            timer.schedule(new RemindTask(), (60/rateOfFire)*1000);
            readyToFire = false;
        }
    }

    class RemindTask extends TimerTask {
        public void run(){
            readyToFire = true;
            timer.cancel(); //Terminate the timer thread
        }
    }


    @Override
    public void reloadFirearm() {
        AmmoInMagazine = 6;
    }
}
