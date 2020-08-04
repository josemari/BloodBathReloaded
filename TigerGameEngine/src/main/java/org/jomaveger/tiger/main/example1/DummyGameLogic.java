package org.jomaveger.tiger.main.example1;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jomaveger.tge.architecture.IGameLogic;
import org.jomaveger.tge.graphics.Window;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;

public class DummyGameLogic implements IGameLogic {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Window window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void input(Window window, KeyInputManager keyInput, MouseInputManager mouseInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float interval, KeyInputManager keyInput, MouseInputManager mouseInput, Window window) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Window window) {
		Graphics2D g = window.getGraphics();
		g.setColor(Color.BLUE);
		g.drawLine(10, 20, 10, 20);
		g.drawLine(50, 60, 50, 60);
		g.dispose();
	}

	
}
