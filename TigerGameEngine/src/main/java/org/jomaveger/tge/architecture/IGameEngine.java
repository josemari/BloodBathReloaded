package org.jomaveger.tge.architecture;

import org.jomaveger.tge.util.lang.IDisposable;
import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;

public interface IGameEngine extends Runnable, IDisposable {
	
	ScreenManager getWindow();
	
	KeyInputManager getKeyInputManager();
	
	MouseInputManager getMouseInputManager();
	
	IGameLogic getGameLogic();
	
	void setWindowTitle(String title);
	
	void gameLoop();
	
	void start();
}
