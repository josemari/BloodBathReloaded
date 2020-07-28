package org.jomaveger.tiger.main.example1;

import java.awt.Color;

import org.jomaveger.tiger.architecture.Scene;

public class ExampleScene extends Scene {

	@Override
	public void start() {
		super.start();
	}
	
	@Override
	public void render() {
		super.render();
		for (int i = 0; i < getFramebuffer().getWidth() * getFramebuffer().getHeight(); i++) {
            getFramebuffer().setPixel(i, 0, Color.BLUE);
        }
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
}
