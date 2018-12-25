package com.zekrom_64.ze.gl.impl;

import java.util.HashSet;
import java.util.Set;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLShaderCompiler.GLShaderProgram;

public class GLPipelineBuilder implements ZEPipelineBuilder {

	private GLPipeline pipeline;
	
	public GLPipelineBuilder(GLRenderBackend backend) {
		pipeline = new GLPipeline(backend);
	}
	
	@Override
	public void setShaderProgram(ZEShaderProgram program) {
		pipeline.pipelineState.shaderState.shaderProgram = (GLShaderProgram)program;
	}

	@Override
	public void setInputGeometry(ZEGeometryType type) {
		pipeline.pipelineState.geometryState.setInputGeometryType(type);
	}

	@Override
	public void setPrimitiveRestartEnable(boolean enable) {
		pipeline.pipelineState.geometryState.primitiveRestart = enable;
	}

	@Override
	public void setPolygonFillMode(ZEPolygonMode mode) {
		pipeline.pipelineState.polygonState.setPolygonMode(mode);
	}

	@Override
	public void setCullMode(ZEFrontBack mode) {
		pipeline.pipelineState.polygonState.setCullMode(mode);
	}

	@Override
	public void setFrontFace(boolean cw) {
		pipeline.pipelineState.polygonState.frontFaceCW = cw;
	}

	@Override
	public void setBlendEnable(boolean enable) {
		pipeline.pipelineState.blendState.blendEnabled = enable;
	}

	@Override
	public void setBlendFunc(ZEBlendFactor srcColor, ZEBlendFactor dstColor, ZEBlendOp opColor, ZEBlendFactor srcAlpha,
			ZEBlendFactor dstAlpha, ZEBlendOp opAlpha) {
		pipeline.pipelineState.blendState.setBlendSrcColor(srcColor);
		pipeline.pipelineState.blendState.setBlendDstColor(dstColor);
		pipeline.pipelineState.blendState.setBlendOpColor(opColor);
		pipeline.pipelineState.blendState.setBlendSrcAlpha(srcAlpha);
		pipeline.pipelineState.blendState.setBlendDstAlpha(dstAlpha);
		pipeline.pipelineState.blendState.setBlendOpAlpha(opAlpha);
	}

	@Override
	public void setBlendConstColor(float r, float g, float b, float a) {
		pipeline.pipelineState.blendState.blendConstColor.x = r;
		pipeline.pipelineState.blendState.blendConstColor.y = g;
		pipeline.pipelineState.blendState.blendConstColor.z = b;
		pipeline.pipelineState.blendState.blendConstColor.w = a;
	}

	@Override
	public void setDepthTestEnable(boolean enable) {
		pipeline.pipelineState.depthViewportScissorState.depthTestEnabled = enable;
	}

	@Override
	public void setDepthWriteEnable(boolean enable) {
		pipeline.pipelineState.depthViewportScissorState.depthWriteEnable = enable;
	}

	@Override
	public void setDepthBounds(float min, float max) {
		pipeline.pipelineState.depthViewportScissorState.depthMin = min;
		pipeline.pipelineState.depthViewportScissorState.depthMax = max;
	}

	@Override
	public void setDepthCompare(ZECompareOp op) {
		pipeline.pipelineState.depthViewportScissorState.setDepthCompare(op);
	}

	@Override
	public ZEPipeline build() {
		return pipeline.clone();
	}

	@Override
	public void setViewports(ZEViewport... viewports) {
		pipeline.pipelineState.depthViewportScissorState.setViewports(viewports);
	}

	@Override
	public void setScissors(Rectangle... scissors) {
		pipeline.pipelineState.depthViewportScissorState.setScissors(scissors);
	}

	@Override
	public void setDepthClampEnable(boolean enabled) {
		pipeline.pipelineState.depthViewportScissorState.depthClampEnabled = enabled;
	}

	@Override
	public void setLineWidth(float lineWidth) {
		pipeline.pipelineState.polygonState.lineWidth = lineWidth;
	}
	
	// Doesn't really matter, OpenGL's state is always modifiable, just here to comply with the API
	private final HashSet<String> dynamicState = new HashSet<>();

	@Override
	public Set<String> dynamicState() {
		return dynamicState;
	}

	@Override
	public void setStencilCompareOp(ZEFrontBack face, ZECompareOp op) {
		pipeline.pipelineState.stencilState.setStencilCompareOp(face, op);
	}

	@Override
	public void setStencilValue(ZEFrontBack face, ZEStencilValue stencilVal, int val) {
		switch(stencilVal) {
		case COMPARE_MASK:
			pipeline.pipelineState.stencilState.setStencilCompareMask(face, val);
			break;
		case WRITE_MASK:
			pipeline.pipelineState.stencilState.setStencilWriteMask(face, val);
			break;
		case COMPARE_REFERENCE:
			pipeline.pipelineState.stencilState.setStencilReference(face, val);
			break;
		}
	}

	@Override
	public void setStencilModifyOp(ZEFrontBack face, ZEStencilTestCondition cond, ZEStencilModifyOp op) {
		pipeline.pipelineState.stencilState.setStencilModifyOp(face, cond, op);
	}

}
