package com.zekrom_64.ze.base.backend.render.obj;

/** Enumeration describing the dimensionality of a texture. This affects how data is accessed
 * when the image is sampled.
 * 
 * @author Zekrom_64
 *
 */
public enum ZETextureDimension {
	
	/** The texture only has a single dimension and is accessed linearly. */
	DIM_1D,
	/** The texture has two dimensions and is accessed in a grid. */
	DIM_2D,
	/** The texture has three dimensions and is accessed as a volumetric cube of pixels. */
	DIM_3D,
	/** The texture is comprised of six separate two dimensional images, arranged as the faces of a cube, with
	 * semantics for determining the face being accessed.
	 */
	CUBE(true, false),
	/** The texture is a two dimensional texture with no mipmaps. It has no power-of-two size restrictions, but 
	 * is accessed by texel values (coordinates of individual texture elements, or pixels) instead of normalized
	 * coordinates.
	 */
	RECTANGLE,
	/** The texture is a one dimensional texture, but its elements are stored in a buffer object. Its purpose is
	 * to allow shaders access to large memory arrays managed by buffers.
	 */
	BUFFER_TEXTURE,
	/** The texture is similar to {@link #DIM_1D} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_2D} texture.
	 */
	DIM_1D_ARRAY(false, true),
	/** The texture is similar to {@link #DIM_2D} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_3D} texture.
	 */
	DIM_2D_ARRAY(false, true),
	/** The texture is similar to {@link #CUBE} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_3D} texture with semantics for the face being accessed.
	 */
	CUBE_ARRAY(true, true);
	
	public final boolean isCubemap;
	public final boolean isArray;
	
	private ZETextureDimension(boolean cube, boolean array) {
		isCubemap = cube;
		isArray = array;
	}
	
	private ZETextureDimension() {
		this(false, false);
	}
	
}
