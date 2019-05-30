package com.zekrom_64.ze.base.backend.render.obj;

/** A sampler builder may create a new sampler independent of a
 * texture's default sampler.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESamplerBuilder extends ZESamplerSettings {

	/** Creates a new sampler with the current settings.
	 * 
	 * @return New sampler
	 */
	public ZESampler build();
	
}
