package com.zekrom_64.ze.base.util;

/** Enumeration of primitive types.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEPrimitiveType {

	/** Signed byte */
	BYTE(1),
	/** Unsigned byte */
	UBYTE(1, null),
	/** Signed short */
	SHORT(2),
	/** Unsigned short */
	USHORT(2, null),
	/** Signed integer */
	INT(4),
	/** Unsigned integer */
	UINT(4, null),
	/** Signed long */
	LONG(8),
	/** Unsigned long */
	ULONG(8, null),
	/** 32-bit single-precision floating point number */
	FLOAT(4, true),
	/** 64-bit double-precision floating point number */
	DOUBLE(8, true),
	/** Single boolean value stored in a byte*/
	BOOL(1),
	/** Single boolean value stored in a full integer */
	INT_BOOL(4);
	
	/** The size of the type in bytes */
	public final int size;
	/** If the type is a floating point number */
	public final boolean isFloating;
	/** If the type is unsigned */
	public final boolean isUnsigned;
	
	private ZEPrimitiveType(int s) {
		size = s;
		isFloating = false;
		isUnsigned = false;
	}
	
	private ZEPrimitiveType(int s, Void v) {
		size = s;
		isFloating = false;
		isUnsigned = true;
	}
	
	private ZEPrimitiveType(int s, boolean fp) {
		size = s;
		isFloating = fp;
		isUnsigned = false;
	}
	
}
