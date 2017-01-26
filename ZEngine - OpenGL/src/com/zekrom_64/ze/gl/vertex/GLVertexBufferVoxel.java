package com.zekrom_64.ze.gl.vertex;

@Deprecated
/** Voxel vertex buffer, composed of 3 int vertices, 4 byte colors, 2 double texcoords, and 3 byte normals.
 * 
 * @author Zekrom_64
 *
 */
public class GLVertexBufferVoxel extends GLVertexBuffer {
	
	public GLVertexBufferVoxel(int nVertices) {
		super(nVertices, GLVertexType.VOXEL_VERTEX);
	}
	
	public void vertex(int x, int y, int z, int rgba, float s, float t, byte nx, byte ny, byte nz) {
		buffer.putInt(x).putInt(y).putInt(z).putInt(rgba).putFloat(s).putFloat(t).put(nx).put(ny).put(nz);
	}
	
}
