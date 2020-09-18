package org.jomaveger.tge.math;

import java.util.List;

import org.jomaveger.tge.graphics.texture.Texture;

public class TexturedPolygon extends Polygon {

	protected Rectangle textureBounds;
	protected Texture texture;

	public TexturedPolygon() {
		super();
        textureBounds = new Rectangle();
    }

	public TexturedPolygon(List<Vector> vertexList) {
        super(vertexList);
        textureBounds = new Rectangle();
    }
	
	public TexturedPolygon(Polygon poly) {
		super(poly);
		if (poly instanceof TexturedPolygon) {
			TexturedPolygon tPoly = (TexturedPolygon) poly;
			textureBounds.setTo(tPoly.textureBounds);
			texture = tPoly.texture;
		}
	}

	public void setTo(Polygon poly) {
		super.setTo(poly);
		if (poly instanceof TexturedPolygon) {
			TexturedPolygon tPoly = (TexturedPolygon) poly;
			textureBounds.setTo(tPoly.textureBounds);
			texture = tPoly.texture;
		}
	}

	public Texture getTexture() {
		return texture;
	}

	public Rectangle getTextureBounds() {
		return textureBounds;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
		textureBounds.setWidth(texture.getWidth());
		textureBounds.setHeight(texture.getHeight());
	}

	public void setTexture(Texture texture, Rectangle bounds) {
		setTexture(texture);
		textureBounds.setTo(bounds);
	}

	public void add(Vector u) {
		super.add(u);
		textureBounds.add(u);
	}

	public void subtract(Vector u) {
		super.subtract(u);
		textureBounds.subtract(u);
	}

	public void addRotation(Transform tform) {
		super.addRotation(tform);
		textureBounds.addRotation(tform);
	}

	public void subtractRotation(Transform tform) {
		super.subtractRotation(tform);
		textureBounds.subtractRotation(tform);
	}

	public Rectangle calcBoundingRectangle() {

		Vector temp1 = new Vector(textureBounds.getDirectionU());
		Vector temp2 = new Vector(textureBounds.getDirectionV());
		Vector d = new Vector();
		Vector u = new Vector(temp1.normalize());
		Vector v = new Vector(temp2.normalize());

		float uMin = 0;
		float uMax = 0;
		float vMin = 0;
		float vMax = 0;
		for (int i = 0; i < getNumVertices(); i++) {
			d.setTo(getVertex(i));
			d.minus(getVertex(0));
			float uLength = d.dotProduct(u);
			float vLength = d.dotProduct(v);
			uMin = MathUtil.min(uLength, uMin);
			uMax = MathUtil.max(uLength, uMax);
			vMin = MathUtil.min(vLength, vMin);
			vMax = MathUtil.max(vLength, vMax);
		}

		Rectangle boundingRect = new Rectangle();
		Vector origin = boundingRect.getOrigin();
		origin.setTo(getVertex(0));
		temp1.setTo(u);
		d.setTo(temp1.multiply(uMin));
		origin.sum(d);
		temp1.setTo(v);
		d.setTo(temp1.multiply(vMin));
		origin.sum(d);
		boundingRect.getDirectionU().setTo(u);
		boundingRect.getDirectionV().setTo(v);
		boundingRect.setWidth(uMax - uMin);
		boundingRect.setHeight(vMax - vMin);

		boundingRect.setNormal(getNormal());

		return boundingRect;
	}
}
