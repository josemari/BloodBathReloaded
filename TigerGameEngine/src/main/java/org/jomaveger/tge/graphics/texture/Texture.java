package org.jomaveger.tge.graphics.texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferUShort;
import java.awt.image.IndexColorModel;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.util.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Texture {
	
	protected static final Logger LOG = LoggerFactory.getLogger(Texture.class);

	protected int width;
    protected int height;

    public Texture(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public abstract short getColor(int x, int y);
    
    public static Texture createTexture(String filename) {
        return createTexture(filename, false);
    }
    
	public static Texture createTexture(String filename, boolean shaded) {
		try {
			return createTexture(ImageIO.read(Texture.class.getClassLoader().getResourceAsStream(filename)), shaded);
		} catch (IOException ex) {
			LOG.error(ExceptionUtils.getExpandedMessage(ex));
			return null;
		}
	}
	
	public static Texture createTexture(BufferedImage image) {
		return createTexture(image, false);
	}
    
	public static Texture createTexture(BufferedImage image, boolean shaded) {
		int type = image.getType();
		int width = image.getWidth();
		int height = image.getHeight();

		if (!MathUtil.isPowerOfTwo(width) || !MathUtil.isPowerOfTwo(height)) {
			throw new IllegalArgumentException("Size of texture must be a power of two.");
		}
		
		if (shaded) {
            if (type != BufferedImage.TYPE_BYTE_INDEXED) {
                BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(image, 0, 0, null);
                g.dispose();
                image = newImage;
            }
            DataBuffer dest = image.getRaster().getDataBuffer();
            return new ShadedTexture(((DataBufferByte)dest).getData(),
                MathUtil.countbits(width-1), MathUtil.countbits(height-1),
                (IndexColorModel)image.getColorModel());
        } else {
        	
        	if (type != BufferedImage.TYPE_USHORT_565_RGB) {
    			BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_USHORT_565_RGB);
    			Graphics2D g = newImage.createGraphics();
    			g.drawImage(image, 0, 0, null);
    			g.dispose();
    			image = newImage;
    		}

    		DataBuffer dest = image.getRaster().getDataBuffer();
    		return new PowerOf2Texture(((DataBufferUShort) dest).getData(), 
    				MathUtil.countbits(width - 1), MathUtil.countbits(height - 1));        	
        }
	}
}
