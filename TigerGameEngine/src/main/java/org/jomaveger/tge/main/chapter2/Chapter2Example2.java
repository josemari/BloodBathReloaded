package org.jomaveger.tge.main.chapter2;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.graphics.PolygonRenderer;
import org.jomaveger.tge.graphics.SolidPolygonRenderer;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.input.GameAction;
import org.jomaveger.tge.input.InputManager;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public class Chapter2Example2 extends GameEngine {
	
	public Chapter2Example2(String windowTitle) {
		super(windowTitle);
	}
	
	public Chapter2Example2(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	protected PolygonRenderer polygonRenderer;
    protected ViewWindow viewWindow;
    protected List<Polygon> polygons;

    private boolean drawFrameRate = true;
    private boolean drawInstructions = true;

    // for calculating frame rate
    private int numFrames;
    private long startTime;
    private float frameRate;

    private GameAction exit = new GameAction("exit");
    private GameAction smallerView = new GameAction("smallerView",
        GameAction.DETECT_INITAL_PRESS_ONLY);
    private GameAction largerView = new GameAction("largerView",
        GameAction.DETECT_INITAL_PRESS_ONLY);
    private GameAction frameRateToggle = new GameAction(
        "frameRateToggle", GameAction.DETECT_INITAL_PRESS_ONLY);
    private GameAction goForward = new GameAction("goForward");
    private GameAction goBackward = new GameAction("goBackward");
    private GameAction goUp = new GameAction("goUp");
    private GameAction goDown = new GameAction("goDown");
    private GameAction goLeft = new GameAction("goLeft");
    private GameAction goRight = new GameAction("goRight");
    private GameAction turnLeft = new GameAction("turnLeft");
    private GameAction turnRight = new GameAction("turnRight");
    private GameAction tiltUp = new GameAction("tiltUp");
    private GameAction tiltDown = new GameAction("tiltDown");
    private GameAction tiltLeft = new GameAction("tiltLeft");
    private GameAction tiltRight = new GameAction("tiltRight");
	
	public void createPolygons() {
        Polygon poly;

        // walls
        List<Vector> lv1 = new ArrayList<>();
		lv1.add(new Vector(-200, 0, -1000));
		lv1.add(new Vector(200, 0, -1000));
		lv1.add(new Vector(200, 250, -1000));
		lv1.add(new Vector(-200, 250, -1000));
        poly = new Polygon(lv1);
        poly.setColor(Color.WHITE);
        polygons.add(poly);
        
        List<Vector> lv2 = new ArrayList<>();
		lv2.add(new Vector(-200, 0, -1400));
		lv2.add(new Vector(-200, 250, -1400));
		lv2.add(new Vector(200, 250, -1400));
		lv2.add(new Vector(200, 0, -1400));
        poly = new Polygon(lv2);
        poly.setColor(Color.WHITE);
        polygons.add(poly);
        
        List<Vector> lv3 = new ArrayList<>();
		lv3.add(new Vector(-200, 0, -1400));
		lv3.add(new Vector(-200, 0, -1000));
		lv3.add(new Vector(-200, 250, -1000));
		lv3.add(new Vector(-200, 250, -1400));
        poly = new Polygon(lv3);
        poly.setColor(Color.GRAY);
        polygons.add(poly);
        
        List<Vector> lv4 = new ArrayList<>();
		lv4.add(new Vector(200, 0, -1000));
		lv4.add(new Vector(200, 0, -1400));
		lv4.add(new Vector(200, 250, -1400));
		lv4.add(new Vector(200, 250, -1000));
        poly = new Polygon(lv4);
        poly.setColor(Color.GRAY);
        polygons.add(poly);

        // door and windows
        List<Vector> lv5 = new ArrayList<>();
		lv5.add(new Vector(0, 0, -1000));
		lv5.add(new Vector(75, 0, -1000));
		lv5.add(new Vector(75, 125, -1000));
		lv5.add(new Vector(0, 125, -1000));
        poly = new Polygon(lv5);
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        
        List<Vector> lv6 = new ArrayList<>();
		lv6.add(new Vector(-150, 150, -1000));
		lv6.add(new Vector(-100, 150, -1000));
		lv6.add(new Vector(-100, 200, -1000));
		lv6.add(new Vector(-150, 200, -1000));
        poly = new Polygon(lv6);
        poly.setColor(new Color(0x660000));
        polygons.add(poly);

        // roof
        List<Vector> lv7 = new ArrayList<>();
		lv7.add(new Vector(-200, 250, -1000));
		lv7.add(new Vector(200, 250, -1000));
		lv7.add(new Vector(75, 400, -1200));
		lv7.add(new Vector(-75, 400, -1200));
        poly = new Polygon(lv7);
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        
        List<Vector> lv8 = new ArrayList<>();
		lv8.add(new Vector(-200, 250, -1400));
		lv8.add(new Vector(-200, 250, -1000));
		lv8.add(new Vector(-75, 400, -1200));
        poly = new Polygon(lv8);
        poly.setColor(new Color(0x330000));
        polygons.add(poly);
        
        List<Vector> lv9 = new ArrayList<>();
		lv9.add(new Vector(200, 250, -1400));
		lv9.add(new Vector(-200, 250, -1400));
		lv9.add(new Vector(-75, 400, -1200));
		lv9.add(new Vector(75, 400, -1200));
        poly = new Polygon(lv9);
        poly.setColor(new Color(0x660000));
        polygons.add(poly);
        
        List<Vector> lv10 = new ArrayList<>();
		lv10.add(new Vector(200, 250, -1000));
		lv10.add(new Vector(200, 250, -1400));
		lv10.add(new Vector(75, 400, -1200));
        poly = new Polygon(lv10);
        poly.setColor(new Color(0x330000));
        polygons.add(poly);
    }
	
	public void createPolygonRenderer() {
        // make the view window the entire screen
        viewWindow = new ViewWindow(0, 0,
            window.getWidth(), window.getHeight(),
            MathUtil.toRadians(75));

        Transform camera = new Transform(0, 100, 0);
        polygonRenderer = new SolidPolygonRenderer(camera, viewWindow);
    }
	
	public void setViewBounds(int width, int height) {
        width = MathUtil.min(width, window.getWidth());
        height = MathUtil.min(height, window.getHeight());
        width = MathUtil.max(64, width);
        height = MathUtil.max(48, height);
        viewWindow.setBounds((window.getWidth() - width) /2,
            (window.getHeight() - height) /2, width, height);
    }

	public void drawText(Graphics2D g) {
		
        g.setColor(Color.WHITE);
        if (drawInstructions) {
            g.drawString("Use the mouse/arrow keys to move. " +
                "Press Esc to exit.", 5, 34);
        }
        
        if (drawFrameRate) {
            calcFrameRate();
            g.drawString(frameRate + " frames/sec", 5,
                window.getHeight() - 5);
        }
    }

    public void calcFrameRate() {
        numFrames++;
        long currTime = System.currentTimeMillis();

        // calculate the frame rate every 500 milliseconds
        if (currTime > startTime + 500) {
            frameRate = (float)numFrames * 1000 /
                (currTime - startTime);
            startTime = currTime;
            numFrames = 0;
        }
    }

	@Override
	protected void initialize() {
		inputManager.setCursor(InputManager.INVISIBLE_CURSOR);
		inputManager.setRelativeMouseMode(true);
		
		inputManager.mapToKey(exit, KeyEvent.VK_ESCAPE);
        inputManager.mapToKey(goForward, KeyEvent.VK_W);
        inputManager.mapToKey(goForward, KeyEvent.VK_UP);
        inputManager.mapToKey(goBackward, KeyEvent.VK_S);
        inputManager.mapToKey(goBackward, KeyEvent.VK_DOWN);
        inputManager.mapToKey(goLeft, KeyEvent.VK_A);
        inputManager.mapToKey(goLeft, KeyEvent.VK_LEFT);
        inputManager.mapToKey(goRight, KeyEvent.VK_D);
        inputManager.mapToKey(goRight, KeyEvent.VK_RIGHT);
        inputManager.mapToKey(goUp, KeyEvent.VK_PAGE_UP);
        inputManager.mapToKey(goDown, KeyEvent.VK_PAGE_DOWN);
        inputManager.mapToMouse(turnLeft,
            InputManager.MOUSE_MOVE_LEFT);
        inputManager.mapToMouse(turnRight,
            InputManager.MOUSE_MOVE_RIGHT);
        inputManager.mapToMouse(tiltUp,
            InputManager.MOUSE_MOVE_UP);
        inputManager.mapToMouse(tiltDown,
            InputManager.MOUSE_MOVE_DOWN);

        inputManager.mapToKey(tiltLeft, KeyEvent.VK_INSERT);
        inputManager.mapToKey(tiltRight, KeyEvent.VK_DELETE);

        inputManager.mapToKey(smallerView, KeyEvent.VK_SUBTRACT);
        inputManager.mapToKey(smallerView, KeyEvent.VK_MINUS);
        inputManager.mapToKey(largerView, KeyEvent.VK_ADD);
        inputManager.mapToKey(largerView, KeyEvent.VK_PLUS);
        inputManager.mapToKey(largerView, KeyEvent.VK_EQUALS);
        inputManager.mapToKey(frameRateToggle, KeyEvent.VK_R);

        createPolygonRenderer();

        // create polygons
        polygons = new ArrayList<>();
        createPolygons();		
	}

	@Override
	protected void input() {
	}

	@Override
	protected void render(Graphics2D g) {
        polygonRenderer.startFrame(g);
        for (int i=0; i<polygons.size(); i++) {
            polygonRenderer.draw(g, polygons.get(i));
        }
        polygonRenderer.endFrame(g);

        drawText(g);
	}

	@Override
	protected void update(float interval) {
		if (exit.isPressed()) {
            stop();
            return;
        }

        // check options
        if (largerView.isPressed()) {
            setViewBounds(viewWindow.getWidth() + 64,
                viewWindow.getHeight() + 48);
        }
        else if (smallerView.isPressed()) {
            setViewBounds(viewWindow.getWidth() - 64,
                viewWindow.getHeight() - 48);
        }
        if (frameRateToggle.isPressed()) {
            drawFrameRate = !drawFrameRate;
        }

        // cap elapsedTime
        float elapsedTime = MathUtil.min(interval, 100);

        float angleChange = 0.0002f*elapsedTime;
        float distanceChange = 0.5f*elapsedTime;

        Transform camera = polygonRenderer.getCamera();
        Vector cameraLoc = camera.getLocation();

        // apply movement
        if (goForward.isPressed()) {
            cameraLoc.x -= distanceChange * camera.getSinAngleY();
            cameraLoc.z -= distanceChange * camera.getCosAngleY();
        }
        if (goBackward.isPressed()) {
            cameraLoc.x += distanceChange * camera.getSinAngleY();
            cameraLoc.z += distanceChange * camera.getCosAngleY();
        }
        if (goLeft.isPressed()) {
            cameraLoc.x -= distanceChange * camera.getCosAngleY();
            cameraLoc.z += distanceChange * camera.getSinAngleY();
        }
        if (goRight.isPressed()) {
            cameraLoc.x += distanceChange * camera.getCosAngleY();
            cameraLoc.z -= distanceChange * camera.getSinAngleY();
        }
        if (goUp.isPressed()) {
            cameraLoc.y += distanceChange;
        }
        if (goDown.isPressed()) {
            cameraLoc.y -= distanceChange;
        }

        // look up/down (rotate around x)
        int tilt = tiltUp.getAmount() - tiltDown.getAmount();
        tilt = MathUtil.min(tilt, 200);
        tilt = MathUtil.max(tilt, -200);

        // limit how far you can look up/down
        float newAngleX = camera.getAngleX() + tilt * angleChange;
        newAngleX = MathUtil.max(newAngleX, -MathUtil.PI/2);
        newAngleX = MathUtil.min(newAngleX, MathUtil.PI/2);
        camera.setAngleX(newAngleX);

        // turn (rotate around y)
        int turn = turnLeft.getAmount() - turnRight.getAmount();
        turn = MathUtil.min(turn, 200);
        turn = MathUtil.max(turn, -200);
        camera.rotateAngleY(turn * angleChange);

        // tilt head left/right (rotate around z)
        if (tiltLeft.isPressed()) {
            camera.rotateAngleZ(10*angleChange);
        }
        if (tiltRight.isPressed()) {
            camera.rotateAngleZ(-10*angleChange);
        }
    }

	@Override
	protected void close() {
	}
}
