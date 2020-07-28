package org.jomaveger.tiger.main.example5;

import org.jomaveger.tiger.architecture.GameObject;

public class GameObject1 extends GameObject {
	
//	private float[] positions = new float[] {
//	      -0.5f,  0.5f,  0.5f,
//            -0.5f, -0.5f,  0.5f,
//             0.5f, -0.5f,  0.5f,
//             0.5f,  0.5f,  0.5f,
//	    };
//	    
//	private int[] indices = new int[]{
//	       0, 1, 3, 3, 1, 2
//	    };
//	    
//	private float[] colours = new float[] {
//	    	  0.5f, 0.0f, 0.0f,
//            0.0f, 0.5f, 0.0f,
//            0.0f, 0.0f, 0.5f,
//            0.0f, 0.5f, 0.5f,
//	   	};
//	
//	private Mesh mesh;
//	
//	private Shader shader = null;
//	
//	private int displxInc = 0;
//
//    private int displyInc = 0;
//
//    private int displzInc = 0;
//
//    private int scaleInc = 0;
//    
//    private static final float FOV = MathUtil.toRadians(60.0f);
//
//    private static final float Z_NEAR = 0.01f;
//
//    private static final float Z_FAR = 1000.0f;
//		
//	@Override
//	public void create() {
//		mesh = new Mesh(positions, colours, indices);
//		shader = new Shader();
//		shader.createVertexShader(ResourceUtil.loadResource("/shaders/vertex2.vert"));
//		shader.createFragmentShader(ResourceUtil.loadResource("/shaders/fragment.frag"));
//		shader.link();
//		
//		this.getTransform().setPosition(0, 0, -2);
//	}
//	
//	@Override
//	public void input(KeyInputManager keyInputManager, MouseInputManager mouseInputManager) {
//		displyInc = 0;
//        displxInc = 0;
//        displzInc = 0;
//        scaleInc = 0;
//        if (keyInputManager.isKeyDown(GLFW_KEY_UP)) {
//            displyInc = 1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_DOWN)) {
//            displyInc = -1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_LEFT)) {
//            displxInc = -1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_RIGHT)) {
//            displxInc = 1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_A)) {
//            displzInc = -1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_Q)) {
//            displzInc = 1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_Z)) {
//            scaleInc = -1;
//        } else if (keyInputManager.isKeyDown(GLFW_KEY_X)) {
//            scaleInc = 1;
//        }
//	}
//	
//	@Override
//	public void update(float interval) {
//		Vector3f itemPos = this.getTransform().getPosition();
//        float posx = itemPos.x + displxInc * 0.01f;
//        float posy = itemPos.y + displyInc * 0.01f;
//        float posz = itemPos.z + displzInc * 0.01f;
//        this.getTransform().setPosition(posx, posy, posz);
//      
//        float scale = this.getTransform().getScale();
//        scale += scaleInc * 0.05f;
//        if ( scale < 0 ) {
//            scale = 0;
//        }
//        this.getTransform().setScale(scale);
//        
//        float rotation = this.getTransform().getRotation().z + 1.5f;
//        if ( rotation > 360 ) {
//            rotation = 0;
//        }
//        this.getTransform().setRotation(0, 0, rotation);
//	}
//	
//	@Override
//	public void render() {
//		shader.bind();
//		shader.createUniform("worldMatrix");
//		
//		shader.createUniform("projectionMatrix");
//                
//        Matrix4f projectionMatrix = Transformation.getProjectionMatrix(FOV, getScene().getEngine().getWindow().getWidth(), 
//        													getScene().getEngine().getWindow().getHeight(), Z_NEAR, Z_FAR);
//        shader.setUniform("projectionMatrix", projectionMatrix);
//        
//        Matrix4f worldMatrix = Transformation.getWorldMatrix(
//        		this.getTransform().getPosition(),
//        		this.getTransform().getRotation(),
//        		this.getTransform().getScale());        
//        shader.setUniform("worldMatrix", worldMatrix);
//        
//        mesh.render();
//        
//        shader.unbind();
//	}
//
//	@Override
//	public void dispose() {
//		super.dispose();
//		mesh.dispose();
//	}
}
