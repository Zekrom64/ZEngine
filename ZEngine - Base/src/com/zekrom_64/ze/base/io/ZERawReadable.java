package com.zekrom_64.ze.base.io;

import java.io.DataInput;
import java.io.IOException;

/** A ZERawReadable object can have its value read from a raw data input.
 * 
 * @author Zekrom_64
 *
 */
public interface ZERawReadable {

	/** Reads the value of this object from a data input.
	 * 
	 * @param input Data input
	 * @throws IOException If an IOException occurs
	 */
	public void rawRead(DataInput input) throws IOException;
	
}
