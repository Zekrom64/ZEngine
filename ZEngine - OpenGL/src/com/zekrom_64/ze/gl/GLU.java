package com.zekrom_64.ze.gl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.mathlib.matrix.impl.Matrix4x4F;
import com.zekrom_64.mathlib.tuple.impl.Vector3F;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

/*
 * Copyright (c) 2002-2008 LWJGL Project
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'LWJGL' nor the names of
 *   its contributors may be used to endorse or promote products derived
 *   from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
/** LWJGL3 version of GLU in lwjgl_util
 * 
 * @author Zekrom_64
 *
 */
public class GLU {
	
	public static final int GLU_INVALID_ENUM = 100900;
	public static final int GLU_INVALID_VALUE = 100901;
	public static final int GLU_OUT_OF_MEMORY = 100902;
	public static final int GLU_INCOMPATIBLE_GL_VERSION = 100903;
	
	public static final int GLU_VERSION = 100800;
	public static final int GLU_EXTENSIONS = 100801;
	
	public static final int GLU_SMOOTH = 1000000;
	public static final int GLU_FLAT = 1000001;
	public static final int GLU_NONE = 1000002;

	public static final int GLU_POINT = 1000010;
	public static final int GLU_LINE = 1000011;
	public static final int GLU_FILL = 1000012;
	public static final int GLU_SILHOUETTE = 1000013;
	
	public static final int GLU_OUTSIDE = 100020;
	public static final int GLU_INSIDE = 100021;
	
	public static final double GLU_TESS_MAX_COORD = 1.0e150;
	public static final double TESS_MAX_COORD = 1.0e150;

	public static final int GLU_TESS_WINDING_RULE = 100140;
	public static final int GLU_TESS_BOUNDARY_ONLY = 100141;
	public static final int GLU_TESS_TOLERANCE = 100142;

	public static final int GLU_TESS_WINDING_ODD = 100130;
	public static final int GLU_TESS_WINDING_NONZERO = 100131;
	public static final int GLU_TESS_WINDING_POSITIVE = 100132;
	public static final int GLU_TESS_WINDING_NEGATIVE = 100133;
	public static final int GLU_TESS_WINDING_ABS_GEQ_TWO = 100134;

	public static final int GLU_TESS_BEGIN = 100100;
	public static final int GLU_TESS_VERTEX = 100101;
	public static final int GLU_TESS_END = 100102;
	public static final int GLU_TESS_ERROR = 100103;
	public static final int GLU_TESS_EDGE_FLAG = 100104;
	public static final int GLU_TESS_COMBINE = 100105;
	public static final int GLU_TESS_BEGIN_DATA = 100106;
	public static final int GLU_TESS_VERTEX_DATA = 100107;
	public static final int GLU_TESS_END_DATA = 100108;
	public static final int GLU_TESS_ERROR_DATA = 100109;
	public static final int GLU_TESS_EDGE_FLAG_DATA = 100110;
	public static final int GLU_TESS_COMBINE_DATA = 100111;

	public static final int GLU_TESS_ERROR1 = 100151;
	public static final int GLU_TESS_ERROR2 = 100152;
	public static final int GLU_TESS_ERROR3 = 100153;
	public static final int GLU_TESS_ERROR4 = 100154;
	public static final int GLU_TESS_ERROR5 = 100155;
	public static final int GLU_TESS_ERROR6 = 100156;
	public static final int GLU_TESS_ERROR7 = 100157;
	public static final int GLU_TESS_ERROR8 = 100158;

	public static final int GLU_TESS_MISSING_BEGIN_POLYGON = GLU_TESS_ERROR1;
	public static final int GLU_TESS_MISSING_BEGIN_CONTOUR = GLU_TESS_ERROR2;
	public static final int GLU_TESS_MISSING_END_POLYGON = GLU_TESS_ERROR3;
	public static final int GLU_TESS_MISSING_END_CONTOUR = GLU_TESS_ERROR4;
	public static final int GLU_TESS_COORD_TOO_LARGE = GLU_TESS_ERROR5;
	public static final int GLU_TESS_NEED_COMBINE_CALLBACK = GLU_TESS_ERROR6;

