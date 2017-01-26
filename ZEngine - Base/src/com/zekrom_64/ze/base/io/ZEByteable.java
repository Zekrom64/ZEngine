package com.zekrom_64.ze.base.io;

/** A byteable object is one that can be converted to a byte array. In order to be reconstructed from
 * the byte array, the object's class is saved and then a new instance is created using a constructor
 * with a byte array argument. The constructor may throw an IOException if an exception occurs when
 * reading the byte array.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEByteable {

	/** Converts this object to an array of bytes. This byte array must be usable
	 * in a constructor to exactly recreate the object.
	 * 
	 * @return Byte value of object
	 */
	public byte[] toBytes();
	
}
