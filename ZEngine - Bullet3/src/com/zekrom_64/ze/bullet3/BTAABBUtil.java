package com.zekrom_64.ze.bullet3;

import javax.vecmath.Matrix3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.zekrom_64.ze.base.math.DoubleByRef;
import com.zekrom_64.ze.base.math.ZEVecmath;

public class BTAABBUtil {

	public static boolean testPointAgainstAABB2(Vector3d min1, Vector3d max1, Vector3d point) {
		boolean overlap = true;
		overlap = (min1.x > point.x || max1.x < point.x) ? false : overlap;
		overlap = (min1.z > point.z || max1.z < point.z) ? false : overlap;
		overlap = (min1.y > point.y || max1.y < point.y) ? false : overlap;
		return overlap;
	}
	
	public static boolean testAABBAgainstAABB2(Vector3d min1, Vector3d max1, Vector3d min2, Vector3d max2) {
		boolean overlap = true;
		overlap = (min1.x > min2.x || max1.x < max2.x) ? false : overlap;
		overlap = (min1.z > min2.z || max1.z < max2.z) ? false : overlap;
		overlap = (min1.y > min2.y || max1.y < max2.y) ? false : overlap;
		return overlap;
	}
	
	public static boolean testTriangleAgainstAABB2(double[] vertices, Vector3d min, Vector3d max) {
		if (Math.min(Math.min(vertices[0], vertices[3]), vertices[6]) > max.x) return false;
		if (Math.max(Math.max(vertices[0], vertices[3]), vertices[6]) > max.x) return false;

		if (Math.min(Math.min(vertices[2], vertices[5]), vertices[8]) > max.z) return false;
		if (Math.max(Math.max(vertices[2], vertices[5]), vertices[8]) > max.z) return false;

		if (Math.min(Math.min(vertices[1], vertices[4]), vertices[7]) > max.y) return false;
		if (Math.max(Math.max(vertices[1], vertices[4]), vertices[7]) > max.y) return false;
		
		return true;
	}
	
	public int outcode(Vector3d p, Vector3d halfExtent) {
		return (p.x < -halfExtent.x ? 0x01 : 0) |
				(p.x > halfExtent.x ? 0x08 : 0) |
				(p.y < -halfExtent.y ? 0x02 : 0) |
				(p.y > halfExtent.y ? 0x10 : 0) |
				(p.z < -halfExtent.z ? 0x04 : 0) |
				(p.z > halfExtent.z ? 0x20 : 0);
	}
	
	public boolean rayAABB2(Vector3d rayFrom, Vector3d rayInvDirection, int[] raySign,
			Vector3d boundsMin, Vector3d boundsMax, DoubleByRef tmin_out, double lambdaMin, double lambdaMax)  {
		double tmin, tmax, tymin, tymax, tzmin, tzmax;
		
		tmin = ((raySign[0] == 0 ? boundsMin : boundsMax).x - rayFrom.x) * rayInvDirection.x;
		tmax = ((1-raySign[0] == 0 ? boundsMin : boundsMax).x - rayFrom.x) * rayInvDirection.x;
		tymin = ((raySign[1] == 0 ? boundsMin : boundsMax).y - rayFrom.y) * rayInvDirection.y;
		tymax = ((1-raySign[1] == 0 ? boundsMin : boundsMax).y - rayFrom.y) * rayInvDirection.y;
		
		if ((tmin > tymax) || (tymin > tmax)) {
			if (tmin_out != null) tmin_out.value = tmin;
			return false;
		}
		
		if (tymin > tmin) tmin = tymin;
		if (tymax < tmax) tmax = tymax;
		
		tzmin = ((raySign[2] == 0 ? boundsMin : boundsMax).z - rayFrom.z) * rayInvDirection.z;
		tzmax = ((1-raySign[2] == 0 ? boundsMin : boundsMax).z - rayFrom.z) * rayInvDirection.z;
		
		if ((tmin > tzmax) || (tzmin > tmax)) {
			if (tmin_out != null) tmin_out.value = tmin;
			return false;
		}
		
		if (tzmin > tmin) tmin = tzmin;
		if (tzmax < tmax) tmax = tzmax;
		
		if (tmin_out != null) tmin_out.value = tmin;
		return ((tmin < lambdaMax) && (tmax > lambdaMin));
	}
	
	public boolean rayAABB(Vector3d rayFrom, Vector3d rayTo, Vector3d aabbMin, Vector3d aabbMax, DoubleByRef param_inout, Vector3d normal) {
		Vector3d aabbHalfExtent = new Vector3d();
		aabbHalfExtent.sub(aabbMax, aabbMin);
		aabbHalfExtent.scale(0.5);
		Vector3d aabbCenter = new Vector3d();
		aabbCenter.add(aabbMax, aabbMin);
		aabbCenter.scale(0.5);
		Vector3d source = new Vector3d();
		source.sub(rayFrom, aabbCenter);
		Vector3d target = new Vector3d();
		target.sub(rayTo, aabbCenter);
		int sourceOutcode = outcode(source, aabbHalfExtent);
		int targetOutcode = outcode(target, aabbHalfExtent);
		if ((sourceOutcode & targetOutcode) == 0) {
			double lambdaEnter = 0;
			double lambdaExit = param_inout.value;
			Vector3d r = new Vector3d();
			r.sub(target, source);
			double normSign = 1;
			Vector3d hitNormal = new Vector3d();
			int bit = 1;
			for(int j = 0; j < 2; j++) {
				for(int i = 0; i != 3; i++) {
					if ((sourceOutcode & bit) != 0) {
						double lambda = (-ZEVecmath.index(source, i) - ZEVecmath.index(aabbHalfExtent, i) * normSign) / ZEVecmath.index(r, i);
						if (lambdaEnter <= lambda) {
							lambdaEnter = lambda;
							hitNormal.set(0, 0, 0);
							ZEVecmath.index(hitNormal, i, (float)normSign);
						}
					} else if ((targetOutcode & bit) != 0) {
						double lambda = (-ZEVecmath.index(source, i) - ZEVecmath.index(aabbHalfExtent, i) * normSign) / ZEVecmath.index(r, i);
						if (lambda < lambdaExit) lambdaExit = lambda;
					}
					bit <<= 1;
				}
				normSign = -1;
			}
			if (lambdaEnter <= lambdaExit) {
				param_inout.value = lambdaEnter;
				normal.set(hitNormal);
				return true;
			}
		}
		return false;
	}
	
	public static void transformAABB(Vector3d halfExtents, double margin, BTTransform t, Vector3d aabbMinOut, Vector3d aabbMaxOut) {
		Vector3d halfExtentsWithMargin = new Vector3d(halfExtents);
		halfExtentsWithMargin.x += margin;
		halfExtentsWithMargin.y += margin;
		halfExtentsWithMargin.z += margin;
		Matrix3d abs_b = t.basis;
		Vector3d center = t.origin;
	}
	
}
