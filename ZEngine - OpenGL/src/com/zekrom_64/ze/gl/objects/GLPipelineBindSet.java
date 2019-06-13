package com.zekrom_64.ze.gl.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL43;

import com.zekrom_64.mathlib.matrix.impl.Matrix2x2F;
import com.zekrom_64.mathlib.matrix.impl.Matrix3x3F;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4F;
import com.zekrom_64.mathlib.tuple.impl.Vector4F;
import com.zekrom_64.mathlib.tuple.impl.Vector4I;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindSet;
import com.zekrom_64.ze.base.backend.render.pipeline.ZEPipelineBindType;
import com.zekrom_64.ze.gl.GLExtensions;
import com.zekrom_64.ze.gl.GLRenderBackend;

public class GLPipelineBindSet implements ZEPipelineBindSet {

	public final GLRenderBackend backend;
	public final Map<Integer,GLPipelineBindLayout.GLPipelineBinding> pipelineBindings;
	public final Map<Integer,GLUniformWrite> unboundWrites;
	public final int numTextureBindings;
	public final int numImageBindings;
	public boolean currentlyBound;
	
	public GLPipelineBindSet(GLRenderBackend backend, Map<Integer,GLPipelineBindLayout.GLPipelineBinding> bindings) {
		this.backend = backend;
		pipelineBindings = Collections.unmodifiableMap(bindings);
		HashMap<Integer,GLUniformWrite> writes = new HashMap<>();
		for(Integer key : bindings.keySet()) writes.put(key, new GLUniformWrite(key));
		unboundWrites = Collections.unmodifiableMap(writes);
		int nTexBindings = 0, nImageBindings = 0;
		for(GLPipelineBindLayout.GLPipelineBinding binding : pipelineBindings.values()) {
			switch(binding.bindType) {
			case COMBINED_IMAGE_SAMPLER:
				nTexBindings++;
				break;
			case STORAGE_IMAGE:
				nImageBindings++;
				break;
			case UNIFORM_BUFFER:
			case STORAGE_BUFFER:
				break;
			}
		}
		numTextureBindings = nTexBindings;
		numImageBindings = nImageBindings;
	}
	
	public enum GLUniformWriteType {
		VEC1,
		VEC2,
		VEC3,
		VEC4,
		IVEC1,
		IVEC2,
		IVEC3,
		IVEC4,
		MATRIX2,
		MATRIX3,
		MATRIX4,
		BUFFER,
		TEXTURE
	}
	
	public class GLUniformWrite {
		
		public GLUniformWrite() {}
		
		public GLUniformWrite(int binding) {
			this.binding = binding;
		}
		
		public int binding = 0;

		public GLUniformWriteType writeType;
		
		public Integer arrayElement = null;
		
		public final Vector4F vecf = new Vector4F();
		public final Vector4I veci = new Vector4I();
		public final Matrix4x4F mat = new Matrix4x4F();
		
		public ZEPipelineBindType bindType;
		
		public GLBuffer buffer;
		public long bufferOffset;
		public long bufferSize;
		
		public GLTexture texture;
		public GLSampler textureSampler;
		public int textureMipLevel;
		
		public void set(GLUniformWrite other) {
			binding = other.binding;
			writeType = other.writeType;
			vecf.set(other.vecf);
			veci.set(other.veci);
			arrayElement = other.arrayElement;
			bindType = other.bindType;
			buffer = other.buffer;
			bufferOffset = other.bufferOffset;
			bufferSize = other.bufferSize;
			texture = other.texture;
			textureSampler = other.textureSampler;
			textureMipLevel = other.textureMipLevel;
		}
		
		private int getLocation() {
			return -1; // TODO: Compute location correctly
		}
		
		public void write() {
			if (!currentlyBound) return;
			int location = getLocation();
			GLExtensions exts = backend.getExtensions();
			if (bindType != null) {
				switch(writeType) {
				case BUFFER:
					switch(bindType) {
					case STORAGE_BUFFER:
						exts.glBindBufferRange(GL43.GL_SHADER_STORAGE_BUFFER, binding, buffer.bufferObject, bufferOffset, bufferSize);
						break;
					case UNIFORM_BUFFER:
						exts.glBindBufferRange(GL31.GL_UNIFORM_BUFFER, binding, buffer.bufferObject, bufferOffset, bufferSize);
						break;
					default: break;
					}
					break;
				case IVEC1: GL20.glUniform1i(location, veci.x); break;
				case IVEC2: GL20.glUniform2i(location, veci.x, veci.y);break;
				case IVEC3: GL20.glUniform3i(location, veci.x, veci.y, veci.z);break;
				case IVEC4: GL20.glUniform4i(location, veci.x, veci.y, veci.z, veci.w);break;
				case MATRIX2: GL20.glUniformMatrix2fv(location, false, new float[] {
						mat.m0x0, mat.m0x1,
						mat.m1x0, mat.m1x1
				}); break;
				case MATRIX3: GL20.glUniformMatrix3fv(location, false, new float[] {
						mat.m0x0, mat.m0x1, mat.m0x2,
						mat.m1x0, mat.m1x1, mat.m1x2,
						mat.m2x0, mat.m2x1, mat.m2x2
				}); break;
				case MATRIX4: GL20.glUniformMatrix3fv(location, false, new float[] {
						mat.m0x0, mat.m0x1, mat.m0x2, mat.m0x3,
						mat.m1x0, mat.m1x1, mat.m1x2, mat.m1x3,
						mat.m2x0, mat.m2x1, mat.m2x2, mat.m2x3,
						mat.m3x0, mat.m3x1, mat.m3x2, mat.m3x3
				}); break;
				case TEXTURE:
					switch(bindType) {
					case COMBINED_IMAGE_SAMPLER:
						break;
					case STORAGE_IMAGE:
						break;
					default:
						break;
					
					}
					break;
				case VEC1: GL20.glUniform1f(location, vecf.x); break;
				case VEC2: GL20.glUniform2f(location, vecf.x, vecf.y); break;
				case VEC3: GL20.glUniform3f(location, vecf.x, vecf.y, vecf.z); break;
				case VEC4: GL20.glUniform4f(location, vecf.x, vecf.y, vecf.z, vecf.w); break;
				}
			}
		}
		
	}
	
