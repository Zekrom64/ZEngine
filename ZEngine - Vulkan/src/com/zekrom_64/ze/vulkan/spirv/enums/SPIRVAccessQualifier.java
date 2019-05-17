package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVAccessQualifier {

	READ_ONLY,
	WRITE_ONLY,
	READ_WRITE;
	
	public final int value;
	
	private SPIRVAccessQualifier() {
		value = ordinal();
	}

	public static SPIRVAccessQualifier parse(int val) {
		SPIRVAccessQualifier[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
