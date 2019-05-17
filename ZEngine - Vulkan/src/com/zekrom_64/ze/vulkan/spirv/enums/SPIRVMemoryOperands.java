package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;
import java.util.List;

public enum SPIRVMemoryOperands {

	VOLATILE(0x1),
	ALIGNED(0x2),
	NONTEMPORAL(0x4),
	MAKE_POINTER_AVAILABLE_KHR(0x8),
	MAKE_POINTER_VISIBLE_KHR(0x10),
	NON_PRIVATE_POINTER_KHR(0x20);
	
	public final int mask;
	
	private SPIRVMemoryOperands(int mask) {
		this.mask = mask;
	}
	
	public static SPIRVMemoryOperands[] parse(int mask) {
		List<SPIRVMemoryOperands> vals = new ArrayList<>();
		for(SPIRVMemoryOperands val : SPIRVMemoryOperands.values())
			if ((mask & val.mask) == val.mask) vals.add(val);
		return vals.toArray(new SPIRVMemoryOperands[0]);
	}
	
	public static int value(SPIRVMemoryOperands ... vals) {
		int value = 0;
		for(SPIRVMemoryOperands val : vals) value |= val.mask;
		return value;
	}
	
}
