package com.zekrom_64.ze.gl;

import org.lwjgl.opengl.ARBImaging;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL45;

import com.zekrom_64.ze.base.backend.render.ZERenderException;

/** The GLException class provides an exception based wrapper for OpenGL errors.
 * 
 * @author Zekrom_64
 *
 */
public class GLException extends ZERenderException {

	private static final long serialVersionUID = -6189298409868460809L;

	public static String getGLError(int err) {
		switch(err) {
		case GL11.GL_INVALID_ENUM: return "Invalid enumeration value";
		case GL11.GL_INVALID_VALUE: return "Invalid value parameter";
		case GL11.GL_INVALID_OPERATION: return "Invalid operation performed";
		case GL11.GL_STACK_UNDERFLOW: return "Stack underflow";
		case GL11.GL_STACK_OVERFLOW: return "Stack overflow";
		case GL11.GL_OUT_OF_MEMORY: return "Out of memory";
		case GL30.GL_INVALID_FRAMEBUFFER_OPERATION: return "Invalid operation performed on framebuffer";
		case GL45.GL_CONTEXT_LOST: return "OpenGL context has been lost";
		case ARBImaging.GL_TABLE_TOO_LARGE: return "Maximum table size exceeded";
		}
		return "Unknown OpenGL error";
	}
	
	public GLException(String msg) {
		super(msg);
	}
	
	public GLException(int err) {
		super(getGLError(err));
	}
	
	public GLException(String msg, int err) {
		super(msg + getGLError(err));
	}
	
	public GLException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
