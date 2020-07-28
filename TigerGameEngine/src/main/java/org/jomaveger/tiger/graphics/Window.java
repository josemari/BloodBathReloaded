package org.jomaveger.tiger.graphics;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

import org.jomaveger.tiger.input.KeyInputManager;
import org.jomaveger.tiger.input.MouseInputManager;
import org.jomaveger.tiger.util.exception.ExceptionUtils;
import org.jomaveger.tiger.util.lang.IDisposable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Window implements IDisposable {

    private static final Logger LOG = LoggerFactory.getLogger(Window.class);

    private final String title;

    private final int width;

    private final int height;

    private final boolean fullscreen;

    private GraphicsDevice screen;

    private JFrame frame;
    
    private Framebuffer framebuffer;
    
    private KeyInputManager keyInputManager;

	private MouseInputManager mouseInputManager;

    public Window(String windowTitle, int width, int height) {
        this.title = windowTitle;
        this.width = width;
        this.height = height;
        this.fullscreen = false;
    }

    public Window(String windowTitle) {
        this.fullscreen = true;
        this.title = windowTitle;
        this.width = 0;
        this.height = 0;
    }

    public boolean isFullscreen() {
        return fullscreen;
    }
    
    public boolean isShowing() {
    	return frame.isShowing();
    }

    public void init() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        screen = ge.getDefaultScreenDevice();

        if (fullscreen) {
            this.setFullScreen(this.getCurrentDisplayMode());
        } else {
            this.setWindowedScreen();
        }
    }

    private DisplayMode getCurrentDisplayMode() {
        return screen.getDisplayMode();
    }

    private void setFullScreen(DisplayMode displayMode) {
        frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        if (!screen.isFullScreenSupported()) {
            LOG.error("ERROR: Full Screen Not Supported!!!");
            System.exit(-1);
        }
        screen.setFullScreenWindow(frame);
        if (displayMode != null && screen.isDisplayChangeSupported()) {
            try {
                screen.setDisplayMode(displayMode);
            } catch (IllegalArgumentException ex) {
                LOG.error(ExceptionUtils.getExpandedMessage(ex));
            }
        }
        this.framebuffer = new Framebuffer(displayMode.getWidth(), displayMode.getHeight());
    }

    private void setWindowedScreen() {
        frame = new JFrame();
        frame.setSize(this.width, this.height);
        this.framebuffer = new Framebuffer(this.width, this.height);
        frame.setResizable(true);
        frame.setTitle(this.getWindowTitle());
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame.setBackground(Color.BLACK);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public int getWidth() {
        java.awt.Window window = screen.getFullScreenWindow();
        if (window != null) {
            return window.getWidth();
        } else {
            return frame.getWidth();
        }
    }

    public int getHeight() {
        java.awt.Window window = screen.getFullScreenWindow();
        if (window != null) {
            return window.getHeight();
        } else {
            return frame.getHeight();
        }
    }

    private Graphics2D getGraphics() {
    	BufferStrategy bufferStrategy = getBufferStrategy();
        return (Graphics2D) bufferStrategy.getDrawGraphics();
    }

	private BufferStrategy getBufferStrategy() {
		BufferStrategy bufferStrategy = frame.getBufferStrategy();
        if (bufferStrategy == null) {
            frame.createBufferStrategy(3);
            bufferStrategy = frame.getBufferStrategy();
        }
		return bufferStrategy;
	}

    public void render() {
    	this.framebuffer.swapBuffers();
    	getGraphics().drawImage(this.framebuffer.getFrameBuffer(), 0, 0, getWidth(), getHeight(), frame);
        if (!getBufferStrategy().contentsLost()) {
        	getBufferStrategy().show();
        }
    }

    public Framebuffer getFramebuffer() {
		return this.framebuffer;
	}

	public String getWindowTitle() {
        return title;
    }

    public void setWindowTitle(String title) {
        frame.setTitle(title);
    }

    public void addKeyboardManager(KeyInputManager keyInputManager) {
    	this.keyInputManager = keyInputManager;
        frame.addKeyListener(keyInputManager);
    }

    @Override
    public void dispose() {
        java.awt.Window window = screen.getFullScreenWindow();
        if (window != null) {
            screen.setFullScreenWindow(null);
            window.dispose();
        }
        frame = null;
        framebuffer.dispose();
    }

	public boolean windowShouldClose() {
		return this.keyInputManager.keyDown(KeyEvent.VK_ESCAPE);
	}

	public void clear() {
		getGraphics().clearRect(0, 0, getWidth(), getHeight());
	}

	public void addMouseManager(MouseInputManager mouseInputManager) {
		this.mouseInputManager = mouseInputManager;
		frame.addMouseListener(mouseInputManager);
		frame.addMouseMotionListener(mouseInputManager);
		frame.addMouseWheelListener(mouseInputManager);
	}
	
	public JFrame getWindowComponent() {
		return frame;
	}
}