	public class GLBindSetUpdate implements ZEBindSetUpdate {
		
		public final ArrayList<GLUniformWrite> writes = new ArrayList<>();
		
		@Override
		public int addWrite() {
			int index = writes.size();
			writes.add(new GLUniformWrite());
			return index;
		}

		@Override
		public int allocateWrites(int writes) {
			int index = this.writes.size();
			while(writes-- > 0) this.writes.add(new GLUniformWrite());
			return index;
		}

		@Override
		public int getWriteCount() {
			return writes.size();
		}

		@Override
		public void setWriteArrayElement(int write, int element) {
			writes.get(write).arrayElement = element;
		}

		@Override
		public void clearWriteArrayElement(int write) {
			writes.get(write).arrayElement = null;
		}

		@Override
		public void write(int writeIndex, int binding, float v0) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.VEC1;
			w.vecf.x = v0;
		}

		@Override
		public void write(int writeIndex, int binding, float v0, float v1) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.VEC2;
			w.vecf.x = v0;
			w.vecf.y = v1;
		}

		@Override
		public void write(int writeIndex, int binding, float v0, float v1, float v2) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.VEC3;
			w.vecf.x = v0;
			w.vecf.y = v1;
			w.vecf.z = v2;
		}

		@Override
		public void write(int writeIndex, int binding, float v0, float v1, float v2, float v3) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.VEC4;
			w.vecf.x = v0;
			w.vecf.y = v1;
			w.vecf.z = v2;
			w.vecf.w = v3;
		}

		@Override
		public void write(int writeIndex, int binding, int v0) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.IVEC1;
			w.veci.x = v0;
		}

		@Override
		public void write(int writeIndex, int binding, int v0, int v1) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.IVEC2;
			w.veci.x = v0;
			w.veci.y = v1;
		}

		@Override
		public void write(int writeIndex, int binding, int v0, int v1, int v2) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.IVEC3;
			w.veci.x = v0;
			w.veci.y = v1;
			w.veci.z = v2;
		}

		@Override
		public void write(int writeIndex, int binding, int v0, int v1, int v2, int v3) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.IVEC4;
			w.veci.x = v0;
			w.veci.y = v1;
			w.veci.z = v2;
			w.veci.w = v3;
		}

		@Override
		public void write(int writeIndex, int binding, Matrix2x2F m) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.MATRIX2;
			w.mat.set(m);
		}

		@Override
		public void write(int writeIndex, int binding, Matrix3x3F m) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.MATRIX3;
			w.mat.set(m);
		}

		@Override
		public void write(int writeIndex, int binding, Matrix4x4F m) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.MATRIX4;
			w.mat.set(m);
		}

		@Override
		public void write(int writeIndex, int binding, ZEPipelineBindType bufferType, ZEBuffer buffer, long offset,
				long size) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.BUFFER;
			w.bindType = bufferType;
			w.buffer = (GLBuffer)buffer;
			w.bufferOffset = offset;
			w.bufferSize = size;
		}

		@Override
		public void write(int writeIndex, int binding, ZEPipelineBindType textureType, ZESampler sampler,
				ZETexture texture, int mipLevel) {
			GLUniformWrite w = writes.get(writeIndex);
			w.binding = binding;
			w.writeType = GLUniformWriteType.BUFFER;
			w.bindType = textureType;
			w.texture = (GLTexture)texture;
			w.textureSampler = (GLSampler)sampler;
			w.textureMipLevel = mipLevel;
		}

		@Override
		public void update() {
			for(GLUniformWrite w : writes) {
				w.write();
				unboundWrites.get(Integer.valueOf(w.binding)).set(w);
			}
		}
		
	}
	
	@Override
	public ZEBindSetUpdate newUpdate() {
		return new GLBindSetUpdate();
	}
	
	public void bind() {
		currentlyBound = true;
		for(GLUniformWrite w : unboundWrites.values()) w.write();
	}

}
