package org.jomaveger.tge.math;

public class Transform {

	private Vector location;
    private float cosAngleX;
    private float sinAngleX;
    private float cosAngleY;
    private float sinAngleY;
    private float cosAngleZ;
    private float sinAngleZ;

    public Transform() {
        this(0, 0, 0);
    }

    public Transform(float x, float y, float z) {
        location = new Vector(x, y, z);
        setAngle(0, 0, 0);
    }

    public Transform(Transform v) {
        location = new Vector();
        setTo(v);
    }

    public Object clone() {
        return new Transform(this);
    }

    public void setTo(Transform v) {
        location.setTo(v.location);
        this.cosAngleX = v.cosAngleX;
        this.sinAngleX = v.sinAngleX;
        this.cosAngleY = v.cosAngleY;
        this.sinAngleY = v.sinAngleY;
        this.cosAngleZ = v.cosAngleZ;
        this.sinAngleZ = v.sinAngleZ;
    }

    public Vector getLocation() {
        return location;
    }

    public float getCosAngleX() {
        return cosAngleX;
    }

    public float getSinAngleX() {
        return sinAngleX;
    }

    public float getCosAngleY() {
        return cosAngleY;
    }

    public float getSinAngleY() {
        return sinAngleY;
    }

    public float getCosAngleZ() {
        return cosAngleZ;
    }

    public float getSinAngleZ() {
        return sinAngleZ;
    }

    public float getAngleX() {
        return MathUtil.atan(sinAngleX, cosAngleX);
    }

    public float getAngleY() {
        return MathUtil.atan(sinAngleY, cosAngleY);
    }

    public float getAngleZ() {
        return MathUtil.atan(sinAngleZ, cosAngleZ);
    }

    public void setAngleX(float angleX) {
        cosAngleX = MathUtil.cos(angleX);
        sinAngleX = MathUtil.sin(angleX);
    }

    public void setAngleY(float angleY) {
        cosAngleY = MathUtil.cos(angleY);
        sinAngleY = MathUtil.sin(angleY);
    }

    public void setAngleZ(float angleZ) {
        cosAngleZ = MathUtil.cos(angleZ);
        sinAngleZ = MathUtil.sin(angleZ);
    }

    public void setAngle(float angleX, float angleY, float angleZ) {
        setAngleX(angleX);
        setAngleY(angleY);
        setAngleZ(angleZ);
    }

    public void rotateAngleX(float angle) {
        if (angle != 0.0f) {
            setAngleX(getAngleX() + angle);
        }
    }

    public void rotateAngleY(float angle) {
        if (angle != 0.0f) {
            setAngleY(getAngleY() + angle);
        }
    }

    public void rotateAngleZ(float angle) {
        if (angle != 0.0f) {
            setAngleZ(getAngleZ() + angle);
        }
    }

    public void rotateAngle(float angleX, float angleY, float angleZ) {
        rotateAngleX(angleX);
        rotateAngleY(angleY);
        rotateAngleZ(angleZ);
    }
}
