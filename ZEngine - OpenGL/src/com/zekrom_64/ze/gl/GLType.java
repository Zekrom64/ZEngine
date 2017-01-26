package com.zekrom_64.ze.gl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

/** Types defined by OpenGL
 * 
 * @author Zekrom_64
 *
 */
public enum GLType {

	BYTE(GL11.GL_BYTE, 1, true, false),
	UNSIGNED_BYTE(GL11.GL_UNSIGNED_BYTE, 1, false, false),
	SHORT(GL11.GL_SHORT, 2, true, false),
	UNSIGNED_SHORT(GL11.GL_UNSIGNED_SHORT, 2, false, false),
	INT(GL11.GL_INT, 4, true, false),
	UNSIGNED_INT(GL11.GL_UNSIGNED_INT, 4, false, false),
	FLOAT(GL11.GL_FLOAT, 4, true, true),
	DOUBLE(GL11.GL_DOUBLE, 8, true, true),
	TWO_BYTES(GL11.GL_2_BYTES, 2, false, false),
	THREE_BYTES(GL11.GL_3_BYTES, 3, false, false),
	FOUR_BYTES(GL11.GL_4_BYTES, 4, false, false),
	HALF_FLOAT(GL30.GL_HALF_FLOAT, 2, true, true);
	
	/** The OpenGL enum value of this type */
	public int glenum;
	/** The size in bytes of the type */
	public int size;
	/** If the type is signed. This is true for signed integers and floating point values. */
	public boolean signed;
	/** If the type is floating point */
	public boolean floating;
	
	private GLType(int e, int s, boolean g, boolean f) {
		glenum = e;
		size = s;
		signed = g;
		floating = f;
	}
	
}
