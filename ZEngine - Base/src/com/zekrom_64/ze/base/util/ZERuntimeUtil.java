package com.zekrom_64.ze.base.util;

import java.io.File;
import java.net.URL;
import java.security.CodeSource;

public class ZERuntimeUtil {
	
	/** Binds the lwjgl native directory to the standard native directory ("natives" folder in the
	 * runtime directory).
	 * 
	 */
	public static void bindLWJGLNatives() {
		char fs = File.separatorChar;
		String userdir = System.getProperty("user.dir");
		String nativedir = userdir + fs + "native";
		String libpath = System.getProperty("java.library.path");
		libpath = libpath != null ? (libpath + ';' + nativedir) : nativedir;
		System.setProperty("java.library.path", libpath);
		System.setProperty("org.lwjgl.librarypath", nativedir);
	}
	
	public static boolean isInJar(Class<?> clazz) {
		CodeSource src = clazz.getProtectionDomain().getCodeSource();
		if (src==null) return false;
		URL srcUrl = src.getLocation();
		return srcUrl != null ? srcUrl.toString().startsWith("jar:") : false;
	}
	
}
