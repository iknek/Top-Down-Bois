package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class ZombieAnimations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Zombie zombie;
    private CollisionController collisionController = new CollisionController();

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

    /**
     * Render method that sets the tetureatlas according to if the player is moving, idle, or hit.
     * Starts a looping animation and draws a batch with it.
     */
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        renderRunning();
        animation = new Animation(1f/20f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),zombie.getX()-15,zombie.getY()-3,(int)(42*zombie.scale/2),(int)(40*zombie.scale/2));
    }

}
