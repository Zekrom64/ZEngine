package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpExecuteCallableNV implements SPIRVInstruction {

	public static final int OPCODE = 5344;
	
	public int sbtIndex;
	public int callableDataID;
	
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
		sbtIndex = ops[0];
		callableDataID = ops[1];
	}

	@Override
	public int[] getArguments() {
		return new int[] { sbtIndex, callableDataID };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(sbtIndex);
		writer.writeWord(callableDataID);
	}

}
