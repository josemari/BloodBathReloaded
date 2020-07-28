package org.jomaveger.tiger.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.Serializable;

import org.jomaveger.tiger.util.lang.IDisposable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Framebuffer implements Serializable, IDisposable {
	
	private static final Logger LOG = LoggerFactory.getLogger(Framebuffer.class);
	
	private BufferedImage framebuffer;
	private Color[] pixels;
	private int width;
	private int height;

	public Framebuffer(int width, int height) {
		this.framebuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.pixels = new Color[width * height];
		this.width = width;
		this.height = height;
	}
	
	public BufferedImage getFrameBuffer() {
		return framebuffer; 
	}
	
	public void setPixel(int x, int y, Color c) {
        pixels[x + y * width] = c;
    }
	
	public void drawLine(float x1, float y1, Color color1, 
						 float x2, float y2, Color color2) {
		
		float xdiff = (x2 - x1);
		float ydiff = (y2 - y1);

		if (xdiff == 0.0f && ydiff == 0.0f) {
			setPixel(Math.round(x1), Math.round(y1), color1);
			return;
		}

		if (Math.abs(xdiff) > Math.abs(ydiff)) {
			
			float xmin, xmax;

			if (x1 < x2) {
				
				xmin = x1;
				xmax = x2;
			} else {
				
				xmin = x2;
				xmax = x1;
			}

			float slope = ydiff / xdiff;
			
			for (float x = xmin; x <= xmax; x += 1.0f) {
				float y = y1 + ((x - x1) * slope);
				Color color = new Color(color1.getRGB() + ((color2.getRGB() - color1.getRGB()) * Math.round(((x - x1) / xdiff))), true);
				setPixel(Math.round(x), Math.round(y), color);
			}
		} else {
			
			float ymin, ymax;

			if (y1 < y2) {
				
				ymin = y1;
				ymax = y2;
			} else {
				
				ymin = y2;
				ymax = y1;
			}

			float slope = xdiff / ydiff;
			for (float y = ymin; y <= ymax; y += 1.0f) {
				float x = x1 + ((y - y1) * slope);
				Color color = new Color(color1.getRGB() + ((color2.getRGB() - color1.getRGB()) * Math.round(((y - y1) / ydiff))), true);
				setPixel(Math.round(x), Math.round(y), color);
			}
		}
	}
	
	public void swapBuffers() {
		int[] outputpixels = ((DataBufferInt) framebuffer.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < pixels.length; ++i) {
			if (pixels[i] != null)
				outputpixels[i] = pixels[i].getRGB();
		}
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}

	@Override
	public void dispose() {
		framebuffer.flush();
	}
}
