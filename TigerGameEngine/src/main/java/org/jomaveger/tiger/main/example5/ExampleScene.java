package org.jomaveger.tiger.main.example5;

import org.jomaveger.tiger.architecture.Scene;

public class ExampleScene extends Scene {
	
	private GameObject1 gameObject1;
	
	@Override
	public void load() {
		gameObject1 = new GameObject1();
		addGameObject(gameObject1);
	}
}
