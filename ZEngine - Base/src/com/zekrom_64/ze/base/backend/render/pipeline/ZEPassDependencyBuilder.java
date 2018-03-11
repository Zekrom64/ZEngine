package com.zekrom_64.ze.base.backend.render.pipeline;

/** A bind format builder acts as a factory for a bind format.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPassDependencyBuilder {
	
	/** Gets the index of the current pass.
	 * 
	 * @return Current pass index
	 */
	public int getCurrentPassIndex();
	
	/** Moves to the next subpass with its own binding set.
	 * 
	 */
	public void nextPass();
	
	/** Pass index for an external subpass to the current one. */
	public static final int PASS_EXTERNAL = 0xFFFFFFFF;
	
	/** <p>Adds a dependency between resources in two subpasses. A dependency forces
	 * the render pipeline to wait for the completion of previous commands operating
	 * on data of the given access types. Dependencies are used for render backends
	 * to determine the optimal order of operations in a subpass while maintaining
	 * any required order of operations.</p>
	 * 
	 * <p><i>Note: dependencies cannot target operations ocurring earlier in the pipeline,
	 * even if the operations occur later in execution.</i></p>
	 * 
	 * <p>If {@link #PASS_EXTERNAL} is used in <i>firstPass</i> or <i>lastPass</i> it applies the
	 * dependency to render work before or after this pass is submitted to the queue.</p>
	 * 
	 * @param firstPass Index of the first pass
	 * @param lastPass Index of the last pass
	 * @param firstScope Initial scope of pipeline stages
	 * @param lastScope Final scope of pipeline stages
	 * @param firstAccesses Initial set of access types
	 * @param lastAccesses Final set of access types
	 */
	public void addDependency(int firstPass, int lastPass, ZEPipelineStage[] firstScope,
			ZEPipelineStage[] lastScope, ZEAccessType[] firstAccesses, ZEAccessType[] lastAccesses);
	
	/** Allocates a new attachment ID to use in the set of subpasses. This value is usable by any
	 * subpass in the builder, but may be allocated at any time.
	 * 
	 * @return New attachment ID
	 */
	public int newAttachment();
	
	/** Adds an input attachment to the current subpass.
	 * 
	 * @param attachID Attachment ID to use
	 * @return Index of input attachment
	 */
	public int addInputAttachment(int attachID);
	
	/** Adds a color attachment to the current subpass.
	 * 
	 * @param attachID Attachment ID to use
	 * @return Index of color attachment
	 */
	public int addColorAttachment(int attachID);
	
	/** Sets the depth-stencil attachment for the current subpass.
	 * 
	 * @param attachID Attachment ID to use
	 * @param readOnly If the subpass will only read from the attachment
	 */
	public void setDepthStencilAttachment(int attachID, boolean readOnly);
	
	/** Adds an attachment to preserve when changing to the next subpass. Some
	 * backends may discard the contents of attachments if preservation is not
	 * explicitly specified.
	 * 
	 * @param attachID Attachment to preserve between subpasses
	 */
	public void addPreserveAttachment(int attachID);
	
	/** Builds the current format.
	 * 
	 * @return Built format
	 */
	public ZEPassDependency build();
	
}
