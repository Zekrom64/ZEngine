package com.zekrom_64.ze.base.backend.render.obj;

import com.zekrom_64.ze.base.image.ZEPixelFormat;

/** A texture is a special type of memory buffer that can store an image. Textures can have their memory
 * mapped just like a buffer, but different implementations might accomplish this differently. For example,
 * an OpenGL backend might use a Pixel Buffer Object to allow access to texture memory, while a
 * Vulkan backend will give direct memory access.
 * 
 * @author Zekrom_64
 *
 */
public interface ZETexture extends ZEBuffer {
	
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
	
	/** Gets the pixel format of this texture.
	 * 
	 * @return Texture pixel format
	 */
	public ZEPixelFormat getPixelFormat();
	
	/** Gets the number of bytes per row of pixels. Some texture formats
	 * may have extra padding bytes between lines.
	 * 
	 * @return Row pitch
	 */
	public int getMemoryRowPitch();
	
	/** Gets the dimensions the texture uses.
	 * 
	 * @return Texture dimensions
	 */
	public ZETextureDimension getDimension();
	
}
