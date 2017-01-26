package com.zekrom_64.ze.base.util;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Set;

/** A configuration is a tree-based system for storing values that can be saved to and read from a file.
 * 
 * @author Zekrom_64
 *
 */
public class ZEConfiguration {
	
	/** A property of a configuration that can be a raw String, boolean, int, float, double, or hexadecimal int.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public class CfgProperty {
		
		/** The comment to be displayed above a the property. */
		public String comment;
		/** The string value of the property. */
		protected String value;
		
		public String asString() {
			return value;
		}
		
		public boolean asBoolean() {
			return Boolean.parseBoolean(value);
		}
		
		public int asInt() {
			return Integer.parseInt(value);
		}
		
		public float asFloat() {
			return Float.parseFloat(value);
		}
		
		public double asDouble() {
			return Double.parseDouble(value);
		}
		
		public int asHexInt() {
			return Integer.parseInt(value, 16);
		}
		
		public void set(String value) {
			this.value = value;
		}
		
		public void set(boolean value) {
			this.value = Boolean.toString(value);
		}
		
		public void set(int value) {
			this.value = Integer.toString(value);
		}
		
		public void setHex(int value) {
			this.value = Integer.toHexString(value);
		}
		
		public void set(float value) {
			this.value = Float.toString(value);
		}
		
		public void set(double value) {
			this.value = Double.toString(value);
		}
		
	}
	
	/** The comment at the head of the configuration.*/
	public String comment;
	
	private HashMap<String, CfgProperty> properties = new HashMap<>();
	private HashMap<String, ZEConfiguration> subconfigs = new HashMap<>();
	
	/** Gets a sub-configuration from this configuration. If the sub-configuration does not exist, a new
	 * one is created and registered. The given comment overrides the already existing one if it is not null.
	 * 
	 * @param name Name of the sub-configuration
	 * @param comment Configuration comment, or null
	 * @return The sub-configuration
	 */
	public ZEConfiguration getConfiguration(String name, String comment) {
		if (name==null) return null;
		ZEConfiguration c = subconfigs.get(name);
		if (c==null) {
			c = new ZEConfiguration();
			subconfigs.put(name, c);
		}
		if (comment!=null) c.comment = comment;
		return c;
	}
	
	/** Gets a property from this configuration. If the property does not exist, a new one is created and registered.
	 * The given comment overrides the already existing one if it is not null.
	 * 
	 * @param name Name of the property
	 * @param comment Property comment, or null
	 * @param def The default value if none exists
	 * @return The property
	 */
	public CfgProperty getProperty(String name, String comment, String def) {
		if (name==null) return null;
		CfgProperty p = properties.get(name);
		if (p==null) {
			p = new CfgProperty();
			p.value = def;
			properties.put(name, p);
		}
		if (comment!=null) p.comment = comment;
		return p;
	}
	
	/** Writes this configuration to a writer.
	 * 
	 * @param w Writer to write to
	 * @throws IOException If an IOException occurred
	 */
	public void write(Writer w) throws IOException {
		StringBuffer buf = new StringBuffer();
		String lf = System.lineSeparator();
		buf.append(ZEStringUtil.makeComment(comment)).append(lf).append(lf);

		Set<String> keys = properties.keySet();
		for(String k : keys) {
			CfgProperty p = properties.get(k);
			if (p!=null) {
				buf.append(ZEStringUtil.indent(ZEStringUtil.makeComment(p.comment)))
				.append(lf)
				.append('\t').append(ZEStringUtil.formatSafe(p.asString()))
				.append(lf).append(lf);
			}
		}
		w.write(buf.toString());
		
		Set<String> ckeys = subconfigs.keySet();
		for(String k : ckeys) {
			ZEConfiguration c = subconfigs.get(k);
			if (c!=null) c._write(w, 1);
		}
	}
	
	private void _write(Writer w, int indentation) throws IOException {
		StringBuffer buf = new StringBuffer();
		
	}
	
	/** Reads into this configuration from a reader.
	 * 
	 * @param r Reader to read from
	 * @throws IOException If an IOException occurred
	 */
	public void read(Reader r) throws IOException {
		String str = ZEIOUtil.readFully(r);
		char[] chars = str.toCharArray();
		_read(chars, 0);
	}
	
	private int _read(char[] chars, int currentindex) throws IOException {
		String key = null;
		StringBuilder comment = new StringBuilder();
		StringBuilder text = new StringBuilder();
		
		boolean wasComment = false;
		
		while(currentindex<chars.length) {
			char c = chars[currentindex++];
			switch(c) {
			case '#':
				{
					if (wasComment) comment.append('\n');
					int startindex = currentindex;
					currentindex = _findeol(chars, currentindex);
					comment.append(chars, startindex, currentindex-startindex);
					wasComment = true;
				}
				
			}
		}
		// TODO: Finish configuration implementation
		return -1;
	}
	
	private int _readprop(char[] chars, int currentindex) throws IOException {
		// TODO: Finish configuration implementation
		return -1;
	}
	
	private int _findeol(char[] chars, int currentindex) {
		while(currentindex < chars.length) {
			char c = chars[currentindex++];
			switch(c) {
			case '\r': 
				if (chars[currentindex+1]=='\n') return currentindex+1;
				else return currentindex;
			case '\n': return currentindex;
			}
		}
		return currentindex;
	}
	
}
