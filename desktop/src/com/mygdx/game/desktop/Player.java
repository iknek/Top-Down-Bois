package com.mygdx.game.desktop;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.desktop.weapons.AutoRifle;
import com.mygdx.game.desktop.weapons.Firearm;

import java.util.Timer;
import java.util.TimerTask;


public class Player extends Sapien{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private Firearm firearm;
    private boolean triggerPulled = false;
    private boolean invincible;
    private Timer timer;
    private int aimAngle;
    private int maxHealth;
    private int money;
    private Animations animations;

    private boolean playerHit;


    private PlayerController playerController;

    /**
     * Constructor for the Player class
     * Sets the texture atlas, initial position and scale of the sprite
     * Sets initial speed and health as well as weapon
     * A player controller is also created so that input can be processed
     * @param atlas is the texture atlas for the sprite. It tells the program how the player should look
     * @param posX is the initial X-coordinate for the player
     * @param posY is the initial Y-coordinate for the player
     * @param scale is the scale for the program, so that the player is the correct size compared to the program.
     */
    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas, posX, posY, scale);
        this.name = "Adam";

        textureAtlas = atlas;

        this.speed = 55*scale;

        this.firearm = new AutoRifle(scale);

        health = 100;
        maxHealth = 100;

        playerController = new PlayerController(this);

        this.animations = new Animations(View.getInstance().getBatch(), this);
        View.getInstance().addSprite(this);

    }

    /**
     * This method, updates the angle of the player depending on which buttons are pressed.
     */
    protected void updateAngle() {
        if (up && !down && !right && !left) {
            angle = 0;
        }
        if (up && !down && right && !left) {
            angle = 45;
        }
        if (!up && !down && right && !left) {
            angle = 90;
        }
        if (!up && down && right && !left) {
            angle = 135;
        }
        if (!up && down && !right && !left) {
            angle = 180;
        }
        if (!up && down && !right && left) {
            angle = 225;
        }
        if (!up && !down && !right && left) {
            angle = 270;
        }
        if (up && !down && !right && left) {
            angle = 315;
        }
        updateAction();
    }

    @Override
    public void draw(Batch batch) {
        animations.render();
    }

    /**
     * This method tells the program whether the player is moving or not
     * @return whether the player is moving
     */
    @Override
    public boolean moving(){
        return up || down || right || left;
    }

    /**
     * This method is called when a Zombie hits a player
     * An invincibility period is started and takes health off of the player
     * If health drops below 0, the player dies.
     * @param damage is the amount of the damage dealt to the player
     */
    public void getHit(int damage){
        if(!invincible) {
            timer = new Timer();
            health = health - damage;
            invincible = true;
            timer.schedule(new RemindTask(), 5*1000);
            System.out.println("oof");
        }

        if(health <= 0){
            die();
        }
    }

    /**
     * When the timer of the invincibility frame ends, invincible is set to false.
     */
    class RemindTask extends TimerTask {
        public void run() {
            invincible = false;
            timer.cancel(); //Terminate the timer thread
        }
    }

    /**
     * Removes the player from View and Movablesubject so that it does not render and cannot move. the player is dead.
     */
    private void die(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
    }

    /**
     * getter for health
     * @return how much health the player currently has
     */
    public int getHealth(){
        return health;
    }

    /**
     * Gives a little bit of health back, used in Rounds
     * @param x is the amount of health to be added
     */
    public void addHealth(int x){
        health += x;
        if(health > maxHealth){ health = maxHealth; }
    }

    /**
     * Sees if the trigger is pulled and if so, fires the weapon
     */
    private void updateAction() {
        if (triggerPulled) {
            firearm.fire(aimAngle, getX() + 16/2, getY() + 16/2);
        }
    }

    /**
     * When a coin is picked up adds to the money variable
     */
    public void coinGained() {
        money = money + 1;
        System.out.println("Coin gained");
    }

    /**
     * setter for the boolean left, called in Playercontroller when the left button is either pressed or released
     * @param bool whether the button is pressed or not
     */
    public void setLeft(boolean bool){
        left = bool;
    }

    /**
     * setter for the boolean right, called in Playercontroller when the right button is either pressed or released
     * @param bool whether the button is pressed or not
     */
    public void setRight(boolean bool){
        right = bool;
    }

    /**
     * setter for the boolean up, called in Playercontroller when the up button is either pressed or released
     * @param bool whether the button is pressed or not
     */
    public void setUp(boolean bool){
        up = bool;
    }

    /**
     * setter for the boolean down, called in Playercontroller when the down button is either pressed or released
     * @param bool whether the button is pressed or not
     */
    public void setDown(boolean bool){
        down = bool;
    }

    /**
     * setter for the triggerpulled, called in Playercontroller when the shoot button is either pressed or released
     * @param bool whether the button is pressed or not
     */
    public void setTriggerPulled(boolean bool){
        triggerPulled = bool;
    }

    /**
     * setter for the aim angle, called in Playercontroller when the mouse is moved
     * @param angle is the angle at which the player will shoot
     */
    public void setAimAngle(int angle){
        aimAngle = angle;
    }

    /**
     * Gives the player a new weeapon
     * @param firearm is the weapon the player recieves
     */
    public void setFirearm(Firearm firearm){
        this.firearm = firearm;
    }

    /**
     * Reloads the weapon.
     * The method is called in Playercontroller when r is pressed.
     */
    public void reload(){
        try{
            firearm.reloadFirearm();
        }
        catch( Exception NullPointerException){
            System.out.println("Reloading full mags doesn't work!");
        }
    }

    public void setPlayerHit(boolean bool){
        playerHit = bool;
    }

    public boolean getPlayerHit(){
        return playerHit;
    }
}