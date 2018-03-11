package com.zekrom_64.ze.gl.impl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GLCapabilities;

import com.zekrom_64.mathlib.tuple.impl.Vector4Float;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendFactor;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEDepthCompare;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEPolygonMode;
import com.zekrom_64.ze.base.util.PrimitiveType;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLShaderCompiler.GLShaderProgram;

/** The pipeline state object maintains a "snapshot" of the OpenGL state that can be
 * used as a pipeline object.
 * 
 * @author Zekrom_64
 *
 */
public class GLPipelineState implements Cloneable {

	private GLRenderBackend backend;
	
	public final Vector4Float blendConstColor = new Vector4Float(0,0,0,0);
	public boolean blendEnabled = false;
	private ZEBlendFactor blendSrcColor = ZEBlendFactor.ONE;
	public void setBlendSrcColor(ZEBlendFactor factor) { if (factor != null) blendSrcColor = factor; }
	public ZEBlendFactor getBlendSrcColor() { return blendSrcColor; }
	private ZEBlendFactor blendDstColor = ZEBlendFactor.ZERO;
	public void setBlendDstColor(ZEBlendFactor factor) { if (factor != null) blendDstColor = factor; }
	public ZEBlendFactor getBlendDstColor() { return blendDstColor; }
	private ZEBlendFactor blendSrcAlpha = ZEBlendFactor.ONE;
	public void setBlendSrcAlpha(ZEBlendFactor factor) { if (factor != null) blendSrcAlpha = factor; }
	public ZEBlendFactor getBlendSrcAlpha() { return blendSrcAlpha; }
	private ZEBlendFactor blendDstAlpha = ZEBlendFactor.ZERO;
	public void setBlendDstAlpha(ZEBlendFactor factor) { if (factor != null) blendDstAlpha = factor; }
	public ZEBlendFactor getBlendDstAlpha() { return blendDstAlpha; }
	private ZEBlendOp blendOpColor = ZEBlendOp.ADD;
	public void setBlendOpColor(ZEBlendOp op) { if (op != null) blendOpColor = op; }
	public ZEBlendOp getBlendOpColor() { return blendOpColor; }
	private ZEBlendOp blendOpAlpha = ZEBlendOp.ADD;
	public void setBlendOpAlpha(ZEBlendOp op) { if (op != null) blendOpAlpha = op; }
	public ZEBlendOp getBlendOpAlpha() { return blendOpAlpha; }
	
	private ZEFrontBack cullMode = ZEFrontBack.BACK;
	public void setCullMode(ZEFrontBack mode) { if (mode != null) cullMode = mode; }
	public ZEFrontBack getCullMode() { return cullMode; }
	public boolean frontFaceCW = false;
	
	public float depthMax = 1, depthMin = 0;
	private ZEDepthCompare depthCompare = ZEDepthCompare.LESS;
	public void setDepthCompare(ZEDepthCompare cmp) { if (cmp != null) depthCompare = cmp; }
	public ZEDepthCompare getDepthCompare() { return depthCompare; }
	public boolean depthTestEnabled = false;
	public boolean depthWriteEnable = true;
	
	private ZEGeometryType inputGeometry = ZEGeometryType.TRIANGLES;
	public void setInputGeometryType(ZEGeometryType type) { if (type != null) inputGeometry = type; }
	public ZEGeometryType getInputGeometryType() { return inputGeometry; }
	private ZEPolygonMode polygonMode = ZEPolygonMode.FILL;
	public void setPolygonMode(ZEPolygonMode mode) { if (mode != null) polygonMode = mode; }
	public ZEPolygonMode getPolygonMode() { return polygonMode; }
	public boolean primitiveRestart;
	private boolean enablePrimitiveRestart;
	
	public GLShaderProgram shaderProgram = null;
	
	private PrimitiveType indexType = PrimitiveType.UINT;
	public PrimitiveType getIndexType() { return indexType; }
	public void setIndexType(PrimitiveType type) { if (type != null) indexType = type; }
	
	
	public static int getGLFactor(ZEBlendFactor factor) {
		switch(factor) {
		case CONSTANT_ALPHA: return GL14.GL_CONSTANT_ALPHA;
		case CONSTANT_COLOR: return GL14.GL_CONSTANT_COLOR;
		case DST_ALPHA: return GL11.GL_DST_ALPHA;
		case DST_COLOR: return GL11.GL_DST_COLOR;
		case ONE: return GL11.GL_ONE;
		case ONE_MINUS_CONSTANT_ALPHA: return GL14.GL_ONE_MINUS_CONSTANT_ALPHA;
		case ONE_MINUS_CONSTANT_COLOR: return GL14.GL_ONE_MINUS_CONSTANT_COLOR;
		case ONE_MINUS_DST_ALPHA: return GL11.GL_ONE_MINUS_DST_ALPHA;
		case ONE_MINUS_DST_COLOR: return GL11.GL_ONE_MINUS_DST_COLOR;
		case ONE_MINUS_SRC_ALPHA: return GL11.GL_ONE_MINUS_SRC_ALPHA;
		case ONE_MINUS_SRC_COLOR: return GL11.GL_ONE_MINUS_SRC_COLOR;
		case SRC_ALPHA: return GL11.GL_SRC_ALPHA;
		case SRC_COLOR: return GL11.GL_SRC_COLOR;
		case ZERO: return GL11.GL_ZERO;
		}
		return 0;
	}
	
