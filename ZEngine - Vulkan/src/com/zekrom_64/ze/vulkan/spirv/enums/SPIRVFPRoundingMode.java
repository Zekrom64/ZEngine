package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVFPRoundingMode {

	ROUND_TO_EVEN,
	ROUND_TO_ZERO,
	ROUND_TO_POSITIVE_INF,
	ROUND_TO_NEGATIVE_INF;
	
	public final int value = ordinal();

	public static SPIRVFPRoundingMode parse(int val) {
		SPIRVFPRoundingMode[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
