package com.zekrom_64.ze.vulkan;

import org.lwjgl.vulkan.VK10;

import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer.ZEBufferUsage;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture.ZETextureUsage;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineStage;

public class VKValues {

	public static int getVKFace_Stencil(ZEFrontBack face) {
		switch(face) {
		case BACK: return VK10.VK_STENCIL_FACE_BACK_BIT;
		case FRONT: return VK10.VK_STENCIL_FACE_FRONT_BIT;
		case FRONT_BACK: return VK10.VK_STENCIL_FRONT_AND_BACK;
		}
		return 0;
	}
	
	public static int getVKPipelineStage(ZEPipelineStage stage) {
		switch(stage) {
		case ALL: return VK10.VK_PIPELINE_STAGE_ALL_COMMANDS_BIT;
		case GRAPHICS: return VK10.VK_PIPELINE_STAGE_ALL_GRAPHICS_BIT;
		case HOST: return VK10.VK_PIPELINE_STAGE_HOST_BIT;
		
		case START_OF_PIPELINE: return VK10.VK_PIPELINE_STAGE_TOP_OF_PIPE_BIT;
		case DRAW_INDIRECT: return VK10.VK_PIPELINE_STAGE_DRAW_INDIRECT_BIT;
		case VERTEX_INPUT: return VK10.VK_PIPELINE_STAGE_VERTEX_INPUT_BIT;
		case VERTEX_SHADER: return VK10.VK_PIPELINE_STAGE_VERTEX_SHADER_BIT;
		case TESSELLATION_CONTROL_SHADER: return VK10.VK_PIPELINE_STAGE_TESSELLATION_CONTROL_SHADER_BIT;
		case TESSELLATION_EVALUATION_SHADER: return VK10.VK_PIPELINE_STAGE_TESSELLATION_EVALUATION_SHADER_BIT;
		case GEOMETRY_SHADER: return VK10.VK_PIPELINE_STAGE_GEOMETRY_SHADER_BIT;
		case FRAGMENT_SHADER: return VK10.VK_PIPELINE_STAGE_FRAGMENT_SHADER_BIT;
		case EARLY_FRAGMENT_TEST: return VK10.VK_PIPELINE_STAGE_EARLY_FRAGMENT_TESTS_BIT;
		case LATE_FRAGMENT_TEST: return VK10.VK_PIPELINE_STAGE_LATE_FRAGMENT_TESTS_BIT;
		case COLOR_OUTPUT: return VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT;
		case END_OF_PIPELINE: return VK10.VK_PIPELINE_STAGE_BOTTOM_OF_PIPE_BIT;
		}
		return 0;
	}
	
	public static int getVKPipelineStages(ZEPipelineStage[] stages) {
		int stage = 0;
		for(ZEPipelineStage s : stages) stage |= getVKPipelineStage(s);
		return stage;
	}
	
	public static int getVKBufferUsages(ZEBufferUsage[] usages) {
		int flags = 0;
		for(ZEBufferUsage u : usages) {
			switch(u) {
			case INDEX_BUFFER: flags |= VK10.VK_BUFFER_USAGE_INDEX_BUFFER_BIT; break;
			case INDIRECT_BUFFER: flags |= VK10.VK_BUFFER_USAGE_INDIRECT_BUFFER_BIT; break;
			case STORAGE_BUFFER: flags |= VK10.VK_BUFFER_USAGE_STORAGE_BUFFER_BIT; break;
			case STORAGE_TEXEL_BUFFER: flags |= VK10.VK_BUFFER_USAGE_STORAGE_TEXEL_BUFFER_BIT; break;
			case TRANSFER_DST: flags |= VK10.VK_BUFFER_USAGE_TRANSFER_DST_BIT; break;
			case TRANSFER_SRC: flags |= VK10.VK_BUFFER_USAGE_TRANSFER_SRC_BIT; break;
			case UNIFORM_BUFFER: flags |= VK10.VK_BUFFER_USAGE_UNIFORM_BUFFER_BIT; break;
			case UNIFORM_TEXEL_BUFFER: flags |= VK10.VK_BUFFER_USAGE_UNIFORM_TEXEL_BUFFER_BIT; break;
			case VERTEX_BUFFER: flags |= VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT; break;
			}
		}
		return flags;
	}
	
	public static int getVKImageUsages(ZETextureUsage[] usages) {
		int flags = 0;
		for(ZETextureUsage u : usages) {
			switch(u) {
			case COLOR_ATTACHMENT: flags |= VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
			case DEPTH_STENCIL_ATTACHMENT: flags |= VK10.VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT;
			case INPUT_ATTACHMENT: flags |= VK10.VK_IMAGE_USAGE_INPUT_ATTACHMENT_BIT;
			case SAMPLED: flags |= VK10.VK_IMAGE_USAGE_SAMPLED_BIT; break;
			case STORAGE: flags |= VK10.VK_IMAGE_USAGE_STORAGE_BIT; break;
			case TRANSFER_DST: flags |= VK10.VK_IMAGE_USAGE_TRANSFER_DST_BIT; break;
			case TRANSFER_SRC: flags |= VK10.VK_IMAGE_USAGE_TRANSFER_SRC_BIT; break;
			}
		}
		return flags;
	}
	
}
