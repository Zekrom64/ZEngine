package org.bridj;

/** Standard C library functions. Some of these exist in {@link org.bridj.JNI JNI}, but have default access, so can only be accessed
 * through this class or any other in the same package.
 * 
 * @author Zekrom_64
 *
 */
public class CLib {

	@SuppressWarnings("deprecation")
	public static void memset(Pointer<?> dst, byte value, long num) {
		JNI.memset(dst.getPeer(), value, num);
	}
	
	public static void memset(Pointer<?> dst, int value, long num) {
		memset(dst, (byte)(value&0xFF), num);
	}
	
	@SuppressWarnings("deprecation")
	public static void memcpy(Pointer<?> dst, Pointer<?> src, long num) {
		JNI.memcpy(dst.getPeer(), src.getPeer(), num);
	}
	
	@SuppressWarnings("deprecation")
	public static int memcmp(Pointer<?> ptr1, Pointer<?> ptr2, long num) {
		return JNI.memcmp(ptr1.getPeer(), ptr2.getPeer(), num);
	}
	
}
