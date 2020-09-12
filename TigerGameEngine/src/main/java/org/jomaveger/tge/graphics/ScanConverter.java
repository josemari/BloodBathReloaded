package org.jomaveger.tge.graphics;

import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tge.math.Polygon;
import org.jomaveger.tge.math.Vector;

public class ScanConverter {
	
    protected ViewWindow view;
    protected Scan[] scans;
    protected int top;
    protected int bottom;

    public static class Scan {

    	public int left;
        public int right;

        public void setBoundary(int x) {
            if (x < left) {
                left = x;
            }
            if (x-1 > right) {
                right = x-1;
            }
        }

        public void clear() {
            left = Integer.MAX_VALUE;
            right = Integer.MIN_VALUE;
        }

        public boolean isValid() {
            return (left <= right);
        }

        public void setTo(int left, int right) {
            this.left = left;
            this.right = right;
        }

        public boolean equals(int left, int right) {
            return (this.left == left && this.right == right);
        }
    }

    public ScanConverter(ViewWindow view) {
        this.view = view;
    }

    public int getTopBoundary() {
        return top;
    }

    public int getBottomBoundary() {
        return bottom;
    }

    public Scan getScan(int y) {
        return scans[y];
    }

    protected void ensureCapacity() {
        int height = view.getTopOffset() + view.getHeight();
        if (scans == null || scans.length != height) {
            scans = new Scan[height];
            for (int i=0; i<height; i++) {
                scans[i] = new Scan();
            }
            top = 0;
            bottom = height - 1;
        }
    }

    private void clearCurrentScan() {
        for (int i=top; i<=bottom; i++) {
            scans[i].clear();
        }
        top = Integer.MAX_VALUE;
        bottom = Integer.MIN_VALUE;
    }

    public boolean convert(Polygon polygon) {

        ensureCapacity();
        clearCurrentScan();

        int minX = view.getLeftOffset();
        int maxX = view.getLeftOffset() + view.getWidth() - 1;
        int minY = view.getTopOffset();
        int maxY = view.getTopOffset() + view.getHeight() - 1;

        int numVertices = polygon.getNumVertices();
        for (int i=0; i<numVertices; i++) {
            Vector v1 = polygon.getVertex(i);
            Vector v2;
            if (i == numVertices - 1) {
                v2 = polygon.getVertex(0);
            }
            else {
                v2 = polygon.getVertex(i+1);
            }

            if (v1.y > v2.y) {
                Vector temp = v1;
                v1 = v2;
                v2 = temp;
            }
            float dy = v2.y - v1.y;

            if (dy == 0) {
                continue;
            }

            int startY = MathUtil.max(MathUtil.ceil(v1.y), minY);
            int endY = MathUtil.min(MathUtil.ceil(v2.y)-1, maxY);
            top = MathUtil.min(top, startY);
            bottom = MathUtil.max(bottom, endY);
            float dx = v2.x - v1.x;

            if (dx == 0) {
                int x = MathUtil.ceil(v1.x);
                x = MathUtil.min(maxX+1, Math.max(x, minX));
                for (int y=startY; y<=endY; y++) {
                    scans[y].setBoundary(x);
                }
            }
            else {
                
                float gradient = dx / dy;
                
                for (int y=startY; y<=endY; y++) {
                    int x = MathUtil.ceil(v1.x + (y - v1.y) * gradient);
                    x = MathUtil.min(maxX+1, MathUtil.max(x, minX));
                    scans[y].setBoundary(x);
                }                
            }
        }

        for (int i=top; i<=bottom; i++) {
            if (scans[i].isValid()) {
                return true;
            }
        }
        return false;
    }
}