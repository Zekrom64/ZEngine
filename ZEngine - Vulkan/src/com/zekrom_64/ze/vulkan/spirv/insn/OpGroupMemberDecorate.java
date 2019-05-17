package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

@Deprecated
public class OpGroupMemberDecorate implements SPIRVInstruction {

	public static final int OPCODE = 75;
	
	public int decorationGroup;
	public int targetID[];
	public int targetMember[];
	
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
		int pairCount = (wordCount - 2) / 2;
		targetID = new int[pairCount];
		targetMember = new int[pairCount];
		for(int i = 0; i < pairCount; i++) {
			targetID[i] = reader.readWord();
			targetMember[i] = reader.readWord();
		}
	}

	@Override
	public int[] getArguments() {
		int[] args = new int[targetID.length+1];
		System.arraycopy(targetID, 0, args, 1, targetID.length);
		args[0] = decorationGroup;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(decorationGroup);
		for(int i = 0; i < targetID.length; i++) {
			writer.writeWord(targetID[i]);
			writer.writeWord(targetMember[i]);
		}
	}

}
