package com.mygdx.game.desktop;

import java.util.ArrayList;

public class Rounds {
    private ZombieFactory zombiefactory = new ZombieFactory();
    private int scale;
    public int roundNumber;

    public Rounds (int scale, float w, float h, Player player){
        this.scale = scale;
        startRounds(player, w,h,scale);
    }

    public void startRounds(Player player, float w, float h, int scale){
        ArrayList<Zombie> zombieList = zombiefactory.createZombie(0, w, h, this.scale);
        int round = 1;
        if(player.getHealth() != 0 && zombieList.isEmpty()){
            System.out.println("New Round!");
            zombiefactory.createZombie(5*round, w, h, this.scale);
            round++;
        }
        else{
            System.out.println("Sorry, you died 3 times!");
        }
    }
}
