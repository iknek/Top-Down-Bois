package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.*;

public class AnimationTest extends ApplicationAdapter {
    Batch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;

    public void create (Batch batch, String string) {
        this.batch = batch;
        textureAtlas = new TextureAtlas(Gdx.files.internal(string));
        animation = new Animation(1f/10f, textureAtlas.getRegions());

    }

    public TextureAtlas getTextureAtlas(){
        return new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureAtlas.dispose();
    }

    @Override
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),400,400,75,75);
        batch.end();
    }
}
