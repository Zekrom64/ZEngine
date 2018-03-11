package com.zekrom_64.ze.vulkan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

public class VKLogicalDevice {

	public static class Selector {
		
		public final VKContext instance;
		private VkPhysicalDevice bestDevice = null;
		private VkPhysicalDevice[] sortedDevices = new VkPhysicalDevice[0];
		
		private Selector(VKContext instance) {
			this.instance = instance;
		}
		
		public static Selector byScore(VKContext instance, Function<Long,Double> scoreFunc) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				int[] bufCount = new int[1];
				int err = VK10.VK_SUCCESS;
				err = VK10.vkEnumeratePhysicalDevices(instance.instance, bufCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate physical devices", err);
				PointerBuffer pPhysicalDevices = sp.mallocPointer(bufCount[0]);
				err = VK10.vkEnumeratePhysicalDevices(instance.instance, bufCount, pPhysicalDevices);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate physical devices", err);
				
				ArrayList<Long> devices = new ArrayList<>();
				while (pPhysicalDevices.hasRemaining()) devices.add(Long.valueOf(pPhysicalDevices.get()));
				
				devices.removeIf((Long dev) -> scoreFunc.apply(dev) < 0);
				
				Collections.sort(devices, (Long d1, Long d2) -> {
					return Double.compare(scoreFunc.apply(d1), scoreFunc.apply(d2));
				});
				
				Selector sel = new Selector(instance);
				
				if (devices.size() > 0) {
					sel.bestDevice = new VkPhysicalDevice(devices.get(0), instance.instance);
					sel.sortedDevices = new VkPhysicalDevice[devices.size()];
					sel.sortedDevices[0] = sel.bestDevice;
					for(int i = 1; i < sel.sortedDevices.length; i++) sel.sortedDevices[i] =
							new VkPhysicalDevice(devices.get(i), instance.instance);
				}
				return sel;
			}
		}
		
		public Builder buildBestDevice() {
			return new Builder(instance, bestDevice);
		}
		
		public Builder buildDevice(int index) {
			return new Builder(instance, sortedDevices[index]);
		}
		
		public VkPhysicalDevice getBestDevice() {
			return bestDevice;
		}
		
		public VkPhysicalDevice[] getSortedDevices() {
			return Arrays.copyOf(sortedDevices, sortedDevices.length);
		}
		
	}
	
	public static class Builder {
		
		public final VKContext instance;
		public final VkPhysicalDevice physicalDevice;
		private Set<String> requiredExtensions = new HashSet<>();
		private Set<String> requiredLayers = new HashSet<>();
		private Set<String> optionalExtensions = new HashSet<>();
		private Set<String> optionalLayers = new HashSet<>();
		private VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.malloc();
		
		private Builder(VKContext instance, VkPhysicalDevice physicalDevice) {
			this.instance = instance;
			this.physicalDevice = physicalDevice;
		}
		
		public VKLogicalDevice build() {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				
				VkDeviceCreateInfo createInfo = VkDeviceCreateInfo.mallocStack(sp);
				createInfo.set(
						VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO,
						0,
						0,
						null,
						null,
						null,
						deviceFeatures
				);
			}
		}
		
	}
	
	public final VKContext instance;
	public final Set<String> deviceExtensions;
	public final Set<String> deviceLayers;
	public final VkDevice logicalDevice;
	public final long physicalDevice;
	
	public VKLogicalDevice(VKContext instance, Set<String> exts, Set<String> lyrs, VkDevice logicalDevice, long physicalDevice) {
		this.instance = instance;
		deviceExtensions = exts;
		deviceLayers = lyrs;
		this.logicalDevice = logicalDevice;
		this.physicalDevice = physicalDevice;
	}
	
}
