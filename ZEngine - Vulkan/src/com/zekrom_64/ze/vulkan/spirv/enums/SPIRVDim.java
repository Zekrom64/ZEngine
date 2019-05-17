package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVDim {

	_1D,
	_2D,
	_3D,
	CUBE,
	RECT,
	BUFFER,
	SUBPASS_DATA;
	
	public final int value;
	
	private SPIRVDim() {
		value = ordinal();
	}

	public static SPIRVDim parse(int val) {
		SPIRVDim[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
