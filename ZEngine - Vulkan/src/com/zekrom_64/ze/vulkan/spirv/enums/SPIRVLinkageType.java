package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVLinkageType {

	EXPORT,
	IMPORT;

	public final int value = ordinal();

	public static SPIRVLinkageType parse(int val) {
		SPIRVLinkageType[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
