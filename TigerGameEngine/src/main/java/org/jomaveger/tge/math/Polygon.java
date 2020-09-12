package org.jomaveger.tge.math;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.graphics.ViewWindow;

public class Polygon {

	private List<Vector> vertexList;
	private int numVertices;
	private Color color;
	private Vector normal;

	public Polygon() {
		this.vertexList = new ArrayList<>();
		this.numVertices = 0;
		this.color = null;
		this.normal = new Vector();
	}
	
	public Polygon(List<Vector> vertexList) {
		this.vertexList = vertexList;
		this.numVertices = this.vertexList.size();
		this.color = null;
		calcNormal();
	}
	
	public Vector calcNormal() {
		if (normal == null) {
            normal = new Vector();
        }
		Vector temp1 = new Vector();
		Vector temp2 = new Vector();
		Vector temp3;
        temp1.setTo(this.vertexList.get(2));
        temp1.minus(this.vertexList.get(1));
        temp2.setTo(this.vertexList.get(0));
        temp2.minus(this.vertexList.get(1));
        temp3 = temp1.crossProduct(temp2);
        normal = temp3.normalize();
        return normal;
		
	}

	public Polygon(Polygon p) {
		this.vertexList = new ArrayList<>();
		this.numVertices = p.vertexList.size();
		for (int i=0; i<numVertices; i++) {
            this.vertexList.add(new Vector(p.getVertex(i)));
        }
		this.color = p.color;
		this.normal = new Vector(p.normal);
	}
	
	public void setTo(Polygon p) {
		this.vertexList.clear();
		this.numVertices = p.vertexList.size();
		for (int i=0; i<numVertices; i++) {
            this.vertexList.add(new Vector(p.getVertex(i)));
        }
		this.color = p.color;
		this.normal.setTo(p.normal);
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
	
	public Vector getNormal() {
        return normal;
    }
	
	public void setNormal(Vector n) {
        if (normal == null) {
            normal = new Vector(n);
        }
        else {
            normal.setTo(n);
        }
    }
	
	public boolean isFacing(Vector u) {
		Vector temp1 = new Vector(u);
        temp1.minus(this.vertexList.get(0));
        return (normal.dotProduct(temp1) >= 0);
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
        normal.addRotation(tform);
    }

    public void subtractRotation(Transform tform) {
        for (Vector v : vertexList) {
           v.subtractRotation(tform);
        }
        normal.subtractRotation(tform);
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
     
     public boolean clip(float clipZ) {
         boolean isCompletelyHidden = true;

         for (int i=0; i<numVertices; i++) {
             int next = (i + 1) % numVertices;
             Vector v1 = this.vertexList.get(i);
             Vector v2 = this.vertexList.get(next);
             if (v1.z < clipZ) {
                 isCompletelyHidden = false;
             }
             
             if (v1.z > v2.z) {
                 Vector temp = v1;
                 v1 = v2;
                 v2 = temp;
             }
             if (v1.z < clipZ && v2.z > clipZ) {
                 float scale = (clipZ-v1.z) / (v2.z - v1.z);
                 insertVertex(next,
                     v1.x + scale * (v2.x - v1.x) ,
                     v1.y + scale * (v2.y - v1.y),
                     clipZ);
                 i++;
             }
         }

         if (isCompletelyHidden) {
             return false;
         }

         for (int i=numVertices-1; i>=0; i--) {
             if (this.vertexList.get(i).z > clipZ) {
                 deleteVertex(i);
             }
         }

         return (numVertices >= 3);
     }
     
	public void insertVertex(int index, float x, float y, float z) {
		this.insertVertex(index, new Vector(x, y, z));
	}

	public void deleteVertex(int index) {
		this.vertexList.remove(index);
		numVertices--;
	}
}
