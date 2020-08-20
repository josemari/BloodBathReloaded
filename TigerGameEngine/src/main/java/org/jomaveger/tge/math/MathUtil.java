package org.jomaveger.tge.math;

public interface MathUtil {
    
    public static final float PI = (float) Math.PI;
    
    public static float toRadians(float degrees) {
        return (float) Math.toRadians(degrees);
    }
    
    public static float toDegrees(float radians) {
        return (float) Math.toDegrees(radians);
    }
    
    public static float sqrt(float value) {
        return (float) Math.sqrt(value);
    }
    
    public static float sin(float value) {
        return (float) Math.sin(value);
    }
    
    public static float cos(float value) {
        return (float) Math.cos(value);
    }
    
    public static float tan(float value) {
        return (float) Math.tan(value);
    }
    
    public static float acos(float value) {
        return (float) Math.acos(value);
    }
    
    public static float asin(float value) {
        return (float) Math.asin(value);
    }
    
    public static float atan(float y, float x) {
        return (float) Math.atan2(y, x);
    }
    
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    public static long min(long a, long b) {
        return Math.min(a, b);
    }
    
    public static float pow(float base, float exponent) {
        return (float) Math.pow(base, exponent);
    }
    
    public static float fma(float a, float b, float c) {
    	return a * b + c;
    }
    
    public static float invsqrt(float value) {
        return 1.0f / (float) Math.sqrt(value);
    }
}
