package com.zekrom_64.ze.base.backend.render.shader;

import java.nio.ByteBuffer;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3D;
import com.zekrom_64.mathlib.matrix.impl.Matrix3x3F;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4D;
import com.zekrom_64.mathlib.matrix.impl.Matrix4x4F;
import com.zekrom_64.mathlib.tuple.impl.Vector2D;
import com.zekrom_64.mathlib.tuple.impl.Vector2F;
import com.zekrom_64.mathlib.tuple.impl.Vector2I;
import com.zekrom_64.mathlib.tuple.impl.Vector3D;
import com.zekrom_64.mathlib.tuple.impl.Vector3F;
import com.zekrom_64.mathlib.tuple.impl.Vector3I;
import com.zekrom_64.mathlib.tuple.impl.Vector4D;
import com.zekrom_64.mathlib.tuple.impl.Vector4F;
import com.zekrom_64.mathlib.tuple.impl.Vector4I;

public interface ZEUniformComplex<T> extends ZEUniform {

	public T get();
	
	public void set(T t);
	
	
	public static interface ZEUniformFloatArray extends ZEUniformComplex<float[]> { }
	
	public static interface ZEUniformIntArray extends ZEUniformComplex<int[]> { }
	
	public static interface ZEUniformUIntArray extends ZEUniformIntArray { }
	
	public static interface ZEUniformDoubleArray extends ZEUniformComplex<double[]> { }
	
	public static interface ZEUniformBoolArray extends ZEUniformComplex<boolean[]> { }
	
	
	public static interface ZEUniformVec2 extends ZEUniformComplex<Vector2F> {
		
		public void set(float x, float y);
		
	}
	
	public static interface ZEUniformVec3 extends ZEUniformComplex<Vector3F> {
		
		public void set(float x, float y, float z);
		
	}
	
	public static interface ZEUniformVec4 extends ZEUniformComplex<Vector4F> {
		
		public void set(float x, float y, float z, float w);
		
	}
	
	public static interface ZEUniformMat3 extends ZEUniformComplex<Matrix3x3F> { }
	
	public static interface ZEUniformMat4 extends ZEUniformComplex<Matrix4x4F> { }
	
	public static interface ZEUniformVec2i extends ZEUniformComplex<Vector2I> {
		
		public void set(int x, int y);
		
	}
	
	public static interface ZEUniformVec3i extends ZEUniformComplex<Vector3I> {
		
		public void set(int x, int y, int z);
		
	}
	
	public static interface ZEUniformVec4i extends ZEUniformComplex<Vector4I> {
		
		public void set(int x, int y, int z, int w);
		
	}
	
	public static interface ZEUniformVec2d extends ZEUniformComplex<Vector2D> {
		
		public void set(double x, double y);
		
	}
	
	public static interface ZEUniformVec3d extends ZEUniformComplex<Vector3D> {
		
		public void set(double x, double y, double z);
		
	}
	
	public static interface ZEUniformVec4d extends ZEUniformComplex<Vector4D> {
		
		public void set(double x, double y, double z, double w);
		
	}

	public static interface ZEUniformMat3d extends ZEUniformComplex<Matrix3x3D> { }
	
	public static interface ZEUniformMat4d extends ZEUniformComplex<Matrix4x4D> { }
	
	
	public static interface ZEUniformVec2Array extends ZEUniformComplex<Vector2F[]> { }
	
	public static interface ZEUniformVec3Array extends ZEUniformComplex<Vector3F[]> { }
	
	public static interface ZEUniformVec4Array extends ZEUniformComplex<Vector4F[]> { }
	
	public static interface ZEUniformCustom extends ZEUniformComplex<ByteBuffer> { }
	
}
