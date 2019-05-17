package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVKernelProfilingInfo {

	CMD_EXEC_TIME;
	
	public final int mask = 1 << ordinal();
	
}
