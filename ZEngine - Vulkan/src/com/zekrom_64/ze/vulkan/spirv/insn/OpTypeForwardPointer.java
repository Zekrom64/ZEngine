package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVStorageClass;

public class OpTypeForwardPointer implements SPIRVInstruction {

	public static final int OPCODE = 39;
	
	public int pointerType;
	public SPIRVStorageClass storageClass;
	
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
		pointerType = ops[0];
		storageClass = SPIRVStorageClass.parse(ops[1]);
	}

	@Override
	public int[] getArguments() {
		return new int[] { pointerType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(pointerType);
		writer.writeWord(storageClass.value);
	}

}
