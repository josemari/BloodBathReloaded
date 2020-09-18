package org.jomaveger.tge.main.chapter3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import org.jomaveger.tge.graphics.ShadedTexturedPolygonRenderer;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.input.GameAction;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.PointLight;
import org.jomaveger.tge.math.Transform;


public class Chapter3Example2 extends Chapter3Example1 {
	
	private GameAction brighterLight = new GameAction("brighter");
    private GameAction dimmerLight = new GameAction("dimmer");

    private PointLight light;

	public Chapter3Example2(String windowTitle) {
		super(windowTitle);
	}

	public Chapter3Example2(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		inputManager.mapToKey(brighterLight, KeyEvent.VK_P);
		inputManager.mapToKey(brighterLight, KeyEvent.VK_PLUS);
        inputManager.mapToKey(brighterLight, KeyEvent.VK_ADD);
        inputManager.mapToKey(brighterLight, KeyEvent.VK_EQUALS);
        inputManager.mapToKey(dimmerLight, KeyEvent.VK_SUBTRACT);
        inputManager.mapToKey(dimmerLight, KeyEvent.VK_MINUS);
        inputManager.mapToKey(dimmerLight, KeyEvent.VK_M);
        
	}

	@Override
	public void createPolygonRenderer() {
        viewWindow = new org.jomaveger.tge.graphics.ViewWindow(0, 0,
            window.getWidth(), window.getHeight(),
            (float)Math.toRadians(75));


        Transform camera = new Transform(0,100,0);
        ShadedTexturedPolygonRenderer polygonRenderer = new ShadedTexturedPolygonRenderer(camera, viewWindow);
        light = new PointLight(-500,500,0, 1f);
        light.setDistanceFalloff(2000);
        polygonRenderer.setLightSource(light);
        polygonRenderer.setAmbientLightIntensity(.05f);

        this.polygonRenderer = polygonRenderer;

    }
	
	@Override
	public Texture loadTexture(String imageName) {
        return Texture.createTexture(imageName, true);
    }

	@Override
	public void render(Graphics2D g) {
        super.render(g);
        g.setColor(Color.WHITE);
        g.drawString("Press P/M to change the light intensity.",
            5, 24*2);
    }

	@Override
    public void update(float interval) {
        super.update(interval);

        if (brighterLight.isPressed()) {
            light.setIntensity(
                MathUtil.min(5,light.getIntensity()+.005f*interval));
        }
        if (dimmerLight.isPressed()) {
            light.setIntensity(
                MathUtil.max(0,light.getIntensity()-.005f*interval));
        }
    }
}