	public static final int GLU_AUTO_LOAD_MATRIX = 100200;
	public static final int GLU_CULLING = 100201;
	public static final int GLU_SAMPLING_TOLERANCE = 100203;
	public static final int GLU_DISPLAY_MODE = 100204;
	public static final int GLU_PARAMETRIC_TOLERANCE = 100202;
	public static final int GLU_SAMPLING_METHOD = 100205;
	public static final int GLU_U_STEP = 100206;
	public static final int GLU_V_STEP = 100207;

	public static final int GLU_PATH_LENGTH = 100215;
	public static final int GLU_PARAMETRIC_ERROR = 100216;
	public static final int GLU_DOMAIN_DISTANCE = 100217;

	public static final int GLU_MAP1_TRIM_2 = 100210;
	public static final int GLU_MAP1_TRIM_3 = 100211;

	public static final int GLU_OUTLINE_POLYGON = 100240;
	public static final int GLU_OUTLINE_PATCH = 100241;

	public static final int GLU_NURBS_ERROR1 = 100251;
	public static final int GLU_NURBS_ERROR2 = 100252;
	public static final int GLU_NURBS_ERROR3 = 100253;
	public static final int GLU_NURBS_ERROR4 = 100254;
	public static final int GLU_NURBS_ERROR5 = 100255;
	public static final int GLU_NURBS_ERROR6 = 100256;
	public static final int GLU_NURBS_ERROR7 = 100257;
	public static final int GLU_NURBS_ERROR8 = 100258;
	public static final int GLU_NURBS_ERROR9 = 100259;
	public static final int GLU_NURBS_ERROR10 = 100260;
	public static final int GLU_NURBS_ERROR11 = 100261;
	public static final int GLU_NURBS_ERROR12 = 100262;
	public static final int GLU_NURBS_ERROR13 = 100263;
	public static final int GLU_NURBS_ERROR14 = 100264;
	public static final int GLU_NURBS_ERROR15 = 100265;
	public static final int GLU_NURBS_ERROR16 = 100266;
	public static final int GLU_NURBS_ERROR17 = 100267;
	public static final int GLU_NURBS_ERROR18 = 100268;
	public static final int GLU_NURBS_ERROR19 = 100269;
	public static final int GLU_NURBS_ERROR20 = 100270;
	public static final int GLU_NURBS_ERROR21 = 100271;
	public static final int GLU_NURBS_ERROR22 = 100272;
	public static final int GLU_NURBS_ERROR23 = 100273;
	public static final int GLU_NURBS_ERROR24 = 100274;
	public static final int GLU_NURBS_ERROR25 = 100275;
	public static final int GLU_NURBS_ERROR26 = 100276;
	public static final int GLU_NURBS_ERROR27 = 100277;
	public static final int GLU_NURBS_ERROR28 = 100278;
	public static final int GLU_NURBS_ERROR29 = 100279;
	public static final int GLU_NURBS_ERROR30 = 100280;
	public static final int GLU_NURBS_ERROR31 = 100281;
	public static final int GLU_NURBS_ERROR32 = 100282;
	public static final int GLU_NURBS_ERROR33 = 100283;
	public static final int GLU_NURBS_ERROR34 = 100284;
	public static final int GLU_NURBS_ERROR35 = 100285;
	public static final int GLU_NURBS_ERROR36 = 100286;
	public static final int GLU_NURBS_ERROR37 = 100287;

	public static final int GLU_CW = 100120;
	public static final int GLU_CCW = 100121;
	public static final int GLU_INTERIOR = 100122;
	public static final int GLU_EXTERIOR = 100123;
	public static final int GLU_UNKNOWN = 100124;

	public static final int GLU_BEGIN = GLU_TESS_BEGIN;
	public static final int GLU_VERTEX = GLU_TESS_VERTEX;
	public static final int GLU_END = GLU_TESS_END;
	public static final int GLU_ERROR = GLU_TESS_ERROR;
	public static final int GLU_EDGE_FLAG = GLU_TESS_EDGE_FLAG;
	
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
	
