package com.zekrom_64.ze.gl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/** LWJGL3 version of GLU in lwjgl_util
 * 
 * @author Zekrom_64
 *
 */
public class GLU {
	  
	private static final float[] forward = new float[3];
	private static final float[] side = new float[3];
	private static final float[] up = new float[3];

	private static final FloatBuffer matrix = BufferUtils.createFloatBuffer(16);
	
	/** gluPerspective specifies a viewing frustum into the world coordinate system. In general, the aspect ratio in gluPerspective should
	 * match the aspect ratio of the associated viewport. For example, aspect = 2.0 means the viewer's angle of view is twice as wide in x
	 * as it is in y. If the viewport is twice as wide as it is tall, it displays the image without distortion.
	 * 
	 * @param fovy Specifies the field of view angle, in degrees, in the y direction.
	 * @param aspect Specifies the aspect ratio that determines the field of view in the x direction. The aspect ratio is the ratio of 
	 * x (width) to y (height).
	 * @param zNear Specifies the distance from the viewer to the near clipping plane (always positive).
	 * @param zFar Specifies the distance from the viewer to the far clipping plane (always positive).
	 */
	public static void gluPerspective(float fovy, float aspect, float zNear, float zFar) {
		float radians = fovy / 2.0F * 3.141593F / 180.0F;
	    
	    float deltaZ = zFar - zNear;
	    float sine = (float)Math.sin(radians);
	    if ((deltaZ == 0.0F) || (sine == 0.0F) || (aspect == 0.0F)) {
	      return;
	    }
	    float cotangent = (float)Math.cos(radians) / sine;

	    matrix.rewind();
	    matrix.put(new float[] {
	    	1, 0, 0, 0,
	    	0, 1, 0, 0,
	    	0, 0, 1, 0,
	    	0, 0, 0, 1
	    });
	    matrix.rewind();
	    matrix.put(0, cotangent / aspect);
	    matrix.put(5, cotangent);
	    matrix.put(10, -(zFar + zNear) / deltaZ);
	    matrix.put(11, -1);
	    matrix.put(14, -2 * zNear * zFar / deltaZ);
	    matrix.put(15, 0);
	    
	    GL11.glMultMatrixf(matrix);
	    
	}
	
	/** gluLookAt creates a viewing matrix derived from an eye point, a reference point indicating the center of the scene, and an UP vector.
	 * 
	 * The matrix maps the reference point to the negative z axis and the eye point to the origin. When a typical projection matrix is used,
	 * the center of the scene therefore maps to the center of the viewport. Similarly, the direction described by the UP vector projected
	 * onto the viewing plane is mapped to the positive y axis so that it points upward in the viewport. The UP vector must not be parallel
	 * to the line of sight from the eye point to the reference point.
	 * 
	 * @param eyex Specifies the position of the eye point.
	 * @param eyey Specifies the position of the eye point.
	 * @param eyez Specifies the position of the eye point.
	 * @param centerx Specifies the position of the reference point.
	 * @param centery Specifies the position of the reference point.
	 * @param centerz Specifies the position of the reference point.
	 * @param upx Specifies the direction of the up vector.
	 * @param upy Specifies the direction of the up vector.
	 * @param upz Specifies the direction of the up vector.
	 */
	public static void gluLookAt(float eyex, float eyey, float eyez, float centerx, float centery, float centerz, float upx, float upy, float upz) {
		float[] forward = GLU.forward;
		float[] side = GLU.side;
		float[] up = GLU.up;
		
		forward[0] = (centerx - eyex);
		forward[1] = (centery - eyey);
		forward[2] = (centerz - eyez);
		
		up[0] = upx;
		up[1] = upy;
		up[2] = upz;
		
		normalize(forward);
		
		
		cross(forward, up, side);
		normalize(side);
		
		cross(side, forward, up);
		
		matrix.rewind();
		matrix.put(new float[] {
			1, 0, 0, 0,
		    0, 1, 0, 0,
		    0, 0, 1, 0,
		    0, 0, 0, 1
		});
		matrix.rewind();
		matrix.put(0, side[0]);
		matrix.put(4, side[1]);
		matrix.put(8, side[2]);
		
		matrix.put(1, up[0]);
		matrix.put(5, up[1]);
		matrix.put(9, up[2]);
		
		matrix.put(2, -forward[0]);
		matrix.put(6, -forward[1]);
		matrix.put(10, -forward[2]);
		
		GL11.glMultMatrixf(matrix);
		GL11.glTranslatef(-eyex, -eyey, -eyez);
	}
	
	private static float[] normalize(float[] v) {
		float r = (float)Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
		if (r == 0.0D) {
			return v;
		}
		r = 1.0F / r;
		
		v[0] *= r;
		v[1] *= r;
		v[2] *= r;
		
		return v;
	}
	
	private static void cross(float[] v1, float[] v2, float[] result) {
		result[0] = (v1[1] * v2[2] - v1[2] * v2[1]);
		result[1] = (v1[2] * v2[0] - v1[0] * v2[2]);
		result[2] = (v1[0] * v2[1] - v1[1] * v2[0]);
	}
	
}
