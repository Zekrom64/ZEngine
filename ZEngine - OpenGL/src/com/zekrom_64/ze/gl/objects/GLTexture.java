package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;

import org.bridj.Pointer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.gl.GLNativeContext;
import com.zekrom_64.ze.gl.GLValues;

/** OpenGL implementation of a {@link ZETexture}. Mapping the texture results in different operations based on the
 * mapping mode. If the mapping mode is read only, a pixel buffer unpacks pixels from the texture into a buffer object.
 * If the mapping is write only, a pixel buffer packs pixels into the texture on unmapping. If the mapping mode is
 * read-write, a buffer is allocated off-heap and filled with the texture data. The buffer is then re-uploaded to the
 * texture and freed when unmapped. If the texture is a buffer texture, it is accessed through its buffer object.
 * 
 * @author Zekrom_64
 *
 */
public class GLTexture implements ZETexture {
	
	/** The OpenGL texture ID */
	public final int texID;
	private GLNativeContext ctx;
	private int width, height, depth;
	private ZEMapMode mappingMode = null;
	private ByteBuffer mappedMem;
	private Pointer<?> mappedPtr;
	private int pixelBuf;
	private ZEPixelFormat pxfmt;
	private int glFormat, glType;
	private ZETextureDimension dimension;
	private GLBuffer bufferTex;
	
	public GLTexture(int tex, ZETextureDimension dim, ZEPixelFormat pixelFormat) {
		this.texID = tex;
		ctx = GLNativeContext.getNativeContext();
		int target = GLValues.getGLTextureTarget(dim);
		GL11.glBindTexture(target, tex);
		width = GL11.glGetTexLevelParameteri(target, 0, GL11.GL_TEXTURE_WIDTH);
		height = GL11.glGetTexLevelParameteri(target, 0, GL11.GL_TEXTURE_HEIGHT);
		depth = GL11.glGetTexLevelParameteri(target, 0, GL12.GL_TEXTURE_DEPTH);
		pxfmt = pixelFormat;
		glFormat = GLValues.getGLTextureFormat(pixelFormat);
		glType = GLValues.getGLTextureType(pixelFormat);
	}
	
	public GLTexture(int tex, ZETextureDimension dim, int w, int h, int d, ZEPixelFormat pixelFormat) {
		texID = tex;
		ctx = GLNativeContext.getNativeContext();
		width = w;
		height = h;
		int layerCount = 1;
		if (dim.isCubemap) layerCount *= 6;
		if (dim.isArray) {
			layerCount *= d;
			d = 0;
		}
		depth = d;
		dimension = dim;
		pxfmt = pixelFormat;
		glFormat = GLValues.getGLTextureFormat(pixelFormat);
		glType = GLValues.getGLTextureType(pixelFormat);
		GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
		for(int i = 0; i < layerCount; i++) glTexImage(0, i, w, h, d, 0);
	}
	
	public GLTexture(int w, int h, int d, ZETextureDimension dim, ZEPixelFormat pixelFormat) {
		this(GL11.glGenTextures(), dim, w, h, d, pixelFormat);
	}
	
	private static int[] cubeFaceTargets = new int[] {
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
	};
	
	public void glTexImage(int mipmap, int layer, int w, int h, int d, long data) {
		int target = GLValues.getGLTextureTarget(dimension);
		if (target != -1) GL11.glBindTexture(target, texID);
		switch(dimension) {
		case BUFFER_TEXTURE:
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferTex.bufferObject);
			GL15.nglBufferData(GL15.GL_ARRAY_BUFFER, pxfmt.sizeOf*w*h*d, data, GL15.GL_DYNAMIC_DRAW);
			break;
		case CUBE: {
			int cubeFace = layer % 6;
			layer /= 6;
			target = cubeFaceTargets[cubeFace];
			GL11.glTexImage2D(target, layer, glFormat, w, h, 0, glFormat, glType, data);
		} break;
		case DIM_1D: GL11.glTexImage1D(target, layer, glFormat, w, 0, glFormat, glType, data); break;
		case RECTANGLE:
		case DIM_2D: GL11.glTexImage2D(target, layer, glFormat, w, h, 0, glFormat, glType, data); break;
		case DIM_3D: GL12.glTexImage3D(target, layer, glFormat, w, h, d, 0, glFormat, glType, data); break;
		case DIM_1D_ARRAY:
		case DIM_2D_ARRAY:
		case CUBE_ARRAY: {
			int cubeFace = layer % 6;
			layer /= 6;
			target = cubeFaceTargets[cubeFace];
			GL12.glTexImage3D(target, layer, glFormat, w, h, d, 0, glFormat, glType, 0);
		} break;
		}
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
		if (mappingMode != null) {
		ctx.executeExclusivly(() -> {
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
		});
		}
		
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

	@Override
	public int getDepth() {
		return depth;
	}

	@Override
	public ZETextureDimension getDimension() {
		return dimension;
	}
	
}
