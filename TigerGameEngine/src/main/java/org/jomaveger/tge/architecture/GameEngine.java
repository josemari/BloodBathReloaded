package org.jomaveger.tge.architecture;

import java.awt.Graphics2D;

import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.InputManager;
import org.jomaveger.tge.time.Timer;
import org.jomaveger.tge.util.exception.ExceptionUtils;
import org.jomaveger.tge.util.lang.IDisposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class GameEngine implements Runnable, IDisposable {

    protected static final Logger LOG = LoggerFactory.getLogger(GameEngine.class);

    public static final int TARGET_FPS = 75;
    public static final int TARGET_UPS = 30;

    protected final ScreenManager window;
    private final Timer timer;
    private final Thread gameLoopThread;
    
    protected InputManager inputManager;
    
    private boolean isRunning;

    public GameEngine(String windowTitle, int width, int height) {
        this.window = new ScreenManager(windowTitle, width, height);
        this.window.init();
        if(this.window.getFullScreenWindow() != null) {
        	this.inputManager = new InputManager(this.window.getFullScreenWindow());	
        } else {
        	this.inputManager = new InputManager(this.window.getWindowComponent());
        }
        this.timer = new Timer();
        this.gameLoopThread = new Thread(this);
    }
    
    public GameEngine(String windowTitle) {
        this.window = new ScreenManager(windowTitle);
        this.window.init();
        if(this.window.getFullScreenWindow() != null) {
        	this.inputManager = new InputManager(this.window.getFullScreenWindow());	
        } else {
        	this.inputManager = new InputManager(this.window.getWindowComponent());
        }
        this.timer = new Timer();
        this.gameLoopThread = new Thread(this);
    }

    public ScreenManager getWindow() {
        return this.window;
    }
    
	public InputManager getInputManager() {
		return this.inputManager;
	}
    
    public void start() {
        this.gameLoopThread.start();
    }
    
    public void stop() {
    	this.isRunning = false;
    }

    @Override
    public void run() {
        try {
            this.init();
            this.gameLoop();
            this.dispose();
        } catch (Throwable ex) {
            LOG.error(ExceptionUtils.getExpandedMessage(ex));
        } finally {
        	if (this.window != null) {
                this.window.restoreScreen();
            }
        	LOG.info("Tiger engine has stopped.");
        	System.exit(0);
        }
    }
    
    private void init() {
        LOG.info("Initializing Tiger Engine ...");

        this.isRunning = true;
        this.timer.init();
        initialize();
        
        LOG.info("Tiger engine initialized successfully!");
    }
    
    protected abstract void initialize();

    public void gameLoop() {
        float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;
        
        while (isRunning) {

            elapsedTime = this.timer.getElapsedTime();
            accumulator += elapsedTime;

            this.input();

            while (accumulator >= interval) {
                this.update(interval);
                accumulator -= interval;
            }

            this.render();
        }
    }

    protected abstract void input();
    
    private void render() {
    	Graphics2D g = this.window.getGraphics();
        render(g);
        g.dispose();
        this.window.update();
    }
    
    protected abstract void render(Graphics2D g);

    protected abstract void update(float interval);
    

    @Override
    public void dispose() {
    	close();
        this.window.dispose();
    }
    
    protected abstract void close();
}
