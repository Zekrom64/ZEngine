package com.zekrom_64.ze.base.mem;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

/** Represents an expandable queue of bytes.
 * 
 * @author Zekrom_64
 *
 */
public class ZEByteQueue extends ZEMemoryQueue {
	
	private byte[] array = new byte[0];
	
	/** Creates a queue of length 0.
	 * 
	 */
	public ZEByteQueue() {}
	
	/** Creates a queue of a given length.
	 * 
	 * @param length Starting length
	 */
	public ZEByteQueue(int length) {
		array = new byte[length];
	}
	
	@Override
	public byte[] toByteArray() {
		return Arrays.copyOf(array, array.length);
	}

	@Override
	public ByteBuffer toByteBuffer() {
		ByteBuffer buf = BufferUtils.createByteBuffer(array.length);
		buf.put(array).rewind();
		return buf;
	}

	@Override
	public void capacity(int cap) {
		if (cap<0) throw new IllegalArgumentException("Cannot resize queue to a negative size");
		if (cap==array.length) return;
		array = Arrays.copyOf(array, cap);
	}

	@Override
	public int capacity() {
		return array.length;
	}

	@Override
	protected void ensureAllocated(int n) {
		int needed = position+n;
		if (needed>array.length) {
			int toadd = needed - array.length;
			toadd = Math.max(toadd, 2048);
			array = Arrays.copyOf(array, array.length+toadd);
		}
	}
	
	/** Puts a byte at the current position, and extends the queue if necessary.
	 * 
	 * @param b Byte to put
	 */
	public void put(byte b) {
		ensureAllocated(1);
		array[position++] = b;
	}
	
	/** Puts an array of bytes at the current position, and extends the queue if necessary.
	 * 
	 * @param array Array to put
	 * @param offset Offset into array
	 * @param length Number of bytes to put
	 */
	public void put(byte[] array, int offset, int length) {
		if (length<0) throw new IllegalArgumentException("Cannot put array of negative length into queue");
		ensureAllocated(length);
		System.arraycopy(array, offset, this.array, position, length);
		position += length;
	}
	
	/** Alternate version of {@link #put(byte[], int, int)} that puts the array at offset 0 with the
	 * length of the entire array.
	 * 
	 * @param array Array to put
	 */
	public void put(byte[] array) {
		ensureAllocated(array.length);
		System.arraycopy(array, 0, this.array, position, array.length);
		position += array.length;
	}
	
	/** Puts the remaining bytes in the ByteBuffer at the current position, and extends the queue if necessary.
	 * 
	 * @param buf ByteBuffer to put
	 */
	public void put(ByteBuffer buf) {
		int n = buf.remaining();
		ensureAllocated(n);
		buf.get(array, position, n);
		position += n;
	}
	
	/** Alternate version of {@link #put(ByteBuffer)} that allows a number of bytes to
	 * put to be specified.
	 * 
	 * @param buf ByteBuffer to put
	 * @param length Number of bytes to put
	 */
	public void put(ByteBuffer buf, int length) {
		if (length<0) throw new IllegalArgumentException("Cannot put buffer of negative length into queue");
		ensureAllocated(length);
		buf.get(array, position, length);
		position += length;
	}
	
	/** Gets a byte from the queue at the current position, and extends the queue if necessary.
	 * 
	 * @return Byte from the queue
	 */
	public byte get() {
		ensureAllocated(1);
		return array[position++];
	}
	
	/** Alternate version of {@link #get(byte[], int, int)} reads into the entire array at offset 0.
	 * 
	 * @param array Array to put bytes into
	 */
	public void get(byte[] array) {
		ensureAllocated(array.length);
		System.arraycopy(this.array, position, array, 0, array.length);
		position += array.length;
	}
	
	/** Gets bytes from the queue and puts them into the given array, and extends the queue if necessary.
	 * 
	 * @param array Array to put bytes into
	 * @param offset Offset into array to put at
	 * @param length Number of bytes to put
	 */
	public void get(byte[] array, int offset, int length) {
		if (length<0) throw new IllegalArgumentException("Cannot put queue of negative length into array");
		ensureAllocated(length);
		System.arraycopy(this.array, position, array, offset, length);
		position += length;
	}
	
	/** Gets bytes from the queue and puts them into the remaining space in a buffer, and extends the queue
	 * if necessary.
	 * 
	 * @param buf ByteBuffer to put bytes into
	 */
	public void get(ByteBuffer buf) {
		int n = buf.remaining();
		ensureAllocated(n);
		buf.put(array, position, n);
		position += n;
	}
	
	/** Alternate version of {@link #get(ByteBuffer)} that allows a number of bytes to put into the buffer to 
	 * be specified.
	 * 
	 * @param buf ByteBuffer to put bytes into
	 * @param length Number of bytes to put
	 */
	public void get(ByteBuffer buf, int length) {
		if (length<0) throw new IllegalArgumentException("Cannot put queue of negative length into buffer");
		ensureAllocated(length);
		buf.put(array, position, length);
		position += length;
	}

}
