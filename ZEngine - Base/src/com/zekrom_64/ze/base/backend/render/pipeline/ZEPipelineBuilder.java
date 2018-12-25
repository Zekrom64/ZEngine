package com.zekrom_64.ze.base.backend.render.pipeline;

import java.util.Set;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.shape.RectanglePosSizeDouble;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;

public interface ZEPipelineBuilder {
	
	/** A compare op specifies how values are compared for a rendering test.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZECompareOp {
		/** The depth test always fails */
		NEVER,
		/** The depth test always succeeds */
		ALWAYS,
		/** The test succeeds if the new depth value is less than the existing depth value */
		LESS,
		/** The test succeeds if the new depth value is less than or equal to the existing depth value */
		LESS_OR_EQUAL,
		/** The test succeeds if the new depth value is greater than the existing depth value */
		GREATER,
		/** The test succeeds if the new depth value is greater than or equal to the existing depth value */
		GREATER_OR_EQUAL,
		/** The test succeeds if the new depth value is equal to the existing depth value */
		EQUAL,
		/** The test succeeds if the new depth value is not equal to the existing depth value */
		NOT_EQUAL
	}
	
	// -----------
	// | SHADERS |
	// -----------
	
	/** Sets the shader program to use with the pipeline.
	 * 
	 * @param program Shader program
	 */
	public void setShaderProgram(ZEShaderProgram program);
	
	// ------------------
	// | INPUT GEOMETRY |
	// ------------------
	
	/** Sets the type of input geometry. By default, this is {@link ZEGeometryType#TRIANGLES TRIANGLES}.
	 * 
	 * @param type
	 */
	public void setInputGeometry(ZEGeometryType type);
	
	/** Sets if a <i>primitive restart</i> is enabled. When this is enabled, an index value with all
	 * bits set (short = 0xFFFF, int = 0xFFFFFFFF, etc.) will restart any strips of vertices. By
	 * default, this is <b>false</b>.
	 * 
	 * @param enable
	 */
	public void setPrimitiveRestartEnable(boolean enable);
	
	// ------------------
	// | VIEWPORT STATE |
	// ------------------
	
	/** A viewport defines a rectangle where the rendered frame is stored
	 * in the framebuffer, and minimum and maximum depth values to store
	 * in the framebuffer.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class ZEViewport {
		
		/** Viewport minimum depth value */
		public double minDepth = 0;
		/** Viewport maximum depth value */
		public double maxDepth = 0;
		/** Viewport framebuffer area */
		public final RectanglePosSizeDouble area = new RectanglePosSizeDouble();
		
	}
	
	/** Sets the initial viewport state for the pipeline.
	 * 
	 * @param viewports Initial viewports
	 */
	public void setViewports(ZEViewport ... viewports);
	
	/** Sets the initial scissor state for the pipeline.
	 * 
	 * @param scissors Initial scissors
	 */
	public void setScissors(Rectangle ... scissors);
	
	// ----------------
	// | POLYGON MODE |
	// ----------------
	
	/** A polygon mode specifies how polygons are drawn.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEPolygonMode {
		
		/** The polygon is filled completely */
		FILL,
		/** The edges of the polygon are drawn */
		LINE,
		/** Each vertex of the polygon is drawn */
		POINT
		
	}
	
	/** Sets the polygon filling mode. By default, this is {@link ZEPolygonMode#FILL FILL}.
	 * 
	 * @param mode Polygon fill mode
	 */
	public void setPolygonFillMode(ZEPolygonMode mode);
	
	// -----------------------
	// | RASTERIZER SETTINGS |
	// -----------------------
	
	/** Sets if depth clamping is enabled. This will clamp fragments beyond the near
	 * and far planes to them instead of discarding them.
	 * 
	 * @param enabled If depth clamping is enabled
	 */
	public void setDepthClampEnable(boolean enabled);
	
	/** Sets the initial line width.
	 * 
	 * @param lineWidth Initial line width
	 */
	public void setLineWidth(float lineWidth);
	
	// -----------
	// | CULLING |
	// -----------
	
	/** Sets the culling mode. By default, this is {@link ZEFrontBack#FRONT_BACK FRONT_BACK}.
	 * 
	 * @param mode Culling mode
	 */
	public void setCullMode(ZEFrontBack mode);
	
	/** Selects which direction of rotation is defined as the front face. By default, this is
	 * counter-clockwise (a value of <b>false</b>).
	 * 
	 * @param cw Culling front face
	 */
	public void setFrontFace(boolean cw);
	
	// ------------
	// | BLENDING |
	// ------------
	
	/** A blend factor is a numerical value used in color blending to compare between source and destination.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEBlendFactor {
		/** A constant of zero */
		ZERO,
		/** A constant of one */
		ONE,
		/** The source color */
		SRC_COLOR,
		/** One minus the source color */
		ONE_MINUS_SRC_COLOR,
		/** The destination color */
		DST_COLOR,
		/** One minus the desintation color */
		ONE_MINUS_DST_COLOR,
		/** The source alpha */
		SRC_ALPHA,
		/** One minus the source alpha */
		ONE_MINUS_SRC_ALPHA,
		/** The destination alpha */
		DST_ALPHA,
		/** One minus the destination alpha */
		ONE_MINUS_DST_ALPHA,
		/** A constant color */
		CONSTANT_COLOR,
		/** One minus a constant color */
		ONE_MINUS_CONSTANT_COLOR,
		/** A constant alpha value */
		CONSTANT_ALPHA,
		/** One minus a constant alpha value */
		ONE_MINUS_CONSTANT_ALPHA
	}
	
	/** A blend op specifies how colors are blended if the blend function is passed.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEBlendOp {
		/** The source and destination colors are added together */
		ADD,
		/** The destination is subtracted from the source */
		SUBTRACT,
		/** The source is subtracted from the destination */
		REVERSE_SUBTRACT,
		/** The minimum value for each component is used */
		MIN,
		/** The maximum value for each component is used */
		MAX
	}
	
	/** Sets if color blending is enabled. By default, this is <b>false</b>
	 * 
	 * @param enable If color blending is enabled
	 */
	public void setBlendEnable(boolean enable);
	
	/** Sets the color blending function. By default, the values are as follows:
	 * <ul>
	 * <li>srcColor = {@link ZEBlendFactor#SRC_ALPHA SRC_ALPHA}</li>
	 * <li>dstColor = {@link ZEBlendFactor#ONE_MINUS_SRC_ALPHA ONE_MINUS_SRC_ALPHA}</li>
	 * <li>opColor = {@link ZEBlendOp#ADD ADD}</li>
	 * <li>srcAlpha = {@link ZEBlendFactor#ONE ONE}</li>
	 * <li>dstAlpha = {@link ZEBlendFactor#ZERO ZERO}</li>
	 * <li>opAlpha = {@link ZEBlendOp#ADD ADD}</li>
	 * </ul>
	 * 
	 * @param srcColor Source color factor
	 * @param dstColor Destination color factor
	 * @param opColor Color blend operation
	 * @param srcAlpha Source alpha factor
	 * @param dstAlpha Destination alpha factor
	 * @param opAlpha Alpha blend operation
	 */
	public void setBlendFunc(ZEBlendFactor srcColor, ZEBlendFactor dstColor, ZEBlendOp opColor, ZEBlendFactor srcAlpha, ZEBlendFactor dstAlpha, ZEBlendOp opAlpha);
	
	/** Sets a constant color optionally used in color blending operations. By default, this is {1,1,1,1},
	 * the same as pure white with 100% opacity.
	 * 
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 * @param a Alpha component
	 */
	public void setBlendConstColor(float r, float g, float b, float a);
	
	// --------------
	// | DEPTH TEST |
	// --------------
	
	/** Sets if the depth should be tested before writing to the
	 * framebuffer. By default, this is set to <b>false</b>
	 * 
	 * @param enable If a depth test is enabled.
	 */
	public void setDepthTestEnable(boolean enable);
	
	/** Sets if the depth value is written to the framebuffer. By default, this is set
	 * to <b>false</b>
	 * 
	 * @param enable If the depth buffer is written
	 */
	public void setDepthWriteEnable(boolean enable);
	
	/** Sets the maxiumum and minimum depth bounds.
	 * 
	 * @param min Minimum depth
	 * @param max Maximum depth
	 */
	public void setDepthBounds(float min, float max);
	
	/** Sets the depth compare operation. By default, this is {@link ZECompareOp#LESS_OR_EQUAL LESS_OR_EQUAL}.
	 * 
	 * @param op Depth compare op
	 */
	public void setDepthCompare(ZECompareOp op);
	
	// -----------
	// | STENCIL |
	// -----------
	
	/** A stencil modify operation is an operation performed on a value in the stencil buffer if the stencil
	 * test succeeds.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEStencilModifyOp {
		/** The value in the buffer is not modified */
		KEEP,
		/** Clear the value in the buffer to zero. */
		ZERO,
		/** Replaces the value in the stencil buffer with the reference value ANDed with the write mask */
		REPLACE,
		/** Increments the value in the buffer if the new value does not create an integer overflow */
		INCREMENT_AND_CLAMP,
		/** Decrements the value in the buffer if the new value does not create an integer underflow */
		DECREMENT_AND_CLAMP,
		/** Bitwise inverts the value in the buffer */
		INVERT,
		/** Increments the value in the buffer, allowing it to integer overflow */
		INCREMENT_AND_WRAP,
		/** Decrements the value in the buffer, allowing it to integer underflow */
		DECREMENT_AND_WRAP
	}
	
	/** Sets the stencil compare operation for the given face(s).
	 * 
	 * @param face Polygon face(s)
	 * @param op Stencil compare operation
	 */
	public void setStencilCompareOp(ZEFrontBack face, ZECompareOp op);
	
	/** Enumeration of stencil test conditions that can have their modification operations defined.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEStencilTestCondition {
		/** The stencil test fails, regardless of the depth test */
		STENCIL_FAILS,
		/** The stencil test succeeds, but the depth test fails */
		DEPTH_FAILS,
		/** Both the stencil and depth test succeed (if the depth test is enabled) */
		SUCCEEDS
	}
	
	public void setStencilModifyOp(ZEFrontBack face, ZEStencilTestCondition cond, ZEStencilModifyOp op);
	
	/** Enumeration of stencil test values that can be assigned.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEStencilValue {
		/** Binary mask ANDed with the value to be compared */
		COMPARE_MASK,
		/** Binary mask ANDed with a value written to the buffer */
		WRITE_MASK,
		/** Constant value used in a stencil comparison operation */
		COMPARE_REFERENCE
	}
	
	/** Sets a value for use in the stencil test.
	 * 
	 * @param face Polygon face(s)
	 * @param stencilVal The stencil value to set
	 * @param val Value to set to
	 */
	public void setStencilValue(ZEFrontBack face, ZEStencilValue stencilVal, int val);
	
	// -----------------
	// | DYNAMIC STATE |
	// -----------------
	
	/** Dynamic state for modifying the viewport */
	public static final String DYNAMIC_STATE_VIEWPORT = "ze.dynstate.viewport";
	/** Dynamic state for modifying the rendering scissor */
	public static final String DYNAMIC_STATE_SCISSOR = "ze.dynstate.scissor";
	/** Dynamic state for modifying line width */
	public static final String DYNAMIC_STATE_LINE_WIDTH = "ze.dynstate.lineWidth";
	/** Dynamic state for modifying depth biasing */
	public static final String DYNAMIC_STATE_DEPTH_BIAS = "ze.dynstate.depthBias";
	/** Dynamic state for modifying the blend constant */
	public static final String DYNAMIC_STATE_BLEND_CONSTANTS = "ze.dynstate.blendConstants";
	/** Dynamic state for modifying the depth bounds */
	public static final String DYNAMIC_STATE_DEPTH_BOUNDS = "ze.dynstate.depthBounds";
	/** Dynamic state for modifying the stencil compare mask */
	public static final String DYNAMIC_STATE_STENCIL_COMPARE_MASK = "ze.dynstate.stencil.compareMask";
	/** Dynamic state for modifying the stencil write mask */
	public static final String DYNAMIC_STATE_STENCIL_WRITE_MASK = "ze.dynstate.stencil.writeMask";
	/** Dynamic state for modifying the stencil reference mask */
	public static final String DYNAMIC_STATE_STENCIL_REFERENCE = "ze.dynstate.stencil.reference";
	
	/** Gets a set containing the pipeline state variables that can be
	 * modified after creation, called the "dynamic state". The set can
	 * be modified by the caller to add (or remove?) dynamic states.
	 * 
	 * @return Set containing the dynamic state
	 */
	public Set<String> dynamicState();
	
	// ------------
	// | BUILDING |
	// ------------
	
	/** Builds the pipeline.
	 * 
	 * @return Built pipeline
	 */
	public ZEPipeline build();
	
}
