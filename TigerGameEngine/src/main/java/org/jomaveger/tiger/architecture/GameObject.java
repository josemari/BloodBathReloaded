package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.datastructures.IList;
import org.jomaveger.tge.datastructures.LinkedList;
import org.jomaveger.tge.graphics.Framebuffer;
import org.jomaveger.tge.graphics.IRenderable;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tiger.util.enums.EnabledState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameObject implements IGameObject {

    protected static final Logger LOG = LoggerFactory.getLogger(GameObject.class);

    private EnabledState enabled = EnabledState.UNDEFINED;
    private IScene scene = null;
    private String name = null;
    private TransformData transform = new TransformData(this);
    private IGameObject parent = null;
    private IList<IGameObject> children = new LinkedList<>();
    private IList<IComponent> components = new LinkedList<>();
    private IList<IGameObject> childrenRemoveQueue = new LinkedList<>();
    private IList<IComponent> componentsRemoveQueue = new LinkedList<>();

    public GameObject() {
    }

    @Override
    public boolean isEnabled() {
        return this.enabled.equals(EnabledState.ENABLED);
    }

    @Override
    public boolean isEnabledUndefined() {
        return this.enabled.equals(EnabledState.UNDEFINED);
    }

    @Override
    public void setEnabled(boolean enabled) {
        boolean change = isEnabledUndefined() || enabled != isEnabled();

        this.enabled = enabled ? EnabledState.ENABLED : EnabledState.DISABLED;

        if (change) {
            if (enabled) {
                onEnable();
            } else {
                onDisable();
            }
        }
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public TransformData getTransform() {
        return this.transform;
    }

    @Override
    public IEngine getEngine() {
        return this.scene.getEngine();
    }

    @Override
    public IScene getScene() {
        return this.scene;
    }

    @Override
    public boolean isSceneStarted() {
        if (this.scene == null) {
            return false;
        }

        return this.scene.isStarted();
    }

    @Override
    public void setScene(IScene scene) {
        this.scene = scene;

        for (IGameObject child : this.getChildren()) {
            child.setScene(this.scene);
        }
    }

    @Override
    public IGameObject getParent() {
        return this.parent;
    }

    @Override
    public boolean hasParent() {
        return getParent() != null;
    }

    @Override
    public void setParent(IGameObject gameObject) {
        this.parent = gameObject;
    }

    @Override
    public IList<IGameObject> getChildren() {
        return this.children;
    }

    @Override
    public boolean hasChildren() {
        return getChildCount(false) > 0;
    }

    @Override
    public int getChildCount(boolean recursive) {
        if (!recursive) {
            return this.children.size();
        }

        int count = 0;

        for (int i = 0, size = this.children.size(); i < size; i++) {
            count = count + this.children.get(i).getChildCount(true);
        }

        return count;
    }

    @Override
    public void addChild(IGameObject gameObject) {
        gameObject.setParent(this);

        gameObject.setScene(getScene());

        this.children.addLast(gameObject);

        if (getScene() != null) {
            gameObject.create();
        }

        if (isSceneStarted()) {
            gameObject.start();
        }
    }

    @Override
    public IGameObject getChild(int i) {
        return this.children.get(i);
    }

    @Override
    public void removeChild(IGameObject gameObject) {
        this.childrenRemoveQueue.addLast(gameObject);
    }

    @Override
    public IGameObject removeChild(int i) {
        IGameObject child = this.children.get(i);

        this.removeChild(child);

        return child;
    }

    @Override
    public IList<IComponent> getComponents() {
        return this.components;
    }

    @Override
    public boolean hasComponents() {
        return getComponentCount() > 0;
    }

    @Override
    public int getComponentCount() {
        return this.components.size();
    }

    @Override
    public void addComponent(IComponent component) {
        this.components.addLast(component);

        component.setOwner(this);

        if (getScene() != null) {
            component.create();
        }

        if (isSceneStarted()) {
            component.start();
        }
    }

    @Override
    public IComponent getComponent(int i) {
        return this.components.get(i);
    }

    @Override
    public <T extends IComponent> T getComponent(Class<T> componentType) {
        for (int i = 0, size = this.components.size(); i < size; i++) {
            if (componentType.isAssignableFrom(this.components.get(i).getClass())) {
                return (T) this.components.get(i);
            }
        }

        return null;
    }

    @Override
    public void removeComponent(IComponent component) {
        this.componentsRemoveQueue.addLast(component);
    }

    @Override
    public IComponent removeComponent(int i) {
        IComponent component = this.components.get(i);

        this.removeComponent(component);

        return component;
    }

    @Override
    public void create() {
        for (int i = 0, size = this.children.size(); i < size; i++) {
            this.children.get(i).create();
        }

        for (int i = 0, size = this.components.size(); i < size; i++) {
            this.components.get(i).create();
        }
    }

    @Override
    public void start() {
        for (int i = 0, size = this.children.size(); i < size; i++) {
            this.children.get(i).start();
        }

        for (int i = 0, size = this.components.size(); i < size; i++) {
            this.components.get(i).start();
        }

        if (isEnabledUndefined()) {
            setEnabled(true);
        }
    }

    @Override
	public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {
    	for (int i = 0, size = this.components.size(); i < size; i++) {
            if (this.components.get(i).isEnabled()) {
                this.components.get(i).input(keyInputManager, mouseInputManager);
            }
        }

        for (int i = 0, size = this.children.size(); i < size; i++) {
            if (this.children.get(i).isEnabled()) {
                this.children.get(i).input(keyInputManager, mouseInputManager);
            }
        }
	}
    
    @Override
    public void update(float interval) {
        for (int i = 0, size = this.components.size(); i < size; i++) {
            if (this.components.get(i).isEnabled()) {
                this.components.get(i).update(interval);
            }
        }

        for (int i = 0, size = this.children.size(); i < size; i++) {
            if (this.children.get(i).isEnabled()) {
                this.children.get(i).update(interval);
            }
        }

        for (int i = 0, size = this.componentsRemoveQueue.size(); i < size; i++) {
            this.componentsRemoveQueue.get(i).setOwner(null);
            this.components.remove(this.componentsRemoveQueue.get(i));
        }

        this.componentsRemoveQueue.clear();

        for (int i = 0, size = this.childrenRemoveQueue.size(); i < size; i++) {
            this.children.remove(this.childrenRemoveQueue.get(i));
        }

        this.childrenRemoveQueue.clear();
    }

    @Override
    public void dispose() {
        setEnabled(false);

        for (int i = 0, size = this.components.size(); i < size; i++) {
            this.components.get(i).dispose();
        }

        for (int i = 0, size = this.children.size(); i < size; i++) {
            this.children.get(i).dispose();
        }

        if (hasParent()) {
            getParent().removeChild(this);
        } else {
            getScene().removeGameObject(this);
        }

        this.scene = null;
        this.name = null;
        this.transform = null;
        this.parent = null;
        this.children.clear();
        this.children = null;
        this.components.clear();
        this.components = null;
        this.childrenRemoveQueue.clear();
        this.childrenRemoveQueue = null;
        this.componentsRemoveQueue.clear();
        this.componentsRemoveQueue = null;
    }

    @Override
    public void render(Framebuffer framebuffer) {
        boolean drawing = false;

        for (int i = 0, size = this.components.size(); i < size; i++) {

            if (this.components.get(i) instanceof IRenderable) {

                if (!drawing) {
                    renderStart(framebuffer);
                    drawing = true;
                }

                if (this.components.get(i).isEnabled()) {
                    ((IRenderable) this.components.get(i)).render(framebuffer);
                }
            }
        }

        if (drawing) {
            renderEnd(framebuffer);
        }

        for (int i = 0, size = this.children.size(); i < size; i++) {
            if (this.children.get(i).isEnabled()) {
                this.children.get(i).render(framebuffer);
            }
        }
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void renderStart(Framebuffer framebuffer) {
    }

    @Override
    public void renderEnd(Framebuffer framebuffer) {
    }

    @Override
    public String toString() {
        return "GameObject[" + getName() + "]";
    }
}
