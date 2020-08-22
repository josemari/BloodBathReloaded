package org.jomaveger.tge.math;

import java.util.Objects;

public final class Plane {
    
    public Vector normal;
    public Vector point0;
    private float d;
    
    public Plane() {
        this.normal = new Vector(1.0f, 0.0f, 0.0f);
        this.point0 = new Vector();
        this.d = computeD();
    }
    
    private float computeD() {
		float value = - this.normal.x * this.point0.x 
        		- this.normal.y * this.point0.y 
        		- this.normal.z * this.point0.z;
		return (Float.compare(-0.0f, value) == 0) ? 0.0f : value;
	}

	public Plane(Vector normal, Vector point0) {
        this.normal = normal;
        this.point0 = point0;
        this.d = computeD();
    }
    
    public Plane(Plane plane) {
        this.normal = plane.normal;
        this.point0 = plane.point0;
        this.d = computeD();
    }
    
    public Plane normalize() {
    	this.normal = this.normal.normalize();
    	this.d = computeD();
    	return this;
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
    
    public float distancePointPlane(Vector point) {
    	float denom = MathUtil.sqrt(normal.x * normal.x 
    							+ normal.y * normal.y 
    							+ normal.z * normal.z);
        return (normal.x * point.x 
        		+ normal.y * point.y 
        		+ normal.z * point.z 
        		+ d) / denom;
    }
    
        
    public IntersectionResolution lineIntersection(Vector linePoint, Vector lineDirection) {
    	IntersectionResolution result = new IntersectionResolution();
        float dot = this.normal.dotProduct(lineDirection);
        
        if (Float.compare(MathUtil.abs(dot), 0.0f) == 0) {
        	if (pointOnPlane(linePoint)) {
        		result.condition = IntersectionCondition.LINE_INTERSECT_EVERYWHERE;
        	} else {
        		result.condition = IntersectionCondition.LINE_NO_INTERSECT;
        	}
        }
        
        float t = -(this.normal.dotProduct(linePoint) + d) / dot;
        
        Vector intersectionPoint = new Vector();
        intersectionPoint.x = linePoint.x + lineDirection.x * t;
        intersectionPoint.y = linePoint.y + lineDirection.y * t;
        intersectionPoint.z = linePoint.z + lineDirection.z * t;
        result.intersectionPoint = intersectionPoint;

        if (t >= 0.0 && t <= 1.0) {
        	result.condition = IntersectionCondition.LINE_INTERSECT_IN_SEGMENT;
        } else {
        	result.condition = IntersectionCondition.LINE_INTERSECT_OUT_SEGMENT;
        }
        
        return result;
    }
}
