package com.zekrom_64.ze.base.backend.render.shader;

public enum ZEUniformType {
	/** A custom type is a special value used for types not defined in this enum */
	CUSTOM,
	FLOAT,
	VEC2, VEC3, VEC4,
	MAT2, MAT3, MAT4,
	MAT2X3, MAT2X4,
	MAT3X2, MAT3X4,
	MAT4X2, MAT4X3,
	
	INT,
	IVEC2, IVEC3, IVEC4,
	IMAT2, IMAT3, IMAT4,
	IMAT2X3, IMAT2X4,
	IMAT3X2, IMAT3X4,
	IMAT4X2, IMAT4X3,
	
	UINT,
	UIVEC2, UIVEC3, UIVEC4,
	UIMAT2, UIMAT3, UIMAT4,
	UIMAT2X3, UIMAT2X4,
	UIMAT3X2, UIMAT3X4,
	UIMAT4X2, UIMAT4X3,
	
	DOUBLE,
	DVEC2, DVEC3, DVEC4,
	DMAT2, DMAT3, DMAT4,
	DMAT2X3, DMAT2X4,
	DMAT3X2, DMAT3X4,
	DMAT4X2, DMAT4X3,

	BOOL,
	BVEC2, BVEC3, BVEC4,
	/*
	BMAT2, BMAT3, BMAT4,
	BMAT2X3, BMAT2X4,
	BMAT3X2, BMAT3X4,
	BMAT4X2, BMAT4X3,
	*/
	
	SAMPLER1D,
	SAMPLER2D,
	SAMPLER3D,
	SAMPLER_CUBE,
	SAMPLER_BUFFER,
	SAMPLER_RECT,
	
	ISAMPLER1D,
	ISAMPLER2D,
	ISAMPLER3D,
	ISAMPLER_CUBE,
	ISAMPLER_BUFFER,
	ISAMPLER_RECT,
	
	UISAMPLER1D,
	UISAMPLER2D,
	UISAMPLER3D,
	UISAMPLER_CUBE,
	UISAMPLER_BUFFER,
	UISAMPLER_RECT
	
}