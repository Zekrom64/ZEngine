package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpExtInst implements SPIRVInstruction {

	public static final int OPCODE = 12;
	
	public int resultType;
	public int result;
	public int set;
	public int instruction;
	public int[] operands = new int[0];
	
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
		resultType = reader.readWord();
		result = reader.readWord();
		set = reader.readWord();
		instruction = reader.readWord();
		operands = new int[wordCount - 5];
		for(int i = 0; i < operands.length; i++) operands[i] = reader.readWord();
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		int[] args = new int[operands.length+2];
		System.arraycopy(operands, 0, args, 2, operands.length);
		args[0] = resultType;
		args[1] = set;
		return args;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(set);
		writer.writeWord(instruction);
		for(int op : operands) writer.writeWord(op);
	}

}
