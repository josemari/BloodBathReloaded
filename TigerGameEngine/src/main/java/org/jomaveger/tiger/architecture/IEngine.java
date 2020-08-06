package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IEngine extends Runnable, IDisposable {

	SceneManager getSceneManager();
	
	IRenderer getRenderer();
	
	ScreenManager getWindow();
	
	KeyInputManager getKeyInputManager();
	
	MouseInputManager getMouseInputManager();
	
	boolean hasRenderer();
	
	void setWindowTitle(String title);
	
	void init();
	
	void gameLoop();
	
	void update(float interval);
}
