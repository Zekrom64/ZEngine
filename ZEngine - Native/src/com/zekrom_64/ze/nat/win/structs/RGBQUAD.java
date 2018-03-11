package com.zekrom_64.ze.nat.win.structs;

import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

/** The <b>RGBQUAD</b> structure describes a color consisting of relative intensities of red, green, and blue.
 * 
 * @author Zekrom_64
 *
 */
public class RGBQUAD extends ZEStruct<RGBQUAD> {

	/** The intensity of blue in the color. */
	@Field(0)
	public byte rgbBlue() {
		return io.getByteField(this, 0);
	}

	/** The intensity of blue in the color. */
	@Field(0)
	public void rgbBlue(byte rgbBlue) {
		io.setByteField(this, 0, rgbBlue);
	}

	/** The intensity of green in the color. */
	@Field(1)
	public byte rgbGreen() {
		return io.getByteField(this, 1);
	}

	/** The intensity of green in the color. */
	@Field(1)
	public void rgbGreen(byte rgbGreen) {
		io.setByteField(this, 1, rgbGreen);
	}

	/** The intensity of red in the color. */
	@Field(2)
	public byte rgbRed() {
		return io.getByteField(this, 2);
	}

	/** The intensity of red in the color. */
	@Field(2)
	public void rgbRed(byte rgbRed) {
		io.setByteField(this, 2, rgbRed);
	}

	/** This member is reserved and must be zero. */
	@Field(3)
	public byte rgbReserved() {
		return io.getByteField(this, 3);
	}

	/** This member is reserved and must be zero. */
	@Field(3)
	public void rgbReserved(byte rgbReserved) {
		io.setByteField(this, 3, rgbReserved);
	}
	
}
