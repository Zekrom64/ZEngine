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
	
}
