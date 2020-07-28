package org.jomaveger.tiger.architecture;

import org.jomaveger.tiger.graphics.Window;
import org.jomaveger.tiger.input.KeyInputManager;
import org.jomaveger.tiger.input.MouseInputManager;
import org.jomaveger.tiger.util.lang.IDisposable;

public interface IEngine extends Runnable, IDisposable {

	SceneManager getSceneManager();
	
	IRenderer getRenderer();
	
	Window getWindow();
	
	KeyInputManager getKeyInputManager();
	
	MouseInputManager getMouseInputManager();
	
	boolean hasRenderer();
	
	void setWindowTitle(String title);
	
	void init();
	
	void gameLoop();
	
	void update(float interval);
}
