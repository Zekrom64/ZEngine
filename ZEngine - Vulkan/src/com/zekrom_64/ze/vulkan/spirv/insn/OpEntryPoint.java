package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVExecutionModel;

public class OpEntryPoint implements SPIRVInstruction {

	public static final int OPCODE = 15;
	
	public SPIRVExecutionModel executionModel;
	public int entryPoint;
	public String name = "";
	public int[] interfaces = new int[0];
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -4;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		int mark = reader.getWordCounter();
		executionModel = SPIRVExecutionModel.parse(reader.readWord());
		entryPoint = reader.readWord();
		name = reader.readString();
		int remaining = (wordCount - 1) - (reader.getWordCounter() - mark);
		interfaces = new int[remaining];
		for(int i = 0; i < remaining; i++) interfaces[i] = reader.readWord();
	}

	@Override
	public int[] getArguments() {
		int[] args = new int[interfaces.length+1];
		System.arraycopy(interfaces, 0, args, 1, interfaces.length);
		args[0] = entryPoint;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		// TODO Auto-generated method stub

	}

}
