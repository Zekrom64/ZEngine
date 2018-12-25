package com.zekrom_64.ze.vulkan.objects;

import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;

import com.zekrom_64.ze.base.backend.render.obj.ZERenderEvent;

/** Vulkan implementation of a {@link ZERenderEvent}.
 * 
 * @author Zekrom_64
 *
 */
public class VKRenderEvent implements ZERenderEvent {

	public final long event;
	private VkDevice device;
	
	public VKRenderEvent(long e, VkDevice dev) {
		event = e;
		device = dev;
	}
	
	@Override
	public boolean isSet() {
		return VK10.vkGetEventStatus(device, event) == VK10.VK_TRUE;
	}

	@Override
	public void set() {
		VK10.vkSetEvent(device, event);
	}

	@Override
	public void reset() {
		VK10.vkResetEvent(device, event);
	}

}
