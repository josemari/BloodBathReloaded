package org.jomaveger.tge.math;

public final class Matrix {
    
    public float m11, m12, m13, m14;
    public float m21, m22, m23, m24;
    public float m31, m32, m33, m34;
    public float m41, m42, m43, m44;
    
    public Matrix() {
    	this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m14 = 0.0f;
        
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m24 = 0.0f;
        
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
        this.m34 = 0.0f;
        
        this.m41 = 0.0f;
        this.m42 = 0.0f;
        this.m43 = 0.0f;
        this.m44 = 1.0f;
    }

    public Matrix identity() {
        this.m11 = 1.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m14 = 0.0f;
        
        this.m21 = 0.0f;
        this.m22 = 1.0f;
        this.m23 = 0.0f;
        this.m24 = 0.0f;
        
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 1.0f;
        this.m34 = 0.0f;
        
        this.m41 = 0.0f;
        this.m42 = 0.0f;
        this.m43 = 0.0f;
        this.m44 = 1.0f;
        return this;
    }
    
    public Matrix(float _11, float _12, float _13, float _14, float _21, float _22, float _23, float _24, 
                    float _31, float _32, float _33, float _34, float _41, float _42, float _43, float _44) {
        this.m11 = _11;
        this.m12 = _12;
        this.m13 = _13;
        this.m14 = _14;
        
        this.m21 = _21;
        this.m22 = _22;
        this.m23 = _23;
        this.m24 = _24;
        
        this.m31 = _31;
        this.m32 = _32;
        this.m33 = _33;
        this.m34 = _34;
        
        this.m41 = _41;
        this.m42 = _42;
        this.m43 = _43;
        this.m44 = _44;
    }
    
