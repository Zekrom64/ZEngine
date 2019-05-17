package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVExecutionModel {

	VERTEX,
	TESSELLATION_CONTROL,
	TESSELLATION_EVALUATION,
	GEOMETRY,
	FRAGMENT,
	GLCOMPUTE,
	KERNEL,
	
	TASK_NV(5267),
	MESH_NV(5268),
	RAY_GENERATION_NV(5313),
	INTERSECTION_NV(5314),
	ANY_HIT_NV(5315),
	CLOSEST_HIT_NV(5316),
	MISS_NV(5317),
	CALLABLE_NV(5318);
	
	public final int value;
	
	private SPIRVExecutionModel() {
		value = ordinal();
	}
	
	private SPIRVExecutionModel(int val) {
		value = val;
	}
	
	private static SPIRVExecutionModel[] linearValues;
	static {
		ArrayList<SPIRVExecutionModel> vals = new ArrayList<>();
		for(SPIRVExecutionModel e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVExecutionModel[0]);
	}
	
	public static SPIRVExecutionModel parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVExecutionModel eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVExecutionModel eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
