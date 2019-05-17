package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVKernelEnqueueFlags {

	NO_WAIT,
	WAIT_KERNEL,
	WAIT_WORK_GROUP;

	public final int value = ordinal();
	
	public static SPIRVKernelEnqueueFlags parse(int val) {
		SPIRVKernelEnqueueFlags[] langs = values();
		if (val < 0 || val >= langs.length) return null;
		return langs[val];
	}
	
}
