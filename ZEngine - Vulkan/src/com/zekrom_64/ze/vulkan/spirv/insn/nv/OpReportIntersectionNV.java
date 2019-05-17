package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpReportIntersectionNV implements SPIRVInstruction {

	public static final int OPCODE = 5334;
	
	public int resultType;
	public int result;
	public int hit;
	public int hitKind;
	
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
		hit = ops[2];
		hitKind = ops[3];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { resultType, hit, hitKind };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(resultType);
		writer.writeWord(result);
		writer.writeWord(hit);
		writer.writeWord(hitKind);
	}

}
