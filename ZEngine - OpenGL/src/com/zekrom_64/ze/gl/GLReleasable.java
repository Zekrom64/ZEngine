package com.zekrom_64.ze.gl;

/** A releasable OpenGL object can be freed only when the current
 * thread has an OpenGL context bound to it.
 * 
 * @author Zekrom_64
 *
 */
public interface GLReleasable {

	/** Release the object with an OpenGL context bound to the current thread.
	 * 
	 */
	public void releaseWithGL();
	
}
