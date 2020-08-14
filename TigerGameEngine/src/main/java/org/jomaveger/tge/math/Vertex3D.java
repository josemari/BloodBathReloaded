package org.jomaveger.tge.math;

public class Vertex3D {

	private Point3D p;
	
	public Vertex3D() {
		this.p = new Point3D();
	}
	
	public Vertex3D(float x, float y, float z) {
        this.p = new Point3D(x, y, z);
    }
    
    public Vertex3D(Vertex3D v) {
        this(v.getX(), v.getY(), v.getZ());
    }

	public float getX() {
		return p.getX();
	}

	public void setX(float x) {
		this.p.setX(x);
	}

	public float getY() {
		return p.getY();
	}

	public void setY(float y) {
		this.p.setY(y);
	}

	public float getZ() {
		return p.getZ();
	}

	public void setZ(float z) {
		this.p.setZ(z);
	}
}
