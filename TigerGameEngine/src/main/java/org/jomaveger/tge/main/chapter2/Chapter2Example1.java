package org.jomaveger.tge.main.chapter2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.input.GameAction;
import org.jomaveger.tge.input.InputManager;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public class Chapter2Example1 extends GameEngine {
	
	public Chapter2Example1(String windowTitle) {
		super(windowTitle);
	}

	private Polygon treeLeaves;
	private Polygon treeTrunk;
	private Polygon transformedPolygon;
	private ViewWindow viewWindow;
	private Transform treeTransform = new Transform(0, 0, -500);
	
	private GameAction exit = new GameAction("exit");
    private GameAction zoomIn = new GameAction("zoomIn");
    private GameAction zoomOut = new GameAction("zoomOut");

	
	private void trandformAndDraw(Graphics2D g, Polygon poly) {
		transformedPolygon.setTo(poly);

        // translate and rotate the polygon
        transformedPolygon.add(treeTransform);

        // project the polygon to the screen
        transformedPolygon.project(viewWindow);

        // convert the polygon to a Java2D GeneralPath and draw it
        GeneralPath path = new GeneralPath();
        Vector v = transformedPolygon.getVertex(0);
        path.moveTo(v.x, v.y);
        for (int i=1; i<transformedPolygon.getNumVertices(); i++) {
            v = transformedPolygon.getVertex(i);
            path.lineTo(v.x, v.y);
        }
        g.setColor(poly.getColor());
        g.fill(path);
	}

	@Override
	protected void initialize() {
		List<Vector> lv1 = new ArrayList<>();
		lv1.add(new Vector(-50, -35, 0));
		lv1.add(new Vector(50, -35, 0));
		lv1.add(new Vector(0, 150, 0));
		treeLeaves = new Polygon(lv1);
		
		List<Vector> lv2 = new ArrayList<>();
		lv2.add(new Vector(-5, -50, 0));
		lv2.add(new Vector(5, -50, 0));
		lv2.add(new Vector(5, -35, 0));
		lv2.add(new Vector(-5, -35, 0));
		treeTrunk = new Polygon(lv2);
		
		transformedPolygon = new Polygon();
		
		viewWindow = new ViewWindow(0, 0,
	            window.getWidth(), window.getHeight(),
	            MathUtil.toRadians(75));
		
		treeLeaves.setColor(Color.GREEN);
		treeTrunk.setColor(Color.RED);
		
		inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
        inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(zoomIn, KeyEvent.VK_UP);
        inputManager.mapToKey(zoomOut, KeyEvent.VK_DOWN);		
	}

	@Override
	protected void input() {
	}

	@Override
	protected void render(Graphics2D g) {
		// erase background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, window.getWidth(), window.getHeight());

        // draw message
        g.setColor(Color.WHITE);
        g.drawString("Press up/down to zoom. Press Esc to exit.",
            15, 48);

        // draw the tree polygons
        trandformAndDraw(g, treeTrunk);
        trandformAndDraw(g, treeLeaves);		
	}

	@Override
	protected void update(float interval) {
		if (exit.isPressed()) {
            stop();
            return;
        }
		
		float elapsedTime = MathUtil.min(interval, 100);
		
		treeTransform.rotateAngleY(0.002f*elapsedTime);
		
		if (zoomIn.isPressed()) {
			treeTransform.getLocation().z += 0.5f*elapsedTime;
		}
		
		if (zoomOut.isPressed()) {
			treeTransform.getLocation().z -= 0.5f*elapsedTime;
		}
	}

	@Override
	protected void close() {
	}
}
