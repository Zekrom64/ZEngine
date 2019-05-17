package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpSourceContinued implements SPIRVInstruction {

	public static final int OPCODE = 2;
	
	public String source = "";
	
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
		source = reader.readString();
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeString(source);
	}
	
	

}
