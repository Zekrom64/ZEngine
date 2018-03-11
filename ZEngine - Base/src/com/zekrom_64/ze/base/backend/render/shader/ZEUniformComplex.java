package com.zekrom_64.ze.base.backend.render.shader;

import java.nio.ByteBuffer;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3Double;
import com.zekrom_64.mathlib.matrix.impl.Matrix3x3Float;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4Double;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4Float;
import com.zekrom_64.mathlib.tuple.impl.Vector2Double;
import com.zekrom_64.mathlib.tuple.impl.Vector2Float;
import com.zekrom_64.mathlib.tuple.impl.Vector2Int;
import com.zekrom_64.mathlib.tuple.impl.Vector3Double;
import com.zekrom_64.mathlib.tuple.impl.Vector3Float;
import com.zekrom_64.mathlib.tuple.impl.Vector3Int;
import com.zekrom_64.mathlib.tuple.impl.Vector4Double;
import com.zekrom_64.mathlib.tuple.impl.Vector4Float;
import com.zekrom_64.mathlib.tuple.impl.Vector4Int;

public interface ZEUniformComplex<T> extends ZEUniform {

	public T get();
	
	public void set(T t);
	
	
	public static interface ZEUniformFloatArray extends ZEUniformComplex<float[]> { }
	
	public static interface ZEUniformIntArray extends ZEUniformComplex<int[]> { }
	
	public static interface ZEUniformUIntArray extends ZEUniformIntArray { }
	
	public static interface ZEUniformDoubleArray extends ZEUniformComplex<double[]> { }
	
	public static interface ZEUniformBoolArray extends ZEUniformComplex<boolean[]> { }
	
	
	public static interface ZEUniformVec2 extends ZEUniformComplex<Vector2Float> {
		
		public void set(float x, float y);
		
	}
	
	public static interface ZEUniformVec3 extends ZEUniformComplex<Vector3Float> {
		
		public void set(float x, float y, float z);
		
	}
	
	public static interface ZEUniformVec4 extends ZEUniformComplex<Vector4Float> {
		
		public void set(float x, float y, float z, float w);
		
	}
	
	public static interface ZEUniformMat3 extends ZEUniformComplex<Matrix3x3Float> { }
	
	public static interface ZEUniformMat4 extends ZEUniformComplex<Matrix4x4Float> { }
	
	public static interface ZEUniformVec2i extends ZEUniformComplex<Vector2Int> {
		
		public void set(int x, int y);
		
	}
	
	public static interface ZEUniformVec3i extends ZEUniformComplex<Vector3Int> {
		
		public void set(int x, int y, int z);
		
	}
	
	public static interface ZEUniformVec4i extends ZEUniformComplex<Vector4Int> {
		
		public void set(int x, int y, int z, int w);
		
	}
	
	public static interface ZEUniformVec2d extends ZEUniformComplex<Vector2Double> {
		
		public void set(double x, double y);
		
	}
	
	public static interface ZEUniformVec3d extends ZEUniformComplex<Vector3Double> {
		
		public void set(double x, double y, double z);
		
	}
	
	public static interface ZEUniformVec4d extends ZEUniformComplex<Vector4Double> {
		
		public void set(double x, double y, double z, double w);
		
	}

	public static interface ZEUniformMat3d extends ZEUniformComplex<Matrix3x3Double> { }
	
	public static interface ZEUniformMat4d extends ZEUniformComplex<Matrix4x4Double> { }
	
	
	public static interface ZEUniformVec2Array extends ZEUniformComplex<Vector2Float[]> { }
	
	public static interface ZEUniformVec3Array extends ZEUniformComplex<Vector3Float[]> { }
	
	public static interface ZEUniformVec4Array extends ZEUniformComplex<Vector4Float[]> { }
	
	public static interface ZEUniformCustom extends ZEUniformComplex<ByteBuffer> { }
	
}
