package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPRIVSelectionControl {

	FLATTEN,
	DONT_FLATTEN;
	
	public final int mask = 1 << ordinal();
	
}
