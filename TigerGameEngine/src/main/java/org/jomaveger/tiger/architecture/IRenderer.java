package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IRenderer extends IDisposable {

    IEngine getEngine();

    void init();
    
    void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager);

    void update(float interval);

    void render();
}
