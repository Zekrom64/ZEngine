package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeArray implements SPIRVInstruction {

	public static final int OPCODE = 28;
	
	public int result;
	public int elementType;
	public int length;
	
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
		elementType = ops[1];
		length = ops[2];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { elementType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(elementType);
		writer.writeWord(length);
	}

}
