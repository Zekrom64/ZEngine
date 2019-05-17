package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpIgnoreIntersectionNV implements SPIRVInstruction {

	public static final int OPCODE = 5335;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException { }

}
