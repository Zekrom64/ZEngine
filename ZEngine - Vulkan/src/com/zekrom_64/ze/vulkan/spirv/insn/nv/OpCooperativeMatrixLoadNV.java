package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVMemoryOperands;

public class OpCooperativeMatrixLoadNV implements SPIRVInstruction {

	public static final int OPCODE = 5359;
	
	public int resultType;
	public int result;
	public int pointer;
	public int stride;
	public int columnMajor;
	public SPIRVMemoryOperands[] memoryOperands = new SPIRVMemoryOperands[0];
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -6;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		resultType = reader.readWord();
		result = reader.readWord();
		pointer = reader.readWord();
		stride = reader.readWord();
		columnMajor = reader.readWord();
		if (wordCount > 6) {
			memoryOperands = SPIRVMemoryOperands.parse(reader.readWord());
		}
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType, pointer, stride, columnMajor };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(pointer);
		writer.writeWord(stride);
		writer.writeWord(columnMajor);
		if (memoryOperands.length > 0) writer.writeWord(SPIRVMemoryOperands.value(memoryOperands));
	}

}
