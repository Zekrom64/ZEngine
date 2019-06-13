package com.zekrom_64.ze.lwjgl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.lwjgl.system.Platform;

public class LWJGLU3 {
	
	/** Name for LWJGL native library */
	public static final String NATIVES_LWJGL = "lwjgl";
	/** Name for GLFW native library */
	public static final String NATIVES_GLFW = "glfw";
	/** Name for STB native library */
	public static final String NATIVES_STB = "lwjgl_stb";
	
	/** Extracts the given native library and loads
	 * it into the JVM.
	 *
	 * @param natives Native library to load
	 * @deprecated LWJGL now self-extracts and loads natives
	 */
	@Deprecated
	public static void extractAndLoadNatives(String natives) {
		extractAndLoadNatives(natives, ClassLoader.getSystemClassLoader());
	}
	
	/** Version of {@link extractAndLoadNatives} that allows an explicit
	 * classloader to extract the native library from.
	 *
	 * @param natives Native library toload
	 * @param loader Classloader to get native library
	 * @deprecated LWJGL now self-extracts and loads natives
	 */
	@Deprecated
	public static void extractAndLoadNatives(String natives, ClassLoader loader) {
		extractAndLoadNatives(new File("natives"), natives, loader);
	}

	@Deprecated
	public static void extractAndLoadNatives(File nativesFolder, String ... natives) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < natives.length; i++) {
			sb.append(natives[i]);
			if (i != natives.length - 1) sb.append(';');
		}
		extractAndLoadNatives(nativesFolder, sb.toString());
	}

	@Deprecated
	public static void extractAndLoadNatives(File nativesFolder, String natives) {
		extractAndLoadNatives(nativesFolder, natives, ClassLoader.getSystemClassLoader());
	}
	
	/** Version of {@link extractAndLoadNatives} that takes a natives folder and class loader.
	 *
	 * @deprecated LWJGL now self-extracts and loads natives
	 */
	@Deprecated
	public static void extractAndLoadNatives(File nativesFolder, String natives, ClassLoader loader) {
		if (!(nativesFolder.exists() && nativesFolder.isDirectory())) {
			if (!nativesFolder.mkdirs()) throw new RuntimeException("Failed to create folder for LWJGL natives");
		}
		String nameWindows, nameUnix;
		if (natives.indexOf(':') == -1) nameWindows = nameUnix = natives;
		else {
			String[] names = natives.split("\\:");
			nameWindows = names[0];
			nameUnix = names[1];
		}
		String filename = null;
		Platform platform = Platform.get();
		switch(platform) {
		case WINDOWS:
			// Pointer.BITS64 cannot be used because it requires LWJGL's library to be loaded in the first place!
			if (org.bridj.Platform.is64Bits()) filename = nameWindows + ".dll";
			else filename = nameWindows + "32.dll";
			break;
		case LINUX: filename = "lib" + nameUnix + ".so"; break;
		case MACOSX: filename = "lib" + nameUnix + ".dylib"; break;
		}
		if (filename==null) throw new RuntimeException("Failed to determine filename for natives \"" + natives + '\"');
		File nativeFile = new File(nativesFolder, filename);
		if (!(nativeFile.exists() && nativeFile.isFile())) {
			try(InputStream stream = loader.getResourceAsStream(filename)) {
				if (stream==null) throw new RuntimeException("Failed to find native file \"" + filename + "\" to extract");
				Path nativePath = Paths.get(nativeFile.toURI());
				Files.copy(stream, nativePath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException("Failed to extract native file \"" + filename + '\"', e);
			}
		}
		System.load(nativeFile.getAbsolutePath());
	}
	
}
