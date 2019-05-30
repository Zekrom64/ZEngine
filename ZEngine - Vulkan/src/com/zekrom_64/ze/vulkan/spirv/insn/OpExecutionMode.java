package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVExecutionMode;

public class OpExecutionMode implements SPIRVInstruction {

	public static final int OPCODE = 16;
	
	public int entryPoint;
	public SPIRVExecutionMode executionMode;
	public int[] executionModeOperands = new int[0];
	
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
		entryPoint = reader.readWord();
		executionMode = SPIRVExecutionMode.parse(reader.readWord());
		executionModeOperands = new int[wordCount - 3];
		for(int i = 0; i < executionModeOperands.length; i++) executionModeOperands[i] = reader.readWord();
	}

	@Override
	public int[] getArguments() {
		return new int[] { entryPoint };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(entryPoint);
		writer.writeWord(executionMode.value);
		for(int op : executionModeOperands) writer.writeWord(op);
	}

}
