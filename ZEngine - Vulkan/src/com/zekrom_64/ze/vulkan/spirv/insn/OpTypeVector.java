package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeVector implements SPIRVInstruction {

	public static final int OPCODE = 23;
	
	public int result;
	public int componentType;
	public int componentCount;
	
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
		componentType = ops[1];
		componentCount = ops[2];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { componentType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(componentType);
		writer.writeWord(componentCount);
	}

}