	public static int getGLBlendOp(ZEBlendOp op) {
		switch(op) {
		case ADD: return GL14.GL_FUNC_ADD;
		case MAX: return GL14.GL_MAX;
		case MIN: return GL14.GL_MIN;
		case REVERSE_SUBTRACT: return GL14.GL_FUNC_REVERSE_SUBTRACT;
		case SUBTRACT: return GL14.GL_FUNC_SUBTRACT;
		}
		return 0;
	}
	
	public static int getGLCullMode(ZEFrontBack mode) {
		switch(mode) {
		case BACK: return GL11.GL_BACK;
		case FRONT: return GL11.GL_FRONT;
		case FRONT_BACK: return GL11.GL_FRONT_AND_BACK;
		}
		return 0;
	}
	
	public static int getGLDepthCompare(ZEDepthCompare cmp) {
		switch(cmp) {
		case ALWAYS: return GL11.GL_ALWAYS;
		case EQUAL: return GL11.GL_EQUAL;
		case GREATER: return GL11.GL_GREATER;
		case GREATER_OR_EQUAL: return GL11.GL_GEQUAL;
		case LESS: return GL11.GL_LESS;
		case LESS_OR_EQUAL: return GL11.GL_LEQUAL;
		case NEVER: return GL11.GL_NEVER;
		case NOT_EQUAL: return GL11.GL_NOTEQUAL;
		}
		return 0;
	}
	
	public static int getGLPolygonMode(ZEPolygonMode mode) {
		switch(mode) {
		case FILL: return GL11.GL_FILL;
		case LINE: return GL11.GL_LINE;
		case POINT: return GL11.GL_POINT;
		}
		return 0;
	}
	
	public static int getGLInputGeometryType(ZEGeometryType type) {
		switch(type) {
		case LINES: return GL11.GL_LINES;
		case LINE_STRIP: return GL11.GL_LINE_STRIP;
		case POINTS: return GL11.GL_POINTS;
		case TRIANGLES: return GL11.GL_TRIANGLES;
		case TRIANGLE_FAN: return GL11.GL_TRIANGLE_FAN;
		case TRIANGLE_STRIP: return GL11.GL_TRIANGLE_STRIP;
		}
		return 0;
	}
	
	public static int getGLType(PrimitiveType type) {
		switch(type) {
		case BYTE: return GL11.GL_BYTE;
		case SHORT: return GL11.GL_SHORT;
		case INT: return GL11.GL_INT;
		case UBYTE: return GL11.GL_UNSIGNED_BYTE;
		case USHORT: return GL11.GL_UNSIGNED_SHORT;
		case UINT: return GL11.GL_UNSIGNED_INT;
		case DOUBLE: return GL11.GL_DOUBLE;
		case FLOAT: return GL11.GL_FLOAT;
		default:
		}
		return 0;
	}
	
