package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVStorageClass;

public class OpTypePointer implements SPIRVInstruction {

	public static final int OPCODE = 32;
	
	public int result;
	public SPIRVStorageClass storageClass;
	public int type;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 4;
	}

	@Override
	public void operands(int[] ops) {
		result = ops[0];
		storageClass = SPIRVStorageClass.parse(ops[1]);
		type = ops[2];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { type };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(storageClass.value);
		writer.writeWord(type);
	}

}
