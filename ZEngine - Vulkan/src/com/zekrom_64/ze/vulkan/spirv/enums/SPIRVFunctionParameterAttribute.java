package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVFunctionParameterAttribute {

	ZERO_EXTEND,
	SIGN_EXTEND,
	BY_VAL,
	STRUCT_RETURN,
	NO_ALIAS,
	NO_CAPTURE,
	NO_WRITE,
	NO_READ_WRITE;

	public final int value = ordinal();
	
	public static SPIRVFunctionParameterAttribute parse(int val) {
		SPIRVFunctionParameterAttribute[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
