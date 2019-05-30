package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeStruct implements SPIRVInstruction {

	public static final int OPCODE = 30;
	
	public int result;
	public int[] members;
	
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
		result = reader.readWord();
		members = new int[wordCount - 2];
		for(int i = 0; i < members.length; i++) members[i] = reader.readWord();
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return members;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		for(int member : members) writer.writeWord(member);
	}

}
