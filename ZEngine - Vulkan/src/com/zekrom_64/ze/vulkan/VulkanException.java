package com.zekrom_64.ze.vulkan;

import org.lwjgl.vulkan.EXTDebugReport;
import org.lwjgl.vulkan.KHRDisplaySwapchain;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.NVGLSLShader;
import org.lwjgl.vulkan.VK10;

/** A Vulkan exception is an exception caused by some error
 * caused by or in relation to Vulkan.
 * 
 * @author Zekrom_64
 *
 */
public class VulkanException extends RuntimeException {

	private static final long serialVersionUID = 6730423186819842285L;

	/** Gets a string describing a Vulkan error code. If an appropriate
	 * string is not known, the string "<tt>Unknown error (0x<errorcode>)</tt>"
	 * is returned.
	 * 
	 * @param err Vulkan error code
	 * @return Descriptive string
	 */
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
		case KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR: return "Swapchain is no longer usable with display surface";
		case KHRDisplaySwapchain.VK_ERROR_INCOMPATIBLE_DISPLAY_KHR: return "Swapchain is incompatible with display";
		case EXTDebugReport.VK_ERROR_VALIDATION_FAILED_EXT: return "Validation failed";
		case NVGLSLShader.VK_ERROR_INVALID_SHADER_NV: return "Invalid GLSL shader (Nvidia)";
		}
		return "Unknown error (0x" + String.format("%08x", err) + ")";
	}
	
	/** Creates a Vulkan exception with the given message.
	 * 
	 * @param msg Error message
	 */
	public VulkanException(String msg) {
		super(msg);
	}
	
	/** Creates a Vulkan exception with a string describing the given
	 * error, retrieved using {@link #getExceptionString(int)}.
	 * 
	 * @param err Vulkan error code
	 */
	public VulkanException(int err) {
		super(getExceptionString(err));
	}
	
	/** Creates a Vulkan exception with the given message,
	 * appending a string describing the error retrieved
	 * using {@link #getExceptionString(int)}.
	 * 
	 * @param msg Error message
	 * @param err Vulkan error code
	 */
	public VulkanException(String msg, int err) {
		super(msg + ": " + getExceptionString(err));
	}
	
	/** Creates a Vulkan exception with the given message
	 * and a Throwable that caused this exception.
	 * 
	 * @param msg Error message
	 * @param cause Cause of exception
	 */
	public VulkanException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
