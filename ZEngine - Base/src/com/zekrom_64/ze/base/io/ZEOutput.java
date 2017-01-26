package com.zekrom_64.ze.base.io;

import java.io.IOException;
import java.io.OutputStream;

/** Interface for objects that can be written to using an OutputStream.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEOutput {

	/** Attempts to open an OutputStream from this object. If a problem occurs
	 * opening the stream, an IOException should be thrown. <b>This method
	 * should never return null!</b>
	 * 
	 * @return The opened stream
	 */
	public OutputStream openOutput() throws IOException ;
	
}
