package com.zekrom_64.ze.base.backend.render;

import java.nio.ByteBuffer;

import org.bridj.Pointer;

/** A buffer is a region of memory that can be mapped and unmapped to access it.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEBuffer {

	/** Maps the buffer into accessible memory. If the buffer is already mapped, the previously mapped
	 * memory is returned. If 
	 * 
	 * @return Mapped memory
	 */
	public ByteBuffer mapMemory();
	
	/** Version of {@link mapMemory} that returns a raw pointer.
	 * 
	 * @return Mapped memory pointer
	 */
	public Pointer<?> mapMemoryRaw();
	
	/** Unmaps any mapped memory. If no memory is mapped, the method returns immediately.
	 * 
	 */
	public void unmapMemory();
	
	/** Tests if the buffer is mapped into accessible memory.
	 * 
	 * @return If the memory is mapped
	 */
	public boolean isMapped();
	
}
