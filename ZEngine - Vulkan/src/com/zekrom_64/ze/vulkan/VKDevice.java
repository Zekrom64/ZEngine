package com.zekrom_64.ze.vulkan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkLayerProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;

public class VKDevice {

	public static class Selector {
		
		public final VKContext instance;
		private VkPhysicalDevice bestDevice = null;
		private VkPhysicalDevice[] sortedDevices = new VkPhysicalDevice[0];
		
		private Selector(VKContext instance) {
			this.instance = instance;
		}
		
		/** Creates a device selector that sorts available devices by an arbitrary scoring
		 * function. The scoring function receives a handle to the {@link VkPhysicalDevice}
		 * to be scored, and returns a score. If the score is less than 0, the device
		 * is discarded. The best device is the highest scoring device.
		 * 
		 * @param instance Vulkan instance
		 * @param scoreFunc Device scoring function
		 * @return Device selector
		 */
		public static Selector byScore(VKContext instance, Function<VkPhysicalDevice,Double> scoreFunc) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				int[] bufCount = new int[1];
				int err = VK10.VK_SUCCESS;
				err = VK10.vkEnumeratePhysicalDevices(instance.instance, bufCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate physical devices", err);
				PointerBuffer pPhysicalDevices = sp.mallocPointer(bufCount[0]);
				err = VK10.vkEnumeratePhysicalDevices(instance.instance, bufCount, pPhysicalDevices);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate physical devices", err);
				
				ArrayList<Entry<VkPhysicalDevice,Double>> devices = new ArrayList<>();
				while (pPhysicalDevices.hasRemaining()) {
					VkPhysicalDevice dev = new VkPhysicalDevice(pPhysicalDevices.get(), instance.instance);
					devices.add(new SimpleEntry<>(dev, Double.valueOf(scoreFunc.apply(dev))));
				}
				
				devices.removeIf((Entry<VkPhysicalDevice,Double> dev) -> dev.getValue() < 0);
				
				Collections.sort(devices, (Entry<VkPhysicalDevice,Double> d1, Entry<VkPhysicalDevice,Double> d2) -> {
					return Double.compare(d1.getValue(), d2.getValue());
				});
				
				Selector sel = new Selector(instance);
				
				if (devices.size() > 0) {
					sel.bestDevice = devices.get(0).getKey();
					sel.sortedDevices = new VkPhysicalDevice[devices.size()];
					sel.sortedDevices[0] = sel.bestDevice;
					for(int i = 1; i < sel.sortedDevices.length; i++) sel.sortedDevices[i] = devices.get(i).getKey();
				}
				return sel;
			}
		}
		
		/** Creates a builder for the best scoring device.
		 * 
		 * @return Builder for best device
		 */
		public Builder buildBestDevice() {
			return new Builder(instance, bestDevice);
		}
		
		/** Creates a builder for one of the selected devices.
		 * 
		 * @param index Index of device to build
		 * @return Builder for device
		 */
		public Builder buildDevice(int index) {
			return new Builder(instance, sortedDevices[index]);
		}
		
		/** Gets the best scoring physical device.
		 * 
		 * @return Best scoring device
		 */
		public VkPhysicalDevice getBestDevice() {
			return bestDevice;
		}
		
