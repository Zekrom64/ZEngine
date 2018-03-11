package com.zekrom_64.ze.gl.cmd;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
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
import com.zekrom_64.mathlib.tuple.impl.Vector3Int;
import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.ZETexture;
import com.zekrom_64.ze.base.backend.render.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.input.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.input.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline.ZEVertexInput;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;
import com.zekrom_64.ze.gl.GLException;
import com.zekrom_64.ze.gl.GLRenderBackend;
import com.zekrom_64.ze.gl.impl.GLPipeline;
import com.zekrom_64.ze.gl.impl.GLPipelineState;
import com.zekrom_64.ze.gl.objects.GLBuffer;
import com.zekrom_64.ze.gl.objects.GLIndexBuffer;
import com.zekrom_64.ze.gl.objects.GLTexture;

public class GLCommandBufferInterpreted extends GLCommandBuffer {
	
	public GLCommandBufferInterpreted(GLRenderBackend backend, boolean rewritable) {
		super(backend, rewritable);
	}
	
	private List<Runnable> commands = null;
	
	// TODO: Implement different glTex commands for different texture dimensions
	private class GLInterpreterRecorder implements ZERenderWorkRecorder {

		private List<Runnable> buildingCommands = new ArrayList<>();
		
		@Override
		public void inlineWork(Runnable r) {
			if (r != null) buildingCommands.add(r);
		}

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

		@Override
		public void beginPass(ZEPipelineBindSet bindSet, ZEFramebuffer framebuffer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void endPass() { }

		@Override
		public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors) {
			if (backend.getCapabilities().glScissorIndexed != 0) {
				buildingCommands.add(() -> {
					for(int i = 0; i < numScissors; i++) {
						Rectangle scissor = scissors[i];
						GL41.glScissorIndexed(
								i + firstScissor,
								(int)scissor.getPositionX(),
								(int)scissor.getPositionY(),
								(int)scissor.getWidth(),
								(int)scissor.getHeight());
					}
				});
			} else if (firstScissor == 0 && numScissors == 1) {
				Rectangle scissor = scissors[0];
				buildingCommands.add(() -> GL11.glScissor(
						(int)scissor.getPositionX(),
						(int)scissor.getPositionY(),
						(int)scissor.getWidth(),
						(int)scissor.getHeight()
				));
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void bindIndexBuffer(ZEIndexBuffer buffer) {
			buildingCommands.add(() -> {
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ((GLIndexBuffer)buffer).parent.bufferObject);
				backend.getCurrentPipeline().pipelineState.setIndexType(buffer.getIndexType());
			});
		}

		@Override
		public void draw(int nVertices, int start) {
			buildingCommands.add(() -> {
				ZEGeometryType geoType = backend.getCurrentPipeline().pipelineState.getInputGeometryType();
				GL11.glDrawArrays(GLPipelineState.getGLInputGeometryType(geoType), start, nVertices);
			});
		}

		@Override
		public void drawIndexed(int nIndices, int startIndex, int startVertex) {
			if (backend.getCapabilities().glDrawElementsBaseVertex != 0) {
				buildingCommands.add(() -> {
					GLPipelineState state = backend.getCurrentPipeline().pipelineState;
					ZEGeometryType geoType = state.getInputGeometryType();
					PrimitiveType indexType = state.getIndexType();
					GL32.glDrawElementsBaseVertex(
							GLPipelineState.getGLInputGeometryType(geoType),
							nIndices,
							GLPipelineState.getGLType(indexType),
							startIndex,
							startVertex
						);
				});
			} else if (startVertex == 0) {
				buildingCommands.add(() -> {
					GLPipelineState state = backend.getCurrentPipeline().pipelineState;
					ZEGeometryType geoType = state.getInputGeometryType();
					PrimitiveType indexType = state.getIndexType();
					GL11.glDrawElements(
							GLPipelineState.getGLInputGeometryType(geoType),
							nIndices,
							GLPipelineState.getGLType(indexType),
							startIndex
						);
				});
			}
		}

		@Override
		public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			if (backend.getCapabilities().glDrawArraysIndirect != 0) {
				GL40.glDrawArraysIndirect(0, (ByteBuffer)null);
			}
		}

		@Override
		public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			GL40.glDrawElementsIndirect(0, 0, (ByteBuffer)null);
		}

		@Override
		public void setEvent(ZERenderEvent event) {
			buildingCommands.add(() -> event.set());
		}

