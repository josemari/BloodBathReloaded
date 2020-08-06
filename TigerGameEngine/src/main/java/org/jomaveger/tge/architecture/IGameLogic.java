package org.jomaveger.tge.architecture;

import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IGameLogic extends IDisposable {

	void init(ScreenManager window);
    
    void input(ScreenManager window, KeyInputManager keyInput, MouseInputManager mouseInput);

    void update(float interval, KeyInputManager keyInput, MouseInputManager mouseInput, ScreenManager window);
    
    void render(ScreenManager window);
}
