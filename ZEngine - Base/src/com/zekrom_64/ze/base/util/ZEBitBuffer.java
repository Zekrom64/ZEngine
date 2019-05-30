package com.zekrom_64.ze.base.util;

import java.util.Arrays;

/** A bit buffer allows for the storage and manipulation of an arbitrary number of bits.
 * 
 * @author Zekrom_64
 *
 */
public class ZEBitBuffer {

	private byte[] allBits;
	private long fastBits;
	/** The number of bits available in the bit buffer */
	public final int numBits;
	
	/** Creates a new bit buffer with the given number of bits.
	 * 
	 * @param bits Number of bits
	 */
	public ZEBitBuffer(int bits) {
		numBits = bits;
		int numBytes = Math.round((bits + 4) / 8);
		allBits = new byte[numBytes];
	}
	
	/** Loads the bit buffer with data from a byte array. The data is
	 * loaded in little-endian format, ie. the lower the byte the closer
	 * it is to the least significant bit.
	 * 
	 * @param bytes Bytes to load into the bit buffer
	 */
	public void load(byte[] bytes) {
		int numBytes = Math.min(allBits.length, bytes.length);
		System.arraycopy(bytes, 0, allBits, 0, numBytes);
		fastBits = 0;
		for(int i = 0; i < 8; i++) fastBits |= (bytes[i] | 0l) << (i * 8);
	}
	
	/** Stores the bit buffer's data into a byte array. The data is
	 * stored in little-endian format, ie. the closer a byte is to
	 * the least significant bit the lower it is in the array.
	 * 
	 * @param bytes Array to store data into
	 */
	public void store(byte[] bytes) {
		int numBytes = Math.min(allBits.length, bytes.length);
		System.arraycopy(allBits, 0, bytes, 0, numBytes);
	}
	
	/** Utility method to create a bit mask for the given number of bits.
	 * 
	 * @param bits Number of bits
	 * @return Bit mask
	 */
	public static long makeMask(int bits) {
		long mask = 0;
		for(int i = 0; i < bits; i++) mask = (mask << 1) | 1;
		return mask;
	}
	
	/** Reads a series of bits from the buffer.
	 * 
	 * @param offset Offset into buffer
	 * @param count Number of bits to read
	 * @return Read bits
	 */
	public long readBits(int offset, int count) {
		if (count > 64) count = 64;
		if ((offset + count) < 64) return (fastBits >> offset) & makeMask(count);
		long value = 0;
		if (offset % 8 == 0 && count % 8 == 0) {
			int byteCount = count / 8;
			int byteOffset = offset / 8;
			for(int i = 0; i < byteCount; i++) {
				value |= allBits[byteOffset + i] << i;
			}
		} else {
			for(int i = offset; i < (offset + count); i++) {
				if (getBit(i)) value |= 1;
				value <<= 1;
			}
		}
		return value;
	}
	
	/** Gets the value of an individual bit.
	 * 
	 * @param offset Bit offset
	 * @return Value of bit
	 */
	public boolean getBit(int offset) {
		if (offset < 64) return ((fastBits >> offset) & 1) != 0;
		return (allBits[offset / 8] & (1 << offset % 8)) != 0;
	}

	/** Writes a series of bits into the buffer.
	 * 
	 * @param offset Offset into buffer
	 * @param count Number of bits to write
	 * @param bits Written bits
	 */
	public void writeBits(int offset, int count, long bits) {
		if (count > 64) count = 64;
		long mask = makeMask(count);
		bits &= mask;
		if (offset < 64) fastBits = (fastBits & (mask << offset)) | (bits << offset);
		if (offset % 8 == 0 && count % 8 == 0) {
			int byteCount = count / 8;
			int byteOffset = offset / 8;
			for(int i = 0; i < byteCount; i++) {
				allBits[byteOffset + i] = (byte)(bits >> i);
			}
		}
		for(int i = offset; i < (offset + count); i++) {
			setBit(i, (bits & 1) != 0);
			bits >>= 1;
		}
	}
	
	/** Sets the value of a bit in the buffer.
	 * 
	 * @param offset Offset of bit
	 * @param value Bit value
	 */
	public void setBit(int offset, boolean value) {
		if (offset < 64) {
			long mask = 1 << offset;
			if (value) fastBits |= mask;
			else fastBits &= ~mask;
		}
		int index = offset / 8;
		int mask = 1 << offset % 8;
		if (value) allBits[index] |= mask;
		else allBits[index] &= ~mask;
	}
	
	/** Clears all the bits in the buffer to 0.
	 * 
	 */
	public void clear() {
		Arrays.fill(allBits, (byte)0);
		fastBits = 0;
	}
	
}
