package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

/**
 * The Animations class handles animating the {@link Player} on screen.
 * These will change depending on the players state, including running, shooting, being hit and dying.
 */
public class Animations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Player player;

    /**
     * Constructor for {@link Animations} class
     * @param batch {@link Batch} of animations
     * @param player {@link Player} object being animated
     */
    public Animations (Batch batch, Player player) {
        this.batch = batch;
        this.player = player;
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
     * Sets {@link TextureAtlas} according to direction the {@link Player} is running.
     */
    private void renderRunning(){
        switch(player.angle){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/running/BackRunningSheet.atlas"));
                break;
            case 45:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/running/Angle2RunningSheet.atlas"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/running/SideRunningSheet.atlas"));
                break;
            case 135:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/running/FrontRunningSheet.atlas"));
                break;
            case 225:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle3/running/running.atlas"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/running/running.atlas"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle4/running/running.atlas"));
                break;
        }
    }

    /**
     * Sets {@link TextureAtlas} according to direction the {@link Player} is standing idle in.
     */
    private void renderIdle(){
        switch (player.angle){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/idle/idle"));
                break;
            case 45:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/idle/idle"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/idle/idle"));
                break;
            case 135:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/idle/idle"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/idle/idle"));
                break;
            case 225:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle3/idle/idle"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/idle/idle"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle4/idle/idle"));
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
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),player.getX()-25,player.getY()-10,(int)(75*player.scale/2),(int)(75*player.scale/2));
    }

    /*
    private void renderShooting(){
        switch(player.angle){
            case 0:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Back/shooting/shooting.atlas"));
                break;
            case 45:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/shooting/shooting.atlas"));
                break;
            case 90:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Side/shooting/shooting.atlas"));
                break;
            case 135:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/shooting/shooting.atlas"));
                break;
            case 180:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Front/shooting/shooting.atlas"));
                break;
            case 225:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/shooting/shooting.atlas"));
                break;
            case 270:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Left/shooting/shooting.atlas"));
                break;
            case 315:
                textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle2/shooting/shooting.atlas"));
                break;
        }
    }*/

}
