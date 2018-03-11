package com.zekrom_64.ze.base.backend.render;

import java.nio.ByteBuffer;

import org.bridj.Pointer;

import com.zekrom_64.ze.base.mem.ZEMapMode;

/** A buffer is a region of memory that can be mapped and unmapped to access it. Buffers may reorder or transfer mapped data
 * to improve efficiency or support operations not inherently supported by the render backend.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEBuffer {

	/** Maps the buffer into accessible memory. If the buffer is already mapped, the previously mapped
	 * memory is returned.
	 * 
	 * @return Mapped memory
	 */
	public ByteBuffer mapMemory(ZEMapMode mode);
	
	/** Version of {@link mapMemory} that returns a raw pointer.
	 * 
	 * @return Mapped memory pointer
	 */
	public Pointer<?> mapMemoryRaw(ZEMapMode mode);
	
	/** Unmaps any mapped memory. If no memory is mapped, the method returns immediately.
	 * 
	 */
	public void unmapMemory();
	
	/** Tests if the buffer is mapped into accessible memory.
	 * 
	 * @return If the memory is mapped
	 */
	public boolean isMapped();
	
	/** Tests if the buffer is accessible by the host system.
	 * 
	 * @return If the buffer is accessible
	 */
	public boolean isHostAccessible();
	
	/** Gets the size of the buffer in bytes. 
	 * 
	 * @return Buffer size
	 */
	public long size();
	
}
