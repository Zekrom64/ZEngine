package com.zekrom_64.ze.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.ARBClearBufferObject;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPolygonOffsetClamp;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL41;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL45;
import org.lwjgl.opengl.GLCapabilities;

/** Contains flags and mappings for OpenGL functionality beyond OpenGL 2.2
 * 
 * @author Zekrom_64
 *
 */
public class GLExtensions {

	public final GLCapabilities caps;
	
	public GLExtensions(GLCapabilities caps) {
		this.caps = caps;
		
		framebufferObjects = caps.OpenGL30 || caps.GL_ARB_framebuffer_object || caps.GL_EXT_framebuffer_object;
		framebufferBlit = caps.OpenGL30 || caps.GL_EXT_framebuffer_blit;
		framebufferTextureLayer = caps.OpenGL30;
		
		generateMipmap = caps.OpenGL30 || caps.GL_EXT_framebuffer_object;
		
		generateTextureMipmap = caps.OpenGL45;
		
		polygonOffsetClamp = caps.GL_EXT_polygon_offset_clamp;
		
		viewportArray = caps.OpenGL41 || caps.GL_ARB_viewport_array;
		
		depthRangeArray = caps.OpenGL41;
		
		namedBufferSubData = caps.OpenGL45;
		
		textureSubImage = caps.OpenGL45;
		
		clearTexSubImage = caps.OpenGL44 || caps.GL_ARB_clear_texture;
		
		clearBuffer = caps.OpenGL30;
		
		clearNamedBufferSubData = caps.OpenGL45 || caps.GL_ARB_clear_buffer_object;
		
		clearBufferSubData = caps.OpenGL43 || caps.GL_ARB_clear_buffer_object;
		
		mapNamedBuffer = caps.OpenGL45;
		
		copyImageSubData = caps.OpenGL43 || caps.GL_ARB_copy_image || caps.GL_NV_copy_image;
		
		copyBufferSubData = caps.OpenGL31 || caps.GL_ARB_copy_buffer;
		
		copyNamedBufferSubData = caps.OpenGL45;
		
		programInterfaceQuery = caps.OpenGL43 || caps.GL_ARB_program_interface_query;
		
		vertexBindingDivisor = caps.OpenGL43;
		
		vertexAttribBinding = caps.OpenGL43;
	}
	
	// Framebuffers
	
	public final boolean framebufferObjects;
	
