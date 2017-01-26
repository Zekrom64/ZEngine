package com.zekrom_64.ze.base.io;

/** Miscellaneous file utilities
 * 
 * @author Zekrom_64
 *
 */
public class ZEFileUtil {

	/** Gets the extension of a file given its name.
	 * 
	 * @param name File name
	 * @return File extension
	 */
	public static String getExtension(String name) {
		int idx = name.replace('\\','/').lastIndexOf('/');
		return name.substring(idx);
	}
	
}
