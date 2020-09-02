package org.jomaveger.tge.graphics;

import org.jomaveger.tge.math.Vector;

public class Vertex {

	private Vector vertex;
	
	public Vertex() {
		this.vertex = new Vector();
    }
    
    public Vertex(float x, float y, float z) {
        this.vertex = new Vector(x, y, z);
    }
    
    public Vector getPoint() {
    	return vertex;
    }
}
