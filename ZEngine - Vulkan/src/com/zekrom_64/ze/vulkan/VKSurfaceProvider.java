package com.zekrom_64.ze.vulkan;

import java.util.List;

public interface VKSurfaceProvider {

	/** Gets the list of required Vulkan instance extensions for the provider.
	 * 
	 * @return List of required instance extensions
	 */
	public List<String> getRequiredInstanceExtensions();
	
	/** Creates a <tt>VkSurfaceKHR</tt> handle given a
	 * Vulkan context.
	 * 
	 * @param context Vulkan context
	 * @return VkSurfaceKHR
	 */
	public long createSurface(VKContext context);
	
}
