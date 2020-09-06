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
		this.vertexList = new ArrayList<>();
		for (Vector v : vertexList) {
			this.vertexList.add(v);	
		}
		this.numVertices = this.vertexList.size();
		this.color = null;
	}
	
	public Polygon(Polygon p) {
		this.vertexList = new ArrayList<>();
		for (Vector v : p.vertexList) {
			this.vertexList.add(v);	
		}
		this.numVertices = this.vertexList.size();
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
	
	//angles in radians
    public void addTransform(Vector location, float angleX, float angleZ, float angleY) {
    	for (Vector v : this.vertexList) {
			v.addRotation(angleX, angleZ, angleY);	
		}
    	for (Vector v : this.vertexList) {
			v.set(v.translate(location.x, location.y, location.z));	
		}
    }
    
    //angles in radians
    public void substractTransform(Vector location, float angleX, float angleZ, float angleY) {
    	for (Vector v : this.vertexList) {
			v.set(v.translate(-location.x, -location.y, -location.z));	
		}
    	for (Vector v : this.vertexList) {
			v.substractRotation(angleX, angleZ, angleY);	
		}
    }
}
