package com.zekrom_64.ze.gl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL40;

import com.zekrom_64.ze.base.backend.render.ZEGeometryType;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEFrontBack;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendFactor;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEBlendOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZECompareOp;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEPolygonMode;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBuilder.ZEStencilModifyOp;
import com.zekrom_64.ze.base.image.ZEPixelFormat;
import com.zekrom_64.ze.base.util.PrimitiveType;

/** Class with utilities to manipulate and convert values in OpenGL.
 * 
 * @author Zekrom_64
 *
 */
public class GLValues {

	/** Gets the OpenGL equivalent enumeration value for a blend factor.
	 * 
	 * @param factor ZEngine blend factor
	 * @return OpenGL blend factor
	 */
	public static int getGLFactor(ZEBlendFactor factor) {
		switch(factor) {
		case CONSTANT_ALPHA: return GL14.GL_CONSTANT_ALPHA;
		case CONSTANT_COLOR: return GL14.GL_CONSTANT_COLOR;
		case DST_ALPHA: return GL11.GL_DST_ALPHA;
		case DST_COLOR: return GL11.GL_DST_COLOR;
		case ONE: return GL11.GL_ONE;
		case ONE_MINUS_CONSTANT_ALPHA: return GL14.GL_ONE_MINUS_CONSTANT_ALPHA;
		case ONE_MINUS_CONSTANT_COLOR: return GL14.GL_ONE_MINUS_CONSTANT_COLOR;
		case ONE_MINUS_DST_ALPHA: return GL11.GL_ONE_MINUS_DST_ALPHA;
		case ONE_MINUS_DST_COLOR: return GL11.GL_ONE_MINUS_DST_COLOR;
		case ONE_MINUS_SRC_ALPHA: return GL11.GL_ONE_MINUS_SRC_ALPHA;
		case ONE_MINUS_SRC_COLOR: return GL11.GL_ONE_MINUS_SRC_COLOR;
		case SRC_ALPHA: return GL11.GL_SRC_ALPHA;
		case SRC_COLOR: return GL11.GL_SRC_COLOR;
		case ZERO: return GL11.GL_ZERO;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for a blend operation.
	 * 
	 * @param op ZEngine blend operation
	 * @return OpenGL blend operation
	 */
	public static int getGLBlendOp(ZEBlendOp op) {
		switch(op) {
		case ADD: return GL14.GL_FUNC_ADD;
		case MAX: return GL14.GL_MAX;
		case MIN: return GL14.GL_MIN;
		case REVERSE_SUBTRACT: return GL14.GL_FUNC_REVERSE_SUBTRACT;
		case SUBTRACT: return GL14.GL_FUNC_SUBTRACT;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for a culling mode.
	 * 
	 * @param mode ZEngine culling mode
	 * @return OpenGL culling mode
	 */
	public static int getGLFace(ZEFrontBack mode) {
		switch(mode) {
		case BACK: return GL11.GL_BACK;
		case FRONT: return GL11.GL_FRONT;
		case FRONT_BACK: return GL11.GL_FRONT_AND_BACK;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for a depth compare function.
	 * 
	 * @param cmp ZEngine depth compare function
	 * @return OpenGL depth compare function
	 */
	public static int getGLCompare(ZECompareOp cmp) {
		switch(cmp) {
		case ALWAYS: return GL11.GL_ALWAYS;
		case EQUAL: return GL11.GL_EQUAL;
		case GREATER: return GL11.GL_GREATER;
		case GREATER_OR_EQUAL: return GL11.GL_GEQUAL;
		case LESS: return GL11.GL_LESS;
		case LESS_OR_EQUAL: return GL11.GL_LEQUAL;
		case NEVER: return GL11.GL_NEVER;
		case NOT_EQUAL: return GL11.GL_NOTEQUAL;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for a polygon drawing mode.
	 * 
	 * @param mode ZEngine polygon drawing mode
	 * @return OpenGL polygon drawing mode
	 */
	public static int getGLPolygonMode(ZEPolygonMode mode) {
		switch(mode) {
		case FILL: return GL11.GL_FILL;
		case LINE: return GL11.GL_LINE;
		case POINT: return GL11.GL_POINT;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for an input geometry type.
	 * 
	 * @param type ZEngine input geometry type
	 * @return OpenGL input geometry type
	 */
	public static int getGLInputGeometryType(ZEGeometryType type) {
		switch(type) {
		case LINES: return GL11.GL_LINES;
		case LINE_STRIP: return GL11.GL_LINE_STRIP;
		case POINTS: return GL11.GL_POINTS;
		case TRIANGLES: return GL11.GL_TRIANGLES;
		case TRIANGLE_FAN: return GL11.GL_TRIANGLE_FAN;
		case TRIANGLE_STRIP: return GL11.GL_TRIANGLE_STRIP;
		}
		return -1;
	}

	/** Gets the OpenGL equivalent enumeration value for a primitive type.
	 * 
	 * @param type ZEngine primitive type
	 * @return OpenGL primitive type
	 */
	public static int getGLType(PrimitiveType type) {
		switch(type) {
		case BYTE: return GL11.GL_BYTE;
		case SHORT: return GL11.GL_SHORT;
		case INT_BOOL:
		case INT: return GL11.GL_INT;
		case UBYTE: return GL11.GL_UNSIGNED_BYTE;
		case USHORT: return GL11.GL_UNSIGNED_SHORT;
		case UINT: return GL11.GL_UNSIGNED_INT;
		case DOUBLE: return GL11.GL_DOUBLE;
		case FLOAT: return GL11.GL_FLOAT;
		case ULONG:
		case LONG:
		case BOOL: return -1;
		}
		return -1;
	}
	
	/** Gets the OpenGL equivalent enumeration value for a stencil modify operation.
	 * 
	 * @param op ZEngine stencil modify operation
	 * @return OpenGL stencil modify operation
	 */
	public static int getGLStencilModifyOp(ZEStencilModifyOp op) {
		switch(op) {
		case KEEP: return GL11.GL_KEEP;
		case ZERO: return GL11.GL_ZERO;
		case REPLACE: return GL11.GL_REPLACE;
		case INCREMENT_AND_CLAMP: return GL11.GL_INCR;
		case DECREMENT_AND_CLAMP: return GL11.GL_DECR;
		case INVERT: return GL11.GL_INVERT;
		case INCREMENT_AND_WRAP: return GL14.GL_INCR_WRAP;
		case DECREMENT_AND_WRAP: return GL14.GL_DECR_WRAP;
		}
		return -1;
	}

	/** Gets the OpenGL texture format for a given pixel format, returning
	 * -1 if the given format cannot be found.
	 * 
	 * @param fmt Pixel format
	 * @return OpenGL texture format
	 */
	public static int getGLTextureFormat(ZEPixelFormat fmt) {
		switch(fmt) {
		case R8G8B8A8_UINT:
		case R8G8B8A8_SINT:
		case R16G16B16A16_UINT:
		case R16G16B16A16_SINT:
		case R32G32B32A32_UINT:
		case R32G32B32A32_SINT:
		case R32G32B32A32_FLOAT:
		case R64G64B64A64_FLOAT: return GL11.GL_RGBA;
		case R8G8B8_UINT:
		case R8G8B8_SINT:
		case R16G16B16_UINT:
		case R16G16B16_SINT:
		case R32G32B32_UINT:
		case R32G32B32_SINT:
		case R32G32B32_FLOAT:
		case R64G64B64_FLOAT: return GL11.GL_RGB;
		case R8G8_UINT:
		case R8G8_SINT:
		case R16G16_UINT:
		case R16G16_SINT:
		case R32G32_UINT:
		case R32G32_SINT:
		case R32G32_FLOAT:
		case R64G64_FLOAT: return GL30.GL_RG;
		case R8_UINT:
		case R8_SINT:
		case R16_UINT:
		case R16_SINT:
		case R32_UINT:
		case R32_SINT:
		case R32_FLOAT:
		case R64_FLOAT: return GL11.GL_RED;
		case A8R8G8B8_UINT: return GL12.GL_BGRA;
		case UNKNOWN:
		default: return -1;
		}
	}

	/** Gets the OpenGL component type for a given pixel format, returning
	 * -1 if the given component type cannot be found.
	 * 
	 * @param fmt Pixel format
	 * @return OpenGL component type
	 */
	public static int getGLTextureType(ZEPixelFormat fmt) {
		switch(fmt) {
		case A8R8G8B8_UINT: return GL12.GL_UNSIGNED_INT_8_8_8_8_REV;
		case R64G64B64A64_FLOAT:
		case R64G64B64_FLOAT:
		case R64G64_FLOAT:
		case R64_FLOAT: return GL11.GL_DOUBLE;
		case R32G32B32A32_FLOAT:
		case R32G32B32_FLOAT:
		case R32G32_FLOAT:
		case R32_FLOAT: return GL11.GL_FLOAT;
		case R16G16B16A16_UINT:
		case R16G16B16_UINT:
		case R16G16_UINT:
		case R16_UINT: return GL11.GL_UNSIGNED_SHORT;
		case R32G32B32A32_UINT:
		case R32G32B32_UINT:
		case R32G32_UINT:
		case R32_UINT: return GL11.GL_UNSIGNED_INT;
		case R8G8B8A8_UINT:
		case R8G8B8_UINT:
		case R8G8_UINT:
		case R8_UINT: return GL11.GL_UNSIGNED_BYTE;
		case R16G16B16A16_SINT:
		case R16G16B16_SINT:
		case R16G16_SINT:
		case R16_SINT: return GL11.GL_SHORT;
		case R32G32B32A32_SINT:
		case R32G32B32_SINT:
		case R32G32_SINT:
		case R32_SINT: return GL11.GL_INT;
		case R8G8B8A8_SINT:
		case R8G8B8_SINT:
		case R8G8_SINT:
		case R8_SINT: return GL11.GL_BYTE;
		case UNKNOWN:
		default: return -1;
		}
	}
	
	public static int getGLTextureTarget(ZETextureDimension dim) {
		switch(dim) {
		case CUBE: return GL13.GL_TEXTURE_CUBE_MAP;
		case DIM_1D: return GL11.GL_TEXTURE_1D;
		case DIM_2D: return GL11.GL_TEXTURE_2D;
		case DIM_3D: return GL12.GL_TEXTURE_3D;
		case DIM_1D_ARRAY: return GL30.GL_TEXTURE_1D_ARRAY;
		case DIM_2D_ARRAY: return GL30.GL_TEXTURE_2D_ARRAY;
		case CUBE_ARRAY: return GL40.GL_TEXTURE_CUBE_MAP_ARRAY;
		default: return -1;
		}
	}

	/** Gets the ZEngine pixel format for a given texture format and component type.
	 * 
	 * @param format Texture format
	 * @param type Component type
	 * @return Pixel format, or <b>null</b> if no matching format was found
	 */
	public static ZEPixelFormat getZEPixelFormat(int format, int type) {
		switch(format) {
		case GL11.GL_RED:
			switch(type) {
			case GL11.GL_UNSIGNED_BYTE:  return ZEPixelFormat.R8_UINT;
			case GL11.GL_BYTE:           return ZEPixelFormat.R8_SINT;
			case GL11.GL_UNSIGNED_SHORT: return ZEPixelFormat.R16_UINT;
			case GL11.GL_SHORT:          return ZEPixelFormat.R16_SINT;
			case GL11.GL_UNSIGNED_INT:   return ZEPixelFormat.R32_UINT;
			case GL11.GL_INT:            return ZEPixelFormat.R32_SINT;
			case GL11.GL_FLOAT:          return ZEPixelFormat.R32_FLOAT;
			case GL11.GL_DOUBLE:         return ZEPixelFormat.R64_FLOAT;
			}
		case GL30.GL_RG:
			switch(type) {
			case GL11.GL_UNSIGNED_BYTE:  return ZEPixelFormat.R8G8_UINT;
			case GL11.GL_BYTE:           return ZEPixelFormat.R8G8_SINT;
			case GL11.GL_UNSIGNED_SHORT: return ZEPixelFormat.R16G16_UINT;
			case GL11.GL_SHORT:          return ZEPixelFormat.R16G16_SINT;
			case GL11.GL_UNSIGNED_INT:   return ZEPixelFormat.R32G32_UINT;
			case GL11.GL_INT:            return ZEPixelFormat.R32G32_SINT;
			case GL11.GL_FLOAT:          return ZEPixelFormat.R32G32_FLOAT;
			case GL11.GL_DOUBLE:         return ZEPixelFormat.R64G64_FLOAT;
			}
		case GL11.GL_RGB:
			switch(type) {
			case GL11.GL_UNSIGNED_BYTE:  return ZEPixelFormat.R8G8B8_UINT;
			case GL11.GL_BYTE:           return ZEPixelFormat.R8G8B8_SINT;
			case GL11.GL_UNSIGNED_SHORT: return ZEPixelFormat.R16G16B16_UINT;
			case GL11.GL_SHORT:          return ZEPixelFormat.R16G16B16_SINT;
			case GL11.GL_UNSIGNED_INT:   return ZEPixelFormat.R32G32B32_UINT;
			case GL11.GL_INT:            return ZEPixelFormat.R32G32B32_SINT;
			case GL11.GL_FLOAT:          return ZEPixelFormat.R32G32B32_FLOAT;
			case GL11.GL_DOUBLE:         return ZEPixelFormat.R64G64B64_FLOAT;
			}
		case GL11.GL_RGBA:
			switch(type) {
			case GL11.GL_UNSIGNED_BYTE:  return ZEPixelFormat.R8G8B8A8_UINT;
			case GL11.GL_BYTE:           return ZEPixelFormat.R8G8B8A8_SINT;
			case GL11.GL_UNSIGNED_SHORT: return ZEPixelFormat.R16G16B16A16_UINT;
			case GL11.GL_SHORT:          return ZEPixelFormat.R16G16B16A16_SINT;
			case GL11.GL_UNSIGNED_INT:   return ZEPixelFormat.R32G32B32A32_UINT;
			case GL11.GL_INT:            return ZEPixelFormat.R32G32B32A32_SINT;
			case GL11.GL_FLOAT:          return ZEPixelFormat.R32G32B32A32_FLOAT;
			case GL11.GL_DOUBLE:         return ZEPixelFormat.R64G64B64A64_FLOAT;
			}
		case GL12.GL_BGRA:
			switch(type) {
			case GL12.GL_UNSIGNED_INT_8_8_8_8_REV: return ZEPixelFormat.A8R8G8B8_UINT;
			}
		}
		return null;
	}

}
