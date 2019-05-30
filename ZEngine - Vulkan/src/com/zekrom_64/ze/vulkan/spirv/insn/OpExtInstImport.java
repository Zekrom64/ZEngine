package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpExtInstImport implements SPIRVInstruction {

	public static final int OPCODE = 11;
	
	public int result;
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
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		result = reader.readWord();
		name = reader.readString();
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeString(name);
	}

}
