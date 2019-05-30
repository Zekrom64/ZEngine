package com.zekrom_64.ze.base.backend.render.obj;

import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEAddressingMode;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEBorderColor;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEMipmapFilter;

/** Sampler settings define how a texture sampler operates. They can define the settings
 * for a default sampler for a texture or a unique sampler.
 * 
 * @author Zekrom_64
 *
 */
public interface ZESamplerSettings {

	/** Sets the minification and magnification filters. The minification filter
	 * defines how the texture is sampled below its level of detail (the texture
	 * is smaller than its normal size). The magnification filter defines how
	 * the texture is sampled above its level of detail (the texture is larger
	 * than its normal size).
	 * 
	 * @param minFilter Minification filter
	 * @param magFilter Magnification filter
	 */
	public void setFilters(ZEFilter minFilter, ZEFilter magFilter);
	
	/** Sets the filter selecting the mipmap from the level of detail. This only
	 * applies when the texture is being minified, as highest level of detail
	 * is used when the texture is magnified.
	 * 
	 * @param filter Mipmap filter
	 */
	public void setMipmapFilter(ZEMipmapFilter filter);
	
	/** Sets the sampler addressing modes for each coordinate used in sampling.
	 * 
	 * @param u U coordinate addressing mode
	 * @param v V coordinate addressing mode
	 * @param w W coordinate addressing mode
	 */
	public void setAddressingModes(ZEAddressingMode u, ZEAddressingMode v, ZEAddressingMode w);
	
	/** Sets the bias for the level of detail for selecting a mipmap level when sampling.
	 * 
	 * @param bias Level of detail bias
	 */
	public void setMipmapBias(float bias);
	
	/** Sets if anisotropic filtering should be used with the sampler and the maximum
	 * level of anisotropy (the number of samples to take) to use. Anisotropy is only
	 * supported if {@link com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_ANISOTROPY FEATURE_ANISOTROPY}
	 * is supported by the backend.
	 * 
	 * @param enable If anisotropic filtering is enabled
	 * @param maxAnisotropy The maximum level of anisotropy
	 */
	public void setAnisotropy(boolean enable, float maxAnisotropy);
	
	/** If the sampled image has a depth component the sampled value can
	 * be compared with a reference and the loaded value will be 1.0 if the
	 * comparison is true and 0.0 if it is false.
	 * 
	 * @param enable If depth comparison should be enabled
	 * @param compareOp The depth comparison operation
	 */
	public void setDepthCompare(boolean enable, ZECompareOp compareOp);
	
	/** Sets the bounds for the level of detail to use to sample from the mipmap
	 * 
	 * @param minLod Minimum level of detail
	 * @param maxLod Maximum level of detail
	 */
	public void setMipmapBounds(float minLod, float maxLod);
	
	/** Sets the border color to sample at the texture's edge.
	 * 
	 * @param borderColor Border color
	 */
	public void setBorderColor(ZEBorderColor borderColor);
	
}
