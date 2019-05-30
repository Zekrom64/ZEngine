package com.zekrom_64.ze.gl.objects;

import com.zekrom_64.ze.base.backend.render.obj.ZESampler;

public class GLSampler implements ZESampler {

	public final int textureObject;
	public final int samplerObject;
	
	public final boolean hasAnisotropy;
	public final boolean hasDepthCompare;
	public final boolean hasMinMaxLod;
	
	public GLSampler(int tex, int samp, boolean ha, boolean hdc, boolean hmml) {
		textureObject = tex;
		samplerObject = samp;
		hasAnisotropy = ha;
		hasDepthCompare = hdc;
		hasMinMaxLod = hmml;
	}
	
}
