package com.zekrom_64.ze.base.math;

/** Utility class for math.
 * 
 * @author Zekrom_64
 *
 */
public class ZEMathUtil {
	
	/** Avogadro's constant in mol^-1 */
	public static final double AVOGADRO_CONSTANT = 6.02214085774d * Math.pow(10, 23);
	/** Speed of light in meters per second */
	public static final double SPEED_OF_LIGHT = 299792458;

	public static int tryParse(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public static float tryParse(String s, float def) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public static long tryParse(String s, long def) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	public static double tryParse(String s, double def) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
}
