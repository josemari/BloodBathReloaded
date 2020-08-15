package org.jomaveger.tiger.architecture;

import org.jomaveger.tge.math.Vector;

public class TransformData {
	
	private final IGameObject owner;
	
	private Vector position;
	
	private float scale;
	
	private Vector rotation;

	public TransformData(IGameObject gameObject) {
		this.owner = gameObject;
		this.position = new Vector();
		this.rotation = new Vector();
        this.scale = 1;
	}

	public IGameObject getOwner() {
		return this.owner;
	}
	
	public Vector getPosition() {
        return this.position;
    }
	
	public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }
	
	public float getScale() {
        return this.scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotationX, float rotationY, float rotationZ) {
        this.rotation.x = rotationX;
        this.rotation.y = rotationY;
        this.rotation.z = rotationZ;
    }
}
