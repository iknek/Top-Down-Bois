package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The ZombieAnimations class handles animating the {@link Zombie} objects on screen.
 * These will change depending on the zombies state, including running, hitting, being hit and dying.
 */

public class ZombieAnimations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Zombie zombie;
    private float startHitTime;

    /**
     * Constructor for {@link ZombieAnimations} class.
     * @param batch {@link Batch} of animations
     * @param zombie {@link Zombie} object being animated
     */
    public ZombieAnimations (Batch batch, Zombie zombie) {
        this.batch = batch;
        this.zombie = zombie;
    }

    /**
     * Disposes of {@link TextureAtlas} and {@link Batch}.
     */
    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Zombie} is running.
     */
    private void renderRunning(){

        switch(zombie.getRenderAngle()){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Back/running/running"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Right/running/running"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Front/running/running"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Left/running/running"));
                break;
        }
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Zombie} is standing idle in.
     */
    private void renderHit(){
        switch (zombie.getRenderAngle()){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Back/hitting/hitting"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Right/hitting/hitting"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Front/hitting/hitting"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Coffin/Left/hitting/hitting"));
                break;
        }
    }

    /**
     * Sets the {@link TextureAtlas} depending on if the {@link Zombie} is moving, idle, or being hit.
     * Starts a looping animation and draws a {@link Batch} with it.
     */
    public void render () {
        float animationTime = 1f/20f * new TextureAtlas(Gdx.files.internal("Coffin/Left/hitting/hitting")).getRegions().size;
        elapsedTime += Gdx.graphics.getDeltaTime();

        if(elapsedTime > animationTime && elapsedTime-startHitTime > animationTime){
            if(zombie.nearPlayer() < 25*zombie.scale){
                //register hit on player
            }
            zombie.setMoving(true);
            startHitTime = 0;
        }

        if(zombie.nearPlayer() < 25*zombie.scale || !zombie.moving()){
            if(startHitTime == 0){
                startHitTime = elapsedTime;
                zombie.setMoving(false);
            }
            renderHit();
        } else{
            renderRunning();
        }

        animation = new Animation(1f/20f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),zombie.getX(),zombie.getY(),(int)(42*zombie.scale/2),(int)(40*zombie.scale/2));
    }
}
