package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVCapability;

public class OpCapability implements SPIRVInstruction {

	public static final int OPCODE = 17;
	
	public SPIRVCapability capability;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 2;
	}

	@Override
	public void operands(int[] ops) {
		capability = SPIRVCapability.parse(ops[0]);
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(capability.value);
	}

}
