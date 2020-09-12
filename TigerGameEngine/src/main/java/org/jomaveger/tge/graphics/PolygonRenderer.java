package org.jomaveger.tge.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Transform;

public abstract class PolygonRenderer {

	protected ScanConverter scanConverter;
    protected Transform camera;
    protected ViewWindow viewWindow;
    protected boolean clearViewEveryFrame;
    protected Polygon sourcePolygon;
    protected Polygon destPolygon;

    public PolygonRenderer(Transform camera, ViewWindow viewWindow) {
        this(camera, viewWindow, true);
    }

    public PolygonRenderer(Transform camera, ViewWindow viewWindow, 
    						boolean clearViewEveryFrame) {
        this.camera = camera;
        this.viewWindow = viewWindow;
        this.clearViewEveryFrame = clearViewEveryFrame;
        init();
    }

    protected void init() {
        destPolygon = new Polygon();
        scanConverter = new ScanConverter(viewWindow);
    }

    public Transform getCamera() {
        return camera;
    }

    public void startFrame(Graphics2D g) {
        if (clearViewEveryFrame) {
            g.setColor(Color.black);
            g.fillRect(viewWindow.getLeftOffset(),
                viewWindow.getTopOffset(),
                viewWindow.getWidth(), viewWindow.getHeight());
        }
    }

    public void endFrame(Graphics2D g) {
    }

    public boolean draw(Graphics2D g, Polygon poly) {
        if (poly.isFacing(camera.getLocation())) {
            sourcePolygon = poly;
            destPolygon.setTo(poly);
            destPolygon.subtract(camera);
            boolean visible = destPolygon.clip(-1);
            if (visible) {
                destPolygon.project(viewWindow);
                visible = scanConverter.convert(destPolygon);
                if (visible) {
                    drawCurrentPolygon(g);
                    return true;
                }
            }
        }
        return false;
    }

    protected abstract void drawCurrentPolygon(Graphics2D g);
}
