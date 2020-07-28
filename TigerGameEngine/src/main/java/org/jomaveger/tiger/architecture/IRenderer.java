package org.jomaveger.tiger.architecture;

import org.jomaveger.tiger.input.KeyInputManager;
import org.jomaveger.tiger.input.MouseInputManager;
import org.jomaveger.tiger.util.lang.IDisposable;

public interface IRenderer extends IDisposable {

    IEngine getEngine();

    void init();
    
    void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager);

    void update(float interval);

    void render();
}
