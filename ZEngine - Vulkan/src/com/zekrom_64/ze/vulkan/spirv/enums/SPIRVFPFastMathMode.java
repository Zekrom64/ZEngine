package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVFPFastMathMode {

	NOT_NAN,
	NOT_INF,
	NO_SIGN,
	ALLOW_RECIPROCAL,
	FAST_ALGEBRA;
	
	public int mask = 1 << ordinal();
	
}
