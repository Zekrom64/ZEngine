package com.zekrom_64.ze.nat.lin;

import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

/**
 * Visual structure; contains information about colormapping possible.
 */
public class Visual extends ZEStruct<Visual> {

	/** hook for extension to hang data */
	@Field(0)
	public Pointer<XExtData> ext_data() {
		return io.getPointerField(this, 0);
	}
	
	/** hook for extension to hang data */
	@Field(0)
	public void ext_data(Pointer<XExtData> ext_data) {
		io.setPointerField(this, 0, ext_data);
	}
	
	/** visual id of this visual */
	@Field(1)
	public long visualid() {
		return io.getCLongField(this, 1);
	}
	
	/** visual id of this visual */
	@Field(1)
	public void visualid(long visualid) {
		io.setCLongField(this, 1, visualid);
	}
	
	/** C++ class of screen (monochrome, etc.) */
	@Field(2)
	public int c_class() {
		return io.getIntField(this, 3);
	}
	
	/** C++ class of screen (monochrome, etc.) */
	@Field(2)
	public void c_class(int c_class) {
		io.setIntField(this, 2, c_class);
	}
	
	/** mask values */
	@Field(3)
	public long red_mask() {
		return io.getCLongField(this, 3);
	}
	
	/** mask values */
	@Field(3)
	public void red_mask(long red_mask) {
		io.setCLongField(this, 3, red_mask);
	}
	
	/** mask values */
	@Field(4)
	public long green_mask() {
		return io.getCLongField(this, 4);
	}
	
	/** mask values */
	@Field(4)
	public void green_mask(long green_mask) {
		io.setCLongField(this, 4, green_mask);
	}
	
	/** mask values */
	@Field(5)
	public long blue_mask() {
		return io.getCLongField(this, 5);
	}
	
	/** mask values */
	@Field(5)
	public void blue_mask(long blue_mask) {
		io.setCLongField(this, 5, blue_mask);
	}
	
	/** log base 2 of distinct color values */
	@Field(6)
	public int bits_per_rgb() {
		return io.getIntField(this, 6);
	}
	
	/** log base 2 of distinct color values */
	@Field(6)
	public void bits_per_rgb(int bits_per_rgb) {
		io.setIntField(this, 6, bits_per_rgb);
	}
	
	/** color map entries */
	@Field(7)
	public int map_entries() {
		return io.getIntField(this, 7);
	}
	
	/** color map entries */
	@Field(7)
	public void map_entries(int map_entries) {
		io.setIntField(this, 7, map_entries);
	}
	
}
