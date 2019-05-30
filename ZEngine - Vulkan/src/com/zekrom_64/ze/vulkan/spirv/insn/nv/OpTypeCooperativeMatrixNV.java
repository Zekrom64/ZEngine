package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVScope;

public class OpTypeCooperativeMatrixNV implements SPIRVInstruction {

	public static final int OPCODE = 5358;
	
	public int result;
	public int componentType;
	public SPIRVScope execution;
	public int rows;
	public int columns;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 6;
	}

	@Override
	public void operands(int[] ops) {
		result = ops[0];
		componentType = ops[1];
		execution = SPIRVScope.parse(ops[2]);
		rows = ops[3];
		columns = ops[4];
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { componentType, rows, columns };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(componentType);
		writer.writeWord(execution.value);
		writer.writeWord(rows);
		writer.writeWord(columns);
	}

}
