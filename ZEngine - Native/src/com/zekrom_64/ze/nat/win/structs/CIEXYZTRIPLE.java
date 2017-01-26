package com.zekrom_64.ze.nat.win.structs;

import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

/** The <b>CIEXYZTRIPLE</b> structure contains the <i>x</i>,<i>y</i>, and <i>z</i> coordinates of the three colors that correspond
 * to the red, green, and blue endpoints for a specified logical color space.
 * 
 * @author Zekrom_64
 *
 */
public class CIEXYZTRIPLE extends Struct<CIEXYZTRIPLE> {

	/** The xyz coordinates of red endpoint.
	 */
	@Field(0)
	public CIEXYZ ciexyzRed() {
		return io.getNativeObjectField(this, 0);
	}

	/** The xyz coordinates of red endpoint.
	 */
	@Field(0)
	public void ciexyzRed(CIEXYZ ciexyzRed) {
		io.setNativeObjectField(this, 0, ciexyzRed);
	}

	/** The xyz coordinates of green endpoint.
	 */
	@Field(1)
	public CIEXYZ ciexyzGreen() {
		return io.getNativeObjectField(this, 1);
	}

	/** The xyz coordinates of green endpoint.
	 */
	@Field(1)
	public void ciexyzGreen(CIEXYZ ciexyzGreen) {
		io.setNativeObjectField(this, 1, ciexyzGreen);
	}

	/** The xyz coordinates of blue endpoint.
	 */
	@Field(2)
	public CIEXYZ ciexyzBlue() {
		return io.getNativeObjectField(this, 2);
	}

	/** The xyz coordinates of blue endpoint.
	 */
	@Field(2)
	public void ciexyzBlue(CIEXYZ ciexyzBlue) {
		io.setNativeObjectField(this, 2, ciexyzBlue);
	}
	
}
