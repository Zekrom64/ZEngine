package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVMemoryOperands;

public class OpCooperativeMatrixStoreNV implements SPIRVInstruction {

	public static final int OPCODE = 5360;
	
	public int pointer;
	public int object;
	public int stride;
	public int columnMajor;
	public SPIRVMemoryOperands[] memoryOperands = new SPIRVMemoryOperands[0];
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -5;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		pointer = reader.readWord();
		object = reader.readWord();
		stride = reader.readWord();
		columnMajor = reader.readWord();
		if (wordCount > 5) {
			memoryOperands = SPIRVMemoryOperands.parse(reader.readWord());
		}
	}

	@Override
	public int[] getArguments() {
		return new int[] { pointer, object, stride, columnMajor };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(pointer);
		writer.writeWord(object);
		writer.writeWord(stride);
		writer.writeWord(columnMajor);
		if (memoryOperands.length > 0) writer.writeWord(SPIRVMemoryOperands.value(memoryOperands));
	}

}
