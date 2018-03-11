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

	/** Attempts to parse the given string using {@link Integer#parseInt(String)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param def Default value
	 * @return Parsed integer, or default
	 */
	public static int tryParse(String s, int def) {
		return tryParse(s, 10, def);
	}
	
	/** Attempts to parse the given string using {@link Integer#parseInt(String,int)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param radix The radix to use for parsing
	 * @param def Default value
	 * @return Parsed integer, or default
	 */
	public static int tryParse(String s, int radix, int def) {
		try {
			return Integer.parseInt(s, radix);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	/** Attempts to parse the given string using {@link Float#parseFloat(String)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param def Default value
	 * @return Parsed float, or default
	 */
	public static float tryParse(String s, float def) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	/** Attempts to parse the given string using {@link Long#parseLong(String)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param radix The radix to use for parsing
	 * @param def Default value
	 * @return Parsed long, or default
	 */
	public static long tryParse(String s, long def) {
		return tryParse(s, 10, def);
	}
	
	/** Attempts to parse the given string using {@link Long#parseLong(String, int)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param radix The radix to use for parsing
	 * @param def Default value
	 * @return Parsed long, or default
	 */
	public static long tryParse(String s, int radix, long def) {
		try {
			return Long.parseLong(s, radix);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
	/** Attempts to parse the given string using {@link Double#parseDouble(String)}, returning
	 * a default value if the parsing fails.
	 * 
	 * @param s String to parse
	 * @param def Default value
	 * @return Parsed long, or default
	 */
	public static double tryParse(String s, double def) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			return def;
		}
	}
	
}
