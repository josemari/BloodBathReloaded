package org.jomaveger.tiger.architecture;

import org.jomaveger.tiger.graphics.Window;
import org.jomaveger.tiger.input.KeyInputManager;
import org.jomaveger.tiger.input.MouseInputManager;
import org.jomaveger.tiger.time.Timer;
import org.jomaveger.tiger.util.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Engine implements IEngine {

    private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    private final IRenderer renderer;
    private final SceneManager sceneManager;
    private final Window window;
    private final Timer timer;
    private final KeyInputManager keyInputManager;
    private final MouseInputManager mouseInputManager;

    private String windowTitle;

    private final IScene scene;

    private double lastFps;

    private int fps;

    public Engine(String windowTitle, int width, int height, IScene scene) {
        this.windowTitle = windowTitle;
        this.sceneManager = new SceneManager(this);
        this.window = new Window(windowTitle, width, height);
        this.renderer = new Renderer(this);
        this.timer = new Timer();
        this.keyInputManager = new KeyInputManager(this.window);
        this.mouseInputManager = new MouseInputManager(this.window);
        this.scene = scene;
    }
    
    public Engine(String windowTitle, IScene scene) {
        this.windowTitle = null;
        this.sceneManager = new SceneManager(this);
        this.window = new Window(windowTitle);
        this.renderer = new Renderer(this);
        this.timer = new Timer();
        this.keyInputManager = new KeyInputManager(this.window);
        this.mouseInputManager = new MouseInputManager(this.window);
        this.scene = scene;
    }

    @Override
    public SceneManager getSceneManager() {
        return this.sceneManager;
    }

    @Override
    public IRenderer getRenderer() {
        return this.renderer;
    }

    @Override
    public Window getWindow() {
        return this.window;
    }

    @Override
    public KeyInputManager getKeyInputManager() {
        return this.keyInputManager;
    }

    @Override
    public MouseInputManager getMouseInputManager() {
        return this.mouseInputManager;
    }

    @Override
    public boolean hasRenderer() {
        return this.renderer != null;
    }

    @Override
    public void setWindowTitle(String title) {
        this.windowTitle = title;

        if (this.window != null) {
            this.window.setWindowTitle(this.windowTitle);
        }
    }

    @Override
    public void init() {
        LOG.info("Initializing Tiger Engine ...");

        this.window.init();
        this.timer.init();
        this.keyInputManager.init();
        this.mouseInputManager.init();
        this.sceneManager.loadScene(scene, window.getFramebuffer());
        this.renderer.init();
        this.lastFps = timer.getTime();
        this.fps = 0;

        LOG.info("Tiger engine initialized successfully!");
    }

    @Override
    public void run() {
        try {
            this.init();
            this.gameLoop();
            this.dispose();
        } catch (Throwable ex) {
            LOG.error(ExceptionUtils.getExpandedMessage(ex));
        }
    }

    @Override
    public void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !window.windowShouldClose()) {

            elapsedTime = timer.getElapsedTime();
            accumulator += elapsedTime;

            this.input();

            while (accumulator >= interval) {
                this.update(interval);
                accumulator -= interval;
            }

            this.render();

            sync();
        }
    }

    private void input() {
    	this.keyInputManager.poll();
        this.mouseInputManager.poll();
        this.renderer.input(keyInputManager, mouseInputManager);
    }

    private void render() {
        if (timer.getLastLoopTime() - lastFps > 1) {
            lastFps = timer.getLastLoopTime();
            window.setWindowTitle(windowTitle + " - " + fps + " FPS");
            fps = 0;
        }
        fps++;

        this.window.clear();
        this.renderer.render();
        this.window.render();
    }

    private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }

    @Override
    public void update(float interval) {
        this.renderer.update(interval);
    }

    @Override
    public void dispose() {
        this.renderer.dispose();
        this.window.dispose();
        this.keyInputManager.dispose();
        this.mouseInputManager.dispose();

        LOG.info("Tiger engine has stopped.");
        System.exit(0);
    }
}
