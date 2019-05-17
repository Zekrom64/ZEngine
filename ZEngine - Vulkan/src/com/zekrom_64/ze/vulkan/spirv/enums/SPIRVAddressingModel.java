package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVAddressingModel {

	LOGICAL,
	PHYSICAL32,
	PHYSICAL64,
	PHYSICAL_STORAGE_BUFFER_64_EXT(5348);
	
	public final int value;
	
	private SPIRVAddressingModel() {
		value = ordinal();
	}
	
	private SPIRVAddressingModel(int val) {
		value = val;
	}
	
	private static SPIRVAddressingModel[] linearValues;
	static {
		ArrayList<SPIRVAddressingModel> vals = new ArrayList<>();
		for(SPIRVAddressingModel e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVAddressingModel[0]);
	}
	
	public static SPIRVAddressingModel parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVAddressingModel eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVAddressingModel eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
