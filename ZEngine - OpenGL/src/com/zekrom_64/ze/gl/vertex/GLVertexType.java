package com.zekrom_64.ze.gl.vertex;

import com.zekrom_64.ze.gl.GLType;

@Deprecated
public class GLVertexType {

	public final GLType vertexType;
	public final int vertexCount;
	public final GLType colorType;
	public final int colorCount;
	public final GLType texcoordType;
	public final int texcoordCount;
	public final GLType normalType;
	
	public GLVertexType(GLType vt, int vc, GLType ct, int cc, GLType tt, int tc, GLType nt) {
		vertexType = vt;
		vertexCount = vc;
		colorType = ct;
		colorCount = cc;
		texcoordType = tt;
		texcoordCount = tc;
		normalType = nt;
	}
	
	public int size() {
		return (vertexCount * vertexType.size) +
				(colorCount > 0 ? (colorCount * colorType.size) : 0)+
				(texcoordCount > 0 ? (texcoordCount * texcoordType.size) : 0) +
				(normalType != null ? (normalType.size * 3) : 0);
	}
	
	public static final GLVertexType STD_VERTEX = new GLVertexType(GLType.DOUBLE, 4, GLType.FLOAT, 4, GLType.DOUBLE, 2, GLType.DOUBLE);
	public static final GLVertexType VOXEL_VERTEX = new GLVertexType(GLType.INT, 3, GLType.BYTE, 4, GLType.DOUBLE, 2, GLType.BYTE);
	
}
