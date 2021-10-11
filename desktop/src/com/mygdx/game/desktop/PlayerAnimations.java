package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The Animations class handles animating the {@link Player} on screen.
 * These will change depending on the players state, including running, shooting, being hit and dying.
 */
public class PlayerAnimations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Player player;

    private TextureAtlas runningBack;
    private TextureAtlas runningRight;
    private TextureAtlas runningFront;
    private TextureAtlas runningLeft;

    private TextureAtlas runningAngle1;
    private TextureAtlas runningAngle2;
    private TextureAtlas runningAngle3;
    private TextureAtlas runningAngle4;

    private TextureAtlas idleRight;
    private TextureAtlas idleLeft;
    private TextureAtlas idleBack;
    private TextureAtlas idleFront;
    private TextureAtlas idleAngle1;
    private TextureAtlas idleAngle2;
    private TextureAtlas idleAngle3;
    private TextureAtlas idleAngle4;


    private List<TextureAtlas> list;

    /**
     * Constructor for {@link Animation} class
     * @param batch {@link Batch} of animations
     * @param player {@link Player} object being animated
     */
    public PlayerAnimations(Batch batch, Player player) {
        this.batch = batch;
        this.player = player;
        preLoad();
    }


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
    private void renderRunning(){
        switch(player.angle){
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
    private void renderIdle(){
        switch (player.angle){
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
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(player.moving()){
            renderRunning();
        }
        if(player.getPlayerHit()){
            textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/dead/dead")); //TODO Controls should be locked for length of animation? Otherwise they wont fully play.
        } else if(!player.moving()){
            renderIdle();
        }

        animation = new Animation(1f/13f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),player.getX()-(player.scale*25/2),player.getY()-(player.scale*6/2),(int)(75*player.scale/2),(int)(75*player.scale/2));
    }

}
