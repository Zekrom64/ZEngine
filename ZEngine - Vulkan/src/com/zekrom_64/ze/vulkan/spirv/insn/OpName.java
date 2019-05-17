package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpName implements SPIRVInstruction {

	public static final int OPCODE = 5;
	
	public int target;
	public String name = "";
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -3;
	}

	@Override
	public int[] getArguments() {
		return new int[] { target };
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		target = reader.readWord();
		name = reader.readString();
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(target);
		writer.writeString(name);
	}

}
