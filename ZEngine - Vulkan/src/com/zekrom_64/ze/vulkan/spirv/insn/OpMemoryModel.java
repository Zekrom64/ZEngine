package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVAddressingModel;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVMemoryModel;

public class OpMemoryModel implements SPIRVInstruction {

	public static final int OPCODE = 14;
	
	public SPIRVAddressingModel addressingModel;
	public SPIRVMemoryModel memoryModel;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 3;
	}

	@Override
	public void operands(int[] ops) {
		addressingModel = SPIRVAddressingModel.parse(ops[0]);
		memoryModel = SPIRVMemoryModel.parse(ops[1]);
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(addressingModel.value);
		writer.writeWord(memoryModel.value);
	}

}
