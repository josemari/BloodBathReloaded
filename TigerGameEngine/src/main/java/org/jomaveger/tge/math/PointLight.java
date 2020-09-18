package org.jomaveger.tge.math;

public class PointLight extends Vector {
	
	public static final float NO_DISTANCE_FALLOFF = -1;

    private float intensity;
    private float distanceFalloff;

    public PointLight() {
        this(0,0,0, 1, NO_DISTANCE_FALLOFF);
    }

    public PointLight(PointLight p) {
        setTo(p);
    }

    public PointLight(float x, float y, float z, float intensity) {
        this(x, y, z, intensity, NO_DISTANCE_FALLOFF);
    }

    public PointLight(float x, float y, float z, float intensity, float distanceFalloff) {
        setTo(x, y, z);
        setIntensity(intensity);
        setDistanceFalloff(distanceFalloff);
    }

    public void setTo(PointLight p) {
        setTo(p.x, p.y, p.z);
        setIntensity(p.getIntensity());
        setDistanceFalloff(p.getDistanceFalloff());
    }

    public float getIntensity(float distance) {
        if (distanceFalloff == NO_DISTANCE_FALLOFF) {
            return intensity;
        }
        else if (distance >= distanceFalloff) {
            return 0;
        }
        else {
            return intensity * (distanceFalloff - distance)
                / (distanceFalloff + distance);
        }
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }

    public float getDistanceFalloff() {
        return distanceFalloff;
    }

    public void setDistanceFalloff(float distanceFalloff) {
        this.distanceFalloff = distanceFalloff;
    }
}
