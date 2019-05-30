package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTypeFunction implements SPIRVInstruction {

	public static final int OPCODE = 33;
	
	public int result;
	public int returnType;
	public int[] parameterTypes;
	
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
		result = reader.readWord();
		returnType = reader.readWord();
		parameterTypes = new int[wordCount-3];
		for(int i = 0; i < parameterTypes.length; i++) parameterTypes[i] = reader.readWord();
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		int[] args = new int[parameterTypes.length+1];
		System.arraycopy(parameterTypes, 0, args, 1, parameterTypes.length);
		args[0] = returnType;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(returnType);
		for(int param : parameterTypes) writer.writeWord(param);
	}

}
