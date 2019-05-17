package com.zekrom_64.ze.vulkan.spirv;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SPIRVReader {
	
	@FunctionalInterface
	public static interface IntReader {
		
		/** Reads an integer from the stream, returns -1 if
		 * the end of stream has been reached.
		 * 
		 * @return Read integer, or -1
		 * @throws IOException If an IOException occurs
		 */
		public long readInt() throws IOException;
		
	}
	
	private IntReader reader;
	private int counter;
	
	public SPIRVReader(IntReader ir) {
		reader = ir;
	}
	
	public SPIRVReader(DataInput in) {
		this(in::readInt);
	}
	
	private static IntReader fromInputStream(InputStream is, boolean bigEndian) {
		return bigEndian ?
				() -> {
					int val = 0;
					int i;
					if ((i = is.read()) == -1) return -1;
					val |= i << 24;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i << 16;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i << 8;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i;
					
					return val;
				} :
				() -> {
					int val = 0;
					int i;
					if ((i = is.read()) == -1) return -1;
					val |= i;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i << 8;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i << 16;
					if ((i = is.read()) == -1) throw new IOException("Unexpected end of stream");
					val |= i << 24;
					
					return val;
				};
	}
	
	public SPIRVReader(InputStream is, boolean bigEndian) {
		this(fromInputStream(is, bigEndian));
	}
	
	private static final byte[] magicLittleEndian = new byte[] { (byte)0x03, (byte)0x02, (byte)0x23, (byte)0x07 };
	private static final byte[] magicBigEndian = new byte[] { (byte)0x07, (byte)0x23, (byte)0x02, (byte)0x03 };
	
	public SPIRVReader(InputStream is) throws IOException {
		PushbackInputStream pbis = new PushbackInputStream(is, 4);
		byte[] magic = new byte[4];
		
		int read = 0, off = 0;
		while (off < 4) {
			read = pbis.read(magic, off, 4 - off);
			if (read == -1) throw new IOException("Unexpected end of stream");
			off += read;
		}
		
		pbis.unread(magic);
		
		if (Arrays.equals(magic, magicLittleEndian)) reader = fromInputStream(pbis, false);
		else if (Arrays.equals(magic, magicBigEndian)) reader = fromInputStream(pbis, true);
		else throw new IOException("Cannot determine reader endianness from magic");
	}
	
	public int getWordCounter() {
		return counter;
	}
	
	public long read() throws IOException {
		long l = reader.readInt();
		if (l != -1) counter++;
		return l;
	}
	
	public int readWord() throws IOException {
		long l = reader.readInt();
		if (l == -1) throw new IOException("Reached end of stream");
		counter++;
		return (int)l;
	}
	
	public String readString() throws IOException {
		int word = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while(true) {
			word = readWord();
			for(int i = 0; i < 32; i += 8) {
				int ch = (word >> i) & 0xFF;
				if (ch == 0) return new String(baos.toByteArray(), StandardCharsets.UTF_8);
				else baos.write(ch);
			}
		}
	}
	
}
