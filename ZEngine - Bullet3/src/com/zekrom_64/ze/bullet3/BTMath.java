package com.zekrom_64.ze.bullet3;

import javax.vecmath.Matrix3d;
import javax.vecmath.Quat4d;

public class BTMath {

	public static double lengthSquared(Quat4d q) {
		return (q.x * q.x) + (q.y * q.y) + (q.z * q.z) + (q.w * q.w);
	}
	
	public static double length(Quat4d q) {
		return Math.sqrt(lengthSquared(q));
	}
	
	public static void setRotation(Matrix3d mat, Quat4d q) {
		double d = lengthSquared(q);
		assert(d != 0);
		double s = 2 / d;
		double xs = q.x * s,  ys = q.y * s,  zs = q.z * s;
		double wx = q.w * xs, wy = q.w * ys, wz = q.w * zs;
		double xx = q.x * xs, xy = q.x * ys, xz = q.x * zs;
		double yy = q.y * ys, yz = q.y * zs, zz = q.z * zs;
		mat.m00 = 1 - (yy + zz);
		mat.m01 = xy - wz;
		mat.m02 = xz + wy;
		mat.m10 = xy + wz;
		mat.m11 = 1 - (xx + zz);
		mat.m12 = yz - wx;
		mat.m20 = xz - wy;
		mat.m21 = yz + wx;
		mat.m22 = 1 - (xx + yy);
	}

}
