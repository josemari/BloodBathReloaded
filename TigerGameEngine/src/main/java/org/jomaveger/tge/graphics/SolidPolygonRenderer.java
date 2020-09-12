package org.jomaveger.tge.graphics;

import java.awt.Graphics2D;

import org.jomaveger.tge.math.Transform;

public class SolidPolygonRenderer extends PolygonRenderer {

	public SolidPolygonRenderer(Transform camera, ViewWindow viewWindow) {
		this(camera, viewWindow, true);
	}

	public SolidPolygonRenderer(Transform camera, ViewWindow viewWindow, 
								boolean clearViewEveryFrame) {
		super(camera, viewWindow, clearViewEveryFrame);
	}

	protected void drawCurrentPolygon(Graphics2D g) {
		g.setColor(sourcePolygon.getColor());
		
		int y = scanConverter.getTopBoundary();
		while (y <= scanConverter.getBottomBoundary()) {
			ScanConverter.Scan scan = scanConverter.getScan(y);
			if (scan.isValid()) {
				g.drawLine(scan.left, y, scan.right, y);
			}
			y++;
		}
	}
}
