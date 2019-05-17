package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpLine implements SPIRVInstruction {

	public static final int OPCODE = 8;
	
	public int file;
	public int line;
	public int column;
	
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
		file = ops[0];
		line = ops[1];
		column = ops[2];
	}

	@Override
	public int[] getArguments() {
		return new int[] { file };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(file);
		writer.writeWord(line);
		writer.writeWord(column);
	}
	
	

}
