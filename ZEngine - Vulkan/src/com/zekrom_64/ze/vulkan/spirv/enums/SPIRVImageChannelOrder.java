package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVImageChannelOrder {

	R,
	A,
	RG,
	RA,
	RGB,
	RGBA,
	BGRA,
	ARGB,
	INTENSITY,
	LUMINANCE,
	RX,
	RGX,
	RGBX,
	DEPTH,
	DEPTH_STENCIL,
	SRGB,
	SRGBX,
	SRGBA,
	SBGRA,
	ABGR;

	public final int value = ordinal();

	public static SPIRVImageChannelOrder parse(int val) {
		SPIRVImageChannelOrder[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
