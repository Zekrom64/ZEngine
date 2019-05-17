package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

@Deprecated
public class OpDecorationGroup implements SPIRVInstruction {

	public static final int OPCODE = 73;
	
	public int result;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
	}

}
