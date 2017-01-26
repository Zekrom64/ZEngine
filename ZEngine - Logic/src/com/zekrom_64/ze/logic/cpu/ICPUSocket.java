package com.zekrom_64.ze.logic.cpu;

public interface ICPUSocket {

	public byte readByte(int address);
	
	public short readShort(int address);
	
	public int readInt(int address);
	
	public void writeByte(int address, byte b);
	
	public void writeShort(int address, short s);
	
	public void writeInt(int address, int i);
	
	public void output(int address, byte b);
	
	public byte input(int address);
	
}
