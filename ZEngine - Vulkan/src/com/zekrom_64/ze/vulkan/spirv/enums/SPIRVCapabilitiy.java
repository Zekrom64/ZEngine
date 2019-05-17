package com.zekrom_64.ze.vulkan.spirv.enums;

import java.util.ArrayList;

public enum SPIRVCapabilitiy {

	MATRIX,
	SHADER,
	GEOMETRY,
	TESSELLATION,
	ADDRESSES,
	LINKAGE,
	KERNEL,
	VECTOR16,
	FLOAT16_BUFFER,
	FLAT16,
	FLOAT64,
	INT64,
	INT64_ATOMICS,
	IMAGE_BASIC,
	IMAGE_READ_WRITE,
	IMAGE_MIPMAP,
	PIPES,
	GROUPS,
	DEVICE_ENQUEUE,
	LITERAL_SAMPLER,
	ATOMIC_STORAGE,
	INT16,
	TESSELLATION_POINT_SIZE,
	GEOMETRY_POINT_SIZE,
	IMAGE_GATHER_EXTENDED,
	STORAGE_IMAGE_MULTISAMPLE,
	UNIFORM_BUFFER_ARRAY_DYNAMIC_INDEXING,
	SAMPLED_IMAGE_ARRAY_DYNAMIC_INDEXING,
	CLIP_DISTANCE,
	CULL_DISTANCE,
	IMAGE_CUBE_ARRAY,
	SAMPLE_RATE_SHADING,
	IMAGE_RECT,
	SAMPLED_RECT,
	GENERIC_POINTER,
	INT8,
	INPUT_ATTACHMENT,
	SPARSE_RESIDENCY,
	MIN_LOD,
	SAMPLED1D,
	IMAGE1D,
	SAMPLED_CUBE_ARRAY,
	SAMPLED_BUFFER,
	IMAGE_BUFFER,
	IMAGE_MS_ARRAY,
	STORAGE_IMAGE_EXTENDED_FORMATS,
	IMAGE_QUERY,
	DERIVATIVE_CONTROL,
	INTERPOLATION_FUNCTION,
	TRANSFORM_FEEDBACK,
	GEOMETRY_STREAMS,
	STORAGE_IMAGE_READ_WITHOUT_FORMAT,
	STORAGE_IMAGE_WRITE_WITHOUT_FORMAT,
	MULI_VIEWPORT,
	
	SUBGROUP_DISPATCH,
	NAMED_BARRIER,
	PIPE_STORAGE,
	
	GROUP_NON_UNIFORM,
	GROUP_NON_UNIFORM_VOTE,
	GROUP_NON_UNIFORM_ARITHMETIC,
	GROUP_NON_UNIFORM_BALLOT,
	GROUP_NON_UNIFORM_SHUFFLE,
	GROUP_NON_UNIFORM_SHUFFLE_RELATIVE,
	GROUP_NON_UNIFORM_CLUSTERED,
	GROUP_NON_UNIFORM_QUAD,
	
	// SPV_KHR_shader_ballot
	SUBGROUP_BALLOT_KHR(4423),
	
	// SPV_KHR_shader_draw_parameters
	DRAW_PARAMETERS(4427),
	
	// SPV_KHR_subgroup_vote
	SUBGROUP_VOTE_KHR(4431),

	// SPV_KHR_16bit_storage
	STORAGE_BUFFER_16BIT_ACCESS(4433),
	UNIFORM_AND_STORAGE_BUFFER_16BIT_ACCESS(4434),
	STORAGE_PUSH_CONSTANT_16(4435),
	STORAGE_INPUT_OUTPUT_16(4436),
	
	// SPV_KHR_device_group
	DEVICE_GROUP(4437),
	
	// SPV_KHR_multiview
	MULTI_VIEW(4439),
	
	// SPV_KHR_variable_pointers
	VARIABLE_POINTERS_STORAGE_BUFFER(4441),
	VARIABLE_POINTERS(4442),
	
	// SPV_KHR_shader_atomic_counter_ops
	ATOMIC_STORAGE_OPS(4445),
	
	// SPV_KHR_post_depth_coverage
	SAMPLE_MASK_POST_DEPTH_COVERAGE(4447),
	
	// SPV_KHR_8bit_storage
	STORAGE_BUFFER_8BIT_ACCESS(4448),
	UNIFORM_AND_STORAGE_BUFFER_8BIT_ACCESS(4449),
	STORAGE_PUSH_CONSTANT8(4450),
	
	// SPV_KHR_float_controls
	DENORM_PRESERVE(4464),
	DENORM_FLUSH_TO_ZERO(4465),
	SIGNED_ZERO_INF_NAN_PRESERVE(4466),
	ROUNDING_MODE_RTE(4467),
	ROUNDING_MODE_RTZ(4468),
	
	// SPV_AMD_gpu_shader_half_float_fetch
	FLOAT16_IMAGE_AMD(5008),
	
	// SPV_AMD_texture_gather_bias_lod
	IMAGE_GATHER_BIAS_LOD_AMD(5009),
	
	// SPV_AMD_shader_fragment_mask
	FRAGMENT_MASK_AMD(5010),
	
	// SPV_EXT_shader_stencil_export
	STENCIL_EXPORT_EXT(5013),
	
	// SPV_AMD_shader_image_load_store_lod
	IMAGE_READ_WRITE_LOD_AMD(5015),
	
