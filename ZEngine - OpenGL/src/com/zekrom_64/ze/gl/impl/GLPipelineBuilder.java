package com.zekrom_64.ze.gl.impl;

import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLShaderCompiler.GLShaderProgram;

public class GLPipelineBuilder implements ZEPipelineBuilder {

	private GLPipelineState state;
	
	public GLPipelineBuilder(GLRenderBackend backend) {
		state = new GLPipelineState(backend);
	}
	
	@Override
	public void setShaderProgram(ZEShaderProgram program) {
		state.shaderProgram = (GLShaderProgram)program;
	}

	@Override
	public void setInputGeometry(ZEGeometryType type) {
		state.setInputGeometryType(type);
	}

	@Override
	public void setPrimitiveRestartEnable(boolean enable) {
		state.primitiveRestart = enable;
	}

	@Override
	public void setPolygonFillMode(ZEPolygonMode mode) {
		state.setPolygonMode(mode);
	}

	@Override
	public void setCullMode(ZEFrontBack mode) {
		state.setCullMode(mode);
	}

	@Override
	public void setFrontFace(boolean cw) {
		state.frontFaceCW = cw;
	}

	@Override
	public void setBlendEnable(boolean enable) {
		state.blendEnabled = enable;
	}

	@Override
	public void setBlendFunc(ZEBlendFactor srcColor, ZEBlendFactor dstColor, ZEBlendOp opColor, ZEBlendFactor srcAlpha,
			ZEBlendFactor dstAlpha, ZEBlendOp opAlpha) {
		state.setBlendSrcColor(srcColor);
		state.setBlendDstColor(dstColor);
		state.setBlendOpColor(opColor);
		state.setBlendSrcAlpha(srcAlpha);
		state.setBlendDstAlpha(dstAlpha);
		state.setBlendOpAlpha(opAlpha);
	}

	@Override
	public void setBlendConstColor(float r, float g, float b, float a) {
		state.blendConstColor.set(r, g, b, a);
	}

	@Override
	public void setDepthTestEnable(boolean enable) {
		state.depthTestEnabled = enable;
	}

	@Override
	public void setDepthWriteEnable(boolean enable) {
		state.depthWriteEnable = enable;
	}

	@Override
	public void setDepthBounds(float min, float max) {
		state.depthMin = min;
		state.depthMax = max;
	}

	@Override
	public void setDepthCompare(ZEDepthCompare op) {
		state.setDepthCompare(op);
	}

	@Override
	public ZEPipeline build() {
		return null;
	}

}