	private static void multMatrixVec(Matrix4x4F matrix, float[] in, float[] out) {
		for(int i = 0; i < 4; i++) {
			out[i] = 
					in[0] * matrix.get(0, i) +
					in[1] * matrix.get(1, i) +
					in[2] * matrix.get(2, i) +
					in[3] * matrix.get(3, i);
		}
	}
	
	private static int nearestPower(int num) {
		int power = 1;
		for(int i = 0; i < 31; i++) {
			if (power > num) return power;
			power <<= 1;
		}
		return -1;
	}
	
	private static int ceil(int a, int b) {
		return (a % b == 0 ? a / b : a / b + 1);
	}
	
	private static int PSS_UNPACK_ROW_LENGTH = 0;
	private static int PSS_UNPACK_ALIGNMENT = 1;
	private static int PSS_UNPACK_SKIP_ROWS = 2;
	private static int PSS_UNPACK_SKIP_PIXELS = 3;
	private static int PSS_PACK_ROW_LENGTH = 4;
	private static int PSS_PACK_ALIGNMENT = 5;
	private static int PSS_PACK_SKIP_ROWS = 6;
	private static int PSS_PACK_SKIP_PIXELS = 7;
	
	private static int[] getPixelStoreState() {
		return new int[] {
			GL11.glGetInteger(GL11.GL_UNPACK_ROW_LENGTH),
			GL11.glGetInteger(GL11.GL_UNPACK_ALIGNMENT),
			GL11.glGetInteger(GL11.GL_UNPACK_SKIP_ROWS),
			GL11.glGetInteger(GL11.GL_UNPACK_SKIP_PIXELS),
			GL11.glGetInteger(GL11.GL_PACK_ROW_LENGTH),
			GL11.glGetInteger(GL11.GL_PACK_ALIGNMENT),
			GL11.glGetInteger(GL11.GL_PACK_SKIP_ROWS),
			GL11.glGetInteger(GL11.GL_PACK_SKIP_PIXELS)
		};
	}
	
