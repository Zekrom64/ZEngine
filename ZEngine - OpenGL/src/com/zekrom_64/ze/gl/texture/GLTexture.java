package com.zekrom_64.ze.gl.texture;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import com.zekrom_64.ze.base.image.ZEImage;

/** Class representing an OpenGL texture object
 * 
 * @author Zekrom_64
 *
 */
public class GLTexture {
	
	public final int id;
	
	public GLTexture() {
		id = GL11.glGenTextures();
	}
	
	public GLTexture(int texId) {
		id = texId;
	}
	
	public GLTexture(ZEImage image) {
		this();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, image.width, image.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image.buffer);
		if (GL.getCapabilities().GL_ARB_framebuffer_object) GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		else GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
	}
	
}
