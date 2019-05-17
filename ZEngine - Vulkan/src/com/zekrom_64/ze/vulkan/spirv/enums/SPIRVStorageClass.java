package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVStorageClass {

	UNIFORM_CONSTANT,
	INPUT,
	UNIFORM,
	OUTPUT,
	WORKGROUP,
	CROSS_WORKGROUP,
	PRIVATE,
	FUNCTION,
	GENERIC,
	PUSH_CONSTANT,
	ATOMIC_COUNTER,
	IMAGE,
	STORAGE_BUFFER,
	
	// SPV_NV_ray_tracing
	CALLABLE_DATA_NV(5328),
	INCOMING_CALLABLE_DATA_NV(5329),
	RAY_PAYLOAD_NV(5338),
	HIT_ATTRIBUTE_NV(5339),
	INCOMING_RAY_PAYLOAD_NV(5324),
	SHADER_RECORD_BUFFER_NV(5343),
	
	// SPV_EXT_physical_storage_buffer
	PHYSICAL_STORAGE_BUFFER_NV(5349);
	
	public final int value;
	
	private SPIRVStorageClass() {
		value = ordinal();
	}
	
	private SPIRVStorageClass(int val) {
		value = val;
	}
	
	private static SPIRVStorageClass[] linearValues;
	static {
		ArrayList<SPIRVStorageClass> vals = new ArrayList<>();
		for(SPIRVStorageClass e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVStorageClass[0]);
	}
	
	public static SPIRVStorageClass parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVStorageClass eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVStorageClass eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
