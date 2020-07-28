package org.jomaveger.tiger.graphics;

import org.jomaveger.tiger.util.lang.IDisposable;

public interface IRenderable extends IDisposable {

    void render(Framebuffer framebuffer);

}