		@Override
		public void resetEvent(ZERenderEvent event) {
			buildingCommands.add(() -> event.reset());
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
					/*   Its ugly to use these buffers for generic transfers but it *should* be more efficient using mapping
					 * than always copying back to system memory then uploading to the GPU again, since the driver will determine
					 * whether they need to be copied to and back or not. */
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glsrc);
					ByteBuffer srcBuf = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_READ_ONLY);
					GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gldst);
					ByteBuffer dstBuf = GL15.glMapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
					srcBuf.position(srcPos);
					dstBuf.position(dstPos);
					int truesize = Math.min(size, Math.min(srcBuf.remaining(), dstBuf.remaining()));
					MemoryUtil.memCopy(MemoryUtil.memAddress(srcBuf), MemoryUtil.memAddress(dstBuf), truesize);
					GL15.glUnmapBuffer(GL15.GL_ARRAY_BUFFER);
					GL15.glUnmapBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER);
				});
			}
		}

		@Override
		public void blitTexture(ZETexture srcTex, ZETexture dstTex, Vector3Int src, Vector3Int dst, Vector3Int size) {
			int glsrc = ((GLTexture)srcTex).texID;
			int gldst = ((GLTexture)dstTex).texID;
			if (backend.getCapabilities().glCopyImageSubData != 0) {
				// Use glCopyImageSubData if available
				buildingCommands.add(() ->
					GL43.glCopyImageSubData(glsrc, GL11.GL_TEXTURE_2D, 0, src.x, src.y, src.z, gldst, GL11.GL_TEXTURE_2D, 0, dst.x, dst.y,
						dst.z, size.x, size.y, size.z));
			} else {
				// Else fall back to GL20 functions
				/* It is possible that it is faster to copy using framebuffers but
				 * that is too much of a hack even for me. */
				if (srcTex.getPixelFormat() == dstTex.getPixelFormat()) {
					
				}
			}
		}

		@Override
		public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value) {
			int glbuf = ((GLBuffer)buf).bufferObject;
			GLCapabilities caps = backend.getCapabilities();
			boolean isPixelSize = value.remaining() < 5;
			if (isPixelSize && caps.glClearNamedBufferData != 0) {
				// Try to use glClearNamedBufferSubData
				buildingCommands.add(() -> {
					GL45.glClearNamedBufferSubData(glbuf, GL11.GL_RED, offset, count * value.remaining(), GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, value);
				});
			} else if (isPixelSize && caps.glClearBufferData != 0) {
				// Slightly less efficient using a binding location, but still much better than the fallback
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glbuf);
					GL43.glClearBufferSubData(GL15.GL_ARRAY_BUFFER, GL11.GL_RED, offset, count * value.remaining(), GL11.GL_RED, GL11.GL_UNSIGNED_BYTE, value);
				});
			} else {
				// Fall back to glBufferSubData with zeroed stack allocated memory
				buildingCommands.add(() -> {
					try (MemoryStack sp = MemoryStack.stackPush()) {
						int width = value.remaining();
						ByteBuffer data = sp.malloc(width * count);
						long src = MemoryUtil.memAddress(value);
						long dst = MemoryUtil.memAddress(data);
						for(int i = 0; i < count; i++) MemoryUtil.memCopy(src, dst + (i * width), width);
						GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, glbuf);
						GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, offset, data);
					}
				});
			}
		}

		@Override
		public void clearTexture(ZETexture tex, Vector3Int start, Vector3Int size, ZEPixelFormat format, ByteBuffer color) {
			int gltex = ((GLTexture)tex).texID;
			GLCapabilities caps = backend.getCapabilities();
			if (caps.glClearTexSubImage != 0) {
				// Try to use glClearTexSubImage
				buildingCommands.add(() -> {
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, gltex);
					GL44.nglClearTexSubImage(GL11.GL_TEXTURE_2D, 0, start.x, start.y, start.z, size.x, size.y, size.z, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
				});
			} else {
				// Fall back to using glTexSubImage
				buildingCommands.add(() -> {
					try(MemoryStack sp = MemoryStack.stackPush()) {
						ByteBuffer mem = sp.calloc(format.sizeOf * size.x * Math.min(size.y, 1) * Math.min(size.z, 1));
						GL11.glBindTexture(GL11.GL_TEXTURE_2D, gltex);
						GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, start.x, start.y, size.x, size.y,
								GLTexture.getGLTextureFormat(format), GLTexture.getGLTextureType(format), mem);
					}
				});
			}
		}

		@Override
		public void imageToBuffer(ZETexture src, Vector3Int srcPos, Vector3Int srcSize, ZEBuffer dst, int dstOffset,
				int dstRowLength, int dstHeight) {
			ZEPixelFormat pxfmt = src.getPixelFormat();
			final int format = GLTexture.getGLTextureFormat(pxfmt);
			final int type = GLTexture.getGLTextureType(pxfmt);
			
			if (srcPos.x == 0 && srcPos.y == 0 && srcPos.z == 0 && srcSize.x == src.getWidth() && srcSize.y == src.getHeight()) {
				// If it is the full texture, use pixel buffers
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_PACK_BUFFER, ((GLBuffer)dst).bufferObject);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((GLTexture)src).texID);
					GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, format, type, dstOffset);
				});
			} else if (backend.getCapabilities().glGetTextureSubImage != 0) {
				// Else we have to do it the hard way, because apparently having glGetTextureSubImage work with buffers is too easy
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, ((GLBuffer)dst).bufferObject);
				ByteBuffer mem = GL15.glMapBuffer(GL15.GL_ARRAY_BUFFER, GL15.GL_WRITE_ONLY);
				mem.position(dstOffset);
				GL45.glGetTextureSubImage(GL11.GL_TEXTURE_2D, 0, srcPos.x, srcPos.y, srcPos.z, srcSize.x, srcSize.y, srcSize.z, format, type, mem);
			}
		}

		@Override
		public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
				Vector3Int dstPos, Vector3Int dstSize) {
			ZEPixelFormat pxformat = dst.getPixelFormat();
			final int format = GLTexture.getGLTextureFormat(pxformat);
			final int type = GLTexture.getGLTextureType(pxformat);
			
			if (srcRowLength == dst.getWidth() * pxformat.sizeOf && srcHeight == (dst.getWidth() * dst.getHeight() * pxformat.sizeOf)) {
				// If the stars align and all the values are right we can use glTexSubImage
				buildingCommands.add(() -> {
					GL15.glBindBuffer(GL21.GL_PIXEL_UNPACK_BUFFER, ((GLBuffer)src).bufferObject);
					GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((GLTexture)dst).texID);
					GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, dstPos.x, dstPos.y, dstSize.x, dstSize.y, format, type, srcOffset);
				});
			} else {
				// TODO Texture subimage upload for weird buffer values
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
