package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDecoration;

public class OpDecorateId implements SPIRVInstruction {

	public static final int OPCODE = 332;
	
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
		int args[] = new int[decorationOperands.length+1];
		System.arraycopy(decorationOperands, 0, args, 1, decorationOperands.length);
		args[0] = target;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		// TODO Auto-generated method stub

	}

}
