package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVSourceLanguage;

public class OpSource implements SPIRVInstruction {

	public static final int OPCODE = 3;
	
	public SPIRVSourceLanguage sourceLanguage;
	public int version;
	public Integer file;
	public String source;
	
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
		sourceLanguage = SPIRVSourceLanguage.parse(reader.readWord());
		version = reader.readWord();
		if (wordCount > 3) {
			file = Integer.valueOf(reader.readWord());
			if(wordCount > 4) {
				source = reader.readString();
			}
		}
	}

	@Override
	public int[] getArguments() {
		return file != null ? new int[] { file } : new int[0];
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(sourceLanguage.value);
		writer.writeWord(version);
		if (file != null) {
			writer.writeWord(file);
			if (source != null) writer.writeString(source);
		}
	}

}
