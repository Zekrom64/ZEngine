package com.zekrom_64.ze.gl.objects;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.EXTPolygonOffsetClamp;
import org.lwjgl.opengl.GL11;
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
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector2D;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEColorClearValue;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder.ZEAttachmentLoadOp;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder.ZEAttachmentUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureRange;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.GLValues;
import com.zekrom_64.ze.gl.impl.GLPipeline;
import com.zekrom_64.ze.gl.impl.GLPipelineState.GLPipelineGeometryState;
import com.zekrom_64.ze.gl.impl.GLVertexInput;

public class GLCommandBufferInterpreted extends GLCommandBuffer {
	
	public GLCommandBufferInterpreted(GLRenderBackend backend, boolean rewritable) {
		super(backend, rewritable);
	}
	
	private List<Runnable> commands = null;

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
	
	private class GLInterpreterRecorder implements ZERenderWorkRecorder {

		private List<Runnable> buildingCommands = new ArrayList<>();
		
		@Override
		public void executeBuffer(ZERenderCommandBuffer buffer) {
			buildingCommands.add(((GLCommandBuffer)buffer)::executeCommands);
		}

		@Override
		public void bindPipeline(ZEPipeline pipeline) {
			buildingCommands.add(() -> {
				backend.setCurrentPipeline((GLPipeline)pipeline);
			});
		}

		private GLRenderPass renderPass = null;
		private int subpass = -1;
		
