package com.zekrom_64.ze.vulkan.spirv;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SPIRVWriter {

	@FunctionalInterface
	public static interface IntWriter {
		
		public void writeInt(int value) throws IOException;
		
	}
	
	private IntWriter writer;
	
	public SPIRVWriter(IntWriter writer) {
		this.writer = writer;
	}
	
	public SPIRVWriter(DataOutput out) {
		writer = out::writeInt;
	}
	
	public SPIRVWriter(OutputStream os) {
		this((DataOutput)new DataOutputStream(os));
	}
	
	private boolean isBuffering = false;
	private int[] wordBuffer = new int[0];
	private int bufferOffset = 0;
	
	private int opcode = 0;
	
	public void writeInsnAndBufferCount(int opcode) throws IOException {
		if (isBuffering) writeNext();
		this.opcode = opcode;
		isBuffering = true;
	}
	
	public void writeWord(int word) throws IOException {
		if (isBuffering) {
			if (bufferOffset >= wordBuffer.length) wordBuffer = Arrays.copyOf(wordBuffer, bufferOffset + 1);
			wordBuffer[bufferOffset++] = word;
		} else writer.writeInt(word);
	}
	
	public void writeString(String str) throws IOException {
		byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);
		
		int wordCount = (utf8.length / 4) + 1;
		if (isBuffering) {
			if ((wordBuffer.length - bufferOffset) < wordCount) wordBuffer = Arrays.copyOf(wordBuffer, bufferOffset + wordCount);
		}
		
		int index = 0;
		while(index < utf8.length) {
			int word = 0;
			if (index < utf8.length) word |= utf8[index++] & 0xFF;
			if (index < utf8.length) word |= (utf8[index++] & 0xFF) << 8;
			if (index < utf8.length) word |= (utf8[index++] & 0xFF) << 16;
			if (index < utf8.length) {
				word |= (utf8[index++] & 0xFF) << 24;
				
				if (isBuffering) wordBuffer[bufferOffset++] = word;
				else writer.writeInt(word);
				
				if (index == utf8.length) {
					if (isBuffering) wordBuffer[bufferOffset++] = 0;
					else writer.writeInt(0);
				}
			} else {
				if (isBuffering) wordBuffer[bufferOffset++] = word;
				else writer.writeInt(word);
			}
		}
	}
	
	public void writeNext() throws IOException {
		if (isBuffering) {
			writer.writeInt((opcode << 16) | (bufferOffset + 1));
			for(int i = 0; i < bufferOffset; i++) writer.writeInt(wordBuffer[i]);
			bufferOffset = 0;
			isBuffering = false;
		}
	}
	
}
