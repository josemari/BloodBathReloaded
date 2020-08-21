package org.jomaveger.tiger.math;

import java.util.Objects;

import org.jomaveger.tge.math.Vector;

public final class Plane {
    
    public Vector normal;
    public Vector point0;
    
    public Plane() {
        this.normal = new Vector(1.0f, 0.0f, 0.0f);
        this.point0 = new Vector();
    }
    
    public Plane(Vector normal, Vector point0) {
        this.normal = normal;
        this.point0 = point0;
    }
    
    public Plane(Plane plane) {
        this.normal = plane.normal;
        this.point0 = plane.point0;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.normal);
        hash = 73 * hash + Objects.hashCode(this.point0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Plane other = (Plane) obj;
        if (!Objects.equals(this.normal, other.normal)) {
            return false;
        }
        if (!Objects.equals(this.point0, other.point0)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Plane{" + "n=" + normal + ", p0=" + point0 + "}";
    }
    
    public boolean pointOnPlane(Vector point) {
        return (Float.compare(this.testPoint(point), 0.0f) == 0) ? true : false;
    }

    public boolean pointBehindThePlane(Vector point) {
        return this.testPoint(point) < 0.0f;
    }
    
    public boolean pointInFrontOfThePlane(Vector point) {
        return this.testPoint(point) > 0.0f;
    }
    
    private float testPoint(Vector point) {
    	return normal.x * (point.x - point0.x)
    			+ normal.y * (point.y - point0.y)
    			+ normal.z * (point.z - point0.z);
    }
    
        
//    public Vector rayIntersection(Vector rayOrigin, Vector rayDirection) {
//        float dotProduct = this.normal.dotProduct(rayDirection);
//        if (dotProduct == 0.0f) {
//            return rayOrigin;   // ray is parallel to plane and will never intersect it
//        }
//        
//        float distanceToPlane = this.distanceToPlane(rayOrigin);
//        distanceToPlane /= dotProduct;
//        Vector term = rayDirection.multiply(distanceToPlane);
//        return rayOrigin.subtract(term);
//    }
}
