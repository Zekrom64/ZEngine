package com.zekrom_64.ze.vulkan.spirv.insn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.zekrom_64.ze.vulkan.spirv.SPIRVInstruction;
import com.zekrom_64.ze.vulkan.spirv.SPIRVReader;
import com.zekrom_64.ze.vulkan.spirv.SPIRVWriter;
import com.zekrom_64.ze.vulkan.spirv.enums.SPIRVDecoration;

public class OpMemberDecorateString implements SPIRVInstruction {

	public static final int OPCODE = 5633;
	
	public int structType;
	public int member;
	public SPIRVDecoration decoration;
	public String[] decorationOperands;
	
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
		structType = reader.readWord();
		member = reader.readWord();
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
		return new int[] { structType };
	}

	@Override
	public void writeBody(SPIRVWriter writer) throws IOException {
		writer.writeWord(structType);
		writer.writeWord(member);
		writer.writeWord(decoration.value);
		for(String op : decorationOperands) writer.writeString(op);
	}

}