	public void bind() {
		GL14.glBlendColor(blendConstColor.x, blendConstColor.y, blendConstColor.z, blendConstColor.w);
		if (blendEnabled) GL11.glEnable(GL11.GL_BLEND);
		else GL11.glDisable(GL11.GL_BLEND);
		GL14.glBlendFuncSeparate(getGLFactor(blendSrcColor), getGLFactor(blendDstColor), getGLFactor(blendSrcAlpha), getGLFactor(blendDstAlpha));
		GL20.glBlendEquationSeparate(getGLBlendOp(blendOpColor), getGLBlendOp(blendOpAlpha));
		
		GL11.glCullFace(getGLCullMode(cullMode));
		GL11.glFrontFace(frontFaceCW ? GL11.GL_CW : GL11.GL_CCW);
		
		GL11.glDepthRange(depthMin, depthMax);
		GL11.glDepthFunc(getGLDepthCompare(depthCompare));
		if (depthTestEnabled) GL11.glEnable(GL11.GL_DEPTH_TEST);
		else GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(depthWriteEnable);
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, getGLPolygonMode(polygonMode));
		if (enablePrimitiveRestart) {
			if (primitiveRestart) {
				GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);
				GL31.glPrimitiveRestartIndex(0xFFFFFFFF);
			} else GL11.glDisable(GL31.GL_PRIMITIVE_RESTART);
		}
		
		GL20.glUseProgram(shaderProgram != null ? shaderProgram.programId : 0);
	}
	
	public void bind(GLPipelineState previous) {
		if (!blendConstColor.equals(previous.blendConstColor)) GL14.glBlendColor(blendConstColor.x, blendConstColor.y, blendConstColor.z, blendConstColor.w);
		if (blendEnabled ^ previous.blendEnabled) {
			if (blendEnabled) GL11.glEnable(GL11.GL_BLEND);
			else GL11.glDisable(GL11.GL_BLEND);
		}
		if (blendSrcColor != previous.blendSrcColor || blendDstColor != previous.blendDstColor || blendSrcAlpha != previous.blendSrcAlpha || blendDstAlpha != previous.blendDstAlpha)
			GL14.glBlendFuncSeparate(getGLFactor(blendSrcColor), getGLFactor(blendDstColor), getGLFactor(blendSrcAlpha), getGLFactor(blendDstAlpha));
		if (blendOpColor != previous.blendOpColor || blendOpAlpha != previous.blendOpColor)
			GL20.glBlendEquationSeparate(getGLBlendOp(blendOpColor), getGLBlendOp(blendOpAlpha));
		
		if (cullMode != previous.cullMode) GL11.glCullFace(getGLCullMode(cullMode));
		if (frontFaceCW != previous.frontFaceCW) GL11.glFrontFace(frontFaceCW ? GL11.GL_CW : GL11.GL_CCW);
		
		if (depthMin != previous.depthMin || depthMax != previous.depthMax) GL11.glDepthRange(depthMin, depthMax);
		if (depthCompare != previous.depthCompare) GL11.glDepthFunc(getGLDepthCompare(depthCompare));
		if (depthTestEnabled ^ previous.depthTestEnabled) {
			if (depthTestEnabled) GL11.glEnable(GL11.GL_DEPTH_TEST);
			else GL11.glDisable(GL11.GL_DEPTH_TEST);
		}
		if (depthWriteEnable ^ previous.depthWriteEnable) GL11.glDepthMask(depthWriteEnable);
		
		if (polygonMode != previous.polygonMode) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, getGLPolygonMode(polygonMode));
		if (enablePrimitiveRestart || previous.enablePrimitiveRestart) {
			if (primitiveRestart ^ previous.primitiveRestart) {
				GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);
				GL31.glPrimitiveRestartIndex(0xFFFFFFFF);
			} else GL11.glDisable(GL31.GL_PRIMITIVE_RESTART);
		}
		
		if (shaderProgram != previous.shaderProgram) GL20.glUseProgram(shaderProgram != null ? shaderProgram.programId : 0);
	}
	
	public GLPipelineState(GLRenderBackend backend) {
		this.backend = backend;
		GLCapabilities caps = backend.getCapabilities();
		enablePrimitiveRestart = caps.glPrimitiveRestartIndex != 0;
	}
	
	public GLPipelineState clone() {
		GLPipelineState other = new GLPipelineState(backend);
		other.blendConstColor.set(blendConstColor);
		other.blendDstAlpha = blendDstAlpha;
		other.blendDstColor = blendDstColor;
		other.blendEnabled = blendEnabled;
		other.blendOpAlpha = blendOpAlpha;
		other.blendOpColor = blendOpColor;
		other.blendSrcAlpha = blendSrcAlpha;
		other.blendSrcColor = blendSrcColor;
		other.cullMode = cullMode;
		other.depthCompare = depthCompare;
		other.depthMax = depthMax;
		other.depthMin = depthMin;
		other.depthTestEnabled = depthTestEnabled;
		other.depthWriteEnable = depthWriteEnable;
		other.frontFaceCW = frontFaceCW;
		other.inputGeometry = inputGeometry;
		other.polygonMode = polygonMode;
		other.primitiveRestart = primitiveRestart;
		other.shaderProgram = shaderProgram;
		return other;
	}
	
	public void set(GLPipelineState other) {
		blendConstColor.set(other.blendConstColor);
		blendDstAlpha = other.blendDstAlpha;
		blendDstColor = other.blendDstColor;
		blendEnabled = other.blendEnabled;
		blendOpAlpha = other.blendOpAlpha;
		blendOpColor = other.blendOpColor;
		blendSrcAlpha = other.blendSrcAlpha;
		blendSrcColor = other.blendSrcColor;
		cullMode = other.cullMode;
		depthCompare = other.depthCompare;
		depthMax = other.depthMax;
		depthMin = other.depthMin;
		depthTestEnabled = other.depthTestEnabled;
		depthWriteEnable = other.depthWriteEnable;
		frontFaceCW = other.frontFaceCW;
		inputGeometry = other.inputGeometry;
		polygonMode = other.polygonMode;
		primitiveRestart = other.primitiveRestart;
		shaderProgram = other.shaderProgram;
	}
}
