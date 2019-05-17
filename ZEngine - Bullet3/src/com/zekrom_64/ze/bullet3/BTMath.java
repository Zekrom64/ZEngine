package com.zekrom_64.ze.bullet3;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3D;
import com.zekrom_64.mathlib.tuple.impl.Vector4D;

public class BTMath {

	public static double lengthSquared(Vector4D q) {
		return (q.x * q.x) + (q.y * q.y) + (q.z * q.z) + (q.w * q.w);
	}
	
	public static double length(Vector4D q) {
		return Math.sqrt(lengthSquared(q));
	}
	
	public static void setRotation(Matrix3x3D mat, Vector4D q) {
		double d = lengthSquared(q);
		assert(d != 0);
		double s = 2 / d;
		double xs = q.x * s,  ys = q.y * s,  zs = q.z * s;
		double wx = q.w * xs, wy = q.w * ys, wz = q.w * zs;
		double xx = q.x * xs, xy = q.x * ys, xz = q.x * zs;
		double yy = q.y * ys, yz = q.y * zs, zz = q.z * zs;
		mat.m0x0 = 1 - (yy + zz);
		mat.m0x1 = xy - wz;
		mat.m0x2 = xz + wy;
		mat.m1x0 = xy + wz;
		mat.m1x1 = 1 - (xx + zz);
		mat.m1x2 = yz - wx;
		mat.m2x0 = xz - wy;
		mat.m2x1 = yz + wx;
		mat.m2x2 = 1 - (xx + yy);
	}

}
