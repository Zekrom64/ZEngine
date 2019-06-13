package com.zekrom_64.ze.vulkan.objects;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkBufferCopy;
import org.lwjgl.vulkan.VkBufferImageCopy;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandBufferInheritanceInfo;
import org.lwjgl.vulkan.VkExtent3D;
import org.lwjgl.vulkan.VkImageCopy;
import org.lwjgl.vulkan.VkImageSubresourceLayers;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkOffset3D;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkViewport;

import com.zekrom_64.mathlib.shape.Rectangle;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.ze.base.backend.render.ZERenderCommandBuffer;
import com.zekrom_64.ze.base.backend.render.ZERenderWorkRecorder;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZEColorClearValue;
import com.zekrom_64.ze.base.backend.render.obj.ZEFramebuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;
import com.zekrom_64.ze.base.backend.render.obj.ZERenderPass;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler.ZEFilter;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayer;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureLayout;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureRange;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipeline;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEViewport;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;
import com.zekrom_64.ze.vulkan.VKRenderBackend;
import com.zekrom_64.ze.vulkan.VKValues;
import com.zekrom_64.ze.vulkan.VulkanException;

public class VKCommandBuffer implements ZERenderCommandBuffer {

	private class VKCommandBufferRecorder implements ZERenderWorkRecorder {

		@Override
		public void executeBuffer(ZERenderCommandBuffer buffer) {
			VK10.vkCmdExecuteCommands(commandBuffer, ((VKCommandBuffer)buffer).commandBuffer);
		}

		@Override
		public void bindPipeline(ZEPipeline pipeline) {
			VK10.vkCmdBindPipeline(commandBuffer, VK10.VK_PIPELINE_BIND_POINT_GRAPHICS, ((VKPipeline)pipeline).pipeline);
		}

		@Override
		public void beginPass(ZERenderPass renderPass, ZEFramebuffer framebuffer, ZEAttachmentClearValue[] clearValues) {
			// TODO Auto-generated method stub
		}

		@Override
		public void nextPass() {
			VK10.vkCmdNextSubpass(commandBuffer, VK10.VK_SUBPASS_CONTENTS_INLINE); // TODO: Secondary command buffers?
		}

		@Override
		public void endPass() {
			VK10.vkCmdEndRenderPass(commandBuffer);
		}

		@Override
		public void setScissor(Rectangle[] scissors, int firstScissor, int numScissors) {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				VkRect2D.Buffer pScissors = VkRect2D.mallocStack(numScissors, sp);
				for(int i = firstScissor; i < scissors.length; i++) {
					Rectangle scissor = scissors[i];
					VkRect2D rect = pScissors.get();
					rect.offset().set((int)scissor.getPositionX(), (int)scissor.getPositionY());
					rect.extent().set((int)scissor.getWidth(), (int)scissor.getHeight());
				}
				VK10.vkCmdSetScissor(commandBuffer, 0, pScissors);
			}
		}

		@Override
		public void setLineWidth(float width) {
			VK10.vkCmdSetLineWidth(commandBuffer, width);
		}

		@Override
		public void setDepthBounds(double min, double max) {
			VK10.vkCmdSetDepthBounds(commandBuffer, (float)min, (float)max);
		}

