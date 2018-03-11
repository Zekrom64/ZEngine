package com.zekrom_64.ze.base.backend.render.pipeline;

/** Enumeration containing the different types of bind objects that can be bound
 * to a pipeline object.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEPipelineBindType {
	
	/** The bind object is a sampled image */
	SAMPLER(true, false),
	/** The bind object is a storage image */
	STORAGE_IMAGE(true, false),
	/** The bind object is a uniform buffer */
	UNIFORM_BUFFER(false, true),
	/** The bind object is a storage buffer */
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