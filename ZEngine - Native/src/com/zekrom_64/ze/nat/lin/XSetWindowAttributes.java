package com.zekrom_64.ze.nat.lin;

import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

/**
 * Data structure for setting window attributes.
 */
public class XSetWindowAttributes extends Struct<XSetWindowAttributes> {

	/** background or None or ParentRelative */
	@Field(0)
	public long background_pixmap() {
		return io.getCLongField(this, 0);
	}

	/** background or None or ParentRelative */
	@Field(0)
	public void background_pixmap(long background_pixmap) {
		io.setCLongField(this, 0, background_pixmap);
	}

	/** background pixel */
	@Field(1)
	public long background_pixel() {
		return io.getCLongField(this, 1);
	}

	/** background pixel */
	@Field(1)
	public void background_pixel(long background_pixel) {
		io.setCLongField(this, 1, background_pixel);
	}

	/** border of the window */
	@Field(2)
	public long border_pixmap() {
		return io.getCLongField(this, 2);
	}

	/** border of the window */
	@Field(2)
	public void border_pixmap(long border_pixmap) {
		io.setCLongField(this, 2, border_pixmap);
	}

	/** border pixel value */
	@Field(3)
	public long border_pixel() {
		return io.getCLongField(this, 3);
	}

	/** border pixel value */
	@Field(3)
	public void border_pixel(long border_pixel) {
		io.setCLongField(this, 3, border_pixel);
	}

	/** one of bit gravity values */
	@Field(4)
	public int bit_gravity() {
		return io.getIntField(this, 4);
	}

	/** one of bit gravity values */
	@Field(4)
	public void bit_gravity(int bit_gravity) {
		io.setIntField(this, 4, bit_gravity);
	}

	/** one of the window gravity values */
	@Field(5)
	public int win_gravity() {
		return io.getIntField(this, 5);
	}

	/** one of the window gravity values */
	@Field(5)
	public void win_gravity(int win_gravity) {
		io.setIntField(this, 5, win_gravity);
	}

	/** NotUseful, WhenMapped, Always */
	@Field(6)
	public int backing_store() {
		return io.getIntField(this, 6);
	}

	/** NotUseful, WhenMapped, Always */
	@Field(6)
	public void backing_store(int backing_store) {
		io.setIntField(this, 6, backing_store);
	}

	/** planes to be preseved if possible */
	@Field(7)
	public long backing_planes() {
		return io.getCLongField(this, 7);
	}

	/** planes to be preseved if possible */
	@Field(7)
	public void backing_planes(long backing_planes) {
		io.setCLongField(this, 7, backing_planes);
	}

	/** value to use in restoring planes */
	@Field(8)
	public long backing_pixel() {
		return io.getCLongField(this, 8);
	}

	/** value to use in restoring planes */
	@Field(8)
	public void backing_pixel(long backing_pixel) {
		io.setCLongField(this, 8, backing_pixel);
	}

	/** should bits under be saved? (popups) */
	@Field(9)
	public int save_under() {
		return io.getIntField(this, 9);
	}

	/** should bits under be saved? (popups) */
	@Field(9)
	public void save_under(int save_under) {
		io.setIntField(this, 9, save_under);
	}

	/** set of events that should be saved */
	@Field(10)
	public long event_mask() {
		return io.getCLongField(this, 10);
	}

	/** set of events that should be saved */
	@Field(10)
	public void event_mask(long event_mask) {
		io.setCLongField(this, 10, event_mask);
	}
	
	/** set of events that should not propagate */
	@Field(11)
	public long do_not_propagate_mask() {
		return io.getCLongField(this, 11);
	}

	/** set of events that should not propagate */
	@Field(11)
	public void do_not_propagate_mask(long do_not_propagate_mask) {
		io.setCLongField(this, 11, do_not_propagate_mask);
	}

	/** boolean value for override-redirect */
	@Field(12)
	public int override_redirect() {
		return io.getIntField(this, 12);
	}

	/** boolean value for override-redirect */
	@Field(12)
	public void override_redirect(int override_redirect) {
		io.setIntField(this, 12, override_redirect);
	}

	/** color map to be associated with window */
	@Field(13)
	public long colormap() {
		return io.getCLongField(this, 13);
	}

	/** color map to be associated with window */
	@Field(13)
	public void colormap(long colormap) {
		io.setCLongField(this, 13, colormap);
	}

	/** cursor to be displayed (or None) */
	@Field(14)
	public long cursor() {
		return io.getCLongField(this, 14);
	}

	/** cursor to be displayed (or None) */
	@Field(14)
	public void cursor(long cursor) {
		io.setCLongField(this, 14, cursor);
	}
	
}
