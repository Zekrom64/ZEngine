package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeFloat implements SPIRVInstruction {

	public static final int OPCODE = 22;
	
	public int result;
	public int width;
	
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
		result = ops[0];
		width = ops[1];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(width);
	}

}
