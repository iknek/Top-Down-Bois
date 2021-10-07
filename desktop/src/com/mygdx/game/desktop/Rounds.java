package com.mygdx.game.desktop;

public class Rounds {
    private ZombieFactory zombiefactory;
    private float scale;
    private int roundNumber;

    private float w;
    private float h;

    /**
     * Constructor for rounds
     * @param scale = scale of zombie factory
     * @param w = width of screen
     * @param h = height of screen
     */
    public Rounds (float scale, float w, float h){
        this.roundNumber = 0;
        this.scale = scale;
        this.w = w;
        this.h = h;
        zombiefactory = new ZombieFactory(scale);
    }

    /**
     * Checks if any zombies are left. If there aren't, and a player isn't dead, it makes a new round
     * @param player = player
     */
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

    /**
     * Starts new round by adding to roundnumber, adding 1 to player health, and calling zombie factory
     * @param player = player
     */
    private void startNewRound(Player player){
        roundNumber++;
        System.out.println(roundNumber);
        player.addHealth(1);
        zombiefactory.createZombie(roundNumber*5, scale);
    }
}