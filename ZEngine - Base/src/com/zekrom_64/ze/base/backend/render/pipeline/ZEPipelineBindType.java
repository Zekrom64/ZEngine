package com.zekrom_64.ze.base.backend.render.pipeline;

/** Enumeration containing the different types of bind objects that can be bound
 * to a pipeline object.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEPipelineBindType {
	
	/** The bind object is a combined image and sampler. */
	COMBINED_IMAGE_SAMPLER(true, false),
	/** The bind object is a storage image, which can be read or written arbitrarily
	 * by shaders. This requires {@link com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_SHADER_STORAGE_IMAGE
	 * FEATURE_SHADER_STORAGE_IMAGE} to be supported. */
	STORAGE_IMAGE(true, false),
	/** The bind object is a uniform buffer, which can be read by shaders. This requires
	 * {@link com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_SHADER_UNIFORM_BUFFER
	 * FEATURE_SHADER_UNIFORM_BUFFER} to be supported. */
	UNIFORM_BUFFER(false, true),
	/** The bind object is a storage buffer, which can be read or written arbitrarily
	 * by shaders. This requires {@link com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_SHADER_UNIFORM_BUFFER
	 * FEATURE_SHADER_UNIFORM_BUFFER} to be supported. */
	STORAGE_BUFFER(false, true);
	
	/** If the bind type accepts a texture as a bind object. */
	public final boolean acceptsTexture;
	/** If the bind type accepts a buffer as a bind object. */
	public final boolean acceptsBuffer;
	
	private ZEPipelineBindType(boolean tex, boolean buf) {
		acceptsTexture = tex;
		acceptsBuffer = buf;
	}
	
}