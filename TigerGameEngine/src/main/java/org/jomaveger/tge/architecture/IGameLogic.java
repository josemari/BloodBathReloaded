package org.jomaveger.tge.architecture;

import org.jomaveger.tge.graphics.Window;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IGameLogic extends IDisposable {

	void init(Window window);
    
    void input(Window window, KeyInputManager keyInput, MouseInputManager mouseInput);

    void update(float interval, KeyInputManager keyInput, MouseInputManager mouseInput, Window window);
    
    void render(Window window);
}
