package com.mygdx.game.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationTest extends ApplicationAdapter {
    SpriteBatch batch;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0f;

    public Animation createAnimation () {
        batch = new SpriteBatch();
        textureAtlas = new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
        return animation = new Animation(1f/15f, textureAtlas.getRegions());
    }

    public TextureAtlas getTextureAtlas(){
        return new TextureAtlas(Gdx.files.internal("Player/Angle1/running/running.atlas"));
    }

    @Override
    public void dispose() {
        //batch.dispose();
        textureAtlas.dispose();
    }

    @Override
    public void render () {
        elapsedTime += Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime,true),400,400);
        batch.end();
    }
}
