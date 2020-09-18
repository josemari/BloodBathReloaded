package org.jomaveger.tge.graphics.texture;

import java.awt.Color;
import java.awt.image.IndexColorModel;

public final class ShadedTexture extends Texture {

    public static final int NUM_SHADE_LEVELS = 64;
    public static final int MAX_LEVEL = NUM_SHADE_LEVELS-1;

    private static final int PALETTE_SIZE_BITS = 8;
    private static final int PALETTE_SIZE = 1 << PALETTE_SIZE_BITS;

    private byte[] buffer;
    private IndexColorModel palette;
    private short[] shadeTable;
    private int defaultShadeLevel;
    private int widthBits;
    private int widthMask;
    private int heightBits;
    private int heightMask;

    private int currRow;

    public ShadedTexture(byte[] buffer,
        int widthBits, int heightBits,
        IndexColorModel palette) {
    	
        this(buffer, widthBits, heightBits, palette, Color.BLACK);
    }

    public ShadedTexture(byte[] buffer,
        int widthBits, int heightBits,
        IndexColorModel palette, Color targetShade) {
    	
        super(1 << widthBits, 1 << heightBits);
        this.buffer = buffer;
        this.widthBits = widthBits;
        this.heightBits = heightBits;
        this.widthMask = getWidth() - 1;
        this.heightMask = getHeight() - 1;
        this.buffer = buffer;
        this.palette = palette;
        defaultShadeLevel = MAX_LEVEL;

        makeShadeTable(targetShade);
    }

    public void makeShadeTable(Color targetShade) {

        shadeTable = new short[NUM_SHADE_LEVELS*PALETTE_SIZE];

        for (int level=0; level<NUM_SHADE_LEVELS; level++) {
            for (int i=0; i<palette.getMapSize(); i++) {
                int red = calcColor(palette.getRed(i),
                    targetShade.getRed(), level);
                int green = calcColor(palette.getGreen(i),
                    targetShade.getGreen(), level);
                int blue = calcColor(palette.getBlue(i),
                    targetShade.getBlue(), level);

                int index = level * PALETTE_SIZE + i;
                // RGB 5:6:5
                shadeTable[index] = (short)(
                            ((red >> 3) << 11) |
                            ((green >> 2) << 5) |
                            (blue >> 3));
            }
        }
    }

    private int calcColor(int palColor, int target, int level) {
        return (palColor - target) * (level+1) /
            NUM_SHADE_LEVELS + target;
    }

    public void setDefaultShadeLevel(int level) {
        defaultShadeLevel = level;
    }

    public int getDefaultShadeLevel() {
        return defaultShadeLevel;
    }

    public short getColor(int x, int y) {
        return getColor(x, y, defaultShadeLevel);
    }

    public short getColor(int x, int y, int shadeLevel) {
        return shadeTable[(shadeLevel << PALETTE_SIZE_BITS) |
            (0xff & buffer[
            (x & widthMask) |
            ((y & heightMask) << widthBits)])];
    }

    public void setCurrRow(int y) {
        currRow = (y & heightMask) << widthBits;
    }

    public short getColorCurrRow(int x, int shadeLevel) {
        return shadeTable[(shadeLevel << PALETTE_SIZE_BITS) |
            (0xff & buffer[(x & widthMask) | currRow])];
    }
}
