package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpNop implements SPIRVInstruction {

	public static final int OPCODE = 0;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException { }

}
