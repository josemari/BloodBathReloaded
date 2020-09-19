package org.jomaveger.tge.graphics;

import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.jomaveger.tge.graphics.texture.ShadedSurface;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Transform;

public class ShadedSurfacePolygonRenderer extends TexturedPolygonRenderer {
	
	private List<ShadedSurface> builtSurfaces = new LinkedList<>();

	public ShadedSurfacePolygonRenderer(Transform camera, ViewWindow viewWindow) {
		this(camera, viewWindow, true);
	}
	
	public ShadedSurfacePolygonRenderer(Transform camera, ViewWindow viewWindow, boolean clearViewEveryFrame) {
		super(camera, viewWindow, clearViewEveryFrame);
	}

	@Override
	public void endFrame(Graphics2D g) {
        super.endFrame(g);

        Iterator<ShadedSurface> i = builtSurfaces.iterator();
        while (i.hasNext()) {
            ShadedSurface surface = i.next();
            if (surface.isDirty()) {
                surface.clearSurface();
                i.remove();
            }
            else {
                surface.setDirty(true);
            }
        }
    }

	@Override
	protected void drawCurrentPolygon(Graphics2D g) {
        buildSurface();
        super.drawCurrentPolygon(g);
    }
	
	protected void buildSurface() {
        if (sourcePolygon instanceof TexturedPolygon) {
            Texture texture = ((TexturedPolygon)sourcePolygon).getTexture();
            if (texture instanceof ShadedSurface) {
                ShadedSurface surface = (ShadedSurface)texture;
                if (surface.isCleared()) {
                    surface.buildSurface();
                    builtSurfaces.add(surface);
                }
                surface.setDirty(false);
            }
        }
    }
}
