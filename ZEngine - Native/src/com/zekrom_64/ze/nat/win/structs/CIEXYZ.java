
package com.zekrom_64.ze.nat.win.structs;

import org.bridj.ann.CLong;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

/** The <b>CIEXYZ</b> structure contains the <i>x</i>,<i>y</i>, and <i>z</i> coordinaztes of a specific color in a specified color space.
 * 
 * @author Zekrom_64
 *
 */
public class CIEXYZ extends Struct<CIEXYZ> {

	/** The x coordinate in fix point (2.30).
	 */
	@CLong
	@Field(0)
	public long ciexyzX() {
		return io.getCLongField(this, 0);
	}

	/** The x coordinate in fix point (2.30).
	 */
	@CLong
	@Field(0)
	public void ciexyzX(long ciexyzX) {
		io.setCLongField(this, 0, ciexyzX);
	}

	/** The y coordinate in fix point (2.30).
	 */
	@CLong
	@Field(1)
	public long ciexyzY() {
		return io.getCLongField(this, 1);
	}

	/** The y coordinate in fix point (2.30).
	 */
	@CLong
	@Field(1)
	public void ciexyzY(long ciexyzY) {
		io.setCLongField(this, 1, ciexyzY);
	}

	/** The z coordinate in fix point (2.30).
	 */
	@CLong
	@Field(2)
	public long ciexyzZ() {
		return io.getCLongField(this, 2);
	}

	/** The z coordinate in fix point (2.30).
	 */
	@CLong
	@Field(2)
	public void ciexyzZ(long ciexyzZ) {
		io.setCLongField(this, 2, ciexyzZ);
	}
}
