package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVScope {

	CROSS_DEVICE,
	DEVICE,
	WORKGROUP,
	SUBGROUP,
	INVOCATION,
	QUEUE_FAMILY_KHR;

	public final int value = ordinal();
	
	public static SPIRVScope parse(int val) {
		SPIRVScope[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
