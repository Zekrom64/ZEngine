package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeMatrix implements SPIRVInstruction {

	public static final int OPCODE = 24;
	
	public int result;
	public int columnType;
	public int columnCount;
	
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
		columnType = ops[1];
		columnCount = ops[2];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { columnType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(columnType);
		writer.writeWord(columnCount);;
	}

}
