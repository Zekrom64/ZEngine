package com.zekrom_64.ze.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import com.zekrom_64.ze.base.mem.ZEByteQueue;

/** Class for I/O utility methods.
 * 
 * @author Zekrom_64
 *
 */
public class ZEIOUtil {

	/** Reads all of the data from a reader and puts it into a string.
	 * 
	 * @param r Reader to read from
	 * @return The entire string
	 * @throws IOException If an IOException occurred
	 */
	public static String readFully(Reader r) throws IOException {
		StringBuffer buf = new StringBuffer();
		char[] buffer = new char[2048];
		int read = -1;
		while((read = r.read(buffer))!=-1) buf.append(buffer, 0, read);
		return buf.toString();
	}
	
	/** Reads all of the data from an input stream and puts it into a byte array
	 * 
	 * @param s Stream to read from
	 * @return The entire array of bytes
	 * @throws IOException If an IOException occurred
	 */
	public static byte[] readFully(InputStream s) throws IOException {
		ZEByteQueue queue = new ZEByteQueue();
		byte[] buffer = new byte[2048];
		int read = 0;
		while((read = s.read(buffer))!=-1) queue.put(buffer, 0, read);
		return queue.toByteArray();
	}
	
}
