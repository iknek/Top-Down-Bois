package com.mygdx.game.desktop.animations;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.desktop.sapiens.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * The Animations class handles animating the {@link Player} on screen.
 * These will change depending on the players state, including running, shooting, being hit and dying.
 */
public class PlayerAnimations extends ApplicationAdapter{

    /**
     * Batch which is used to draw the animations
     */
    private Batch batch;
    /**
     * The current textureAtlas of the player
     */
    private TextureAtlas textureAtlas;
    /**
     * The current animation which the player should be in
     */
    private Animation animation;
    /**
     * A variable which keeps track of the time passed
     */
    private float elapsedTime = 0f;

    /**
     * Texture atlases for running
     */
    private TextureAtlas runningBack, runningRight, runningFront, runningLeft;
    private TextureAtlas runningAngle1, runningAngle2, runningAngle3, runningAngle4;

    /**
     * Texture atlases for when player is idle
     */
    private TextureAtlas idleRight, idleLeft, idleBack, idleFront;
    private TextureAtlas idleAngle1, idleAngle2, idleAngle3, idleAngle4;

    /**
     * A list of all these texture atlases so that they can easily be disposed
     */
    private List<TextureAtlas> list;

    /**
     * Constructor for {@link Animation} class
     * @param batch {@link Batch} of animations
     */
    public PlayerAnimations(Batch batch) {
        this.batch = batch;
        preLoad();
    }

    /**
     * Method to preload all {@link TextureAtlas} when game launches.
     * This is done so that the game does not have to do it during runtime, and so lag is reduced.
     */
    private void preLoad(){
        list = new ArrayList();

        runningBack = new TextureAtlas(Gdx.files.internal("Player/Back/running/BackRunningSheet.atlas"));
        runningRight = new TextureAtlas(Gdx.files.internal("Player/Side/running/SideRunningSheet.atlas"));
        runningFront = new TextureAtlas(Gdx.files.internal("Player/Front/running/FrontRunningSheet.atlas"));
        runningLeft = new TextureAtlas(Gdx.files.internal("Player/Left/running/running.atlas"));

        list.add(runningBack);
        list.add(runningRight);
        list.add(runningLeft);
        list.add(runningFront);

        runningAngle1 = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
        runningAngle2 = new TextureAtlas(Gdx.files.internal("Player/Angle2/running/Angle2RunningSheet.atlas"));
        runningAngle3 = new TextureAtlas(Gdx.files.internal("Player/Angle3/running/running.atlas"));
        runningAngle4 = new TextureAtlas(Gdx.files.internal("Player/Angle4/running/running.atlas"));

        list.add(runningAngle1);
        list.add(runningAngle2);
        list.add(runningAngle3);
        list.add(runningAngle4);

        idleBack = new TextureAtlas(Gdx.files.internal("Player/Back/idle/idle"));
        idleRight = new TextureAtlas(Gdx.files.internal("Player/Side/idle/idle"));
        idleFront = new TextureAtlas(Gdx.files.internal("Player/Front/idle/idle"));
        idleLeft = new TextureAtlas(Gdx.files.internal("Player/Left/idle/idle"));

        list.add(idleBack);
        list.add(idleRight);
        list.add(idleFront);
        list.add(idleLeft);

        idleAngle1 = new TextureAtlas(Gdx.files.internal("Player/Angle1/idle/idle"));
        idleAngle2 = new TextureAtlas(Gdx.files.internal("Player/Angle2/idle/idle"));
        idleAngle3 = new TextureAtlas(Gdx.files.internal("Player/Angle3/idle/idle"));
        idleAngle4 = new TextureAtlas(Gdx.files.internal("Player/Angle4/idle/idle"));

        list.add(idleAngle1);
        list.add(idleAngle2);
        list.add(idleAngle3);
        list.add(idleAngle4);
    }

    /**
     * Disposes of {@link TextureAtlas} and {@link Batch}.
     */
    @Override
    public void dispose() {
        batch.dispose();
        for (int i = 0; i < 15; i++) {
            list.get(i).dispose();
        }
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Player} is running.
     */
    private void renderRunning(int angle){
        switch(angle){
            case 0:
                textureAtlas = runningBack;
                break;
            case 45:
                textureAtlas = runningAngle2;
                break;
            case 90:
                textureAtlas = runningRight;
                break;
            case 135:
                textureAtlas = runningAngle1;
                break;
            case 180:
                textureAtlas = runningFront;
                break;
            case 225:
                textureAtlas = runningAngle3;
                break;
            case 270:
                textureAtlas = runningLeft;
                break;
            case 315:
                textureAtlas = runningAngle4;
                break;
        }
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Player} is standing idle in.
     */
    private void renderIdle(int angle){
        switch (angle){
            case 0:
                textureAtlas = idleBack;
                break;
            case 45:
                textureAtlas = idleAngle2;
                break;
            case 90:
                textureAtlas = idleRight;
                break;
            case 135:
                textureAtlas = idleAngle1;
                break;
            case 180:
                textureAtlas = idleFront;
                break;
            case 225:
                textureAtlas = idleAngle3;
                break;
            case 270:
                textureAtlas = idleLeft;
                break;
            case 315:
                textureAtlas = idleAngle4;
                break;
        }
    }

    /**
     * Sets the {@link TextureAtlas} according to if the {@link Player} is moving, idle, or being hit.
     * Starts a looping animation and draws a {@link Batch} with it.
     */
    public void render (float x, float y, boolean moving, float scale, boolean hit, int angle) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(moving){
            renderRunning(angle);
        }
        if(hit){
            textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/dead/dead")); //TODO Controls should be locked for length of animation? Otherwise they wont fully play.
        } else if(!moving){
            renderIdle(angle);
        }

        animation = new Animation(1f/13f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),x-(scale*25/2),y-(scale*6/2),(int)(75*scale/2),(int)(75*scale/2));
    }
}
