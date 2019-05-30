package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GL44;
import org.lwjgl.opengl.GL45;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCString;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector2I;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEColorClearValue;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder.ZEAttachmentLoadOp;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder.ZEAttachmentUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayer;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureRange;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;
import com.zekrom_64.ze.gl.GLEnvironment;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLExtensions;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLValues;
import com.zekrom_64.ze.gl.objects.GLPipelineState.GLPipelineGeometryState;
import com.zekrom_64.ze.gl.objects.GLRenderPass.GLSubpass;

public class GLCommandBufferInterpreted extends GLCommandBuffer {
	
	public GLCommandBufferInterpreted(GLRenderBackend backend, boolean rewritable) {
		super(backend, rewritable);
	}
	
	// The list of commands to execute
	private List<Runnable> commands = null;

	// Clear color conversion stuff
	private static interface IntToFloat {
		
		public float convert(int i);
		
	}
	
	private static Runnable clearColorToCommand(GLTexture tex, ZEColorClearValue color) {
		IntToFloat itof;
		switch(tex.getPixelFormat().elementType) {
		case FLOAT:
		case DOUBLE: return () -> GL11.glClearColor(color.getFloatR(), color.getFloatG(), color.getFloatB(), color.getFloatA());
		case BYTE:
			itof = (int i) -> (byte)i;
			break;
		case UBYTE:
			itof = (int i) -> i & 0xFF;
			break;
		case SHORT:
			itof = (int i) -> (short)i;
			break;
		case USHORT:
			itof = (int i) -> i & 0xFFFF;
			break;
		case INT:
		case LONG:
			itof = (int i) -> i;
			break;
		case UINT:
		case ULONG:
			itof = (int i) -> i&0xFFFFFFFFL;
			break;
		default: throw new UnsupportedOperationException();
		}
		float r = itof.convert(color.getIntR());
		float g = itof.convert(color.getIntG());
		float b = itof.convert(color.getIntB());
		float a = itof.convert(color.getIntA());
		return () -> GL11.glClearColor(r,g,b,a);
	}
	
	private static void limitCoordsForDimension(Vector3I position, Vector3I size, GLTexture tex, int baseArray, int arrayCount) {
		switch(tex.getDimension()) {
		case DIM_1D:
			position.y = 0;
			position.z = 0;
			if (size != null) {
				size.y = 0;
				size.z = 0;
			}
			break;
		case DIM_1D_ARRAY:
			position.y = baseArray;
			position.z = 0;
			if (size != null) {
				size.y = arrayCount;
				size.z = 0;
			}
			break;
		case DIM_2D:
			position.z = 0;
			if (size != null) {
				size.z = 0;
			}
			break;
		case CUBE:
		case CUBE_ARRAY:
		case DIM_2D_ARRAY:
			position.z = baseArray;
			if (size != null) {
				size.z = arrayCount;
			}
			break;
		case DIM_3D: break;
		}
	}
	
	private void glFramebufferTexture(int target, int attachment, GLTexture tex, int mipLevel, int depth, int arrayIndex) {
		GLExtensions exts = backend.getExtensions();
		switch(tex.getDimension()) {
		case DIM_2D_ARRAY:
		case CUBE:
		case CUBE_ARRAY:
			exts.glFramebufferTextureLayer(target, attachment, tex.textureObject, mipLevel, arrayIndex);
			break;
		case DIM_2D:
			exts.glFramebufferTexture2D(target, attachment, GL11.GL_TEXTURE_2D, tex.textureObject, mipLevel);
			break;
		case DIM_3D:
			exts.glFramebufferTexture3D(target, attachment, GL12.GL_TEXTURE_3D, tex.textureObject, mipLevel, depth);
			break;
		case DIM_1D:
		case DIM_1D_ARRAY: throw new UnsupportedOperationException("Cannot bind 1D texture to framebuffer");
		}
	}
	
	// Recorder implementation
	private class GLInterpreterRecorder implements ZERenderWorkRecorder {

		private List<Runnable> buildingCommands = new ArrayList<>();
		
		@Override
		public void executeBuffer(ZERenderCommandBuffer buffer) {
			buildingCommands.add(((GLCommandBuffer)buffer)::executeCommands);
		}

		private GLRenderPass renderPass = null;
		private int subpass = -1;
		private boolean defaultFramebuffer;
		private GLTexture[] attachments = new GLTexture[0];
		
