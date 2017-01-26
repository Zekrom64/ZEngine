package com.zekrom_64.ze.vulkan;

import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.KHRDisplaySwapchain;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.NVGLSLShader;
import org.lwjgl.vulkan.VK10;

public class VulkanException extends Exception {

	private static final long serialVersionUID = 6730423186819842285L;

	public static final String getExceptionString(int err) {
		switch(err) {
		case VK10.VK_ERROR_DEVICE_LOST: return "Device lost";
		case VK10.VK_ERROR_EXTENSION_NOT_PRESENT: return "Extension not present";
		case VK10.VK_ERROR_FEATURE_NOT_PRESENT: return "Feature not present";
		case VK10.VK_ERROR_FORMAT_NOT_SUPPORTED: return "Format not supported";
		case VK10.VK_ERROR_FRAGMENTED_POOL: return "Memory pool too fragmented";
		case VK10.VK_ERROR_INCOMPATIBLE_DRIVER: return "Vulkan version incompatible with driver";
		case VK10.VK_ERROR_INITIALIZATION_FAILED: return "Initialization failed";
		case VK10.VK_ERROR_LAYER_NOT_PRESENT: return "Layer not present";
		case VK10.VK_ERROR_MEMORY_MAP_FAILED: return "Memory map failed";
		case VK10.VK_ERROR_OUT_OF_DEVICE_MEMORY: return "Out of device memory";
		case VK10.VK_ERROR_OUT_OF_HOST_MEMORY: return "Out of host memory";
		case VK10.VK_ERROR_TOO_MANY_OBJECTS: return "Too many objects";
		case KHRSurface.VK_ERROR_NATIVE_WINDOW_IN_USE_KHR: return "Display surface native window already in use";
		case KHRSurface.VK_ERROR_SURFACE_LOST_KHR: return "Display surface lost";
		case KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR: return "Swapchain has become incompatible with display surface";
		case KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR: return "Swapchain is incompatible with display";
		case EXTDebugReport.VK_ERROR_VALIDATION_FAILED_EXT: return "Validation failed";
		case NVGLSLShader.VK_ERROR_INVALID_SHADER_NV: return "Invalid GLSL shader (Nvidia)";
		}
		return "Unknown error (0x" + Integer.toHexString(err) + ")";
	}
	
	public VulkanException(int err) {
		super(getExceptionString(err));
	}
	
	public VulkanException(int err, String msg) {
		super(getExceptionString(err) + ": " + msg);
	}
	
}
