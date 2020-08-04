package org.jomaveger.tiger.main;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.architecture.IGameEngine;
import org.jomaveger.tiger.main.example1.DummyGameLogic;

public class Main {
    
    public static void main(String[] args) {
//        Engine tiger = new Engine("Tiger", new ExampleScene());
    	
//    	IGameEngine tiger = new GameEngine("Tiger Game Engine", 640, 480, new DummyGameLogic());
    	IGameEngine tiger = new GameEngine("Tiger Game Engine", new DummyGameLogic());
        tiger.start();        
    }
}
