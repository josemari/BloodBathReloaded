package org.jomaveger.tge.graphics.texture;

import java.lang.ref.SoftReference;
import java.util.List;

import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.PointLight;
import org.jomaveger.tge.math.Rectangle;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Vector;

public final class ShadedSurface extends Texture {
	
	public static final int SURFACE_BORDER_SIZE = 1;

    public static final int SHADE_RES_BITS = 4;
    public static final int SHADE_RES = 1 << SHADE_RES_BITS;
    public static final int SHADE_RES_MASK = SHADE_RES - 1;
    public static final int SHADE_RES_SQ = SHADE_RES*SHADE_RES;
    public static final int SHADE_RES_SQ_BITS = SHADE_RES_BITS*2;

    private short[] buffer;
    private SoftReference<short[]> bufferReference;
    private boolean dirty;
    private ShadedTexture sourceTexture;
    private Rectangle sourceTextureBounds;
    private Rectangle surfaceBounds;
    private byte[] shadeMap;
    private int shadeMapWidth;
    private int shadeMapHeight;

    private int shadeValue;
    private int shadeValueInc;

	public ShadedSurface(int width, int height) {
		this(null, width, height);
	}

	public ShadedSurface(short[] buffer, int width, int height) {
        super(width, height);
        this.buffer = buffer;
        bufferReference = new SoftReference<>(buffer);
        sourceTextureBounds = new Rectangle();
        dirty = true;
    }
	
	@Override
	public short getColor(int x, int y) {
		return buffer[x + y * width];
	}
	
    public short getColorChecked(int x, int y) {
        if (x < 0) {
            x = 0;
        }
        else if (x >= width) {
            x = width-1;
        }
        if (y < 0) {
            y = 0;
        }
        else if (y >= height) {
            y = height-1;
        }
        return getColor(x,y);
    }
	
	public static void createShadedSurface(TexturedPolygon poly, ShadedTexture texture, List<PointLight> lights, float ambientLightIntensity) {
		Vector origin = poly.getVertex(0);
		Vector dv = new Vector(poly.getVertex(1));
		dv.minus(origin);
		Vector du = new Vector();
		du.setTo(poly.getNormal().crossProduct(dv));
		Rectangle bounds = new Rectangle(origin, du, dv, texture.getWidth(), texture.getHeight());

		createShadedSurface(poly, texture, bounds, lights, ambientLightIntensity);
	}
	
	public static void createShadedSurface(TexturedPolygon poly, ShadedTexture texture, Rectangle textureBounds,
											List<PointLight> lights, float ambientLightIntensity) {

		poly.setTexture(texture, textureBounds);
		Rectangle surfaceBounds = poly.calcBoundingRectangle();

		Vector du = new Vector(surfaceBounds.getDirectionU());
		Vector dv = new Vector(surfaceBounds.getDirectionV());
		du.setTo(du.multiply(SURFACE_BORDER_SIZE));
		dv.setTo(dv.multiply(SURFACE_BORDER_SIZE));
		surfaceBounds.getOrigin().minus(du);
		surfaceBounds.getOrigin().minus(dv);
		int width = MathUtil.ceil(surfaceBounds.getWidth() + SURFACE_BORDER_SIZE * 2);
		int height = MathUtil.ceil(surfaceBounds.getHeight() + SURFACE_BORDER_SIZE * 2);
		surfaceBounds.setWidth(width);
		surfaceBounds.setHeight(height);

		ShadedSurface surface = new ShadedSurface(width, height);
		surface.setTexture(texture, textureBounds);
		surface.setSurfaceBounds(surfaceBounds);

		surface.buildShadeMap(lights, ambientLightIntensity);

		poly.setTexture(surface, surfaceBounds);
	}

	public void buildShadeMap(List<PointLight> pointLights, float ambientLightIntensity) {

		Vector surfaceNormal = surfaceBounds.getNormal();

		int polyWidth = (int) surfaceBounds.getWidth() - SURFACE_BORDER_SIZE * 2;
		int polyHeight = (int) surfaceBounds.getHeight() - SURFACE_BORDER_SIZE * 2;
		
		shadeMapWidth = polyWidth / SHADE_RES + 4;
		shadeMapHeight = polyHeight / SHADE_RES + 4;
		shadeMap = new byte[shadeMapWidth * shadeMapHeight];

		Vector origin = new Vector(surfaceBounds.getOrigin());
		Vector du = new Vector(surfaceBounds.getDirectionU());
		Vector dv = new Vector(surfaceBounds.getDirectionV());
		du.setTo(du.multiply(SHADE_RES - SURFACE_BORDER_SIZE));
		dv.setTo(dv.multiply(SHADE_RES - SURFACE_BORDER_SIZE));
		origin.minus(du);
		origin.minus(dv);

		Vector point = new Vector();
		du.setTo(surfaceBounds.getDirectionU());
		dv.setTo(surfaceBounds.getDirectionV());
		du.setTo(du.multiply(SHADE_RES));
		dv.setTo(dv.multiply(SHADE_RES));
		for (int v = 0; v < shadeMapHeight; v++) {
			point.setTo(origin);
			for (int u = 0; u < shadeMapWidth; u++) {
				shadeMap[u + v * shadeMapWidth] = calcShade(surfaceNormal, point, pointLights, ambientLightIntensity);
				point.sum(du);
			}
			origin.sum(dv);
		}
	}

