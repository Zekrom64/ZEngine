package com.zekrom_64.ze.base.backend.render.obj;

import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A texture is a special type of memory buffer that can store an image. Textures can have their memory
 * mapped just like a buffer, but different implementations might accomplish this differently. For example,
 * an OpenGL backend might use a Pixel Buffer Object to allow access to texture memory, while a
 * Vulkan backend will give direct memory access. Textures also may have different memory layouts which
 * must be taken into account when accessing their mapped memory.
 * 
 * @author Zekrom_64
 *
 */
public interface ZETexture extends ZEGraphicsMemory {
	
	/** A texture layout describes how a texture resides in memory, with
	 * certain layouts only usable in certain applications. Textures will
	 * be initialized in the {@link ZETextureLayout#GENERAL} layout, but
	 * may be transitioned to a new layout by a command buffer.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZETextureLayout {
		
		/** A general layout can be used in any operation, but is not always optimized. */
		GENERAL,
		/** A color attachment layout can be used as a color attachment to shaders. */
		COLOR_ATTACHMENT,
		/** A depth-stencil attachment layout can be used as a depth-stencil attachment to a shader. */
		DEPTH_STENCIL_ATTACHMENT,
		/** A specialization of {@link #DEPTH_STENCIL_ATTACHMENT} that is read-only. */
		DEPTH_STENCIL_READ_ONLY,
		/** A read-only attachment for a shader. */
		SHADER_READ_ONLY,
		/** A layout optimized as a source for transfer operations. */
		TRANSFER_SRC,
		/** A layout optimized as a destination for transfer operations. */
		TRANSFER_DST
		
	}
	
	/** A texture aspect is a component of a texture. Textures may have multiple aspects
	 * depending on their format.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZETextureAspect {
		
		/** The color aspect of a texture. */
		COLOR,
		/** The depth aspect of a texture, used for depth buffers. */
		DEPTH,
		/** The stencil aspect of a texture, used for stencil buffers. */
		STENCIL
		
	}
	
	/** Enumeration of usages for a texture.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZETextureUsage {
		
		/** Texture can be read from in memory transfers. */
		TRANSFER_SRC,
		/** Texture can be written to in memory transfers. */
		TRANSFER_DST,
		/** Texture can be sampled from in a shader. */
		SAMPLED,
		/** Texture can be used as a storage texture in a shader. */
		STORAGE,
		/** Texture can be used as a color attachment in a framebuffer. */
		COLOR_ATTACHMENT,
		/** Texture can be used as a depth/stencil attachment in a framebuffer. */
		DEPTH_STENCIL_ATTACHMENT,
		/** Texture can be used as an <a href="https://www.saschawillems.de/blog/2018/07/19/vulkan-input-attachments-and-sub-passes/">
		 * input attachment</a>, which can be used in fragment shaders to read from the
		 * same pixel previously written to without requiring memory barriers.
		 */
		INPUT_ATTACHMENT
		
	}
	
	/** A texture range describes a selection of the parts of a texture, including
	 * contents (color, depth, stencil), mipmap level, and texture array level.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureRange {
		
		/** The texture aspects included in the range, <b>null</b> includes all aspects. */
		public ZETextureAspect[] aspects = null;
		/** The first mipmap level to include. */
		public int baseMipLevel = 0;
		/** The number of mipmap levels to include starting at {@link #baseMipLevel}. This
		 * is clamped to the maximum number of levels after the base, so {@link Integer#MAX_VALUE}
		 * selects all mipmap levels.
		 */
		public int mipLevelCount = Integer.MAX_VALUE;
		/** The first array layer for cube and cube array textures. */
		public int baseArrayLayer = 0;
		/** The number of array layers to include starting at {@link #baseArrayLayer}. This
		 * is clamped to the maximum number of layers after the base, so {@link Integer#MAX_VALUE}
		 * selects all array layers.
		 */
		public int arrayLayerCount = Integer.MAX_VALUE;
		
		public ZETextureRange() {}
		
		public ZETextureRange(ZETextureRange other) {
			aspects = other.aspects;
			baseMipLevel = other.baseMipLevel;
			mipLevelCount = other.mipLevelCount;
			baseArrayLayer = other.baseArrayLayer;
			arrayLayerCount = other.arrayLayerCount;
		}
		
	}
	
	/** A texture layer selects a single part of a texture as described in {@link ZETextureRange}.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureLayer {

		/** The texture aspects included in the range, <b>null</b> includes all aspects. */
		public ZETextureAspect[] aspects = null;
		/** The first mipmap level to include. */
		public int mipLevel = 0;
		/** The first array layer for cube and array textures. */
		public int baseArrayLayer = 0;
		/** The number of array layers to include starting at {@link #baseArrayLayer}. This
		 * is clamped to the maximum number of layers after the base, so {@link Integer#MAX_VALUE}
		 * selects all array layers.
		 */
		public int arrayLayerCount = Integer.MAX_VALUE;
		
		public ZETextureLayer() {}
		
		public ZETextureLayer(ZETextureLayer other) {
			aspects = other.aspects;
			mipLevel = other.mipLevel;
			baseArrayLayer = other.baseArrayLayer;
			arrayLayerCount = other.arrayLayerCount;
		}
		
	}
	
	/** The texture memory layout describes how the elements of a texture are laid
	 * out in memory.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZETextureMemoryLayout {
		
		/** The base offset of the texture in memory. */
		public final long offset;
		/** The size of the texture in memory in bytes, including any extra space. */
		public final long size;
		/** The number of bytes required for every row of pixels in a 2D image. */
		public final long rowPitch;
		/** The number of bytes required for every 2D slice of pixels in a 3D image. */
		public final long depthPitch;
		/** The number of bytes required for every layer in an array image. */
		public final long arrayPitch;
		
		public ZETextureMemoryLayout(long off, long sz, long row, long depth, long array) {
			offset = off;
			size = sz;
			rowPitch = row;
			depthPitch = depth;
			arrayPitch = array;
		}
		
		/** Tests if the memory layout is tightly packed, ie. the memory regions for the different planes
		 * of pixels have no empty space between them.
		 * 
		 * @param tex Texture
		 * @return If the texture's memory is tightly packed
		 */
		public boolean isTightlyPacked(ZETexture tex) {
			long pixelSize = tex.getPixelFormat().sizeOf;
			
			long rowSize = tex.getWidth() * pixelSize;
			long depthSize = tex.getWidth() * tex.getHeight() * pixelSize;
			long arraySize = tex.getWidth() * tex.getHeight() * tex.getDepth() * pixelSize;
			
			switch(tex.getDimension()) {
			case DIM_1D: return true;
			case DIM_1D_ARRAY:
				return arraySize == arrayPitch;
			case DIM_2D:
				return rowSize == rowPitch;
			case DIM_2D_ARRAY:
			case CUBE:
				return rowSize == rowPitch && arraySize == arrayPitch;
			case DIM_3D:
				return rowSize == rowPitch && depthSize == depthPitch;
			case CUBE_ARRAY:
				return rowSize == rowPitch && depthSize == depthPitch && arraySize == arrayPitch;
			default:
				throw new UnsupportedOperationException();
			}
		}
		
	}
	
	/** Gets the width of this texture (X size).
	 * 
	 * @return Texture width
	 */
	public int getWidth();
	
	/** Gets the height of this texture (Y size). Returns zero if
	 * the texture is not 2D or 3D.
	 * 
	 * @return Texture height
	 */
	public int getHeight();
	
	/** Gets the depth of this texture (Z size). Returns zero if
	 * the texture is not 3D.
	 * 
	 * @return Texture depth
	 */
	public int getDepth();
	
	/** Gets the number of array layers this texture has. Returns
	 * zero if the texture is not an array texture.
	 * 
	 * @return Texture array layer count
	 */
	public int getArrayLayers();
	
	/** Gets the number of mipmap levels this texture has.
	 * 
	 * @return Mipmap level count
	 */
	public int getMipmapLevels();
	
	/** Gets the pixel format of this texture.
	 * 
	 * @return Texture pixel format
	 */
	public ZEPixelFormat getPixelFormat();
	
	/** Gets the memory layout for the texture.
	 * 
	 * @return Texture memory layout
	 */
	public ZETextureMemoryLayout getMemoryLayout();
	
	/** Gets the dimensions the texture uses.
	 * 
	 * @return Texture dimensions
	 */
	public ZETextureDimension getDimension();
	
	/** Gets all the valid usages for this texture.
	 * 
	 * @return Valid usages
	 */
	public ZETextureUsage[] getValidUsages();
	
	/** Gets access to the settings to use when constructing the default sampler
	 * for the texture.
	 * 
	 * @return Default sampler settings
	 */
	public ZESamplerSettings getDefaultSamplerSettings();
	
	/** Gets the default sampler for the texture. The sampler has settings defined with
	 * {@link #getDefaultSamplerSettings()} before the first invocation of this
	 * method. All subsequent calls will return a sampler with fixed settings.
	 * 
	 * @return The default sampler
	 */
	public ZESampler getDefaultSampler();
	
	/** Creates a new builder for a sampler linked to this texture. The render backend
	 * must support {@link com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_INDEPENDENT_SAMPLER
	 * FEATURE_INDEPENDENT_SAMPLER} to use samplers independent of the default sampler.
	 * 
	 * @return Sampler builder
	 */
	public ZESamplerBuilder createSamplerBuilder();
	
	/** Creates a duplicate of an existing sampler, using the same
	 * sampler settings but sampling this texture. This may be more
	 * efficient on some backends than creating samplers with the same
	 * settings for multiple textures.
	 * 
	 * @param other Sampler to duplicate from settings
	 * @return Duplicate sampler sampling this texture
	 */
	public ZESampler duplicateSampler(ZESampler other);
	
}
