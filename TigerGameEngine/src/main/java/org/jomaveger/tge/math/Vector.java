package org.jomaveger.tge.math;

public final class Vector {
    
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
    
    public void set(Vector vec) {
        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
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
    
    public Vector add(Vector vec) {
        return new Vector(this.x + vec.x, this.y + vec.y, this.z + vec.z);
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
    
    public float distance(Vector vec) {
        Vector res = this.subtract(vec);
    	
    	float distance = res.length();

    	return distance;
    }
    
    //R = E - 2n(E.n)
    public Vector reflection(Vector normal) {
        float dotProduct = this.dotProduct(normal); // (E.n)
        dotProduct *= 2.0; // 2(E.n)
        Vector temp = normal.multiply(dotProduct); // 2n(E.n)
        return this.subtract(temp); // E - 2n(E.n)
    }
    
    //Rodrigues' Rotation Formula
    // resul = v cos + (k x v) sin + k(k.v)(1 - cos)
    public Vector rotate(float angle, Vector normal) {
        float cosine = MathUtil.cos(angle);
        float sine = MathUtil.sin(angle);
        
        Vector term1 = this.multiply(cosine);
        Vector term2 = normal.crossProduct(this).multiply(sine);
        float dotProduct = this.dotProduct(normal);
        dotProduct *= (1 - cosine);
        Vector term3 = normal.multiply(dotProduct);
        
        return term1.add(term2).add(term3);
    }
    
    //rotate a vector p using a unit quaternion q.
    public Vector rotateByAxisAndAngle(float angle, Vector axis) {
        Quaternion p = new Quaternion(this.x, this.y, this.z, 0.0f);

        axis.normalize();

        Quaternion q = new Quaternion(axis.x, axis.y, axis.z, angle);
        q.convertToUnitNormQuaternion();

        Quaternion qInverse = q.inverse();

        Quaternion rotatedVector = q.multiply(p).multiply(qInverse);
		
        return new Vector(rotatedVector.x, rotatedVector.y, rotatedVector.z);
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

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
