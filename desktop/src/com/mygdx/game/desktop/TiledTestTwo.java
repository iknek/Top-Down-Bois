package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter implements InputProcessor {
    private OrthographicCamera camera;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();
    private Player player;
    private int scale;
    private Rounds rounds;

    public TiledTestTwo (int scale){
        this.scale = scale;
    }

    @Override
    public void create () {
        View.createInstance(this.scale);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        TextureAtlas playerAtlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));

        camera = View.getInstance().createCamera(w, h);

        player = new Player(playerAtlas,w/2,h/2,this.scale);

        rounds = new Rounds(scale,w, h);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        View.getInstance().setView(camera);
        View.getInstance().render();
        movableSubject.notifyUpdate();
        collisionController.checkCollisions(View.getInstance(), player, this.scale);
        movableSubject.removeDeleted();

        movableSubject.playerLocation((int) player.getX(),(int) player.getY());
        rounds.checkNewRound(player);
    }

    @Override
    public boolean keyDown(int keycode) {
        return player.getInputProcessor().keyDown(keycode);
    }

    @Override public boolean keyUp(int keycode) {
        return player.getInputProcessor().keyUp(keycode);
    }

    @Override public boolean keyTyped(char character) {
        return false;
    }

    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return player.getInputProcessor().touchDown(screenX,screenY,pointer,button);
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return player.getInputProcessor().touchUp(screenX,screenY,pointer,button);
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return player.getInputProcessor().touchDragged(screenX,screenY,pointer);
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}