		@Override
		public void beginPass(ZERenderPass renderPass, ZEFramebuffer framebuffer,
				ZEAttachmentClearValue[] clearValues) {
			GLRenderPass rp = (GLRenderPass)renderPass;
			if (((GLFramebuffer)framebuffer).framebuffer == 0) { // Default framebuffer
				buildingCommands.add(() -> {
					GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((GLFramebuffer)framebuffer).framebuffer);
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
			} else { // Framebuffer with attachments
				buildingCommands.add(() -> {
					GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, ((GLFramebuffer)framebuffer).framebuffer);
				});
				ZETexture[] attachments = ((GLFramebuffer)framebuffer).attachments;
				for(int i = 0; i < attachments.length; i++) {
					GLTexture tex = (GLTexture)attachments[i];
					boolean isDepth = tex.getPixelFormat().depthType != null;
					boolean isStencil = tex.getPixelFormat().stencilType != null;
					final float depth = clearValues[i].depth;
					final int stencil = clearValues[i].stencil;
					if (isDepth || isStencil) { // Depth and stencil formats are kind of weird
						if (isDepth && !isStencil) {
							buildingCommands.add(() -> {
								GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.texID, 0);
								GL11.glClearDepth(depth);
								GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
							});
						} else if (isStencil && !isDepth) {
							buildingCommands.add(() -> {
								GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.texID, 0);
								GL11.glClearStencil(stencil);
								GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
							});
						} else {
							buildingCommands.add(() -> {
								GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, tex.texID, 0);
								GL11.glClearDepth(depth);
								GL11.glClearStencil(stencil);
								GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
							});
						}
					} else { // Clear the color attachment
						buildingCommands.add(() -> {
							GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, tex.texID, 0);
						});
						buildingCommands.add(clearColorToCommand(tex, clearValues[i].color));
					}
				}
			}
			renderPass = rp;
			subpass = -1;
			nextPass();
		}

		@Override
		public void nextPass() {
			subpass++;
		}

		@Override
		public void endPass() { }

		@Override
		public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors) {
			if (backend.getCapabilities().glScissorIndexed != 0) {
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
					}
				});
			} else if (firstScissor == 0 && numScissors == 1) {
				Rectangle scissor = scissors[0];
				int posX = (int)scissor.getPositionX(), posY = (int)scissor.getPositionY();
				int w = (int)scissor.getWidth(), h = (int)scissor.getHeight();
				buildingCommands.add(() -> GL11.glScissor(posX, posY, w, h));
			} else throw new GLException("Cannot set scissors for multiple viewports without glScissorIndexed");
		}

		@Override
		public void setLineWidth(float width) {
			buildingCommands.add(() -> GL11.glLineWidth(width));
		}

		@Override
		public void setDepthBounds(double min, double max) {
			buildingCommands.add(() -> GL11.glDepthRange(min, max));
		}

		@Override
		public void bindVertexBuffer(ZEVertexInput bindPoint, ZEVertexBuffer buffer) {
			if (backend.getCapabilities().glBindVertexBuffer != 0) {
				buildingCommands.add(() -> {
					GL43.glBindVertexBuffer(((GLVertexInput)bindPoint).bindingIndex, ((GLVertexBuffer)buffer).bufferObject, 0, 0);
				});
			} else {
				if (((GLVertexInput)bindPoint).bindingIndex != 0)
					throw new GLException("Cannot bind vertex buffer to bind point >0 with glBindBuffer");
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ((GLVertexBuffer)buffer).bufferObject);
				});
			}
		}

		@Override
		public void bindVertexBuffers(ZEVertexInput firstBindPoint, ZEVertexBuffer... buffers) {
			if (backend.getCapabilities().glBindVertexBuffers != 0) {
				IntBuffer pBuffers = BufferUtils.createIntBuffer(buffers.length);
				for(ZEVertexBuffer buf : buffers) pBuffers.put(((GLVertexBuffer)buf).bufferObject);
				buildingCommands.add(() -> {
					try(MemoryStack sp = MemoryStack.stackPush()) {
						PointerBuffer pOffsets = sp.callocPointer(pBuffers.capacity());
						IntBuffer pStrides = sp.callocInt(pBuffers.capacity());
						GL44.glBindVertexBuffers(((GLVertexInput)firstBindPoint).bindingIndex, pBuffers, pOffsets, pStrides);
					}
				});
			} else if (backend.getCapabilities().glBindVertexBuffer != 0) {
				int[] bufs = new int[buffers.length];
				for(int i = 0; i < buffers.length; i++) bufs[i] = ((GLVertexBuffer)buffers[i]).bufferObject;
				int baseIndex = ((GLVertexInput)firstBindPoint).bindingIndex;
				buildingCommands.add(() -> {
					for(int i = 0; i < bufs.length; i++) GL43.glBindVertexBuffer(i + baseIndex, bufs[i], 0, 0);
				});
			} else {
				int bindIndex = ((GLVertexInput)firstBindPoint).bindingIndex;
				if (bindIndex > 0) throw new GLException("Cannot bind vertex buffer to bind point >0 with glBindBuffer");
				GLVertexBuffer buf = (GLVertexBuffer)buffers[0];
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, buf.bufferObject);
				});
			}
		}

		@Override
		public void bindIndexBuffer(ZEIndexBuffer buffer) {
			buildingCommands.add(() -> {
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ((GLIndexBuffer)buffer).parent.bufferObject);
				backend.getCurrentPipeline().pipelineState.geometryState.setIndexType(buffer.getIndexType());
			});
		}

		@Override
		public void draw(int nVertices, int start) {
			buildingCommands.add(() -> {
				ZEGeometryType geoType = backend.getCurrentPipeline().pipelineState.geometryState.getInputGeometryType();
				GL11.glDrawArrays(GLValues.getGLInputGeometryType(geoType), start, nVertices);
			});
		}

		@Override
		public void drawIndexed(int nIndices, int startIndex, int startVertex) {
			if (backend.getCapabilities().glDrawElementsBaseVertex != 0) {
				buildingCommands.add(() -> {
					GLPipelineGeometryState state = backend.getCurrentPipeline().pipelineState.geometryState;
					ZEGeometryType geoType = state.getInputGeometryType();
					PrimitiveType indexType = state.getIndexType();
					GL32.glDrawElementsBaseVertex(
							GLValues.getGLInputGeometryType(geoType),
							nIndices,
							GLValues.getGLType(indexType),
							startIndex,
							startVertex
						);
				});
			} else {
				if (startVertex != 0)
					throw new GLException("Cannot use 'drawIndexed' with startVertex != 0 when glDrawElementsBaseVertex is not supported");
				buildingCommands.add(() -> {
					GLPipelineGeometryState state = backend.getCurrentPipeline().pipelineState.geometryState;
					ZEGeometryType geoType = state.getInputGeometryType();
					PrimitiveType indexType = state.getIndexType();
					GL11.glDrawElements(
							GLValues.getGLInputGeometryType(geoType),
							nIndices,
							GLValues.getGLType(indexType),
							startIndex
						);
				});
			}
		}

		@Override
		public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			if (backend.getCapabilities().glDrawArraysIndirect != 0)
				buildingCommands.add(() -> GL40.glDrawArraysIndirect(0, (ByteBuffer)null));
			else throw new GLException("Cannot use 'drawIndirect' when glDrawArraysIndirect is not supported");
		}

		@Override
		public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			if (backend.getCapabilities().glDrawElementsIndirect != 0)
				buildingCommands.add(() -> GL40.glDrawElementsIndirect(0, 0, (ByteBuffer)null));
			else throw new GLException("Cannot use 'drawIndexedIndirect' when glDrawElementsIndirect is not supported");
		}

		@Override
		public void setEvent(ZEPipelineStage stage, ZERenderEvent event) {
			buildingCommands.add(() -> event.set());
		}

		@Override
		public void resetEvent(ZEPipelineStage stage, ZERenderEvent event) {
			buildingCommands.add(() -> event.reset());
		}

		@Override
		public void waitForEvents(ZEPipelineStage[] signalStages, ZEPipelineStage[] waitingStages,
				ZERenderEvent... events) {
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
		public void blitBuffer(ZEBuffer src, ZEBuffer dst, int srcPos, int dstPos, int size) {
			int glsrc = ((GLBuffer)src).bufferObject;
			int gldst = ((GLBuffer)dst).bufferObject;
			if (backend.getCapabilities().glCopyBufferSubData != 0) {
				// Use glCopyBufferSubData if available
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL31.GL_COPY_READ_BUFFER, glsrc);
					GL15.glBindBuffer(GL31.GL_COPY_WRITE_BUFFER, gldst);
					GL31.glCopyBufferSubData(GL31.GL_COPY_READ_BUFFER, GL31.GL_COPY_WRITE_BUFFER, srcPos, dstPos, size);
				});
			} else {
				// Else fall back to GL20 functions
				buildingCommands.add(() -> {
					int bindingArray = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
					int bindingElement = GL11.glGetInteger(GL15.GL_ELEMENT_ARRAY_BUFFER_BINDING);
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glsrc);
					ByteBuffer srcBuf = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_READ_ONLY);
					// GL_ELEMENT_ARRAY_BUFFER is just convenient to avoid rebinding GL_ARRAY_BUFFER
					GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gldst);
					ByteBuffer dstBuf = GL15.glMapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					srcBuf.position(srcPos);
					dstBuf.position(dstPos);
					int truesize = Math.min(size, Math.min(srcBuf.remaining(), dstBuf.remaining()));
					MemoryUtil.memCopy(MemoryUtil.memAddress(srcBuf), MemoryUtil.memAddress(dstBuf), truesize);
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
					GL15.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bindingArray);
					GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bindingElement);
				});
			}
		}

		@Override
		public void blitTexture(ZETexture srcTex, ZETexture dstTex, Vector3I src, Vector3I dst, Vector3I size) {
			int glsrc = ((GLTexture)srcTex).texID;
			int gldst = ((GLTexture)dstTex).texID;
			if (backend.getCapabilities().glCopyImageSubData != 0) {
				// Use glCopyImageSubData if available
				int srcx = src.x, srcy = src.y, srcz = src.z, dstx = dst.x, dsty = dst.y, dstz = dst.z;
				int sizex = size.x, sizey = size.y, sizez = size.z;
				buildingCommands.add(() -> {
					int target = GLValues.getGLTextureTarget(srcTex.getDimension());
					GL43.glCopyImageSubData(glsrc, target, 0, srcx, srcy, srcz, gldst, target, 0, dstx, dsty, dstz, sizex, sizey, sizez);
				});
			} else { // TODO: Fallback texture blit method
				if (src.x == srcTex.getWidth())
				buildingCommands.add(() -> {
					
				});
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
			int glbuf = ((GLBuffer)buf).bufferObject;
			GLCapabilities caps = backend.getCapabilities();
			
			int valueSize = value.remaining();
			boolean isPixelSize = valueSize == 1 || valueSize == 2 || valueSize == 4;
			boolean isPixelOffset = offset % valueSize == 0;

			ByteBuffer value2 = BufferUtils.createByteBuffer(value.remaining());
			int pos = value.position();
			value2.put(value);
			value.position(pos);
			value2.rewind();
			
			if (isPixelSize && isPixelOffset && caps.glClearNamedBufferData != 0) {
				int clearFormat = getClearFormatType(valueSize);
				// Try to use glClearNamedBufferSubData
				buildingCommands.add(() -> {
					GL45.glClearNamedBufferSubData(glbuf, clearFormat, offset, count * value.remaining(), clearFormat, GL11.GL_UNSIGNED_BYTE, value2);
				});
			} else if (isPixelSize && isPixelOffset && caps.glClearBufferData != 0) {
				int clearFormat = getClearFormatType(valueSize);
				// Slightly less efficient using a binding location, but still much better than the fallback
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL31.GL_COPY_WRITE_BUFFER, glbuf);
					GL43.glClearBufferSubData(GL31.GL_COPY_WRITE_BUFFER, clearFormat, offset, count * value.remaining(), clearFormat, GL11.GL_UNSIGNED_BYTE, value2);
				});
			} else {
				// Fall back to buffer mapping and CPU-side modification
				buildingCommands.add(() -> {
					int bindingArray = GL11.glGetInteger(GL15.GL_ARRAY_BUFFER_BINDING);
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glbuf);
					ByteBuffer mbuf = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					mbuf.position(offset * valueSize);
					for(int i = 0; i < count; i++) mbuf.put(value2).rewind();
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bindingArray);
				});
			}
		}
		
		// TODO Implement for multi-dimension textures
		@Override
		public void clearTexture(ZETexture tex, Vector3I start, Vector3I size, ZETextureRange range, ZEColorClearValue color) {
			int gltex = ((GLTexture)tex).texID;
			int target = GLValues.getGLTextureTarget(tex.getDimension());
			int glformat = GLValues.getGLTextureFormat(tex.getPixelFormat());
			int gltype = GLValues.getGLTextureType(tex.getPixelFormat());
			
			GLCapabilities caps = backend.getCapabilities();
			if (caps.glClearTexSubImage != 0) {
				// Try to use glClearTexSubImage
				ByteBuffer pColor = BufferUtils.createByteBuffer(tex.getPixelFormat().sizeOf);
				color.write(tex.getPixelFormat(), pColor);
				
				buildingCommands.add(() -> {
					try(MemoryStack sp = MemoryStack.stackPush()) {
						GL11.glBindTexture(target, gltex);
						int nLevels = Math.min(range.mipLevelCount, 1 - range.baseMipLevel); // TODO: Introspect mipmap level count
						if (tex.getDimension().isArray) {
							int nArrays = Math.min(range.arrayLayerCount, tex.getDepth() - range.baseArrayLayer);
							for(int i = range.baseMipLevel; i < nLevels; i++)
								GL44.glClearTexSubImage(target, i, start.x, start.y, range.baseArrayLayer, size.x, size.y, nArrays, glformat, gltype, pColor);
						} else {
							for(int i = range.baseMipLevel; i < nLevels; i++)
								GL44.glClearTexSubImage(target, i, start.x, start.y, start.z, size.x, size.y, size.z, glformat, gltype, pColor);
						}
					}
				});
			} else {
				// Fall back to using glTexSubImage
				ByteBuffer pixels = BufferUtils.createByteBuffer(size.x * Math.min(size.y, 1) * Math.min(size.z, 1) * tex.getPixelFormat().sizeOf);
				
				
				buildingCommands.add(() -> {
					GL11.glBindTexture(target, gltex);
					GL11.glTexSubImage2D(target, 0, start.x, start.y, size.x, size.y, glformat, gltype, pixels);
				});
			}
		}

		// TODO Implement for multi-dimension textures
		@Override
		public void imageToBuffer(ZETexture src, Vector3I srcPos, Vector3I srcSize, ZETextureRange srcRange,
				ZEBuffer dst, int dstOffset, int dstRowLength, int dstHeight) {
			ZEPixelFormat pxfmt = src.getPixelFormat();
			final int format = GLValues.getGLTextureFormat(pxfmt);
			final int type = GLValues.getGLTextureType(pxfmt);
			
			if (srcPos.x == 0 && srcPos.y == 0 && srcPos.z == 0 && srcSize.x == src.getWidth() && srcSize.y == src.getHeight()
					&& dstRowLength == srcSize.x && dstHeight == srcSize.y) {
				// If it is the full texture, use pixel buffers
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, ((GLBuffer)dst).bufferObject);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((GLTexture)src).texID);
					GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, format, type, dstOffset);
				});
			} else if (backend.getCapabilities().glGetTextureSubImage != 0) {
				int srcPosx = srcPos.x, srcPosy = srcPos.y, srcPosz = srcPos.z;
				int srcSizex = srcSize.x, srcSizey = srcSize.y, srcSizez = srcSize.z;
				buildingCommands.add(() -> {
					// Else we have to do it the hard way, because apparently having glGetTextureSubImage work with buffers is too easy
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ((GLBuffer)dst).bufferObject);
					ByteBuffer mem = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					GL45.nglGetTextureSubImage(GL11.GL_TEXTURE_2D, 0, srcPosx, srcPosy, srcPosz, srcSizex, srcSizey, srcSizez, format, type, mem.capacity() - dstOffset, MemoryUtil.memAddress(mem) + dstOffset);
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
				});
			}
		}

		// TODO Implement for multi-dimension textures
		@Override
		public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
				Vector3I dstPos, Vector3I dstSize, ZETextureRange dstRange) {
			ZEPixelFormat pxformat = dst.getPixelFormat();
			final int format = GLValues.getGLTextureFormat(pxformat);
			final int type = GLValues.getGLTextureType(pxformat);
			
			if ((srcRowLength == 0 || srcRowLength == dst.getWidth() * pxformat.sizeOf) &&
				(srcHeight == 0 || srcHeight == (dst.getWidth() * dst.getHeight() * pxformat.sizeOf))) {
				// If the stars align and all the values are right we can use glTexSubImage
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, ((GLBuffer)src).bufferObject);
					
				});
			} else {
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, ((GLBuffer)src).bufferObject);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((GLTexture)dst).texID);
					for(int i = 0; i < dstSize.y; i++) {
						GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, dstPos.x, dstPos.y + i, dstSize.x, 1, format, type, srcOffset + (srcRowLength * i));
					}
				});
			}
		}

		@Override
		public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data) {
			buildingCommands.add(() -> {
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ((GLBuffer)dst).bufferObject);
				GL15.nglBufferSubData(GL15.GL_ARRAY_BUFFER, dstoff, len, MemoryUtil.memAddress(data));
			});
		}

		@Override
		public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout) {
			// No-op since OpenGL textures do not have (visible) memory layouts
		}

		@Override
		public void setViewport(ZEViewport[] viewports, int firstViewport) {
			if (backend.getCapabilities().glViewportArrayv != 0) {
				buildingCommands.add(() -> {
					try(MemoryStack sp = MemoryStack.stackPush()) {
						FloatBuffer viewBuf = sp.mallocFloat(viewports.length * 4);
						DoubleBuffer depthBuf = sp.mallocDouble(viewports.length * 2);
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
						GL41.glViewportArrayv(0, viewBuf);
						GL41.glDepthRangeArrayv(0, depthBuf);
					}
				});
			} else {
				if (viewports.length == 1) {
					ZEViewport vp = viewports[firstViewport];
					buildingCommands.add(() -> {
						Vector2D extent = vp.area.extent;
						Vector2D position = vp.area.position;
						GL11.glViewport((int)position.x, (int)position.y, (int)extent.x, (int)extent.y);
						GL11.glDepthRange(vp.minDepth, vp.maxDepth);
					});
				} else throw new GLException("Cannot set multiple viewports with glViewport & glDepthRange");
			}
		}

		@Override
		public void setDepthBias(double constantFactor, double clamp, double slopeFactor) {
			// Depth bias in OpenGL is overcomplicated
			if (backend.getCapabilities().GL_EXT_polygon_offset_clamp) {
				buildingCommands.add(() -> {
					GL11.glPolygonOffset((float)constantFactor, (float)slopeFactor);
					EXTPolygonOffsetClamp.glPolygonOffsetClampEXT((float)constantFactor, (float)slopeFactor, (float)clamp);
				});
			} else {
				buildingCommands.add(() -> {
					GL11.glPolygonOffset((float)constantFactor, (float)slopeFactor);
				});
			}
		}

		@Override
		public void setBlendConstants(float... constants) {
			buildingCommands.add(() -> {
				GL14.glBlendColor(constants[0], constants[1], constants[2], constants[3]);
			});
		}

		@Override
		public void setStencilCompareMask(ZEFrontBack face, int compareMask) {
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				int func = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
				int ref = GL11.glGetInteger(GL11.GL_STENCIL_REF);
				GL20.glStencilFuncSeparate(glface, func, ref, compareMask);
			});
		}

		@Override
		public void setStencilWriteMask(ZEFrontBack face, int writeMask) {
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				GL20.glStencilMaskSeparate(glface, writeMask);
			});
		}

		@Override
		public void setStencilReference(ZEFrontBack face, int reference) {
			int glface = GLValues.getGLFace(face);
			buildingCommands.add(() -> {
				int func = GL11.glGetInteger(GL11.GL_STENCIL_FUNC);
				int mask = GL11.glGetInteger(GL11.GL_STENCIL_VALUE_MASK);
				GL20.glStencilFuncSeparate(glface, func, reference, mask);
			});
		}

		@Override
		public void bindPipelineBindSet(ZEPipelineBindSet bindSet) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void waitForEvents(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages,
				ZERenderEvent[] events, ZEPipelineBarrier... barriers) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pipelineBarrier(ZEPipelineStage[] readyStages, ZEPipelineStage[] waitingStages,
				ZEPipelineBarrier... barriers) {
			// TODO Auto-generated method stub
			
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
