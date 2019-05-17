package com.zekrom_64.ze.base.backend.render.obj;

import java.nio.ByteBuffer;

import org.bridj.Pointer;

import com.zekrom_64.ze.base.mem.ZEMapMode;

/** Graphics memory is a region of memory that can be mapped and unmapped to access it.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEGraphicsMemory {

	/** Maps the graphics memory into accessible memory. If the memory is already mapped, the previously mapped
	 * memory is returned.
	 * 
	 * @return Mapped memory
	 */
	public ByteBuffer mapMemory(ZEMapMode mode);
	
	/** Version of {@link #mapMemory} that returns a raw pointer.
	 * 
	 * @return Mapped memory pointer
	 */
	public Pointer<?> mapMemoryRaw(ZEMapMode mode);
	
	/** Unmaps any mapped memory. If no memory is mapped, the method returns immediately.
	 * 
	 */
	public void unmapMemory();
	
	/** Tests if the memory is mapped into accessible memory.
	 * 
	 * @return If the memory is mapped
	 */
	public boolean isMapped();
	
	/** Tests if the memory is accessible by the host system.
	 * 
	 * @return If the memory is accessible
	 */
	public boolean isHostAccessible();
	
	/** Gets the size of the memory in bytes. 
	 * 
	 * @return Memory size
	 */
	public long size();
	
}
