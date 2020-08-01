package org.jomaveger.tge.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import org.jomaveger.tge.graphics.Window;
import org.jomaveger.tge.util.lang.IDisposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KeyInputManager implements IDisposable, KeyListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(KeyInputManager.class);

	private static final int KEY_COUNT = 256;
	
    private Window window;
    private boolean[] keys;
    private int[] polled;

    public KeyInputManager(Window window) {
        this(window, false);
    }

    public KeyInputManager(Window window, boolean init) {
        this.window = window;
        this.keys = new boolean[KEY_COUNT];
        this.polled = new int[KEY_COUNT];
        
        if (init) {
            init();
        }
    }

    public void init() {
        this.window.addKeyboardManager(this);
    }

    public Window getWindow() {
        return this.window;
    }

    public boolean keyDown(int keyCode) {
        return polled[keyCode] > 0;
    }

    public boolean keyDownOnce(int keyCode) {
        return polled[keyCode] == 1;
    }

    public synchronized void poll() {
        for (int i = 0; i < keys.length; ++i) {
            if (keys[i]) {
                polled[i]++;
            } else {
                polled[i] = 0;
            }
        }
    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < KEY_COUNT && keys != null) {
            keys[keyCode] = true;
        }
    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < KEY_COUNT && keys != null) {
            keys[keyCode] = false;
        }
    }

    @Override
    public synchronized void keyTyped(KeyEvent e) {
    }

    @Override
    public void dispose() {
    	keys = null;
    	polled = null;
    }
}
