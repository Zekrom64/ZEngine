package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;

/** A render output controls the presentation of the output of a render backend. The
 * output defines an array of framebuffers that can be drawn to, and the {@link #preRender}
 * and {@link #postRender} methods are called to acquire a framebuffer to draw to and
 * present the acquired framebuffer, respectively.
 * 
 * @author Zekrom_64
 *
 * @param <B> Backend type
 */
public interface ZERenderOutput<B extends ZERenderBackend<?>> {
	
	/** Prepares the render output for rendering. This may be asynchronously
	 * completed, so a render semaphore may be signaled to synchronize with
	 * render commands. Also returns the index of the framebuffer that will
	 * be presented next.
	 * 
	 * @param signalSemaphore Render semaphore to signal when complete.
	 * @param signalFence Render fence to signal when complete
	 * @return Index of framebuffer to use
	 */
	public int preRender(ZERenderSemaphore signalSemaphore, ZERenderFence signalFence);
	
	/** Finishes rendering and presents the result to the render output. To
	 * synchronize with render commands a list of render semaphores to wait
	 * on can be provided.
	 * 
	 * @param wait Semaphores to wait on
	 */
	public void postRender(ZERenderSemaphore ... wait);
	
	/** Returns an array of framebuffers for presentation. The framebuffer
	 * to render to will be indicated by {@link preRender} and its output
	 * will be presented during {@link postRender}. The specific details of
	 * the framebuffers used for presentation are determined during instatiation
	 * of the implementation-specific object, (eg. OpenGL has a default framebuffer,
	 * Vulkan has a swapchain of images which are made into framebuffers).
	 * 
	 * @return
	 */
	public ZEFramebuffer[] getFramebuffers();
	
}
