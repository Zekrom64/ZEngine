package com.zekrom_64.ze.vulkan;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkAllocationCallbacks;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;

/** <p>A vulkan context contains common information between
 * different usages of a vulkan instance, ie. the
 * VkInstance object.</p>
 * 
 * <p>Objects created in different usages of the same context
 * are compatible with other usages of the same context (Ex.
 * a buffer created in one render backend can be used in
 * another render backend using the same context).</p>
 * 
 * @author Zekrom_64
 *
 */
public class VKContext {
	
	/** Builder class for {@link VKContext}.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static class Builder {
		
		/** Set containing required instance extensions. */
		public final Set<String> requiredExtensions = new HashSet<>();
		/** Set containing required instance layers. */
		public final Set<String> requiredLayers = new HashSet<>();
		/** Set containing optional instance extensions. */
		public final Set<String> optionalExtensions = new HashSet<>();
		/** Set containing optional instance layers. */
		public final Set<String> optionalLayers = new HashSet<>();
		private String engineName = "";
		private String appName = "";
		private int engineVer = 0;
		private int appVer = 0;
		private VkAllocationCallbacks allocCallbacks = null;
		private int apiVer = VK10.VK_API_VERSION_1_0;
		
		/** Builds the context with the current builder information.
		 * 
		 * @return New VKContext
		 * @throws VulkanException If the context could not be created
		 */
		public VKContext build() {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				int bp;
				
				ByteBuffer pAppName = sp.ASCII(appName);
				ByteBuffer pEngName = sp.ASCII(engineName);
				
				VkApplicationInfo appInfo = VkApplicationInfo.mallocStack(sp);
				appInfo.set(
						VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO,
						0,
						pAppName,
						appVer,
						pEngName,
						engineVer,
						apiVer
				);
				
				int err = VK10.VK_SUCCESS;
				
				bp = sp.getPointer();
				
				int[] propCount = new int[1];
				err = VK10.vkEnumerateInstanceExtensionProperties((ByteBuffer)null, propCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance extensions", err);
				VkExtensionProperties.Buffer bufExtensionProps = VkExtensionProperties.mallocStack(propCount[0], sp);
				err = VK10.vkEnumerateInstanceExtensionProperties((ByteBuffer)null, propCount, bufExtensionProps);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance extensions", err);
				
				Set<String> availableExtensions = new HashSet<>();
				while(bufExtensionProps.hasRemaining()) availableExtensions.add(bufExtensionProps.extensionNameString());
				
				sp.setPointer(bp);

				err = VK10.vkEnumerateInstanceLayerProperties(propCount, null);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to enumerate available instance layers", err);
				VkLayerProperties.Buffer bufLayerProps = VkLayerProperties.mallocStack(propCount[0], sp);
				err = VK10.vkEnumerateInstanceLayerProperties(propCount, bufLayerProps);
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
					throw new VulkanException("Missing extension(s) when creating Vulkan instance: " + sb.toString());
				}
				
				if (!availableLayers.containsAll(requiredLayers)) {
					layers.removeAll(availableLayers);
					StringBuilder sb = new StringBuilder();
					sb.append('[');
					for(String missingLyr : layers) sb.append(missingLyr).append(',');
					sb.deleteCharAt(sb.length() - 1);
					sb.append(']');
					throw new VulkanException("Missing layer(s) when creating Vulkan instance: " + sb.toString());
				}
				
				PointerBuffer ppExtensions = sp.mallocPointer(requiredExtensions.size());
				for(String ext : requiredExtensions) ppExtensions.put(sp.ASCII(ext));
				ppExtensions.flip();
				
				PointerBuffer ppLayers = sp.mallocPointer(requiredLayers.size());
				for(String lyr : requiredLayers) ppLayers.put(sp.ASCII(lyr));
				ppLayers.flip();
				
				VkInstanceCreateInfo instInfo = VkInstanceCreateInfo.mallocStack(sp);
				instInfo.set(
						VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO,
						0,
						0,
						appInfo,
						ppLayers,
						ppExtensions
				);
				
				PointerBuffer pInstance = sp.pointers(0);
				err = VK10.vkCreateInstance(instInfo, allocCallbacks, pInstance);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create Vulkan instance", err);
				
				VkInstance inst = new VkInstance(pInstance.get(), instInfo);
				
				return new VKContext(
						inst,
						Collections.unmodifiableSet(new HashSet<>(extensions)),
						Collections.unmodifiableSet(new HashSet<>(layers)),
						allocCallbacks
				);
			}
		}
		
		/** Adds a required extension for the context.
		 * 
		 * @param ext Required extension name
		 */
		public Builder addRequiredExtension(String ext) {
			requiredExtensions.add(ext);
			return this;
		}
		
		/** Adds a required layer for the context.
		 * 
		 * @param lyr Required layer name
		 */
		public Builder addRequiredLayer(String lyr) {
			requiredLayers.add(lyr);
			return this;
		}
		
		/** Adds an optional extension for the context.
		 * 
		 * @param ext Optional extension name
		 */
		public Builder addOptionalExtension(String ext) {
			optionalExtensions.add(ext);
			return this;
		}
		
		/** Adds an optional layer name for the context.
		 * 
		 * @param lyr Optional layer name
		 */
		public Builder addOptionalLayer(String lyr) {
			optionalLayers.add(lyr);
			return this;
		}
		
		/** Sets the engine name to use with the context.
		 * 
		 * @param engName Engine name
		 */
		public Builder setEngineName(String engName) {
			engineName = engName;
			return this;
		}
		
		/** Sets the application name to use with the context.
		 * 
		 * @param appName Application name
		 */
		public Builder setApplicationName(String appName) {
			this.appName = appName;
			return this;
		}
		
		/** Sets the engine version to use with the context.
		 * 
		 * @param major Major version number
		 * @param minor Minor version number
		 * @param patch Patch number
		 */
		public Builder setEngineVersion(int major, int minor, int patch) {
			engineVer = VK10.VK_VERSION_MAJOR(major) | VK10.VK_VERSION_MINOR(minor) | VK10.VK_VERSION_PATCH(patch);
			return this;
		}
		
		/** Sets the application version to use with the context.
		 * 
		 * @param major Major version number
		 * @param minor Minor version number
		 * @param patch Patch number
		 */
		public Builder setApplicationVersion(int major, int minor, int patch) {
			appVer = VK10.VK_VERSION_MAJOR(major) | VK10.VK_VERSION_MINOR(minor) | VK10.VK_VERSION_PATCH(patch);
			return this;
		}
		
		/** Sets the allocation callbacks to use with the context.
		 * 
		 * @param callbacks Allocation callbacks
		 */
		public Builder setAllocationCallbacks(VkAllocationCallbacks callbacks) {
			allocCallbacks = callbacks;
			return this;
		}
		
		/** Sets the Vulkan API version for the context to use.
		 * 
		 * @param apiVersion Vulkan API version
		 */
		public Builder setAPIVersion(int apiVersion) {
			apiVer = apiVersion;
			return this;
		}
		
	}

	/** The Vulkan instance */
	public final VkInstance instance;
	/** Read-only set containing the extensions in use. */
	public final Set<String> extensions;
	/** Read-only set containing the layers in use. */
	public final Set<String> layers;
	/** Allocation callbacks to use with the context. */
	public final VkAllocationCallbacks allocCallbacks;
	
	public VKContext(VkInstance instance, Set<String> exts, Set<String> lyrs, VkAllocationCallbacks allocCbk) {
		this.instance = instance;
		extensions = exts;
		layers = lyrs;
		allocCallbacks = allocCbk;
	}
	
}
