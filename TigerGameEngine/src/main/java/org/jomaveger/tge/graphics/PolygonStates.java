package org.jomaveger.tge.graphics;

public class PolygonStates {

	private boolean isActive;
	private boolean isClipped;
	private boolean isBackface;
	
	public PolygonStates() {
		this.isActive = true;
		this.isClipped = false;
		this.isBackface = false;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isClipped() {
		return isClipped;
	}

	public void setClipped(boolean isClipped) {
		this.isClipped = isClipped;
	}

	public boolean isBackface() {
		return isBackface;
	}

	public void setBackface(boolean isBackface) {
		this.isBackface = isBackface;
	}
}
