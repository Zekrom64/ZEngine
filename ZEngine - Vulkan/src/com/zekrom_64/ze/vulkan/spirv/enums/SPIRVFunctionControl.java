package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVFunctionControl {

	INLINE,
	DONT_INLINE,
	PURE,
	CONST;
	
	public final int mask = 1 << ordinal();
	
}
