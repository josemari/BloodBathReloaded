package org.jomaveger.tge.main;

import org.jomaveger.tge.architecture.GameEngine;
import org.jomaveger.tge.main.chapter1.Chapter1Example1;
import org.jomaveger.tge.main.chapter2.Chapter2Example1;
import org.jomaveger.tge.main.chapter2.Chapter2Example2;
import org.jomaveger.tge.main.chapter3.Chapter3Example1;
import org.jomaveger.tge.main.chapter3.Chapter3Example2;
import org.jomaveger.tge.main.chapter3.Chapter3Example3;
import org.jomaveger.tge.math.Quaternion;

public class Main {
    
    public static void main(String[] args) {
//    	GameEngine tigerCh1Ex1 = new Chapter1Example1("Tiger Game Engine", 640, 480);
//    	tigerCh1Ex1.start();
//        
//        GameEngine tigerCh2Ex1 = new Chapter2Example1("Tiger Game Engine");
//        tigerCh2Ex1.start();
        
//        GameEngine tigerCh2Ex2 = new Chapter2Example2("Tiger Game Engine", 640, 480);
//        tigerCh2Ex2.start();
        
//        GameEngine tigerCh3Ex1 = new Chapter3Example1("Tiger Game Engine", 640, 480);
//        tigerCh3Ex1.start();
        
//        GameEngine tigerCh3Ex2 = new Chapter3Example2("Tiger Game Engine", 640, 480);
//        tigerCh3Ex2.start();
        
        GameEngine tigerCh3Ex3 = new Chapter3Example3("Tiger Game Engine", 640, 480);
        tigerCh3Ex3.start();
    	    	
//    	Matrix mat = new Matrix();
//    	mat.m11 = 0.0f;
//    	mat.m12 = 0.0f;
//    	mat.m13 = 0.0f;
//    	mat.m14 = 1.0f;
//    	
//    	mat.m21 = 1.0f;
//    	mat.m22 = 1.0f;
//    	mat.m23 = 1.0f;
//    	mat.m24 = 1.0f;
//    	
//    	mat.m31 = 0.0f;
//    	mat.m32 = -1.0f;
//    	mat.m33 = 0.0f;
//    	mat.m34 = -1.0f;
//    	
//    	mat.m41 = 1.0f;
//    	mat.m42 = 0.0f;
//    	mat.m43 = 0.0f;
//    	mat.m44 = 0.0f;
//    	
//    	Matrix inverse = mat.inverse();
//    	
//    	System.out.println(inverse);
    	
    	/**
    	 * El resultado debe ser:
    	 * Matrix{m11=0.0, m12=0.0, m13=0.0, m14=1.0, 
 m21=-1.0, m22=0.0, m23=-1.0, m24=0.0, 
 m31=0.0, m32=1.0, m33=1.0, m34=-1.0, 
 m41=1.0, m42=0.0, m43=0.0, m44=0.0}

    	 */
    	
//    	Quaternion quat = new Quaternion(5, 2, -3, 1);
//    	Quaternion mul = quat.multiply(new Quaternion(2, -1, 6, 8));
//    	System.out.println(mul);
    	
    	/**
    	 * El resultado debe ser:
    	 * 
    	 * Quaternion{w=22.0, x=-31.0, y=7.0, z=51.0}
    	 */
    	
//    	Vector n = new Vector();
//    	n.x = 0;
//    	n.y = 0;
//    	n.z = 1;
//    	Vector p = new Vector();
//    	Plane plane = new Plane(n, p);
//    	
//    	Vector linePoint = new Vector(); linePoint.x = 5; linePoint.y = 5; linePoint.z = 5;
//    	Vector lineDirection = new Vector(); lineDirection.x = 0; lineDirection.y = 0; lineDirection.z = 10;
//    	
//    	IntersectionResolution lineIntersection = plane.lineIntersection(linePoint, lineDirection);
//    	System.out.println(lineIntersection.condition);
//    	System.out.println(lineIntersection.intersectionPoint);
    	
    	/**
    	 * El resultado debe ser
    	 * LINE_INTERSECT_OUT_SEGMENT
Vector{x=5.0, y=5.0, z=0.0}
    	 */
    	
//    	Quaternion.slerp(new Quaternion(0, 1, 2, 3), new Quaternion(0, -4, -5, -6), 0.3f);
    }
}