	public void glBindFramebuffer(int target, int framebuffer) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glBindFramebuffer(target, framebuffer);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glBindFramebufferEXT(target, framebuffer);
		else throw new GLException("glBindFramebuffer is not supported");
	}
	
	public void glDeleteFramebuffers(int framebuffer) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glDeleteFramebuffers(framebuffer);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffer);
		else throw new GLException("glDeleteFramebuffers is not supported");
	}
	
	public int glGenFramebuffers() {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) return GL30.glGenFramebuffers();
		else if (caps.GL_EXT_framebuffer_object) return EXTFramebufferObject.glGenFramebuffersEXT();
		else throw new GLException("glGenFramebuffers is not supported");
	}
	
	public void glFramebufferTexture1D(int target, int attachment, int textarget, int texture, int level) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glFramebufferTexture1D(target, attachment, textarget, texture, level);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glFramebufferTexture1DEXT(target, attachment, textarget, texture, level);
		else throw new GLException("glFramebufferTexture1D is not supported");
	}
	
	public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
		else throw new GLException("glFramebufferTexture2D is not supported");
	}
	
	public void glFramebufferTexture3D(int target, int attachment, int textarget, int texture, int level, int zoffset) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glFramebufferTexture3DEXT(target, attachment, textarget, texture, level, zoffset);
		else throw new GLException("glFramebufferTexture3D is not supported");
	}
	
	public final boolean framebufferTextureLayer;
	
	public void glFramebufferTextureLayer(int target, int attachment, int texture, int level, int layer) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glFramebufferTextureLayer(target, attachment, texture, level, layer);
		else throw new GLException("glFramebufferTextureLayer is not supported");
	}
	
	public final boolean framebufferBlit;
	
	public void glBlitFramebuffer(int srcX0, int srcY0, int srcX1, int srcY1, int dstX0, int dstY0, int dstX1, int dstY1, int mask, int filter) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		else if (caps.GL_EXT_framebuffer_blit) EXTFramebufferBlit.glBlitFramebufferEXT(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
		else throw new GLException("glBlitFramebuffer is not supported");
	}
	
	// Generate mipmap
	
	public final boolean generateMipmap;
	
	public void glGenerateMipmap(int target) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glGenerateMipmap(target);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glGenerateMipmapEXT(target);
		else throw new GLException("glGenerateMipmap is not supported");
	}
	
	// Generate texture mipmap
	
	public final boolean generateTextureMipmap;
	
	public void glGenerateTextureMipmap(int texture) {
		if (caps.OpenGL45) GL45.glGenerateTextureMipmap(texture);
		else throw new GLException("glGenerateTextureMipmap is not supported");
	}
	
	// Polygon offset clamp
	
	public final boolean polygonOffsetClamp;
	
	public void glPolygonOffsetClamp(float factor, float units, float clamp) {
		if (caps.GL_EXT_polygon_offset_clamp) EXTPolygonOffsetClamp.glPolygonOffsetClampEXT(factor, units, clamp);
		else throw new GLException("glPolygonOffsetClamp is not supported");
	}
	
	// Viewport array
	
	public final boolean viewportArray;
	
	public void glViewportArrayv(int first, float[] v) {
		if (caps.OpenGL41 || caps.GL_ARB_viewport_array) GL41.glViewportArrayv(first, v);
		else throw new GLException("glViewportArrayv is not supported");
	}
	
	public void glViewportArrayv(int first, FloatBuffer v) {
		if (caps.OpenGL41 || caps.GL_ARB_viewport_array) GL41.glViewportArrayv(first, v);
		else throw new GLException("glViewportArrayv is not supported");
	}
	
	// Depth range array
	
	public final boolean depthRangeArray;
	
	// Named buffer sub data
	
	public final boolean namedBufferSubData;
	
	// Texture sub image
	
	public final boolean textureSubImage;
	
	// Clear tex sub image
	
	public final boolean clearTexSubImage;
	
	// Clear buffer
	
	public final boolean clearBuffer;
	
	// Clear named buffer sub data
	
	public final boolean clearNamedBufferSubData;
	
	public void glClearNamedBufferSubData(int buffer, int internalformat, long offset, long size, int format, int type, ByteBuffer data) {
		if (caps.OpenGL45) GL45.glClearNamedBufferSubData(buffer, internalformat, offset, size, format, type, data);
		else if (caps.GL_ARB_clear_buffer_object) ARBClearBufferObject.glClearNamedBufferSubDataEXT(buffer, internalformat, offset, size, format, type, data);
		else throw new GLException("glClearNamedBufferSubData is not supported");
	}
	
	// Clear buffer sub data
	
	public final boolean clearBufferSubData;
	
	public void glClearBufferSubData(int target, int internalformat, long offset, long size, int format, int type, ByteBuffer data) {
		if (caps.OpenGL43 || caps.GL_ARB_clear_buffer_object) GL43.glClearBufferSubData(target, internalformat, offset, size, format, type, data);
		else throw new GLException("glClearBufferSubData is not supported");
	}
	
	// Map named buffer
	
	public final boolean mapNamedBuffer;
	
	// Copy image sub data
	
	public final boolean copyImageSubData;
	
	public void glCopyImageSubData(int srcName, int srcTarget, int srcLevel, int srcX, int srcY, int srcZ,
			int dstName, int dstTarget, int dstLevel, int dstX, int dstY, int dstZ,
			int srcWidth, int srcHeight, int srcDepth) {
		if (caps.OpenGL43 || caps.GL_ARB_copy_image) GL43.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
		else if (caps.GL_NV_copy_image) GL43.glCopyImageSubData(srcName, srcTarget, srcLevel, srcX, srcY, srcZ, dstName, dstTarget, dstLevel, dstX, dstY, dstZ, srcWidth, srcHeight, srcDepth);
		else throw new GLException("glCopyImageSubData is not supported");
	}
	
	// Copy buffer sub data
	
	public final boolean copyBufferSubData;
	
	// Copy named buffer sub data
	
	public final boolean copyNamedBufferSubData;
	
	// Program interface query
	
	public final boolean programInterfaceQuery;
	
	// Vertex binding divisor
	
	public final boolean vertexBindingDivisor;
	
	// Vertex attrib binding
	
	public final boolean vertexAttribBinding;
}
