package com.zekrom_64.ze.vulkan.objects;

import org.lwjgl.vulkan.VK10;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderFence;
import com.zekrom_64.ze.vulkan.VKDevice;
import com.zekrom_64.ze.vulkan.VulkanException;

public class VKFence implements ZERenderFence {

	public final VKDevice device;
	public final long fence;
	
	public VKFence(VKDevice device, long f) {
		this.device = device;
		fence = f;
	}

	@Override
	public boolean getStatus() {
		int err = VK10.vkGetFenceStatus(device.logicalDevice, fence);
		if (err == VK10.VK_SUCCESS) return true;
		if (err == VK10.VK_NOT_READY) return false;
		throw new VulkanException("Failed to get fence status", err);
	}

	@Override
	public void reset() {
		int err = VK10.vkResetFences(device.logicalDevice, fence);
		if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to reset fence", err);
	}

	@Override
	public boolean waitFence(long timeout) {
		int err = VK10.vkWaitForFences(device.logicalDevice, fence, false, timeout);
		if (err == VK10.VK_SUCCESS) return false;
		if (err == VK10.VK_TIMEOUT) return true;
		throw new VulkanException("Failed to wait for fence", err);
	}
	
}
