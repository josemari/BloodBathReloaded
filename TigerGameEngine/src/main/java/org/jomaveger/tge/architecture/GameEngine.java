package org.jomaveger.tge.architecture;

import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.time.Timer;
import org.jomaveger.tge.util.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class GameEngine implements IGameEngine {

    private static final Logger LOG = LoggerFactory.getLogger(GameEngine.class);

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    private final ScreenManager window;
    private final Timer timer;
    private final KeyInputManager keyInputManager;
    private final MouseInputManager mouseInputManager;
    private final IGameLogic gameLogic;
    private final Thread gameLoopThread;

    private String windowTitle;

    public GameEngine(String windowTitle, int width, int height, IGameLogic gameLogic) {
        this.windowTitle = windowTitle;
        this.window = new ScreenManager(windowTitle, width, height);
        this.timer = new Timer();
        this.keyInputManager = new KeyInputManager(this.window);
        this.mouseInputManager = new MouseInputManager(this.window);
        this.gameLogic = gameLogic;
        this.gameLoopThread = new Thread(this);
    }
    
    public GameEngine(String windowTitle, IGameLogic gameLogic) {
        this.windowTitle = null;
        this.window = new ScreenManager(windowTitle);
        this.timer = new Timer();
        this.keyInputManager = new KeyInputManager(this.window);
        this.mouseInputManager = new MouseInputManager(this.window);
        this.gameLogic = gameLogic;
        this.gameLoopThread = new Thread(this);
    }

    @Override
    public IGameLogic getGameLogic() {
        return this.gameLogic;
    }

    @Override
    public ScreenManager getWindow() {
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
    public void setWindowTitle(String title) {
        this.windowTitle = title;

        if (this.window != null) {
            this.window.setWindowTitle(this.windowTitle);
        }
    }
    
    @Override
    public void start() {
        this.gameLoopThread.start();
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

    private void init() {
        LOG.info("Initializing Tiger Engine ...");

        this.window.init();
        this.timer.init();
        this.keyInputManager.init();
        this.mouseInputManager.init();
        this.gameLogic.init(this.window);
        
        LOG.info("Tiger engine initialized successfully!");
    }

    @Override
    public void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running && !this.window.windowShouldClose()) {

            elapsedTime = this.timer.getElapsedTime();
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
        this.gameLogic.input(this.window, this.keyInputManager, this.mouseInputManager);
    }

    private void render() {
        this.gameLogic.render(this.window);
        this.window.update();
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

    private void update(float interval) {
        this.gameLogic.update(interval, this.keyInputManager, this.mouseInputManager, this.window);
    }

    @Override
    public void dispose() {
        this.gameLogic.dispose();
        this.window.dispose();
        this.keyInputManager.dispose();
        this.mouseInputManager.dispose();

        LOG.info("Tiger engine has stopped.");
        System.exit(0);
    }
}
