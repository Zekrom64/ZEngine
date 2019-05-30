package com.zekrom_64.ze.gl.objects;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector4F;
import com.zekrom_64.ze.base.backend.render.obj.ZECompareOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendFactor;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEPolygonMode;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEStencilModifyOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEStencilTestCondition;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLValues;
import com.zekrom_64.ze.gl.shader.GLShaderProgram;

/** The pipeline state object maintains a "snapshot" of the OpenGL state that can be
 * used as a pipeline object.
 * 
 * @author Zekrom_64
 *
 */
public class GLPipelineState implements Cloneable {

	// The OpenGL backend/context this state belongs to
	private GLRenderBackend backend;
	
	public interface GLPipelineStateModule {
		
		public void bind(GLPipelineState current);
		
		public void bind(GLPipelineState previous, GLPipelineState current);
		
		public void set(GLPipelineState other);
		
	}
	
	public class GLPipelineBlendState implements GLPipelineStateModule {
	
		/** The color constant to be used in color blending. */
		public final Vector4F blendConstColor = new Vector4F(0,0,0,0);
		/** If color blending is enabled */
		public boolean blendEnabled = false;
		private ZEBlendFactor blendSrcColor = ZEBlendFactor.ONE;
		/** Sets the blend source factor */
		public void setBlendSrcColor(ZEBlendFactor factor) { if (factor != null) blendSrcColor = factor; }
		/** Gets the blend source factor */
		public ZEBlendFactor getBlendSrcColor() { return blendSrcColor; }
		private ZEBlendFactor blendDstColor = ZEBlendFactor.ZERO;
		/** Sets the blend destination factor */
		public void setBlendDstColor(ZEBlendFactor factor) { if (factor != null) blendDstColor = factor; }
		/** Gets the blend destination factor */
		public ZEBlendFactor getBlendDstColor() { return blendDstColor; }
		private ZEBlendFactor blendSrcAlpha = ZEBlendFactor.ONE;
		/** Sets the blend source alpha factor */
		public void setBlendSrcAlpha(ZEBlendFactor factor) { if (factor != null) blendSrcAlpha = factor; }
		/** Gets the blend source alpha factor */
		public ZEBlendFactor getBlendSrcAlpha() { return blendSrcAlpha; }
		private ZEBlendFactor blendDstAlpha = ZEBlendFactor.ZERO;
		/** Sets the blend destination alpha factor */
		public void setBlendDstAlpha(ZEBlendFactor factor) { if (factor != null) blendDstAlpha = factor; }
		/** Gets the blend destination alpha factor */
		public ZEBlendFactor getBlendDstAlpha() { return blendDstAlpha; }
		private ZEBlendOp blendOpColor = ZEBlendOp.ADD;
		/** Sets the color blending operation */
		public void setBlendOpColor(ZEBlendOp op) { if (op != null) blendOpColor = op; }
		/** Gets the color blending operation */
		public ZEBlendOp getBlendOpColor() { return blendOpColor; }
		private ZEBlendOp blendOpAlpha = ZEBlendOp.ADD;
		/** Sets the alpha blending operation */
		public void setBlendOpAlpha(ZEBlendOp op) { if (op != null) blendOpAlpha = op; }
		/** Gets the alpha blending operation */
		public ZEBlendOp getBlendOpAlpha() { return blendOpAlpha; }
		
