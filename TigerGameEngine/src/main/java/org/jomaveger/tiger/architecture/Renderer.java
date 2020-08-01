package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Renderer implements IRenderer {

    private static final Logger LOG = LoggerFactory.getLogger(Renderer.class);

    private final IEngine engine;

    public Renderer(Engine engine) {
        this.engine = engine;
    }

    @Override
    public IEngine getEngine() {
        return this.engine;
    }

    @Override
    public void init() {
        getEngine().getSceneManager().start();
    }

    @Override
    public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {
        getEngine().getSceneManager().input(keyInputManager, mouseInputManager);
    }

    @Override
    public void update(float interval) {
        if (getEngine().getSceneManager().getScene().getLightManager() != null) {
            getEngine().getSceneManager().getScene().getLightManager().update(interval);
        }

        getEngine().getSceneManager().update(interval);
    }

    @Override
    public void render() {
        getEngine().getSceneManager().render();
    }

    @Override
    public void dispose() {
        getEngine().getSceneManager().getScene().dispose();
    }
}
