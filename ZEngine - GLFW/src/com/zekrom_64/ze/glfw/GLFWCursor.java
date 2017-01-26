package com.zekrom_64.ze.glfw;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import com.zekrom_64.ze.base.image.ZEImage;

/** Object representing a native GLFW cursor.
 * 
 * @author Zekrom_64
 *
 */
public class GLFWCursor {

	public final long glfwcursor;
	public final int xhotspot, yhotspot;
	
	public GLFWCursor(GLFWImage image) {
		this(image, 0, 0);
	}
	
	public GLFWCursor(ZEImage image) {
		this(image, 0, 0);
	}
	
	public GLFWCursor(GLFWImage image, int xhotspot, int yhotspot) {
		glfwcursor = GLFW.glfwCreateCursor(image, xhotspot, yhotspot);
		this.xhotspot = xhotspot;
		this.yhotspot = yhotspot;
	}
	
	public GLFWCursor(ZEImage image, int xhotspot, int yhotspot) {
		this(GLFWU.toGLFWImage(image), xhotspot, yhotspot);
	}
	
	@Override
	protected void finalize() {
		GLFW.glfwDestroyCursor(glfwcursor);
	}
	
}
