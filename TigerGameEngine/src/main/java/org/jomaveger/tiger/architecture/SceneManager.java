package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.graphics.Framebuffer;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SceneManager {

    private static final Logger LOG = LoggerFactory.getLogger(SceneManager.class);

    private final IEngine engine;

    private IScene scene;

    public SceneManager(IEngine engine) {
        this.engine = engine;
        this.scene = null;
    }

    public IEngine getEngine() {
        return this.engine;
    }

    public IScene getScene() {
        return this.scene;
    }

    public boolean isSceneLoaded() {
        return this.scene != null;
    }

    public void loadScene(IScene scene, Framebuffer framebuffer) {
        boolean start = false;
        if (this.scene != null) {

            start = this.scene.isStarted();

            try {
                this.scene.dispose();
            } catch (Exception e) {
                LOG.error(ExceptionUtils.getExpandedMessage(e));
                throw new RuntimeException(e);
            }
        }

        this.scene = scene;

        this.scene.setEngine(getEngine());
        this.scene.setFramebuffer(framebuffer);

        LOG.info("Loading " + scene + " ... ");

        this.scene.load();

        LOG.info(scene + " loaded.");

        if (start) {
            this.scene.start();
        }
    }

    public void start() {
        if (isSceneLoaded()) {
            this.scene.start();
        }
    }

    public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {
        if (isSceneLoaded()) {
            this.scene.input(keyInputManager, mouseInputManager);
        }
    }

    public void update(float interval) {
        if (isSceneLoaded()) {
            this.scene.update(interval);
        }
    }

    public void render() {
        if (isSceneLoaded()) {
            this.scene.render();
        }
    }
}
