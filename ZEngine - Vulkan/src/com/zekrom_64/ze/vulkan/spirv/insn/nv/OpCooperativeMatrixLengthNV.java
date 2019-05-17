package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpCooperativeMatrixLengthNV implements SPIRVInstruction {

	public static final int OPCODE = 5362;
	
	public int resultType;
	public int result;
	public int type;
	
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
		resultType = ops[0];
		result = ops[1];
		type = ops[2];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType, type };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(type);
	}

}
