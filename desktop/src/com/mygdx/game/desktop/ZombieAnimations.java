package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The ZombieAnimations class handles animating the {@link Zombie} objects on screen.
 * These will change depending on the zombies state, including running, hitting, being hit and dying.
 */

public class ZombieAnimations extends ApplicationAdapter{
    private Batch batch;

    private TextureAtlas textureAtlas;
    private TextureAtlas runningBack;
    private TextureAtlas runningRight;
    private TextureAtlas runningFront;
    private TextureAtlas runningLeft;

    private TextureAtlas hittingBack;
    private TextureAtlas hittingFront;
    private TextureAtlas hittingLeft;
    private TextureAtlas hittingRight;

    private TextureAtlas deadAtlas;

    private Animation animation;
    private float elapsedTime = 0f;
    private Zombie zombie;
    private float startHitTime;
    private List<TextureAtlas> list;

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
            if(zombie.nearPlayer() < 15*zombie.scale){
                zombie.setHitPlayer(true);
            }
            zombie.setMoving(true);
            startHitTime = 0;
        }

        if(zombie.nearPlayer() < 15*zombie.scale || !zombie.moving()){
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
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),zombie.getX(),zombie.getY(),(int)(42*zombie.scale/2),(int)(40*zombie.scale/2));
    }
}
