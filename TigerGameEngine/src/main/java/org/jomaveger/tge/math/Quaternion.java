package org.jomaveger.tge.math;

public final class Quaternion {
    
    public float w;
	public float x;
    public float y;
    public float z;
    
    public Quaternion() {
    	this.w = 1.0f;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }
    
    public Quaternion(float w, float x, float y, float z) {
    	this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Quaternion(final Quaternion quat) {
    	this.w = quat.w;
        this.x = quat.x;
        this.y = quat.y;
        this.z = quat.z;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Float.floatToIntBits(this.w);
        hash = 37 * hash + Float.floatToIntBits(this.x);
        hash = 37 * hash + Float.floatToIntBits(this.y);
        hash = 37 * hash + Float.floatToIntBits(this.z);
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
        final Quaternion other = (Quaternion) obj;
        if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
            return false;
        }
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
        return "Quaternion{" + "w=" + w + ", x=" + x + ", y=" + y + ", z=" + z + '}';
    }
    
    public Quaternion add(Quaternion quat) {
        return new Quaternion(this.w + quat.w, this.x + quat.x, this.y + quat.y, this.z + quat.z);
    }
    
    public Quaternion substract(Quaternion quat) {
        return new Quaternion(this.w - quat.w, this.x - quat.x, this.y - quat.y, this.z - quat.z);
    }
    
    public Quaternion multiply(final Quaternion quat) {	
        Quaternion result = new Quaternion();

        result.w = (this.w * quat.w) - (this.x * quat.x) - (this.y * quat.y) - (this.z * quat.z);
        result.x = (this.x * quat.w) + (this.w * quat.x) + (this.y * quat.z) - (this.z * quat.y);
        result.y = (this.y * quat.w) + (this.w * quat.y) + (this.z * quat.x) - (this.x * quat.z);
        result.z = (this.z * quat.w) + (this.w * quat.z) + (this.x * quat.y) - (this.y * quat.x);

        return result;
    }

    public float norm() {
    	return MathUtil.sqrt(this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
    }
    
    public void normalize() {
        float norm = this.norm();
        
        if (norm > 0) {
            
            float t = 1 / norm;

            this.x *= t;
            this.y *= t;
            this.z *= t;
            this.w *= t;
        }
    }

    public Quaternion conjugate() {
        return new Quaternion(this.w, -1 * this.x, -1 * this.y, -1 * this.z);
    }
    
    public Quaternion inverse() {
    	Quaternion result = this.conjugate();

    	float norm = this.norm();
    	float inverseOfSquaredNorm = 1 / (norm * norm);

    	result.w *= inverseOfSquaredNorm;
    	result.x *= inverseOfSquaredNorm;
    	result.y *= inverseOfSquaredNorm;
    	result.z *= inverseOfSquaredNorm;

    	return result;
    }
    
    //angle in radians, axis is unit vector
    public void convertToAxisAngleQuaternion(float angle, Vector axis) {
    	this.w = MathUtil.cos(angle * 0.5f);
    	this.x = axis.x * MathUtil.sin(angle * 0.5f);
    	this.y = axis.y * MathUtil.sin(angle * 0.5f);
    	this.z = axis.z * MathUtil.sin(angle * 0.5f);
    }

    //angles in radians
    public void fromEulerAngles(float thetaZ, float thetaY, float thetaX) {
    	float cosx = MathUtil.cos(thetaX * 0.5f);
    	float sinx = MathUtil.sin(thetaX * 0.5f);

    	float cosy = MathUtil.cos(thetaY * 0.5f);
    	float siny = MathUtil.sin(thetaY * 0.5f);

    	float cosz = MathUtil.cos(thetaZ * 0.5f);
    	float sinz = MathUtil.sin(thetaZ * 0.5f);

    	this.w = cosz * cosy * cosx + sinz * siny * sinx;
    	this.x = cosz * cosy * sinx - sinz * siny * cosx;
    	this.y = cosz * siny * cosx + sinz * cosy * sinx;
    	this.z = sinz * cosy * cosx - cosz * siny * sinx;
    }

    public Quaternion multiply(float scalar) {
        return new Quaternion(this.w * scalar, this.x * scalar, this.y * scalar, this.z * scalar);
    }
    
    public float dotProduct(final Quaternion quat) {
    	return (this.w * quat.w) + (this.x * quat.x) + (this.y * quat.y) + (this.z * quat.z);
    }

    //q0 and q1 are unit quaternions, t from 0.0 to 1.0
    public static Quaternion slerp(Quaternion q0, Quaternion q1, float t) {
    	float w, x, y, z;
    	
    	float cosOmega = q0.dotProduct(q1); 
    	
    	if (cosOmega < 0.0f) {
    		q1 = q1.multiply(-1.0f); 
    		cosOmega = -1 * cosOmega;
    	} 
    	
    	float k0, k1; 
    	
    	if (cosOmega > 0.9999f) { 
    		k0 = 1.0f - t;
    		k1 = t ;
    	} else { 
    		
    		float sinOmega = MathUtil.sqrt(1.0f - (cosOmega * cosOmega)); 
    		
    		float omega = MathUtil.atan(sinOmega, cosOmega);
    		
    		float oneOverSinOmega = 1.0f / sinOmega;

    		k0 = MathUtil.sin((1.0f - t) * omega) * oneOverSinOmega; 
    		k1 = MathUtil.sin(t * omega) * oneOverSinOmega;
    	}
    	
    	w = q0.w * k0 + q1.w * k1; 
    	x = q0.x * k0 + q1.x * k1; 
    	y = q0.y * k0 + q1.y * k1; 
    	z = q0.z * k0 + q1.z * k1; 
    	
        return new Quaternion(w, x, y, z);
    }
}
