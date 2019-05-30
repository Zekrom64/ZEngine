package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVAccessQualifier;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDim;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVImageFormat;

public class OpTypeImage implements SPIRVInstruction {

	public static final int OPCODE = 25;
	
	public int result;
	public int sampledType;
	public SPIRVDim dim;
	
	public enum Depth {
		NO_DEPTH,
		DEPTH,
		UNKNOWN;
		public static Depth parse(int value) {
			if (value < 0 || value > 2) return null;
			return values()[value];
		}
	}
	public Depth depth;
	
	public boolean arrayed;
	public boolean multisampled;
	
	public enum Sampled {
		COMPLE_TIME_UNKNOWN,
		SAMPLED_IMAGE,
		STORAGE_IMAGE;
		public static Sampled parse(int value) {
			if (value < 0 || value > 2) return null;
			return values()[value];
		}
	}
	public Sampled sampled;
	
	public SPIRVImageFormat imageFormat;
	public SPIRVAccessQualifier accessQualifier;
	
	@Override
	public int opcode() {
		return OPCODE;
	}

	@Override
	public int wordCount() {
		return -9;
	}

	@Override
	public void read(SPIRVReader reader, int wordCount) throws IOException {
		result = reader.readWord();
		sampledType = reader.readWord();
		dim = SPIRVDim.parse(reader.readWord());
		depth = Depth.parse(reader.readWord());
		arrayed = reader.readWord() != 0;
		multisampled = reader.readWord() != 0;
		sampled = Sampled.parse(reader.readWord());
		imageFormat = SPIRVImageFormat.parse(reader.readWord());
		if (wordCount > 9) accessQualifier = SPIRVAccessQualifier.parse(reader.readWord());
	}

	@Override
	public int getResult() {
		return result;
	}

	@Override
	public int[] getArguments() {
		return new int[] { sampledType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(result);
		writer.writeWord(sampledType);
		writer.writeWord(dim.value);
		writer.writeWord(depth.ordinal());
		writer.writeWord(arrayed ? 1 : 0);
		writer.writeWord(multisampled ? 1 : 0);
		writer.writeWord(sampled.ordinal());
		writer.writeWord(imageFormat.value);
		if (accessQualifier != null) writer.writeWord(accessQualifier.value);
	}

}