	// SPV_NV_sample_mask_override_coverage
	SAMPLE_MASK_OVERRIDE_COVERAGE_NV(5249),
	
	// SPV_NV_geometry_shader_passthrough
	GEOMETRY_SHADER_PASSTHROUGH_NV(5251),
	
	// SPV_EXT_shader_viewport_index_layer
	SHADER_VIEWPORT_INDEX_LAYER_EXT(5254),
	
	// SPV_NV_viewport_array2
	SHADER_VIEWPORT_MASK_NV(5255),
	
	// SPV_NV_stereo_view_rendering
	SHADER_STEREO_VIEW_NV(5259),
	
	// SPV_NVX_multiview_per_view_attributes
	PER_VIEW_ATTRIBUTES_NV(5260),
	
	// SPV_EXT_fragment_fully_covered
	FRAGMENT_FULLY_COVERED_EXT(5265),
	
	// SPV_NV_mesh_shader
	MESH_SHADING_NV(5266),
	
	// SPV_EXT_descriptor_indexing
	SHADER_NON_UNIFORM_EXT(5301),
	RUNTIME_DESCRIPTOR_ARRAY_EXT(5302),
	INPUT_ATTACHMENT_ARRAY_DYNAMIC_INDEXING_EXT(5303),
	UNIFORM_TEXEL_BUFFER_ARRAY_DYNAMIC_INDEXING_EXT(5304),
	STORAGE_TEXEL_BUFFER_ARRAY_DYNAMIC_INDEXING_EXT(5305),
	UNIFORM_BUFFER_ARRAY_NON_UNIFORM_INDEXING_EXT(5306),
	SAMPLED_IMAGE_ARRAY_NON_UNIFORM_INDEXING_EXT(5307),
	STORAGE_BUFFER_ARRAY_NON_UNIFORM_INDEXING_EXT(5308),
	STORAGE_IMAGE_ARRAY_NON_UNIFORM_INDEXING_EXT(5309),
	INPUT_ATTACHMENT_ARRAY_NON_UNIFORM_INDEXING_EXT(5310),
	UNIFORM_TEXEL_BUFFER_ARRAY_NON_UNIFORM_INDEXING_EXT(5311),
	STORAGE_TEXEL_BUFFER_ARRAY_NON_UNIFORM_INDEXING_EXT(5312),
	
	// SPV_NV_ray_tracing
	RAY_TRACING_NV(5340),
	
	// SPV_INTEL_subgroups
	SUBGROUP_SHUFFLE_INTEL(5568),
	SUBGROUP_BUFFER_BLOCK_IO_INTEL(5569),
	SUBGROUP_IMAGE_BLOCK_IO_INTEL(5570),
	
	// SPV_INTEL_media_block_io
	SUBGROUP_IMAGE_MEDIA_BLOCK_IO_INTEL(5579),
	
	// SPV_INTEL_device_side_avc_motion_estimation
	SUBGROUP_AVC_MOTION_ESTIMATION_INTEL(5696),
	SUBGROUP_AVC_MOTION_ESTIMATION_INTRA_INTEL(5697),
	SUBGROUP_AVC_MOTION_ESTIMATION_CHROMA_INTEL(5698),
	
	// SPV_NV_shader_subgroup_partitioned
	GROUP_NON_UNIFORM_PARTITIONED_NV(5297),
	
	// SPV_KHR_vulkan_memory_model
	VULKAN_MEMORY_MODEL_KHR(5345),
	VULKAN_MEMORY_MODEL_DEVICE_SCOPE_KHR(5346),
	
	// SPV_NV_shader_image_footprint
	IMAGE_FOOTPRINT_NV(5282),
	
	// SPV_NV_fragment_shader_barycentric
	FRAGMENT_BARYCENTRIC_NV(5284),
	
	// SPV_NV_compute_shader_derivatives
	COMPUTE_DERIVATIVE_GROUP_QUADS_NV(5288),
	COMPUTE_DERIVATIVE_GROUP_LINEAR_NV(5350),
	
	// SPV_EXT_fragment_invocation_density
	// SPV_NV_shading_rate
	FRAGMENT_DENSITY_EXT(5291),
	
	// SPV_EXT_physical_storage_buffer
	PHYSICAL_STORAGE_BUFFER_ADDRESSES_EXT(5347),
	
	// SPV_NV_cooperative_matrix
	COOPERATIVE_MATRIX_NV(5357);
	
	public final int value;
	
	private SPIRVCapabilitiy() {
		value = ordinal();
	}
	
	private SPIRVCapabilitiy(int val) {
		value = val;
	}
	
	private static SPIRVCapabilitiy[] linearValues;
	static {
		ArrayList<SPIRVCapabilitiy> vals = new ArrayList<>();
		for(SPIRVCapabilitiy e : values()) {
			if (e.ordinal() == e.value) {
				vals.ensureCapacity(e.value+1);
				vals.set(e.value, e);
			}
		}
		linearValues = vals.toArray(new SPIRVCapabilitiy[0]);
	}
	
	public static SPIRVCapabilitiy parse(int val) {
		if (val < 0) return null;
		if (val < linearValues.length) {
			SPIRVCapabilitiy eval = linearValues[val];
			if (eval != null) return eval;
		}
		for(SPIRVCapabilitiy eval : values()) if (eval.value == val) return eval;
		return null;
	}
	
}
