package com.zekrom_64.ze.vulkan.spirv;

import java.io.IOException;

import com.zekrom_64.ze.vulkan.spirv.insn.OpDecorate;
import com.zekrom_64.ze.vulkan.spirv.insn.OpDecorateId;
import com.zekrom_64.ze.vulkan.spirv.insn.OpDecorateString;
import com.zekrom_64.ze.vulkan.spirv.insn.OpDecorationGroup;
import com.zekrom_64.ze.vulkan.spirv.insn.OpGroupDecorate;
import com.zekrom_64.ze.vulkan.spirv.insn.OpGroupMemberDecorate;
import com.zekrom_64.ze.vulkan.spirv.insn.OpLine;
import com.zekrom_64.ze.vulkan.spirv.insn.OpMemberDecorate;
import com.zekrom_64.ze.vulkan.spirv.insn.OpMemberDecorateString;
import com.zekrom_64.ze.vulkan.spirv.insn.OpMemberName;
import com.zekrom_64.ze.vulkan.spirv.insn.OpModuleProcessed;
import com.zekrom_64.ze.vulkan.spirv.insn.OpName;
import com.zekrom_64.ze.vulkan.spirv.insn.OpNoLine;
import com.zekrom_64.ze.vulkan.spirv.insn.OpNop;
import com.zekrom_64.ze.vulkan.spirv.insn.OpSizeOf;
import com.zekrom_64.ze.vulkan.spirv.insn.OpSource;
import com.zekrom_64.ze.vulkan.spirv.insn.OpSourceContinued;
import com.zekrom_64.ze.vulkan.spirv.insn.OpSourceExtension;
import com.zekrom_64.ze.vulkan.spirv.insn.OpString;
import com.zekrom_64.ze.vulkan.spirv.insn.OpUndef;
import com.zekrom_64.ze.vulkan.spirv.insn.amd.OpFragmentFetchAMD;
import com.zekrom_64.ze.vulkan.spirv.insn.amd.OpFragmentMaskFetchAMD;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpCooperativeMatrixLengthNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpCooperativeMatrixLoadNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpCooperativeMatrixMulAddNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpCooperativeMatrixStoreNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpExecuteCallableNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpIgnoreIntersectionNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpReportIntersectionNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpTerminateRayNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpTraceNV;
import com.zekrom_64.ze.vulkan.spirv.insn.nv.OpWritePackedPrimitiveIndices4x8NV;

public interface SPIRVInstruction {

	public static class SPIRVUnknownInstruction implements SPIRVInstruction {

		private int op;
		private int[] operands;
		
		public SPIRVUnknownInstruction(int op) {
			this.op = op;
		}

		@Override
		public int opcode() {
			return op;
		}

		@Override
		public void operands(int[] ops) {
			operands = ops;
		}

		@Override
		public void writeBody(SPIRVWriter writer) throws IOException {
			for(int op : operands) writer.writeWord(op);
		}
		
	}
	
