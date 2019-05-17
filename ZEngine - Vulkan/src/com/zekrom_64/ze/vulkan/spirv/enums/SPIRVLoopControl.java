package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVLoopControl {

	UNROLL,
	DONT_UNROLL,
	DEPENDENCY_INFINITE,
	DEPENDENCY_LENGTH,
	MIN_ITERATIONS,
	MAX_ITERATIONS,
	ITERATION_MULTIPLE,
	PEEL_COUNT,
	PARTIAL_COUNT;
	
	public final int mask = 1 << ordinal();
	
}
