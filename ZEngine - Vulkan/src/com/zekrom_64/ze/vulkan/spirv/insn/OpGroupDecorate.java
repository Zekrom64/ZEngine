package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

@Deprecated
public class OpGroupDecorate implements SPIRVInstruction {

	public static final int OPCODE = 74;
	
	public int decorationGroup;
	public int[] targets;
	
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
		decorationGroup = reader.readWord();
		targets = new int[wordCount - 2];
		for(int i = 0; i < targets.length; i++) targets[i] = reader.readWord();
	}

	@Override
	public int[] getArguments() {
		int[] args = new int[targets.length+1];
		System.arraycopy(targets, 0, args, 1, targets.length);
		args[0] = decorationGroup;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(decorationGroup);
		for(int targ : targets) writer.writeWord(targ);
	}

}
