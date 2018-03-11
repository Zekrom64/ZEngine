package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.ze.base.backend.render.ZETexture;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.gl.GLNativeContext;

/** OpenGL implementation of a {@link ZETexture}. Mapping the texture results in different operations based on the
 * mapping mode. If the mapping mode is read only, a pixel buffer unpacks pixels from the texture into a buffer object.
 * If the mapping is write only, a pixel buffer packs pixels into the texture on unmapping. If the mapping mode is
 * read-write, a buffer is allocated off-heap and filled with the texture data. The buffer is then re-uploaded to the
 * texture and freed when unmapped.
 * 
 * @author Zekrom_64
 *
 */
public class GLTexture implements ZETexture {
	
	/** The OpenGL texture ID */
	public final int texID;
	private GLNativeContext ctx;
	private int width, height;
	private ZEMapMode mappingMode = null;
	private ByteBuffer mappedMem;
	private Pointer<?> mappedPtr;
	private int pixelBuf;
	private ZEPixelFormat pxfmt;
	private int glFormat, glType;
	
	public GLTexture(int tex, ZEPixelFormat pixelFormat) {
		this.texID = tex;
		ctx = GLNativeContext.getNativeContext();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
		height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
		pxfmt = pixelFormat;
		glFormat = getGLTextureFormat(pixelFormat);
		glType = getGLTextureType(pixelFormat);
	}
	
	public GLTexture(int w, int h, ZEPixelFormat pixelFormat) {
		this.texID = GL11.glGenTextures();
		ctx = GLNativeContext.getNativeContext();
		width = w;
		height = h;
		pxfmt = pixelFormat;
		glFormat = getGLTextureFormat(pixelFormat);
		glType = getGLTextureType(pixelFormat);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, w, h, 0, glFormat, glType, 0);
	}

	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		if (mappingMode == null) map(mode);
		return mappedMem;
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		if (mappingMode == null) map(mode);
		return mappedPtr;
	}
	
	@SuppressWarnings("deprecation")
	private void map(ZEMapMode mode) {
		ctx.executeExclusivly(() -> {
			switch(mode) {
			case READ_ONLY:
				if (pixelBuf != 0) pixelBuf = GL15.glGenBuffers();
				GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
				GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, glFormat, glType, 0);
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY);
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				break;
			case WRITE_ONLY:
				if (pixelBuf != 0) pixelBuf = GL15.glGenBuffers();
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, GL15.GL_WRITE_ONLY);
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				break;
			case READ_WRITE:
				ByteBuffer buf = LibCStdlib.malloc(width * height * pxfmt.sizeOf);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
				GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, glFormat, glType, buf);
				mappedMem = buf;
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(buf));
				break;
			}
		});
	}

	@Override
	public void unmapMemory() {
		ctx.executeExclusivly(() -> {
			if (mappingMode != null) {
				switch(mappingMode) {
				case READ_ONLY:
					GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
					GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
					mappedMem = null;
					mappedPtr = null;
					break;
				case WRITE_ONLY:
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, width, height, 0, glFormat, glType, 0);
					mappedMem = null;
					mappedPtr = null;
					break;
				case READ_WRITE:
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, texID);
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, width, height, 0, glFormat, glType, mappedMem);
					LibCStdlib.free(mappedMem);
					mappedMem = null;
					mappedPtr = null;
					break;
				}
			}
		});
		
	}

	@Override
	public boolean isMapped() {
		return mappingMode != null;
	}

	@Override
	public boolean isHostAccessible() {
		return true;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public ZEPixelFormat getPixelFormat() {
		return pxfmt;
	}
	
	/** Gets the OpenGL texture format for a given pixel format, returning
	 * -1 if the given format cannot be found.
	 * 
	 * @param fmt Pixel format
	 * @return OpenGL texture format
	 */
	public static int getGLTextureFormat(ZEPixelFormat fmt) {
		switch(fmt) {
		case R8G8B8A8_UINT:
		case R16G16B16A16_UINT:
		case R32G32B32A32_UINT:
		case R32G32B32A32_FLOAT: return GL11.GL_RGBA;
		case R8G8B8_UINT:
		case R16G16B16_UINT:
		case R32G32B32_UINT:
		case R32G32B32_FLOAT: return GL11.GL_RGB;
		case R8G8_UINT:
		case R16G16_UINT:
		case R32G32_UINT:
		case R32G32_FLOAT: return GL30.GL_RG;
		case R8_UINT:
		case R16_UINT:
		case R32_UINT:
		case R32_FLOAT: return GL11.GL_R;
		case A8R8G8B8_UINT: return GL12.GL_BGRA;
		default: return -1;
		}
	}
	
	/** Gets the OpenGL component type for a given pixel format, returning
	 * -1 if the given component type cannot be found.
	 * 
	 * @param fmt Pixel format
	 * @return OpenGL component type
	 */
	public static int getGLTextureType(ZEPixelFormat fmt) {
		switch(fmt) {
		case A8R8G8B8_UINT: return GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
		case R16G16B16A16_UINT:
		case R16G16B16_UINT:
		case R16G16_UINT:
		case R16_UINT: return GL11.GL_UNSIGNED_SHORT;
		case R32G32B32A32_FLOAT:
		case R32G32B32_FLOAT:
		case R32G32_FLOAT:
		case R32_FLOAT: return GL11.GL_FLOAT;
		case R32G32B32A32_UINT:
		case R32G32B32_UINT:
		case R32G32_UINT:
		case R32_UINT: return GL11.GL_UNSIGNED_INT;
		case R8G8B8A8_UINT:
		case R8G8B8_UINT:
		case R8G8_UINT:
		case R8_UINT: return GL11.GL_UNSIGNED_BYTE;
		default: return -1;
		}
	}

	// OpenGL textures (almost) always have no padding bytes
	@Override
	public int getMemoryRowPitch() {
		return pxfmt.sizeOf * width;
	}

	@Override
	public long size() {
		return height * getMemoryRowPitch();
	}
	
	/** Gets the OpenGL format of the texture.
	 * 
	 * @return OpenGL format
	 */
	public int getGLFormat() {
		return glFormat;
	}
	
	/** Gets the OpenGL component type of the texture.
	 * 
	 * @return OpenGL component type
	 */
	public int getGLType() {
		return glType;
	}
	
}