	/** Reads the next instruction from an IntReader. Returns the
	 * parsed instruction, or <b>null</b> if the end of stream has
	 * been reached.
	 * 
	 * @param reader The SPIR-V reader
	 * @return Parsed instruction, or <b>null</b>
	 * @throws IOException If an IOException occurs
	 */
	@SuppressWarnings("deprecation")
	public static SPIRVInstruction readInstruction(SPIRVReader reader) throws IOException {
		long insn = reader.read();
		if (insn == -1) return null;
		int wordCount = (int)(insn >> 16);
		
		int[] ops = new int[wordCount];
		for(int i = 0; i < wordCount-1; i++) {
			long op = reader.read();
			if (op == -1) throw new IOException("Unexpected end of stream");
			ops[i] = (int)op;
		}
		
		SPIRVInstruction instr = null;
		switch((int)(insn & 0xFFFF)) {
		// Miscellaneous Instructions
		case OpNop.OPCODE: instr = new OpNop(); break;
		case OpUndef.OPCODE: instr = new OpUndef(); break;
		case OpSizeOf.OPCODE: instr = new OpSizeOf(); break;
		case OpFragmentMaskFetchAMD.OPCODE: instr = new OpFragmentMaskFetchAMD(); break;
		case OpFragmentFetchAMD.OPCODE: instr = new OpFragmentFetchAMD(); break;
		case OpWritePackedPrimitiveIndices4x8NV.OPCODE: instr = new OpWritePackedPrimitiveIndices4x8NV(); break;
		case OpReportIntersectionNV.OPCODE: instr = new OpReportIntersectionNV(); break;
		case OpIgnoreIntersectionNV.OPCODE: instr = new OpIgnoreIntersectionNV(); break;
		case OpTerminateRayNV.OPCODE: instr = new OpTerminateRayNV(); break;
		case OpTraceNV.OPCODE: instr = new OpTraceNV(); break;
		case OpExecuteCallableNV.OPCODE: instr = new OpExecuteCallableNV(); break;
		case OpCooperativeMatrixLoadNV.OPCODE: instr = new OpCooperativeMatrixLoadNV(); break;
		case OpCooperativeMatrixStoreNV.OPCODE: instr = new OpCooperativeMatrixStoreNV(); break;
		case OpCooperativeMatrixMulAddNV.OPCODE: instr = new OpCooperativeMatrixMulAddNV(); break;
		case OpCooperativeMatrixLengthNV.OPCODE: instr = new OpCooperativeMatrixLengthNV(); break;
		
		// Debug Instructions
		case OpSourceContinued.OPCODE: instr = new OpSourceContinued(); break;
		case OpSource.OPCODE: instr = new OpSource(); break;
		case OpSourceExtension.OPCODE: instr = new OpSourceExtension(); break;
		case OpName.OPCODE: instr = new OpName(); break;
		case OpMemberName.OPCODE: instr = new OpName(); break;
		case OpString.OPCODE: instr = new OpString(); break;
		case OpLine.OPCODE: instr = new OpLine(); break;
		case OpNoLine.OPCODE: instr = new OpNoLine(); break;
		case OpModuleProcessed.OPCODE: instr = new OpModuleProcessed(); break;
		
		// Annotation Instructions
		case OpDecorate.OPCODE: instr = new OpDecorate(); break;
		case OpMemberDecorate.OPCODE: instr = new OpMemberDecorate(); break;
		case OpDecorationGroup.OPCODE: instr = new OpDecorationGroup(); break;
		case OpGroupDecorate.OPCODE: instr = new OpGroupDecorate(); break;
		case OpGroupMemberDecorate.OPCODE: instr = new OpGroupMemberDecorate(); break;
		case OpDecorateId.OPCODE: instr = new OpDecorateId(); break;
		case OpDecorateString.OPCODE: instr = new OpDecorateString(); break;
		case OpMemberDecorateString.OPCODE: instr = new OpMemberDecorateString(); break;
		default: instr = new SPIRVUnknownInstruction((int)(insn & 0xFFFF)); break;
		
		// Extension Instructions
		}
		
		instr.read(reader, wordCount);
		return instr;
	}
	
	/** Gets the opcode for this instruction.
	 * 
	 * @return Instruction opcode
	 */
	public int opcode();
	
	/** Gets the number of words in this instruction. If negative, the length
	 * is variable and the absolute value is the number of words always 
	 * 
	 * @return Instruction word count
	 */
	public default int wordCount() {
		return 1;
	}
	
	/** Loads this instruction with the given array of operands following
	 * the instruction.
	 * 
	 * @param ops Instruction operands
	 */
	public default void operands(int[] ops) { }

	/** Reads the instruction from a reader with it containing the given
	 * number of words.
	 * 
	 * @param reader SPIR-V reader
	 * @param wordCount Number of words in instruction
	 * @throws IOException If an IOException occurs
	 */
	public default void read(SPIRVReader reader, int wordCount) throws IOException {
		int wc = wordCount();
		if (wc < 0) throw new IOException("Cannot use default instruction reader with variable-length instruction");
		if (wc > 1) {
			int[] ops = new int[wc-1];
			for(int i = 0; i < ops.length; i++) ops[i] = reader.readWord();
			operands(ops);
		}
	}
	
	/** Writes the instruction to a writer, including the opcode and
	 * word count.
	 * 
	 * @param writer Writer to write to
	 * @throws IOException If an IOException occurs
	 */
	public default void write(SPIRVWriter writer) throws IOException {
		writer.writeInsnAndBufferCount(opcode());
		writeBody(writer);
		writer.writeNext();
	}
	
	/** Writes the body of this instruction to a writer.
	 * 
	 * @param writer Writer to write to
	 * @throws IOException If an IOException occurs
	 */
	public void writeBody(SPIRVWriter writer) throws IOException;
	
	/** Gets the result ID given by this instruction. If this instruction
	 * doesn't return anything, this returns -1.
	 * 
	 * @return Result ID, or -1
	 */
	public default int getResult() {
		return -1;
	}
	
	/** Gets the arguments given to the instruction.
	 * 
	 * @return Argument IDs
	 */
	public default int[] getArguments() {
		return new int[0];
	}
	
	
}
