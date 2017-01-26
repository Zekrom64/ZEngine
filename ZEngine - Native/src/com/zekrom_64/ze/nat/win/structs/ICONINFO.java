package com.zekrom_64.ze.nat.win.structs;

import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;
import com.zekrom_64.ze.nat.win.WinTypes.HBITMAP;

/** Contains information about an icon or a cursor
 * 
 * @author Zekrom_64
 *
 */
public class ICONINFO extends Struct<ICONINFO> {

	/** Specifies whether this structure defines an icon or a cursor. A value of <b>TRUE</b> specifies an icon; <b>FALSE</b>
	 * specifies a cursor.
	 */
	@Field(0)
	public int fIcon() {
		return io.getIntField(this, 0);
	}

	/** Specifies whether this structure defines an icon or a cursor. A value of <b>TRUE</b> specifies an icon; <b>FALSE</b>
	 * specifies a cursor.
	 */
	@Field(0)
	public void fIcon(int fIcon) {
		io.setIntField(this, 0, fIcon);
	}

	/** The x-coordinate of a cursor's hot spot. If this structure defines an icon, the hot spot is always in the center of the icon,
	 * and this member is ignored.
	 */
	@Field(1)
	public int xHotspot() {
		return io.getIntField(this, 1);
	}

	/** The x-coordinate of a cursor's hot spot. If this structure defines an icon, the hot spot is always in the center of the icon,
	 * and this member is ignored.
	 */
	@Field(1)
	public void xHotspot(int xHotspot) {
		io.setIntField(this, 1, xHotspot);
	}

	/** The y-coordinate of a cursor's hot spot. If this structure defines an icon, the hot spot is always in the center of the icon,
	 * and this member is ignored.
	 */
	@Field(2)
	public int yHotspot() {
		return io.getIntField(this, 2);
	}

	/** The y-coordinate of a cursor's hot spot. If this structure defines an icon, the hot spot is always in the center of the icon,
	 * and this member is ignored.
	 */
	@Field(2)
	public void yHotspot(int yHotspot) {
		io.setIntField(this, 2, yHotspot);
	}

	/** The icon bitmask bitmap. If this structure defines a black and white icon, this bitmask is formatted so that the upper half is
	 * the icon AND bitmask and the lower half is the icon XOR bitmask. Under this condition, the height should be and even multiple
	 * of two. If this structure defines a color icon, this mask only defines the AND bitmask of the icon.
	 */
	@Field(3)
	public Pointer<HBITMAP> hbmMask() {
		return io.getPointerField(this, 3);
	}

	/** The icon bitmask bitmap. If this structure defines a black and white icon, this bitmask is formatted so that the upper half is
	 * the icon AND bitmask and the lower half is the icon XOR bitmask. Under this condition, the height should be and even multiple
	 * of two. If this structure defines a color icon, this mask only defines the AND bitmask of the icon.
	 */
	@Field(3)
	public void hbmMask(Pointer<HBITMAP> hbmMask) {
		io.setPointerField(this, 3, hbmMask);
	}

	/** A handle to the icon color bitmap. This member can be optional if this structure defines a black and white icon. The AND bitmask
	 * of <b>hbmMask</b> is applied with the <b>SRCAND</b> flag to the destination; subsequently, the color bitmap is applied (using
	 * XOR) to the destination by using the <b>SRCINVERT</b> flag.
	 */
	@Field(4)
	public Pointer<HBITMAP> hbmColor() {
		return io.getPointerField(this, 4);
	}

	/** A handle to the icon color bitmap. This member can be optional if this structure defines a black and white icon. The AND bitmask
	 * of <b>hbmMask</b> is applied with the <b>SRCAND</b> flag to the destination; subsequently, the color bitmap is applied (using
	 * XOR) to the destination by using the <b>SRCINVERT</b> flag.
	 */
	@Field(4)
	public void hbmColor(Pointer<HBITMAP> hbmColor) {
		io.setPointerField(this, 4, hbmColor);
	}
	
}
