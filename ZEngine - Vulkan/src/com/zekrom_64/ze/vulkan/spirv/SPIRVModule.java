package com.zekrom_64.ze.vulkan.spirv;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SPIRVModule implements SPIRVObject {

	/** The magic number identifying the SPIR-V module. */
	public static final int MAGIC_NUMBER = 0x07230203;
	
	/** Generator magic value for Khronos. */
	public static final int GENERATOR_KHRONOS = 0;
	/** Generator magic value for LunarG. */
	public static final int GENERATOR_LUNARG = 1;
	/** Generator magic value for Valve. */
	public static final int GENERATOR_VALVE = 2;
	/** Generator magic value for Codeplay. */
	public static final int GENERATOR_CODEPLAY = 3;
	/** Generator magic value for NVIDIA. */
	public static final int GENERATOR_NVIDIA = 4;
	/** Generator magic value for ARM. */
	public static final int GENERATOR_ARM = 5;
	/** Generator magic value for the LLVM/SPIR-V translator from Khronos. */
	public static final int GENERATOR_KHRONOS_LLVM2SPIRV = 6;
	/** Generator magic value for the SPIR-V assembler from Khronos. */
	public static final int GENERATOR_KHRONOS_SPIRV_ASM = 7;
	/** Generator magic value for the Glsland tool from Khronos. */
	public static final int GENERATOR_KHRONOS_GLSLANG = 8;
	/** Generator magic value for Qualcomm. */
	public static final int GENERATOR_QUALCOMM = 9;
	/** Generator magic value for AMD. */
	public static final int GENERATOR_AMD = 10;
	/** Generator magic value for Intel. */
	public static final int GENERATOR_INTEL = 11;
	
	/** The SPIR-V header is located at the start of the SPIR-V module and stores information
	 * about the module and instructions.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class SPIRVHeader implements SPIRVObject {
		
		/** The magic value identifying the SPIR-V module, should be {@link SPIRVModule#MAGIC_NUMBER}.*/
		public int magic;
		/** The version number, stored in bytes from high to low:<br/>
		 * <tt> 0 | Major Number | Minor Number | 0</tt>
		 */
		public int version;
		/** The magic value for the tool or organization that generated the module. */
		public int generatorMagic;
		/** Bound from all IDs, where all IDs in the module will be less than this value. */
		public int bound;
		/** Normally 0, reserved for an instruction schema. */
		public int insnSchema;
		
		@Override
		public void read(SPIRVReader reader) throws IOException {
			magic = reader.readWord();
			version = reader.readWord();
			generatorMagic = reader.readWord();
			bound = reader.readWord();
			insnSchema = reader.readWord();
		}

		@Override
		public void write(SPIRVWriter writer) throws IOException {
			writer.writeWord(magic);
			writer.writeWord(version);
			writer.writeWord(generatorMagic);
			writer.writeWord(bound);
			writer.writeWord(insnSchema);
		}
		
	}
	
	/** The header for the module. */
	public final SPIRVHeader header = new SPIRVHeader();
	/** The sequence of instructions composing the module. */
	public final List<SPIRVInstruction> instructions = new ArrayList<>();
	
	@Override
	public void read(SPIRVReader reader) throws IOException {
		instructions.clear();
		header.read(reader);
		SPIRVInstruction insn;
		while ((insn = SPIRVInstruction.readInstruction(reader)) != null) instructions.add(insn);
	}

	@Override
	public void write(SPIRVWriter writer) throws IOException {
		header.write(writer);
		for(SPIRVInstruction insn : instructions) insn.write(writer);
	}

}
