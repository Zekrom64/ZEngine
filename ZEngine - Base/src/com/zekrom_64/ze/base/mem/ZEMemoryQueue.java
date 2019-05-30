package com.zekrom_64.ze.base.mem;

import java.nio.ByteBuffer;

/** A queue is similar to a NIO buffer, but is not of a fixed size. A queue will allocate as much
 * memory as it needs to store its values, but will only deallocate that memory if its capacity
 * is changed using the {@link #capacity(int)} method, or when disposed. All queues can be converted
 * into raw byte arrays or buffers.
 * 
 * @author Zekrom_64
 *
 */
public abstract class ZEMemoryQueue {

	/** The current position in the queue.*/
	protected int position = 0;
	
	/** Converts this queue to an array of bytes in big-endian order.
	 * 
	 * @return The bytes of this queue
	 */
	public abstract byte[] toByteArray();
	
	/** Converts this queue to a byte buffer in the native order used by
	 * the system.
	 * 
	 * @return The bytes of this queue
	 */
	public abstract ByteBuffer toByteBuffer();
	
	/** Sets the capacity of this queue. If the capacity is greater than the current capacity, new memory
	 * is allocated and zeroed. If the capacity is less than than the current capacity, the excess memory
	 * is discarded.
	 * 
	 * @param cap New capacity
	 */
	public abstract void capacity(int cap);
	
	/** Gets the current capacity of this queue.
	 * 
	 * @return Current capacity
	 */
	public abstract int capacity();
	
	/** Sets the position in this queue. If the position is greater than the current capacity, new memory
	 * is allocated and zeroed up to the new position.
	 * 
	 * @param pos New position
	 */
	public void position(int pos) {
		if (pos<0) throw new IndexOutOfBoundsException("Cannot index negative position in queue");
		ensureAllocated(pos+1);
		position = pos;
	}
	
	/** Ensures that the number of elements given is available at the current position. If there is not
	 * enough space at the current position for the number of elements, new space is allocated and zeroed.
	 * 
	 * @param n Number of elements to ensure allocated
	 */
	protected abstract void ensureAllocated(int n);
	
	/** Gets the current position in this queue.
	 * 
	 * @return Current position
	 */
	public int position() {
		return position;
	}
	
}
