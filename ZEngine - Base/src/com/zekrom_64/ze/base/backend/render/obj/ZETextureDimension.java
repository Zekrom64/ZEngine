package com.zekrom_64.ze.base.backend.render.obj;

/** Enumeration describing the dimensionality of a texture. This affects how data is accessed
 * when the image is sampled.
 * 
 * @author Zekrom_64
 *
 */
public enum ZETextureDimension {
	
	/** The texture only has a single dimension and is accessed linearly. */
	DIM_1D(1),
	/** The texture has two dimensions and is accessed in a grid. */
	DIM_2D(2),
	/** The texture has three dimensions and is accessed as a volumetric cube of pixels. */
	DIM_3D(3),
	/** The texture is comprised of six separate two dimensional images, arranged as the faces of a cube, with
	 * the face being selected with a third dimension the same as an array texture.
	 */
	CUBE(true, false,3),
	/** The texture is similar to {@link #DIM_1D} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_2D} texture.
	 */
	DIM_1D_ARRAY(false, true,2),
	/** The texture is similar to {@link #DIM_2D} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_3D} texture.
	 */
	DIM_2D_ARRAY(false, true,3),
	/** The texture is similar to {@link #CUBE} but with an array of images for any given mipmap level, and is accessed
	 * like a {@link #DIM_3D} texture with the array layer being <tt>Z / 6</tt>, and the cubemap face being <tt>Z % 6</tt>.
	 */
	CUBE_ARRAY(true, true,3);
	
	/** If the texture dimensions are for a cubemap. */
	public final boolean isCubemap;
	/** If the texture dimensions are for an array. */
	public final boolean isArray;
	/** The number of dimensions used by the texture. */
	public final int numDimensions;
	
	private ZETextureDimension(boolean cube, boolean array, int dims) {
		isCubemap = cube;
		isArray = array;
		numDimensions = dims;
	}
	
	private ZETextureDimension(int dims) {
		this(false, false, dims);
	}
	
}
