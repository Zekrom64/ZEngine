package com.zekrom_64.ze.gl.vertex;

@Deprecated
/** Standard vertex buffer, composed of 4 double vertices, 4 float colors, 2 double texcoords, and 3 double normals.
 * 
 * @author Zekrom_64
 *
 */
public class GLVertexBufferStd extends GLVertexBuffer {
	
	public GLVertexBufferStd(int nVertices) {
		super(nVertices, GLVertexType.STD_VERTEX);
	}
	
	private float[] color = new float[4];
	private double[] texcoord = new double[2];
	private double[] normal = new double[3];
	
	public void color(float r, float g, float b, float a) {
		color[0] = r;
		color[1] = g;
		color[2] = b;
		color[3] = a;
	}
	
	public void texcoord(double s, double t) {
		texcoord[0] = s;
		texcoord[1] = t;
	}
	
	public void normal(double nx, double ny, double nz) {
		normal[0] = nx;
		normal[1] = ny;
		normal[2] = nz;
	}
	
	public void vertex(double x, double y, double z, double w) {
		buffer.putDouble(x).putDouble(y).putDouble(z).putDouble(w)
		.putFloat(color[0]).putFloat(color[1]).putFloat(color[2]).putFloat(color[3])
		.putDouble(texcoord[0]).putDouble(texcoord[1])
		.putDouble(normal[0]).putDouble(normal[1]).putDouble(normal[2]);
	}
	
	public void vertex(double x, double y, double z, float r, float g, float b, float a, double s, double t, double nx, double ny, double nz) {
		buffer.putDouble(x).putDouble(y).putDouble(z)
		.putFloat(r).putFloat(g).putFloat(b).putFloat(a)
		.putDouble(s).putDouble(t)
		.putDouble(nx).putDouble(ny).putDouble(nz);
	}
	
}
