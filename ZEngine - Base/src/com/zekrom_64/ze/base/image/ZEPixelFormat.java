package com.zekrom_64.ze.base.image;

import com.zekrom_64.ze.base.util.ZEPrimitiveType;

/** A pixel format describes how picture elements are laid out in memory. Unless
 * otherwise specified, the components are laid out in ascending byte and bit
 * locations in memory as they are left to right in the format name. Color
 * formats can contain <b><u>r</u>ed</b>, <b><u>g</u>reen</b>, <b><u>b</u>lue</b>,
 * and <b><u>a</u>lpha</b> components. Depth/stencil formats can contain
 * <b><u>d</u>epth</b> and <b><u>s</u>tencil</b> components.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEPixelFormat {

	/** An unknown pixel format, with no elements and a size of 0. */
	UNKNOWN(0,-1,-1,-1,-1,null),
	/** A format with a single <b>red</b> component that is an unsigned byte. */
	R8_UINT(1,0,-1,-1,-1,ZEPrimitiveType.UBYTE),
	/** A format with a single <b>red</b> component that is a signed byte. */
	R8_SINT(1,0,-1,-1,-1,ZEPrimitiveType.BYTE),
	/** A format with <b>red</b> and <b>green</b> components that are unsigned bytes. */
	R8G8_UINT(2,0,8,-1,-1,ZEPrimitiveType.UBYTE),
	/** A format with <b>red</b> and <b>green</b> components that are signed bytes. */
	R8G8_SINT(2,0,8,-1,-1,ZEPrimitiveType.BYTE),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components
	 * that are unsigned bytes. */
	R8G8B8_UINT(3,0,8,16,-1,ZEPrimitiveType.UBYTE),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components
	 * that are signed bytes. */
	R8G8B8_SINT(3,0,8,16,-1,ZEPrimitiveType.BYTE),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are unsigned bytes. */
	R8G8B8A8_UINT(4,0,8,16,24,ZEPrimitiveType.UBYTE),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are signed bytes. */
	R8G8B8A8_SINT(4,0,8,16,24,ZEPrimitiveType.BYTE),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b> components
	 * that are unsigned bytes.  Unlike {@link #R8G8B8A8_UINT} the order of the components
	 * is shifted in memory, starting with <b>alpha</b> instead of <b>red</b>. */
	A8R8G8B8_UINT(4,8,16,24,0,ZEPrimitiveType.UBYTE),
	/** A format with a single <b>red</b> component that is an unsigned short. */
	R16_UINT(2,0,-1,-1,-1,ZEPrimitiveType.USHORT),
	/** A format with a single <b>red</b> component that is a signed short. */
	R16_SINT(2,0,-1,-1,-1,ZEPrimitiveType.SHORT),
	/** A format with <b>red</b> and <b>green</b> components that are unsigned shorts. */
	R16G16_UINT(4,0,2,-1,-1,ZEPrimitiveType.USHORT),
	/** A format with <b>red</b> and <b>green</b> components that are signed shorts. */
	R16G16_SINT(4,0,2,-1,-1,ZEPrimitiveType.SHORT),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components
	 * that are unsigned shorts. */
	R16G16B16_UINT(6,0,2,4,-1,ZEPrimitiveType.USHORT),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components
	 * that are signed shorts. */
	R16G16B16_SINT(6,0,2,4,-1,ZEPrimitiveType.SHORT),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are unsigned shorts. */
	R16G16B16A16_UINT(8,0,2,4,6,ZEPrimitiveType.USHORT),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are signed shorts. */
	R16G16B16A16_SINT(8,0,2,4,6,ZEPrimitiveType.SHORT),
	/** A format with a single <b>red</b> component that is an unsigned int. */
	R32_UINT(4,0,-1,-1,-1,ZEPrimitiveType.UINT),
	/** A format with a single <b>red</b> component that is a signed int. */
	R32_SINT(4,0,-1,-1,-1,ZEPrimitiveType.INT),
	/** A format with <b>red</b> and <b>green</b> components that are unsigned ints. */
	R32G32_UINT(8,0,4,-1,-1,ZEPrimitiveType.UINT),
	/** A format with <b>red</b> and <b>green</b> components that are signed ints. */
	R32G32_SINT(8,0,4,-1,-1,ZEPrimitiveType.INT),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components that
	 * are unsigned ints. */
	R32G32B32_UINT(12,0,4,8,-1,ZEPrimitiveType.UINT),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components that
	 * are signed ints. */
	R32G32B32_SINT(12,0,4,8,-1,ZEPrimitiveType.INT),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are unsigned ints. */
	R32G32B32A32_UINT(16,0,4,8,12,ZEPrimitiveType.UINT),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are signed ints. */
	R32G32B32A32_SINT(16,0,4,8,12,ZEPrimitiveType.UINT),
	/** A format with a single <b>red</b> component that is a single-precision
	 * floating point number */
	R32_FLOAT(4,0,-1,-1,-1,ZEPrimitiveType.FLOAT),
	/** A format with <b>red</b> and <b>green</b> components that are single-precision
	 * floating point numbers */
	R32G32_FLOAT(8,0,32,-1,-1,ZEPrimitiveType.FLOAT),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components that
	 * are single-precision floating point numbers */
	R32G32B32_FLOAT(12,0,32,64,-1,ZEPrimitiveType.FLOAT),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are single-precision floating point numbers */
	R32G32B32A32_FLOAT(16,0,32,64,96,ZEPrimitiveType.FLOAT),
	/** A format with a single <b>red</b> component that is a single-precision
	 * floating point number */
	R64_FLOAT(8,0,-1,-1,-1,ZEPrimitiveType.DOUBLE),
	/** A format with <b>red</b> and <b>green</b> components that are double-precision
	 * floating point numbers */
	R64G64_FLOAT(16,0,64,-1,-1,ZEPrimitiveType.DOUBLE),
	/** A format with <b>red</b>, <b>green</b>, and <b>blue</b> components that
	 * are double-precision floating point numbers */
	R64G64B64_FLOAT(24,0,64,128,-1,ZEPrimitiveType.DOUBLE),
	/** A format with <b>red</b>, <b>green</b>, <b>blue</b>, and <b>alpha</b>
	 * components that are double-precision floating point numbers */
	R64G64B64A64_FLOAT(32,0,64,128,192,ZEPrimitiveType.DOUBLE),
	/** A format with a single <b>depth</b> component that is an unsigned short. */
	D16_UINT(16,0,-1,ZEPrimitiveType.USHORT,null),
	/** A format with a single <b>depth</b> component that is a single precision
	 * floating point number. */
	D32_FLOAT(32,0,-1,ZEPrimitiveType.FLOAT,null),
	/** A format with a 24-bit unsigned integer <b>depth</b> component and an
	 * 8-bit unsigned byte <b>stencil</b> component. */
	D24_UINT_S8_UINT(32,0,24,ZEPrimitiveType.UINT,ZEPrimitiveType.UBYTE),
	/** A format with a 32-bit single-precision floating point <b>depth</b> component
	 * and an 8-bit unsigned byte <b>stencil</b> component. */
	D32_FLOAT_S8_UINT(40,0,32,ZEPrimitiveType.FLOAT,ZEPrimitiveType.UBYTE),
	/** A format with a single <b>stencil</b> component that is an unsigned byte. */
	S8_UINT(8,-1,0,null,ZEPrimitiveType.UBYTE);
	
	/** The size of a pixel in bytes */
	public final int sizeOf;
	/** Bitwise offsets of each component from the start of a pixel */
	public final int redOffset, greenOffset, blueOffset, alphaOffset;
	/** The primtive type each component is composed of */
	public final ZEPrimitiveType elementType;
	/** Bitwise offsets of each component from the start of an element. */
	public final int depthOffset, stencilOffset;
	/** Depth and stencil element types. */
	public final ZEPrimitiveType depthType, stencilType;
	/** The number of components. */
	public final int elementCount;
	
	private ZEPixelFormat(int sz, int roff, int goff, int boff, int aoff, ZEPrimitiveType elemType) {
		sizeOf = sz;
		redOffset = roff;
		greenOffset = goff;
		blueOffset = boff;
		alphaOffset = aoff;
		elementType = elemType;
		
		elementCount =
				(roff != -1 ? 1 : 0) + 
				(goff != -1 ? 1 : 0) +
				(boff != -1 ? 1 : 0) +
				(aoff != -1 ? 1 : 0);
		
		depthOffset = -1;
		stencilOffset = -1;
		depthType = null;
		stencilType = null;
	}
	
	private ZEPixelFormat(int sz, int doff, int soff, ZEPrimitiveType dtype, ZEPrimitiveType stype) {
		sizeOf = sz;
		depthOffset = doff;
		stencilOffset = soff;
		depthType = dtype;
		stencilType = stype;
		
		elementCount =
				(doff != -1 ? 1 : 0) +
				(soff != -1 ? 1 : 0);
		
		redOffset = -1;
		greenOffset = -1;
		blueOffset = -1;
		alphaOffset = -1;
		elementType = null;
	}
	
}
