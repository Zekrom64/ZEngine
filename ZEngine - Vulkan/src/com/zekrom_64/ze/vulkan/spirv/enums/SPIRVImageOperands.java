package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVImageOperands {

	BIAS,
	LOD,
	GRAD,
	CONST_OFFSET,
	OFFSET,
	CONST_OFFSETS,
	SAMPLE,
	MIN_LOD,
	MAKE_TEXEL_AVAILABLE_KHR,
	MAKE_TEXEL_VISIBLE_KHR,
	NON_PRIVATE_TEXEL_KHR,
	VOLATILE_TEXEL_KHR,
	SIGN_EXTEND,
	ZERO_EXTEND;
	
	public int mask = 1 << ordinal();
	
}
