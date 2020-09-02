package org.jomaveger.tge.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Face {

	private PolygonStates states;
	private PolygonAttributes attributes;
	private Color color;
	private List<Vertex> vertexList;
	private int numberOfVertex;
	
	public Face() {
		this.vertexList = new ArrayList<>();
		this.color = null;
		this.states = new PolygonStates();
		this.attributes = new PolygonAttributes();
		this.numberOfVertex = 0;
	}
	
	public PolygonStates getStates() {
		return states;
	}

	public void setStates(PolygonStates states) {
		this.states = states;
	}

	public PolygonAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(PolygonAttributes attributes) {
		this.attributes = attributes;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public int getNumberOfVertex() {
		return numberOfVertex;
	}
	
	public List<Vertex> getFace() {
		return vertexList;
	}
	
	public void addVertex(Vertex vertex) {
		this.vertexList.add(vertex);
	}
}
