package org.jomaveger.tge.graphics;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polygon {

	private PolygonStates states;
	private PolygonAttributes attributes;
	private Color color;
	private List<Vertex> vertexList;
	private List<Integer> indexList;
	private int numberOfVertex;

	public Polygon() {
		this.vertexList = new ArrayList<>();
		this.indexList = new ArrayList<>();
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
	
	public void setPolygon(List<Vertex> vertexList, List<Integer> indexList) {
		this.vertexList = vertexList;
		this.indexList = indexList;
		this.numberOfVertex = this.indexList.size();
	}
	
	public List<Vertex> getPolygon() {
		List<Vertex> polygon = new ArrayList<>();
		for (Iterator<Integer> iterator = indexList.iterator(); iterator.hasNext();) {
			Integer integer = iterator.next();
			polygon.add(this.vertexList.get(integer));
		}
		return polygon;
	}
}
