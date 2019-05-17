package com.zekrom_64.ze.vulkan.spirv.enums;

public enum SPIRVMemorySemantics {

	ACQUIRE(0x2),
	RELEASE(0x4),
	ACQUIRE_RELEASE(0x8),
	SEQUENTIALLY_CONSISTENT(0x10),
	UNIFORM_MEMORY(0x40),
	SUBGROUP_MEMORY(0x80),
	WORKGROUP_MEMORY(0x100),
	CROSS_WORKGROUP_MEMORY(0x200),
	ATOMIC_COUNTER_MEMORY(0x400),
	IMAGE_MEMORY(0x800),
	OUTPUT_MEMORY_KHR(0x1000),
	MAKE_AVAILABLE_KHR(0x2000),
	MAKE_VISIBLE_KHR(0x4000);
	
	public final int mask;
	
	private SPIRVMemorySemantics(int mask) {
		this.mask = mask;
	}
	
}
