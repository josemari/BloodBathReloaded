package org.jomaveger.tge.main.example1;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jomaveger.tge.architecture.IGameLogic;
import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;

public class DummyGameLogic implements IGameLogic {

	@Override
	public void dispose() {
	}

	@Override
	public void init(ScreenManager window) {
	}

	@Override
	public void input(ScreenManager window, KeyInputManager keyInput, MouseInputManager mouseInput) {
	}

	@Override
	public void update(float interval, KeyInputManager keyInput, MouseInputManager mouseInput, ScreenManager window) {
	}

	@Override
	public void render(ScreenManager window) {
		Graphics2D g = window.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(window.getWidth() / 2, window.getHeight() / 2, window.getWidth() / 2, window.getHeight() / 2);
	}
}
