package com.zekrom_64.ze.gl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL41;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3Float;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4Float;
import com.zekrom_64.mathlib.tuple.impl.Vector2Float;
import com.zekrom_64.mathlib.tuple.impl.Vector3Float;
import com.zekrom_64.mathlib.tuple.impl.Vector4Float;
import com.zekrom_64.ze.base.backend.render.obj.ZETextureDimension;
import com.zekrom_64.ze.base.backend.render.shader.ZEAttributeDescription;
import com.zekrom_64.ze.base.backend.render.shader.ZEShader;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderCompiler;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderProgram;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderType;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderType.ZEShaderTypeImageSampler;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderType.ZEShaderTypeMatrix;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderType.ZEShaderTypePrimitive;
import com.zekrom_64.ze.base.backend.render.shader.ZEShaderType.ZEShaderTypeVector;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniform;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformBool;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformBoolArray;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformDoubleArray;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformFloatArray;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformIntArray;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformMat3;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformMat4;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformUIntArray;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec2;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec2Array;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec3;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec3Array;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec4;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformComplex.ZEUniformVec4Array;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformDouble;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformFloat;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformInt;
import com.zekrom_64.ze.base.backend.render.shader.ZEUniformUInt;
import com.zekrom_64.ze.base.util.PrimitiveType;

public class GLShaderCompiler implements ZEShaderCompiler {
	
	public static class GLShader implements ZEShader {

		public final int shaderId;
		
		public GLShader(int id) {
			shaderId = id;
		}
		
		@Override
		public String getShaderType() {
			return "GLSL";
		}

		@Override
		protected void finalize() throws Throwable {
			GL20.glDeleteShader(shaderId);
			super.finalize();
		}
		
