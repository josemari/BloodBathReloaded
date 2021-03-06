package org.jomaveger.tge.math;

public class Vector {
    
    public float x;
    public float y;
    public float z;

    public Vector() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vector(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector(Vector vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public void setTo(Vector vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
    }
    
    public void setTo(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Float.floatToIntBits(this.x);
        hash = 59 * hash + Float.floatToIntBits(this.y);
        hash = 59 * hash + Float.floatToIntBits(this.z);
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
        final Vector other = (Vector) obj;
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
    public void sum(Vector vec) {
    	this.x += vec.x;
    	this.y += vec.y;
    	this.z += vec.z;
    }
    
    public Vector add(Vector vec) {
        return new Vector(this.x + vec.x, this.y + vec.y, this.z + vec.z);
    }
    
    public void minus(Vector vec) {
    	this.x -= vec.x;
    	this.y -= vec.y;
    	this.z -= vec.z;
    }
    
    public Vector subtract(Vector vec) {
        return new Vector(this.x - vec.x, this.y - vec.y, this.z - vec.z);
    }
    
    public Vector negate() {
        return new Vector(-this.x, -this.y, -this.z);
    }
    
    public Vector multiply(float s) {
        return new Vector(this.x * s, this.y * s, this.z * s);
    }
    
    public Vector divide(float s) {
        return new Vector(this.x / s, this.y / s, this.z / s);
    }
    
    public float dotProduct(Vector vec) {
        return this.x * vec.x + this.y * vec.y + this.z * vec.z;
    }
    
    public Vector crossProduct(Vector vec) {
        return new Vector(this.y * vec.z - this.z * vec.y, 
                            this.z * vec.x - this.x * vec.z, 
                            this.x * vec.y - this.y * vec.x);
    }
    
    public float length() {
        return MathUtil.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public float lengthSquared() {
    	return this.x * this.x + this.y * this.y + this.z * this.z;
    }
    
    public Vector normalize() {
        return this.divide(this.length());
    }
    
    public float angle(Vector vec) {
        float dotProduct = this.dotProduct(vec);
        float vectorsMagnitude = this.length() * vec.length();
        float angle = MathUtil.acos(dotProduct / vectorsMagnitude);
        if(angle == Float.NaN) {
            return 0.0f;
        }
        return angle;    
    }
    
    public float distance(Vector point) {
        Vector res = this.subtract(point);
    	
    	float distance = res.length();

    	return distance;
    }

    public float distanceSquared(Vector point) {
        Vector res = this.subtract(point);
    	
    	float distance = res.lengthSquared();

    	return distance;
    }

    public Vector perpendicular() {
    	return new Vector(0, -z, y);
    }
    
    public Vector translate(float dx, float dy, float dz) {
        Vector vec = new Vector();
        vec.x = this.x + dx;
        vec.y = this.y + dy;
        vec.z = this.z + dz;
        return vec;
    }
    
    public Vector scale(float sx, float sy, float sz) {
        Vector vec = new Vector();
        vec.x = this.x * sx;
        vec.y = this.y * sy;
        vec.z = this.z * sz;
        return vec;
    }
    
    //angle in radians
    public Vector rotateAroundAxisX(float angle) {
        Vector vec = new Vector();
        vec.x = this.x;
        vec.y = (this.y * MathUtil.cos(angle)) - (this.z * MathUtil.sin(angle));
        vec.z = (this.y * MathUtil.sin(angle)) + (this.z * MathUtil.cos(angle));
        return vec;
    }
    
    //angle in radians
    public Vector rotateAroundAxisY(float angle) {
        Vector vec = new Vector();
        vec.x = (this.x * MathUtil.cos(angle)) + (this.z * MathUtil.sin(angle));
        vec.y = y;
        vec.z = -(this.x * MathUtil.sin(angle)) + (this.z * MathUtil.cos(angle));
        return vec;
    }
    
    //angle in radians
    public Vector rotateAroundAxisZ(float angle) {
        Vector vec = new Vector();
        vec.x = (this.x * MathUtil.cos(angle)) - (this.y * MathUtil.sin(angle));
        vec.y = (this.x * MathUtil.sin(angle)) + (this.y * MathUtil.cos(angle));
        vec.z = this.z;
        return vec;
    }
    
    //rotate a vector p using a unit quaternion q.
    //angle in radians, axis is unit vector
    public Vector rotateByAxisAndAngle(float angle, Vector axis) {
        Quaternion p = new Quaternion(0.0f, this.x, this.y, this.z);

        Quaternion q = new Quaternion();
        q.convertToAxisAngleQuaternion(angle, axis);

        Quaternion qInverse = q.conjugate();

        Quaternion pRotated = q.multiply(p).multiply(qInverse);
		
        return new Vector(pRotated.x, pRotated.y, pRotated.z);
    }

    //Rodriguez' Rotation Formula, angle in radians, axis is unit vector
    //p' = (p * cosθ) + (n x p) * sinθ + n * (n.p) * (1 - cosθ)
    public Vector rotateByAxisAndAngleRodrigues(float angle, Vector axis) {
        float cosine = MathUtil.cos(angle);
        float sine = MathUtil.sin(angle);
        
        Vector term1 = this.multiply(cosine);
        
        Vector term2 = axis.crossProduct(this).multiply(sine);
        
        float dotProduct = this.dotProduct(axis);
        dotProduct *= (1 - cosine);
        Vector term3 = axis.multiply(dotProduct);
        
        return term1.add(term2).add(term3);
    }
    
    //angles in radians
    public Vector rotateByEulerAngles(float thetaZ, float thetaY, float thetaX) {
    	Quaternion p = new Quaternion(0.0f, this.x, this.y, this.z);

        Quaternion q = new Quaternion();
        q.fromEulerAngles(thetaZ, thetaY, thetaX);

        Quaternion qInverse = q.conjugate();

        Quaternion pRotated = q.multiply(p).multiply(qInverse);
		
        return new Vector(pRotated.x, pRotated.y, pRotated.z);
    }
    
	public void rotateX(float cosAngle, float sinAngle) {
		float newY = y * cosAngle - z * sinAngle;
		float newZ = y * sinAngle + z * cosAngle;
		y = newY;
		z = newZ;
	}

	public void rotateY(float cosAngle, float sinAngle) {
		float newX = z * sinAngle + x * cosAngle;
		float newZ = z * cosAngle - x * sinAngle;
		x = newX;
		z = newZ;
	}

	public void rotateZ(float cosAngle, float sinAngle) {
		float newX = x * cosAngle - y * sinAngle;
		float newY = x * sinAngle + y * cosAngle;
		x = newX;
		y = newY;
	}
	
	public void add(Transform tform) {

		// rotate
		addRotation(tform);

		// translate
		sum(tform.getLocation());
	}

	public void subtract(Transform tform) {

		// translate
		minus(tform.getLocation());

		// rotate
		subtractRotation(tform);
	}
	
	public void addRotation(Transform tform) {
        rotateX(tform.getCosAngleX(), tform.getSinAngleX());
        rotateZ(tform.getCosAngleZ(), tform.getSinAngleZ());
        rotateY(tform.getCosAngleY(), tform.getSinAngleY());
    }

    public void subtractRotation(Transform tform) {
        rotateY(tform.getCosAngleY(), -tform.getSinAngleY());
        rotateZ(tform.getCosAngleZ(), -tform.getSinAngleZ());
        rotateX(tform.getCosAngleX(), -tform.getSinAngleX());
    }
}
