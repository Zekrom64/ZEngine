package com.zekrom_64.ze.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.ARBBufferStorage;
import org.lwjgl.opengl.ARBClearBufferObject;
import org.lwjgl.opengl.ARBTextureStorage;
import org.lwjgl.opengl.EXTFramebufferBlit;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPolygonOffsetClamp;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.opengl.GL42;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
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
		
		drawIndirect = caps.OpenGL40 || caps.GL_ARB_draw_indirect;
		
		uniformBufferObject = caps.OpenGL31 || caps.GL_ARB_uniform_buffer_object;
		
		shaderStorageBufferObject = caps.OpenGL43 || caps.GL_ARB_shader_storage_buffer_object;
		
		shaderImageLoadStore = caps.OpenGL42 || caps.GL_ARB_shader_image_load_store || caps.GL_EXT_shader_image_load_store;
		
		textureCubeMapArray = caps.OpenGL40 || caps.GL_ARB_texture_cube_map;
		
		textureArray = caps.OpenGL30 || caps.GL_EXT_texture_array;
		
		samplerObjects = caps.OpenGL33 || caps.GL_ARB_sampler_objects;
		
		textureFilterAnisotropic = caps.GL_EXT_texture_filter_anisotropic;
		
		texStorage = caps.OpenGL42 || caps.GL_ARB_texture_storage;
		
		textureStorage = caps.OpenGL45 || caps.GL_ARB_texture_storage;
		
		bufferStorage = caps.OpenGL44 || caps.GL_ARB_buffer_storage;
		
		namedBufferStorage = caps.OpenGL45 || caps.GL_ARB_buffer_storage;
		
		drawElementsBaseVertex = caps.OpenGL32 || caps.GL_ARB_draw_elements_base_vertex;
		
		bindVertexBuffers = caps.OpenGL44 || caps.GL_ARB_multi_bind;
		
		bindVertexBuffer = caps.OpenGL43;
		
		scissorArray = caps.OpenGL41;
		
		scissorIndexed = caps.OpenGL41;
		
		bindBufferRange = caps.OpenGL30 || caps.GL_ARB_uniform_buffer_object;
		
		getTextureSubImage = caps.OpenGL45;
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
	
	public void glDeleteFramebuffers(int[] framebuffers) {
		if (caps.OpenGL30 || caps.GL_ARB_framebuffer_object) GL30.glDeleteFramebuffers(framebuffers);
		else if (caps.GL_EXT_framebuffer_object) EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffers);
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
	
	public void glTextureSubImage1D(int texture, int level, int xoffset, int width, int format, int type, long pixels) {
		if (caps.OpenGL45) GL45.glTextureSubImage1D(texture, level, xoffset, width, format, type, pixels);
		else throw new GLException("glTextureSubImage1D is not supported");
	}
	
	public void glTextureSubImage2D(int texture, int level, int xoffset, int yoffset, int width, int height, int format, int type, long pixels) {
		if (caps.OpenGL45) GL45.glTextureSubImage2D(texture, level, xoffset, yoffset, width, height, format, type, pixels);
		else throw new GLException("glTextureSubImage2D is not supported");
	}
	
	public void glTextureSubImage3D(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, long pixels) {
		if (caps.OpenGL45) GL45.glTextureSubImage3D(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
		else throw new GLException("glTextureSubImage3D is not supported");
	}
	
	// Clear tex sub image
	
	public final boolean clearTexSubImage;
	
	public void glClearTexSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer data) {
		if (caps.OpenGL44 || caps.GL_ARB_clear_texture) GL44.glClearTexSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, data);
		else throw new GLException("glClearTexSubImage is not supported");
	}
	
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
	
	public ByteBuffer glMapNamedBuffer(int buffer, int access) {
		if (caps.OpenGL45) return GL45.glMapNamedBuffer(buffer, access);
		else throw new GLException("glMapNamedBuffer is not supported");
	}
	
	public void glUnmapNamedBuffer(int buffer) {
		if (caps.OpenGL45) GL45.glUnmapNamedBuffer(buffer);
		else throw new GLException("glUnmapNamedBuffer");
	}
	
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
	
	public void glCopyBufferSubData(int readTarget, int writeTarget, long readOffset, long writeOffset, long size) {
		if (caps.OpenGL31) GL31.glCopyBufferSubData(readTarget, writeTarget, readOffset, writeOffset, size);
		else throw new GLException("glCopyBufferSubData is not supported");
	}
	
	// Copy named buffer sub data
	
	public final boolean copyNamedBufferSubData;
	
	public void glCopyNamedBufferSubData(int readBuffer, int writeBuffer, long readOffset, long writeOffset, long size) {
		if (caps.OpenGL45) GL45.glCopyNamedBufferSubData(readBuffer, writeBuffer, readOffset, writeOffset, size);
		else throw new GLException("glCopyNamedBufferSubData is not supported");
	}
	
	// Program interface query
	
	public final boolean programInterfaceQuery;
	
	// Vertex binding divisor
	
	public final boolean vertexBindingDivisor;
	
	// Vertex attrib binding
	
	public final boolean vertexAttribBinding;
	
	// Draw indirect
	
	public final boolean drawIndirect;
	
	public void glDrawArraysIndirect(int mode, ByteBuffer indirect) {
		if (caps.OpenGL40 || caps.GL_ARB_draw_indirect) GL40.glDrawArraysIndirect(mode, indirect);
		else throw new GLException("glDrawArraysIndirect is not supported");
	}
	
	public void glDrawElementsIndirect(int mode, int type, ByteBuffer indirect) {
		if (caps.OpenGL40 || caps.GL_ARB_draw_indirect) GL40.glDrawElementsIndirect(mode, type, indirect);
		else throw new GLException("glDrawElementsIndirect is not supported");
	}
	
	// Uniform buffer object
	
	public final boolean uniformBufferObject;
	
	public int glGetUniformBlockIndex(int program, String uniformBlockName) {
		if (caps.OpenGL31 || caps.GL_ARB_uniform_buffer_object) return GL31.glGetUniformBlockIndex(program, uniformBlockName);
		else throw new GLException("glGetUniformBlockIndex is not supported");
	}
	
	public void glUniformBlockBinding(int program, int uniformBlockIndex, int uniformBlockBinding) {
		if (caps.OpenGL31 || caps.GL_ARB_uniform_buffer_object) GL31.glUniformBlockBinding(program, uniformBlockIndex, uniformBlockBinding);
		else throw new GLException("glUniformBlockBinding is not supported");
	}
	
	// Shader storage buffer object
	
	public final boolean shaderStorageBufferObject;
	
	// Shader image load store
	
	public final boolean shaderImageLoadStore;
	
	// Cubemap array
	
	public final boolean textureCubeMapArray;
	
	// Texture array
	
	public final boolean textureArray;
	
	// Sampler objects
	
	public final boolean samplerObjects;
	
	public int glGenSamplers() {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) return GL33.glGenSamplers();
		else throw new GLException("glGenSamplers is not supported");
	}
	
	public void glDeleteSamplers(int sampler) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glDeleteSamplers(sampler);
		else throw new GLException("glDeleteSamplers is not supported");
	}
	
	public void glDeleteSamplers(int[] samplers) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glDeleteSamplers(samplers);
		else throw new GLException("glDeleteSamplers is not supported");
	}
	
	public void glSamplerParameteri(int sampler, int pname, int param) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glSamplerParameteri(sampler, pname, param);
		else throw new GLException("glSamplerParameteri is not supported");
	}
	
	public void glSamplerParameterf(int sampler, int pname, float param) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glSamplerParameterf(sampler, pname, param);
		else throw new GLException("glSamplerParameterf is not supported");
	}
	
	public void glSamplerParameterfv(int sampler, int pname, float[] params) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glSamplerParameterfv(sampler, pname, params);
		else throw new GLException("glSamplerParameterfv is not supported");
	}
	
	public int glGetSamplerParameteri(int sampler, int pname) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) return GL33.glGetSamplerParameteri(sampler, pname);
		else throw new GLException("glGetSamplerParameteri is not supported");
	}
	
	public float glGetSamplerParameterf(int sampler, int pname) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) return GL33.glGetSamplerParameterf(sampler, pname);
		else throw new GLException("glGetSamplerParameterf is not supported");
	}
	
	public void glGetSamplerParameterfv(int sampler, int pname, float[] params) {
		if (caps.OpenGL33 || caps.GL_ARB_sampler_objects) GL33.glGetSamplerParameterfv(sampler, pname, params);
		else throw new GLException("glGetSamplerParameterfv is not supported");
	}
	
	// Texture filter anisotropic
	
	public final boolean textureFilterAnisotropic;
	
	// Tex storage
	
	public final boolean texStorage;
	
	public void glTexStorage1D(int target, int levels, int internalformat, int width) {
		if (caps.OpenGL42 || caps.GL_ARB_texture_storage) GL42.glTexStorage1D(target, levels, internalformat, width);
		else throw new GLException("glTexStorage1D is not supported");
	}
	
	public void glTexStorage2D(int target, int levels, int internalformat, int width, int height) {
		if (caps.OpenGL42 || caps.GL_ARB_texture_storage) GL42.glTexStorage2D(target, levels, internalformat, width, height);
		else throw new GLException("glTexStorage2D is not supported");
	}
	
	public void glTexStorage3D(int target, int levels, int internalformat, int width, int height, int depth) {
		if (caps.OpenGL42 || caps.GL_ARB_texture_storage) GL42.glTexStorage3D(target, levels, internalformat, width, height, depth);
		else throw new GLException("glTexStorage3D is not supported");
	}
	
	// Texture storage
	
	public final boolean textureStorage;
	
	public void glTextureStorage1D(int texture, int target, int levels, int internalformat, int width) {
		if (caps.OpenGL42) GL45.glTextureStorage1D(texture, levels, internalformat, width);
		else if (caps.GL_ARB_texture_storage) ARBTextureStorage.glTextureStorage1DEXT(texture, target, levels, internalformat, width);
		else throw new GLException("glTexStorage1D is not supported");
	}
	
	public void glTextureStorage2D(int texture, int target, int levels, int internalformat, int width, int height) {
		if (caps.OpenGL42) GL45.glTextureStorage2D(texture, levels, internalformat, width, height);
		else if (caps.GL_ARB_texture_storage) ARBTextureStorage.glTextureStorage2DEXT(texture, target, levels, internalformat, width, height);
		else throw new GLException("glTexStorage2D is not supported");
	}
	
	public void glTextureStorage3D(int texture, int target, int levels, int internalformat, int width, int height, int depth) {
		if (caps.OpenGL42) GL45.glTextureStorage3D(texture, levels, internalformat, width, height, depth);
		else if (caps.GL_ARB_texture_storage) ARBTextureStorage.glTextureStorage3DEXT(texture, target, levels, internalformat, width, height, depth);
		else throw new GLException("glTexStorage3D is not supported");
	}
	
	// Buffer storage
	
	public final boolean bufferStorage;
	
	public void glBufferStorage(int target, long size, int flags) {
		if (caps.OpenGL44 || caps.GL_ARB_buffer_storage) GL44.glBufferStorage(target, size, flags);
		else throw new GLException("glBufferStorage is not supported");
	}
	
	// Named buffer storage
	
	public final boolean namedBufferStorage;
	
	public void glNamedBufferStorage(int buffer, long size, int flags) {
		if (caps.OpenGL44) GL45.glNamedBufferStorage(buffer, size, flags);
		else if (caps.GL_ARB_buffer_storage) ARBBufferStorage.glNamedBufferStorageEXT(buffer, size, flags);
		else throw new GLException("glNamedBufferStorage is not supported");
	}
	
	// Draw elements base vertex
	
	public final boolean drawElementsBaseVertex;
	
	public void glDrawElementsBaseVertex(int mode, int count, int type, long indices, int basevertex) {
		if (caps.OpenGL32 || caps.GL_ARB_draw_elements_base_vertex) GL32.glDrawElementsBaseVertex(mode, count, type, indices, basevertex);
		else throw new GLException("glDrawElementsBaseVertex is not supported");
	}
	
	// Bind vertex buffers
	
	public final boolean bindVertexBuffers;
	
	public void glBindVertexBuffers(int first, IntBuffer buffers, PointerBuffer offsets, IntBuffer strides) {
		if (caps.OpenGL44 || caps.GL_ARB_multi_bind) GL44.glBindVertexBuffers(first, buffers, offsets, strides);
		else throw new GLException("glBindVertexBuffers is not supported");
	}
	
	// Bind vertex buffer
	
	public final boolean bindVertexBuffer;
	
	public void glBindVertexBuffer(int bindingindex, int buffer, long offset, int stride) {
		if (caps.OpenGL43) GL43.glBindVertexBuffer(bindingindex, buffer, offset, stride);
		else throw new GLException("glBindVertexBuffer is not supported");
	}
	
	// Scissor array
	
	public final boolean scissorArray;
	
	public void glScissorArrayv(int first, IntBuffer v) {
		if (caps.OpenGL41) GL41.glScissorArrayv(first, v);
		else throw new GLException("glScissorArrayv is not supported");
	}
	
	// Scissor indexed
	
	public final boolean scissorIndexed;
	
	public void glScissorIndexed(int index, int left, int bottom, int width, int height) {
		if (caps.OpenGL41) GL41.glScissorIndexed(index, left, bottom, width, height);
		else throw new GLException("glScissorIndexed is not supported");
	}
	
	// Bind buffer range
	
	public final boolean bindBufferRange;
	
	public void glBindBufferRange(int target, int index, int buffer, long offset, long size) {
		if (caps.OpenGL30 || caps.GL_ARB_uniform_buffer_object) GL30.glBindBufferRange(target, index, buffer, offset, size);
		else throw new GLException("glBindBufferRange is not supported");
	}
	
	// Get texture sub image
	
	public final boolean getTextureSubImage;
	
	public void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, ByteBuffer pixels) {
		GL45.glGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels);
	}
	
	public void glGetTextureSubImage(int texture, int level, int xoffset, int yoffset, int zoffset, int width, int height, int depth, int format, int type, int bufSize, long pixels) {
		GL45.nglGetTextureSubImage(texture, level, xoffset, yoffset, zoffset, width, height, depth, format, type, bufSize, pixels);
	}
	
}
