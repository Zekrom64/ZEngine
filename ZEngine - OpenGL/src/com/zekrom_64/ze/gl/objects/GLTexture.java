package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.bridj.Pointer;
import org.lwjgl.opengl.EXTTextureFilterAnisotropic;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCStdlib;
import org.lwjgl.system.libc.LibCString;

import com.zekrom_64.ze.base.backend.render.obj.ZECompareOp;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEAddressingMode;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEBorderColor;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEMipmapFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZESamplerBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZESamplerSettings;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.mem.ZEMapMode;
import com.zekrom_64.ze.gl.GLExtensions;
import com.zekrom_64.ze.gl.GLNativeContext;
import com.zekrom_64.ze.gl.GLRenderBackend;
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
	public final int textureObject;
	
	private final GLNativeContext ctx;
	private final GLRenderBackend backend;
	
	private final int width, height, depth;
	private final int arrays, mipmaps;
	
	private ZEMapMode mappingMode = null;
	private ByteBuffer mappedMem = null;
	private Pointer<?> mappedPtr = null;
	private int pixelBuf = 0;
	
	private final ZEPixelFormat pxfmt;
	private final int glFormat, glType;
	private final ZETextureDimension dimension;
	
	private final ZETextureMemoryLayout layout;
	
	public GLTexture(GLRenderBackend backend, int tex, ZETextureDimension dim, int w, int h, int d, int a, int m, ZEPixelFormat pixelFormat) {
		textureObject = tex;
		
		this.backend = backend;
		ctx = backend.getNativeContext();
		
		width = w;
		height = dim.numSampleDimensions > 1 ? h : 0;
		depth = dim.numSampleDimensions > 2 ? d : 0;
		arrays = dim.isArray ? a : 0;
		mipmaps = m;
		
		dimension = dim;
		pxfmt = pixelFormat;
		layout = new ZETextureMemoryLayout(
			0,
			width * (height != 0 ? height : 1) * (depth != 0 ? depth : 1) * (dim.isCubemap ? 6 : 1) * (arrays != 0 ? arrays : 1),
			pixelFormat.sizeOf * width,
			pixelFormat.sizeOf * width * height,
			pixelFormat.sizeOf * width * (height != 0 ? height : 1)
		);
		
		glFormat = GLValues.getGLTextureFormat(pixelFormat);
		glType = GLValues.getGLTextureType(pixelFormat);
		
		GLExtensions exts = backend.getExtensions();
		ctx.bindExclusively();
		try {
			int boundTex = GL11.glGetInteger(GLValues.getGLTextureBinding(dim));
			int target = GLValues.getGLTextureBindTarget(dim);
			GL11.glBindTexture(target, textureObject);
			backend.checkErrorFine();
			GL11.glTexParameteri(target, GL12.GL_TEXTURE_BASE_LEVEL, 0);
			backend.checkErrorFine();
			GL11.glTexParameteri(target, GL12.GL_TEXTURE_MAX_LEVEL, m-1);
			backend.checkErrorFine();
			if (exts.textureStorage || exts.texStorage) {
				glTexStorage(w,h,d,a);
			} else {
				int boundBuf = GL11.glGetInteger(GL21.GL_PIXEL_UNPACK_BUFFER_BINDING);
				backend.checkErrorFine();
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
				backend.checkErrorFine();
				ByteBuffer initdata = LibCStdlib.malloc(layout.size);
				try {
					LibCString.memset(initdata, 0);
					glTexImage(0, width, height, depth, arrays, MemoryUtil.memAddress(initdata));
				} finally {
					LibCStdlib.free(initdata);
				}
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, boundBuf);
				backend.checkErrorFine();
			}
			if (exts.generateTextureMipmap) {
				exts.glGenerateTextureMipmap(textureObject);
				backend.checkErrorFine();
			} else if (exts.generateMipmap) {
				exts.glGenerateMipmap(target);
				backend.checkErrorFine();
			} else {
				GL11.glTexParameteri(target, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
				backend.checkErrorFine();
			}
			GL11.glBindTexture(target, boundTex);
			backend.checkErrorCoarse("Failed to create texture");
		} finally {
			ctx.unbindExclusively();
		}
	}
	
	private static int[] cubeFaceTargets = new int[] {
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y,
		GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
		GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z
	};
	
	private void glTexStorage(int w, int h, int d, int a) {
		GLExtensions exts = backend.getExtensions();
		int target = GLValues.getGLTextureBindTarget(dimension);
		if (exts.textureStorage) {
			switch(dimension) {
			case DIM_1D:
				exts.glTextureStorage1D(textureObject, target, mipmaps, glFormat, w);
				break;
			case DIM_1D_ARRAY:
				exts.glTextureStorage2D(textureObject, target, mipmaps, glFormat, w, a);
				break;
			case DIM_2D:
				exts.glTextureStorage2D(textureObject, target, mipmaps, glFormat, w, h);
				break;
			case DIM_2D_ARRAY:
				exts.glTextureStorage3D(textureObject, target, mipmaps, glFormat, w, h, a);
				break;
			case DIM_3D:
				exts.glTextureStorage3D(textureObject, target, mipmaps, glFormat, w, h, d);
				break;
			case CUBE:
				exts.glTextureStorage2D(textureObject, target, mipmaps, glFormat, w, h);
				break;
			case CUBE_ARRAY:
				exts.glTextureStorage3D(textureObject, target, mipmaps, glFormat, w, h, a);
				break;
			}
			backend.checkErrorFine();
		} else if (exts.texStorage) {
			GL11.glBindTexture(target, textureObject);
			backend.checkErrorFine();
			switch(dimension) {
			case DIM_1D:
				exts.glTexStorage1D(target, mipmaps, glFormat, w);
				break;
			case DIM_1D_ARRAY:
				exts.glTexStorage2D(target, mipmaps, glFormat, w, a);
				break;
			case DIM_2D:
				exts.glTexStorage2D(target, mipmaps, glFormat, w, h);
				break;
			case DIM_2D_ARRAY:
				exts.glTexStorage3D(target, mipmaps, glFormat, w, h, a);
				break;
			case DIM_3D:
				exts.glTexStorage3D(target, mipmaps, glFormat, w, h, d);
				break;
			case CUBE:
				exts.glTexStorage2D(target, mipmaps, glFormat, w, h);
				break;
			case CUBE_ARRAY:
				exts.glTexStorage3D(target, mipmaps, glFormat, w, h, a);
				break;
			}
			backend.checkErrorFine();
		}
	}
	
	void glTextureSubImage(int mipmap, int x, int y, int z, int w, int h, int d, int a, int ac, long data) {
		GLExtensions exts = backend.getExtensions();
		switch(dimension) {
		case DIM_1D:
			exts.glTextureSubImage1D(textureObject, mipmap, x, w, glFormat, glType, data);
			break;
		case DIM_1D_ARRAY:
			exts.glTextureSubImage2D(textureObject, mipmap, x, a, w, ac, glFormat, glType, data);
			break;
		case DIM_2D:
			exts.glTextureSubImage2D(textureObject, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case DIM_2D_ARRAY:
			exts.glTextureSubImage3D(textureObject, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		case DIM_3D:
			exts.glTextureSubImage3D(textureObject, mipmap, x, y, z, w, h, d, glFormat, glType, data);
			break;
		case CUBE:
			exts.glTextureSubImage2D(textureObject, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case CUBE_ARRAY:
			exts.glTextureSubImage3D(textureObject, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		}
		backend.checkErrorFine();
	}
	
	void glTexSubImage(int mipmap, int x, int y, int z, int w, int h, int d, int a, int ac, long data) {
		int target = GLValues.getGLTextureBindTarget(dimension);
		GL11.glBindTexture(target, textureObject);
		backend.checkErrorFine();
		switch(dimension) {
		case DIM_1D:
			GL11.glTexSubImage1D(target, mipmap, x, w, glFormat, glType, data);
			break;
		case DIM_1D_ARRAY:
			GL11.glTexSubImage2D(target, mipmap, x, a, w, ac, glFormat, glType, data);
			break;
		case DIM_2D:
			GL11.glTexSubImage2D(target, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case DIM_2D_ARRAY:
			GL12.glTexSubImage3D(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		case DIM_3D:
			GL12.glTexSubImage3D(target, mipmap, x, y, z, w, h, d, glFormat, glType, data);
			break;
		case CUBE:
			GL11.glTexSubImage2D(target, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case CUBE_ARRAY:
			GL12.glTexSubImage3D(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		}
		backend.checkErrorFine();
	}
	
	void glTexSubImage(int target, int mipmap, int x, int y, int z, int w, int h, int d, int a, int ac, long data) {
		switch(dimension) {
		case DIM_1D:
			GL11.glTexSubImage1D(target, mipmap, x, w, glFormat, glType, data);
			break;
		case DIM_1D_ARRAY:
			GL11.glTexSubImage2D(target, mipmap, x, a, w, ac, glFormat, glType, data);
			break;
		case DIM_2D:
			GL11.glTexSubImage2D(target, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case DIM_2D_ARRAY:
			GL12.glTexSubImage3D(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		case DIM_3D:
			GL12.glTexSubImage3D(target, mipmap, x, y, z, w, h, d, glFormat, glType, data);
			break;
		case CUBE:
			GL11.glTexSubImage2D(target, mipmap, x, y, w, h, glFormat, glType, data);
			break;
		case CUBE_ARRAY:
			GL12.glTexSubImage3D(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		}
		backend.checkErrorFine();
	}
	
	private void glTexImage(int mipmap, int w, int h, int d, int a, long data) {
		int target = GLValues.getGLTextureBindTarget(dimension);
		GL11.glBindTexture(target, textureObject);
		backend.checkErrorFine();
		switch(dimension) {
		case DIM_1D:
			GL11.glTexImage1D(target, mipmap, glFormat, w, 0, glFormat, glType, data);
			break;
		case DIM_1D_ARRAY:
			GL11.glTexImage2D(target, mipmap, glFormat, w, a, 0, glFormat, glType, data);
			break;
		case DIM_2D:
			GL11.glTexImage2D(target, mipmap, glFormat, w, h, 0, glFormat, glType, data);
			break;
		case DIM_2D_ARRAY:
			GL12.glTexImage3D(target, mipmap, glFormat, w, h, a, 0, glFormat, glType, data);
			break;
		case DIM_3D:
			GL12.glTexImage3D(target, mipmap, glFormat, w, h, d, 0, glFormat, glType, data);
			break;
		case CUBE: {
			target = cubeFaceTargets[d];
			GL11.glBindTexture(target, textureObject);
			backend.checkErrorFine();
			GL11.glTexImage2D(target, mipmap, glFormat, w, h, 0, glFormat, glType, data);
		} break;
		case CUBE_ARRAY: {
			int cubeFace = a % 6;
			a /= 6;
			target = cubeFaceTargets[cubeFace];
			GL11.glBindTexture(target, textureObject);
			backend.checkErrorFine();
			GL12.glTexImage3D(target, mipmap, glFormat, w, h, a, 0, glFormat, glType, data);
		} break;
		}
		backend.checkErrorFine();
	}
	
	void glClearTexSubImage(int mipmap, int x, int y, int z, int w, int h, int d, int a, int ac, ByteBuffer data) {
		int target = GLValues.getGLTextureBindTarget(dimension);
		GL11.glBindTexture(target, textureObject);
		GLExtensions exts = backend.getExtensions();
		backend.checkErrorFine();
		switch(dimension) {
		case DIM_1D:
			exts.glClearTexSubImage(target, mipmap, x, 0, 0, w, 0, 0, glFormat, glType, data);
			break;
		case DIM_1D_ARRAY:
			exts.glClearTexSubImage(target, mipmap, x, a, 0, w, ac, 0, glFormat, glType, data);
			break;
		case DIM_2D:
			exts.glClearTexSubImage(target, mipmap, x, y, 0, w, h, 0, glFormat, glType, data);
			break;
		case DIM_2D_ARRAY:
			exts.glClearTexSubImage(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		case DIM_3D:
			exts.glClearTexSubImage(target, mipmap, x, y, z, w, h, d, glFormat, glType, data);
			break;
		case CUBE:
		case CUBE_ARRAY:
			exts.glClearTexSubImage(target, mipmap, x, y, a, w, h, ac, glFormat, glType, data);
			break;
		}
		backend.checkErrorFine();
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
			// Ensure pixel buffer is ready
			if (pixelBuf != 0) {
				pixelBuf = GL15.glGenBuffers();
				backend.checkErrorFine();
			}
			int target = GLValues.getGLTextureBindTarget(dimension);
			switch(mode) {
			case READ_ONLY:
			case READ_WRITE:
				// Unpack buffer may be read, unpack pixels
				GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
				backend.checkErrorFine();
				GL11.glBindTexture(target, textureObject);
				backend.checkErrorFine();
				GL11.glGetTexImage(target, 0, glFormat, glType, 0);
				backend.checkErrorFine();
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_PACK_BUFFER, GL15.GL_READ_ONLY);
				backend.checkErrorFine();
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
				backend.checkErrorFine();
				break;
			case WRITE_ONLY: // Write-only, just initialize pixel unpack buffer
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
				backend.checkErrorFine();
				GL15.glBufferData(GL21.GL_PIXEL_UNPACK_BUFFER, layout.size, GL15.GL_WRITE_ONLY);
				backend.checkErrorFine();
				mappedMem = GL15.glMapBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, GL15.GL_WRITE_ONLY);
				backend.checkErrorFine();
				mappedPtr = Pointer.pointerToAddress(MemoryUtil.memAddress(mappedMem));
				GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
				backend.checkErrorFine();
				break;
			}
			backend.checkErrorCoarse("Failed to map texture memory");
		});
	}

	@Override
	public void unmapMemory() {
		mapLock.lock();
		try {
			if (mappingMode != null) {
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					switch(mappingMode) {
					case READ_ONLY:
						GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, pixelBuf);
						backend.checkErrorFine();
						GL15.glUnmapBuffer(GL21.GL_PIXEL_PACK_BUFFER);
						backend.checkErrorFine();
						GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, 0);
						backend.checkErrorFine();
						mappedMem = null;
						mappedPtr = null;
						break;
					case WRITE_ONLY:
					case READ_WRITE:
						GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, pixelBuf);
						backend.checkErrorFine();
						GL15.glUnmapBuffer(GL21.GL_PIXEL_UNPACK_BUFFER);
						backend.checkErrorFine();
						GL11.glBindTexture(target, textureObject);
						backend.checkErrorFine();
						glTexSubImage(0, 0, 0, 0, width, height, depth, 0, arrays, 0);
						backend.checkErrorFine();
						GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, 0);
						backend.checkErrorFine();
						mappedMem = null;
						mappedPtr = null;
						break;
					}
					backend.checkErrorCoarse("Failed to unmap texture memory");
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
	
	private boolean defaultHasAnisotropy = false;
	private boolean defaultHasDepth = false;
	private boolean defaultHasBounds = false;
	private boolean mayEditDefaultSampler = true;

	@Override
	public ZESamplerSettings getDefaultSamplerSettings() {
		return new ZESamplerSettings() {
			
			private ZEMipmapFilter mipFilter = ZEMipmapFilter.NEAREST;
			private ZEFilter minFilter = ZEFilter.NEAREST;

			@Override
			public void setFilters(ZEFilter minFilter, ZEFilter magFilter) {
				if (!mayEditDefaultSampler) return;
				this.minFilter = minFilter;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, GLValues.getGLMagFilter(magFilter));
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GLValues.getGLMinFilter(minFilter, mipFilter));
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler filters");
				});
			}

			@Override
			public void setMipmapFilter(ZEMipmapFilter filter) {
				if (!mayEditDefaultSampler) return;
				this.mipFilter = filter;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, GLValues.getGLMinFilter(minFilter, filter));
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler mipmap filter");
				});
			}

			@Override
			public void setAddressingModes(ZEAddressingMode u, ZEAddressingMode v, ZEAddressingMode w) {
				if (!mayEditDefaultSampler) return;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, GLValues.getGLAddressingMode(u));
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, GLValues.getGLAddressingMode(v));
					backend.checkErrorFine();
					GL11.glTexParameteri(target, GL12.GL_TEXTURE_WRAP_R, GLValues.getGLAddressingMode(w));
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler addressing modes");
				});
			}

			@Override
			public void setMipmapBias(float bias) {
				if (!mayEditDefaultSampler) return;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameterf(target, GL14.GL_TEXTURE_LOD_BIAS, bias);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler mipmap bias");
				});
			}

			@Override
			public void setAnisotropy(boolean enable, float maxAnisotropy) {
				if (!mayEditDefaultSampler) return;
				defaultHasAnisotropy = enable;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameterf(target, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, enable ? maxAnisotropy : 1);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler anisotropy");
				});
			}

			@Override
			public void setDepthCompare(boolean enable, ZECompareOp compareOp) {
				if (!mayEditDefaultSampler) return;
				defaultHasDepth = enable;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					if (enable) {
						GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_COMPARE_R_TO_TEXTURE);
						backend.checkErrorFine();
						GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_FUNC, GLValues.getGLCompare(compareOp));
						backend.checkErrorFine();
					} else {
						GL11.glTexParameteri(target, GL14.GL_TEXTURE_COMPARE_MODE, GL11.GL_NONE);
						backend.checkErrorFine();
					}
					backend.checkErrorCoarse("Failed to set default sampler depth comparison");
				});
			}

			@Override
			public void setMipmapBounds(float minLod, float maxLod) {
				if (!mayEditDefaultSampler) return;
				defaultHasBounds = true;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					GL11.glTexParameterf(target, GL12.GL_TEXTURE_MIN_LOD, minLod);
					backend.checkErrorFine();
					GL11.glTexParameterf(target, GL12.GL_TEXTURE_MAX_LOD, maxLod);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set default sampler mipmap bounds");
				});
			}

			@Override
			public void setBorderColor(ZEBorderColor borderColor) {
				if (!mayEditDefaultSampler) return;
				ctx.executeExclusivly(() -> {
					int target = GLValues.getGLTextureBindTarget(dimension);
					GL11.glBindTexture(target, textureObject);
					backend.checkErrorFine();
					switch(borderColor) {
					case OPAQUE_BLACK:
						GL11.glTexParameterfv(target, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 0, 0, 0, 1});
						backend.checkErrorFine();
						break;
					case TRANSPARENT_BLACK:
						GL11.glTexParameterfv(target, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 0, 0, 0, 0});
						backend.checkErrorFine();
						break;
					case TRANSPARENT_WHITE:
						GL11.glTexParameterfv(target, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 1, 1, 1, 0});
						backend.checkErrorFine();
						break;
					}
					backend.checkErrorCoarse("Failed to set default sampler border color");
				});
			}
			
		};
	}

	private GLSampler defaultSampler;
	
	@Override
	public ZESampler getDefaultSampler() {
		mayEditDefaultSampler = false;
		if (defaultSampler != null) defaultSampler = new GLSampler(textureObject, 0, defaultHasAnisotropy, defaultHasDepth, defaultHasBounds);
		return defaultSampler;
	}

	private class GLSamplerBuilder implements ZESamplerBuilder {

		private ZEFilter minFilter = ZEFilter.NEAREST, magFilter = ZEFilter.NEAREST;
		
		@Override
		public void setFilters(ZEFilter minFilter, ZEFilter magFilter) {
			this.minFilter = minFilter;
			this.magFilter = magFilter;
		}

		private ZEMipmapFilter mipFilter = ZEMipmapFilter.NEAREST;
		
		@Override
		public void setMipmapFilter(ZEMipmapFilter filter) {
			mipFilter = filter;
		}

		private ZEAddressingMode modeU = ZEAddressingMode.CLAMP_TO_EDGE, modeV = ZEAddressingMode.CLAMP_TO_EDGE, modeW = ZEAddressingMode.CLAMP_TO_EDGE;
		
		@Override
		public void setAddressingModes(ZEAddressingMode u, ZEAddressingMode v, ZEAddressingMode w) {
			modeU = u;
			modeV = v;
			modeW = w;
		}

		private float mipBias = 0;
		
		@Override
		public void setMipmapBias(float bias) {
			mipBias = bias;
		}

		private boolean anisotropy = false;
		private float maxAnisotropy = 1;
		
		@Override
		public void setAnisotropy(boolean enable, float maxAnisotropy) {
			anisotropy = enable;
			this.maxAnisotropy = maxAnisotropy;
		}
		
		private boolean depthCompare = false;
		private ZECompareOp depthOp = ZECompareOp.ALWAYS;

		@Override
		public void setDepthCompare(boolean enable, ZECompareOp compareOp) {
			depthCompare = enable;
			depthOp = compareOp;
		}

		private boolean hasBounds = false;
		private float minLod, maxLod;
		
		@Override
		public void setMipmapBounds(float minLod, float maxLod) {
			this.minLod = minLod;
			this.maxLod = maxLod;
			hasBounds = true;
		}
		
		private ZEBorderColor borderColor = ZEBorderColor.TRANSPARENT_BLACK;

		@Override
		public void setBorderColor(ZEBorderColor borderColor) {
			this.borderColor = borderColor;
		}

		@Override
		public ZESampler build() {
			GLExtensions exts = backend.getExtensions();
			ctx.bindExclusively();
			int sampler = exts.glGenSamplers();
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_MIN_FILTER, GLValues.getGLMinFilter(minFilter, mipFilter));
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_MAG_FILTER, GLValues.getGLMagFilter(magFilter));
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_S, GLValues.getGLAddressingMode(modeU));
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_T, GLValues.getGLAddressingMode(modeV));
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL12.GL_TEXTURE_WRAP_R, GLValues.getGLAddressingMode(modeW));
			backend.checkErrorFine();
			exts.glSamplerParameterf(sampler, GL14.GL_TEXTURE_LOD_BIAS, mipBias);
			backend.checkErrorFine();
			if (anisotropy) {
				exts.glSamplerParameterf(sampler, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, maxAnisotropy);
				backend.checkErrorFine();
			}
			if (depthCompare) {
				exts.glSamplerParameteri(sampler, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_COMPARE_R_TO_TEXTURE);
				backend.checkErrorFine();
				exts.glSamplerParameteri(sampler, GL14.GL_TEXTURE_COMPARE_FUNC, GLValues.getGLCompare(depthOp));
				backend.checkErrorFine();
			}
			if (hasBounds) {
				exts.glSamplerParameterf(sampler, GL12.GL_TEXTURE_MIN_LOD, minLod);
				backend.checkErrorFine();
				exts.glSamplerParameterf(sampler, GL12.GL_TEXTURE_MAX_LOD, maxLod);
				backend.checkErrorFine();
			}
			switch(borderColor) {
			case OPAQUE_BLACK:
				exts.glSamplerParameterfv(sampler, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 0, 0, 0, 1});
				backend.checkErrorFine();
				break;
			case TRANSPARENT_BLACK:
				exts.glSamplerParameterfv(sampler, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 0, 0, 0, 0});
				backend.checkErrorFine();
				break;
			case TRANSPARENT_WHITE:
				exts.glSamplerParameterfv(sampler, GL11.GL_TEXTURE_BORDER_COLOR, new float[] { 1, 1, 1, 0});
				backend.checkErrorFine();
				break;
			}
			backend.checkErrorCoarse("Failed to create new sampler object");
			ctx.unbindExclusively();
			return new GLSampler(textureObject, sampler, anisotropy, depthCompare, hasBounds);
		}
		
	}
	
	@Override
	public ZESamplerBuilder createSamplerBuilder() {
		return new GLSamplerBuilder();
	}

	@Override
	public ZESampler duplicateSampler(ZESampler other) {
		GLSampler glother = (GLSampler)other;
		
		int minFilter, magFilter;
		int modeS, modeT, modeR;
		float mipBias;
		float anisotropy = 1;
		int depthCompareOp = GL11.GL_ALWAYS;
		float minLod = -1000, maxLod = 1000;
		float[] borderColor = new float[4];
		
		GLExtensions exts = backend.getExtensions();
		ctx.bindExclusively();
		
		if (glother.samplerObject == 0) { // Texture sampler
			int target = GLValues.getGLTextureBindTarget(dimension);
			minFilter = GL11.glGetTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER);
			backend.checkErrorFine();
			magFilter = GL11.glGetTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER);
			backend.checkErrorFine();
			modeS = GL11.glGetTexParameteri(target, GL11.GL_TEXTURE_WRAP_S);
			backend.checkErrorFine();
			modeT = GL11.glGetTexParameteri(target, GL11.GL_TEXTURE_WRAP_T);
			backend.checkErrorFine();
			modeR = GL11.glGetTexParameteri(target, GL12.GL_TEXTURE_WRAP_R);
			backend.checkErrorFine();
			mipBias = GL11.glGetTexParameterf(target, GL14.GL_TEXTURE_LOD_BIAS);
			backend.checkErrorFine();
			if (glother.hasAnisotropy) {
				anisotropy = GL11.glGetTexParameterf(target, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT);
				backend.checkErrorFine();
			}
			if (glother.hasDepthCompare) {
				depthCompareOp = GL11.glGetTexParameteri(target, GL14.GL_TEXTURE_COMPARE_FUNC);
				backend.checkErrorFine();
			}
			if (glother.hasMinMaxLod) {
				minLod = GL11.glGetTexParameterf(target, GL12.GL_TEXTURE_MIN_LOD);
				backend.checkErrorFine();
				maxLod = GL11.glGetTexParameterf(target, GL12.GL_TEXTURE_MAX_LOD);
				backend.checkErrorFine();
			}
			GL11.glGetTexParameterfv(target, GL11.GL_TEXTURE_BORDER_COLOR, borderColor);
			backend.checkErrorFine();
		} else { // Sampler object
			int sampler = glother.samplerObject;
			minFilter = exts.glGetSamplerParameteri(sampler, GL11.GL_TEXTURE_MIN_FILTER);
			backend.checkErrorFine();
			magFilter = exts.glGetSamplerParameteri(sampler, GL11.GL_TEXTURE_MAG_FILTER);
			backend.checkErrorFine();
			modeS = exts.glGetSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_S);
			backend.checkErrorFine();
			modeT = exts.glGetSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_T);
			backend.checkErrorFine();
			modeR = exts.glGetSamplerParameteri(sampler, GL12.GL_TEXTURE_WRAP_R);
			backend.checkErrorFine();
			mipBias = exts.glGetSamplerParameterf(sampler, GL14.GL_TEXTURE_LOD_BIAS);
			backend.checkErrorFine();
			if (glother.hasAnisotropy) {
				anisotropy = exts.glGetSamplerParameterf(sampler, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT);
				backend.checkErrorFine();
			}
			if (glother.hasDepthCompare) {
				depthCompareOp = exts.glGetSamplerParameteri(sampler, GL14.GL_TEXTURE_COMPARE_FUNC);
				backend.checkErrorFine();
			}
			if (glother.hasMinMaxLod) {
				minLod = exts.glGetSamplerParameterf(sampler, GL12.GL_TEXTURE_MIN_LOD);
				backend.checkErrorFine();
				maxLod = exts.glGetSamplerParameterf(sampler, GL12.GL_TEXTURE_MAX_LOD);
				backend.checkErrorFine();
			}
			exts.glGetSamplerParameterfv(sampler, GL11.GL_TEXTURE_BORDER_COLOR, borderColor);
			backend.checkErrorFine();
		}
		
		int sampler = exts.glGenSamplers();
		backend.checkErrorFine();
		exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_MIN_FILTER, minFilter);
		backend.checkErrorFine();
		exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_MAG_FILTER, magFilter);
		backend.checkErrorFine();
		exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_S, modeS);
		backend.checkErrorFine();
		exts.glSamplerParameteri(sampler, GL11.GL_TEXTURE_WRAP_T, modeT);
		backend.checkErrorFine();
		exts.glSamplerParameteri(sampler, GL12.GL_TEXTURE_WRAP_R, modeR);
		backend.checkErrorFine();
		exts.glSamplerParameterf(sampler, GL14.GL_TEXTURE_LOD_BIAS, mipBias);
		backend.checkErrorFine();
		if (glother.hasAnisotropy) {
			exts.glSamplerParameterf(sampler, EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT, anisotropy);
			backend.checkErrorFine();
		}
		if (glother.hasDepthCompare) {
			exts.glSamplerParameteri(sampler, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_COMPARE_R_TO_TEXTURE);
			backend.checkErrorFine();
			exts.glSamplerParameteri(sampler, GL14.GL_TEXTURE_COMPARE_FUNC, depthCompareOp);
			backend.checkErrorFine();
		}
		if (glother.hasMinMaxLod) {
			exts.glSamplerParameterf(sampler, GL12.GL_TEXTURE_MIN_LOD, minLod);
			backend.checkErrorFine();
			exts.glSamplerParameterf(sampler, GL12.GL_TEXTURE_MAX_LOD, maxLod);
			backend.checkErrorFine();
		}
		exts.glSamplerParameterfv(sampler, GL11.GL_TEXTURE_BORDER_COLOR, borderColor);
		backend.checkErrorFine();
		backend.checkErrorCoarse("Failed to duplicate sampler");
		
		ctx.unbindExclusively();
		return new GLSampler(textureObject, sampler, glother.hasAnisotropy, glother.hasDepthCompare, glother.hasMinMaxLod);
	}

	@Override
	public int getArrayLayers() {
		return arrays;
	}

	@Override
	public int getMipmapLevels() {
		return mipmaps;
	}
	
}
