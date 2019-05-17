package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVImageChannelDataType {

	SNORM_INT8,
	SNORM_INT16,
	UNORM_INT8,
	UNORM_INT16,
	UNORM_SHORT565,
	UNORM_SHORT555,
	UNORM_INT101010,
	SIGNED_INT8,
	SIGNED_INT16,
	SIGNED_INT32,
	UNSIGNED_INT8,
	UNSIGNED_INT16,
	UNSIGNED_INT32,
	HALF_FLOAT,
	FLOAT,
	UNORM_INT24,
	UNORM_INT101010_2;

	public final int value = ordinal();
	
	public static SPIRVImageChannelDataType parse(int val) {
		SPIRVImageChannelDataType[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
