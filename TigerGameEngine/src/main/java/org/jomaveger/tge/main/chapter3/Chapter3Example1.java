package org.jomaveger.tge.main.chapter3;

import java.util.ArrayList;
import java.util.List;

import org.jomaveger.tge.architecture.GameCore;
import org.jomaveger.tge.graphics.TexturedPolygonRenderer;
import org.jomaveger.tge.graphics.ViewWindow;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Rectangle;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public class Chapter3Example1 extends GameCore {

	public Chapter3Example1(String windowTitle, int width, int height) {
		super(windowTitle, width, height);
	}

	public Chapter3Example1(String windowTitle) {
		super(windowTitle);
	}

	@Override
	public void createPolygons() {

        // create Textures
        Texture wall = loadTexture("textures/wall1.png");
        Texture roof = loadTexture("textures/roof1.png");

        TexturedPolygon poly;

        // walls
        List<Vector> lv1 = new ArrayList<>();
        lv1.add(new Vector(-200, 250, -1000));
        lv1.add(new Vector(-200, 0, -1000));
        lv1.add(new Vector(200, 0, -1000));
        lv1.add(new Vector(200, 250, -1000));
        poly = new TexturedPolygon(lv1);
        setTexture(poly, wall);
        polygons.add(poly);

        List<Vector> lv2 = new ArrayList<>();
		lv2.add(new Vector(200, 250, -1400));
		lv2.add(new Vector(200, 0, -1400));
		lv2.add(new Vector(-200, 0, -1400));
		lv2.add(new Vector(-200, 250, -1400));
        poly = new TexturedPolygon(lv2);
        setTexture(poly, wall);
        polygons.add(poly);

        List<Vector> lv3 = new ArrayList<>();
		lv3.add(new Vector(-200, 250, -1400));
		lv3.add(new Vector(-200, 0, -1400));
		lv3.add(new Vector(-200, 0, -1000));
		lv3.add(new Vector(-200, 250, -1000));
        poly = new TexturedPolygon(lv3);
        setTexture(poly, wall);
        polygons.add(poly);

        List<Vector> lv4 = new ArrayList<>();
		lv4.add(new Vector(200, 250, -1000));
		lv4.add(new Vector(200, 0, -1000));
		lv4.add(new Vector(200, 0, -1400));
		lv4.add(new Vector(200, 250, -1400));
        poly = new TexturedPolygon(lv4);
        setTexture(poly, wall);
        polygons.add(poly);

        // roof
        List<Vector> lv5 = new ArrayList<>();
		lv5.add(new Vector(-200, 250, -1000));
		lv5.add(new Vector(200, 250, -1000));
		lv5.add(new Vector(75, 400, -1200));
		lv5.add(new Vector(-75, 400, -1200));
        poly = new TexturedPolygon(lv5);
        setTexture(poly, roof);
        polygons.add(poly);

        List<Vector> lv6 = new ArrayList<>();
		lv6.add(new Vector(-200, 250, -1400));
		lv6.add(new Vector(-200, 250, -1000));
		lv6.add(new Vector(-75, 400, -1200));
        poly = new TexturedPolygon(lv6);
        setTexture(poly, roof);
        polygons.add(poly);
        
        List<Vector> lv7 = new ArrayList<>();
		lv7.add(new Vector(200, 250, -1400));
		lv7.add(new Vector(-200, 250, -1400));
		lv7.add(new Vector(-75, 400, -1200));
		lv7.add(new Vector(75, 400, -1200));
        poly = new TexturedPolygon(lv7);
        setTexture(poly, roof);
        polygons.add(poly);

        List<Vector> lv8 = new ArrayList<>();
		lv8.add(new Vector(200, 250, -1000));
		lv8.add(new Vector(200, 250, -1400));
		lv8.add(new Vector(75, 400, -1200));
        poly = new TexturedPolygon(lv8);
        setTexture(poly, roof);
        polygons.add(poly);
    }

    public void setTexture(TexturedPolygon poly, Texture texture) {
        Vector origin = poly.getVertex(0);

        Vector dv = new Vector(poly.getVertex(1));
        dv.minus(origin);

        Vector du = new Vector();
        du.setTo(poly.getNormal().crossProduct(dv));

        Rectangle textureBounds = new Rectangle(origin, du, dv,
            texture.getWidth(), texture.getHeight());

        poly.setTexture(texture, textureBounds);
    }

    public Texture loadTexture(String imageName) {
        return Texture.createTexture(imageName);
    }

    @Override
    public void createPolygonRenderer() {
        viewWindow = new ViewWindow(0, 0,
            window.getWidth(), window.getHeight(),
            MathUtil.toRadians(75));


        Transform camera = new Transform(0,100,0);
        polygonRenderer = new TexturedPolygonRenderer(camera, viewWindow);
    }
}
