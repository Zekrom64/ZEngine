package com.zekrom_64.ze.base.backend.render;

/** A render work buffer records a series of render commands and can then be
 * played back to execute the commands. Some command buffers support invoking
 * other command buffers to some extent, though some backends are more limited
 * in when the invocation can be made (Example: Vulkan only supports a single
 * level of invocation and can only be invoked during a render pass.)
 * 
 * @author Zekrom_64
 *
 */
public interface ZERenderCommandBuffer {
	
	/** Begins recording the command buffer. The returned recorder will record 
	 * commands into the buffer and will no longer be usable once the recording
	 * has ended. This command buffer may not be callable by another command
	 * buffer on some render backends due to its implementation of command
	 * buffer execution.
	 * 
	 * @return Work factory to begin recording
	 */
	public ZERenderWorkRecorder beginRecording();
	
	/** Begins recording the command buffer to be called by another command buffer.
	 * This is best used with the calling command buffer recorded up to the
	 * point that this command buffer will be executed.
	 * 
	 * @param parent Calling command buffer
	 * @return Work factory to begin recording
	 */
	public ZERenderWorkRecorder beginRecording(ZERenderCommandBuffer parent);
	
	/** Ends recording of the command buffer.
	 * 
	 */
	public void endRecording();
	
}