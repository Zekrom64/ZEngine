package com.zekrom_64.ze.base.backend.render.obj;

/** A buffer is a raw form of graphics memory.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEBuffer extends ZEGraphicsMemory {

	/** Enumeration of usages for a buffer.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEBufferUsage {
		/** Buffer can be read from in memory transfers. */
		TRANSFER_SRC,
		/** Buffer can be written to in memory transfers. */
		TRANSFER_DST,
		/** Buffer can be used as a uniform texture element buffer. */
		UNIFORM_TEXEL_BUFFER,
		/** Buffer can be used as a storage texture element buffer. */
		STORAGE_TEXEL_BUFFER,
		/** Buffer can be used as a uniform buffer. */
		UNIFORM_BUFFER,
		/** Buffer can be used as a storage buffer. */
		STORAGE_BUFFER,
		/** Buffer can be used for storing vertex indices. */
		INDEX_BUFFER,
		/** Buffer can be used for storing vertices. */
		VERTEX_BUFFER,
		/** Buffer can be used for storing indirection parameters. */
		INDIRECT_BUFFER
	}
	
	/** Gets the valid usages for this buffer.
	 * 
	 * @return Valid buffer usages
	 */
	public ZEBufferUsage[] getValidUsages();
	
}
