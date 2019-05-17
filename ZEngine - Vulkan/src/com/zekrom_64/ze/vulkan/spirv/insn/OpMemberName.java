package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpMemberName implements SPIRVInstruction {

	public static final int OPCODE = 6;
	
	public int type;
	public int member;
	public String name = "";
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -4;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		type = reader.readWord();
		member = reader.readWord();
		name = reader.readString();
	}

	@Override
	public int[] getArguments() {
		return new int[] { type };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(type);
		writer.writeWord(member);
		writer.writeString(name);
	}

}
