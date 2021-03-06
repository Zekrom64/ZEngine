package com.zekrom_64.ze.vulkan.spirv.insn.amd;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpFragmentMaskFetchAMD implements SPIRVInstruction {

	public static final int OPCODE = 5011;
	
	public int resultType;
	public int result;
	public int image;
	public int coordinate;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 5;
	}

	@Override
	public void operands(int[] ops) {
		resultType = ops[0];
		result = ops[1];
		image = ops[2];
		coordinate = ops[3];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType, image, coordinate };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(image);
		writer.writeWord(coordinate);
	}

}
