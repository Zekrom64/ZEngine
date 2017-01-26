package com.zekrom_64.ze.base.io;

import java.io.DataOutput;
import java.io.IOException;

/** A ZERawWritable object can write its value to a raw data output.
 * 
 * @author Zekrom_64
 *
 */
public interface ZERawWritable {

	/** Writes the value of this object to a data output.
	 * 
	 * @param output Data output
	 * @throws IOException If an IOException occurs
	 */
	public void rawWrite(DataOutput output) throws IOException;
	
}
