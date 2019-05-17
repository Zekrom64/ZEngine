package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bridj.Pointer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.system.MemoryUtil;

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
	
	private ZETextureMemoryLayout layout;
	
	public GLTexture(int tex, ZETextureDimension dim, ZEPixelFormat pixelFormat) {
		this.texID = tex;
		pxfmt = pixelFormat;
		glFormat = GLValues.getGLTextureFormat(pixelFormat);
		glType = GLValues.getGLTextureType(pixelFormat);
		
		ctx = GLNativeContext.getNativeContext();
		int target = GLValues.getGLTextureTarget(dim);
		ctx.bindExclusively();
		try {
			GL11.glBindTexture(target, tex);
			width = GL11.glGetTexLevelParameteri(target, 0, GL11.GL_TEXTURE_WIDTH);
			height = GL11.glGetTexLevelParameteri(target, 0, GL11.GL_TEXTURE_HEIGHT);
			depth = GL11.glGetTexLevelParameteri(target, 0, GL12.GL_TEXTURE_DEPTH);
		} finally {
			ctx.unbindExclusively();
		}
		
		layout = new ZETextureMemoryLayout(
				0,
				pxfmt.sizeOf * width * (height != 0 ? height : 1) * (depth != 0 ? depth : 1),
				pxfmt.sizeOf * width,
				pxfmt.sizeOf * width * height,
				pxfmt.sizeOf * width * height
		);
	}
	
	public GLTexture(int tex, ZETextureDimension dim, int w, int h, int d, ZEPixelFormat pixelFormat) {
		texID = tex;
		ctx = GLNativeContext.getNativeContext();
		width = w;
		height = h;
		depth = d;
		dimension = dim;
		pxfmt = pixelFormat;
		glFormat = GLValues.getGLTextureFormat(pixelFormat);
		glType = GLValues.getGLTextureType(pixelFormat);
		ctx.bindExclusively();
		try {
			GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
			glTexImage(0, w, h, d, 0);
		} finally {
			ctx.unbindExclusively();
		}
	}
	
	private static int _glGenTextures() {
		GLNativeContext ctx = GLNativeContext.getNativeContext();
		ctx.bindExclusively();
		try {
			return GL11.glGenTextures();
		} finally {
			ctx.unbindExclusively();
		}
	}
	
	public GLTexture(int w, int h, int d, ZETextureDimension dim, ZEPixelFormat pixelFormat) {
		this(_glGenTextures(), dim, w, h, d, pixelFormat);
	}
	
	private static int[] cubeFaceTargets = new int[] {
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
	};
	
	void glTexImage(int mipmap, int w, int h, int d, long data) {
		int target = GLValues.getGLTextureTarget(dimension);
		if (target != -1) GL11.glBindTexture(target, texID);
		switch(dimension) {
		case CUBE: {
			target = cubeFaceTargets[d];
			GL11.glTexImage2D(target, mipmap, glFormat, w, h, 0, glFormat, glType, data);
		} break;
		case DIM_1D: GL11.glTexImage1D(target, mipmap, glFormat, w, 0, glFormat, glType, data); break;
		case DIM_1D_ARRAY:
		case DIM_2D: GL11.glTexImage2D(target, mipmap, glFormat, w, h, 0, glFormat, glType, data); break;
		case DIM_2D_ARRAY:
		case DIM_3D: GL12.glTexImage3D(target, mipmap, glFormat, w, h, d, 0, glFormat, glType, data); break;
		case CUBE_ARRAY: {
			int cubeFace = d % 6;
			d /= 6;
			target = cubeFaceTargets[cubeFace];
			GL12.glTexImage3D(target, mipmap, glFormat, w, h, d, 0, glFormat, glType, 0);
		} break;
		}
	}

	private Lock mapLock = new ReentrantLock();
	
	@Override
	public ByteBuffer mapMemory(ZEMapMode mode) {
		mapLock.lock();
		try {
			if (mappingMode == null) map(mode);
			return mappedMem;
		} finally {
			mapLock.unlock();
		}
	}

	@Override
	public Pointer<?> mapMemoryRaw(ZEMapMode mode) {
		mapLock.lock();
		try {
			if (mappingMode == null) map(mode);
			return mappedPtr;
		} finally {
			mapLock.unlock();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void map(ZEMapMode mode) {
		mappingMode = mode;
		ctx.executeExclusivly(() -> {
			if (pixelBuf != 0) pixelBuf = GL15.glGenBuffers();
			int target = GLValues.getGLTextureTarget(dimension);
			switch(mode) {
			case READ_ONLY:
			case READ_WRITE:
				GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
				GL11.glBindTexture(target, texID);
				GL11.glGetTexImage(target, 0, glFormat, glType, 0);
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY);
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
				break;
			case WRITE_ONLY:
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, GL15.GL_WRITE_ONLY);
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
				break;
			}
		});
	}

	@Override
	public void unmapMemory() {
		mapLock.lock();
		try {
			if (mappingMode != null) {
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureTarget(dimension);
					switch(mappingMode) {
					case READ_ONLY:
						GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
						GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
						GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
						mappedMem = null;
						mappedPtr = null;
						break;
					case WRITE_ONLY:
					case READ_WRITE:
						GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
						GL15.glUnmapBuffer(GL21.GL_PIXEL_UNPACK_BUFFER);
						GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
						GL11.glBindTexture(target, texID);
						GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, glFormat, width, height, 0, glFormat, glType, 0);
						mappedMem = null;
						mappedPtr = null;
						break;
					}
				});
				mappingMode = null;
			}
		} finally {
			mapLock.unlock();
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

	@Override
	public ZETextureMemoryLayout getMemoryLayout() {
		return layout;
	}

	@Override
	public long size() {
		return layout.size;
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

	@Override
	public ZETextureUsage[] getValidUsages() {
		return new ZETextureUsage[] {
			ZETextureUsage.TRANSFER_SRC,
			ZETextureUsage.TRANSFER_DST,
			ZETextureUsage.SAMPLED,
			ZETextureUsage.STORAGE,
			ZETextureUsage.COLOR_ATTACHMENT,
			ZETextureUsage.DEPTH_STENCIL_ATTACHMENT,
			ZETextureUsage.INPUT_ATTACHMENT
		};
	}
	
}