		// TODO: Add error checks
		@Override
		public void beginPass(ZERenderPass renderPass, ZEFramebuffer framebuffer,
				ZEAttachmentClearValue[] clearValues) {
			GLRenderPass rp = (GLRenderPass)renderPass;
			if (((GLFramebuffer)framebuffer).framebufferObject == 0) { // Default framebuffer
				buildingCommands.add(() -> {
					GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((GLFramebuffer)framebuffer).framebufferObject);
				});
				ZEAttachmentUsage colorUsage = rp.attachmentUsages[0];
				if (colorUsage.loadOp == ZEAttachmentLoadOp.CLEAR) {
					buildingCommands.add(() -> {
						ZEColorClearValue color = clearValues[0].color;
						GL11.glClearColor(color.getFloatR(), color.getFloatG(), color.getFloatB(), color.getFloatA());
						GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
					});
				}
				ZEAttachmentUsage depthstencilUsage = rp.attachmentUsages[1];
				if (depthstencilUsage.loadOp == ZEAttachmentLoadOp.CLEAR) {
					buildingCommands.add(() -> {
						GL11.glClearDepth(clearValues[1].depth);
						GL11.glClearStencil(clearValues[1].stencil);
						GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
					});
				}
				defaultFramebuffer = true;
				attachments = new GLTexture[0];
			} else { // Framebuffer with attachments
				buildingCommands.add(() -> {
					GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((GLFramebuffer)framebuffer).framebufferObject);
				});
				attachments = ((GLFramebuffer)framebuffer).attachments;
				for(int i = 0; i < attachments.length; i++) {
					GLTexture tex = attachments[i];
					boolean isDepth = tex.getPixelFormat().depthType != null;
					boolean isStencil = tex.getPixelFormat().stencilType != null;
					final float depth = clearValues[i].depth;
					final int stencil = clearValues[i].stencil;
					// TODO: Array texture support
					if (isDepth || isStencil) { // Depth and stencil formats are kind of weird
						if (isDepth && !isStencil) {
							buildingCommands.add(() -> {
								backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.textureObject, 0);
								GL11.glClearDepth(depth);
								GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
							});
						} else if (isStencil && !isDepth) {
							buildingCommands.add(() -> {
								backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.textureObject, 0);
								GL11.glClearStencil(stencil);
								GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
							});
						} else {
							buildingCommands.add(() -> {
								backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.textureObject, 0);
								GL11.glClearDepth(depth);
								GL11.glClearStencil(stencil);
								GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
							});
						}
					} else { // Clear the color attachment
						buildingCommands.add(() -> {
							backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, tex.textureObject, 0);
						});
						buildingCommands.add(clearColorToCommand(tex, clearValues[i].color));
					}
				}
				defaultFramebuffer = false;
			}
			renderPass = rp;
			subpass = -1;
			nextPass();
		}

		@Override
		public void nextPass() {
			subpass++;
			GLSubpass subpass = renderPass.subpasses[this.subpass];
			if (!defaultFramebuffer) { // Default framebuffer cannot be remapped
				buildingCommands.add(() -> {
					Integer depthStencil = subpass.depthStencilAttachment;
					//TODO: Array texture support
					if (depthStencil == null)
						backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, 0, 0);
					else
						backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, attachments[depthStencil].textureObject, 0);
					for(int i = 0; i < subpass.colorAttachments.length; i++) {
						Integer color = subpass.colorAttachments[i];
						if (color == null)
							backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0+i, GL11.GL_TEXTURE_2D, 0, 0);
						else
							backend.getExtensions().glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0+i, GL11.GL_TEXTURE_2D, attachments[color].textureObject, 0);
					}
				});
			}
		}

		@Override
		public void endPass() { }

		@Override
		public void bindPipeline(ZEPipeline pipeline) {
			/** Complete */
			buildingCommands.add(() -> {
				GLPipeline oldpipeline = backend.getCurrentPipeline();
				for(Runnable cleanup : oldpipeline.pipelineState.vertexInputCleanup) cleanup.run();
				backend.setCurrentPipeline((GLPipeline)pipeline);
				backend.checkErrorCoarse("Failed to bind pipeline");
			});
		}

		@Override
		public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors) {
			/** Complete */
			if (backend.getCapabilities().glScissorArrayv != 0) {
				IntBuffer pScissors = BufferUtils.createIntBuffer(numScissors * 4);
				for(int i = 0; i < numScissors; i++) {
					Rectangle scissor = scissors[i];
					pScissors.put((int)scissor.getPositionX());
					pScissors.put((int)scissor.getPositionY());
					pScissors.put((int)scissor.getWidth());
					pScissors.put((int)scissor.getHeight());
				}
				pScissors.rewind();
				buildingCommands.add(() -> {
					GL41.glScissorArrayv(firstScissor, pScissors);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set scissor");
				});
			} else if (backend.getCapabilities().glScissorIndexed != 0) {
				int[] posX = new int[numScissors];
				int[] posY = new int[numScissors];
				int[] w = new int[numScissors];
				int[] h = new int[numScissors];
				for(int i = 0; i < numScissors; i++) {
					posX[i] = (int)scissors[i].getPositionX();
					posY[i] = (int)scissors[i].getPositionY();
					w[i] = (int)scissors[i].getWidth();
					h[i] = (int)scissors[i].getHeight();
				}
				buildingCommands.add(() -> {
					for(int i = 0; i < numScissors; i++) {
						GL41.glScissorIndexed(i + firstScissor, posX[i], posY[i], w[i], h[i]);
						backend.checkErrorFine();
					}
					backend.checkErrorCoarse("Failed to set scissor");
				});
			} else if (firstScissor == 0 && numScissors == 1) {
				Rectangle scissor = scissors[0];
				int posX = (int)scissor.getPositionX(), posY = (int)scissor.getPositionY();
				int w = (int)scissor.getWidth(), h = (int)scissor.getHeight();
				buildingCommands.add(() -> {
					GL11.glScissor(posX, posY, w, h);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set scissor");
				});
			} else throw new GLException("Cannot set scissors for multiple viewports without 'glScissorArrayv' or 'glScissorIndexed'");
		}

		@Override
		public void setLineWidth(float width) {
			/** Complete  */
			buildingCommands.add(() -> {
				GL11.glLineWidth(width);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set line width");
			});
		}

		@Override
		public void setDepthBounds(double min, double max) {
			/** Complete */
			buildingCommands.add(() -> {
				GL11.glDepthRange(min, max);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set depth bounds");
			});
		}

		@Override
		public void bindVertexBuffers(int firstBindPoint, ZEBuffer... buffers) {
			/** Complete */
			int[] bufs = new int[buffers.length];
			for(int i = 0; i < buffers.length; i++) bufs[i] = ((GLBuffer)buffers[i]).bufferObject;
			if (backend.getCapabilities().glBindVertexBuffers != 0) {
				IntBuffer pBuffers = BufferUtils.createIntBuffer(buffers.length);
				for(ZEBuffer buf : buffers) pBuffers.put(((GLBuffer)buf).bufferObject);
				pBuffers.rewind();
				buildingCommands.add(() -> {
					try(MemoryStack sp = MemoryStack.stackPush()) {
						PointerBuffer pOffsets = sp.callocPointer(pBuffers.capacity());
						IntBuffer pStrides = sp.callocInt(pBuffers.capacity());
						GL44.glBindVertexBuffers(firstBindPoint, pBuffers, pOffsets, pStrides);
						backend.checkErrorFine();
						backend.checkErrorCoarse("Failed to bind vertex buffers");
					}
				});
			} else if (backend.getCapabilities().glBindVertexBuffer != 0) {
				buildingCommands.add(() -> {
					for(int i = 0; i < bufs.length; i++) {
						GL43.glBindVertexBuffer(i + firstBindPoint, bufs[i], 0, 0);
						backend.checkErrorFine();
					}
					backend.checkErrorCoarse("Failed to bind vertex buffers");
				});
			} else {
				if (firstBindPoint > 0) throw new GLException("Cannot bind vertex buffer to bind point >0 with glBindBuffer");
				GLBuffer buf = (GLBuffer)buffers[0];
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buf.bufferObject);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to bind vertex buffers");
				});
			}
			buildingCommands.add(() -> {
				ArrayList<ArrayList<Runnable>> setup = backend.getCurrentPipeline().pipelineState.vertexInputSetup;
				for(int i = 0; i < bufs.length; i++) {
					ArrayList<Runnable> bindingSetup = setup.get(firstBindPoint + 1);
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufs[i]);
					backend.checkErrorFine();
					for(Runnable r : bindingSetup) r.run();
				}
				backend.checkErrorCoarse("Failed to setup vertex input when binding vertex buffers");
			});
		}

		@Override
		public void bindIndexBuffer(ZEBuffer buffer, ZEPrimitiveType indexType) {
			/** Complete */
			buildingCommands.add(() -> {
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ((GLBuffer)buffer).bufferObject);
				backend.getCurrentPipeline().pipelineState.geometryState.setIndexType(indexType);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to bind index buffer");
			});
		}

		@Override
		public void draw(int nVertices, int start) {
			/** Complete */
			buildingCommands.add(() -> {
				ZEGeometryType geoType = backend.getCurrentPipeline().pipelineState.geometryState.getInputGeometryType();
				GL11.glDrawArrays(GLValues.getGLInputGeometryType(geoType), start, nVertices);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to draw");
			});
		}

		@Override
		public void drawIndexed(int nIndices, int startIndex, int startVertex) {
			/** Complete */
			if (backend.getCapabilities().glDrawElementsBaseVertex != 0) {
				buildingCommands.add(() -> {
					GLPipelineGeometryState state = backend.getCurrentPipeline().pipelineState.geometryState;
					ZEGeometryType geoType = state.getInputGeometryType();
					ZEPrimitiveType indexType = state.getIndexType();
					GL32.glDrawElementsBaseVertex(
						GLValues.getGLInputGeometryType(geoType),
						nIndices,
						GLValues.getGLType(indexType),
						startIndex,
						startVertex
					);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to draw indexed");
				});
			} else {
				if (startVertex != 0)
					throw new GLException("Cannot use 'drawIndexed' with startVertex != 0 when glDrawElementsBaseVertex is not supported");
				buildingCommands.add(() -> {
					GLPipelineGeometryState state = backend.getCurrentPipeline().pipelineState.geometryState;
					ZEGeometryType geoType = state.getInputGeometryType();
					ZEPrimitiveType indexType = state.getIndexType();
					GL11.glDrawElements(
						GLValues.getGLInputGeometryType(geoType),
						nIndices,
						GLValues.getGLType(indexType),
						startIndex
					);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to draw indexed");
				});
			}
		}

		@Override
		public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			/** Complete */
			if (backend.getCapabilities().glDrawArraysIndirect != 0)
				buildingCommands.add(() -> {
					GL40.glDrawArraysIndirect(0, (ByteBuffer)null);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to draw indirect");
				});
			else throw new GLException("Cannot use 'drawIndirect' when glDrawArraysIndirect is not supported");
		}

		@Override
		public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			/** Complete */
			if (backend.getCapabilities().glDrawElementsIndirect != 0)
				buildingCommands.add(() -> {
					GL40.glDrawElementsIndirect(0, 0, (ByteBuffer)null);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to draw indexed indirect");
				});
			else throw new GLException("Cannot use 'drawIndexedIndirect' when glDrawElementsIndirect is not supported");
		}

		@Override
		public void setEvent(ZEPipelineStage stage, ZERenderEvent event) {
			/** Complete */
			buildingCommands.add(() -> event.set());
		}

		@Override
		public void resetEvent(ZEPipelineStage stage, ZERenderEvent event) {
			/** Complete */
			buildingCommands.add(() -> event.reset());
		}

		@Override
		public void waitForEvents(ZEPipelineStage[] signalStages, ZEPipelineStage[] waitingStages,
				ZERenderEvent... events) {
			/** Complete */
			ZERenderEvent[] events2 = Arrays.copyOf(events, events.length);
			buildingCommands.add(() -> {
				for(ZERenderEvent e : events2) {
					while(!e.isSet()) {
						Thread.yield(); // *Can* help with thread scheduling.
						try {
							Thread.sleep(10);
						} catch (InterruptedException x) {}
					}
				}
			});
		}

		@Override
		public void blitBuffer(ZEBuffer src, ZEBuffer dst, long srcPos, long dstPos, long size) {
			/** Complete */
			int glsrc = ((GLBuffer)src).bufferObject;
			int gldst = ((GLBuffer)dst).bufferObject;
			GLExtensions exts = backend.getExtensions();
			if (exts.copyNamedBufferSubData) {
				// Use glCopyNamedBufferSubData if available
				buildingCommands.add(() -> {
					GL45.glCopyNamedBufferSubData(glsrc, gldst, srcPos, dstPos, size);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to blit buffer");
				});
			} else if (exts.copyBufferSubData) {
				// Use glCopyBufferSubData if available
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL31.GL_COPY_READ_BUFFER, glsrc);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL31.GL_COPY_WRITE_BUFFER, gldst);
					backend.checkErrorFine();
					GL31.glCopyBufferSubData(GL31.GL_COPY_READ_BUFFER, GL31.GL_COPY_WRITE_BUFFER, srcPos, dstPos, size);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to blit buffer");
				});
			/* If we don't have glCopyBufferSubData we don't have glMapNamedBuffer
			} else if (exts.mapNamedBuffer) {
				// Else fall back to GL20 functions
				buildingCommands.add(() -> {
					ByteBuffer srcBuf = GL45.glMapNamedBuffer(glsrc, GL15.GL_READ_ONLY);
					ByteBuffer dstBuf = GL45.glMapNamedBuffer(gldst, GL15.GL_WRITE_ONLY);
					LibCString.nmemcpy(MemoryUtil.memAddress(srcBuf) + srcPos, MemoryUtil.memAddress(dstBuf) + dstPos, size);
					GL45.glUnmapNamedBuffer(glsrc);
					GL45.glUnmapNamedBuffer(gldst);
				});
			*/
			} else {
				buildingCommands.add(() -> {
					int bindingArray = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
					backend.checkErrorFine();
					int bindingElement = GL11.glGetInteger(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glsrc);
					backend.checkErrorFine();
					ByteBuffer srcBuf = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_READ_ONLY);
					backend.checkErrorFine();
					// GL_ELEMENT_ARRAY_BUFFER is just convenient to avoid rebinding GL_ARRAY_BUFFER
					GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gldst);
					backend.checkErrorFine();
					ByteBuffer dstBuf = GL15.glMapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					backend.checkErrorFine();
					LibCString.nmemcpy(MemoryUtil.memAddress(srcBuf) + srcPos, MemoryUtil.memAddress(dstBuf) + dstPos, size);
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
					backend.checkErrorFine();
					GL15.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bindingArray);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bindingElement);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to blit buffer");
				});
			}
		}

		@Override
		public void blitTexture(ZETexture srcTex, ZETextureLayer srcLayer, ZETextureLayout srcLayout, Vector3I srcPos, Vector3I srcSize,
				ZETexture dstTex, ZETextureLayer dstLayer, ZETextureLayout dstLayout, Vector3I dstPos, Vector3I dstSize,
				ZEFilter filter) {
			GLTexture glsrcobj = ((GLTexture)srcTex);
			GLTexture gldstobj = ((GLTexture)dstTex);
			int glsrc = glsrcobj.textureObject;
			int gldst = gldstobj.textureObject;
			int srcm = srcLayer.mipLevel, dstm = dstLayer.mipLevel;
			int srca = srcLayer.baseArrayLayer, dsta = dstLayer.baseArrayLayer;
			int layerCount = Math.min(srcLayer.arrayLayerCount, dstLayer.arrayLayerCount);
			
			Vector3I _srcPos = new Vector3I(srcPos), _dstPos = new Vector3I(dstPos);
			Vector3I _srcSize = new Vector3I(srcSize), _dstSize = new Vector3I(dstSize);
			limitCoordsForDimension(_srcPos, _srcSize, glsrcobj, srcLayer.baseArrayLayer, srcLayer.arrayLayerCount);
			limitCoordsForDimension(_dstPos, _dstSize, gldstobj, dstLayer.baseArrayLayer, dstLayer.arrayLayerCount);
			
			GLExtensions exts = backend.getExtensions();
			
			if (srcSize.equals(dstSize)) { // Simple blit, no scaling
				if (exts.copyImageSubData) {
					// Use glCopyImageSubData if available
					buildingCommands.add(() -> {
						int target = GLValues.getGLTextureBindTarget(srcTex.getDimension());
						exts.glCopyImageSubData(
								glsrc, target, srcm, _srcPos.x, _srcPos.y, _srcPos.z,
								gldst, target, dstm, _dstPos.x, _dstPos.y, _dstPos.z,
								_srcSize.x, _srcSize.y, _srcSize.z);
					});
				} else if (exts.framebufferBlit) {
					// We could use framebuffers
					buildingCommands.add(() -> {
						int fbo = exts.glGenFramebuffers();
						int currentFBO = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);
						exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
						for(int ao = 0; ao < layerCount; ao++) {
							glFramebufferTexture(GL30.GL_READ_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, glsrcobj, srcm, srca + ao, _srcPos.z + ao);
							glFramebufferTexture(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, gldstobj, dstm, dsta + ao, _dstPos.z + ao);
							GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT1);
							exts.glBlitFramebuffer(
									_srcPos.x, _srcPos.y, _srcPos.x+_srcSize.x, _srcPos.y+_srcSize.y,
									_dstPos.x, _dstPos.y, _dstPos.x+_dstSize.x, _dstPos.y+_dstSize.y,
									GL11.GL_COLOR_BUFFER_BIT,
									GL11.GL_NEAREST);
						}
						exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, currentFBO);
						exts.glDeleteFramebuffers(fbo);
					});
				} else {
					// We fall back to using pixel buffer objects
					buildingCommands.add(() -> {
						int pbo = GL15.glGenBuffers();
						// TODO: Pixel buffer blit
					});
				}
			} else { // Scaled blit
				if (exts.framebufferBlit && srcTex.getDimension().numSampleDimensions > 1) {
					// Use framebuffer objects to transfer the texture
					int glfilter = GLValues.getGLMagFilter(filter);
					
					buildingCommands.add(() -> {
						int fbo = exts.glGenFramebuffers();
						int currentFBO = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);
						exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
						for(int ao = 0; ao < layerCount; ao++) {
							glFramebufferTexture(GL30.GL_READ_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, glsrcobj, srcm, srca + ao, _srcPos.z + ao);
							glFramebufferTexture(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT1, gldstobj, dstm, dsta + ao, _dstPos.z + ao);
							GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT1);
							exts.glBlitFramebuffer(
									_srcPos.x, _srcPos.y, _srcPos.x+_srcSize.x, _srcPos.y+_srcSize.y,
									_dstPos.x, _dstPos.y, _dstPos.x+_dstSize.x, _dstPos.y+_dstSize.y,
									GL11.GL_COLOR_BUFFER_BIT,
									glfilter);
						}
						exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, currentFBO);
						exts.glDeleteFramebuffers(fbo);
					});
					// Else, give up and throw an exception
				} else throw new GLException("Blitting operation not supported");
			}
		}
		
		private int getClearFormatType(int nElems) {
			switch(nElems) {
			case 1: return GL30.GL_R8;
			case 2: return GL30.GL_RG8;
			case 4: return GL11.GL_RGBA8;
			default: return -1;
			}
		}

		@Override
		public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value) {
			/** Complete */
			int glbuf = ((GLBuffer)buf).bufferObject;
			GLExtensions exts = backend.getExtensions();
			
			int valueSize = value.remaining();
			boolean isPixelSize = valueSize == 1 || valueSize == 2 || valueSize == 4;
			boolean isPixelOffset = offset % valueSize == 0;

			ByteBuffer value2 = BufferUtils.createByteBuffer(value.remaining());
			int pos = value.position();
			value2.put(value);
			value.position(pos);
			value2.rewind();
			
			if (isPixelSize && isPixelOffset && exts.clearNamedBufferSubData) {
				int clearFormat = getClearFormatType(valueSize);
				// Try to use glClearNamedBufferSubData
				buildingCommands.add(() -> {
					exts.glClearNamedBufferSubData(glbuf, clearFormat, offset, count * value.remaining(), clearFormat, GL11.GL_UNSIGNED_BYTE, value2);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to clear buffer");
				});
			} else if (isPixelSize && isPixelOffset && exts.clearBufferSubData) {
				int clearFormat = getClearFormatType(valueSize);
				// Slightly less efficient using a binding location, but still much better than the fallback
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL31.GL_COPY_WRITE_BUFFER, glbuf);
					backend.checkErrorFine();
					exts.glClearBufferSubData(GL31.GL_COPY_WRITE_BUFFER, clearFormat, offset, count * value.remaining(), clearFormat, GL11.GL_UNSIGNED_BYTE, value2);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to clear buffer");
				});
			} else if (exts.mapNamedBuffer) {
				// Fall back to buffer mapping and CPU-side modification
				buildingCommands.add(() -> {
					ByteBuffer mbuf = GL45.glMapNamedBuffer(glbuf, GL15.GL_WRITE_ONLY);
					backend.checkErrorFine();
					mbuf.position(offset * valueSize);
					for(int i = 0; i < count; i++) mbuf.put(value2).rewind();
					GL45.glUnmapNamedBuffer(glbuf);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to clear buffer");
				});
			} else {
				buildingCommands.add(() -> {
					int bindingArray = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glbuf);
					backend.checkErrorFine();
					ByteBuffer mbuf = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					backend.checkErrorFine();
					mbuf.position(offset * valueSize);
					for(int i = 0; i < count; i++) mbuf.put(value2).rewind();
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bindingArray);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to clear buffer");
				});
			}
		}
		
		@Override
		public void clearTexture(ZETexture tex, Vector3I start, Vector3I size, ZETextureRange range, ZEColorClearValue color) {
			/** Complete */
			GLTexture gltexobj = (GLTexture)tex;
			int gltex = gltexobj.textureObject;
			int target = GLValues.getGLTextureBindTarget(tex.getDimension());
			int mipBase = range.baseMipLevel;
			int mipCount = range.mipLevelCount;
			int arrayBase = range.baseArrayLayer;
			int arrayCount = range.arrayLayerCount;
			
			Vector3I _start = new Vector3I(start);
			Vector3I _size = new Vector3I(size);
			limitCoordsForDimension(_start, _size, gltexobj, range.baseArrayLayer, range.arrayLayerCount);
			
			GLExtensions exts = backend.getExtensions();
			if (exts.clearTexSubImage) {
				// Try to use glClearTexSubImage
				ByteBuffer pColor = BufferUtils.createByteBuffer(tex.getPixelFormat().sizeOf);
				color.write(tex.getPixelFormat(), pColor);
				
				buildingCommands.add(() -> {
					for(int i = mipCount; i < mipCount; i++) {
						gltexobj.glClearTexSubImage(
								mipBase + i,
								_start.x, _start.y, _start.z,
								_size.x, _size.y, _size.z,
								arrayBase, arrayCount,
								pColor);
						backend.checkErrorFine();
						backend.checkErrorCoarse("Failed to clear texture");
					}
				});
			} else if (exts.clearBuffer && tex.getDimension().numDimensions > 1) {
				// Try to clear with framebuffers
				ByteBuffer pClearColor = BufferUtils.createByteBuffer(16);
				pClearColor.putInt(color.getIntR());
				pClearColor.putInt(color.getIntG());
				pClearColor.putInt(color.getIntB());
				pClearColor.putInt(color.getIntA());
				pClearColor.rewind();
				ZEPixelFormat pxfmt = tex.getPixelFormat();

				if (_size.z == 0) _size.z = 1;
				
				buildingCommands.add(() -> {
					int boundFBO = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);
					backend.checkErrorFine();
					int fbo = exts.glGenFramebuffers();
					backend.checkErrorFine();
					exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, fbo);
					backend.checkErrorFine();
					GL11.glDrawBuffer(GL30.GL_COLOR_ATTACHMENT0);
					backend.checkErrorFine();
					for(int m = 0; m < mipCount; m++) {
						for(int i = 0; i < _size.z; i++) {
							glFramebufferTexture(GL30.GL_DRAW_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, gltexobj, mipBase + m,
									arrayBase + i, _start.z + i);
							backend.checkErrorFine();
							if (pxfmt.elementType.isFloating) GL30.glClearBufferfv(GL11.GL_COLOR, 0, pClearColor.asFloatBuffer());
							else GL30.glClearBufferiv(GL11.GL_COLOR, 0, pClearColor.asIntBuffer());
							backend.checkErrorFine();
						}
					}
					exts.glBindFramebuffer(GL30.GL_FRAMEBUFFER, boundFBO);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to clear texture");
				});
			} else {
				// Fall back to using glTexSubImage
				ByteBuffer pixel = BufferUtils.createByteBuffer(tex.getPixelFormat().sizeOf);
				color.write(tex.getPixelFormat(), pixel);
				
				
				Vector3I fillTexSize = new Vector3I(Math.min(_size.x, 16), Math.min(_size.y, 16), Math.min(_size.z, 16));
				if (fillTexSize.y == 0) fillTexSize.y = 1;
				if (fillTexSize.z == 0) fillTexSize.z = 1;
				ByteBuffer fillTex = BufferUtils.createByteBuffer(tex.getPixelFormat().sizeOf *
						fillTexSize.x * fillTexSize.y * fillTexSize.z);
				while(fillTex.hasRemaining()) {
					fillTex.put(pixel);
					pixel.rewind();
				}
				fillTex.rewind();
				
				if (_size.y == 0) _size.y = 1;
				if (_size.z == 0) _size.z = 1;
				
				buildingCommands.add(() -> {
					long data = MemoryUtil.memAddress(fillTex);
					GL11.glBindTexture(target, gltex);
					backend.checkErrorFine();
					for(int m = mipBase; m < mipCount; m++) {
						for(int zo = 0; zo < _size.z; zo += fillTexSize.z) {
							for(int yo = 0; yo < _size.y; yo += fillTexSize.y) {
								for(int xo = 0; xo < _size.x; xo += fillTexSize.x) {
									gltexobj.glTexSubImage(target, m,
											_start.x + xo, _start.y + yo, _start.z + zo,
											Math.min(_size.x - xo, fillTexSize.x),
											Math.min(_size.y - yo, fillTexSize.y),
											Math.min(_size.z - zo, fillTexSize.z),
											arrayBase, arrayCount, data);
								}
							}
						}
					}
					backend.checkErrorCoarse("Failed to clear texture");
				});
			}
		}

		@Override
		public void imageToBuffer(ZETexture src, Vector3I srcPos, Vector3I srcSize, ZETextureLayer srcLayer,
				ZEBuffer dst, int dstOffset, int dstRowLength, int dstHeight) {
			ZEPixelFormat pxfmt = src.getPixelFormat();
			ZETextureDimension dim = src.getDimension();
			int gltex = ((GLTexture)src).textureObject;
			int glbuf = ((GLBuffer)dst).bufferObject;
			int target = GLValues.getGLTextureBindTarget(dim);
			final int format = GLValues.getGLTextureFormat(pxfmt);
			final int type = GLValues.getGLTextureType(pxfmt);
			
			int srcm = srcLayer.mipLevel;
			int srcx = srcPos.x, srcy = srcPos.y, srcz = srcPos.z;
			int srcw = srcSize.x, srch = srcSize.y, srcd = srcSize.z;
			int srca = srcLayer.baseArrayLayer, srcac = srcLayer.arrayLayerCount;
			
			if (srcx == 0 && srcy == 0 && srcz == 0 && srcw == src.getWidth() && srch == src.getHeight() && srcd == src.getDepth()
					&& srca == 0 && srcac == src.getArrayLayers()) {
				// If it is the full texture, use pixel buffers
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, glbuf);
					GL11.glPixelStorei(GL11.GL_PACK_ROW_LENGTH, dstRowLength);
					GL11.glPixelStorei(GL12.GL_PACK_IMAGE_HEIGHT, dstHeight);
					GL11.glBindTexture(target, gltex);
					GL11.glGetTexImage(target, srcm, format, type, dstOffset);
					GL11.glPixelStorei(GL11.GL_PACK_ROW_LENGTH, 0);
					GL11.glPixelStorei(GL12.GL_PACK_IMAGE_HEIGHT, 0);
				});
			} else if (backend.getCapabilities().glGetTextureSubImage != 0 &&
					(dim.numSampleDimensions < 2 || dstRowLength == src.getWidth()) &&
					(dim.numSampleDimensions < 3 || dstHeight == src.getHeight() * src.getWidth())) {
				buildingCommands.add(() -> {
					// Else we have to do it the hard way, because apparently having glGetTextureSubImage work with buffers is too easy
					ByteBuffer mem = GL45.glMapNamedBuffer(glbuf, GL15.GL_WRITE_ONLY);
					GL45.glGetTextureSubImage(gltex, 0, srcx, srcy, srcz, srcw, srch, srcd, format, type, mem.capacity() - dstOffset, MemoryUtil.memAddress(mem) + dstOffset);
					GL45.glUnmapNamedBuffer(glbuf);
				});
			} else { // And if all else fails its back to the hell of texture blitting
				// TODO: Manual texture blitting
			}
		}

		@Override
		public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
				Vector3I dstPos, Vector3I dstSize, ZETextureLayer dstLayer) {
			/** Complete */
			GLTexture gldst = (GLTexture)dst;
			int target = GLValues.getGLTextureBindTarget(dst.getDimension());
			int bindname = GLValues.getGLTextureBinding(dst.getDimension());
			int glbuf = ((GLBuffer)src).bufferObject;
			
			int dstm = dstLayer.mipLevel;
			int dsta = dstLayer.baseArrayLayer, dstac = dstLayer.arrayLayerCount;
			int dstx = dstPos.x, dsty = dstPos.y, dstz = dstPos.z;
			int dstw = dstSize.x, dsth = dstSize.y, dstd = dstSize.z;
			
			GLExtensions exts = backend.getExtensions();
			
			if (exts.textureSubImage) {
				buildingCommands.add(() -> {
					GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, srcRowLength);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL12.GL_UNPACK_IMAGE_HEIGHT, srcHeight);
					backend.checkErrorFine();
					gldst.glTextureSubImage(dstm, dstx, dsty, dstz, dstw, dsth, dstd, dsta, dstac, srcOffset);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL12.GL_UNPACK_IMAGE_HEIGHT, 0);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to transfer from buffer to image");
				});
			} else {			
				buildingCommands.add(() -> {
					int boundBuf = GL11.glGetInteger(GL21.GL_PIXEL_UNPACK_BUFFER_BINDING);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, glbuf);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, srcRowLength);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL12.GL_UNPACK_IMAGE_HEIGHT, srcHeight);
					backend.checkErrorFine();
					int boundTex = GL11.glGetInteger(bindname);
					backend.checkErrorFine();
					gldst.glTexSubImage(dstm, dstx, dsty, dstz, dstw, dsth, dstd, dsta, dstac, srcOffset);
					GL11.glBindTexture(target, boundTex);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
					backend.checkErrorFine();
					GL11.glPixelStorei(GL12.GL_UNPACK_IMAGE_HEIGHT, 0);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, boundBuf);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to transfer from buffer to image");
				});
			}
		}

		@Override
		public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data) {
			/** Complete */
			int glbuf = ((GLBuffer)dst).bufferObject;
			GLExtensions exts = backend.getExtensions();
			if (exts.namedBufferSubData) {
				buildingCommands.add(() -> {
					GL45.nglNamedBufferSubData(glbuf, dstoff, len, MemoryUtil.memAddress(data));
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to upload to buffer");
				});
			} else {
				buildingCommands.add(() -> {
					int boundBuf = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glbuf);
					backend.checkErrorFine();
					GL15.nglBufferSubData(GL15.GL_ARRAY_BUFFER, dstoff, len, MemoryUtil.memAddress(data));
					backend.checkErrorFine();
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, boundBuf);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to upload to buffer");
				});
			}
		}

		@Override
		public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout) {
			/** Complete */
			// No-op since OpenGL textures do not have (visible) memory layouts
		}

		@Override
		public void setViewport(ZEViewport[] viewports, int firstViewport) {
			/** Complete */
			GLExtensions exts = backend.getExtensions();
			if (exts.viewportArray && exts.depthRangeArray) {
				FloatBuffer viewBuf = BufferUtils.createFloatBuffer(viewports.length * 4);
				DoubleBuffer depthBuf = BufferUtils.createDoubleBuffer(viewports.length * 2);
				for(int i = 0; i < viewports.length; i++) {
					ZEViewport vp = viewports[firstViewport + i];
					viewBuf.put((float)vp.area.position.x);
					viewBuf.put((float)vp.area.position.y);
					viewBuf.put((float)vp.area.extent.x);
					viewBuf.put((float)vp.area.extent.y);
					depthBuf.put(vp.minDepth);
					depthBuf.put(vp.maxDepth);
				}
				viewBuf.rewind();
				depthBuf.rewind();
				buildingCommands.add(() -> {
						exts.glViewportArrayv(0, viewBuf);
						backend.checkErrorFine();
						GL41.glDepthRangeArrayv(0, depthBuf);
						backend.checkErrorFine();
						backend.checkErrorCoarse("Failed to set viewports");
				});
			} else {
				if (viewports.length == 1) {
					ZEViewport vp = viewports[firstViewport];
					Vector2I extent = new Vector2I((int)vp.area.extent.x, (int)vp.area.extent.y);
					Vector2I position = new Vector2I((int)vp.area.position.x, (int)vp.area.position.y);
					double minDepth = vp.minDepth, maxDepth = vp.maxDepth;
					buildingCommands.add(() -> {
						GL11.glViewport(position.x, position.y, extent.x, extent.y);
						backend.checkErrorFine();
						GL11.glDepthRange(minDepth, maxDepth);
						backend.checkErrorFine();
						backend.checkErrorCoarse("Failed to set viewports");
					});
				} else throw new GLException("Cannot set multiple viewports with 'glViewport' & 'glDepthRange'");
			}
		}

		@Override
		public void setDepthBias(double constantFactor, double clamp, double slopeFactor) {
			/** Complete */
			GLExtensions exts = backend.getExtensions();
			// Try glPolygonOffsetClamp
			if (exts.polygonOffsetClamp) {
				buildingCommands.add(() -> {
					exts.glPolygonOffsetClamp((float)constantFactor, (float)slopeFactor, (float)clamp);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set depth bias");
				});
			} else { // Clamping may not be possible
				buildingCommands.add(() -> {
					GL11.glPolygonOffset((float)constantFactor, (float)slopeFactor);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to set depth bias");
				});
			}
		}

		@Override
		public void setBlendConstants(float... constants) {
			/** Complete */
			float r = constants[0], g = constants[1], b = constants[2], a = constants[3];
			buildingCommands.add(() -> {
				GL14.glBlendColor(r,g,b,a);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set blend constants");
			});
		}

		@Override
		public void setStencilCompareMask(ZEFrontBack face, int compareMask) {
			/** Complete */
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				int func = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
				backend.checkErrorFine();
				int ref = GL11.glGetInteger(GL11.GL_STENCIL_REF);
				backend.checkErrorFine();
				GL20.glStencilFuncSeparate(glface, func, ref, compareMask);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set stencil compare mask");
			});
		}

		@Override
		public void setStencilWriteMask(ZEFrontBack face, int writeMask) {
			/** Complete */
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				GL20.glStencilMaskSeparate(glface, writeMask);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set stencil write mask");
			});
		}

		@Override
		public void setStencilReference(ZEFrontBack face, int reference) {
			/** Complete */
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				int func = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
				backend.checkErrorFine();
				int mask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK);
				backend.checkErrorFine();
				GL20.glStencilFuncSeparate(glface, func, reference, mask);
				backend.checkErrorFine();
				backend.checkErrorCoarse("Failed to set stencil reference");
			});
		}

		@Override
		public void bindPipelineBindSet(ZEPipelineBindSet bindSet) {
			// TODO: Bind uniforms
		}

		@Override
		public void waitForEvents(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages,
				ZERenderEvent[] events, ZEPipelineBarrier... barriers) {
			/** Complete */
			CountDownLatch cdl = new CountDownLatch(events.length);
			for(ZERenderEvent e : events) {
				GLRenderEvent gle = (GLRenderEvent)e;
				synchronized(gle.latches) {
					gle.latches.add(cdl);
				}
			}
			try {
				cdl.await();
			} catch (InterruptedException e1) { }
		}

		@Override
		public void pipelineBarrier(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages, ZEPipelineBarrier... barriers) {
			/** Complete */
			// No-op since barriers are not necessary for OpenGL
		}

		@Override
		public void generateMipmaps(ZETexture tex) {
			/** Complete */
			int gltex = ((GLTexture)tex).textureObject;
			int target = GLValues.getGLTextureBindTarget(tex.getDimension());
			int targetBinding = GLValues.getGLTextureBinding(tex.getDimension());
			GLExtensions exts = backend.getExtensions();
			
			if (exts.generateTextureMipmap) {
				buildingCommands.add(() -> {
					exts.glGenerateTextureMipmap(gltex);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to generate texture mipmaps");
				});
			} else if (exts.generateMipmap) {
				buildingCommands.add(() -> {
					int boundTex = GL11.glGetInteger(targetBinding);
					backend.checkErrorFine();
					GL11.glBindTexture(target, gltex);
					backend.checkErrorFine();
					if (GLEnvironment.quirkGLTextureBadATIMipmaps) {
						GL11.glEnable(target);
						backend.checkErrorFine();
					}
					exts.glGenerateMipmap(target);
					backend.checkErrorFine();
					GL11.glBindTexture(target, boundTex);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to generate texture mipmaps");
				});
			} else {
				buildingCommands.add(() -> {
					int boundTex = GL11.glGetInteger(targetBinding);
					backend.checkErrorFine();
					GL11.glBindTexture(target, gltex);
					backend.checkErrorFine();
					int hint = GL11.glGetTexParameteri(target, GL14.GL_GENERATE_MIPMAP);
					backend.checkErrorFine();
					if (hint != GL11.GL_TRUE) {
						GL11.glTexParameteri(target, GL14.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
						backend.checkErrorFine();
					}
					GL11.glBindTexture(target, boundTex);
					backend.checkErrorFine();
					backend.checkErrorCoarse("Failed to generate texture mipmaps");
				});
			}
		}
		
	}
	
	private GLInterpreterRecorder recorder = null;
	
	@Override
	public ZERenderWorkRecorder beginRecording() {
		if (commands != null && !isRewritable) return null;
		if (recorder == null) recorder = new GLInterpreterRecorder();
		return recorder;
	}

	@Override
	public ZERenderWorkRecorder beginRecording(ZERenderCommandBuffer parent) {
		return beginRecording();
	}

	@Override
	public void endRecording() {
		if (recorder != null) {
			if (commands == null) commands = new ArrayList<>();
			synchronized(commands) {
				commands.clear();
				commands.addAll(recorder.buildingCommands);
			}
			recorder = null;
		}
	}

	@Override
	public void executeCommands() {
		if (commands != null) {
			synchronized(commands) {
				for(Runnable r : commands) r.run();
			}
		}
	}

}
