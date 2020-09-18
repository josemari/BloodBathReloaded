package org.jomaveger.tge.graphics.texture;

public abstract class ScanRenderer {
	
	protected Texture currentTexture;

    public void setTexture(Texture texture) {
        this.currentTexture = texture;
    }

    public abstract void render(int offset, int left, int right);
}