		/** Gets the ZEngine equivalent uniform type from a GLSL uniform type value.
		 * 
		 * @param glslType GLSL uniform type
		 * @return ZEngine uniform type
		 */
		public static ZEShaderType getUniformType(int glslType) {
			if (glslType==-1) return null;
			switch(glslType) {
			case GL11.GL_FLOAT: return ZEShaderTypePrimitive.FLOAT;
			case GL11.GL_INT: return ZEShaderTypePrimitive.INT;
			case GL11.GL_DOUBLE: return ZEShaderTypePrimitive.DOUBLE;
			case GL11.GL_UNSIGNED_INT: return ZEShaderTypePrimitive.get(PrimitiveType.UINT);
			case GL20.GL_BOOL: return ZEShaderTypePrimitive.get(PrimitiveType.INT_BOOL);
			case GL20.GL_BOOL_VEC2: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT_BOOL), 2);
			case GL20.GL_BOOL_VEC3: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT_BOOL), 3);
			case GL20.GL_BOOL_VEC4: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT_BOOL), 4);
			case GL20.GL_FLOAT_MAT2: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 2), 2);
			case GL20.GL_FLOAT_MAT3: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 3), 3);
			case GL20.GL_FLOAT_MAT4: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), 4);
			case GL20.GL_FLOAT_VEC2: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 2);
			case GL20.GL_FLOAT_VEC3: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 3);
			case GL20.GL_FLOAT_VEC4: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4);
			case GL20.GL_INT_VEC2: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 2);
			case GL20.GL_INT_VEC3: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 3);
			case GL20.GL_INT_VEC4: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4);
			case GL20.GL_SAMPLER_1D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.DIM_1D);
			case GL20.GL_SAMPLER_2D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.DIM_2D);
			case GL20.GL_SAMPLER_3D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.DIM_3D);
			case GL20.GL_SAMPLER_CUBE: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.CUBE);
			case GL21.GL_FLOAT_MAT2x3: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 3), 2);
			case GL21.GL_FLOAT_MAT2x4: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), 2);
			case GL21.GL_FLOAT_MAT3x2: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 2), 3);
			case GL21.GL_FLOAT_MAT3x4: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), 3);
			case GL21.GL_FLOAT_MAT4x2: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), 2);
			case GL21.GL_FLOAT_MAT4x3: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), 3);
			case GL30.GL_UNSIGNED_INT_VEC2: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 2);
			case GL30.GL_UNSIGNED_INT_VEC3: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 3);
			case GL30.GL_UNSIGNED_INT_VEC4: return ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4);
			case GL30.GL_INT_SAMPLER_1D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.DIM_1D);
			case GL30.GL_INT_SAMPLER_2D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.DIM_2D);
			case GL30.GL_INT_SAMPLER_3D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.DIM_3D);
			case GL30.GL_INT_SAMPLER_CUBE: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.CUBE);
			case GL30.GL_UNSIGNED_INT_SAMPLER_1D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.DIM_1D);
			case GL30.GL_UNSIGNED_INT_SAMPLER_2D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.DIM_2D);
			case GL30.GL_UNSIGNED_INT_SAMPLER_3D: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.DIM_3D);
			case GL30.GL_UNSIGNED_INT_SAMPLER_CUBE: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.CUBE);
			case GL31.GL_SAMPLER_2D_RECT: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.RECTANGLE);
			case GL31.GL_SAMPLER_BUFFER: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.FLOAT), 4), ZETextureDimension.BUFFER_TEXTURE);
			case GL31.GL_INT_SAMPLER_2D_RECT: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.RECTANGLE);
			case GL31.GL_INT_SAMPLER_BUFFER: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.INT), 4), ZETextureDimension.BUFFER_TEXTURE);
			case GL31.GL_UNSIGNED_INT_SAMPLER_2D_RECT: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.RECTANGLE);
			case GL31.GL_UNSIGNED_INT_SAMPLER_BUFFER: return ZEShaderTypeImageSampler.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.UINT), 4), ZETextureDimension.BUFFER_TEXTURE);
			case GL40.GL_DOUBLE_MAT2: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.DOUBLE), 2), 2);
			case GL40.GL_DOUBLE_MAT2x3: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.DOUBLE), 3), 2);
			case GL40.GL_DOUBLE_MAT2x4: return ZEShaderTypeMatrix.get(ZEShaderTypeVector.get(ZEShaderTypePrimitive.get(PrimitiveType.DOUBLE), 4), 2);
			}
			return null;
		}

	}
	
	public static class GLShaderProgram implements ZEShaderProgram {
		
		// ARB_separate_shader_objects or OpenGL 4.1 can use glProgramUniform* to set uniforms
		private final boolean canUseFastUniforms = GLVersion.extensionExists("ARB_separate_shader_objects") || 
				(GLVersion.getFromGL().compareTo(GLVersion.VERSION_4_1) >= 0);
		/*
		// ARB_program_interface_query or OpenGL 4.3 can use glProgramInterface to query program info
		private final boolean canUseFastQuery = GLVersion.extensionExists("ARB_program_interface_query") ||
				(GLVersion.getFromGL().compareTo(GLVersion.VERSION_4_3) >= 0);
		*/
		public final int programId;
		
		public GLShaderProgram(int id) {
			programId = id;
		}

		@Override
		public ZEUniform getUniform(String name) {
			int loc = GL20.glGetUniformLocation(programId, name);
			if (loc==-1) return null;
			int[] pType = new int[1];
			GL20.glGetActiveUniform(programId, loc, null, null, pType, null);
			return createUniform(pType[0]);
		}

		@Override
		public List<ZEUniform> getUniforms() {
			MemoryStack sp = MemoryStack.stackGet();
			int bp = sp.getPointer();
			ArrayList<ZEUniform> uniforms = new ArrayList<>();
			int nameLen = GL20.glGetProgrami(programId, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH) + 1;
			ByteBuffer pName = sp.calloc(nameLen);
			int nUniforms = GL20.glGetProgrami(programId, GL20.GL_ACTIVE_UNIFORMS);
			for(int i = 0; i < nUniforms; i++) {
				GL20.glGetActiveUniform(programId, i, (IntBuffer)null, null, null, pName);
				uniforms.add(createUniform(GL20.glGetUniformLocation(programId, pName)));
			}
			sp.setPointer(bp);
			return uniforms;
		}
		
		private ZEUniform createUniform(int loc) {
			int[] pSize = new int[1];
			int[] pType = new int[1];
			GL20.glGetActiveUniform(programId, loc, null, pSize, pType, null);
			if (pSize[0]>1) {
				switch(pType[0]) {
				case GL20.GL_BOOL: return new GLUniformBoolArray(loc);
				case GL11.GL_INT: return new GLUniformIntArray(loc);
				case GL11.GL_UNSIGNED_INT: return new GLUniformUIntArray(loc);
				case GL11.GL_FLOAT: return new GLUniformFloatArray(loc);
				case GL11.GL_DOUBLE: return new GLUniformDoubleArray(loc);
				case GL20.GL_FLOAT_VEC2: return new GLUniformVec2Array(loc);
				case GL20.GL_FLOAT_VEC3: return new GLUniformVec3Array(loc);
				case GL20.GL_FLOAT_VEC4: return new GLUniformVec4Array(loc);
				}
			} else {
				switch(pType[0]) {
				case GL20.GL_BOOL: return new GLUniformBool(loc);
				case GL11.GL_INT: return new GLUniformInt(loc);
				case GL11.GL_UNSIGNED_INT: return new GLUniformUInt(loc);
				case GL11.GL_FLOAT: return new GLUniformFloat(loc);
				case GL11.GL_DOUBLE: return new GLUniformDouble(loc);
				case GL20.GL_FLOAT_VEC2: return new GLUniformVec2(loc);
				case GL20.GL_FLOAT_VEC3: return new GLUniformVec3(loc);
				case GL20.GL_FLOAT_VEC4: return new GLUniformVec4(loc);
				case GL20.GL_FLOAT_MAT3: return new GLUniformMat3(loc);
				case GL20.GL_FLOAT_MAT4: return new GLUniformMat4(loc);
				}
			}
			return null;
		}

		@Override
		public ZEAttributeDescription getAttribute(String name) {
			return null;
		}

		@Override
		public List<ZEAttributeDescription> getAttributes() {
			return null;
		}
		
		private class GLUniform implements ZEUniform {

			protected int location;
			
			public GLUniform(int loc) {
				location = loc;
			}

			@Override
			public String getName() {
				MemoryStack sp = MemoryStack.stackGet();
				int bp = sp.getPointer();
				int maxlen = GL20.glGetProgrami(programId, GL20.GL_ACTIVE_UNIFORM_MAX_LENGTH) + 1;
				ByteBuffer pName = sp.calloc(maxlen);
				GL20.glGetActiveUniform(programId, location, (IntBuffer)null, null, null, pName);
				String name = MemoryUtil.memASCII(pName);
				sp.setPointer(bp);
				return name;
			}
			
			@Override
			public int getLength() {
				int[] pSize = new int[1];
				GL20.glGetActiveUniform(programId, location, null, pSize, null, null);
				return pSize[0];
			}

			@Override
			public ZEShaderType getShaderType() {
				int[] pType = new int[1];
				GL20.glGetActiveUniform(programId, location, null, null, pType, null);
				return GLShader.getUniformType(pType[0]);
			}
			
		}

		public class GLUniformBool extends GLUniform implements ZEUniformBool {

			public GLUniformBool(int loc) {
				super(loc);
			}

			@Override
			public void set(boolean value) {
				if (canUseFastUniforms) GL41.glProgramUniform1i(programId, location, value ? 1 : 0);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1i(location, value ? 1 : 0);
				}
			}

			@Override
			public boolean get() {
				return GL20.glGetUniformi(programId, location) != 0;
			}
				
		}

		public class GLUniformInt extends GLUniform implements ZEUniformInt {

			public GLUniformInt(int loc) {
				super(loc);
			}

			@Override
			public void set(int value) {
				if (canUseFastUniforms) GL41.glProgramUniform1i(programId, location, value);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1i(location, value);
				}
			}

			@Override
			public int get() {
				return GL20.glGetUniformi(programId, location);
			}
			
		}
		
		public class GLUniformUInt extends GLUniformInt implements ZEUniformUInt {

			public GLUniformUInt(int loc) {
				super(loc);
			}
			
		}

		public class GLUniformFloat extends GLUniform implements ZEUniformFloat {
			
			public GLUniformFloat(int loc) {
				super(loc);
			}

			@Override
			public void set(float f) {
				if (canUseFastUniforms) GL41.glProgramUniform1f(programId, location, f);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1f(location, f);
				}
			}

			@Override
			public float get() {
				return GL20.glGetUniformf(programId, location);
			}
			
		}

		public class GLUniformDouble extends GLUniform implements ZEUniformDouble {

			public GLUniformDouble(int loc) {
				super(loc);
			}

			@Override
			public void set(double value) {
				if (canUseFastUniforms) GL41.glProgramUniform1d(programId, location, value);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL40.glUniform1d(location, value);
				}
			}

			@Override
			public double get() {
				return GL40.glGetUniformd(programId, location);
			}
			
		}

		public class GLUniformVec2 extends GLUniform implements ZEUniformVec2 {

			public GLUniformVec2(int loc) {
				super(loc);
			}
			
			@Override
			public Vector2Float get() {
				float[] pVec = new float[2];
				GL20.glGetUniformfv(programId, location, pVec);
				return new Vector2Float(pVec);
			}

			@Override
			public void set(Vector2Float t) {
				set(t.x, t.y);
			}

			@Override
			public void set(float x, float y) {
				if (canUseFastUniforms) GL41.glProgramUniform2f(programId, location, x, y);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform2f(location, x, y);
				}
			}
			
		}

		public class GLUniformVec3 extends GLUniform implements ZEUniformVec3 {

			public GLUniformVec3(int loc) {
				super(loc);
			}
			
			@Override
			public Vector3Float get() {
				float[] pVec = new float[3];
				GL20.glGetUniformfv(programId, location, pVec);
				return new Vector3Float(pVec);
			}

			@Override
			public void set(Vector3Float t) {
				set(t.x, t.y, t.z);
			}

			@Override
			public void set(float x, float y, float z) {
				if (canUseFastUniforms) GL41.glProgramUniform3f(programId, location, x, y, z);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform3f(location, x, y, z);
				}
			}
			
		}

		public class GLUniformVec4 extends GLUniform implements ZEUniformVec4 {

			public GLUniformVec4(int loc) {
				super(loc);
			}
			
			@Override
			public Vector4Float get() {
				float[] pVec = new float[4];
				GL20.glGetUniformfv(programId, location, pVec);
				return new Vector4Float(pVec);
			}

			@Override
			public void set(Vector4Float t) {
				set(t.x, t.y, t.z, t.w);
			}

			@Override
			public void set(float x, float y, float z, float w) {
				if (canUseFastUniforms) GL41.glProgramUniform4f(programId, location, x, y, z, w);
			}
			
		}
		
		public class GLUniformMat3 extends GLUniform implements ZEUniformMat3 {

			public GLUniformMat3(int loc) {
				super(loc);
			}

			@Override
			public Matrix3x3Float get() {
				float[] pMat = new float[9];
				GL20.glGetUniformfv(programId, location, pMat);
				return new Matrix3x3Float(pMat);
			}

			@Override
			public void set(Matrix3x3Float t) {
				float[] pMat = new float[9];
				for(int x = 0; x < 3; x++)
					for(int y = 0; y < 3; y++) pMat[x + (y * 3)] = t.getFloat(x,  y);
				if (canUseFastUniforms) GL41.glProgramUniformMatrix3fv(programId, location, false, pMat);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniformMatrix3fv(location, false, pMat);
				}
			}
			
		}

		public class GLUniformMat4 extends GLUniform implements ZEUniformMat4 {

			public GLUniformMat4(int loc) {
				super(loc);
			}

			@Override
			public Matrix4x4Float get() {
				float[] pMat = new float[16];
				GL20.glGetUniformfv(programId, location, pMat);
				return new Matrix4x4Float(pMat);
			}

			@Override
			public void set(Matrix4x4Float t) {
				float[] pMat = new float[16];
				for(int x = 0; x < 4; x++)
					for(int y = 0; y < 4; y++) pMat[x + (y * 4)] = t.getFloat(x,  y);
				if (canUseFastUniforms) GL41.glProgramUniformMatrix4fv(programId, location, false, pMat);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniformMatrix4fv(location, false, pMat);
				}
			}
			
		}

		
		public class GLUniformFloatArray extends GLUniform implements ZEUniformFloatArray {

			public GLUniformFloatArray(int loc) {
				super(loc);
			}

			@Override
			public float[] get() {
				float[] pVals = new float[getLength()];
				GL20.glGetUniformfv(programId, location, pVals);
				return pVals;
			}

			@Override
			public void set(float[] t) {
				if (canUseFastUniforms) GL41.glProgramUniform1fv(programId, location, t);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1fv(location, t);
				}
			}
			
		}
		
		public class GLUniformIntArray extends GLUniform implements ZEUniformIntArray {

			public GLUniformIntArray(int loc) {
				super(loc);
			}

			@Override
			public int[] get() {
				int[] pVals = new int[getLength()];
				GL20.glGetUniformiv(programId, location, pVals);
				return pVals;
			}

			@Override
			public void set(int[] t) {
				if (canUseFastUniforms) GL41.glProgramUniform1iv(programId, location, t);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1iv(location, t);
				}
			}
			
		}

		public class GLUniformBoolArray extends GLUniform implements ZEUniformBoolArray {

			public GLUniformBoolArray(int loc) {
				super(loc);
			}

			@Override
			public boolean[] get() {
				int[] pValsi = new int[getLength()];
				GL20.glGetUniformiv(programId, location, pValsi);
				boolean[] pVals = new boolean[pValsi.length];
				for(int i = 0; i < pVals.length; i++) pVals[i] = pValsi[i] != 0;
				return pVals;
			}

			@Override
			public void set(boolean[] t) {
				int[] pVals = new int[t.length];
				for(int i = 0; i < pVals.length; i++) pVals[i] = t[i] ? 1 : 0;
				if (canUseFastUniforms) GL41.glProgramUniform1iv(programId, location, pVals);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform1iv(location, pVals);
				}
			}
			
		}

		public class GLUniformUIntArray extends GLUniformIntArray implements ZEUniformUIntArray {

			public GLUniformUIntArray(int loc) {
				super(loc);
			}
		}

		public class GLUniformDoubleArray extends GLUniform implements ZEUniformDoubleArray {

			public GLUniformDoubleArray(int loc) {
				super(loc);
			}

			@Override
			public double[] get() {
				double[] pVals = new double[getLength()];
				GL40.glGetUniformdv(programId, location, pVals);
				return pVals;
			}

			@Override
			public void set(double[] t) {
				if (canUseFastUniforms) GL41.glProgramUniform1dv(programId, location, t);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL40.glUniform1dv(location, t);
				}
			}
			
		}

		public class GLUniformVec2Array extends GLUniform implements ZEUniformVec2Array {

			public GLUniformVec2Array(int loc) {
				super(loc);
			}

			@Override
			public Vector2Float[] get() {
				Vector2Float[] vals = new Vector2Float[getLength()];
				float[] pVals = new float[vals.length * 2];
				GL20.glGetUniformfv(programId, location, pVals);
				for(int i = 0; i < vals.length; i++) {
					int j = i * 2;
					vals[i] = new Vector2Float(pVals[j], pVals[j + 1]);
				}
				return vals;
			}

			@Override
			public void set(Vector2Float[] t) {
				float[] vals = new float[t.length*2];
				for(int i = 0; i < t.length; i++) {
					int j = i * 2;
					Vector2Float val = t[i];
					vals[j] = val.x;
					vals[j + 1] = val.y;
				}
				if (canUseFastUniforms) GL41.glProgramUniform2fv(programId, location, vals);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform2fv(location, vals);
				}
			}
			
		}

		public class GLUniformVec3Array extends GLUniform implements ZEUniformVec3Array {

			public GLUniformVec3Array(int loc) {
				super(loc);
			}

			@Override
			public Vector3Float[] get() {
				Vector3Float[] vals = new Vector3Float[getLength()];
				float[] pVals = new float[vals.length*3];
				GL20.glGetUniformfv(programId, location, pVals);
				for(int i = 0; i < vals.length; i++) {
					int j = i * 3;
					vals[i] = new Vector3Float(pVals[j], pVals[j + 1], pVals[j + 2]);
				}
				return vals;
			}

			@Override
			public void set(Vector3Float[] t) {
				float[] pVals = new float[t.length*3];
				for(int i = 0; i < t.length; i++) {
					int j = i * 3;
					Vector3Float val = t[i];
					pVals[j] = val.x;
					pVals[j+1] = val.y;
					pVals[j+2] = val.z;
				}
				if (canUseFastUniforms) GL41.glProgramUniform3fv(programId, location, pVals);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform3fv(location, pVals);
				}
			}
			
		}

		public class GLUniformVec4Array extends GLUniform implements ZEUniformVec4Array {

			public GLUniformVec4Array(int loc) {
				super(loc);
			}

			@Override
			public Vector4Float[] get() {
				Vector4Float vals[] = new Vector4Float[getLength()];
				float[] pVals = new float[vals.length * 4];
				GL20.glGetUniformfv(programId, location, pVals);
				for(int i = 0; i < vals.length; i++) {
					int j = i * 4;
					vals[i] = new Vector4Float(pVals[j], pVals[j+1], pVals[j+2], pVals[j+3]);
				}
				return vals;
			}

			@Override
			public void set(Vector4Float[] t) {
				float[] pVals = new float[t.length * 4];
				for(int i = 0; i < t.length; i++) {
					int j = i * 4;
					Vector4Float val = t[i];
					pVals[j] = val.x;
					pVals[j+1] = val.y;
					pVals[j+2] = val.z;
					pVals[j+3] = val.w;
				}
				if (canUseFastUniforms) GL41.glProgramUniform4fv(programId, location, pVals);
				else {
					if (GL11.glGetInteger(GL20.GL_CURRENT_PROGRAM)!=programId) GL20.glUseProgram(programId);
					GL20.glUniform4fv(location, pVals);
				}
			}
			
		}
 		
	}
	
	@Override
	public ZEShader compileShader(Object src, String type, String shaderType) {
		if (shaderType!=null&&!shaderType.equals(ZEShaderCompiler.SHADER_SOURCE_TYPE_GLSL)) {
			return null;
		}
		int glsType = -1;
		switch(type) {
		case ZEShader.SHADER_TYPE_VERTEX: glsType = GL20.GL_VERTEX_SHADER; break;
		case ZEShader.SHADER_TYPE_FRAGMENT: glsType = GL20.GL_FRAGMENT_SHADER; break;
		case ZEShader.SHADER_TYPE_GEOMETRY: glsType = GL32.GL_GEOMETRY_SHADER; break;
		case ZEShader.SHADER_TYPE_TESSELLATION_CONTROL: glsType = GL40.GL_TESS_CONTROL_SHADER; break;
		case ZEShader.SHADER_TYPE_TESSELLATION_EVALUATION: glsType = GL40.GL_TESS_EVALUATION_SHADER; break;
		default: { return null;
		}
		}
		int shaderId = GL20.glCreateShader(glsType);
		if (shaderId==0) {
			return null;
		}
		return new GLShader(shaderId);
	}

	@Override
	public ZEShaderProgram compileShaderProgram(ZEShader... modules) {
		int[] shaders = new int[modules.length];
		for(int i = 0; i < shaders.length; i++) {
			GLShader gls = (GLShader)modules[i];
			shaders[i] = gls.shaderId;
		}
		int prgm = GL20.glCreateProgram();
		if (prgm==0) return null;
		for(int shader : shaders) GL20.glAttachShader(prgm, shader);
		return new GLShaderProgram(prgm);
	}

}
