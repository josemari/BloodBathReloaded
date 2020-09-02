package org.jomaveger.tge.graphics;

import org.jomaveger.tge.util.lang.IDisposable;
import org.jomaveger.tiger.architecture.Framebuffer;

public interface IRenderable extends IDisposable {

    void render(Framebuffer framebuffer);

}
