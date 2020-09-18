package org.jomaveger.tge.math;

public class Rectangle {

	private Vector origin;
    private Vector directionU;
    private Vector directionV;
    private Vector normal;
    private float width;
    private float height;

    public Rectangle() {
        origin = new Vector();
        directionU = new Vector(1,0,0);
        directionV = new Vector(0,1,0);
        width = 0.0f;
        height = 0.0f;
    }

    public Rectangle(Vector origin, Vector directionU, 
    				Vector directionV, float width, float height) {
    	
        this.origin = new Vector(origin);
        Vector temp1 = new Vector(directionU);
        this.directionU = new Vector(temp1.normalize());
        Vector temp2 = new Vector(directionV);
        this.directionV = new Vector(temp2.normalize());
        this.width = width;
        this.height = height;
    }

    public void setTo(Rectangle rect) {
        origin.setTo(rect.origin);
        directionU.setTo(rect.directionU);
        directionV.setTo(rect.directionV);
        width = rect.width;
        height = rect.height;
    }

    public Vector getOrigin() {
        return origin;
    }

    public Vector getDirectionU() {
        return directionU;
    }

    public Vector getDirectionV() {
        return directionV;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    protected Vector calcNormal() {
        if (normal == null) {
            normal = new Vector();
        }
        Vector temp = directionU.crossProduct(directionV);
        normal.setTo(temp.normalize());
        return normal;
    }

    public Vector getNormal() {
        if (normal == null) {
            calcNormal();
        }
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

    public void add(Vector u) {
        origin.sum(u);
    }

    public void subtract(Vector u) {
        origin.minus(u);
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
        origin.addRotation(tform);
        directionU.addRotation(tform);
        directionV.addRotation(tform);
    }

    public void subtractRotation(Transform tform) {
        origin.subtractRotation(tform);
        directionU.subtractRotation(tform);
        directionV.subtractRotation(tform);
    }
}
