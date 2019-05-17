package com.zekrom_64.ze.base.backend.render.obj;

public interface ZERenderFence {

	/** Gets the signaling status of the fence.
	 * 
	 * @return If the fence is signaled
	 */
	public boolean getStatus();
	
	/** Resets the fence to an unsignaled state.
	 * 
	 */
	public void reset();
	
	/** Waits for this fence to be signaled.
	 * 
	 * @param timeout Timeout in nanoseconds
	 * @return If the waiting timed out
	 */
	public boolean waitFence(long timeout);
	
}
