package org.jomaveger.tiger.math;

public final class Quaternion {
    
    public float x;
    public float y;
    public float z;
    public float w;
    
    public Quaternion() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 0.0f;
    }
    
    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
    
    public Quaternion(final Quaternion quat) {
        this.x = quat.x;
        this.y = quat.y;
        this.z = quat.z;
        this.w = quat.w;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Float.floatToIntBits(this.x);
        hash = 37 * hash + Float.floatToIntBits(this.y);
        hash = 37 * hash + Float.floatToIntBits(this.z);
        hash = 37 * hash + Float.floatToIntBits(this.w);
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
        if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
            return false;
        }
        if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(this.z) != Float.floatToIntBits(other.z)) {
            return false;
        }
        if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Quaternion{" + "x=" + x + ", y=" + y + ", z=" + z + ", w=" + w + '}';
    }
    
    public Quaternion add(Quaternion quat) {
        return new Quaternion(this.x + quat.x, this.y + quat.y, this.z + quat.z, this.w + quat.w);
    }
    
    public Quaternion substract(Quaternion quat) {
        return new Quaternion(this.x - quat.x, this.y - quat.y, this.z - quat.z, this.w - quat.w);
    }
    
    public Quaternion multiply(float scalar) {
        return new Quaternion(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
    }
    
    public Quaternion multiply(final Quaternion quat) {	
        Quaternion result = new Quaternion();

	result.w = (this.w * quat.w) - (this.x * quat.x) - (this.y * quat.y) - (this.z * quat.z);
	result.x = (this.x * quat.w) + (this.w * quat.x) + (this.y * quat.z) - (this.z * quat.y);
	result.y = (this.y * quat.w) + (this.w * quat.y) + (this.z * quat.x) - (this.x * quat.z);
	result.z = (this.z * quat.w) + (this.w * quat.z) + (this.x * quat.y) - (this.y * quat.x);

	return result;
    }
    
    public Quaternion multiply(final Vector vec) {
        Quaternion result = new Quaternion();

        result.w = - (this.x * vec.x) - (this.y * vec.y) - (this.z * vec.z);
        result.x =   (this.w * vec.x) + (this.y * vec.z) - (this.z * vec.y);
        result.y =   (this.w * vec.y) + (this.z * vec.x) - (this.x * vec.z);
        result.z =   (this.w * vec.z) + (this.x * vec.y) - (this.y * vec.x);

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
        return new Quaternion(-1 * this.x, -1 * this.y, -1 * this.z, this.w);
    }
    
    public Quaternion inverse() {
    	Quaternion result = this.conjugate();

    	float norm = this.norm();
    	float inverseOfSquaredNorm = 1 / (norm * norm);

    	result.x *= inverseOfSquaredNorm;
    	result.y *= inverseOfSquaredNorm;
    	result.z *= inverseOfSquaredNorm;
    	result.w *= inverseOfSquaredNorm;

    	return result;
    }
    
    public Quaternion divide(final Quaternion quat) {
    	return this.multiply(quat.inverse());
    }
    
    public float dotProduct(final Quaternion quat) {
    	return (this.x * quat.x) + (this.y * quat.y) + (this.z * quat.z) + (this.w * quat.w);
    }

    public float angle(final Quaternion quat) {
        float dotProduct = this.dotProduct(quat);
        float normProduct = this.norm() * quat.norm();
        return MathUtil.acos(dotProduct / normProduct);
    }
    
    public void convertToUnitNormQuaternion() {
    	float angle = MathUtil.toRadians(w);
		
    	this.w = MathUtil.cos(angle * 0.5f);

    	Vector vec = new Vector(this.x, this.y, this.z);
    	vec.normalize();
		
    	this.x = vec.x * MathUtil.sin(angle * 0.5f);
    	this.y = vec.y * MathUtil.sin(angle * 0.5f);
    	this.z = vec.z * MathUtil.sin(angle * 0.5f);
    }
    
    public void fromEulerAngles(float pitch, float yaw, float roll) {
    	float x = MathUtil.toRadians(pitch / 2);
    	float y = MathUtil.toRadians(yaw / 2);
    	float z = MathUtil.toRadians(roll / 2);

    	float cosx = MathUtil.cos(x);
    	float sinx = MathUtil.sin(x);

    	float cosy = MathUtil.cos(y);
    	float siny = MathUtil.sin(y);

    	float cosz = MathUtil.cos(z);
    	float sinz = MathUtil.sin(z);

    	this.w = cosz * cosy * cosx + sinz * siny * sinx;
    	this.x = cosz * cosy * sinx - sinz * siny * cosx;
    	this.y = cosz * siny * cosx + sinz * cosy * sinx;
    	this.z = sinz * cosy * cosx - cosz * siny * sinx;
    }
    
    public Vector toEulerAngles() {
    	float x = 0.0f;
    	float y = 0.0f;
    	float z = 0.0f;

    	float test = 2 * (this.x * this.z - this.w * this.y);

    	if (test != 1 && test != -1) {
		
            x = MathUtil.atan(this.y * this.z + this.w * this.x, 0.5f - (this.x * this.x + this.y * this.y));
            y = MathUtil.asin(-2 * (this.x * this.z - this.w * this.y));
            z = MathUtil.atan(this.x * this.y + this.w * this.z, 0.5f - (this.y * this.y + this.z * this.z));
            
    	} else if (test == 1) {
		
            z = MathUtil.atan(this.x * this.y + this.w * this.z, 0.5f - (this.y * this.y + this.z * this.z));
            y = -1 * MathUtil.PI / 2.0f;
            x = -z + MathUtil.atan(this.x * this.y - this.w * this.z, this.x * this.z + this.w * this.y);
            
    	} else if (test == -1) {
		
            z = MathUtil.atan(this.x * this.y + this.w * this.z, 0.5f - (this.y * this.y + this.z * this.z));
            y = MathUtil.PI / 2.0f;
            x = z + MathUtil.atan(this.x * this.y - this.w * this.z, this.x * this.z + this.w * this.y);
    	}

    	x = MathUtil.toDegrees(x);
    	y = MathUtil.toDegrees(y);
    	z = MathUtil.toDegrees(z);

    	Vector euler = new Vector(x, y, z);

        return euler;
    }
    
    public Matrix toMatrix() {
    	Matrix mat = new Matrix();

		float yy = this.y * this.y;
		float zz = this.z * this.z;
		float xy = this.x * this.y;
		float zw = this.z * this.w;
		float xz = this.x * this.z;
		float yw = this.y * this.w;
		float xx = this.x * this.x;
		float yz = this.y * this.z;
		float xw = this.x * this.w;

		mat.Xx = 1 - 2 * yy - 2 * zz;
		mat.Xy = 2 * xy + 2 * zw;
		mat.Xz = 2 * xz - 2 * yw;

		mat.Yx = 2 * xy - 2 * zw;
		mat.Yy = 1 - 2 * xx - 2 * zz;
		mat.Yz = 2 * yz + 2 * xw;

		mat.Zx = 2 * xz + 2 * yw;
		mat.Zy = 2 * yz - 2 * xw;
		mat.Zz = 1 - 2 * xx - 2 * yy;

        return mat;
    }
    
    public void fromMatrix(final Matrix mat) {
        float trace = mat.Xx + mat.Yy + mat.Zz;

		if (trace > 0) {

			this.w = 0.5f * MathUtil.sqrt(1 + trace);
			float S = 0.25f / this.w;

			this.x = S * (mat.Yz - mat.Zy);
			this.y = S * (mat.Zx - mat.Xz);
			this.z = S * (mat.Xy - mat.Yx);

		} else if (mat.Xx > mat.Yy && mat.Xx > mat.Zz) {

			this.x = 0.5f * MathUtil.sqrt(1 + mat.Xx - mat.Yy - mat.Zz);
			float X = 0.25f / this.x;

			this.y = X * (mat.Yx * mat.Xy);
			this.z = X * (mat.Zx * mat.Xz);
			this.w = X * (mat.Yz * mat.Zy);

		} else if (mat.Yy > mat.Zz) {

			this.y = 0.5f * MathUtil.sqrt(1 - mat.Xx + mat.Yy - mat.Zz);
			float Y = 0.25f / this.y;

			this.x = Y * (mat.Yx + mat.Xy);
			this.z = Y * (mat.Zy + mat.Yz);
			this.w = Y * (mat.Zx - mat.Xz);

		} else {

			this.z = 0.5f * MathUtil.sqrt(1 - mat.Xx - mat.Yy + mat.Zz);
			float Z = 0.25f / this.z;

			this.x = Z * (mat.Zx + mat.Xz);
			this.y = Z * (mat.Zy + mat.Yz);
			this.w = Z * (mat.Xy + mat.Yx);
		}
    }
    
    public Quaternion slerp(float amount, Quaternion end) {
        if (amount < 0.0) {
            return this;
        } else if (amount > 1.0) {
            return end;
        }
            
        float dot = this.dotProduct(end);
        float x2, y2, z2, w2;
        
        if (dot < 0.0) {
            
            dot = 0.0f - dot;
            x2 = 0.0f - end.x;
            y2 = 0.0f - end.y;
            z2 = 0.0f - end.z;
            w2 = 0.0f - end.w;
        
        } else {
            
            x2 = end.x;
            y2 = end.y;
            z2 = end.z;
            w2 = end.w;
        }

        float t1, t2;
        final float EPSILON = 0.0001f;
        
        if ((1.0f - dot) > EPSILON) { // standard case (slerp)
            
            float angle = MathUtil.acos(dot);
            float sinAngle = MathUtil.sin(angle);
            t1 = MathUtil.sin((1.0f - amount) * angle) / sinAngle;
            t2 = MathUtil.sin(amount * angle) / sinAngle;
        
        } else { // just lerp
            
            t1 = 1.0f - amount;
            t2 = amount;
        
        }

        return new Quaternion((this.x * t1) + (x2 * t2), (this.y * t1) + (y2 * t2),
                                (this.z * t1) + (z2 * t2), (this.w * t1) + (w2 * t2));
    }
}