		@Override
		public void bind(GLPipelineState current) {
			if (blendEnabled) GL11.glEnable(GL11.GL_BLEND);
			else GL11.glDisable(GL11.GL_BLEND);
			backend.checkErrorFine();
			GL14.glBlendFuncSeparate(GLValues.getGLFactor(blendSrcColor), GLValues.getGLFactor(blendDstColor), GLValues.getGLFactor(blendSrcAlpha), GLValues.getGLFactor(blendDstAlpha));
			backend.checkErrorFine();
			GL20.glBlendEquationSeparate(GLValues.getGLBlendOp(blendOpColor), GLValues.getGLBlendOp(blendOpAlpha));
			backend.checkErrorFine();
			GL14.glBlendColor(blendConstColor.x, blendConstColor.y, blendConstColor.z, blendConstColor.w);
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to bind pipline blend state");
		}
		
		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (blendEnabled ^ previous.blendState.blendEnabled) {
				if (blendEnabled) GL11.glEnable(GL11.GL_BLEND);
				else GL11.glDisable(GL11.GL_BLEND);
				backend.checkErrorFine();
			}
			if (blendSrcColor != previous.blendState.blendSrcColor || blendDstColor != previous.blendState.blendDstColor || blendSrcAlpha != previous.blendState.blendSrcAlpha || blendDstAlpha != previous.blendState.blendDstAlpha) {
				GL14.glBlendFuncSeparate(GLValues.getGLFactor(blendSrcColor), GLValues.getGLFactor(blendDstColor), GLValues.getGLFactor(blendSrcAlpha), GLValues.getGLFactor(blendDstAlpha));
				backend.checkErrorFine();
			}
			if (blendOpColor != previous.blendState.blendOpColor || blendOpAlpha != previous.blendState.blendOpColor) {
				GL20.glBlendEquationSeparate(GLValues.getGLBlendOp(blendOpColor), GLValues.getGLBlendOp(blendOpAlpha));
				backend.checkErrorFine();
			}
			if (!blendConstColor.equals(previous.blendState.blendConstColor)) {
				GL14.glBlendColor(blendConstColor.x, blendConstColor.y, blendConstColor.z, blendConstColor.w);
				backend.checkErrorFine();
			}
			backend.checkErrorCoarse("Failed to bind pipline blend state");
		}
		
