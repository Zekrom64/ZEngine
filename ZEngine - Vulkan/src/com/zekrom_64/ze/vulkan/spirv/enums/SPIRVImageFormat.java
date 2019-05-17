package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVImageFormat {

	UNKNOWN,
	RGBA32F,
	RGBA16F,
	R32F,
	RGBA8,
	RGBA8_SNORM,
	RG32F,
	RG16F,
	R11FG11FB10F,
	R16F,
	RGBA16,
	RGB10A2,
	RG16,
	RG8,
	R16,
	R8,
	RGBA16_SNORM,
	RG16_SNORM,
	RG8_SNORM,
	R16_SNORM,
	R8_SNORM,
	RGBA32I,
	RGBA16I,
	RGBA8I,
	R32I,
	RG32I,
	RG16I,
	RG8I,
	R16I,
	R8I,
	RGBA32UI,
	RGBA16UI,
	RGBA8UI,
	R32UI,
	RGB10A2UI,
	RG32UI,
	RG16UI,
	RG8UI,
	R16UI,
	R8UI;

	public final int value = ordinal();

	public static SPIRVImageFormat parse(int val) {
		SPIRVImageFormat[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
