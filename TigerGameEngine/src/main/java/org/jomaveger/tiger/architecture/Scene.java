package org.jomaveger.tiger.architecture;

import org.jomaveger.tiger.datastructures.IList;
import org.jomaveger.tiger.datastructures.LinkedList;
import org.jomaveger.tiger.graphics.Framebuffer;
import org.jomaveger.tiger.input.KeyInputManager;
import org.jomaveger.tiger.input.MouseInputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scene implements IScene {

    private static final Logger LOG = LoggerFactory.getLogger(Scene.class);

    private IEngine engine = null;
    private ILightManager lightManager = null;
    private IPhysicsEngine physicsEngine = null;
    private IList<IGameObject> gameObjects = new LinkedList<>();
    private IList<IGameObject> gameObjectsRemoveQueue = new LinkedList<>();
    private Framebuffer framebuffer;
    private boolean started;

    public Scene() {
    }

    @Override
    public void setEngine(IEngine engine) {
        this.engine = engine;
    }

    @Override
    public IEngine getEngine() {
        return this.engine;
    }

    @Override
    public ILightManager getLightManager() {
        return this.lightManager;
    }

    @Override
    public void setLightManager(ILightManager lightManager) {
        this.lightManager = lightManager;
    }

    @Override
    public IPhysicsEngine getPhysicsEngine() {
        return this.physicsEngine;
    }

    @Override
    public void setPhysicsEngine(IPhysicsEngine physicsEngine) {
        this.physicsEngine = physicsEngine;
    }

    @Override
    public void start() {
        LOG.info("Starting scene...");

        if (isStarted()) {
            return;
        }

        for (IGameObject gameObject : this.gameObjects) {
            gameObject.start();
        }

        this.started = true;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void load() {
    	if (getPhysicsEngine() != null)
    		getPhysicsEngine().setUp();
    }
    
    @Override
	public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {
    	for (int i = 0, size = this.gameObjects.size(); i < size; i++) {
            if (this.gameObjects.get(i).isEnabled()) {
                this.gameObjects.get(i).input(keyInputManager, mouseInputManager);
            }
        }
	}

    @Override
    public void update(float interval) {
        for (int i = 0, size = this.gameObjects.size(); i < size; i++) {
            if (this.gameObjects.get(i).isEnabled()) {
                this.gameObjects.get(i).update(interval);
            }
        }

        if (getPhysicsEngine() != null)
        	getPhysicsEngine().update(interval);

        for (int i = 0, size = this.gameObjectsRemoveQueue.size(); i < size; i++) {
            this.gameObjectsRemoveQueue.get(i).setParent(null);

            this.gameObjects.remove(this.gameObjectsRemoveQueue.get(i));
        }

        this.gameObjectsRemoveQueue.clear();
    }

    @Override
    public void render() {
        for (int i = 0, size = this.gameObjects.size(); i < size; i++) {
            if (this.gameObjects.get(i).isEnabled()) {
                this.gameObjects.get(i).render(framebuffer);
            }
        }
    }

    @Override
    public void dispose() {
        for (int i = 0, size = this.gameObjects.size(); i < size; i++) {
            this.gameObjects.get(i).dispose();
        }

        LOG.info(this + " is queued to be unloaded.");

        this.engine = null;
        this.lightManager = null;
        this.physicsEngine = null;
        this.gameObjects.clear();
        this.gameObjects = null;
        this.gameObjectsRemoveQueue.clear();
        this.gameObjectsRemoveQueue = null;
        this.started = false;
    }

    @Override
    public IList<IGameObject> getGameObjects() {
        return this.gameObjects;
    }

    @Override
    public int getGameObjectCount(boolean recursive) {
        if (!recursive) {
            return this.gameObjects.size();
        }

        int count = 0;

        for (int i = 0, size = this.gameObjects.size(); i < size; i++) {
            count = count + this.gameObjects.get(i).getChildCount(true);
        }

        return count;
    }

    @Override
    public int getTotalGameObjectCount() {
        return getGameObjectCount(true);
    }

    @Override
    public boolean hasGameObjects() {
        return getGameObjectCount(false) > 0;
    }

    @Override
    public void addGameObject(IGameObject gameObject) {
        gameObject.setScene(this);

        this.gameObjects.addLast(gameObject);

        gameObject.create();

        if (this.isStarted()) {
            gameObject.start();
        }
    }

    @Override
    public IGameObject getGameObject(int i) {
        return this.gameObjects.get(i);
    }

    @Override
    public void removeGameObject(IGameObject gameObject) {
        this.gameObjectsRemoveQueue.addLast(gameObject);
    }

    @Override
    public IGameObject removeGameObject(int i) {
        IGameObject gameObject = getGameObject(i);

        this.removeGameObject(gameObject);

        return gameObject;
    }

    @Override
    public String toString() {
        return "Scene[" + getClass().getSimpleName() + "]";
    }

	@Override
	public void setFramebuffer(Framebuffer framebuffer) {
		this.framebuffer = framebuffer;
	}

	@Override
	public Framebuffer getFramebuffer() {
		return this.framebuffer;
	}
}
