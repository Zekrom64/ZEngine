package com.zekrom_64.ze.logic;

public class Port8 implements IReadable8, IWritable8 {

	private byte inputBuf;
	private byte outputBuf;
	
	public byte inputPull = 0;
	public byte outputPull = 0;
	
	@Override
	public void writeByte(byte b) {
		inputBuf = b;
	}

	@Override
	public void unwriteByte() {
		inputBuf = inputPull;
	}

	@Override
	public byte readByte() {
		return outputBuf;
	}
	
	public final Port8Internal internal = new Port8Internal();
	
	private class Port8Internal implements IReadable8, IWritable8 {

		@Override
		public void writeByte(byte b) {
			outputBuf = b;
		}

		@Override
		public void unwriteByte() {
			outputBuf = outputPull;
		}

		@Override
		public byte readByte() {
			return inputBuf;
		}
		
	}
	
}
