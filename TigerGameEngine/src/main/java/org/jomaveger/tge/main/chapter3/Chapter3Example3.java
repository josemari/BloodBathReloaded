package org.jomaveger.tge.main.chapter3;

import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.graphics.ShadedSurfacePolygonRenderer;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.graphics.texture.ShadedSurface;
import org.jomaveger.tge.graphics.texture.ShadedTexture;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.PointLight;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Transform;

public class Chapter3Example3 extends Chapter3Example1 {
	
	private List<PointLight> lights;
    private float ambientLightIntensity;

	public Chapter3Example3(String windowTitle) {
		super(windowTitle);
	}

	public Chapter3Example3(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	@Override
	protected void initialize() {
		ambientLightIntensity = .05f;
        lights = new ArrayList<>();
        lights.add(new PointLight(-100,100,-975, 1f, 500));
        lights.add(new PointLight(50,150,-700, 1f, 500));
        lights.add(new PointLight(2000,2000,-2000, .1f, -1));
        lights.add(new PointLight(-250,250,-1200, 1f, 500));
		super.initialize();
	}
	
	@Override
	public void setTexture(TexturedPolygon poly, Texture texture) {
		ShadedSurface.createShadedSurface(poly, (ShadedTexture) texture, lights, ambientLightIntensity);
	}
	
	@Override
	public Texture loadTexture(String imageName) {
        return Texture.createTexture(imageName, true);
    }
	
	@Override
	public void createPolygonRenderer() {
        viewWindow = new ViewWindow(0, 0,
            window.getWidth(), window.getHeight(),
            MathUtil.toRadians(75));

        Transform camera = new Transform(0,100,0);
        polygonRenderer =
            new ShadedSurfacePolygonRenderer(camera, viewWindow);

    }
}
