package com.zekrom_64.ze.gl;

/** Interface for an object with an OpenGL context.
 * 
 * @author Zekrom_64
 *
 */
public interface GLContext {

	/** Makes this OpenGL context current for the current thread.
	 * 
	 */
	public void bind();
	
	/** Swaps the buffers for this OpenGL context and updates the view.
	 * 
	 */
	public void swapBuffers();
	
	/** Tests if the OpenGL context is bound to the current thread.
	 * 
	 */
	public boolean isBound();
	
	/** Tests {@link #isBound()} and if false calls {@link #bind()}.
	 * 
	 */
	public default void ensureBound() {
		if (!isBound()) bind();
	}
	
	
}
