package com.zekrom_64.ze.vulkan;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkEventCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderContext;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEIndexBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZEVertexBuffer;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShader;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;
import com.zekrom_64.ze.vulkan.objects.VKBuffer;
import com.zekrom_64.ze.vulkan.objects.VKRenderEvent;
import com.zekrom_64.ze.vulkan.objects.VKTexture;

/** Vulkan render backend implementation.
 * 
 * @author Zekrom_64
 *
 */
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
	
	/** The Vulkan context for the render backend. */
	public final VKContext context;
	/** The Vulkan logical device used by the render backend. */
	public final VKLogicalDevice device;
	
	/** Creates a new Vulkan render backed using the given logical device.
	 * 
	 * @param device Logical device
	 */
	public VKRenderBackend(VKLogicalDevice device) {
		this.device = device;
		this.context = device.instance;
	}
	
	private static class VulkanLocalInfo {

		// Vulkan pipeline staging flags
		private int pipelineFlags = VK10.VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT;
		
	}
	
	private static ThreadLocal<VulkanLocalInfo> localInfo = new ThreadLocal<>();
	
	private static VulkanLocalInfo getLocalInfo() {
		VulkanLocalInfo info = localInfo.get();
		if (info == null) {
			info = new VulkanLocalInfo();
			localInfo.set(info);
		}
		return info;
	}
	
	/** Sets flags to use for the next command requiring pipeline staging flags.
	 * 
	 * @param flags Pipeline staging flags
	 */
	public void setPipelineStageFlags(int flags) {
		getLocalInfo().pipelineFlags = flags;
	}
	
	/** Gets the flags to use for pipeline staging.
	 * 
	 * @return Pipeline staging flags
	 */
	public int getPipelineStageFlags() {
		return getLocalInfo().pipelineFlags;
	}
	
	/** Form of {@link #getPipelineStageFlags()} that resets the flags to
	 * {@link org.lwjgl.vulkan.VK10#VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT
	 * VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT} on return.
	 * 
	 * @return Pipeline staging flags
	 */
	public int getAndResetPipelineStageFlags() {
		VulkanLocalInfo info = getLocalInfo();
		int flags = info.pipelineFlags;
		info.pipelineFlags = VK10.VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT;
		return flags;
	}
	
	@Override
	public boolean supportsFeature(String feature) {
		return __features.contains(feature);
	}

	@Override
	public void init(ZERenderContext<VKRenderBackend> backend) {
		
	}

	@Override
	public void deinit() {
		
	}

	@Override
	public ZEPipeline getDefaultPipeline() {
		return null;
	}

	@Override
	public ZEPipelineBuilder createPipelineBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZEShaderCompiler getShaderCompiler() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZERenderEvent createRenderEvent() {
		try(MemoryStack sp = MemoryStack.stackPush()) {
			VkEventCreateInfo createInfo = VkEventCreateInfo.mallocStack(sp);
			createInfo.set(VK10.VK_STRUCTURE_TYPE_EVENT_CREATE_INFO, 0, 0);
			long[] pEvent = new long[1];
			int err = VK10.vkCreateEvent(device.logicalDevice, createInfo, context.allocCallbacks, pEvent);
			if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create Vulkan event", err);
			return new VKRenderEvent(pEvent[0], device.logicalDevice);
		}
	}

	@Override
	public ZEBuffer allocateBuffer(int size, int flags) {
		try (MemoryStack sp = MemoryStack.stackPush()) {
			VkBufferCreateInfo bufferInfo = VkBufferCreateInfo.mallocStack(sp);
			bufferInfo.set(
					VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
					0,
					0,
					size,
					usage,
					sharingMode,
					pQueueFamilyIndices
			);

			long[] pBuffer = new long[1];
			int err = VK10.vkCreateBuffer(device.logicalDevice, bufferInfo, context.allocCallbacks, pBuffer);
			if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create memory buffer", err);
			
			VkMemoryAllocateInfo allocInfo = VkMemoryAllocateInfo.mallocStack(sp);
			allocInfo.set(
					VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO,
					0,
					size,
					memoryTypeIndex
			);
			
			long[] pMem = new long[1];
			err = VK10.vkAllocateMemory(device.logicalDevice, allocInfo, context.allocCallbacks, pMem);
			if (err != VK10.VK_SUCCESS) {
				VK10.vkDestroyBuffer(device.logicalDevice, pBuffer[0], context.allocCallbacks);
				throw new VulkanException("Failed to allocate device memory for buffer", err);
			}
			
			err = VK10.vkBindBufferMemory(device.logicalDevice, pBuffer[0], pMem[0], 0);
			if (err != VK10.VK_SUCCESS) {
				VK10.vkDestroyBuffer(device.logicalDevice, pBuffer[0], context.allocCallbacks);
				VK10.vkFreeMemory(device.logicalDevice, pMem[0], context.allocCallbacks);
			}
			
			return new VKBuffer(device.logicalDevice, pBuffer[0], size, pMem[0], 0, flags);
		}
	}

	@Override
	public ZEBuffer[] allocateBuffers(int[] sizes, int[] flags) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void freeBuffer(ZEBuffer buffer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void freeBuffer(ZEBuffer... buffers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZEVertexBuffer createVertexBuffer(ZEBuffer buffer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZETexture createTexture(int width, int height, ZEPixelFormat format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZETexture[] createTextures(int[] width, int[] height, ZEPixelFormat[] format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyTexture(ZETexture texture) {
		VKTexture vktex = (VKTexture)texture;
		VK10.vkDestroyImageView(device.logicalDevice, vktex.imageView, context.allocCallbacks);
		VK10.vkDestroyImage(device.logicalDevice, vktex.image, context.allocCallbacks);
		VK10.vkFreeMemory(device.logicalDevice, vktex.imageMemory, context.allocCallbacks);
	}

	@Override
	public void destroyPipeline(ZEPipeline pipeline) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZEIndexBuffer createIndexBuffer(ZEBuffer buffer, PrimitiveType indexType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyTexture(ZETexture... textures) {
		for(ZETexture tex : textures) {
			VKTexture vktex = (VKTexture)tex;
			VK10.vkDestroyImageView(device.logicalDevice, vktex.imageView, context.allocCallbacks);
			VK10.vkDestroyImage(device.logicalDevice, vktex.image, context.allocCallbacks);
			VK10.vkFreeMemory(device.logicalDevice, vktex.imageMemory, context.allocCallbacks);
		}
	}

	@Override
	public ZERenderCommandBuffer createCommandBuffer(boolean multipleRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZERenderCommandBuffer createCommandBuffers(int count, boolean multipleRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyCommandBuffer(ZERenderCommandBuffer cmdBuf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desotryCommandBuffers(ZERenderCommandBuffer... cmdBufs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitCommands(ZERenderCommandBuffer cmdBuf) {
		// TODO Auto-generated method stub
		
	}

}
