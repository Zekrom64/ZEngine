package com.zekrom_64.ze.gl;

import org.lwjgl.opengl.GL11;

/** Class wrapping an OpenGL version number.
 * 
 * @author Zekrom_64
 *
 */
public class GLVersion implements Comparable<GLVersion> {

	public static final GLVersion
			VERSION_1_1 = new GLVersion(1, 1),
			VERSION_1_2 = new GLVersion(1, 2),
			VERSION_1_3 = new GLVersion(1, 3),
			VERSION_1_4 = new GLVersion(1, 4),
			VERSION_1_5 = new GLVersion(1, 5),
			VERSION_2_0 = new GLVersion(2, 0),
			VERSION_2_1 = new GLVersion(2, 1),
			VERSION_3_0 = new GLVersion(3, 0),
			VERSION_3_1 = new GLVersion(3, 1),
			VERSION_3_2 = new GLVersion(3, 2),
			VERSION_3_3 = new GLVersion(3, 3),
			VERSION_4_0 = new GLVersion(4, 0),
			VERSION_4_1 = new GLVersion(4, 1),
			VERSION_4_2 = new GLVersion(4, 2),
			VERSION_4_3 = new GLVersion(4, 3),
			VERSION_4_4 = new GLVersion(4, 4),
			VERSION_4_5 = new GLVersion(4, 5);
	
	public final int major, minor;
	
	public GLVersion(int maj, int min) {
		major = maj;
		minor = min;
	}
	
	public static GLVersion getFromGL() {
		String verstr = GL11.glGetString(GL11.GL_VERSION);
		int idx0 = verstr.indexOf('.');
		String ver0 = verstr.substring(0, idx0);
		String verstr0 = verstr.substring(idx0+1);
		int idx1 = verstr0.indexOf('.');
		String ver1 = verstr0.substring(0, idx1);
		try {
			return new GLVersion(Integer.parseInt(ver0), Integer.parseInt(ver1));
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static String getVendor() {
		return GL11.glGetString(GL11.GL_VENDOR);
	}
	
	public static String getRenderer() {
		return GL11.glGetString(GL11.GL_RENDER);
	}

	@Override
	public int compareTo(GLVersion arg0) {
		int cmp = Integer.compare(major, arg0.major);
		if (cmp!=0) return cmp;
		return Integer.compare(minor, arg0.minor);
	}
	
	public static boolean extensionExists(String name) {
		String exts = GL11.glGetString(GL11.GL_EXTENSIONS);
		return exts.contains(name);
	}
	
}
