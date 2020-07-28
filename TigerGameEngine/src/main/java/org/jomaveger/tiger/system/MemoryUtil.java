package org.jomaveger.tiger.system;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class MemoryUtil {

	private static final int SIZE_FLOAT = 4;
	private static final int SIZE_INT = 4;

	private MemoryUtil() {
	}

	public static ByteBuffer createByteBuffer(int capacity) {
		return ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder());
	}

	public static FloatBuffer createFloatBuffer(int size) {
		return createByteBuffer(size * SIZE_FLOAT).asFloatBuffer();
	}

	public static IntBuffer createIntBuffer(int size) {
		return createByteBuffer(size * SIZE_INT).asIntBuffer();
	}
	
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buffer = createFloatBuffer(data.length);
		return write(buffer, data);
	}

	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buffer = createIntBuffer(data.length);
		return write(buffer, data);
	}
	
	private static FloatBuffer write(FloatBuffer buffer, float[] data) {
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer write(IntBuffer buffer, int[] data) {
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
