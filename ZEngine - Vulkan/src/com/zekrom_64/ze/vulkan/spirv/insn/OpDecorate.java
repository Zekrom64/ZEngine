package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDecoration;

public class OpDecorate implements SPIRVInstruction {

	public static final int OPCODE = 71;
	
	public int target;
	public SPIRVDecoration decoration;
	public int[] decorationOperands;
	
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
		target = reader.readWord();
		decoration = SPIRVDecoration.parse(reader.readWord());
		decorationOperands = new int[wordCount - 3];
		for(int i = 0; i < decorationOperands.length; i++) decorationOperands[i] = reader.readWord();
	}

	@Override
	public int[] getArguments() {
		return new int[] { target };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(target);
		writer.writeWord(decoration.value);
		for(int deco : decorationOperands) writer.writeWord(deco);
	}

}
