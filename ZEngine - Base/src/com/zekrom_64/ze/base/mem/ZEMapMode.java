package com.zekrom_64.ze.base.mem;

/** A map mode specifies how memory should be mapped for modification.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEMapMode {

	/** The mapped memory will only be read from */
	READ_ONLY,
	/** The mapped memory will only be written to */
	WRITE_ONLY,
	/** The mapped memory will be read from and written to */
	READ_WRITE
	
}
