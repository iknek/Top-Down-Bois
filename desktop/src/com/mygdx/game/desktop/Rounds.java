package com.mygdx.game.desktop;

public class Rounds {
    private ZombieFactory zombiefactory;
    private float scale;
    private int roundNumber;

    private float w;
    private float h;

    public Rounds (float scale, float w, float h){
        this.roundNumber = 0;
        this.scale = scale;
        this.w = w;
        this.h = h;

        zombiefactory = new ZombieFactory(scale);
    }
    
    public void checkNewRound(Player player){
        int zombiesLeft = 0;
        for (Movable o : MovableSubject.getInstance().getObservers()) {
            if(o instanceof Zombie){
                zombiesLeft++;
            }
        }
        if(zombiesLeft == 0 && player.getHealth() != 0){
            startNewRound(player);
        }
    }
    
    private void startNewRound(Player player){
        roundNumber++;
        player.addHealth(1);
        zombiefactory.createZombie(roundNumber*5, scale);
    }
}