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

/** A bind set is a set of attachments to be bound to a render pipeline
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
	
	public static interface ZEBindSetUpdate {
		
		public int addWrite();
		
		public void allocateWrites(int writes);
		
		public int getWriteCount();
		
		public void write(int writeIndex, int binding, float v0);
		public void write(int writeIndex, int binding, float v0, float v1);
		public void write(int writeIndex, int binding, float v0, float v1, float v2);
		public void write(int writeIndex, int binding, float v0, float v1, float v2, float v3);
		public void write(int writeIndex, int binding, Vector2F v);
		public void write(int writeIndex, int binding, Vector3F v);
		public void write(int writeIndex, int binding, Vector4F v);
		public void write(int writeIndex, int binding, int v0);
		public void write(int writeIndex, int binding, int v0, int v1);
		public void write(int writeIndex, int binding, int v0, int v1, int v2);
		public void write(int writeIndex, int binding, int v0, int v1, int v2, int v3);
		public void write(int writeIndex, int binding, Vector2I v);
		public void write(int writeIndex, int binding, Vector3I v);
		public void write(int writeIndex, int binding, Vector4I v);
		public void write(int writeIndex, int binding, float[] v);
		public void write(int writeIndex, int binding, FloatBuffer v);
		public void write(int writeIndex, int binding, int[] v);
		public void write(int writeIndex, int binding, IntBuffer v);
		public void write(int writeIndex, int binding, Matrix2x2F m);
		public void write(int writeIndex, int binding, Matrix3x3F m);
		public void write(int writeIndex, int binding, Matrix4x4F m);
		public void write(int writeIndex, int binding, ZEPipelineBindType bufferType, ZEBuffer buffer, long offset, long size);
		public void write(int writeIndex, int binding, ZEPipelineBindType textureType, ZESampler sampler, ZETexture texture);
		
	}
	
}
