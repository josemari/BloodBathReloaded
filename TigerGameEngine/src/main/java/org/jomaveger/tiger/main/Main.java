package org.jomaveger.tiger.main;

import org.jomaveger.tiger.architecture.Engine;
import org.jomaveger.tiger.main.example2.ExampleScene;

public class Main {
    
    public static void main(String[] args) {
//        Engine tiger = new Engine("Tiger", new ExampleScene());
    	
    	Engine tiger = new Engine("Tiger", 640, 480, new ExampleScene());
        new Thread(tiger).start();        
    }
}
