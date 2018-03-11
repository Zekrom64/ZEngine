package com.zekrom_64.ze.vulkan.objects;

import java.awt.Rectangle;
import java.nio.ByteBuffer;

import javax.vecmath.Point3i;

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

import com.zekrom_64.ze.base.backend.render.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.ZEFramebuffer;
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
import com.zekrom_64.ze.vulkan.VKRenderBackend;
import com.zekrom_64.ze.vulkan.VulkanException;

public class VKCommandBuffer implements ZERenderCommandBuffer {

	private class VKCommandBufferRecorder implements ZERenderWorkRecorder {

		@Override
		public void inlineWork(Runnable r) {
			
		}

		@Override
		public void executeBuffer(ZERenderCommandBuffer buffer) {
			VK10.vkCmdExecuteCommands(commandBuffer, ((VKCommandBuffer)buffer).commandBuffer);
		}

		@Override
		public void bindPipeline(ZEPipeline pipeline) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beginPass(ZEPipelineBindSet bindSet, ZEFramebuffer framebuffer) {
			// TODO Auto-generated method stub
			
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
					rect.offset().set(scissor.x, scissor.y);
					rect.extent().set(scissor.width, scissor.height);
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
		public void bindVertexBuffer(ZEVertexInput bindPoint, ZEVertexBuffer buffer) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void bindIndexBuffer(ZEIndexBuffer buffer) {
			PrimitiveType indexType = buffer.getIndexType();
			int vkIndexType = -1;
			switch(indexType) {
			case UINT: vkIndexType = VK10.VK_INDEX_TYPE_UINT32; break;
			case USHORT: vkIndexType = VK10.VK_INDEX_TYPE_UINT16; break;
			default: throw new VulkanException("Cannot use index buffer of primitive type " + indexType + " with Vulkan");
			}
			VK10.vkCmdBindIndexBuffer(commandBuffer, ((VKIndexBuffer)buffer).buffer.buffer, 0, vkIndexType);
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
		public void setEvent(ZERenderEvent event) {
			VK10.vkCmdSetEvent(commandBuffer, ((VKRenderEvent)event).event, backend.getAndResetPipelineStageFlags());
		}

		@Override
		public void resetEvent(ZERenderEvent event) {
			VK10.vkCmdResetEvent(commandBuffer, ((VKRenderEvent)event).event, backend.getAndResetPipelineStageFlags());
		}

		@Override
		public void blitBuffer(ZEBuffer src, ZEBuffer dst, int srcPos, int dstPos, int size) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkBufferCopy.Buffer region = VkBufferCopy.mallocStack(1, sp);
				region.get(0).set(srcPos, dstPos, size);
				VK10.vkCmdCopyBuffer(commandBuffer, ((VKBuffer)src).buffer, ((VKBuffer)dst).buffer, region);
			}
		}

		@Override
		public void blitTexture(ZETexture srcTex, ZETexture dstTex, Point3i src, Point3i dst, Point3i size) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageCopy.Buffer region = VkImageCopy.mallocStack(1, sp);
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				subresource.set(VK10.VK_IMAGE_ASPECT_COLOR_BIT, 0, 0, 1);
				VkOffset3D offsetSrc = VkOffset3D.mallocStack(sp), offsetDst = VkOffset3D.mallocStack(sp);
				offsetSrc.set(src.x, src.y, src.z);
				offsetDst.set(dst.x, dst.y, dst.z);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				extent.set(size.x, size.y, size.z);
				region.get(0).set(subresource, offsetSrc, subresource, offsetDst, extent);
				VK10.vkCmdCopyImage(commandBuffer, ((VKTexture)srcTex).image, ((VKTexture)srcTex).imageLayout, ((VKTexture)dstTex).image, ((VKTexture)dstTex).imageLayout, region);
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
		public void clearTexture(ZETexture tex, Point3i start, Point3i size, ZEPixelFormat format, ByteBuffer color) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceRange range = VkImageSubresourceRange.mallocStack(sp);
				VK10.vkCmdClearColorImage(commandBuffer, ((VKTexture)tex).image, 0, null, range);
			}
		}

		@Override
		public void imageToBuffer(ZETexture src, Point3i srcPos, Point3i srcSize, ZEBuffer dst, int dstOffset,
				int dstRowLength, int dstHeight) {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				VkBufferImageCopy.Buffer region = VkBufferImageCopy.mallocStack(1, sp);
				VkOffset3D offset = VkOffset3D.mallocStack(sp);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				offset.set(srcPos.x, srcPos.y, srcPos.z);
				extent.set(srcSize.x, srcSize.y, srcSize.z);
				subresource.set(VK10.VK_IMAGE_ASPECT_COLOR_BIT, 0, 0, 1);
				region.get(9).set(dstOffset, dstRowLength, dstHeight, subresource, offset, extent);
				VK10.vkCmdCopyImageToBuffer(commandBuffer, ((VKTexture)src).image, ((VKTexture)src).imageLayout, ((VKBuffer)dst).buffer, region);
			}
		}

		@Override
		public void bufferToImage(ZEBuffer src, int srcOffset, int srcRowLength, int srcHeight, ZETexture dst,
				Point3i dstPos, Point3i dstSize) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				VkImageSubresourceLayers subresource = VkImageSubresourceLayers.mallocStack(sp);
				VkBufferImageCopy.Buffer region = VkBufferImageCopy.mallocStack(1, sp);
				VkOffset3D offset = VkOffset3D.mallocStack(sp);
				VkExtent3D extent = VkExtent3D.mallocStack(sp);
				offset.set(dstPos.x, dstPos.y, dstPos.z);
				extent.set(dstSize.x, dstSize.y, dstSize.z);
				subresource.set(VK10.VK_IMAGE_ASPECT_COLOR_BIT, 0, 0, 1);
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
			
		}
		
	}
	
	public final VKRenderBackend backend;
	public final boolean rerecordable;
	private VkCommandBuffer commandBuffer;
	private VKCommandBufferRecorder recorder;
	private boolean recorded = false;
	
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
			VkCommandBufferInheritanceInfo inheritInfo = VkCommandBufferInheritanceInfo.mallocStack(sp);
			inheritInfo.set( // TODO Finish command buffer inherit info
					VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_INHERITANCE_INFO,
					0,
					0 /* */,
					0 /* */,
					0 /* */,
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
