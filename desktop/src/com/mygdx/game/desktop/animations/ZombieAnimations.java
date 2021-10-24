package com.mygdx.game.desktop.animations;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.desktop.sapiens.Zombie;
import java.util.ArrayList;
import java.util.List;

/**
 * The ZombieAnimations class handles animating the {@link Zombie} objects on screen.
 * These will change depending on the zombies state, including running, hitting, being hit and dying.
 * This class is created by {@link Zombie} and uses {@link Zombie} to get essential information for animations
 * This class is used by {@link Zombie} when it is called to render itself.
 * @author Imad
 */

public class ZombieAnimations extends ApplicationAdapter{
    /**
     * Batch which is used to draw the animations
     */
    private Batch batch;
    /**
     * The current textureAtlas of the player
     */
    private TextureAtlas textureAtlas;

    /**
     * Texture atlases for when the zombie is running as well as hitting
     */
    private TextureAtlas runningBack, runningRight, runningFront, runningLeft;
    private TextureAtlas hittingBack, hittingFront, hittingLeft, hittingRight;

    /**
     * Texture atlas for when zombie dies
     */
    private TextureAtlas deadAtlas;
    /**
     * The current animation which the player should be in
     */
    private Animation animation;
    /**
     * A variable which keeps track of the time passed
     */
    private float elapsedTime = 0f;
    /**
     * A list of all these texture atlases so that they can easily be disposed
     */
    private List<TextureAtlas> list;
    /**
     * An instance of the zombie which the animations are connected to.
     */
    private Zombie zombie;
    /**
     * The game time at which this particular zombie starts the hitting animation
     */
    private float startHitTime;

    /**
     * Constructor for {@link ZombieAnimations} class.
     * @param batch {@link Batch} of animations
     * @param zombie {@link Zombie} object being animated
     */
    public ZombieAnimations (Batch batch, Zombie zombie) {
        this.batch = batch;
        this.zombie = zombie;
        preLoad();
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Zombie} is running.
     */
    private void renderRunning(){
        switch(zombie.getRenderAngle()){
            case 0:
                textureAtlas = runningBack;
                break;
            case 90:
                textureAtlas = runningRight;
                break;
            case 180:
                textureAtlas = runningFront;
                break;
            case 270:
                textureAtlas = runningLeft;
                break;
        }
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Zombie} is standing idle in.
     */
    private void renderHit(){
        switch (zombie.getRenderAngle()){
            case 0:
                textureAtlas = hittingBack;
                break;
            case 90:
                textureAtlas = hittingRight;
                break;
            case 180:
                textureAtlas = hittingFront;
                break;
            case 270:
                textureAtlas = hittingLeft;
                break;
        }
    }

    /**
     * Sets the {@link TextureAtlas} depending on if the {@link Zombie} is moving, idle, or being hit.
     * Starts a looping animation and draws a {@link Batch} with it.
     */
    public void render () {
        float animationTime = 1f/20f * hittingBack.getRegions().size;
        elapsedTime += Gdx.graphics.getDeltaTime();

        if(elapsedTime > animationTime && elapsedTime-startHitTime > animationTime){
            if(zombie.nearPlayer() < 15*zombie.getScale()){
                zombie.setHitPlayer(true);
            }
            zombie.setMoving(true);
            startHitTime = 0;
        }

        if(zombie.nearPlayer() < 15*zombie.getScale() || !zombie.moving()){
            if(startHitTime == 0){
                startHitTime = elapsedTime;
                zombie.setMoving(false);
            }
            renderHit();
        }
        else{
            renderRunning();
        }

        animation = new Animation(1f/20f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),zombie.getX(),zombie.getY(),(int)(42*zombie.getScale()/2),(int)(40*zombie.getScale()/2));
    }

    /**
     * Method to preload all {@link TextureAtlas} when game launches.
     * This is done so that the game does not have to do it during runtime, and so lag is reduced.
     */
    private void preLoad(){
        list = new ArrayList();

        runningBack = new TextureAtlas(Gdx.files.internal("Coffin/Back/running/running"));
        runningRight = new TextureAtlas(Gdx.files.internal("Coffin/Right/running/running"));;
        runningFront = new TextureAtlas(Gdx.files.internal("Coffin/Front/running/running"));
        runningLeft = new TextureAtlas(Gdx.files.internal("Coffin/Left/running/running"));

        hittingBack = new TextureAtlas(Gdx.files.internal("Coffin/Back/hitting/hitting"));
        hittingRight = new TextureAtlas(Gdx.files.internal("Coffin/Right/hitting/hitting"));
        hittingFront =  new TextureAtlas(Gdx.files.internal("Coffin/Front/hitting/hitting"));
        hittingLeft = new TextureAtlas(Gdx.files.internal("Coffin/Left/hitting/hitting"));

        deadAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Dead/dead"));

        list.add(runningBack);
        list.add(runningRight);
        list.add(runningLeft);
        list.add(runningFront);

        list.add(hittingBack);
        list.add(hittingRight);
        list.add(hittingFront);
        list.add(hittingLeft);

        list.add(deadAtlas);
    }

    /**
     * Disposes of {@link TextureAtlas} and {@link Batch}.
     */
    @Override
    public void dispose() {
        batch.dispose();
        for (int i = 0; i < 8; i++) {
            list.get(i).dispose();
        }
    }
}
