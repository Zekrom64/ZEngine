package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVGroupOperation {

	REDUCE,
	INCLUSIVE_SCAN,
	EXCLUSIVE_SCAN,
	CLUSTERED_REDUCE,
	
	// SPV_NV_shader_subgroup_partitioned
	PARTITIONED_REDUCE_NV(6),
	PARTITIONED_INCLUSIVE_SCAN_NV(7),
	PARTITIONED_EXCLUSIVE_SCAN_NV(8);
	
	public final int value;
	
	private SPIRVGroupOperation() {
		value = ordinal();
	}
	
	private SPIRVGroupOperation(int val) {
		value = val;
	}
	
	private static SPIRVGroupOperation[] linearValues;
	static {
		ArrayList<SPIRVGroupOperation> vals = new ArrayList<>();
		for(SPIRVGroupOperation e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVGroupOperation[0]);
	}
	
	public static SPIRVGroupOperation parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVGroupOperation eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVGroupOperation eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