		/** Gets a sorted array of all the selected devices, with higher scoring devices
		 * at lower indices.
		 * 
		 * @return Sorted array of all devices
		 */
		public VkPhysicalDevice[] getSortedDevices() {
			return Arrays.copyOf(sortedDevices, sortedDevices.length);
		}
		
	}
	
	public static class Builder {
		
		public final VKContext instance;
		public final VkPhysicalDevice physicalDevice;
		/** Set containing required device extensions. */
		public final Set<String> requiredExtensions = new HashSet<>();
		/** Set containing required device layers. */
		public final Set<String> requiredLayers = new HashSet<>();
		/** Set containing optional device extensions. */
		public final Set<String> optionalExtensions = new HashSet<>();
		/** Set containing optional device layers. */
		public final Set<String> optionalLayers = new HashSet<>();
		/** Device features to be enabled. */
		public final VkPhysicalDeviceFeatures deviceFeatures = VkPhysicalDeviceFeatures.malloc();
		
		private Map<Integer,List<Float>> queuePriorities = new HashMap<>();
		
		private Builder(VKContext instance, VkPhysicalDevice physicalDevice) {
			this.instance = instance;
			this.physicalDevice = physicalDevice;
		}
		
		public VKDevice build() {
			try(MemoryStack sp = MemoryStack.stackPush()) {
				int bp;
				
				int err = VK10.VK_SUCCESS;
				
				bp = sp.getPointer();
				
				int[] propCount = new int[1];
				err = VK10.vkEnumerateDeviceExtensionProperties(physicalDevice, (ByteBuffer)null, propCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance extensions", err);
				VkExtensionProperties.Buffer bufExtensionProps = VkExtensionProperties.mallocStack(propCount[0], sp);
				err = VK10.vkEnumerateDeviceExtensionProperties(physicalDevice, (ByteBuffer)null, propCount, bufExtensionProps);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance extensions", err);
				
				Set<String> availableExtensions = new HashSet<>();
				while(bufExtensionProps.hasRemaining()) availableExtensions.add(bufExtensionProps.extensionNameString());
				
				sp.setPointer(bp);

				err = VK10.vkEnumerateDeviceLayerProperties(physicalDevice, propCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance layers", err);
				VkLayerProperties.Buffer bufLayerProps = VkLayerProperties.mallocStack(propCount[0], sp);
				err = VK10.vkEnumerateDeviceLayerProperties(physicalDevice, propCount, bufLayerProps);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance layers", err);
				
				Set<String> availableLayers = new HashSet<>();
				while(bufLayerProps.hasRemaining()) availableLayers.add(bufLayerProps.layerNameString());
				
				sp.setPointer(bp);
				
				Set<String> extensions = new HashSet<>();
				Set<String> layers = new HashSet<>();
				
				extensions.addAll(optionalExtensions);
				layers.addAll(optionalLayers);
				
				extensions.retainAll(availableExtensions);
				layers.retainAll(availableLayers);
				
				extensions.addAll(requiredExtensions);
				layers.addAll(requiredLayers);
				
				if (!availableExtensions.containsAll(requiredExtensions)) {
					extensions.removeAll(availableExtensions);
					StringBuilder sb = new StringBuilder();
					sb.append('[');
					for(String missingExt : extensions) sb.append(missingExt).append(',');
					sb.deleteCharAt(sb.length() - 1);
					sb.append(']');
					throw new VulkanException("Missing extension(s) when creating Vulkan device: " + sb.toString());
				}
				
				if (!availableLayers.containsAll(requiredLayers)) {
					layers.removeAll(availableLayers);
					StringBuilder sb = new StringBuilder();
					sb.append('[');
					for(String missingLyr : layers) sb.append(missingLyr).append(',');
					sb.deleteCharAt(sb.length() - 1);
					sb.append(']');
					throw new VulkanException("Missing layer(s) when creating Vulkan device: " + sb.toString());
				}
				
				PointerBuffer ppExtensions = sp.mallocPointer(requiredExtensions.size());
				for(String ext : requiredExtensions) ppExtensions.put(sp.ASCII(ext));
				ppExtensions.flip();
				
				PointerBuffer ppLayers = sp.mallocPointer(requiredLayers.size());
				for(String lyr : requiredLayers) ppLayers.put(sp.ASCII(lyr));
				ppLayers.flip();
				
				VkDeviceQueueCreateInfo.Buffer pQueueInfos = VkDeviceQueueCreateInfo.mallocStack(queuePriorities.size(), sp);
				for(Entry<Integer,List<Float>> e : queuePriorities.entrySet()) {
					FloatBuffer pQueuePriorities = sp.mallocFloat(e.getValue().size());
					for(Float f : e.getValue()) pQueuePriorities.put(f);
					pQueuePriorities.rewind();
					pQueueInfos.get().set(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO, 0, 0, e.getKey(), pQueuePriorities);
				}
				pQueueInfos.rewind();
				
				VkDeviceCreateInfo createInfo = VkDeviceCreateInfo.mallocStack(sp);
				createInfo.set(
						VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO,
						0,
						0,
						pQueueInfos,
						ppLayers,
						ppExtensions,
						deviceFeatures
				);
				
				PointerBuffer pDevice = sp.mallocPointer(1);
				err = VK10.vkCreateDevice(physicalDevice, createInfo, instance.allocCallbacks, pDevice);
				
				return new VKDevice(instance, extensions, layers, new VkDevice(pDevice.get(), physicalDevice, createInfo), physicalDevice);
			}
		}
		
		/** Adds a required extension for the device.
		 * 
		 * @param ext Required extension name
		 */
		public Builder addRequiredExtension(String ext) {
			requiredExtensions.add(ext);
			return this;
		}
		
		/** Adds a required layer for the device.
		 * 
		 * @param lyr Required layer name
		 */
		public Builder addRequiredLayer(String lyr) {
			requiredLayers.add(lyr);
			return this;
		}
		
		/** Adds an optional extension for the device.
		 * 
		 * @param ext Optional extension name
		 */
		public Builder addOptionalExtension(String ext) {
			optionalExtensions.add(ext);
			return this;
		}
		
		/** Adds an optional layer name for the device.
		 * 
		 * @param lyr Optional layer name
		 */
		public Builder addOptionalLayer(String lyr) {
			optionalLayers.add(lyr);
			return this;
		}
		
		/** Enables a feature for the device.
		 * 
		 * @param name Name of feature in {@link VkPhysicalDeviceFeatures} to enable.
		 */
		public Builder enableFeature(String name) {
			try {
				Method m = VkPhysicalDeviceFeatures.class.getMethod(name, boolean.class);
				m.invoke(deviceFeatures, true);
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException("No such physical device feature \"" + name + "\"");
			} catch (SecurityException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
				throw new RuntimeException("Failed to reflect physical device features", e);
			}
			return this;
		}
		
		/** Adds an array of queue priorities for the given queue family index
		 * 
		 * @param queueFamilyIndex Queue family index
		 * @param priorities Logical queue priorities to add
		 */
		public Builder addQueuePriorities(int queueFamilyIndex, float ... priorities) {
			List<Float> qp = queuePriorities.get(Integer.valueOf(queueFamilyIndex));
			if (qp == null) {
				qp = new ArrayList<>();
				queuePriorities.put(Integer.valueOf(queueFamilyIndex), qp);
			}
			for(float pri : priorities) qp.add(Float.valueOf(pri));
			return this;
		}
		
	}
	
	public final VKContext instance;
	/** Immutable set containing all enabled device extensions. */
	public final Set<String> deviceExtensions;
	public final Set<String> deviceLayers;
	public final VkDevice logicalDevice;
	public final VkPhysicalDevice physicalDevice;
	
	public VKDevice(VKContext instance, Set<String> exts, Set<String> lyrs, VkDevice logicalDevice, VkPhysicalDevice physicalDevice) {
		this.instance = instance;
		deviceExtensions = exts;
		deviceLayers = lyrs;
		this.logicalDevice = logicalDevice;
		this.physicalDevice = physicalDevice;
	}
	
}
