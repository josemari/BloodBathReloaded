package org.jomaveger.tge.math;

public interface MathUtil {
    
    public static final float PI = (float) Math.PI;
    
    public static float toRadians(float degrees) {
        return (float) Math.toRadians(degrees);
    }
    
    public static float toDegrees(float radians) {
        return (float) Math.toDegrees(radians);
    }
    
    public static float abs(float value) {
    	return Math.abs(value);
    }
    
    public static int abs(int value) {
    	return Math.abs(value);
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
    
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    public static int min(int a, int b) {
        return Math.min(a, b);
    }
    
    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    public static float min(float a, float b) {
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
    
    public static int ceil(float f) {
        if (f > 0) {
            return (int)f + 1;
        }
        else {
           return (int)f;
        }
    }
    
    public static int ceil(double d) {
    	return (int) Math.ceil(d);
    }
    
    public static boolean isPowerOfTwo(int n) {
        return ((n & (n-1)) == 0);
    }
    
    public static int countbits(int n) {
        int count = 0;
        while (n > 0) {
            count+=(n & 1);
            n>>=1;
        }
        return count;
    }
    
    public static int round(float value) {
        return Math.round(value);
    }
}
