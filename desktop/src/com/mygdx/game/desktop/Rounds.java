package com.mygdx.game.desktop;


/**
 * The Rounds class represents every group of {@link Zombie} objects spawning in. Once all zombies are killed, a new round starts
 * with new zombies spawning in with increased health, for the sake of increased difficulty.
 */
public class Rounds {
    /**
     * {@link ZombieFactory} object, called for creating new {@link Zombie} objects each round.
     */
    private ZombieFactory zombiefactory;
    /**
     * Scaling factor for {@link Zombie} objects in ZombieFactory to render in proper scale to the map.
     */
    private float scale;
    /**
     * Counter for keeping track of which round the player has progressed to.
     */
    private int roundNumber;

    /**
     * Screen width.
     */
    private float w;
    /**
     * Screen height.
     */
    private float h;

    /**
     * Constructor for the {@link Rounds} class.
     * @param scale scale of zombie factory
     * @param w screen width
     * @param h screen height
     */
    public Rounds (float scale, float w, float h){
        this.roundNumber = 0;
        this.scale = scale;
        this.w = w;
        this.h = h;
        zombiefactory = new ZombieFactory(scale);
    }

    /**
     * Checks if any {@link Zombie} objects remain on the map. If there aren't while the player isn't dead, it starts a new round.
     * @param player current {@link Player} object
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
     * Starts new round by increasing <code>roundnumber</code>, adding 1 to player health, and calling {@link ZombieFactory} to create new {@link Zombie} objects.
     * @param player current {@link Player} object
     */
    private void startNewRound(Player player){
        roundNumber++;
        System.out.println(roundNumber);
        player.addHealth(1);
        zombiefactory.createZombie(roundNumber*5, scale);
    }
}