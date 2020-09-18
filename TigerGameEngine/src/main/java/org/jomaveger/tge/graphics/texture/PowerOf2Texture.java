package org.jomaveger.tge.graphics.texture;

public final class PowerOf2Texture extends Texture {

	private short[] buffer;
    private int widthBits;
    private int widthMask;
    private int heightBits;
    private int heightMask;

	public PowerOf2Texture(short[] buffer, 
			int widthBits, int heightBits) {
		
		super(1 << widthBits, 1 << heightBits);
	    this.buffer = buffer;
	    this.widthBits = widthBits;
	    this.heightBits = heightBits;
	    this.widthMask = getWidth() - 1;
	    this.heightMask = getHeight() - 1;
	}

	public short getColor(int x, int y) {
        return buffer[
            (x & widthMask) +
            ((y & heightMask) << widthBits)];
    }
}
