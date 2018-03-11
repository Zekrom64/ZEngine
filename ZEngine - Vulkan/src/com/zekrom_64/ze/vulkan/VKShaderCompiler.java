package com.zekrom_64.ze.vulkan;

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;

import com.zekrom_64.ze.base.backend.render.shader.ZEShader;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.vulkan.objects.VKShader;

public class VKShaderCompiler implements ZEShaderCompiler {
	
	public final VKRenderBackend backend;
	private boolean vk_ext_nv_glsl;
	
	public VKShaderCompiler(VKRenderBackend backend) {
		this.backend = backend;
	}

	@Override
	public ZEShader compileShader(Object src, String type, String shaderType) {
		if (type == null || SHADER_SOURCE_TYPE_SPIRV.equals(type)) {
			try (MemoryStack sp = MemoryStack.stackPush()) {
				ByteBuffer code = null;
				if (src instanceof int[]) {
					int[] intSrc = (int[])src;
					code = sp.malloc(intSrc.length * 4);
					code.asIntBuffer().put(intSrc);
				} else if (src instanceof IntBuffer) {
					IntBuffer intSrc = (IntBuffer)src;
					int pos = intSrc.position();
					code = sp.malloc(intSrc.remaining() * 4);
					code.asIntBuffer().put(intSrc);
					intSrc.position(pos);
				} else if (src instanceof IntStream) {
					IntStream intSrc = (IntStream)src;
					int[] ints = intSrc.toArray();
					code = sp.malloc(ints.length * 4);
					code.asIntBuffer().put(ints);
				} else if (src instanceof DataInput) {
					DataInput intSrc = (DataInput)src;
					int[] ints = StreamSupport.intStream(new Spliterator.OfInt() {
						
						@Override
						public long estimateSize() {
							return Long.MAX_VALUE;
						}
						
						@Override
						public int characteristics() {
							return IMMUTABLE;
						}
						
						@Override
						public OfInt trySplit() {
							return null;
						}
						
						@Override
						public boolean tryAdvance(IntConsumer action) {
							try {
								action.accept(intSrc.readInt());
								return true;
							} catch (EOFException e) {
								return false;
							} catch(IOException e) {
								throw new VulkanException("Caught exception when reading shader from data input", e);
							}
						}
						
					}, false).toArray();
					code = sp.malloc(ints.length * 4);
					code.asIntBuffer().put(ints);
				} else throw new VulkanException("Shader source object type \"" + (src != null ? src.getClass() : "null") + "\" not supported for SPIR-V shaders");
				
				VkShaderModuleCreateInfo createInfo = VkShaderModuleCreateInfo.mallocStack(sp);
				createInfo.set(
						VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
						0,
						0,
						code);
				
				long[] pShader = new long[1];
				int err = VK10.vkCreateShaderModule(backend.device.logicalDevice, createInfo, backend.context.allocCallbacks, pShader);
				if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create shader module", err);
				return new VKShader(pShader[0], shaderType);
			}
		} else if (SHADER_SOURCE_TYPE_GLSL.equals(type)) {
			if (vk_ext_nv_glsl) {
				String str = src.toString();
				try (MemoryStack sp = MemoryStack.stackPush()) {
					ByteBuffer text = sp.ASCII(str);
					VkShaderModuleCreateInfo createInfo = VkShaderModuleCreateInfo.mallocStack(sp);
					createInfo.set(
							VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO,
							0,
							0,
							text);
					
					long[] pShader = new long[1];
					int err = VK10.vkCreateShaderModule(backend.device.logicalDevice, createInfo, backend.context.allocCallbacks, pShader);
					if (err != VK10.VK_SUCCESS) throw new VulkanException("Failed to create shader module", err);
					return new VKShader(pShader[0], shaderType);
				}
			} // TODO: Implement glslangValidator into the backend eventually
		}
		throw new VulkanException("Shader type \"" + type + "\" not supported by the Vulkan backend");
	}

	@Override
	public ZEShaderProgram compileShaderProgram(ZEShader... modules) {
		Map<String,VKShader> shaders = new HashMap<>();
		for(ZEShader module : modules) {
			String modtype = module.getShaderType();
			if (shaders.containsKey(modtype)) throw new VulkanException("Duplicate shader of type \"" + modtype + "\" when creating shader program");
			shaders.put(modtype, (VKShader)module);
		}
		
		
	}

}