	protected byte calcShade(Vector normal, Vector point, List<PointLight> pointLights, float ambientLightIntensity) {
		float intensity = 0;
		Vector directionToLight = new Vector();

		for (int i = 0; i < pointLights.size(); i++) {
			PointLight light = pointLights.get(i);
			directionToLight.setTo(light);
			directionToLight.minus(point);

			float distance = directionToLight.length();
			directionToLight.setTo(directionToLight.normalize());
			float lightIntensity = light.getIntensity(distance) * directionToLight.dotProduct(normal);
			lightIntensity = MathUtil.min(lightIntensity, 1);
			lightIntensity = MathUtil.max(lightIntensity, 0);
			intensity += lightIntensity;
		}

		intensity = MathUtil.min(intensity, 1);
		intensity = MathUtil.max(intensity, 0);

		intensity += ambientLightIntensity;

		intensity = MathUtil.min(intensity, 1);
		intensity = MathUtil.max(intensity, 0);
		int level = MathUtil.round(intensity * ShadedTexture.MAX_LEVEL);
		return (byte) level;
	}
	
	public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
	
	public boolean isDirty() {
        return dirty;
    }
	
	protected void newSurface(int width, int height) {
        buffer = new short[width*height];
        bufferReference = new SoftReference<>(buffer);
    }
	
	public void clearSurface() {
        buffer = null;
    }
	
	public boolean isCleared() {
        return (buffer == null);
    }
	
	public boolean retrieveSurface() {
        if (buffer == null) {
            buffer = bufferReference.get();
        }
        return !(buffer == null);
    }
	
	public void setTexture(ShadedTexture texture) {
        this.sourceTexture = texture;
        sourceTextureBounds.setWidth(texture.getWidth());
        sourceTextureBounds.setHeight(texture.getHeight());
    }
	
	public void setTexture(ShadedTexture texture, Rectangle bounds) {
		setTexture(texture);
		sourceTextureBounds.setTo(bounds);
	}
	
	public void setSurfaceBounds(Rectangle surfaceBounds) {
        this.surfaceBounds = surfaceBounds;
    }
	
	public Rectangle getSurfaceBounds() {
        return surfaceBounds;
    }
	
	public void buildSurface() {

        if (retrieveSurface()) {
            return;
        }

        int width = (int)surfaceBounds.getWidth();
        int height = (int)surfaceBounds.getHeight();

        newSurface(width, height);

        Vector origin = sourceTextureBounds.getOrigin();
        Vector directionU = sourceTextureBounds.getDirectionU();
        Vector directionV = sourceTextureBounds.getDirectionV();

        Vector d = new Vector(surfaceBounds.getOrigin());
        d.minus(origin);
        int startU = (int)((d.dotProduct(directionU) - SURFACE_BORDER_SIZE));
        int startV = (int)((d.dotProduct(directionV) - SURFACE_BORDER_SIZE));
        int offset = 0;
        int shadeMapOffsetU = SHADE_RES - SURFACE_BORDER_SIZE - startU;
        int shadeMapOffsetV = SHADE_RES - SURFACE_BORDER_SIZE - startV;

        for (int v=startV; v<startV + height; v++) {
            sourceTexture.setCurrRow(v);
            int u = startU;
            int amount = SURFACE_BORDER_SIZE;
            while (u < startU + width) {
                getInterpolatedShade(u + shadeMapOffsetU, v + shadeMapOffsetV);

                int endU = MathUtil.min(startU + width, u + amount);
                while (u < endU) {
                    buffer[offset++] =
                        sourceTexture.getColorCurrRow(u,
                             shadeValue >> SHADE_RES_SQ_BITS);
                    shadeValue+=shadeValueInc;
                    u++;
                }
                amount = SHADE_RES;
            }
        }
    }
	
	public int getInterpolatedShade(int u, int v) {

        int fracU = u & SHADE_RES_MASK;
        int fracV = v & SHADE_RES_MASK;

        int offset = (u >> SHADE_RES_BITS) + ((v >> SHADE_RES_BITS) * shadeMapWidth);

        int shade00 = (SHADE_RES-fracV) * shadeMap[offset];
        int shade01 = fracV * shadeMap[offset + shadeMapWidth];
        int shade10 = (SHADE_RES-fracV) * shadeMap[offset + 1];
        int shade11 = fracV * shadeMap[offset + shadeMapWidth + 1];

        shadeValue = SHADE_RES_SQ/2 +
            (SHADE_RES-fracU) * shade00 +
            (SHADE_RES-fracU) * shade01 +
            fracU * shade10 +
            fracU * shade11;

        shadeValueInc = -shade00 - shade01 + shade10 + shade11;

        return shadeValue >> SHADE_RES_SQ_BITS;
    }
	
	public int getShade(int u, int v) {
        return shadeMap[u + v * shadeMapWidth];
    }
}
