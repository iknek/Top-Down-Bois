package com.mygdx.game.desktop;

public class Rounds {
    private ZombieFactory zombiefactory;
    private int scale;
    private int roundNumber;

    public float w;
    public float h;


    public Rounds (int scale, float w, float h){
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
            startNewRound();
        }
    }
    
    private void startNewRound(){
        roundNumber++;
        zombiefactory.createZombie(roundNumber*5, w, h, scale);
    }
}
