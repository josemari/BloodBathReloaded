package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.datastructures.IList;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IScene extends IDisposable {

	void setEngine(IEngine engine);
	
	IEngine getEngine();
	
	ILightManager getLightManager();
	
	void setLightManager(ILightManager lightManager);
	
	IPhysicsEngine getPhysicsEngine();
	
	void setPhysicsEngine(IPhysicsEngine physicsEngine);

	void start();
	
	boolean isStarted();
	
	void load();
	
	void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager);

	void update(float interval);

	void render();
	
	IList<IGameObject> getGameObjects();

	int getGameObjectCount(boolean recursive);
	
	int getTotalGameObjectCount();
	
	boolean hasGameObjects();
	
	void addGameObject(IGameObject gameObject);
	
	IGameObject getGameObject(int i);
	
	void removeGameObject(IGameObject gameObject);
	
	IGameObject removeGameObject(int i);

	void setFramebuffer(Framebuffer framebuffer);
	
	Framebuffer getFramebuffer();
}
