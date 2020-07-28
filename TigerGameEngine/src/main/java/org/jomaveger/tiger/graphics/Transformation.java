package org.jomaveger.tiger.graphics;

import org.jomaveger.tiger.math.MathUtil;

public final class Transformation {

	private Transformation() {		
	}
	
//	public final static Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) { 
//		float aspectRatio = width / height;
//        return new Matrix4f().identity().perspective(fov, aspectRatio, zNear, zFar);
//    }
//    
//    public final static Matrix4f getWorldMatrix(Vector3f translation, Vector3f rotation, float scale) {
//        return new Matrix4f().identity().translate(translation.x, translation.y, translation.z).
//                rotateX(MathUtil.toRadians(rotation.x)).
//                rotateY(MathUtil.toRadians(rotation.y)).
//                rotateZ(MathUtil.toRadians(rotation.z)).
//                scale(scale);
//    }
}
