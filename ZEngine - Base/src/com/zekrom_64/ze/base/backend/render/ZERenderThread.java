package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;

public interface ZERenderThread {

	/** Submits a command buffer for execution, optionally signalling
	 * a set of semaphores.
	 *
	 * @param cmdbuf Command buffer to submit
	 * @param signal Semaphores to signal on completion
	 */
	public void submit(ZERenderCommandBuffer cmdbuf, ZERenderSemaphore ... signal);

	/** Tests if the commands in a command buffer are compatible with this render thread.
	 *
	 * @param cmdbuf Command buffer to test for compatibility
	 * @return If the command buffer can be submitted to this thread
	 */
	public boolean isCompatible(ZERenderCommandBuffer cmdbuf);

}
