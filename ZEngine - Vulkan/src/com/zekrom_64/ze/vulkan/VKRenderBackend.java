package com.zekrom_64.ze.vulkan;

import java.awt.Rectangle;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCopy;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkImageBlit;
import org.lwjgl.vulkan.VkImageCopy;

import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.ZEShader;
import com.zekrom_64.ze.base.backend.render.ZEShaderCompiler;
import com.zekrom_64.ze.base.backend.render.ZETexture;
import com.zekrom_64.ze.base.backend.render.ZEVertexBuffer;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.threading.ZEEvent;

public class VKRenderBackend implements ZERenderBackend<VKRenderBackend> {

	private static final List<String> __features;
	static {
		String[] features = new String[] {
				ZERenderBackend.FEATURE_CACHEABLE_PIPELINE,
				ZERenderBackend.FEATURE_GENERIC_BUFFER,
				ZERenderBackend.FEATURE_GENERIC_IMAGE,
				ZERenderBackend.FEATURE_GENERIC_MEMORY,
				ZERenderBackend.FEATURE_MULTIPLE_FRAMEBUFFERS,
				ZERenderBackend.FEATURE_MULTIPLE_PIPELINES,
				ZERenderBackend.FEATURE_MULTITHREAD_RENDERING,
				ZERenderBackend.FEATURE_MULTITHREAD_SYNCHRONIZATION,
				ZERenderBackend.FEATURE_SHADER_COMPUTE_AVAILALE,
				ZERenderBackend.FEATURE_SHADER_MODULAR,
				ZERenderBackend.FEATURE_SHADER_UNIFORM_BUFFER,
				ZEShader.SHADER_TYPE_COMPUTE,
				ZEShader.SHADER_TYPE_VERTEX,
				ZEShader.SHADER_TYPE_FRAGMENT,
				ZEShader.SHADER_TYPE_GEOMETRY,
				ZEShader.SHADER_TYPE_TESSELLATION_CONTROL,
				ZEShader.SHADER_TYPE_TESSELLATION_EVALUATION,
				ZEShaderCompiler.SHADER_SOURCE_TYPE_SPIRV
		};
		Arrays.sort(features);
		__features = Arrays.asList(features);
	}
	
	@Override
	public boolean supportsFeature(String feature) {
		return __features.contains(feature);
	}

	@Override
	public void init(ZERenderOutput<VKRenderBackend> backend) {
		
	}

	@Override
	public void deinit() {
		
	}

	@Override
	public ZEPipeline createPipeline() {
		return null;
	}

	@Override
	public void reschedule(ZERenderWork ... work) {
		
	}

	@Override
	public void scheduleOnce(ZERenderWork[] work, Consumer<Void> finished) {
		
	}
	
	public static interface VKRenderWork extends ZERenderWork {
		
		public void queueWork(VkCommandBuffer buffer);
		
		public default Runnable getRunnable() {
			return null;
		}
		
	}
	
	public static class VKRenderWorkFactory implements ZERenderWorkFactory {

		@Override
		public VKRenderWork inlineWork(Runnable r) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) { }
				
