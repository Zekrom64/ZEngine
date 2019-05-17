package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVSamplerAddressingMode {

	NONE,
	CLAMP_TO_EDGE,
	CLAMP,
	REPEAT,
	REPEAT_MIRRORED;

	public final int value = ordinal();

	public static SPIRVSamplerAddressingMode parse(int val) {
		SPIRVSamplerAddressingMode[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
