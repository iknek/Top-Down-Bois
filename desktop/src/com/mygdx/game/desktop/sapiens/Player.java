package com.mygdx.game.desktop.sapiens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.Coin;
import com.mygdx.game.desktop.MovableSubject;
import com.mygdx.game.desktop.Rounds;
import com.mygdx.game.desktop.animations.PlayerAnimations;
import com.mygdx.game.desktop.views.View;
import com.mygdx.game.desktop.weapons.AutoRifle;
import com.mygdx.game.desktop.weapons.Firearm;
import com.mygdx.game.desktop.weapons.Revolver;
import com.mygdx.game.desktop.weapons.Shotgun;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author David
 * @author Imad
 */
public class Player extends Sapien {

    private boolean left, right, up, down;
    private boolean triggerPulled = false;
    private boolean invincible;
    private Timer timerInvinc, timerSprint;
    private int aimAngle;
    private int maxHealth;
    private int money;
    private PlayerAnimations animations;
    private boolean playerHit;
    private int currentWeapon;

    private Firearm[] weapons = new Firearm[3];

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

        weapons[0] = new Revolver(scale);
        weapons[1] = new Shotgun(scale);
        weapons[2] = new AutoRifle(scale);

        currentWeapon = 0;
        //this.firearm = weapons[2];

        health = 100;
        maxHealth = 100;

        new PlayerController(this);

        this.animations = new PlayerAnimations(View.getInstance().getBatch());
        View.getInstance().addSprite(this);
    }

    public Firearm getWeapon(){
        return weapons[currentWeapon];
    }

    /**
     * Updates the angle of the player depending on which buttons are pressed.
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
        animations.render(getX(), getY(), moving(), scale, getPlayerHit(), angle);
    }

    /**
     * Tells the program whether this object is moving or not.
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
        if(!invincible && damage!=0) {
            setPlayerHit(true);
            timerInvinc = new Timer();
            health = health - damage;
            invincible = true;
            timerInvinc.schedule(new RemindTaskInvincibility(), 3*1000);
            System.out.println("oof");
        }

        if(health <= 0){
            die();
        }
    }

    /**
     * When the timer of the invincibility frame ends, invincible is set to false.
     */
    private class RemindTaskInvincibility extends TimerTask {
        public void run() {
            invincible = false;
            setPlayerHit(false);
            timerInvinc.cancel();
            timerInvinc.purge();
        }
    }

    /**
     * Removes the player from View and {@link MovableSubject} so that it does not render and cannot move. the player is dead.
     */
    private void die(){
        View.getInstance().removeSprite(this);
        MovableSubject.getInstance().detach(this);
    }

    /**
     * Gives a little bit of health back, used in {@link Rounds}
     * @param x is the amount of health to be added
     */
    public void addHealth(int x){
        health += x;
        if(health > maxHealth){ health = maxHealth; }
    }

    /**
     * Sees if the trigger is pulled and if so, fires the {@link Firearm} object.
     */
    private void updateAction() {
        if (triggerPulled) {
            weapons[currentWeapon].fire(aimAngle, getX() + 16/2, getY() + 16/2);
        }
    }

    /**
     * Adds to the <code>money</code> variable when a {@link Coin} object is picked up
     */
    public void coinGained() {
        money += 1;
        System.out.println("Coin gained");
    }

    /**
     * Sets speed for sprinting as opposed to walking. Uses timer to control how long player can run
     * @param bool sprinting
     */
    public void setSprint(boolean bool){
        if(bool){
            this.speed = (float)82.5*scale;
            timerSprint = new Timer();
            timerSprint.schedule(new RemindTaskSprint(), 3*1000);
        }else{
            this.speed = 55*scale;
        }
    }

    /**
     * Timer for setSprint
     */
    private class RemindTaskSprint extends TimerTask {
        public void run() {
            speed = 55*scale;
            timerSprint.cancel();
            timerSprint.purge();
        }
    }

    /**
     * Reloads the weapon.
     * This method is called in {@link PlayerController} when the R key is pressed.
     */
    public void reload(){
        weapons[currentWeapon].reloadFirearm();
    }

    public int getHealth(){
        return health;
    }

    public int getMoney(){
        return money;
    }

    public void setLeft(boolean bool){
        left = bool;
    }

    public void setRight(boolean bool){
        right = bool;
    }

    public void setUp(boolean bool){
        up = bool;
    }

    public void setDown(boolean bool){
        down = bool;
    }

    public void setTriggerPulled(boolean bool){
        triggerPulled = bool;
    }

    public void setAimAngle(int angle){
        aimAngle = angle;
    }

    public void setFirearm(int weaponNumber){
        currentWeapon = weaponNumber;
    }

    public void setPlayerHit(boolean bool){
        playerHit = bool;
    }

    public boolean getPlayerHit(){
        return playerHit;
    }
}