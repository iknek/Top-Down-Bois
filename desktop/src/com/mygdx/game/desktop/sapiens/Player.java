package com.mygdx.game.desktop.sapiens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.desktop.coins.Coin;
import com.mygdx.game.desktop.coins.CoinSubject;
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
 * The character that the user controls.
 * An instance of this class is created by the Model class.
 * This class uses {@link PlayerAnimations}, {@link Firearm} and {@link CoinSubject}.
 * This class is used by Model, {@link Store}, {@link PlayerController}
 * @author David
 * @author Imad
 * @author Anders
 */
public class Player extends Sapien {
    /**
     * booleans in order to determine which direction the player is moving
     */
    private boolean left, right, up, down;
    /**
     * boolean to determine whether the player is trying to shoot or not
     */
    private boolean triggerPulled = false;
    /**
     * boolean to determine whether player is in invincibility frame or nor
     */
    private boolean invincible;
    /**
     * timers which are used to limit sprinting and invincibility
     */
    private Timer timerInvinc, timerSprint;
    /**
     * The angle at which the player is aiming
     */
    private int aimAngle;
    /**
     * The maximum health that the player can have
     */
    private int maxHealth;
    /**
     * The current amount of money that the player has
     */
    private int money;
    /**
     * Instance of {@link PlayerAnimations} so that the player has animations
     */
    private PlayerAnimations animations;
    /**
     * boolean to determine whether the player has been hit by {@link Zombie} or not. Used for animations
     */
    private boolean playerHit;
    /**
     * The current weapon slot which the player has selected
     */
    private int currentWeapon;
    /**
     * An array to store the Weapons that the player is holding
     */
    private boolean hasSprintPerk = false;
    private boolean hasDoublePerk = false;
    private boolean hasMagnetPerk = false;

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

        textureAtlas = atlas;

        this.speed = 50*scale;

        weapons[0] = new Revolver(scale);

        currentWeapon = 0;

        health = 3;
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
        System.out.println(this.getX() + " " + this.getY());
    }

    /**
     * Method which delegates the drawing responsibility to animations
     * @param batch is the batch the program uses to draw the sprites
     */
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
            health =0;
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
            weapons[currentWeapon].fire(aimAngle, getX(), getY(), hasDoublePerk);
        }
    }

    /**
     * Adds to the <code>money</code> variable when a {@link Coin} object is picked up
     */
    public void coinGained() {
        money += 200;
    }

    public void giveFasterSprint(int price){
        if(price<money && !hasSprintPerk){
            hasSprintPerk = true;
        }
    }

    /**
     * Sets speed for sprinting as opposed to walking. Uses timer to control how long player can run
     * @param bool sprinting
     */
    public void setSprint(boolean bool){
        if(bool){
            this.speed = (float)75*scale;
            timerSprint = new Timer();
            if(hasSprintPerk){
                timerSprint.schedule(new RemindTaskSprint(), 6*1000);
            }
            else{
                timerSprint.schedule(new RemindTaskSprint(), 3*1000);
            }
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
     * If the player purchases a weapon, this method will give them the firearm, presuming they have enough money.
     * @param firearm = firearm to be given.
     * @param price = price of weapon (checked if less or equal to total player money)
     */
    public void giveWeapon(Firearm firearm, int price){
        if(price<=money){
            money -= price;
            for(int i = 0; i<3; i++){
                if (weapons[i] == null){
                    weapons[i] = firearm;
                    return;
                }
            }
            weapons[currentWeapon] = firearm;
        }
    }

    /**
     * If the player purchases ammo for an autorifle, this method will give them the ammo, presuming they have enough money.
     * @param firearm = ammo for this firearm is to be given.
     * @param price = price of ammo (checked if less or equal to total player money)
     */
    public void giveFullAmmoAutorifle(Firearm firearm, int price){
        if(price<=money){
            for(int i = 0; i<3; i++){
                if (weapons[i] != null && weapons[i] instanceof AutoRifle){
                    weapons[i] = firearm;
                    money -= price;
                    return;
                }
            }
        }
    }

    /**
     * If the player purchases ammo for a shotgun, this method will give them the ammo, presuming they have enough money.
     * @param firearm = ammo for this firearm is to be given.
     * @param price = price of ammo (checked if less or equal to total player money)
     */
    public void giveFullAmmoShotgun(Firearm firearm, int price){
        if(price<=money){
            for(int i = 0; i<3; i++){
                if (weapons[i] != null && weapons[i] instanceof Shotgun){
                    weapons[i] = firearm;
                    money -= price;
                    return;
                }
            }
        }
    }

    /**
     * If the player purchases ammo for a revolver, this method will give them the ammo, presuming they have enough money.
     * @param firearm = ammo for this firearm is to be given.
     * @param price = price of ammo (checked if less or equal to total player money)
     */
    public void giveFullAmmoRevolver(Firearm firearm, int price){
        if(price<=money) {
            for (int i = 0; i < 3; i++) {
                if (weapons[i] != null && weapons[i] instanceof Revolver) {
                    weapons[i] = firearm;
                    money -= price;
                    return;
                }
            }
        }
    }

    public void regainControls(){
        setLeft(false);
        setRight(false);
        setDown(false);
        setUp(false);
        setTriggerPulled(false);
        new PlayerController(this);
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
        if(weapons[weaponNumber] != null){
            currentWeapon = weaponNumber;
        }
    }

    public void setPlayerHit(boolean bool){
        playerHit = bool;
    }
    public boolean getPlayerHit(){
        return playerHit;
    }

    public void setDouble(boolean bool, int price){
        if(price<=money && !hasDoublePerk){
            money -= price;
            hasDoublePerk = bool;
        }
    }

    public void strongerMagnet(int price){
        if(price<=money && !hasMagnetPerk){
            hasMagnetPerk = true;
            money -= price;
            CoinSubject.getInstance().updateCoins(50,100);
        }
    }
}