		@Override
		public void set(GLPipelineState other) {
			blendDstAlpha = other.blendState.blendDstAlpha;
			blendDstColor = other.blendState.blendDstColor;
			blendEnabled = other.blendState.blendEnabled;
			blendOpAlpha = other.blendState.blendOpAlpha;
			blendOpColor = other.blendState.blendOpColor;
			blendSrcAlpha = other.blendState.blendSrcAlpha;
			blendSrcColor = other.blendState.blendSrcColor;
			blendConstColor.set(other.blendState.blendConstColor);
		}
	
	}
	
	/** The pipeline's color blending state */
	public final GLPipelineBlendState blendState = new GLPipelineBlendState();
	
	public class GLPipelineDepthViewportScissorState implements GLPipelineStateModule {
	
		private boolean enableViewportArray;
		private boolean enableScissorArray;
		
		/** Depth bound values */
		public float depthMax = 1, depthMin = 0;
		private ZECompareOp depthCompare = ZECompareOp.LESS;
		/** Sets the depth compare function */
		public void setDepthCompare(ZECompareOp cmp) { if (cmp != null) depthCompare = cmp; }
		/** Gets the depth comapre function */
		public ZECompareOp getDepthCompare() { return depthCompare; }
		/** If the depth test is enabled */
		public boolean depthTestEnabled = false;
		/** If writing to the depth buffer is enabled */
		public boolean depthWriteEnable = true;
		/** If depth value clamping is enabled */
		public boolean depthClampEnabled = false;
		/** Depth biasing constant factor */
		public float depthBiasConstant = 0;
		/** Depth bias constant factor */
		public float depthBiasClamp = 0;
		/** Depth bias slope factor */
		public float depthBiasSlope = 0;
		
		private ZEViewport[] viewports = new ZEViewport[0];
		/** Sets the rendering viewports to use. */
		public void setViewports(ZEViewport ... viewports) {
			if (viewports != null) this.viewports = viewports;
		}
		/** Gets the rendering viewports in use. */
		public ZEViewport[] getViewports() {
			return Arrays.copyOf(viewports, viewports.length);
		}
		
		private Rectangle[] scissors = new Rectangle[0];
		/** Sets the rendering scissors to use. */
		public void setScissors(Rectangle ... scissors) {
			if (scissors != null) this.scissors = scissors;
		}
		/** Gets the rendering scissors in use. */
		public Rectangle[] getScissors() {
			return Arrays.copyOf(scissors, scissors.length);
		}
		
		@Override
		public void bind(GLPipelineState current) {
			GL11.glDepthFunc(GLValues.getGLCompare(depthCompare));
			backend.checkErrorFine();
			if (depthTestEnabled) GL11.glEnable(GL11.GL_DEPTH_TEST);
			else GL11.glDisable(GL11.GL_DEPTH_TEST);
			backend.checkErrorFine();
			GL11.glDepthMask(depthWriteEnable);
			backend.checkErrorFine();
			
			double minDepth = depthMin, maxDepth = depthMax;
			if (enableViewportArray) {
				try (MemoryStack sp = MemoryStack.stackPush()) {
					FloatBuffer viewportBuf = sp.mallocFloat(4 * viewports.length);
					for(ZEViewport vp : viewports) {
						viewportBuf.put((float)vp.area.position.x);
						viewportBuf.put((float)vp.area.position.y);
						viewportBuf.put((float)vp.area.extent.x);
						viewportBuf.put((float)vp.area.extent.y);
						minDepth = Math.max(minDepth, vp.minDepth);
						maxDepth = Math.min(maxDepth, vp.maxDepth);
					}
					GL41.glViewportArrayv(0, viewportBuf);
					backend.checkErrorFine();
				}
			} else if (viewports.length > 0) {
				ZEViewport vp = viewports[0];
				minDepth = Math.max(minDepth, vp.minDepth);
				maxDepth = Math.min(maxDepth, vp.maxDepth);
				GL11.glViewport(
						(int)vp.area.position.x,
						(int)vp.area.position.y,
						(int)vp.area.extent.x,
						(int)vp.area.extent.y);
				backend.checkErrorFine();
			}
			GL11.glDepthRange(minDepth, maxDepth);
			backend.checkErrorFine();
			
			if (enableScissorArray) {
				try (MemoryStack sp = MemoryStack.stackPush()) {
					IntBuffer scissorBuf = sp.mallocInt(4 * viewports.length);
					for(Rectangle scissor : scissors) {
						scissorBuf.put((int)scissor.getPositionX());
						scissorBuf.put((int)scissor.getPositionY());
						scissorBuf.put((int)scissor.getWidth());
						scissorBuf.put((int)scissor.getHeight());
					}
					GL41.glScissorArrayv(0, scissorBuf);
					backend.checkErrorFine();
				}
			} else {
				Rectangle sc = scissors[0];
				GL11.glScissor((int)sc.getPositionX(), (int)sc.getPositionY(), (int)sc.getWidth(), (int)sc.getHeight());
				backend.checkErrorFine();
			}
			backend.checkErrorCoarse("Failed to bind pipline depth/viewport/scissor state");
		}
		
		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			// Depth test
			if (depthCompare != previous.depthViewportScissorState.depthCompare) {
				GL11.glDepthFunc(GLValues.getGLCompare(depthCompare));
				backend.checkErrorFine();
			}
			if (depthTestEnabled ^ previous.depthViewportScissorState.depthTestEnabled) {
				if (depthTestEnabled) GL11.glEnable(GL11.GL_DEPTH_TEST);
				else GL11.glDisable(GL11.GL_DEPTH_TEST);
				backend.checkErrorFine();
			}
			if (depthWriteEnable ^ previous.depthViewportScissorState.depthWriteEnable) {
				GL11.glDepthMask(depthWriteEnable);
				backend.checkErrorFine();
			}
			
			double minDepth = depthMin, maxDepth = depthMax;
			
			// Viewport and scissors
			boolean updateViewports = scissors.length != scissors.length;
			if (!updateViewports) {
				for(int i = 0; i < viewports.length; i++) {
					if (!viewports[i].equals(previous.depthViewportScissorState.viewports[i])) {
						updateViewports = true;
						break;
					}
				}
			}
			if (updateViewports) {
				if (enableViewportArray) {
					try (MemoryStack sp = MemoryStack.stackPush()) {
						FloatBuffer viewportBuf = sp.mallocFloat(4 * viewports.length);
						for(ZEViewport vp : viewports) {
							viewportBuf.put((float)vp.area.position.x);
							viewportBuf.put((float)vp.area.position.y);
							viewportBuf.put((float)vp.area.extent.x);
							viewportBuf.put((float)vp.area.extent.y);
							minDepth = Math.max(minDepth, vp.minDepth);
							maxDepth = Math.min(maxDepth, vp.maxDepth);
						}
						GL41.glViewportArrayv(0, viewportBuf);
						backend.checkErrorFine();
					}
				} else if (viewports.length > 0) {
					ZEViewport vp = viewports[0];
					minDepth = Math.max(minDepth, vp.minDepth);
					maxDepth = Math.min(maxDepth, vp.maxDepth);
					GL11.glViewport(
							(int)vp.area.position.x,
							(int)vp.area.position.y,
							(int)vp.area.extent.x,
							(int)vp.area.extent.y);
					backend.checkErrorFine();
				}
			}
			boolean updateScissors = scissors.length != scissors.length;
			if (!updateScissors) {
				for(int i = 0; i < scissors.length; i++) {
					if (!scissors[i].equals(previous.depthViewportScissorState.scissors[i])) {
						updateScissors = true;
						break;
					}
				}
			}
			if (updateScissors) {
				if (enableScissorArray) {
					try (MemoryStack sp = MemoryStack.stackPush()) {
						IntBuffer scissorBuf = sp.mallocInt(4 * viewports.length);
						for(Rectangle scissor : scissors) {
							scissorBuf.put((int)scissor.getPositionX());
							scissorBuf.put((int)scissor.getPositionY());
							scissorBuf.put((int)scissor.getWidth());
							scissorBuf.put((int)scissor.getHeight());
						}
						GL41.glScissorArrayv(0, scissorBuf);
						backend.checkErrorFine();
					}
				} else {
					Rectangle sc = scissors[0];
					GL11.glScissor((int)sc.getPositionX(), (int)sc.getPositionY(), (int)sc.getWidth(), (int)sc.getHeight());
					backend.checkErrorFine();
				}
			}
			
			if (minDepth != previous.depthViewportScissorState.depthMin || maxDepth != previous.depthViewportScissorState.depthMax) {
				GL11.glDepthRange(minDepth, maxDepth);
				backend.checkErrorFine();
			}
			backend.checkErrorCoarse("Failed to bind pipline depth/viewport/scissor state");
		}
		
		@Override
		public void set(GLPipelineState other) {
			depthCompare = other.depthViewportScissorState.depthCompare;
			depthTestEnabled = other.depthViewportScissorState.depthTestEnabled;
			depthWriteEnable = other.depthViewportScissorState.depthWriteEnable;
			depthClampEnabled = other.depthViewportScissorState.depthClampEnabled;
			viewports = other.depthViewportScissorState.viewports;
			scissors = other.depthViewportScissorState.scissors;
			depthBiasConstant = other.depthViewportScissorState.depthBiasConstant;
			depthBiasClamp = other.depthViewportScissorState.depthBiasClamp;
			depthBiasSlope = other.depthViewportScissorState.depthBiasSlope;
			depthMax = other.depthViewportScissorState.depthMax;
			depthMin = other.depthViewportScissorState.depthMin;
		}
	
	}
	
	/** The pipeline's depth, depth test, viewport, and scissor state */
	public final GLPipelineDepthViewportScissorState depthViewportScissorState = new GLPipelineDepthViewportScissorState();
	
	public class GLPipelinePolygonState implements GLPipelineStateModule {
	
		private ZEFrontBack cullMode = ZEFrontBack.BACK;
		/** Sets the culling mode */
		public void setCullMode(ZEFrontBack mode) { if (mode != null) cullMode = mode; }
		/** Gets the culling mode */
		public ZEFrontBack getCullMode() { return cullMode; }
		/** True if the culling "front face" is clockwise, false if it is counter-clockwise */
		public boolean frontFaceCW = false;
		private ZEPolygonMode polygonMode = ZEPolygonMode.FILL;
		/** Sets the polygon mode */
		public void setPolygonMode(ZEPolygonMode mode) { if (mode != null) polygonMode = mode; }
		/** Gets the polygon mode */
		public ZEPolygonMode getPolygonMode() { return polygonMode; }
		/** The width of lines drawn by the pipeline */
		public float lineWidth = 0;
		
		@Override
		public void bind(GLPipelineState current) {
			GL11.glCullFace(GLValues.getGLFace(cullMode));
			backend.checkErrorFine();
			GL11.glFrontFace(frontFaceCW ? GL11.GL_CW : GL11.GL_CCW);
			backend.checkErrorFine();
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GLValues.getGLPolygonMode(polygonMode));
			backend.checkErrorFine();
			GL11.glLineWidth(lineWidth);
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to bind pipeline polygon state");
		}
		
		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (cullMode != previous.polygonState.cullMode) {
				GL11.glCullFace(GLValues.getGLFace(cullMode));
				backend.checkErrorFine();
			}
			if (frontFaceCW != previous.polygonState.frontFaceCW) {
				GL11.glFrontFace(frontFaceCW ? GL11.GL_CW : GL11.GL_CCW);
				backend.checkErrorFine();
			}
			if (polygonMode != previous.polygonState.polygonMode) {
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GLValues.getGLPolygonMode(polygonMode));
				backend.checkErrorFine();
			}
			if (lineWidth != previous.polygonState.lineWidth) {
				GL11.glLineWidth(lineWidth);
				backend.checkErrorFine();
			}
			backend.checkErrorCoarse("Failed to bind pipeline polygon state");
		}
		
		@Override
		public void set(GLPipelineState other) {
			cullMode = other.polygonState.cullMode;
			frontFaceCW = other.polygonState.frontFaceCW;
			polygonMode = other.polygonState.polygonMode;
			lineWidth = other.polygonState.lineWidth;
		}
	
	}
	
	/** The pipeline's polygon rendering state */
	public final GLPipelinePolygonState polygonState = new GLPipelinePolygonState();
	
	public class GLPipelineGeometryState implements GLPipelineStateModule {
	
		private ZEPrimitiveType indexType = ZEPrimitiveType.UINT;
		/** Gets the primitive type of indices */
		public ZEPrimitiveType getIndexType() { return indexType; }
		/** Sets the primitive type of indices */
		public void setIndexType(ZEPrimitiveType type) { if (type != null) indexType = type; }
		/** If primtive drawing can be restarted by special index values */
		public boolean primitiveRestart;
		private boolean enablePrimitiveRestart;
		private ZEGeometryType inputGeometry = ZEGeometryType.TRIANGLES;
		/** Sets the input geometry type */
		public void setInputGeometryType(ZEGeometryType type) { if (type != null) inputGeometry = type; }
		/** Gets the input geometry type */
		public ZEGeometryType getInputGeometryType() { return inputGeometry; }
		
		@Override
		public void bind(GLPipelineState current) {
			if (enablePrimitiveRestart) {
				if (primitiveRestart) {
					GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);
					backend.checkErrorFine();
					GL31.glPrimitiveRestartIndex(0xFFFFFFFF);
					backend.checkErrorFine();
				} else {
					GL11.glDisable(GL31.GL_PRIMITIVE_RESTART);
					backend.checkErrorFine();
				}
			}
			backend.checkErrorCoarse("Failed to bind pipline geometry state");
		}
		
		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (enablePrimitiveRestart) {
				if (primitiveRestart ^ previous.geometryState.primitiveRestart) {
					if (primitiveRestart) {
						GL11.glEnable(GL31.GL_PRIMITIVE_RESTART);
						backend.checkErrorFine();
						GL31.glPrimitiveRestartIndex(0xFFFFFFFF);
						backend.checkErrorFine();
					} else {
						GL11.glDisable(GL31.GL_PRIMITIVE_RESTART);
						backend.checkErrorFine();
					}
				}
			}
			backend.checkErrorCoarse("Failed to bind pipeline geometry state");
		}
		
		@Override
		public void set(GLPipelineState other) {
			inputGeometry = other.geometryState.inputGeometry;
			primitiveRestart = other.geometryState.primitiveRestart;
		}
	
	}
	
	/** The pipeline's geometry input state */
	public final GLPipelineGeometryState geometryState = new GLPipelineGeometryState();
	
	public class GLPipelineShaderState implements GLPipelineStateModule {
		
		/** The currently bound shader program */
		public GLShaderProgram shaderProgram = null;

		@Override
		public void bind(GLPipelineState current) {
			GL20.glUseProgram(shaderProgram != null ? shaderProgram.programObject : 0);
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to bind pipeline shader state");
		}

		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (shaderProgram != previous.shaderState.shaderProgram) {
				GL20.glUseProgram(shaderProgram != null ? shaderProgram.programObject : 0);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to bind pipeline shader state");
			}
		}

		@Override
		public void set(GLPipelineState other) {
			shaderProgram = other.shaderState.shaderProgram;
		}
	
	}
	
	/** The pipeline's shader state */
	public final GLPipelineShaderState shaderState = new GLPipelineShaderState();
	
	public class GLPipelineStencilState implements GLPipelineStateModule {
	
		private int stencilCompareMaskFront = 0;
		private int stencilCompareMaskBack = 0;
		/** Sets the stencil compare mask for the given face(s). */
		public void setStencilCompareMask(ZEFrontBack face, int mask) {
			if (face.front) stencilCompareMaskFront = mask;
			if (face.back) stencilCompareMaskBack = mask;
		}
		/** Gets the stencil compare mask for the given face. */
		public int getStencilCompareMask(boolean back) {
			if (back) return stencilCompareMaskBack;
			else return stencilCompareMaskFront;
		}
		
		private int stencilWriteMaskFront = 0;
		private int stencilWriteMaskBack = 0;
		/** Sets the stencil write mask for the given face(s). */
		public void setStencilWriteMask(ZEFrontBack face, int mask) {
			if (face.front) stencilWriteMaskFront = mask;
			if (face.back) stencilWriteMaskBack = mask;
		}
		/** Gets the stencil write mask for the given face. */
		public int getStencilWriteMask(boolean back) {
			if (back) return stencilWriteMaskBack;
			else return stencilWriteMaskFront;
		}
		
		private int stencilReferenceFront = 0;
		private int stencilReferenceBack = 0;
		/** Sets the stencil reference value for the given face(s). */
		public void setStencilReference(ZEFrontBack face, int ref) {
			if (face.front) stencilReferenceFront = ref;
			if (face.back) stencilReferenceBack = ref;
		}
		/** Gets the stencil reference value for the given face. */
		public int getStencilReference(boolean back) {
			if (back) return stencilReferenceBack;
			else return stencilReferenceFront;
		}
		
		private ZECompareOp stencilCompareOpFront = ZECompareOp.ALWAYS;
		private ZECompareOp stencilCompareOpBack = ZECompareOp.ALWAYS;
		public void setStencilCompareOp(ZEFrontBack face, ZECompareOp op) {
			if (op == null) return;
			if (face.front) stencilCompareOpFront = op;
			if (face.back) stencilCompareOpBack = op;
		}
		public ZECompareOp getStencilCompareOp(boolean back) {
			if (back) return stencilCompareOpBack;
			else return stencilCompareOpFront;
		}
		
		private ZEStencilModifyOp stencilModifyOpStencilFailBack = ZEStencilModifyOp.KEEP;
		private ZEStencilModifyOp stencilModifyOpStencilFailFront = ZEStencilModifyOp.KEEP;
		private ZEStencilModifyOp stencilModifyOpDepthFailBack = ZEStencilModifyOp.KEEP;
		private ZEStencilModifyOp stencilModifyOpDepthFailFront = ZEStencilModifyOp.KEEP;
		private ZEStencilModifyOp stencilModifyOpSucceedBack = ZEStencilModifyOp.KEEP;
		private ZEStencilModifyOp stencilModifyOpSucceedFront = ZEStencilModifyOp.KEEP;
		public void setStencilModifyOp(ZEFrontBack face, ZEStencilTestCondition cond, ZEStencilModifyOp op) {
			switch(cond) {
			case STENCIL_FAILS:
				if (face.front) stencilModifyOpStencilFailFront = op;
				if (face.back) stencilModifyOpStencilFailBack = op;
				break;
			case DEPTH_FAILS:
				if (face.front) stencilModifyOpDepthFailFront = op;
				if (face.back) stencilModifyOpDepthFailBack = op;
				break;
			case SUCCEEDS:
				if (face.front) stencilModifyOpSucceedFront = op;
				if (face.back) stencilModifyOpSucceedBack = op;
				break;
			}
		}
		
		@Override
		public void bind(GLPipelineState current) {
			GL20.glStencilMaskSeparate(GL11.GL_FRONT, stencilWriteMaskFront);
			backend.checkErrorFine();
			GL20.glStencilMaskSeparate(GL11.GL_BACK, stencilWriteMaskBack);
			backend.checkErrorFine();
			GL20.glStencilFuncSeparate(GL11.GL_FRONT, GLValues.getGLCompare(stencilCompareOpFront), stencilReferenceFront, stencilCompareMaskFront);
			backend.checkErrorFine();
			GL20.glStencilFuncSeparate(GL11.GL_BACK, GLValues.getGLCompare(stencilCompareOpBack), stencilReferenceBack, stencilCompareMaskBack);
			backend.checkErrorFine();
			GL20.glStencilOpSeparate(GL11.GL_FRONT, GLValues.getGLStencilModifyOp(stencilModifyOpStencilFailFront), GLValues.getGLStencilModifyOp(stencilModifyOpDepthFailFront), GLValues.getGLStencilModifyOp(stencilModifyOpSucceedFront));
			backend.checkErrorFine();
			GL20.glStencilOpSeparate(GL11.GL_BACK, GLValues.getGLStencilModifyOp(stencilModifyOpStencilFailBack), GLValues.getGLStencilModifyOp(stencilModifyOpDepthFailBack), GLValues.getGLStencilModifyOp(stencilModifyOpSucceedBack));
			backend.checkErrorFine();
			backend.checkErrorCoarse("Failed to bind pipeline stencil state");
		}
		
		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (stencilWriteMaskFront != previous.stencilState.stencilWriteMaskFront) GL20.glStencilMaskSeparate(GL11.GL_FRONT, stencilWriteMaskFront);
			if (stencilWriteMaskBack != previous.stencilState.stencilWriteMaskBack) GL20.glStencilMaskSeparate(GL11.GL_BACK, stencilWriteMaskBack);
			if (stencilCompareOpFront != previous.stencilState.stencilCompareOpFront ||
				stencilReferenceFront != previous.stencilState.stencilReferenceFront ||
				stencilCompareMaskFront != previous.stencilState.stencilCompareMaskFront) {
				GL20.glStencilFuncSeparate(GL11.GL_FRONT, GLValues.getGLCompare(stencilCompareOpFront), stencilReferenceFront, stencilCompareMaskFront);
				backend.checkErrorFine();
			}
			if (stencilCompareOpBack != previous.stencilState.stencilCompareOpBack ||
				stencilReferenceBack != previous.stencilState.stencilReferenceBack ||
				stencilCompareMaskBack != previous.stencilState.stencilCompareMaskBack) {
				GL20.glStencilFuncSeparate(GL11.GL_BACK, GLValues.getGLCompare(stencilCompareOpBack), stencilReferenceBack, stencilCompareMaskBack);
				backend.checkErrorFine();
			}
			if (stencilModifyOpStencilFailFront != previous.stencilState.stencilModifyOpStencilFailFront ||
				stencilModifyOpDepthFailFront != previous.stencilState.stencilModifyOpDepthFailFront ||
				stencilModifyOpSucceedFront != previous.stencilState.stencilModifyOpSucceedFront) {
				GL20.glStencilOpSeparate(GL11.GL_FRONT, GLValues.getGLStencilModifyOp(stencilModifyOpStencilFailFront), GLValues.getGLStencilModifyOp(stencilModifyOpDepthFailFront), GLValues.getGLStencilModifyOp(stencilModifyOpSucceedFront));
				backend.checkErrorFine();
			}
			if (stencilModifyOpStencilFailBack != previous.stencilState.stencilModifyOpStencilFailBack ||
					stencilModifyOpDepthFailBack != previous.stencilState.stencilModifyOpDepthFailBack ||
					stencilModifyOpSucceedBack != previous.stencilState.stencilModifyOpSucceedBack) {
				GL20.glStencilOpSeparate(GL11.GL_BACK, GLValues.getGLStencilModifyOp(stencilModifyOpStencilFailBack), GLValues.getGLStencilModifyOp(stencilModifyOpDepthFailBack), GLValues.getGLStencilModifyOp(stencilModifyOpSucceedBack));
				backend.checkErrorFine();
			}
			backend.checkErrorCoarse("Failed to bind pipeline stencil state");
		}
		
		@Override
		public void set(GLPipelineState other) {
			stencilWriteMaskFront = other.stencilState.stencilWriteMaskFront;
			stencilWriteMaskBack = other.stencilState.stencilWriteMaskBack;
			
			stencilCompareOpFront = other.stencilState.stencilCompareOpFront;
			stencilCompareOpBack = other.stencilState.stencilCompareOpBack;
			stencilCompareMaskFront = other.stencilState.stencilCompareMaskFront;
			stencilCompareMaskBack = other.stencilState.stencilCompareMaskBack;
			stencilReferenceFront = other.stencilState.stencilReferenceFront;
			stencilReferenceBack = other.stencilState.stencilReferenceBack;
			
			stencilModifyOpStencilFailFront = other.stencilState.stencilModifyOpStencilFailFront;
			stencilModifyOpStencilFailBack = other.stencilState.stencilModifyOpStencilFailBack;
			stencilModifyOpDepthFailFront = other.stencilState.stencilModifyOpDepthFailFront;
			stencilModifyOpDepthFailBack = other.stencilState.stencilModifyOpDepthFailBack;
			stencilModifyOpSucceedFront = other.stencilState.stencilModifyOpSucceedFront;
			stencilModifyOpSucceedBack = other.stencilState.stencilModifyOpSucceedBack;
		}
	
	}
	
	/** The pipeline's stencil buffer state */
	public final GLPipelineStencilState stencilState = new GLPipelineStencilState();
	
	public class GLPipelineTessellationState implements GLPipelineStateModule {

		private boolean enablePointsPerPatch;
		public int pointsPerPatch;
		
		@Override
		public void bind(GLPipelineState current) {
			if (enablePointsPerPatch) {
				GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, pointsPerPatch);
				backend.checkErrorFine();
			}
		}

		@Override
		public void bind(GLPipelineState previous, GLPipelineState current) {
			if (pointsPerPatch != previous.tessellationState.pointsPerPatch && enablePointsPerPatch) {
				GL40.glPatchParameteri(GL40.GL_PATCH_VERTICES, pointsPerPatch);
				backend.checkErrorFine();
			}
		}

		@Override
		public void set(GLPipelineState other) {
			pointsPerPatch = other.tessellationState.pointsPerPatch;
		}
		
	}
	
	/** The pipeline's tessellation state */
	public final GLPipelineTessellationState tessellationState = new GLPipelineTessellationState();
	
	
	/** Assigns all the values in this state object to the current thread's OpenGL context. */
	public void bind() {
		blendState.bind(this);
		depthViewportScissorState.bind(this);
		polygonState.bind(this);
		geometryState.bind(this);
		shaderState.bind(this);
		stencilState.bind(this);
		tessellationState.bind(this);
	}
	
	/** Possibly more efficient version of {@link #bind()} that will only assign values if
	 * they differ from the specified previous state.
	 * 
	 * @param previous Previous pipeline state
	 */
	public void bind(GLPipelineState previous) {
		blendState.bind(previous, this);
		depthViewportScissorState.bind(previous, this);
		polygonState.bind(previous, this);
		geometryState.bind(previous, this);
		shaderState.bind(previous, this);
		stencilState.bind(previous, this);
		tessellationState.bind(previous, this);
	}
	
	/** Creates a new pipeline state belonging to the given OpenGL backend.
	 * 
	 * @param backend OpenGL backend state belongs to
	 */
	public GLPipelineState(GLRenderBackend backend) {
		this.backend = backend;
		GLCapabilities caps = backend.getCapabilities();
		depthViewportScissorState.enableViewportArray = caps.glViewportArrayv !=0 ;
		depthViewportScissorState.enableScissorArray = caps.glScissorArrayv != 0;
		geometryState.enablePrimitiveRestart = caps.glPrimitiveRestartIndex != 0;
		tessellationState.enablePointsPerPatch = caps.glPatchParameteri != 0;
	}
	
	public final ArrayList<ArrayList<Runnable>> vertexInputSetup = new ArrayList<>();
	public final ArrayList<Runnable> vertexInputCleanup = new ArrayList<>();
	
	public GLPipelineState clone() {
		GLPipelineState other = new GLPipelineState(backend);
		other.set(this);
		return other;
	}
	
	/** Sets this pipeline state to another pipeline state.
	 * 
	 * @param other Pipeline state to set to
	 */
	public void set(GLPipelineState other) {
		blendState.set(other);
		depthViewportScissorState.set(other);
		polygonState.set(other);
		geometryState.set(other);
		shaderState.set(other);
		stencilState.set(other);
		tessellationState.set(other);
		vertexInputSetup.clear();
		vertexInputSetup.addAll(other.vertexInputSetup);
		vertexInputCleanup.clear();
		vertexInputCleanup.addAll(other.vertexInputCleanup);
	}
}
