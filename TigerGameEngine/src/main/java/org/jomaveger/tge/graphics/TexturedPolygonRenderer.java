package org.jomaveger.tge.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferUShort;
import java.util.HashMap;
import java.util.Map;

import org.jomaveger.tge.graphics.texture.PowerOf2Texture;
import org.jomaveger.tge.graphics.texture.ScanRenderer;
import org.jomaveger.tge.graphics.texture.ShadedTexture;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.math.Rectangle;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;


public class TexturedPolygonRenderer extends PolygonRenderer {

	protected Vector a = new Vector();
    protected Vector b = new Vector();
    protected Vector c = new Vector();
    protected Vector viewPos = new Vector();
    protected BufferedImage doubleBuffer;
    protected short[] doubleBufferData;
    protected Map<Class<? extends Texture>, ScanRenderer> scanRenderers;

	public TexturedPolygonRenderer(Transform camera, ViewWindow viewWindow) {
		this(camera, viewWindow, true);
    }

	public TexturedPolygonRenderer(Transform camera, ViewWindow viewWindow, boolean clearViewEveryFrame) {
		super(camera, viewWindow, clearViewEveryFrame);
    }
	
	protected void init() {
        destPolygon = new TexturedPolygon();
        scanConverter = new ScanConverter(viewWindow);

        scanRenderers = new HashMap<>();
        scanRenderers.put(PowerOf2Texture.class, new PowerOf2TextureRenderer());
        scanRenderers.put(ShadedTexture.class, new ShadedTextureRenderer());
    }
	
	public void startFrame(Graphics2D g) {
		if (doubleBuffer == null || 
			doubleBuffer.getWidth() != viewWindow.getWidth() ||
			doubleBuffer.getHeight() != viewWindow.getHeight()) {
			
			doubleBuffer = new BufferedImage(viewWindow.getWidth(), viewWindow.getHeight(),
					BufferedImage.TYPE_USHORT_565_RGB);
			
			DataBuffer dest = doubleBuffer.getRaster().getDataBuffer();
			doubleBufferData = ((DataBufferUShort) dest).getData();
		}
		if (clearViewEveryFrame) {
			for (int i = 0; i < doubleBufferData.length; i++) {
				doubleBufferData[i] = 0;
			}
		}
	}

	public void endFrame(Graphics2D g) {
		g.drawImage(doubleBuffer, viewWindow.getLeftOffset(), viewWindow.getTopOffset(), null);
	}

	protected void drawCurrentPolygon(Graphics2D g) {
		if (!(sourcePolygon instanceof TexturedPolygon)) {
			return;
		}
		TexturedPolygon poly = (TexturedPolygon) destPolygon;
		Texture texture = poly.getTexture();
		ScanRenderer scanRenderer = (ScanRenderer) scanRenderers.get(texture.getClass());
		scanRenderer.setTexture(texture);
		Rectangle textureBounds = poly.getTextureBounds();

		a.setTo(textureBounds.getDirectionV().crossProduct(textureBounds.getOrigin()));
		b.setTo(textureBounds.getOrigin().crossProduct(textureBounds.getDirectionU()));
		c.setTo(textureBounds.getDirectionU().crossProduct(textureBounds.getDirectionV()));
		
		int y = scanConverter.getTopBoundary();
		viewPos.y = viewWindow.convertFromScreenYToViewY(y);
		viewPos.z = -viewWindow.getDistance();

		while (y <= scanConverter.getBottomBoundary()) {
			ScanConverter.Scan scan = scanConverter.getScan(y);

			if (scan.isValid()) {
				viewPos.x = viewWindow.convertFromScreenXToViewX(scan.left);
				int offset = (y - viewWindow.getTopOffset()) * viewWindow.getWidth()
						+ (scan.left - viewWindow.getLeftOffset());

				scanRenderer.render(offset, scan.left, scan.right);
			}
			y++;
			viewPos.y--;
		}
	}

	class PowerOf2TextureRenderer extends ScanRenderer {

		@Override
		public void render(int offset, int left, int right) {
			PowerOf2Texture texture = (PowerOf2Texture)currentTexture;
			float u = a.dotProduct(viewPos);
	        float v = b.dotProduct(viewPos);
	        float z = c.dotProduct(viewPos);
	        float du = a.x;
	        float dv = b.x;
	        float dz = c.x;
	        for (int x=left; x<=right; x++) {
	            doubleBufferData[offset++] =
	                texture.getColor((int)(u/z), (int)(v/z));
	            u+=du;
	            v+=dv;
	            z+=dz;
	        }
		}
	}
	
	class ShadedTextureRenderer extends ScanRenderer {

		@Override
		public void render(int offset, int left, int right) {
			ShadedTexture texture = (ShadedTexture)currentTexture;
			float u = a.dotProduct(viewPos);
	        float v = b.dotProduct(viewPos);
	        float z = c.dotProduct(viewPos);
	        float du = a.x;
	        float dv = b.x;
	        float dz = c.x;
	        for (int x=left; x<=right; x++) {
	            doubleBufferData[offset++] =
	                texture.getColor((int)(u/z), (int)(v/z));
	            u+=du;
	            v+=dv;
	            z+=dz;
	        }
		}
	}
}
