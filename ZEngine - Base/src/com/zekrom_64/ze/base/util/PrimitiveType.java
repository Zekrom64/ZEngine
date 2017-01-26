package com.zekrom_64.ze.base.util;

/** Enumeration of primitive types.
 * 
 * @author Zekrom_64
 *
 */
public enum PrimitiveType {

	BYTE(1),
	UBYTE(1),
	SHORT(2),
	USHORT(2),
	INT(4),
	UINT(4),
	LONG(8),
	ULONG(8),
	FLOAT(4),
	DOUBLE(8);
	
	public final int size;
	private PrimitiveType(int s) {
		size = s;
	}
	
}
