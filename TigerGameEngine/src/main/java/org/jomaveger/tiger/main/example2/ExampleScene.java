package org.jomaveger.tiger.main.example2;

import java.awt.Color;

import org.jomaveger.tge.math.MathUtil;
import org.jomaveger.tiger.architecture.Scene;

public class ExampleScene extends Scene {
	
	@Override
	public void render() {
		super.render();
		
		float r = 0.0f;
		
		// calculate coordinates for triangle
		float size = 110.0f;
		float x1 = (float)(getEngine().getWindow().getWidth() / 2) 
				+ MathUtil.cos(r - MathUtil.PI / 6.0f) * size;
		float y1 = (float)(getEngine().getWindow().getHeight() / 2) 
				+ MathUtil.sin(r - MathUtil.PI / 6.0f) * size;
		float x2 = (float)(getEngine().getWindow().getWidth() / 2) 
				+ MathUtil.cos(r + MathUtil.PI / 2.0f) * size;
		float y2 = (float)(getEngine().getWindow().getHeight() / 2) 
				+ MathUtil.sin(r + MathUtil.PI / 2.0f) * size;
		float x3 = (float)(getEngine().getWindow().getWidth() / 2) 
				+ MathUtil.cos(r + MathUtil.PI + MathUtil.PI / 6.0f) * size;
		float y3 = (float)(getEngine().getWindow().getHeight() / 2) 
				+ MathUtil.sin(r + MathUtil.PI + MathUtil.PI / 6.0f) * size;
		
		Color color1 = new Color(1.0f, 0.0f, 0.0f);
		Color color2 = new Color(0.0f, 1.0f, 0.0f);
		Color color3 = new Color(0.0f, 0.0f, 1.0f);

		// render triangle
		getFramebuffer().drawLine(x1, y1, color1, x2, y2, color2);
		getFramebuffer().drawLine(x2, y2, color2, x3, y3, color3);
		getFramebuffer().drawLine(x3, y3, color3, x1, y1, color1);
	}
}
