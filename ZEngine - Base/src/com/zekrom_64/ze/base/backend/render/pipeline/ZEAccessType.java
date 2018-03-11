package com.zekrom_64.ze.base.backend.render.pipeline;

/** An access type describes a type of memory access. This is used for filtering
 * access types to use for synchronization.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEAccessType {

	/** An indirect draw or dispatch command is reading data from a buffer */
	INDIRECT_COMMAND_READ,
	/** An index buffer is being read as part of a drawing command */
	INDEX_READ,
	/** A vertex buffer is being read as part of a drawing command */
	VERTEX_ATTRIBUTE_READ,
	/** A uniform buffer is being read at some time in the scope */
	UNIFORM_READ,
	/** An input attachment is being read in a render pass during fragment shading */
	INPUT_ATTACHMENT_READ,
	
	/** A shader is reading a storage buffer, uniform or storage texel buffer, sampled image, or storage image */
	SHADER_READ,
	/** A shader is writing to a storage buffer, storage texel buffer, or storage image */
	SHADER_WRITE,
	
	/** A color attachment is being read in some way, such as color blending, logic operations, or subpass load/store operations */
	COLOR_ATTACHMENT_READ,
	/** A color attachment is being written to during a render pass, or subpass load/store operation */
	COLOR_ATTACHMENT_WRITE,
	
	/** The depth/stencil attachment is bing read by a depth/stencil operation or subpass load/store operation */
	DEPTH_STENCIL_ATTACHMENT_READ,
	/** THe depth/stencil attachment is being written to by a depth/stencil opereation or subpass load/store operation */
	DEPTH_STENCIL_ATTACHMENT_WRITE,
	
	/** An image or buffer is being read in a copy operation */
	TRANSFER_READ,
	/** An image or buffer is being written in a copy or clear operation */
	TRANSFER_WRITE,
	
	/** The host (CPU-side) is reading some memory in the scope */
	HOST_READ,
	/** The host (CPU-side) is writing to some memory in the scope */
	HOST_WRITE,
	
	/** A generic memory read is being performed. */
	MEMORY_READ,
	/** A generic memory write is being performed. */
	MEMORY_WRITE
	
}
