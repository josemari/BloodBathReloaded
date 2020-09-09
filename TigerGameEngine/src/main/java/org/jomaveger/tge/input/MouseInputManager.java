package org.jomaveger.tge.input;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.util.exception.ExceptionUtils;
import org.jomaveger.tge.util.lang.IDisposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MouseInputManager implements IDisposable, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(MouseInputManager.class);

	public static final Cursor INVISIBLE_CURSOR =
		Toolkit.getDefaultToolkit().createCustomCursor(
			Toolkit.getDefaultToolkit().getImage(""),
	            new Point(0,0), "invisible");

	private static final int BUTTON_COUNT = 3;

	private Point mousePos;
	private Point currentPos;
	private boolean[] mouse;
	private int[] polled;
	private int notches;
	private int polledNotches;

	private int dx, dy;
	private Robot robot;
	private ScreenManager window;
	private boolean relative;
	
	public MouseInputManager(ScreenManager window) {
		this(window, false);
	}
	
	public MouseInputManager(ScreenManager window, boolean init) {
		this.window = window;
		
		try {
			robot = new Robot();
		} catch (Exception ex) {
			LOG.error(ExceptionUtils.getExpandedMessage(ex));
		}

		mousePos = new Point(0, 0);
		currentPos = new Point(0, 0);
		mouse = new boolean[BUTTON_COUNT];
		polled = new int[BUTTON_COUNT];

		if (init)
			init();
	}
	
	public void init() {
		this.window.addMouseInputManager(this);
	}
	
	public ScreenManager getWindow() {
		return this.window;
	}
	
	@Override
	public void dispose() {
		robot = null;
		mousePos = null;
		currentPos = null;
		mouse = null;
		polled = null;
	}
	
	public synchronized void poll() {

		if (isRelative()) {
			mousePos = new Point(dx, dy);
		} else {
			mousePos = new Point(currentPos);
		}
		dx = dy = 0;

		polledNotches = notches;
		notches = 0;

		for (int i = 0; i < mouse.length; ++i) {
			if (mouse[i]) {
				polled[i]++;
			} else {
				polled[i] = 0;
			}
		}
	}

	public boolean isRelative() {
		return relative;
	}

	public void setRelative(boolean relative) {
		this.relative = relative;
		if (relative) {
			centerMouse();
		}
	}

	public Point getPosition() {
		return mousePos;
	}

	public int getNotches() {
		return polledNotches;
	}
	
	public boolean buttonDown(int button) {
		return polled[button - 1] > 0;
	}

	public boolean buttonDownOnce(int button) {
		return polled[button - 1] == 1;
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		int button = e.getButton() - 1;
		if (button >= 0 && button < mouse.length) {
			mouse[button] = true;
		}
	}
	
	@Override
	public synchronized void mouseReleased(MouseEvent e) {
		int button = e.getButton() - 1;
		if (button >= 0 && button < mouse.length) {
			mouse[button] = false;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {	
	}
	
	@Override
	public synchronized void mouseEntered(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseExited(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseDragged(MouseEvent e) {
		mouseMoved(e);
	}

	@Override
	public synchronized void mouseMoved(MouseEvent e) {
		if (isRelative()) {
			Point p = e.getPoint();
			Point center = getComponentCenter();
			dx += p.x - center.x;
			dy += p.y - center.y;
			centerMouse();
		} else {
			currentPos = e.getPoint();
		}
	}

	@Override
	public synchronized void mouseWheelMoved(MouseWheelEvent e) {
		notches += e.getWheelRotation();
	}
	
	private Point getComponentCenter() {
		int w = window.getWidth();
		int h = window.getHeight();
		return new Point(w / 2, h / 2);
	}

	private void centerMouse() {
		if (robot != null && window.isShowing()) {
			Point center = getComponentCenter();
			SwingUtilities.convertPointToScreen(center, window.getWindowComponent());
			robot.mouseMove(center.x, center.y);
		}
	}
}