	private static void setPixelStoreState(int[] state) {
		GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, state[PSS_UNPACK_ROW_LENGTH]);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, state[PSS_UNPACK_ALIGNMENT]);
		GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, state[PSS_UNPACK_SKIP_ROWS]);
		GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, state[PSS_UNPACK_SKIP_PIXELS]);
		GL11.glPixelStorei(GL11.GL_PACK_ROW_LENGTH, state[PSS_PACK_ROW_LENGTH]);
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, state[PSS_PACK_ALIGNMENT]);
		GL11.glPixelStorei(GL11.GL_PACK_SKIP_ROWS, state[PSS_PACK_SKIP_ROWS]);
		GL11.glPixelStorei(GL11.GL_PACK_SKIP_PIXELS, state[PSS_PACK_SKIP_PIXELS]);
	}
	
	public static String gluGetString(int name) {
		switch(name) {
		case GLU_VERSION:
			return "1.3";
		case GLU_EXTENSIONS:
			return "GL_EXT_nurbs_tessellator GLU_EXT_object_space_tess";
		}
		return null;
	}
	
	public static boolean gluCheckExtension(String extName, String extString) {
		if (extString == null || extName == null) return false;
		return extString.indexOf(extName) != -1;
	}
	
	public static int gluBuild2DMipmaps(int target, int components, int width, int height, int format, int type, ByteBuffer data) {
		if (width < 1 || height < 1) return GLU_INVALID_VALUE;
		ZEPixelFormat pxfmt = GLValues.getZEPixelFormat(format, type);
		if (pxfmt == null) return GLU_INVALID_ENUM;
		int maxSize = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
		
		int w = nearestPower(width);
		if (w == -1) return GLU_INVALID_VALUE;
		if (w > maxSize) w = maxSize;
		int h = nearestPower(height);
		if (h == -1) return GLU_INVALID_VALUE;
		if (h > maxSize) h = maxSize;
		
		int[] pss = getPixelStoreState();
		
		GL11.glPixelStorei(GL11.GL_PACK_ROW_LENGTH, 0);
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
		GL11.glPixelStorei(GL11.GL_PACK_SKIP_ROWS, 0);
		GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
		
		ByteBuffer image;
		
		if (w != width || h != height) {
			image = LibCStdlib.malloc((w + 4) * h * pxfmt.sizeOf);
			int error = gluScaleImage(format, width, height, type, data, w, h, type, image);
			if (error != 0) {
				LibCStdlib.free(image);
				setPixelStoreState(pss);
				return error;
			}
			
			GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
			GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
			GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, 0);
			GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
		} else image = data;
		
		ByteBuffer bufferA = null, bufferB = null;
		
		int level = 0;
		boolean done = false;
		while(!done) {
			if (image != data) {
				GL11.glPixelStorei(GL11.GL_UNPACK_ROW_LENGTH, 0);
				GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
				GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_ROWS, 0);
				GL11.glPixelStorei(GL11.GL_UNPACK_SKIP_PIXELS, 0);
			}
			GL11.glTexImage2D(target, level, components, h, h, 0, format, type, image);
			if (w == 1 && h == 1) break;
			int newW = (w < 2) ? 1 : w >> 1;
			int newH = (h < 2) ? 1 : h >> 1;
			ByteBuffer newImage;
			if (bufferA == null)
				newImage = (bufferA = LibCStdlib.malloc((newW + 4) * newH * pxfmt.sizeOf));
			else if (bufferB == null)
				newImage = (bufferB = LibCStdlib.malloc((newW + 4) * newH * pxfmt.sizeOf));
			else
				newImage = bufferB;
			
			int error = gluScaleImage(format, w, h, type, image, newW, newH, type, newImage);
			if (error != 0) {
				if (image != data) LibCStdlib.free(image);
				setPixelStoreState(pss);
				if (bufferA != null) LibCStdlib.free(bufferA);
				if (bufferB != null) LibCStdlib.free(bufferB);
				return error;
			}
			
			LibCStdlib.free(image);
			image = newImage;
			
			if (bufferB != null) {
				LibCStdlib.free(bufferB);
				bufferB = bufferA;
			}
			
			w = newW;
			h = newH;
			level++;
		}
		
		if (bufferA != null) LibCStdlib.free(bufferA);
		if (bufferB != null) LibCStdlib.free(bufferB);
		
		if (image != data) LibCStdlib.free(image);
		setPixelStoreState(pss);
		return 0;
	}
	
	public static int gluScaleImage(int format, int widthIn, int heightIn, int typeIn, ByteBuffer dataIn,
			int widthOut, int heightOut, int typeOut, ByteBuffer dataOut) {
		int components = -1;
		switch(format) {
		case GL11.GL_COLOR_INDEX:
		case GL11.GL_STENCIL_INDEX:
		case GL11.GL_DEPTH_COMPONENT:
		case GL11.GL_RED:
		case GL11.GL_GREEN:
		case GL11.GL_BLUE:
		case GL11.GL_ALPHA:
		case GL11.GL_LUMINANCE:
			components = 1;
			break;
		case GL11.GL_LUMINANCE_ALPHA:
			components = 2;
			break;
		case GL11.GL_RGB:
		case GL12.GL_BGR:
			components = 3;
			break;
		case GL11.GL_RGBA:
		case GL12.GL_BGRA:
			components = 4;
			break;
		}
		if (components == -1) return GLU_INVALID_ENUM;
		
		int sizeIn, sizeOut;
		switch(typeIn) {
		case GL11.GL_UNSIGNED_BYTE:
			sizeIn = 1;
			break;
		case GL11.GL_FLOAT:
			sizeIn = 4;
			break;
		default: return GLU_INVALID_ENUM;
		}
		switch(typeOut) {
		case GL11.GL_UNSIGNED_BYTE:
			sizeOut = 1;
			break;
		case GL11.GL_FLOAT:
			sizeOut = 4;
			break;
		default: return GLU_INVALID_ENUM;
		}
		
		int[] pss = getPixelStoreState();
		
		int rowlen;
		if(pss[PSS_UNPACK_ROW_LENGTH] > 0)
			rowlen = pss[PSS_UNPACK_ROW_LENGTH];
		else
			rowlen = widthIn;
		
		int rowstride;
		if(sizeIn >= pss[PSS_UNPACK_ALIGNMENT])
			rowstride = components * rowlen;
		else
			rowstride = pss[PSS_UNPACK_ALIGNMENT] / sizeIn * ceil(components * rowlen * sizeIn, pss[PSS_UNPACK_ALIGNMENT]);
		
		float[] tempIn = new float[widthIn * heightIn * components];
		float[] tempOut = new float[widthOut * heightOut * components];
		
		int i,j,k;
		switch(typeIn) {
		case GL11.GL_UNSIGNED_BYTE:
			k = 0;
			dataIn.rewind();
			for(i = 0; i < heightIn; i++) {
				int ubptr = i * rowstride + pss[PSS_UNPACK_SKIP_ROWS] * rowstride + pss[PSS_UNPACK_SKIP_PIXELS] * components;
				for(j = 0; j < widthIn * components; j++) {
					tempIn[k++] = dataIn.get(ubptr++) & 0xFF;
				}
			}
			break;
		case GL11.GL_FLOAT:
			k = 0;
			dataIn.rewind();
			for(i = 0; i < heightIn; i++) {
				int fptr = 4 * (i * rowstride * pss[PSS_UNPACK_SKIP_ROWS] * rowstride + pss[PSS_UNPACK_SKIP_PIXELS] * components);
				for(j = 0; j < widthIn * components; j++) {
					tempIn[k++] = dataIn.getFloat(fptr);
					fptr += 4;
				}
			}
			break;
		}
		
		float sx = (float)widthIn / widthOut;
		float sy = (float)heightIn / heightOut;
		
		float[] c = new float[components];
		int src, dst;
		
		for ( int iy = 0; iy < heightOut; iy++ ) {
			for ( int ix = 0; ix < widthOut; ix++ ) {
				int x0 = (int)(ix * sx);
				int x1 = (int)((ix + 1) * sx);
				int y0 = (int)(iy * sy);
				int y1 = (int)((iy + 1) * sy);

				int readPix = 0;

				// reset weighted pixel
				for ( int ic = 0; ic < components; ic++ ) {
					c[ic] = 0;
				}

				// create weighted pixel
				for ( int ix0 = x0; ix0 < x1; ix0++ ) {
					for ( int iy0 = y0; iy0 < y1; iy0++ ) {

						src = (iy0 * widthIn + ix0) * components;

						for ( int ic = 0; ic < components; ic++ ) {
							c[ic] += tempIn[src + ic];
						}

						readPix++;
					}
				}

				// store weighted pixel
				dst = (iy * widthOut + ix) * components;

				if ( readPix == 0 ) {
					// Image is sized up, caused by non power of two texture as input
					src = (y0 * widthIn + x0) * components;
					for ( int ic = 0; ic < components; ic++ ) {
						tempOut[dst++] = tempIn[src + ic];
					}
				} else {
					// sized down
					for ( k = 0; k < components; k++ ) {
						tempOut[dst++] = c[k] / readPix;
					}
				}
			}
		}
		
		if (pss[PSS_PACK_ROW_LENGTH] > 0)
			rowlen = pss[PSS_PACK_ROW_LENGTH];
		else
			rowlen = widthOut;
		
		if (sizeOut >= pss[PSS_PACK_ALIGNMENT])
			rowstride = components * rowlen;
		else
			rowstride = pss[PSS_PACK_ALIGNMENT] / sizeOut * ceil(components * rowlen * sizeOut, pss[PSS_PACK_ALIGNMENT]);
		
		switch(typeOut) {
		case GL11.GL_UNSIGNED_BYTE:
			k = 0;
			for ( i = 0; i < heightOut; i++ ) {
				int ubptr = i * rowstride + pss[PSS_PACK_SKIP_ROWS] * rowstride + pss[PSS_PACK_SKIP_PIXELS] * components;
				for ( j = 0; j < widthOut * components; j++ ) {
					dataOut.put(ubptr++, (byte)tempOut[k++]);
				}
			}
			break;
		case GL11.GL_FLOAT:
			k = 0;
			for ( i = 0; i < heightOut; i++ ) {
				int fptr = 4 * (i * rowstride + pss[PSS_PACK_SKIP_ROWS] * rowstride + pss[PSS_PACK_SKIP_PIXELS] * components);
				for ( j = 0; j < widthOut * components; j++ ) {
					dataOut.putFloat(fptr, tempOut[k++]);
					fptr += 4;
				}
			}
			break;
		}
		
		return 0;
	}
	
	public static void gluOrtho2D(float left, float right, float bottom, float top) {
		GL11.glOrtho(left, right, bottom, top, -1, 1);
	}
	
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
	    
	    try(MemoryStack sp = MemoryStack.stackPush()) {
	    	FloatBuffer matrix = sp.mallocFloat(16);
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
		float[] forward = new float[3];
		float[] side = new float[3];
		float[] up = new float[3];
		
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
		try (MemoryStack sp = MemoryStack.stackPush()) {
			FloatBuffer matrix = sp.mallocFloat(16);
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
		}
		GL11.glTranslatef(-eyex, -eyey, -eyez);
	}
	
	public static boolean gluProject(float x, float y, float z, Matrix4x4F modelMatrix, Matrix4x4F projMatrix,
			int[] viewport, Vector3F winPos) {
		float[] in = new float[4];
		float[] out = new float[4];
		in[0] = x;
		in[1] = y;
		in[2] = z;
		in[3] = 1;
		multMatrixVec(modelMatrix, in, out);
		multMatrixVec(projMatrix, out, in);
		if (in[3] == 0) return false;
		in[3] = (1.0f / in[3]) * 0.5f;
		in[0] = in[0] * in[3] + 0.5f;
		in[1] = in[1] * in[3] + 0.5f;
		in[2] = in[2] * in[3] + 0.5f;
		winPos.x = in[0] * viewport[2] + viewport[0];
		winPos.y = in[1] * viewport[3] + viewport[1];
		winPos.z = in[2];
		return true;
	}
	
	public static boolean gluUnProject(float x, float y, float z, Matrix4x4F modelMatrix, Matrix4x4F projMatrix,
			int[] viewport, Vector3F objPos) {
		Matrix4x4F finalMatrix = new Matrix4x4F();
		finalMatrix.set(modelMatrix);
		finalMatrix.mul(projMatrix);
		if (!finalMatrix.invert()) return false;
		float[] in = new float[4], out = new float[4];
		in[0] = x;
		in[1] = y;
		in[2] = z;
		in[3] = 1;
		
		in[0] = (in[0] - viewport[0] / viewport[2]);
		in[1] = (in[1] - viewport[1] / viewport[3]);
		
		in[0] = in[0] * 2 - 1;
		in[1] = in[1] * 2 - 1;
		in[2] = in[2] * 2 - 1;
		
		multMatrixVec(finalMatrix, in, out);
		
		if (out[3] == 0) return false;
		
		out[3] = 1 / out[3];
		objPos.x = out[0] * out[3];
		objPos.y = out[1] * out[3];
		objPos.z = out[2] * out[3];
		return true;
	}
	
	public static void gluPickMatrix(float x, float y, float deltaX, float deltaY, int[] viewport) {
		if (deltaX <= 0 || deltaY == 0) return;
		GL11.glTranslatef(
				(viewport[2] - 2 * (x - viewport[0])) / deltaX,
				(viewport[3] - 2 * (y - viewport[1])) / deltaY,
				0);
		GL11.glScalef(viewport[2] / deltaX, viewport[3] / deltaY, 1);
	}
	
	public static String gluErrorString(int errorCode) {
		switch(errorCode) {
		case GLU_INVALID_ENUM: return "Invalid enum (glu)";
		case GLU_INVALID_VALUE: return "Invalid value (glu)";
		case GLU_OUT_OF_MEMORY: return "Out of memory (glu)";
		default: return GLException.getGLError(errorCode);
		}
	}
	
}
