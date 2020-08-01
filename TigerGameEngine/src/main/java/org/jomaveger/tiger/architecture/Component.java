package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tiger.datastructures.IList;
import org.jomaveger.tiger.util.enums.EnabledState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Component implements IComponent {
	
	protected static final Logger LOG = LoggerFactory.getLogger(Component.class);
	
	private EnabledState enabled = EnabledState.UNDEFINED;
	private IGameObject owner = null;

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
	public void setOwner(IGameObject gameObject) {
		this.owner = gameObject;
	}

	@Override
	public IGameObject getOwner() {
		return this.owner;
	}

	@Override
	public void addOwnerChild(IGameObject gameObject) {
		if (getOwner() == null) {
			LOG.error("Unable to add " + gameObject + " child to " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to add " + gameObject + " child to " + this + " owner, owner undefined.");
		}
		
		getOwner().addChild(gameObject);
	}

	@Override
	public void removeOwnerChild(IGameObject gameObject) {
		if (getOwner() == null) {
			LOG.error("Unable to remove " + gameObject + " child from " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to remove " + gameObject + " child from " + this + " owner, owner undefined.");
		}
		
		getOwner().removeChild(gameObject);
	}

	@Override
	public IGameObject removeOwnerChild(int i) {
		if (getOwner() == null) {
			LOG.error("Unable to remove child from " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to remove child from " + this + " owner, owner undefined.");
		}
		
		return getOwner().removeChild(i);
	}

	@Override
	public void addComponent(IComponent component) {
		if (getOwner() == null) {
			LOG.error("Unable to add " + component + " to " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to add " + component + " to " + this + " owner, owner undefined.");
		}
		
		getOwner().addComponent(component);
	}

	@Override
	public void removeComponent(IComponent component) {
		if (getOwner() == null) {
			LOG.error("Unable to remove " + component + " from " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to remove " + component + " from " + this + " owner, owner undefined.");
		}
		
		getOwner().removeComponent(component);
	}

	@Override
	public IComponent removeComponent(int i) {
		if (getOwner() == null) {
			LOG.error("Unable to remove component from " + this + " owner, owner undefined.");
			throw new RuntimeException("Unable to remove component from " + this + " owner, owner undefined.");
		}
		
		return getOwner().removeComponent(i);
	}

	@Override
	public IEngine getEngine() {
		return getOwner().getEngine();
	}

	@Override
	public IScene getScene() {
		return getOwner().getScene();
	}

	@Override
	public boolean isSceneStarted() {
		if (getScene() == null)
			return false;
		
		return getScene().isStarted();
	}

	@Override
	public TransformData getTransform() {
		return this.owner.getTransform();
	}

	@Override
	public IList<IComponent> getComponents() {
		return getOwner().getComponents();
	}

	@Override
	public boolean hasComponents() {
		return getOwner().hasComponents();
	}

	@Override
	public int getComponentCount() {
		return getOwner().getComponentCount();
	}

	@Override
	public IComponent getComponent(int i) {
		return getOwner().getComponent(i);
	}

	@Override
	public <T extends IComponent> T getComponent(Class<T> componentType) {
		return getOwner().getComponent(componentType);
	}

	@Override
	public void create() {
	}

	@Override
	public void start() {
		if (isEnabledUndefined())
			setEnabled(true);
	}
	
	@Override
	public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {		
	}

	@Override
	public void update(float interval) {
	}

	@Override
	public void dispose() {
		setEnabled(false);
		
		if (getOwner() == null)
			return;
		
		getOwner().removeComponent(this);
		
		this.owner = null;
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public String toString() {
		return "Component[" + getClass().getSimpleName() + "]"; 
	}
}
