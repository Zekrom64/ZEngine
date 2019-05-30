package com.zekrom_64.ze.base.util;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;

/** Collection of various utilities for manipulating or inspecting the
 * application's runtime state.
 * 
 * @author Zekrom_64
 *
 */
public class ZERuntimeUtil {
	
	/** Binds the lwjgl native directory to the standard native directory ("natives" folder in the
	 * runtime directory).
	 * 
	 * @deprecated LWJGL will now automatically setup native libraries
	 */
	@Deprecated
	public static void bindLWJGLNatives() {
		char fs = File.separatorChar;
		String userdir = System.getProperty("user.dir");
		String nativedir = userdir + fs + "native";
		String libpath = System.getProperty("java.library.path");
		libpath = libpath != null ? (libpath + ';' + nativedir) : nativedir;
		System.setProperty("java.library.path", libpath);
		System.setProperty("org.lwjgl.librarypath", nativedir);
	}
	
	/** Tests if the given class was loaded from a JAR file by
	 * checking its code source.
	 * 
	 * @param clazz Class to test
	 * @return If the class was loaded from a JAR
	 */
	public static boolean isInJar(Class<?> clazz) {
		CodeSource src = clazz.getProtectionDomain().getCodeSource();
		if (src==null) return false;
		URL srcUrl = src.getLocation();
		return srcUrl != null ? srcUrl.toString().startsWith("jar:") : false;
	}
	
}
