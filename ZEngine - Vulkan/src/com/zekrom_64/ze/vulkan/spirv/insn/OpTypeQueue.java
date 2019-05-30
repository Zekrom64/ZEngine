package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeQueue implements SPIRVInstruction {

	public static final int OPCODE = 37;
	
	public int result;
	
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
		result = ops[0];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
	}

}
