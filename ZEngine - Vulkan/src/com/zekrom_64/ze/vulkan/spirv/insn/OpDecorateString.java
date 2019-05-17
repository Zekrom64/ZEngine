package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDecoration;

public class OpDecorateString implements SPIRVInstruction {

	public static final int OPCODE = 5632;
	
	public int target;
	public SPIRVDecoration decoration;
	public String[] decorationOperands;
	
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
		target = reader.readWord();
		decoration = SPIRVDecoration.parse(reader.readWord());
		int mark = reader.getWordCounter();
		
		List<String> strings = new ArrayList<>();
		do {
			strings.add(reader.readString());
		} while(reader.getWordCounter() - mark < wordCount - 3);
		decorationOperands = strings.toArray(new String[0]);
	}

	@Override
	public int[] getArguments() {
		return new int[] { target };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(target);
		writer.writeWord(decoration.value);
		for(String op : decorationOperands) writer.writeString(op);
	}

}
