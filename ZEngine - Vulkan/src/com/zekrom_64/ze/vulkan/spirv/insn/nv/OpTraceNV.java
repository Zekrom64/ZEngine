package com.zekrom_64.ze.vulkan.spirv.insn.nv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;

public class OpTraceNV implements SPIRVInstruction {

	public static final int OPCODE = 5337;
	
	public int accel;
	public int rayFlags;
	public int cullMask;
	public int sbtOffset;
	public int sbtStride;
	public int missIndex;
	public int rayOrigin;
	public int rayTMin;
	public int rayDirection;
	public int rayTMax;
	public int payloadID;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return 12;
	}

	@Override
	public void operands(int[] ops) {
		accel = ops[0];
		rayFlags = ops[1];
		cullMask = ops[2];
		sbtOffset = ops[3];
		sbtStride = ops[4];
		missIndex = ops[5];
		rayOrigin = ops[6];
		rayTMin = ops[7];
		rayDirection = ops[8];
		rayTMax = ops[9];
		payloadID = ops[10];
	}

	@Override
	public int[] getArguments() {
		return new int[] {
			accel,
			rayFlags,
			cullMask,
			sbtOffset,
			sbtStride,
			missIndex,
			rayOrigin,
			rayTMin,
			rayDirection,
			rayTMax,
			payloadID
		};
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(accel);
		writer.writeWord(rayFlags);
		writer.writeWord(cullMask);
		writer.writeWord(sbtOffset);
		writer.writeWord(sbtStride);
		writer.writeWord(missIndex);
		writer.writeWord(rayOrigin);
		writer.writeWord(rayTMin);
		writer.writeWord(rayDirection);
		writer.writeWord(rayTMax);
		writer.writeWord(payloadID);
	}

}
