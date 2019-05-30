package com.zekrom_64.ze.gl;

/** Similar to {@link com.zekrom_64.ze.ZEEnvironment} but for OpenGL parameters.
 * 
 * @author Zekrom_64
 *
 */
public class GLEnvironment {

	private static boolean tryParse(String s, boolean def) {
		if ("true".equals(s)) return true;
		else if ("false".equals(s)) return false;
		else return def;
	}
	
	/** Some pre-2011 ATI drivers have buggy behavior in the order of OpenGL calls
	 * to generate mipmaps. If true, a workaround of the bug is enabled, but this
	 * is normally false for performance.
	 */
	public static boolean quirkGLTextureBadATIMipmaps = tryParse(System.getProperty("gl.quirk.texture.badATIMipmap"), false);

}
