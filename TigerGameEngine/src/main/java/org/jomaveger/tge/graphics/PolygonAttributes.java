package org.jomaveger.tge.graphics;

public class PolygonAttributes {

	private boolean isOneSided;
	private boolean isTransparent;
	private boolean is8BitColor;
	private boolean isRGB16;
	private boolean isRBG24;
	private boolean isShadeModePure;
	private boolean isShadeModeFlat;
	private boolean isShadeModeGouraud;
	private boolean isShadeModePhong;
	
	public PolygonAttributes() {
		this.isOneSided = true;
		this.isTransparent = false;
		this.is8BitColor = false;
		this.isRGB16 = false;
		this.isRBG24 = true;
		this.isShadeModePure = true;
		this.isShadeModeFlat = false;
		this.isShadeModeGouraud = false;
		this.isShadeModePhong = false;
	}

	public boolean isOneSided() {
		return isOneSided;
	}

	public void setOneSided(boolean isOneSided) {
		this.isOneSided = isOneSided;
	}

	public boolean isTransparent() {
		return isTransparent;
	}

	public void setTransparent(boolean isTransparent) {
		this.isTransparent = isTransparent;
	}

	public boolean isIs8BitColor() {
		return is8BitColor;
	}

	public void setIs8BitColor(boolean is8BitColor) {
		this.is8BitColor = is8BitColor;
	}

	public boolean isRGB16() {
		return isRGB16;
	}

	public void setRGB16(boolean isRGB16) {
		this.isRGB16 = isRGB16;
	}

	public boolean isRBG24() {
		return isRBG24;
	}

	public void setRBG24(boolean isRBG24) {
		this.isRBG24 = isRBG24;
	}

	public boolean isShadeModePure() {
		return isShadeModePure;
	}

	public void setShadeModePure(boolean isShadeModePure) {
		this.isShadeModePure = isShadeModePure;
	}

	public boolean isShadeModeFlat() {
		return isShadeModeFlat;
	}

	public void setShadeModeFlat(boolean isShadeModeFlat) {
		this.isShadeModeFlat = isShadeModeFlat;
	}

	public boolean isShadeModeGouraud() {
		return isShadeModeGouraud;
	}

	public void setShadeModeGouraud(boolean isShadeModeGouraud) {
		this.isShadeModeGouraud = isShadeModeGouraud;
	}

	public boolean isShadeModePhong() {
		return isShadeModePhong;
	}

	public void setShadeModePhong(boolean isShadeModePhong) {
		this.isShadeModePhong = isShadeModePhong;
	}
}
