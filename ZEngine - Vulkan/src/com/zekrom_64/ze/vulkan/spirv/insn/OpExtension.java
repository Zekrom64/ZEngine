package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpExtension implements SPIRVInstruction {

	public static final int OPCODE = 10;
	
	public String name = "";
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -2;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		name = reader.readString();
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeString(name);
	}

}
