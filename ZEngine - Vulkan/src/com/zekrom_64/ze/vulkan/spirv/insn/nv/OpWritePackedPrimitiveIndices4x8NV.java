package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpWritePackedPrimitiveIndices4x8NV implements SPIRVInstruction {

	public static final int OPCODE = 5299;
	
	public int indexOffset;
	public int packedIndices;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 3;
	}

	@Override
	public void operands(int[] ops) {
		indexOffset = ops[0];
		packedIndices = ops[1];
	}

	@Override
	public int[] getArguments() {
		return new int[] { indexOffset, packedIndices };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(indexOffset);
		writer.writeWord(packedIndices);
	}

}