				@Override
				public Runnable getRunnable() {
					return r;
				}
				
			};
		}

		@Override
		public ZECompoundRenderWork compoundWork(ZERenderWork[] components) {
			return null;
		}

		@Override
		public VKRenderWork bindPipeline(ZEPipeline pipeline) {
			return null;
		}

		@Override
		public VKRenderWork setScissor(Rectangle[] scissors) {
			return null;
		}

		@Override
		public VKRenderWork setLineWidth(float width) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdSetLineWidth(buffer, width);
				}
				
			};
		}

		@Override
		public VKRenderWork setDepthBounds(double min, double max) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdSetDepthBounds(buffer, (float)min, (float)max);
				}
				
			};
		}

		@Override
		public VKRenderWork bindVertexBuffer(ZEVertexBuffer buffer) {
			return null;
		}

		@Override
		public VKRenderWork bindIndexBuffer(ZEIndexBuffer buffer) {
			return null;
		}

		@Override
		public VKRenderWork draw(int nVertices, int start) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdDraw(buffer, nVertices, 1, start, 0);
				}
				
			};
		}

		@Override
		public VKRenderWork drawIndexed(int nIndices, int startIndex, int startVertex) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdDrawIndexed(buffer, nIndices, 1, startIndex, startVertex, 0);
				}
				
			};
		}

		@Override
		public VKRenderWork setEvent(ZEEvent event) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdSetEvent(buffer, 0, 0); // TODO: Determine the VkPipelineStageFlagBits to use for this
				}
				
			};
		}

		@Override
		public VKRenderWork resetEvent(ZEEvent event) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdResetEvent(buffer, 0, 0); // TODO: Determine the VKPipelineStageFlagBits to use for this
				}
				
			};
		}

		@Override
		public VKRenderWork blitBuffer(ZEBuffer src, ZEBuffer dst, int srcIndex, int dstIndex, int size) {
			VkBufferCopy.Buffer bufCopy = new VkBufferCopy.Buffer(BufferUtils.createByteBuffer(VkBufferCopy.SIZEOF));
			bufCopy.get(0).set(srcIndex, dstIndex, size);
			return new VKRenderWork() {
				
				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdCopyBuffer(buffer, ((VKBuffer)src).buffer, ((VKBuffer)dst).buffer, bufCopy);
				}
				
			};
		}
		
		public VKRenderWork vkBlitBuffer(ZEBuffer src, ZEBuffer dst, VkBufferCopy ... regions) {
			VkBufferCopy.Buffer pRegions = new VkBufferCopy.Buffer(BufferUtils.createByteBuffer(VkBufferCopy.SIZEOF * regions.length));
			for(int i = 0; i < regions.length; i++) pRegions.put(i, regions[i]);
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdCopyBuffer(buffer, ((VKBuffer)src).buffer, ((VKBuffer)dst).buffer, pRegions);
				}
				
			};
		}
		
		public VKRenderWork vkCopyImage(ZETexture src, ZETexture dst, VkImageCopy ... regions) {
			VkImageCopy.Buffer pRegions = new VkImageCopy.Buffer(BufferUtils.createByteBuffer(VkImageCopy.SIZEOF * regions.length));
			for(int i = 0; i < regions.length; i++) pRegions.put(i, regions[i]);
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdCopyImage(buffer, ((VKTexture)src).image, ((VKTexture)src).imageLayout, ((VKTexture)dst).image, 
							((VKTexture)dst).imageLayout, pRegions);
				}
				
			};
		}
		
		public VKRenderWork vkBlitImage(ZETexture src, ZETexture dst, int filter, VkImageBlit ... regions) {
			VkImageBlit.Buffer pRegions = new VkImageBlit.Buffer(BufferUtils.createByteBuffer(VkImageBlit.SIZEOF * regions.length));
			for(int i = 0; i < regions.length; i++) pRegions.put(i, regions[i]);
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdBlitImage(buffer, ((VKTexture)src).image, ((VKTexture)src).imageLayout, ((VKTexture)dst).image, 
							((VKTexture)dst).imageLayout, pRegions, filter);
				}
			
			};
		}
		
		public VKRenderWork vkCopyImageToBuffer(ZETexture src, ZEBuffer dst, VkBufferImageCopy ... regions) {
			VkBufferImageCopy.Buffer pRegions = new VkBufferImageCopy.Buffer(
					BufferUtils.createByteBuffer(VkBufferImageCopy.SIZEOF * regions.length));
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdCopyImageToBuffer(buffer, ((VKTexture)src).image, ((VKTexture)src).imageLayout,
							((VKBuffer)dst).buffer, pRegions);
				}
				
			};
		}
		
		public VKRenderWork vkCopyBufferToImage(ZEBuffer src, ZETexture dst, VkBufferImageCopy ... regions) {
			VkBufferImageCopy.Buffer pRegions = new VkBufferImageCopy.Buffer(
					BufferUtils.createByteBuffer(VkBufferImageCopy.SIZEOF * regions.length));
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdCopyBufferToImage(buffer, ((VKBuffer)src).buffer, ((VKTexture)dst).image,
							((VKTexture)dst).imageLayout, pRegions);
				}
				
			};
		}
		
		public VKRenderWork vkFillBuffer(ZEBuffer dst, long offset, long size, int data) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdFillBuffer(buffer, ((VKBuffer)dst).buffer, offset, size, data);
				}
				
			};
		}
		
		public VKRenderWork vkSetDepthBias(float constFactor, float clamp, float slopeFactor) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buffer) {
					VK10.vkCmdSetDepthBias(buffer, constFactor, clamp, slopeFactor);
				}
				
			};
		}
		
		public VKRenderWork vkDrawIndirect(ZEBuffer buffer, long offset, int drawCount, int stride) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buf) {
					VK10.vkCmdDrawIndirect(buf, ((VKBuffer)buffer).buffer, offset, drawCount, stride);
				}
			
			};
		}
		
		public VKRenderWork vkDrawIndexedIndirect(ZEBuffer buffer, long offset, int drawCount, int stride) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buf) {
					VK10.vkCmdDrawIndexedIndirect(buf, ((VKBuffer)buffer).buffer, offset, drawCount, stride);
				}
				
			};
		}
		
		public VKRenderWork vkUpdateBuffer(final ZEBuffer buffer, long offset, long size, Buffer data) {
			return new VKRenderWork() {

				@Override
				public void queueWork(VkCommandBuffer buf) {
					VK10.nvkCmdUpdateBuffer(buf, ((VKBuffer)buffer).buffer, offset, size, MemoryUtil.memAddress0(data));
				}
				
			};
		}
		
	}
	
	// It doesn't technically need to be per render backend instance, but it could be useful in the future
	private final VKRenderWorkFactory factory = new VKRenderWorkFactory();

	@Override
	public ZERenderWorkFactory getWorkFactory() {
		return factory;
	}
	
	public static int getVkFormat(ZEPixelFormat fmt) {
		switch(fmt) {
		case A8R8G8B8: return VK10.VK_FORMAT_A8B8G8R8_UNORM_PACK32;
		case R8G8B8A8: return VK10.VK_FORMAT_R8G8B8A8_UNORM;
		}
		return VK10.VK_FORMAT_UNDEFINED;
	}

}
