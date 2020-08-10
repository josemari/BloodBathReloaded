package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.datastructures.IList;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.util.lang.IDisposable;

public interface IComponent extends IDisposable {
	
	boolean isEnabled();
	
	boolean isEnabledUndefined();
	
	void setEnabled(boolean enabled);

	void setOwner(IGameObject gameObject);
	
	IGameObject getOwner();
	
	void addOwnerChild(IGameObject gameObject);
	
	void removeOwnerChild(IGameObject gameObject);
	
	IGameObject removeOwnerChild(int i);
	
	void addComponent(IComponent component);
	
	void removeComponent(IComponent component);
	
	IComponent removeComponent(int i);
	
	IEngine getEngine();
	
	IScene getScene();
	
	boolean isSceneStarted();
	
	TransformData getTransform();
	
	IList<IComponent> getComponents();
	
	boolean hasComponents();
	
	int getComponentCount();
	
	IComponent getComponent(int i);
	
	<T extends IComponent> T getComponent(Class<T> componentType);
	
	void create();

	void start();
	
	void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager);

	void update(float interval);

	void onEnable();
	
	void onDisable();
}
