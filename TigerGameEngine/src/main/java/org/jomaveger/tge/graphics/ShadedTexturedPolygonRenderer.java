package org.jomaveger.tge.graphics;

import java.awt.Graphics2D;

import org.jomaveger.tge.graphics.texture.ShadedTexture;
import org.jomaveger.tge.graphics.texture.Texture;
import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.PointLight;
import org.jomaveger.tge.math.TexturedPolygon;
import org.jomaveger.tge.math.Transform;
import org.jomaveger.tge.math.Vector;

public class ShadedTexturedPolygonRenderer extends TexturedPolygonRenderer {

	private PointLight lightSource;
    private float ambientLightIntensity = 0.5f;
    private Vector directionToLight = new Vector();

    public ShadedTexturedPolygonRenderer(Transform camera, ViewWindow viewWindow) {
        this(camera, viewWindow, true);
    }

    public ShadedTexturedPolygonRenderer(Transform camera, ViewWindow viewWindow, boolean clearViewEveryFrame) {
        super(camera, viewWindow, clearViewEveryFrame);
    }

    public PointLight getLightSource() {
        return lightSource;
    }

    public void setLightSource(PointLight lightSource) {
        this.lightSource = lightSource;
    }

    public float getAmbientLightIntensity() {
        return ambientLightIntensity;
    }

    public void setAmbientLightIntensity(float i) {
        ambientLightIntensity = i;
    }

    protected void drawCurrentPolygon(Graphics2D g) {
        if (sourcePolygon instanceof TexturedPolygon) {
            TexturedPolygon poly = ((TexturedPolygon)sourcePolygon);
            Texture texture = poly.getTexture();
            if (texture instanceof ShadedTexture) {
                calcShadeLevel();
            }
        }
        super.drawCurrentPolygon(g);
    }

    private void calcShadeLevel() {
        TexturedPolygon poly = (TexturedPolygon)sourcePolygon;
        float intensity = 0;
        if (lightSource != null) {

            // average all the vertices in the polygon
            directionToLight.setTo(0,0,0);
            for (int i=0; i<poly.getNumVertices(); i++) {
                directionToLight.sum(poly.getVertex(i));
            }
            directionToLight.setTo(directionToLight.divide(poly.getNumVertices()));

            // make the vector from the average vertex
            // to the light
            directionToLight.minus(lightSource);
            directionToLight.setTo(directionToLight.multiply(-1));

            // get the distance to the light for falloff
            float distance = directionToLight.length();

            // compute the diffuse reflect
            directionToLight.setTo(directionToLight.normalize());
            Vector normal = poly.getNormal();
            intensity = lightSource.getIntensity(distance) * directionToLight.dotProduct(normal);
            intensity = MathUtil.min(intensity, 1);
            intensity = MathUtil.max(intensity, 0);
        }

        intensity+=ambientLightIntensity;
        intensity = MathUtil.min(intensity, 1);
        intensity = MathUtil.max(intensity, 0);
        int level = MathUtil.round(intensity*ShadedTexture.MAX_LEVEL);
        ((ShadedTexture)poly.getTexture()).setDefaultShadeLevel(level);
    }
}
