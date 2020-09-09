package org.jomaveger.tge.graphics;

import java.awt.Rectangle;

import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Vector;

public class ViewWindow {
	
	private Rectangle bounds;
    private float angle;
    private float distanceToCamera;

    //angle in radians
    public ViewWindow(int left, int top, int width, int height, float angle) {
        this.bounds = new Rectangle();
        this.angle = angle;
        setBounds(left, top, width, height);
    }

    public void setBounds(int left, int top, int width, int height) {
        this.bounds.x = left;
        this.bounds.y = top;
        this.bounds.width = width;
        this.bounds.height = height;
        this.distanceToCamera = 
        	(bounds.width / 2) / MathUtil.tan(angle / 2);
    }

    //angle in radians
    public void setAngle(float angle) {
        this.angle = angle;
        this.distanceToCamera = 
        	(bounds.width / 2) / MathUtil.tan(angle / 2);
    }


    //angle in radians
    public float getAngle() {
        return angle;
    }


    public int getWidth() {
        return bounds.width;
    }


    public int getHeight() {
        return bounds.height;
    }


    public int getTopOffset() {
        return bounds.y;
    }


    public int getLeftOffset() {
        return bounds.x;
    }


    public float getDistance() {
        return distanceToCamera;
    }

    public float convertFromViewXToScreenX(float x) {
        return x + bounds.x + bounds.width / 2;
    }

    public float convertFromViewYToScreenY(float y) {
        return -y + bounds.y + bounds.height / 2;
    }

    public float convertFromScreenXToViewX(float x) {
        return x - bounds.x - bounds.width / 2;
    }

    public float convertFromScreenYToViewY(float y) {
        return -y + bounds.y + bounds.height / 2;
    }

    public void project(Vector v) {
    	
    	// project to view window
        v.x = distanceToCamera * v.x / -v.z;
        v.y = distanceToCamera * v.y / -v.z;

        // convert to screen coordinates
        v.x = convertFromViewXToScreenX(v.x);
        v.y = convertFromViewYToScreenY(v.y);
    }
}