    public Matrix(Matrix mat) {
        this.m11 = mat.m11;
        this.m12 = mat.m12;
        this.m13 = mat.m13;
        this.m14 = mat.m14;
        
        this.m21 = mat.m21;
        this.m22 = mat.m22;
        this.m23 = mat.m23;
        this.m24 = mat.m24;
        
        this.m31 = mat.m31;
        this.m32 = mat.m32;
        this.m33 = mat.m33;
        this.m34 = mat.m34;
        
        this.m41 = mat.m41;
        this.m42 = mat.m42;
        this.m43 = mat.m43;
        this.m44 = mat.m44;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Float.floatToIntBits(this.m11);
        hash = 59 * hash + Float.floatToIntBits(this.m12);
        hash = 59 * hash + Float.floatToIntBits(this.m13);
        hash = 59 * hash + Float.floatToIntBits(this.m14);
        hash = 59 * hash + Float.floatToIntBits(this.m21);
        hash = 59 * hash + Float.floatToIntBits(this.m22);
        hash = 59 * hash + Float.floatToIntBits(this.m23);
        hash = 59 * hash + Float.floatToIntBits(this.m24);
        hash = 59 * hash + Float.floatToIntBits(this.m31);
        hash = 59 * hash + Float.floatToIntBits(this.m32);
        hash = 59 * hash + Float.floatToIntBits(this.m33);
        hash = 59 * hash + Float.floatToIntBits(this.m34);
        hash = 59 * hash + Float.floatToIntBits(this.m41);
        hash = 59 * hash + Float.floatToIntBits(this.m42);
        hash = 59 * hash + Float.floatToIntBits(this.m43);
        hash = 59 * hash + Float.floatToIntBits(this.m44);
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
        final Matrix other = (Matrix) obj;
        if (Float.floatToIntBits(this.m11) != Float.floatToIntBits(other.m11)) {
            return false;
        }
        if (Float.floatToIntBits(this.m12) != Float.floatToIntBits(other.m12)) {
            return false;
        }
        if (Float.floatToIntBits(this.m13) != Float.floatToIntBits(other.m13)) {
            return false;
        }
        if (Float.floatToIntBits(this.m14) != Float.floatToIntBits(other.m14)) {
            return false;
        }
        if (Float.floatToIntBits(this.m21) != Float.floatToIntBits(other.m21)) {
            return false;
        }
        if (Float.floatToIntBits(this.m22) != Float.floatToIntBits(other.m22)) {
            return false;
        }
        if (Float.floatToIntBits(this.m23) != Float.floatToIntBits(other.m23)) {
            return false;
        }
        if (Float.floatToIntBits(this.m24) != Float.floatToIntBits(other.m24)) {
            return false;
        }
        if (Float.floatToIntBits(this.m31) != Float.floatToIntBits(other.m31)) {
            return false;
        }
        if (Float.floatToIntBits(this.m32) != Float.floatToIntBits(other.m32)) {
            return false;
        }
        if (Float.floatToIntBits(this.m33) != Float.floatToIntBits(other.m33)) {
            return false;
        }
        if (Float.floatToIntBits(this.m34) != Float.floatToIntBits(other.m34)) {
            return false;
        }
        if (Float.floatToIntBits(this.m41) != Float.floatToIntBits(other.m41)) {
            return false;
        }
        if (Float.floatToIntBits(this.m42) != Float.floatToIntBits(other.m42)) {
            return false;
        }
        if (Float.floatToIntBits(this.m43) != Float.floatToIntBits(other.m43)) {
            return false;
        }
        if (Float.floatToIntBits(this.m44) != Float.floatToIntBits(other.m44)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Matrix{" + "m11=" + m11 + ", m12=" + m12 + ", m13=" + m13 + ", m14=" + m14 + ", \n m21=" + m21 + ", m22=" + m22 + ", m23=" + m23 + ", m24=" + m24 + ", \n m31=" + m31 + ", m32=" + m32 + ", m33=" + m33 + ", m34=" + m34 + ", \n m41=" + m41 + ", m42=" + m42 + ", m43=" + m43 + ", m44=" + m44 + '}';
    }
    
    public Matrix zero() {
        this.m11 = 0.0f;
        this.m12 = 0.0f;
        this.m13 = 0.0f;
        this.m14 = 0.0f;
        
        this.m21 = 0.0f;
        this.m22 = 0.0f;
        this.m23 = 0.0f;
        this.m24 = 0.0f;
        
        this.m31 = 0.0f;
        this.m32 = 0.0f;
        this.m33 = 0.0f;
        this.m34 = 0.0f;
        
        this.m41 = 0.0f;
        this.m42 = 0.0f;
        this.m43 = 0.0f;
        this.m44 = 0.0f;
        return this;
    }
    
    public boolean isZero() {
        if (this.m11 == 0.0f && this.m12 == 0.0f && this.m13 == 0.0f && this.m14 == 0.0f &&
            this.m21 == 0.0f && this.m22 == 0.0f && this.m23 == 0.0f && this.m24 == 0.0f &&
            this.m31 == 0.0f && this.m32 == 0.0f && this.m33 == 0.0f && this.m34 == 0.0f &&
            this.m41 == 0.0f && this.m42 == 0.0f && this.m43 == 0.0f && this.m44 == 0.0f) {
            
            return true;
            
        } else {
            
            return false;
            
        }
    }
    
    public boolean isIdentity() {
        if (this.m11 == 1.0f && this.m12 == 0.0f && this.m13 == 0.0f && this.m14 == 0.0f &&
            this.m21 == 0.0f && this.m22 == 1.0f && this.m23 == 0.0f && this.m24 == 0.0f &&
            this.m31 == 0.0f && this.m32 == 0.0f && this.m33 == 1.0f && this.m34 == 0.0f &&
            this.m41 == 0.0f && this.m42 == 0.0f && this.m43 == 0.0f && this.m44 == 1.0f) {
            
            return true;
            
        } else {
            
            return false;
            
        }
    }
    
    public float[] toArray() {
    	float[] array = new float[] {m11, m21, m31, m41, m12, m22, m32, m42, m13, m23, m33, m43, m14, m24, m34, m44};
    	return array;
    }
    
    public Matrix transpose() {
		float temp = this.m12;
		this.m12 = this.m21;
		this.m21 = temp;

		temp = this.m13;
		this.m13 = this.m31;
		this.m31 = temp;

		temp = this.m14;
		this.m14 = this.m41;
		this.m41 = temp;

		temp = this.m23;
		this.m23 = this.m32;
		this.m32 = temp;

		temp = this.m24;
		this.m24 = this.m42;
		this.m42 = temp;

		temp = this.m34;
		this.m34 = this.m43;
		this.m43 = temp;
		return this;
    }
  
    public float determinant() {
        Matrix mTrans = new Matrix(this);
        float[] fTemp = new float[12];
        float fDet, tempXx, tempXy, tempXz, tempXw;

        mTrans.transpose();

		fTemp[0] = mTrans.m33 * mTrans.m44;
		fTemp[1] = mTrans.m34 * mTrans.m43;
		fTemp[2] = mTrans.m32 * mTrans.m44;
		fTemp[3] = mTrans.m34 * mTrans.m42;
		fTemp[4] = mTrans.m32 * mTrans.m43;
		fTemp[5] = mTrans.m33 * mTrans.m42;
		fTemp[6] = mTrans.m31 * mTrans.m44;
		fTemp[7] = mTrans.m34 * mTrans.m41;
		fTemp[8] = mTrans.m31 * mTrans.m43;
		fTemp[9] = mTrans.m33 * mTrans.m41;
		fTemp[10] = mTrans.m31 * mTrans.m42;
		fTemp[11] = mTrans.m32 * mTrans.m41;

		tempXx = fTemp[0] * mTrans.m22 + fTemp[3] * mTrans.m23 + fTemp[4] * mTrans.m24;
		tempXx -= fTemp[1] * mTrans.m22 + fTemp[2] * mTrans.m23 + fTemp[5] * mTrans.m24;

		tempXy = fTemp[1] * mTrans.m21 + fTemp[6] * mTrans.m23 + fTemp[9] * mTrans.m24;
		tempXy -= fTemp[0] * mTrans.m21 + fTemp[7] * mTrans.m23 + fTemp[8] * mTrans.m24;

		tempXz = fTemp[2] * mTrans.m21 + fTemp[7] * mTrans.m22 + fTemp[10] * mTrans.m24;
		tempXz -= fTemp[3] * mTrans.m21 + fTemp[6] * mTrans.m22 + fTemp[11] * mTrans.m24;

		tempXw = fTemp[5] * mTrans.m21 + fTemp[8] * mTrans.m22 + fTemp[11] * mTrans.m23;
		tempXw -= fTemp[4] * mTrans.m21 + fTemp[9] * mTrans.m22 + fTemp[10] * mTrans.m23;

		fDet = mTrans.m11 * tempXx + mTrans.m12 * tempXy + mTrans.m13 * tempXz + mTrans.m14 * tempXw;

		return fDet;
    }
    
    public Matrix inverse() {
    	
    	float a = m11 * m22 - m12 * m21;
        float b = m11 * m23 - m13 * m21;
        float c = m11 * m24 - m14 * m21;
        float d = m12 * m23 - m13 * m22;
        float e = m12 * m24 - m14 * m22;
        float f = m13 * m24 - m14 * m23;
        float g = m31 * m42 - m32 * m41;
        float h = m31 * m43 - m33 * m41;
        float i = m31 * m44 - m34 * m41;
        float j = m32 * m43 - m33 * m42;
        float k = m32 * m44 - m34 * m42;
        float l = m33 * m44 - m34 * m43;
        
        float det = a * l - b * k + c * j + d * i - e * h + f * g;
        det = 1.0f / det;
        
        float nm11 = MathUtil.fma( m22, l, MathUtil.fma(-m23, k,  m23 * j)) * det;
        float nm12 = MathUtil.fma(-m12, l, MathUtil.fma( m13, k, -m14 * j)) * det;
        float nm13 = MathUtil.fma( m42, f, MathUtil.fma(-m43, e,  m44 * d)) * det;
        float nm14 = MathUtil.fma(-m32, f, MathUtil.fma( m33, e, -m34 * d)) * det;
        float nm21 = MathUtil.fma(-m21, l, MathUtil.fma( m23, i, -m24 * h)) * det;
        float nm22 = MathUtil.fma( m11, l, MathUtil.fma(-m13, i,  m14 * h)) * det;
        float nm23 = MathUtil.fma(-m41, f, MathUtil.fma( m43, c, -m44 * b)) * det;
        float nm24 = MathUtil.fma( m31, f, MathUtil.fma(-m33, c,  m34 * b)) * det;
        float nm31 = MathUtil.fma( m21, k, MathUtil.fma(-m22, i,  m24 * g)) * det;
        float nm32 = MathUtil.fma(-m11, k, MathUtil.fma( m12, i, -m14 * g)) * det;
        float nm33 = MathUtil.fma( m41, e, MathUtil.fma(-m42, c,  m44 * a)) * det;
        float nm34 = MathUtil.fma(-m31, e, MathUtil.fma( m32, c, -m34 * a)) * det;
        float nm41 = MathUtil.fma(-m21, j, MathUtil.fma( m22, h, -m23 * g)) * det;
        float nm42 = MathUtil.fma( m11, j, MathUtil.fma(-m12, h,  m13 * g)) * det;
        float nm43 = MathUtil.fma(-m41, d, MathUtil.fma( m42, b, -m43 * a)) * det;
        float nm44 = MathUtil.fma( m31, d, MathUtil.fma(-m32, b,  m33 * a)) * det;
        
        this.m11 = (Float.compare(-0.0f, nm11) == 0) ? 0.0f : nm11;
		this.m12 = (Float.compare(-0.0f, nm12) == 0) ? 0.0f : nm12;
		this.m13 = (Float.compare(-0.0f, nm13) == 0) ? 0.0f : nm13;
		this.m14 = (Float.compare(-0.0f, nm14) == 0) ? 0.0f : nm14;

		this.m21 = (Float.compare(-0.0f, nm21) == 0) ? 0.0f : nm21;
		this.m22 = (Float.compare(-0.0f, nm22) == 0) ? 0.0f : nm22;
		this.m23 = (Float.compare(-0.0f, nm23) == 0) ? 0.0f : nm23;
		this.m24 = (Float.compare(-0.0f, nm24) == 0) ? 0.0f : nm24;

		this.m31 = (Float.compare(-0.0f, nm31) == 0) ? 0.0f : nm31;
		this.m32 = (Float.compare(-0.0f, nm32) == 0) ? 0.0f : nm32;
		this.m33 = (Float.compare(-0.0f, nm33) == 0) ? 0.0f : nm33;
		this.m34 = (Float.compare(-0.0f, nm34) == 0) ? 0.0f : nm34;

		this.m41 = (Float.compare(-0.0f, nm41) == 0) ? 0.0f : nm41;
		this.m42 = (Float.compare(-0.0f, nm42) == 0) ? 0.0f : nm42;
		this.m43 = (Float.compare(-0.0f, nm43) == 0) ? 0.0f : nm43;
		this.m44 = (Float.compare(-0.0f, nm44) == 0) ? 0.0f : nm44;
		return this;
	}

	public Matrix negate() {
		Matrix result = new Matrix();

		result.m11 = -1 * this.m11;
		result.m12 = -1 * this.m12;
		result.m13 = -1 * this.m13;
		result.m14 = -1 * this.m14;

		result.m21 = -1 * this.m21;
		result.m22 = -1 * this.m22;
		result.m23 = -1 * this.m23;
		result.m24 = -1 * this.m24;

		result.m31 = -1 * this.m31;
		result.m32 = -1 * this.m32;
		result.m33 = -1 * this.m33;
		result.m34 = -1 * this.m34;

		result.m41 = -1 * this.m41;
		result.m42 = -1 * this.m42;
		result.m43 = -1 * this.m43;
		result.m44 = -1 * this.m44;

		return result;
    }
    
    public Matrix multiply(float scalar) {
		Matrix result = new Matrix();

		result.m11 = scalar * this.m11;
		result.m12 = scalar * this.m12;
		result.m13 = scalar * this.m13;
		result.m14 = scalar * this.m14;

		result.m21 = scalar * this.m21;
		result.m22 = scalar * this.m22;
		result.m23 = scalar * this.m23;
		result.m24 = scalar * this.m24;

		result.m31 = scalar * this.m31;
		result.m32 = scalar * this.m32;
		result.m33 = scalar * this.m33;
		result.m34 = scalar * this.m34;

		result.m41 = scalar * this.m41;
		result.m42 = scalar * this.m42;
		result.m43 = scalar * this.m43;
		result.m44 = scalar * this.m44;
        
        return result;
    }
    
    public Matrix add(Matrix mat) {
		Matrix result = new Matrix();

		result.m11 = this.m11 + mat.m11;
		result.m12 = this.m12 + mat.m12;
		result.m13 = this.m13 + mat.m13;
		result.m14 = this.m14 + mat.m14;

		result.m21 = this.m21 + mat.m21;
		result.m22 = this.m22 + mat.m22;
		result.m23 = this.m23 + mat.m23;
		result.m24 = this.m24 + mat.m24;

		result.m31 = this.m31 + mat.m31;
		result.m32 = this.m32 + mat.m32;
		result.m33 = this.m33 + mat.m33;
		result.m34 = this.m34 + mat.m34;

		result.m41 = this.m41 + mat.m41;
		result.m42 = this.m42 + mat.m42;
		result.m43 = this.m43 + mat.m43;
		result.m44 = this.m44 + mat.m44;
                
        return result;
    }
    
    public Matrix substract(Matrix mat) {
		Matrix result = new Matrix();

		result.m11 = this.m11 - mat.m11;
		result.m12 = this.m12 - mat.m12;
		result.m13 = this.m13 - mat.m13;
		result.m14 = this.m14 - mat.m14;

		result.m21 = this.m21 - mat.m21;
		result.m22 = this.m22 - mat.m22;
		result.m23 = this.m23 - mat.m23;
		result.m24 = this.m24 - mat.m24;

		result.m31 = this.m31 - mat.m31;
		result.m32 = this.m32 - mat.m32;
		result.m33 = this.m33 - mat.m33;
		result.m34 = this.m34 - mat.m34;

		result.m41 = this.m41 - mat.m41;
		result.m42 = this.m42 - mat.m42;
		result.m43 = this.m43 - mat.m43;
		result.m44 = this.m44 - mat.m44;
                
        return result;
    }
    
    public Matrix multiply(Matrix mat) {
        Matrix result = new Matrix();

		result.m11 = this.m11 * mat.m11 + this.m12 * mat.m21 + this.m13 * mat.m31 + this.m14 * mat.m41;
		result.m12 = this.m11 * mat.m12 + this.m12 * mat.m22 + this.m13 * mat.m32 + this.m14 * mat.m42;
		result.m13 = this.m11 * mat.m13 + this.m12 * mat.m23 + this.m13 * mat.m33 + this.m14 * mat.m43;
		result.m14 = this.m11 * mat.m14 + this.m12 * mat.m24 + this.m13 * mat.m34 + this.m14 * mat.m44;

		result.m21 = this.m21 * mat.m11 + this.m22 * mat.m21 + this.m23 * mat.m31 + this.m24 * mat.m41;
		result.m22 = this.m21 * mat.m12 + this.m22 * mat.m22 + this.m23 * mat.m32 + this.m24 * mat.m42;
		result.m23 = this.m21 * mat.m13 + this.m22 * mat.m23 + this.m23 * mat.m33 + this.m24 * mat.m43;
		result.m24 = this.m21 * mat.m14 + this.m22 * mat.m24 + this.m23 * mat.m34 + this.m24 * mat.m44;

		result.m31 = this.m31 * mat.m11 + this.m32 * mat.m21 + this.m33 * mat.m31 + this.m34 * mat.m41;
		result.m32 = this.m31 * mat.m12 + this.m32 * mat.m22 + this.m33 * mat.m32 + this.m34 * mat.m42;
		result.m33 = this.m31 * mat.m13 + this.m32 * mat.m23 + this.m33 * mat.m33 + this.m34 * mat.m43;
		result.m34 = this.m31 * mat.m14 + this.m32 * mat.m24 + this.m33 * mat.m34 + this.m34 * mat.m44;

		result.m41 = this.m41 * mat.m11 + this.m42 * mat.m21 + this.m43 * mat.m31 + this.m44 * mat.m41;
		result.m42 = this.m41 * mat.m12 + this.m42 * mat.m22 + this.m43 * mat.m32 + this.m44 * mat.m42;
		result.m43 = this.m41 * mat.m13 + this.m42 * mat.m23 + this.m43 * mat.m33 + this.m44 * mat.m43;
		result.m44 = this.m41 * mat.m14 + this.m42 * mat.m24 + this.m43 * mat.m34 + this.m44 * mat.m44;

		return result;
    }
    
    public Matrix setTranslation(float dx, float dy, float dz) {
    	this.identity();
    	this.m41 = dx;
    	this.m42 = dy;
    	this.m43 = dz;
    	return this;
    }
    
    public Matrix setScaling(float sx, float sy, float sz) {
        this.identity();
        this.m11 = sx;
        this.m22 = sy;
        this.m33 = sz;
       return this;
    }
    
    public Matrix setRotationByAxisX(float angleInRadians) {
		float cos = MathUtil.cos(angleInRadians);
		float sin = MathUtil.sin(angleInRadians);

		this.m11 = 1.0f;
		this.m12 = 0.0f;
		this.m13 = 0.0f;
		this.m14 = 0.0f;

		this.m21 = 0.0f;
		this.m22 = cos;
		this.m23 = sin;
		this.m24 = 0.0f;

		this.m31 = 0.0f;
		this.m32 = -sin;
		this.m33 = cos;
		this.m34 = 0.0f;

		this.m41 = 0.0f;
		this.m42 = 0.0f;
		this.m43 = 0.0f;
		this.m44 = 1.0f;
		return this;
    }
    
    public Matrix setRotationByAxisY(float angleInRadians) {
		float cos = MathUtil.cos(angleInRadians);
		float sin = MathUtil.sin(angleInRadians);

		this.m11 = cos;
		this.m12 = 0.0f;
		this.m13 = -sin;
		this.m14 = 0.0f;

		this.m21 = 0.0f;
		this.m22 = 1.0f;
		this.m23 = 0.0f;
		this.m24 = 0.0f;

		this.m31 = sin;
		this.m32 = 0.0f;
		this.m33 = cos;
		this.m34 = 0.0f;

		this.m41 = 0.0f;
		this.m42 = 0.0f;
		this.m43 = 0.0f;
		this.m44 = 1.0f;
		return this;
    }
    
    public Matrix setRotationByAxisZ(float angleInRadians) {
		float cos = MathUtil.cos(angleInRadians);
		float sin = MathUtil.sin(angleInRadians);

		this.m11 = cos;
		this.m12 = sin;
		this.m13 = 0.0f;
		this.m14 = 0.0f;

		this.m21 = -sin;
		this.m22 = cos;
		this.m23 = 0.0f;
		this.m24 = 0.0f;

		this.m31 = 0.0f;
		this.m32 = 0.0f;
		this.m33 = 1.0f;
		this.m34 = 0.0f;

		this.m41 = 0.0f;
		this.m42 = 0.0f;
		this.m43 = 0.0f;
		this.m44 = 1.0f;
		return this;
    }
    
    public Vector premultiplyByPoint(Vector vec) {
		Vector result = new Vector();
		float x, y, z;

		x = vec.x * this.m11 + vec.y * this.m21 + vec.z * this.m31 + this.m41;
		y = vec.x * this.m12 + vec.y * this.m22 + vec.z * this.m32 + this.m42;
		z = vec.x * this.m13 + vec.y * this.m23 + vec.z * this.m33 + this.m43;

		result.x = x;
		result.y = y;
		result.z = z;

		return result;
    }
}
