package com.zekrom_64.ze.base.backend.render.shader;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Matrix3d;
import javax.vecmath.Matrix3f;
import javax.vecmath.Matrix4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Point2d;
import javax.vecmath.Point2f;
import javax.vecmath.Point2i;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Point3i;
import javax.vecmath.Point4d;
import javax.vecmath.Point4f;
import javax.vecmath.Point4i;
import javax.vecmath.Quat4d;
import javax.vecmath.Quat4f;
import javax.vecmath.TexCoord2f;
import javax.vecmath.TexCoord3f;
import javax.vecmath.TexCoord4f;
import javax.vecmath.Tuple2d;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple2i;
import javax.vecmath.Tuple3d;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple3i;
import javax.vecmath.Tuple4d;
import javax.vecmath.Tuple4f;
import javax.vecmath.Tuple4i;
import javax.vecmath.Vector2d;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4d;
import javax.vecmath.Vector4f;

public interface ZEUniformComplex<T> extends ZEUniform {

	public T get();
	
	public void set(T t);
	
	
	public static interface ZEUniformFloatArray extends ZEUniformComplex<float[]> { }
	
	public static interface ZEUniformIntArray extends ZEUniformComplex<int[]> { }
	
	public static interface ZEUniformUIntArray extends ZEUniformIntArray { }
	
	public static interface ZEUniformDoubleArray extends ZEUniformComplex<double[]> { }
	
	public static interface ZEUniformBoolArray extends ZEUniformComplex<boolean[]> { }
	
	
	public static interface ZEUniformVec2 extends ZEUniformComplex<Tuple2f> {
		
		public void set(float x, float y);
		
		default public Vector2f getVec() {
			return new Vector2f(get());
		}
		
		default public Point2f getPoint() {
			return new Point2f(get());
		}
		
		default public TexCoord2f getTexCoord() {
			return new TexCoord2f(get());
		}
		
	}
	
	public static interface ZEUniformVec3 extends ZEUniformComplex<Tuple3f> {
		
		public void set(float x, float y, float z);
		
		default public Vector3f getVec() {
			return new Vector3f(get());
		}
		
		default public Point3f getPoint() {
			return new Point3f(get());
		}
		
		default public Color3f getColor() {
			return new Color3f(get());
		}
		
		default public TexCoord3f getTexCoord() {
			return new TexCoord3f(get());
		}
		
	}
	
	public static interface ZEUniformVec4 extends ZEUniformComplex<Tuple4f> {
		
		public void set(float x, float y, float z, float w);
		
		default public Vector4f getVec() {
			return new Vector4f(get());
		}
		
		default public Point4f getPoint() {
			return new Point4f(get());
		}
		
		default public Color4f getColor() {
			return new Color4f(get());
		}
		
		default public Quat4f getQuat() {
			return new Quat4f(get());
		}
		
		default public TexCoord4f getTexCoord() {
			return new TexCoord4f(get());
		}
		
	}
	
	public static interface ZEUniformMat3 extends ZEUniformComplex<Matrix3f> { }
	
	public static interface ZEUniformMat4 extends ZEUniformComplex<Matrix4f> { }
	
	public static interface ZEUniformVec2i extends ZEUniformComplex<Tuple2i> {
		
		public void set(int x, int y);
		
		default public Point2i getPoint() {
			return new Point2i(get());
		}
		
	}
	
	public static interface ZEUniformVec3i extends ZEUniformComplex<Tuple3i> {
		
		public void set(int x, int y, int z);
		
		default public Point3i getPoint() {
			return new Point3i(get());
		}
		
	}
	
	public static interface ZEUniformVec4i extends ZEUniformComplex<Tuple4i> {
		
		public void set(int x, int y, int z, int w);
		
		default public Point4i getPoint() {
			return new Point4i(get());
		}
		
	}
	
	public static interface ZEUniformVec2d extends ZEUniformComplex<Tuple2d> {
		
		public void set(double x, double y);
		
		default public Vector2d getVec() {
			return new Vector2d(get());
		}
		
		default public Point2d getPoint() {
			return new Point2d(get());
		}
		
	}
	
	public static interface ZEUniformVec3d extends ZEUniformComplex<Tuple3d> {
		
		public void set(double x, double y, double z);
		
		default public Vector3d getVec() {
			return new Vector3d(get());
		}
		
		default public Point3d getPoint() {
			return new Point3d(get());
		}
		
	}
	
	public static interface ZEUniformVec4d extends ZEUniformComplex<Tuple4d> {
		
		public void set(double x, double y, double z, double w);
		
		public default Vector4d getVec() {
			return new Vector4d(get());
		}
		
		public default Point4d getPoint() {
			return new Point4d(get());
		}
		
		public default Quat4d getQuat() {
			return new Quat4d(get());
		}
		
	}

	public static interface ZEUniformMat3d extends ZEUniformComplex<Matrix3d> { }
	
	public static interface ZEUniformMat4d extends ZEUniformComplex<Matrix4d> { }
	
	
	public static interface ZEUniformVec2Array extends ZEUniformComplex<Tuple2f[]> { }
	
	public static interface ZEUniformVec3Array extends ZEUniformComplex<Tuple3f[]> { }
	
	public static interface ZEUniformVec4Array extends ZEUniformComplex<Tuple4f[]> { }
	
}
