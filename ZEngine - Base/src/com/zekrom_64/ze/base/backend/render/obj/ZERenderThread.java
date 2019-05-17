package com.zekrom_64.ze.base.backend.render.obj;

import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;

/** A render thread is an independent thread of execution for render commands.
 * Some backends may not have independent threads (OpenGL), and some backends
 * might only support certain operations for a given render thread (Vulkan).
 * 
 * @author Zekrom_64
 *
 */
public interface ZERenderThread {
	
	/** Enumeration of usages for render threads.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZERenderThreadUsage {
		/** The thread supports all graphics commands */
		GRAPHICS,
		/** The thread only supports memory transfer operations */
		TRANSFER,
		/** The thread supports GPGPU computational operations */
		COMPUTE,
		/** The thread supports presentation operations, used internally for render outputs */
		PRESENT
	}

	/** Submits commands to this render thread for execution. Commands
	 * will be queued until they are executed.
	 * 
	 * @param cmdbuf Command buffer to execute.
	 * @param signalFence Fence to signal on completion
	 * @param waitSemaphores Semaphores to wait before execution
	 * @param signalSemaphores Semaphores to signal on completion
	 */
	public void submitCommands(ZERenderCommandBuffer cmdbuf, ZERenderFence signalFence, ZERenderSemaphore[] waitSemaphores, ZERenderSemaphore ... signalSemaphores);
	
	/** Waits until the render thread has no commands queued.
	 * 
	 */
	public void waitIdle();
	
	/** Gets the valid usages for this render thread.
	 * 
	 * @return Valid render thread usages.
	 */
	public ZERenderThreadUsage[] getValidUsages();
	
}
