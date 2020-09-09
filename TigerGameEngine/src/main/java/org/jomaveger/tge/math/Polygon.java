package org.jomaveger.tge.math;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.graphics.ViewWindow;

public class Polygon {

	private List<Vector> vertexList;
	private int numVertices;
	private Color color;

	public Polygon() {
		this.vertexList = new ArrayList<>();
		this.numVertices = 0;
		this.color = null;
	}
	
	public Polygon(List<Vector> vertexList) {
		this.vertexList = vertexList;
		this.numVertices = this.vertexList.size();
		this.color = null;
	}
	
	public Polygon(Polygon p) {
		this.vertexList = new ArrayList<>();
		this.numVertices = p.vertexList.size();
		for (int i=0; i<numVertices; i++) {
            this.vertexList.add(new Vector(p.getVertex(i)));
        }
		this.color = p.color;
	}
	
	public void setTo(Polygon p) {
		this.vertexList.clear();
		this.numVertices = p.vertexList.size();
		for (int i=0; i<numVertices; i++) {
            this.vertexList.add(new Vector(p.getVertex(i)));
        }
		this.color = p.color;
	}
	
	public void insertVertex(int index, Vector vertex) {
		this.vertexList.add(index, vertex);
		this.numVertices++;
	}
	
	public int getNumVertices() {
		return numVertices;
	}
	
	public Vector getVertex(int index) {
		return this.vertexList.get(index);
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setColor(Color c) {
		this.color = c;
	}
	
	public void project(ViewWindow view) {
		for (Vector v : this.vertexList) {
			view.project(v);	
		}
    }
	
	public void add(Transform tform) {
        addRotation(tform);
        add(tform.getLocation());
    }

    public void subtract(Transform tform) {
        subtract(tform.getLocation());
        subtractRotation(tform);
    }

    public void addRotation(Transform tform) {
        for (Vector v : vertexList) {
           v.addRotation(tform);
        }
    }

    public void subtractRotation(Transform tform) {
        for (Vector v : vertexList) {
           v.subtractRotation(tform);
        }
    }
    
    public void add(Vector u) {
        for (Vector v : vertexList) {
            v.sum(u);
        }
     }

     public void subtract(Vector u) {
        for (Vector v : vertexList) {
            v.minus(u);
        }
     }
}