		@Override
		public void bindVertexBuffers(int firstBindPoint, ZEBuffer ... buffers) {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				int count = buffers.length;
				LongBuffer pBuffers = sp.mallocLong(count);
				for(int i = 0; i < count; i++) pBuffers.put(((VKBuffer)buffers[i]).buffer);
				LongBuffer pOffsets = sp.mallocLong(count);
				for(int i = 0; i < count; i++) pOffsets.put(firstBindPoint + i);
				pBuffers.rewind();
				pOffsets.rewind();
				VK10.vkCmdBindVertexBuffers(commandBuffer, 0, pBuffers, pOffsets);
			}
		}

		@Override
		public void bindIndexBuffer(ZEBuffer buffer, ZEPrimitiveType indexType) {
			int vkIndexType = -1;
			switch(indexType) {
			case UINT: vkIndexType = VK10.VK_INDEX_TYPE_UINT32; break;
			case USHORT: vkIndexType = VK10.VK_INDEX_TYPE_UINT16; break;
			default: throw new VulkanException("Cannot use index buffer of primitive type " + indexType + " with Vulkan");
			}
			VK10.vkCmdBindIndexBuffer(commandBuffer, ((VKBuffer)buffer).buffer, 0, vkIndexType);
		}

		@Override
		public void draw(int nVertices, int start) {
			VK10.vkCmdDraw(commandBuffer, nVertices, 1, start, 0);
		}

		@Override
		public void drawIndexed(int nIndices, int startIndex, int startVertex) {
			VK10.vkCmdDrawIndexed(commandBuffer, nIndices, 1, startIndex, startVertex, 0);
		}

		@Override
		public void drawIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			VK10.vkCmdDrawIndirect(commandBuffer, ((VKBuffer)paramBuffer).buffer, offset, drawCount, stride);
		}

		@Override
		public void drawIndexedIndirect(ZEBuffer paramBuffer, int offset, int drawCount, int stride) {
			VK10.vkCmdDrawIndexedIndirect(commandBuffer, ((VKBuffer)paramBuffer).buffer, offset, drawCount, stride);
		}

		@Override
		public void setEvent(ZEPipelineStage stage, ZERenderEvent event) {
			VK10.vkCmdSetEvent(commandBuffer, ((VKRenderEvent)event).event, VKValues.getVKPipelineStage(stage));
		}

		@Override
		public void resetEvent(ZEPipelineStage stage, ZERenderEvent event) {
			VK10.vkCmdResetEvent(commandBuffer, ((VKRenderEvent)event).event, VKValues.getVKPipelineStage(stage));
		}

		@Override
		public void blitBuffer(ZEBuffer src, ZEBuffer dst, long srcPos, long dstPos, long size) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkBufferCopy.Buffer region = VkBufferCopy.mallocStack(1, sp);
				region.get(0).set(srcPos, dstPos, size);
				VK10.vkCmdCopyBuffer(commandBuffer, ((VKBuffer)src).buffer, ((VKBuffer)dst).buffer, region);
			}
		}

		@Override
		public void blitTexture(ZETexture srcTex, ZETextureLayer srcLayer, ZETextureLayout srcLayout, Vector3I srcPos, Vector3I srcSize,
				ZETexture dstTex, ZETextureLayer dstLayer, ZETextureLayout dstLayout, Vector3I dstPos, Vector3I dstSize,
				ZEFilter filter) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageCopy.Buffer region = VkImageCopy.mallocStack(1, sp);
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				subresource.set(VK10.VK_IMAGE_ASPECT_COLOR_BIT, 0, 0, 1);
				VkOffset3D offsetSrc = VkOffset3D.mallocStack(sp), offsetDst = VkOffset3D.mallocStack(sp);
				offsetSrc.set(srcPos.x, srcPos.y, srcPos.z);
				offsetDst.set(dstPos.x, dstPos.y, dstPos.z);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				if (srcSize.equals(dstSize)) {
					extent.set(srcSize.x, srcSize.y, srcSize.z);
					region.get(0).set(subresource, offsetSrc, subresource, offsetDst, extent);
					//VK10.vkCmdCopyImage(commandBuffer, ((VKTexture)srcTex).image, ((VKTexture)srcTex).imageLayout, ((VKTexture)dstTex).image, ((VKTexture)dstTex).imageLayout, region);
				} else {
					// TODO
				}
			}
		}

		@Override
		public void clearBuffer(ZEBuffer buf, int offset, int count, ByteBuffer value) {
			final int size = value.remaining();
			if (offset % 4 == 0 && size == 4) {
				// Aligned ints can be used with vkCmdFillBuffer
				VK10.vkCmdFillBuffer(commandBuffer, ((VKBuffer)buf).buffer, offset, count * 4, value.getInt(value.position()));
			} else {
				if (count * size < 65536) {
					// If the total clear size is small enough, vkCmdUpdateBuffer can be used
					try (MemoryStack sp = MemoryStack.stackPush()) {
						ByteBuffer temp = sp.malloc(count * size);
						byte[] byteValue = new byte[size];
						int pos = value.position();
						value.get(byteValue);
						value.position(pos);
						for(int i = 0; i < count; i++) temp.put(byteValue);
						temp.flip();
						VK10.vkCmdUpdateBuffer(commandBuffer, ((VKBuffer)buf).buffer, offset, temp);
					}
				} else {
					// TODO: Implement non-aligned value filling for large buffers
				}
			}
		}

		@Override
		public void clearTexture(ZETexture tex, Vector3I start, Vector3I size, ZETextureRange range, ZEColorClearValue color) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceRange vkrange = VkImageSubresourceRange.mallocStack(sp);
				// TODO
				VK10.vkCmdClearColorImage(commandBuffer, ((VKTexture)tex).image, 0, null, vkrange);
			}
		}

		@Override
		public void imageToBuffer(ZETexture src, Vector3I srcPos, Vector3I srcSize, ZETextureLayer srcLayer, ZEBuffer dst, int dstOffset,
				int dstRowLength, int dstHeight) {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				VkBufferImageCopy.Buffer region = VkBufferImageCopy.mallocStack(1, sp);
				VkOffset3D offset = VkOffset3D.mallocStack(sp);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				offset.set(srcPos.x, srcPos.y, srcPos.z);
				extent.set(srcSize.x, srcSize.y, srcSize.z);
				subresource.set(VKValues.getVKImageAspects(srcLayer.aspects), srcLayer.mipLevel, srcLayer.baseArrayLayer, srcLayer.arrayLayerCount);
				region.get(0).set(dstOffset, dstRowLength, dstHeight, subresource, offset, extent);
				VK10.vkCmdCopyImageToBuffer(commandBuffer, ((VKTexture)src).image, ((VKTexture)src).imageLayout, ((VKBuffer)dst).buffer, region);
			}
		}

		@Override
		public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
				Vector3I dstPos, Vector3I dstSize, ZETextureLayer dstLayer) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				VkBufferImageCopy.Buffer region = VkBufferImageCopy.mallocStack(1, sp);
				VkOffset3D offset = VkOffset3D.mallocStack(sp);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				offset.set(dstPos.x, dstPos.y, dstPos.z);
				extent.set(dstSize.x, dstSize.y, dstSize.z);
				subresource.set(VKValues.getVKImageAspects(dstLayer.aspects), dstLayer.mipLevel, dstLayer.baseArrayLayer, dstLayer.arrayLayerCount);
				region.get(0).set(srcOffset, srcRowLength, srcHeight, subresource, offset, extent);
				VK10.vkCmdCopyBufferToImage(commandBuffer, ((VKBuffer)src).buffer, ((VKTexture)dst).image, ((VKTexture)dst).imageLayout, region);
			}
		}

		@Override
		public void uploadToBuffer(ZEBuffer dst, int dstoff, int len, ByteBuffer data) {
			VK10.nvkCmdUpdateBuffer(commandBuffer, ((VKBuffer)dst).buffer, dstoff, len, MemoryUtil.memAddress(data));
		}

		@Override
		public void transitionTextureLayout(ZETexture tex, ZETextureLayout oldLayout, ZETextureLayout newLayout) {
			// TODO
		}

		@Override
		public void setViewport(ZEViewport[] viewports, int firstViewport) {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				VkViewport.Buffer pViewports = VkViewport.mallocStack(viewports.length, sp);
				for(ZEViewport vp : viewports) {
					pViewports.get().set(
							(float)vp.area.getPositionX(), 
							(float)vp.area.getPositionY(), 
							(float)vp.area.getWidth(), 
							(float)vp.area.getHeight(), 
							(float)vp.minDepth, 
							(float)vp.maxDepth
					);
				}
				pViewports.rewind();
				VK10.vkCmdSetViewport(commandBuffer, firstViewport, pViewports);
			}
		}

		@Override
		public void setDepthBias(double constantFactor, double clamp, double slopeFactor) {
			VK10.vkCmdSetDepthBias(commandBuffer, (float)constantFactor, (float)clamp, (float)slopeFactor);
		}

		@Override
		public void setBlendConstants(float... constants) {
			VK10.vkCmdSetBlendConstants(commandBuffer, constants);
		}

		@Override
		public void setStencilCompareMask(ZEFrontBack face, int compareMask) {
			VK10.vkCmdSetStencilCompareMask(commandBuffer, VKValues.getVKFace_Stencil(face), compareMask);
		}

		@Override
		public void setStencilWriteMask(ZEFrontBack face, int writeMask) {
			VK10.vkCmdSetStencilWriteMask(commandBuffer, VKValues.getVKFace_Stencil(face), writeMask);
		}

		@Override
		public void setStencilReference(ZEFrontBack face, int reference) {
			VK10.vkCmdSetStencilReference(commandBuffer, VKValues.getVKFace_Stencil(face), reference);
		}

		@Override
		public void waitForEvents(ZEPipelineStage[] signalStages, ZEPipelineStage[] waitingStages,
				ZERenderEvent... events) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				LongBuffer pEvents = sp.mallocLong(events.length);
				VK10.vkCmdWaitEvents(
					commandBuffer,
					pEvents,
					VKValues.getVKPipelineStages(waitingStages),
					VKValues.getVKPipelineStages(signalStages),
					null,
					null,
					null
				);
			}
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

		@Override
		public void generateMipmaps(ZETexture tex) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public final VKRenderBackend backend;
	public final boolean rerecordable;
	private VkCommandBuffer commandBuffer;
	private VKCommandBufferRecorder recorder;
	private boolean recorded = false;
	
	private VKFramebuffer recordPassFramebuffer;
	
	public VKCommandBuffer(VkCommandBuffer cmdBuf, VKRenderBackend backend, boolean rerecordable) {
		this.backend = backend;
		commandBuffer = cmdBuf;
		this.rerecordable = rerecordable;
	}
	
	@Override
	public ZERenderWorkRecorder beginRecording() {
		if (recorder != null) return recorder;
		if (recorded && !rerecordable) return null;
		recorder = new VKCommandBufferRecorder();
		try(MemoryStack sp = MemoryStack.stackPush()) {
			VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.mallocStack(sp);
			beginInfo.set(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO, 0, 0, null);
			
			VK10.vkBeginCommandBuffer(commandBuffer, beginInfo);
		}
		return recorder;
	}

	@Override
	public ZERenderWorkRecorder beginRecording(ZERenderCommandBuffer parent) {
		if (recorder != null) return recorder;
		if (recorded && !rerecordable) return null;
		recorder = new VKCommandBufferRecorder();
		try(MemoryStack sp = MemoryStack.stackPush()) {
			VKCommandBuffer vkparent = (VKCommandBuffer)parent;
			VkCommandBufferInheritanceInfo inheritInfo = VkCommandBufferInheritanceInfo.mallocStack(sp);
			inheritInfo.set( // TODO Finish command buffer inherit info
					VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO,
					0,
					0 /* */,
					0 /* */,
					vkparent.recordPassFramebuffer.framebuffer /* */,
					false /* */,
					0 /* */,
					0 /* */
			);
			
			VkCommandBufferBeginInfo beginInfo = VkCommandBufferBeginInfo.mallocStack(sp);
			beginInfo.set(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO, 0, 0, inheritInfo);
			
			VK10.vkBeginCommandBuffer(commandBuffer, beginInfo);
		}
		return recorder;
	}

	@Override
	public void endRecording() {
		if (recorder != null) {
			VK10.vkEndCommandBuffer(commandBuffer);
		}
	}

}
