package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.graphics.Framebuffer;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;
import org.jomaveger.tiger.datastructures.IList;

public interface IGameObject extends IDisposable {

    boolean isEnabled();

    boolean isEnabledUndefined();

    void setEnabled(boolean enabled);

    void setName(String name);

    String getName();

    TransformData getTransform();

    IEngine getEngine();

    IScene getScene();

    boolean isSceneStarted();

    void setScene(IScene scene);

    IGameObject getParent();

    boolean hasParent();

    void setParent(IGameObject gameObject);

    IList<IGameObject> getChildren();

    boolean hasChildren();

    int getChildCount(boolean recursive);

    void addChild(IGameObject gameObject);

    IGameObject getChild(int i);

    void removeChild(IGameObject gameObject);

    IGameObject removeChild(int i);

    IList<IComponent> getComponents();

    boolean hasComponents();

    int getComponentCount();

    void addComponent(IComponent component);

    IComponent getComponent(int i);

    <T extends IComponent> T getComponent(Class<T> componentType);

    void removeComponent(IComponent component);

    IComponent removeComponent(int i);

    void create();

    void start();
    
    void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager);

    void update(float interval);

    void render(Framebuffer framebuffer);

    void renderStart(Framebuffer framebuffer);

    void renderEnd(Framebuffer framebuffer);

    void onEnable();

    void onDisable();
}
