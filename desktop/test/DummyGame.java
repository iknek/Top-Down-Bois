import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglNativesLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.mygdx.game.desktop.views.View;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import static org.mockito.Mockito.mock;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;

public class DummyGame extends BlockJUnit4ClassRunner implements ApplicationListener {
    private Map<FrameworkMethod, RunNotifier> invokeInRender = new HashMap<FrameworkMethod, RunNotifier>();

    public DummyGame(Class<?> klass) throws InitializationError {
        super(klass);
        LwjglApplicationConfiguration conf = new LwjglApplicationConfiguration();
        new LwjglApplication(this, conf);
        Gdx.gl = mock(GL20.class);
    }

    @Override
    public void create() {
        //View.createInstance(1);
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
        synchronized (invokeInRender) {
            System.out.println(invokeInRender.size());
            for (Map.Entry<FrameworkMethod, RunNotifier> each : invokeInRender.entrySet()) {
                super.runChild(each.getKey(), each.getValue());
            }
            invokeInRender.clear();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        synchronized (invokeInRender) {
            // add for invoking in render phase, where gl context is available
            invokeInRender.put(method, notifier);
        }
        // wait until that test was invoked
        waitUntilInvokedInRenderMethod();
    }

    /**
     *
     */
    private void waitUntilInvokedInRenderMethod() {
        try {
            while (true) {
                Thread.sleep(10);
                synchronized (invokeInRender) {
                    if (invokeInRender.isEmpty())
                        break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

