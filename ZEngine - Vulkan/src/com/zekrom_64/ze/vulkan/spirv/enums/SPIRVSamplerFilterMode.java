package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVSamplerFilterMode {

	NEAREST,
	LINER;

	public final int value = ordinal();

	public static SPIRVSamplerFilterMode parse(int val) {
		SPIRVSamplerFilterMode[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
