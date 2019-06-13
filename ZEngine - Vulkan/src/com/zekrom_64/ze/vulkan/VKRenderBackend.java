package com.zekrom_64.ze.vulkan;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkEventCreateInfo;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkMemoryAllocateInfo;
import org.lwjgl.vulkan.VkMemoryRequirements;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;

import com.zekrom_64.ze.base.backend.render.ZERenderBackend;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderOutput;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer.ZEBufferUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebufferBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPassBuilder;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderSemaphore;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderThread;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindLayoutBuilder;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindPool;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineLayout;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineLayoutBuilder;
import com.zekrom_64.ze.base.backend.render.shader.ZEShader;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.vulkan.objects.VKBuffer;
import com.zekrom_64.ze.vulkan.objects.VKFence;
import com.zekrom_64.ze.vulkan.objects.VKRenderEvent;
import com.zekrom_64.ze.vulkan.objects.VKSemaphore;
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
				ZERenderBackend.FEATURE_MULTIPLE_FRAMEBUFFERS,
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
	public final VKDevice device;
	
	/** Creates a new Vulkan render backed using the given logical device.
	 * 
	 * @param device Logical device
	 */
	public VKRenderBackend(VKDevice device) {
		this.device = device;
		this.context = device.instance;
	}
	
	/*
	
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
	 *
	public void setPipelineStageFlags(int flags) {
		getLocalInfo().pipelineFlags = flags;
	}
	
	/** Gets the flags to use for pipeline staging.
	 * 
	 * @return Pipeline staging flags
	 *
	public int getPipelineStageFlags() {
		return getLocalInfo().pipelineFlags;
	}
	
	/** Form of {@link #getPipelineStageFlags()} that resets the flags to
	 * {@link org.lwjgl.vulkan.VK10#VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT
	 * VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT} on return.
	 * 
	 * @return Pipeline staging flags
	 *
	public int getAndResetPipelineStageFlags() {
		VulkanLocalInfo info = getLocalInfo();
		int flags = info.pipelineFlags;
		info.pipelineFlags = VK10.VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT;
		return flags;
	}
	*/
	
	@Override
	public boolean supportsFeature(String feature) {
		return __features.contains(feature);
	}

	@Override
	public int getLimitInt(String limit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void init(ZERenderOutput<VKRenderBackend> backend) {
		
	}

	@Override
	public void deinit() {
		
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
	public ZEBuffer allocateBuffer(long size, ZEBufferUsage[] usages, ZEBufferParameter ... params) {
		try (MemoryStack sp = MemoryStack.stackPush()) {
			boolean concurrent = false;
			IntBuffer pQueueFamilyIndices = null;
			if (concurrent) {
				// TODO: Fill with queue family indices
			}
			
			VkBufferCreateInfo bufferInfo = VkBufferCreateInfo.mallocStack(sp);
			bufferInfo.set(
					VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO,
					0,
					0,
					size,
					VKValues.getVKBufferUsages(usages),
					concurrent ? VK10.VK_SHARING_MODE_CONCURRENT : VK10.VK_SHARING_MODE_EXCLUSIVE,
					pQueueFamilyIndices
			);

			long[] pBuffer = new long[1];
			int err = VK10.vkCreateBuffer(device.logicalDevice, bufferInfo, context.allocCallbacks, pBuffer);
			if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create memory buffer", err);
			
			VkMemoryRequirements pMemoryRequirements = VkMemoryRequirements.mallocStack(sp);
			VK10.vkGetBufferMemoryRequirements(device.logicalDevice, pBuffer[0], pMemoryRequirements);
			
			VkMemoryAllocateInfo allocInfo = VkMemoryAllocateInfo.mallocStack(sp);
			allocInfo.set(
					VK10.VK_STRUCTURE_TYPE_MEMORY_ALLOCATE_INFO,
					0,
					size,
					0 // TODO
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
			
			return new VKBuffer(device.logicalDevice, pBuffer[0], size, pMem[0], 0, 0, usages);
		}
	}
	
	@Override
	public void freeBuffers(ZEBuffer... buffers) {
		for(ZEBuffer buf : buffers) {
			VKBuffer vkbuf = (VKBuffer)buf;
			VK10.vkDestroyBuffer(vkbuf.device, vkbuf.buffer, context.allocCallbacks);
		}
	}

	@Override
	public ZETexture createTexture(ZETextureDimension dim, int width, int height, int depth,
			ZEPixelFormat format, ZETextureUsage[] usages, ZETextureParameter ... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyTextures(ZETexture ... textures) {
		for(ZETexture texture : textures) {
			VKTexture vktex = (VKTexture)texture;
			VK10.vkDestroyImageView(device.logicalDevice, vktex.imageView, context.allocCallbacks);
			VK10.vkDestroyImage(device.logicalDevice, vktex.image, context.allocCallbacks);
			VK10.vkFreeMemory(device.logicalDevice, vktex.imageMemory, context.allocCallbacks);
		}
	}

	@Override
	public void destroyPipelines(ZEPipeline ... pipelines) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZERenderCommandBuffer createCommandBuffer(boolean multipleRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyCommandBuffers(ZERenderCommandBuffer... cmdBufs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZERenderCommandBuffer[] createCommandBuffers(int count, boolean multipleRecords) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZERenderThread getPrimaryRenderThread() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ZERenderThread[] getRenderThreads() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyRenderEvents(ZERenderEvent... events) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZERenderSemaphore createRenderSemaphore() {
		try(MemoryStack sp = MemoryStack.stackPush()) {
			VkSemaphoreCreateInfo pCreateInfo = VkSemaphoreCreateInfo.mallocStack(sp);
			pCreateInfo.set(VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO, 0, 0);
			long[] pSemaphore = new long[1];
			int err = VK10.vkCreateSemaphore(device.logicalDevice, pCreateInfo, context.allocCallbacks, pSemaphore);
			if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create semaphore", err);
			return new VKSemaphore(pSemaphore[0]);
		}
	}

	@Override
	public void destroyRenderSemaphores(ZERenderSemaphore... semaphores) {
		for(ZERenderSemaphore s : semaphores) {
			VK10.vkDestroySemaphore(device.logicalDevice, ((VKSemaphore)s).semaphore, context.allocCallbacks);
		}
	}

	@Override
	public ZERenderFence createRenderFence() {
		try(MemoryStack sp = MemoryStack.stackPush()) {
			VkFenceCreateInfo pCreateInfo = VkFenceCreateInfo.mallocStack(sp);
			pCreateInfo.set(VK10.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO, 0, 0);
			long[] pFence = new long[1];
			int err = VK10.vkCreateFence(device.logicalDevice, pCreateInfo, context.allocCallbacks, pFence);
			if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create fence", err);
			return new VKFence(device, pFence[0]);
		}
	}

	@Override
	public void destroyRenderFences(ZERenderFence... fences) {
		for(ZERenderFence f : fences) {
			VK10.vkDestroyFence(device.logicalDevice, ((VKFence)f).fence, context.allocCallbacks);
		}
	}

	@Override
	public boolean waitForFences(ZEFenceWaitMode waitMode, long timeout, ZERenderFence... fences) {
		long[] vkfences = new long[fences.length];
		for(int i = 0; i < fences.length; i++) vkfences[i] = ((VKFence)fences[i]).fence;
		int err = VK10.vkWaitForFences(device.logicalDevice, vkfences, waitMode == ZEFenceWaitMode.ALL, timeout);
		if (err == VK10.VK_SUCCESS) return false;
		if (err == VK10.VK_TIMEOUT) return true;
		throw new VulkanException("Failed to wait for fences", err);
	}

	@Override
	public void resetFences(ZERenderFence... fences) {
		long[] vkfences = new long[fences.length];
		for(int i = 0; i < fences.length; i++) vkfences[i] = ((VKFence)fences[i]).fence;
		int err = VK10.vkResetFences(device.logicalDevice, vkfences);
		if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to reset fences", err);
	}

	@Override
	public float getLimitFloat(String limit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ZEPipelineLayoutBuilder createPipelineLayoutBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyPipelineLayouts(ZEPipelineLayout... layouts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZEPipelineBindLayoutBuilder createPipelineBindLayoutBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyPipelineBindLayouts(ZEPipelineBindLayout... pipelineBindLayouts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZEPipelineBindPool createBindPool(int maxAllocSets, ZEBindingCount... allocBindings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyBindPools(ZEPipelineBindPool... bindPools) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZEFramebufferBuilder createFramebufferBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyFramebuffers(ZEFramebuffer... framebuffers) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ZERenderPassBuilder createRenderPassBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroyRenderPasses(ZERenderPass... renderPasses) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroySamplers(ZESampler... samplers) {
		// TODO Auto-generated method stub
		
	}

}
