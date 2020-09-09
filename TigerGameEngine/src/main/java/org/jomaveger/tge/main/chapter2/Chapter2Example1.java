package org.jomaveger.tge.main.chapter2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.architecture.IGameLogic;
import org.jomaveger.tge.graphics.ScreenManager;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.input.KeyInputManager;
import org.jomaveger.tge.input.MouseInputManager;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public class Chapter2Example1 implements IGameLogic {
	
	private Polygon treeLeaves;
	private Polygon treeTrunk;
	private Polygon transformedPolygon;
	private ViewWindow viewWindow;
	private Transform treeTransform = new Transform(0, 0, -500); 
	
	@Override
	public void dispose() {
	}

	@Override
	public void init(ScreenManager window) {
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
		
		window.setCursor(MouseInputManager.INVISIBLE_CURSOR);
	}	

	@Override
	public void update(float interval, KeyInputManager keyInput, MouseInputManager mouseInput, ScreenManager window) {
		treeTransform.rotateAngleY(0.002f*interval);
		
		if (keyInput.keyDown(KeyEvent.VK_UP)) {
			treeTransform.getLocation().z += 0.5f*interval;
		}
		
		if (keyInput.keyDown(KeyEvent.VK_DOWN)) {
			treeTransform.getLocation().z -= 0.5f*interval;
		}
	}

	@Override
	public void render(ScreenManager window) {
		Graphics2D g = window.getGraphics();
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
        
        g.dispose();
	}

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
	public void input(ScreenManager window, KeyInputManager keyInput, MouseInputManager mouseInput) {
	}
}
