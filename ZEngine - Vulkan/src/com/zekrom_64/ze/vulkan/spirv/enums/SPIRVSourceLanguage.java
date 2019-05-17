package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVSourceLanguage {

	UNKNOWN,
	ESSL,
	GLSL,
	OPENCL_C,
	OPENCL_CPP,
	HLSL;

	public final int value = ordinal();
	
	public static SPIRVSourceLanguage parse(int val) {
		SPIRVSourceLanguage[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
