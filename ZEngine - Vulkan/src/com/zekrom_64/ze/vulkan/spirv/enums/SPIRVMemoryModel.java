package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVMemoryModel {

	SIMPLE,
	GLSL450,
	OPENCL,
	VULKAN_KHR;

	public final int value = ordinal();
	
	public static SPIRVMemoryModel parse(int val) {
		SPIRVMemoryModel[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
