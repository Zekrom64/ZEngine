package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDecoration;

public class OpMemberDecorate implements SPIRVInstruction {

	public static final int OPCODE = 72;
	
	public int structureType;
	public int member;
	public SPIRVDecoration decoration;
	public int[] decorationOperands;
	
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
		structureType = reader.readWord();
		member = reader.readWord();
		decoration = SPIRVDecoration.parse(reader.readWord());
		decorationOperands = new int[wordCount - 4];
		for(int i = 0; i < decorationOperands.length; i++) decorationOperands[i] = reader.readWord();
	}

	@Override
	public int[] getArguments() {
		return new int[] { structureType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(structureType);
		writer.writeWord(member);
		writer.writeWord(decoration.value);
		for(int deco : decorationOperands) writer.writeWord(deco);
	}
	
	

}
