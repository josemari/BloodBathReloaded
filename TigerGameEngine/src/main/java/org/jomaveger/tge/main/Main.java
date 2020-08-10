package org.jomaveger.tge.main;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.architecture.IGameEngine;
import org.jomaveger.tge.main.example1.DummyGameLogic;

public class Main {
    
    public static void main(String[] args) {
    	IGameEngine tiger = new GameEngine("Tiger Game Engine", 640, 480, new DummyGameLogic());
//    	IGameEngine tiger = new GameEngine("Tiger Game Engine", new DummyGameLogic());
        tiger.start();        
    }
}
