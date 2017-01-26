package com.zekrom_64.ze.base.util;

import java.util.Arrays;

public class ZEStringUtil {

	/** Converts a string into a comment. This prepends a '#' character at the beginning of each line, and
	 * replaces newlines with the system line separator given by {@link java.lang.System#lineSeparator() System.lineSeparator()}.
	 * 
	 * @param cmt The comment string
	 * @return Formatted comment
	 */
	public static String makeComment(String cmt) {
		StringBuffer buf = new StringBuffer();
		String[] lines = cmt.replace("\r", "").split("\n");
		String lf = System.lineSeparator();
		for(String l : lines) buf.append('#').append(l).append(lf);
		int len = buf.length();
		buf.delete(len-lf.length(), len);
		return buf.toString();
	}

	/** Converts the string into a 'safe' format compatible with most charsets. If a character is outside the bounds
	 * of UTF-8, or is not alphanumeric (0-9,a-z,A-Z), it is converted to '\xxxx' where x is the hexadecimal value of
	 * the character in UTF-16. Else, the character is left as-is.
	 * 
	 * @param s The source string
	 * @return The safe string
	 */
	public static String formatSafe(String s) {
		StringBuffer buf = new StringBuffer();
		char[] chars = s.toCharArray();
		for(char c : chars) {
			if (c>0xFF||(!Character.isLetterOrDigit(c))) {
				String hexstr = Integer.toHexString(c);
				StringBuffer hex = new StringBuffer();
				for(int i = hexstr.length(); i < 4; i++) hex.append('0');
				hex.append(hexstr);
				buf.append(hex.toString());
			} else buf.append(c);
		}
		return buf.toString();
	}
	
	/** Indents the input string using one tab (\t) character.
	 * 
	 * @param input Input string
	 * @return Indented string
	 */
	public static String indent(String input) {
		StringBuffer buf = new StringBuffer();
		String[] lines = input.replace("\r","").split("\n");
		for(String l : lines) buf.append('\t').append(l).append('\n');
		return buf.toString();
	}
	
	/** Indents the input string using the number of tab (\t) characters given by the <tt>nIndent</tt> parameter.
	 * 
	 * @param input Input string
	 * @param nIndent Number of indents
	 * @return Indented string
	 */
	public static String indent(String input, int nIndent) {
		StringBuffer buf = new StringBuffer();
		char[] tabs = new char[nIndent];
		Arrays.fill(tabs, '\t');
		String[] lines = input.replace("\r","").split("\n");
		for(String l : lines) {
			buf.append(tabs, 0, nIndent);
			buf.append(l).append('\n');
		}
		return buf.toString();
	}

}
