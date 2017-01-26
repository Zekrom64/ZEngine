package com.zekrom_64.ze.se.files;

public class SEVPKFile {

	public static final int SIGNATURE = 0x55aa1234;
	
	// Standard header
	public int signature;
	public int version;
	
	// Header v1
	public int treeSize;
	// Header v2
	public int fileDataSectionSize;
	public int archiveMD5SectionSize;
	public int otherMD5SectionSize;
	public int signatureSectionSize;
	
	public static class SEVPKDirectory {
		
		public int crc;
		public short preloadBytes;
		public short archiveIndex;
		public int entryOffset;
		public int entryLength;
		public final short terminator = (short)0xffff;
	}
	
}
