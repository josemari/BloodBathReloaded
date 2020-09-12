package org.jomaveger.tge.main.chapter1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.input.GameAction;

public class Chapter1Example1 extends GameEngine {

	private GameAction exit = new GameAction("exit");
	
	public Chapter1Example1(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	@Override
	protected void initialize() {
		inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
	}

	@Override
	protected void input() {
	}

	@Override
	protected void render(Graphics2D g) {
		g.setBackground(Color.BLACK);
		g.clearRect(0, 0, window.getWidth(), window.getHeight());
		g.setColor(Color.RED);
		g.drawLine(window.getWidth() / 2, window.getHeight() / 2, window.getWidth() / 2, window.getHeight() / 2);
	}

	@Override
	protected void update(float interval) {
		if (exit.isPressed()) {
            stop();
            return;
        }
	}

	@Override
	protected void close() {
	}
}
