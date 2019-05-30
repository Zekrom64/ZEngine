package com.zekrom_64.ze.base.backend.render.pipeline;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.zekrom_64.mathlib.matrix.impl.Matrix2x2F;
import com.zekrom_64.mathlib.matrix.impl.Matrix3x3F;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4F;
import com.zekrom_64.mathlib.tuple.impl.Vector2F;
import com.zekrom_64.mathlib.tuple.impl.Vector2I;
import com.zekrom_64.mathlib.tuple.impl.Vector3F;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.mathlib.tuple.impl.Vector4F;
import com.zekrom_64.mathlib.tuple.impl.Vector4I;
import com.zekrom_64.ze.base.backend.render.obj.ZEBuffer;
import com.zekrom_64.ze.base.backend.render.obj.ZESampler;
import com.zekrom_64.ze.base.backend.render.obj.ZETexture;

/** A bind set is a set of pipeline bindings to be bound to a render pipeline
 * during a render pass.
 * 
 * @author Zekrom_64
 *
 */
public interface ZEPipelineBindSet {

	/** Gets the pool this bind set is allocated from.
	 * 
	 * @return Bind pool
	 */
	public ZEPipelineBindPool getPool();
	
	/** Creates a new update for this bind set.
	 * 
	 * @return New update
	 */
	public ZEBindSetUpdate newUpdate();
	
	/** A bind set update buffers a list of updates to send to a bind set.
	 * 
	 * @author Zekrom_64
	 *
	 */
	public static interface ZEBindSetUpdate {
		
		/** Adds a new write update and returns the index of the
		 * write update.
		 * 
		 * @return New write update index
		 */
		public int addWrite();
		
		/** Allocates a group of write updates and returns the
		 * first index of the updates.
		 * 
		 * @param writes Number of writes to allocate
		 * @return Base write update index
		 */
		public int allocateWrites(int writes);
		
		/** Gets the number of writes buffered for this update.
		 * 
		 * @return Number of buffered writes
		 */
		public int getWriteCount();
		
		// Primitive writes
		
		/** Writes a single float to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 Float to write
		 */
		public void write(int writeIndex, int binding, float v0);
		/** Writes a 2-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First float to write
		 * @param v1 Second float to write
		 */
		public void write(int writeIndex, int binding, float v0, float v1);
		/** Writes a 3-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First float to write
		 * @param v1 Second float to write
		 * @param v2 Third float to write
		 */
		public void write(int writeIndex, int binding, float v0, float v1, float v2);
		/** Writes a 4-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First float to write
		 * @param v1 Second float to write
		 * @param v2 Third float to write
		 * @param v3 Fourth float to write
		 */
		public void write(int writeIndex, int binding, float v0, float v1, float v2, float v3);
		/** Writes a 2-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector2F v) { write(writeIndex, binding, v.x, v.y); }
		/** Writes a 3-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector3F v) { write(writeIndex, binding, v.x, v.y, v.z); }
		/** Writes a 4-element float vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector4F v) { write(writeIndex, binding, v.x, v.y, v.z, v.w); }
		/** Writes a single int to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 Integer to write
		 */
		public void write(int writeIndex, int binding, int v0);
		/** Writes a 2-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First integer to write
		 * @param v1 Second integer to write
		 */
		public void write(int writeIndex, int binding, int v0, int v1);
		/** Writes a 3-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First integer to write
		 * @param v1 Second integer to write
		 * @param v2 Third integer to write
		 */
		public void write(int writeIndex, int binding, int v0, int v1, int v2);
		/** Writes a 4-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v0 First integer to write
		 * @param v1 Second integer to write
		 * @param v2 Third integer to write
		 * @param v3 Fourth integer to write
		 */
		public void write(int writeIndex, int binding, int v0, int v1, int v2, int v3);
		/** Writes a 2-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector2I v) { write(writeIndex, binding, v.x, v.y); }
		/** Writes a 3-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector3I v) { write(writeIndex, binding, v.x, v.y, v.z); }
		/** Writes a 4-element int vector to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Vector to write
		 */
		public default void write(int writeIndex, int binding, Vector4I v) { write(writeIndex, binding, v.x, v.y, v.z, v.w); }
		/** Writes an array of floats to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Array to write
		 */
		public void write(int writeIndex, int binding, float[] v);
		/** Writes an array of floats to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Array to write
		 */
		public void write(int writeIndex, int binding, FloatBuffer v);
		/** Writes an array of ints to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Array to write
		 */
		public void write(int writeIndex, int binding, int[] v);
		/** Writes an array of ints to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param v Array to write
		 */
		public void write(int writeIndex, int binding, IntBuffer v);
		/** Writes a 2x2 float matrix to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param m Matrix to write
		 */
		public void write(int writeIndex, int binding, Matrix2x2F m);
		/** Writes a 3x3 float matrix to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param m Matrix to write
		 */
		public void write(int writeIndex, int binding, Matrix3x3F m);
		/** Writes a 4x4 float matrix to the binding. Requires {@link
		 * com.zekrom_64.ze.base.backend.render.ZERenderBackend#FEATURE_BIND_SET_PRIMTIVE_WRITE FEATURE_BIND_SET_PRIMITIVE_WRITE}.
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param m Matrix to write
		 */
		public void write(int writeIndex, int binding, Matrix4x4F m);
		
		// Normal writes
		
		/** Writes data from the given buffer to the bound object starting at a byte
		 * offset from the start of the buffer and writing a number of bytes.
		 * 
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param bufferType The type of buffer binding to write
		 * @param buffer The buffer containing the data
		 * @param offset The offset into the buffer to start reading
		 * @param size The number of bytes to write, -1 writes the full size of the data structure
		 */
		public void write(int writeIndex, int binding, ZEPipelineBindType bufferType, ZEBuffer buffer, long offset, long size);
		
		/** Writes a texture and/or sampler to the bound object.
		 * 
		 * @param writeIndex Update write index
		 * @param binding Binding location
		 * @param textureType The type of texture binding to write
		 * @param sampler The sampler to use with the binding
		 * @param texture The texture to use with the binding
		 */
		public void write(int writeIndex, int binding, ZEPipelineBindType textureType, ZESampler sampler, ZETexture texture);
		
	}
	
}
