package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpCooperativeMatrixMulAddNV implements SPIRVInstruction {

	public static final int OPCODE = 5361;
	
	public int resultType;
	public int result;
	public int a, b, c;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 6;
	}

	@Override
	public void operands(int[] ops) {
		resultType = ops[0];
		result = ops[1];
		a = ops[2];
		b = ops[3];
		c = ops[4];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType, a, b, c };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(a);
		writer.writeWord(b);
		writer.writeWord(c);
	}

}
