package com.zekrom_64.ze.base.backend.render.obj;

/** A sampler controls how values are read from a texture in a shader.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESampler {

	/** A sampler filter defines how a texture is sampled in its coordinate space.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEFilter {
		/** The pixel nearest to the texture coordinate is sampled. */
		NEAREST,
		/** The nearest pixels to the texture coordinate are combined
		 * by weighted average of their distances from the coordinate.
		 */
		LINEAR
	}
	
	/** A mipmap filter defines how a texture is sampled at differing
	 * levels of detail.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEMipmapFilter {
		/** The pixels in the nearest mipmap level are sampled. */
		NEAREST,
		/** The pixels from the closest mipmap levels are sampled and combined
		 * by weighted average of their distance from the levels of detail.
		 */
		LINEAR
	}
	
	/** A sampler addressing mode defines the behavior of a sampler
	 * when addressing outside of the normal bounds of a texture.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEAddressingMode {
		/** The sampled value is equivalent to wrapping around from the opposite side
		 * of the axis that is out of bounds.
		 */
		REPEAT,
		/** Identical to {@link REPEAT}, but the coordinates are inverted when sampling
		 * out of bounds.
		 */
		MIRRORED_REPEAT,
		/** Values sampled outside of the bounds are equivalent to the nearest pixel
		 * inside the bounds.
		 */
		CLAMP_TO_EDGE,
		/** Values sampled outside of the bounds are equivalent to the border color */
		CLAMP_TO_BORDER
	}
	
	/** A border color defines a constant value that may be sampled at the edge of the
	 * texture, if defined by the addressing mode.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEBorderColor {
		/** The sampled value is a transparent black. */
		TRANSPARENT_BLACK,
		/** The sampled value is an opaque black. */
		OPAQUE_BLACK,
		/** The sampled value is a transparent white. */
		TRANSPARENT_WHITE
	}
	
}
