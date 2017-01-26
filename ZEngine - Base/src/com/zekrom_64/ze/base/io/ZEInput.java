package com.zekrom_64.ze.base.io;

import java.io.IOException;
import java.io.InputStream;

/** Interface for objects that can be read from using an InputStream.
 * 
 * @author Zekrom_64
 *
 */
@FunctionalInterface
public interface ZEInput {

	/** Attempts to open an InputStream from this object. If a problem occurs
	 * opening the stream, an IOException should be thrown. <b>This method
	 * should never return null!</b>
	 * 
	 * @return The opened stream
	 */
	public InputStream openInput() throws IOException ;
	
}
