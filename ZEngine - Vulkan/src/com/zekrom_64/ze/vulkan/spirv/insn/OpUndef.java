package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpUndef implements SPIRVInstruction {

	public static final int OPCODE = 1;
	
	public int resultType;
	public int result;
	
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
		resultType = ops[0];
		result = ops[1];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
	}

}
