package com.zekrom_64.ze.base.backend.render;

import com.zekrom_64.ze.base.util.PrimitiveType;

/** A vertex format describes all the attributes of each vertex.
 * 
 * @author Zekrom_64
 *
 */
public class ZEVertexFormat {

	/** A vertex attribute is a single value stored per vertex.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEVertexAttrib {
		
		/** Gets the name of the attribute.
		 * 
		 * @return Attribute name
		 */
		public String attribName();
		
	}
	
	/** Standard vertex attributes.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public enum ZEStdVertexAttribs implements ZEVertexAttrib {
		VERTEX("Vertex"),
		COLOR("Color"),
		TEXCOORDS("TexCoords"),
		NORMAL("Normal");
		
		private ZEStdVertexAttribs(String n) {
			attribName=  n;
		}
		
		public final String attribName;
		public String attribName() {
			return attribName;
		}
		
	}
	
	public class ZEVertexFormatEntry {
		
		/** Type of the vertex format entry */
		public final PrimitiveType type;
		/** Number of items in the entry */
		public final int count;
		/** Vertex attribute description */
		public final ZEVertexAttrib attrib;
		
		public ZEVertexFormatEntry(PrimitiveType type, int count, ZEVertexAttrib attrib) {
			this.type = type;
			this.count = count;
			this.attrib = attrib;
		}
		
	}
	
	/** Array of entries in the vertex format */
	public final ZEVertexFormatEntry[] entries;
	
	public ZEVertexFormat(ZEVertexFormatEntry ... entries) {
		this.entries = entries;
	}
	
	/** Determines the stride between vertices in bytes.
	 * 
	 * @return Vertex stride
	 */
	public int stride() {
		int bytes = 0;
		for(ZEVertexFormatEntry e : entries) bytes += e.count * e.type.size;
		return bytes;
	}
	
}
