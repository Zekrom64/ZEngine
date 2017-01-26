package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.util.PrimitiveType;

/** An index buffer is a specialized form of a buffer that holds the indices of vertices to be rendered.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEIndexBuffer extends ZEBuffer {
	
	/** Gets the type of indices stored in the vertex buffer.
	 * 
	 * @return Index type
	 */
	public PrimitiveType getIndexType();
	
}
