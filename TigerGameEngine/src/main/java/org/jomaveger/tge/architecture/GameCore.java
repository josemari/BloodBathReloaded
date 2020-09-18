package org.jomaveger.tge.architecture;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.graphics.PolygonRenderer;
import org.jomaveger.tge.graphics.SolidPolygonRenderer;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.input.GameAction;
import org.jomaveger.tge.input.InputManager;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public abstract class GameCore extends GameEngine {

	protected PolygonRenderer polygonRenderer;
    protected ViewWindow viewWindow;
    protected List<Polygon> polygons;

    private boolean drawFrameRate = true;
    private boolean drawInstructions = true;

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
    protected GameAction goForward = new GameAction("goForward");
    protected GameAction goBackward = new GameAction("goBackward");
    protected GameAction goUp = new GameAction("goUp");
    protected GameAction goDown = new GameAction("goDown");
    protected GameAction goLeft = new GameAction("goLeft");
    protected GameAction goRight = new GameAction("goRight");
    protected GameAction turnLeft = new GameAction("turnLeft");
    protected GameAction turnRight = new GameAction("turnRight");
    protected GameAction tiltUp = new GameAction("tiltUp");
    protected GameAction tiltDown = new GameAction("tiltDown");
    protected GameAction tiltLeft = new GameAction("tiltLeft");
    protected GameAction tiltRight = new GameAction("tiltRight");
        
    public GameCore(String windowTitle) {
		super(windowTitle);
	}

	public GameCore(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
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

        polygons = new ArrayList<>();
        createPolygons();		
	}

    public abstract void createPolygons();
    
    public void createPolygonRenderer() {
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

        for (int i=0; i<2; i++) {
            Graphics2D g = window.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0,0, window.getWidth(), window.getHeight());
            window.update();
        }
    }
    
    @Override
	protected void input() {
	}
    
    @Override
	protected void close() {
	}
    
    @Override
    public void update(float elapsedTime) {

        if (exit.isPressed()) {
            stop();
            return;
        }
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

        updateWorld(elapsedTime);
    }
    
    public void updateWorld(float elapsedTime) {

        // cap elapsedTime
        elapsedTime = MathUtil.min(elapsedTime, 100);

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
    public void render(Graphics2D g) {
        int viewX1 = viewWindow.getLeftOffset();
        int viewY1 = viewWindow.getTopOffset();
        int viewX2 = viewX1 + viewWindow.getWidth();
        int viewY2 = viewY1 + viewWindow.getHeight();
        if (viewX1 != 0 || viewY1 != 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0,0, viewX1, window.getHeight());
            g.fillRect(viewX2, 0, window.getWidth() - viewX2,
                window.getHeight());
            g.fillRect(viewX1,0, viewWindow.getWidth(), viewY1);
            g.fillRect(viewX1,viewY2, viewWindow.getWidth(),
                window.getHeight() - viewY2);
        }

        drawPolygons(g);
        drawText(g);
    }

    public void drawPolygons(Graphics2D g) {
        polygonRenderer.startFrame(g);
        for (int i=0; i<polygons.size(); i++) {
            polygonRenderer.draw(g, polygons.get(i));
        }
        polygonRenderer.endFrame(g);
    }

    public void drawText(Graphics2D g) {

        g.setColor(Color.WHITE);

        if (drawInstructions) {
            g.drawString("Use the mouse/arrow keys to move. " +
                "Press Esc to exit.", 5, 30);
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
}
