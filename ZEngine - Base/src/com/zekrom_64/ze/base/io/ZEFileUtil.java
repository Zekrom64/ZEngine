package com.zekrom_64.ze.base.io;

/** Miscellaneous file utilities
 * 
 * @author Zekrom_64
 *
 */
public class ZEFileUtil {

	/** Attempts to get the extension of a file given its name.
	 * 
	 * @param name File name
	 * @return File extension
	 */
	public static String getExtension(String name) {
		int dotIndex = name.lastIndexOf('.');
		if (name.replace('\\', '/').lastIndexOf('/') > dotIndex) return "";
		return name.substring(dotIndex);
	}
	
}
