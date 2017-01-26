package com.zekrom_64.ze.gl.compat;

import com.zekrom_64.ze.gl.vertex.GLVertexBuffer;
import com.zekrom_64.ze.gl.vertex.GLVertexType;

@Deprecated
/** A cross-GL3.0 compatible renderer. If GL3.0 or above is used, alot of backend code is
 * used to simplify code to a GL2.1 level, while still allowing GL3.0 features. If GL2.1
 * or below is used, functionality is identical, but 3.0 features are unavailable.
 * 
 * @author Zekrom_64
 *
 */
public interface IGLRenderer {
	
	/** Returns true if this renderer is compatible with OpenGL 3.0 or above.
	 * 
	 * @return If OpenGL 3.0 compatible
	 */
	public boolean isGL30Compatible();
	
	/** Creates a compatible vertex buffer object for use with this renderer.
	 * 
	 * @param count The number of vertices.
	 * @return
	 */
	public IGLCompatVBO createCompatibleVBO(int count);
	
	/** Creates a compatible vertex buffer object for use with this renderer from
	 * a different vertex buffer.
	 * 
	 * @param buffer Buffer to create from
	 * @return A compatible vertex buffer
	 */
	public IGLCompatVBO createCompatibleVBO(GLVertexBuffer buffer);
	
	/** Gets the vertex format of this renderer.
	 * 
	 * @return Vertex format
	 */
	public GLVertexType getVertexType();
	
}
