package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeInt implements SPIRVInstruction {

	public static final int OPCODE = 21;
	
	public int result;
	public int width;
	public boolean signedness;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 4;
	}

	@Override
	public void operands(int[] ops) {
		result = ops[0];
		width = ops[1];
		signedness = ops[2] != 0;
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(width);
		writer.writeWord(signedness ? 1 : 0);
	}

}
