package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;

public class Animations extends ApplicationAdapter{
    private Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;
    private Player player;
    private CollisionController collisionController = new CollisionController();

    /**
     * constructor for animations
     * @param batch = animations batch
     * @param player = player
     */
    public Animations (Batch batch, Player player) {
        this.batch = batch;
        this.player = player;
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
     * Switch statement that sets textureatlas according to direction the player is running.
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
     * Switch statement that sets textureatlas according to direction the player is standing idle in.
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
     * Render method that sets the tetureatlas according to if the player is moving, idle, or hit.
     * Starts a looping animation and draws a batch with it.
     */
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(player.moving()){
            renderRunning();
        }
        if(collisionController.playerZombieCollision(player)){
            textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/dead/dead")); //TODO Controlls should be locked for length of animation? Otherwise they dont fully play.
        }
        else if(!player.moving() && !collisionController.playerZombieCollision(player)){
            renderIdle();
        }

        animation = new Animation(1f/13f, textureAtlas.getRegions());
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),player.getX()-15,player.getY()-3,(int)(75*player.scale/2),(int)(75*player.scale/2));
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
