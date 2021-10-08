package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.Timer;
import java.util.TimerTask;

public class ZombieAnimations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Zombie zombie;
    private CollisionController collisionController = new CollisionController();
    private float startHitTime;

    /**
     * constructor for animations
     * @param batch = animations batch
     * @param zombie = zombie
     */
    public ZombieAnimations (Batch batch, Zombie zombie) {
        this.batch = batch;
        this.zombie = zombie;
    }

    /**
     * Disposes of textureatlas and batch
     */
    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    /**
     * Switch statement that sets textureatlas according to direction the zombie is running.
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
     * Switch statement that sets textureatlas according to direction the player is standing idle in.
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

    boolean hitting = false;

    /**
     * Render method that sets the textureatlas according to if the player is moving, idle, or hit.
     * Starts a looping animation and draws a batch with it.
     */
    public void render () {
        animation = new Animation(1f/20f, (new TextureAtlas(Gdx.files.internal("Coffin/Left/hitting/hitting")).getRegions()));

        elapsedTime += Gdx.graphics.getDeltaTime();


        if(elapsedTime > animation.getAnimationDuration() && elapsedTime-startHitTime > animation.getAnimationDuration()){
            hitting = false;
            startHitTime = 0;
        }

        if(zombie.nearPlayer() < 25* zombie.scale || hitting){
            if(startHitTime == 0){
                startHitTime = elapsedTime;
                hitting = true;
            }
            zombie.setMoving(false);
            renderHit();
        } else{
            zombie.setMoving(true);
            renderRunning();
        }

        animation = new Animation(1f/20f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),zombie.getX(),zombie.getY(),(int)(42*zombie.scale/2),(int)(40*zombie.scale/2));
    }
}
