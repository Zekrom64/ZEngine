package com.zekrom_64.ze.nat.win.structs;

import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Struct;

/** The <b>BITMAPINFO</b> structure defines the dimensions and color information for a DIB.
 * 
 * @author Zekrom_64
 *
 */
@Struct(size=44)
public class BITMAPINFO extends com.zekrom_64.ze.nat.ZEStruct<BITMAPINFO> {

	/** A {@link BITMAPINFOHEADER} structure that contains information about the dimensions of color format. */
	@Field(0)
	public BITMAPINFOHEADER bmiHeader() {
		return io.getNativeObjectField(this, 0);
	}

	/** A {@link BITMAPINFOHEADER} structure that contains information about the dimensions of color format. */
	@Field(0)
	public void bmiHeader(BITMAPINFOHEADER bmiHeader) {
		io.setNativeObjectField(this, 0, bmiHeader);
	}
	
	/** The <b>bmiColors</b> member contains one of the following:
	 * <ul>
	 * <li>An array of {@link RGBQUAD}. The elements of the array that makes up the color table.</li>
	 * <li>An array of 16-bit unsigned integers that specifies indexes into the currently realized logical
	 * palette. This use of <b>bmiColors</b> is allowed for functions that use DIBs. When <b>bmiColors</b>
	 * elements contain indexes to a realized logical palette, tey must also call the following bitmap
	 * functions:<br/>
	 * {@link 
	 * </ul>
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Field(1)
	public Pointer<RGBQUAD> bmiColors() {
		return io.getTypedPointerField(this, 1);
	}
	
}
