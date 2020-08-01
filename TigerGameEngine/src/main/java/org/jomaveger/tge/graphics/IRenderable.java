package org.jomaveger.tge.graphics;

import org.jomaveger.tge.util.lang.IDisposable;

public interface IRenderable extends IDisposable {

    void render(Framebuffer framebuffer);

}
