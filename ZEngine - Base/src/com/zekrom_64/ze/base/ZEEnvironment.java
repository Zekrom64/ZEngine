package com.zekrom_64.ze.base;

/** The environment class provides environment values for ZEngine to base its behavior
 * on (ex. optimizations).
 * 
 * @author Zekrom_64
 *
 */
public class ZEEnvironment {

	private static boolean tryParse(String s, boolean def) {
		if ("true".equals(s)) return true;
		else if ("false".equals(s)) return false;
		else return def;
	}
	
	/** If the {@link com.zekrom_64.ze.base.image.ZEImage ZEImage} class should
	 * use {@link org.lwjgl.system.libc.LibCStdlib} for memory management. */
	public static boolean optimizationZEImageUnsafeMemory = tryParse(System.getProperty("ze.optimization.image.unsafeMemory"), false);
	
}
