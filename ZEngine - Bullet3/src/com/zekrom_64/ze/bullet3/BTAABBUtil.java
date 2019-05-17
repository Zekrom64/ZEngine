package com.zekrom_64.ze.bullet3;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3D;
import com.zekrom_64.mathlib.tuple.impl.Vector3D;
import com.zekrom_64.ze.base.math.DoubleByRef;

public class BTAABBUtil {

	public static boolean testPointAgainstAABB2(Vector3D min1, Vector3D max1, Vector3D point) {
		boolean overlap = true;
		overlap = (min1.x > point.x || max1.x < point.x) ? false : overlap;
		overlap = (min1.z > point.z || max1.z < point.z) ? false : overlap;
		overlap = (min1.y > point.y || max1.y < point.y) ? false : overlap;
		return overlap;
	}
	
	public static boolean testAABBAgainstAABB2(Vector3D min1, Vector3D max1, Vector3D min2, Vector3D max2) {
		boolean overlap = true;
		overlap = (min1.x > min2.x || max1.x < max2.x) ? false : overlap;
		overlap = (min1.z > min2.z || max1.z < max2.z) ? false : overlap;
		overlap = (min1.y > min2.y || max1.y < max2.y) ? false : overlap;
		return overlap;
	}
	
	public static boolean testTriangleAgainstAABB2(double[] vertices, Vector3D min, Vector3D max) {
		if (Math.min(Math.min(vertices[0], vertices[3]), vertices[6]) > max.x) return false;
		if (Math.max(Math.max(vertices[0], vertices[3]), vertices[6]) > max.x) return false;

		if (Math.min(Math.min(vertices[2], vertices[5]), vertices[8]) > max.z) return false;
		if (Math.max(Math.max(vertices[2], vertices[5]), vertices[8]) > max.z) return false;

		if (Math.min(Math.min(vertices[1], vertices[4]), vertices[7]) > max.y) return false;
		if (Math.max(Math.max(vertices[1], vertices[4]), vertices[7]) > max.y) return false;
		
		return true;
	}
	
	public int outcode(Vector3D p, Vector3D halfExtent) {
		return (p.x < -halfExtent.x ? 0x01 : 0) |
				(p.x > halfExtent.x ? 0x08 : 0) |
				(p.y < -halfExtent.y ? 0x02 : 0) |
				(p.y > halfExtent.y ? 0x10 : 0) |
				(p.z < -halfExtent.z ? 0x04 : 0) |
				(p.z > halfExtent.z ? 0x20 : 0);
	}
	
	public boolean rayAABB2(Vector3D rayFrom, Vector3D rayInvDirection, int[] raySign,
			Vector3D boundsMin, Vector3D boundsMax, DoubleByRef tmin_out, double lambdaMin, double lambdaMax)  {
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
	
	public boolean rayAABB(Vector3D rayFrom, Vector3D rayTo, Vector3D aabbMin, Vector3D aabbMax, DoubleByRef param_inout, Vector3D normal) {
		Vector3D aabbHalfExtent = new Vector3D(aabbMax);
		aabbHalfExtent.sub(aabbMin);
		aabbHalfExtent.mul(0.5);
		Vector3D aabbCenter = new Vector3D(aabbMax);
		aabbCenter.add(aabbMin);
		aabbCenter.mul(0.5);
		Vector3D source = new Vector3D(rayFrom);
		source.sub(aabbCenter);
		Vector3D target = new Vector3D(rayTo);
		target.sub(aabbCenter);
		int sourceOutcode = outcode(source, aabbHalfExtent);
		int targetOutcode = outcode(target, aabbHalfExtent);
		if ((sourceOutcode & targetOutcode) == 0) {
			double lambdaEnter = 0;
			double lambdaExit = param_inout.value;
			Vector3D r = new Vector3D(target);
			r.sub(source);
			double normSign = 1;
			Vector3D hitNormal = new Vector3D();
			int bit = 1;
			for(int j = 0; j < 2; j++) {
				for(int i = 0; i != 3; i++) {
					if ((sourceOutcode & bit) != 0) {
						double lambda = (-source.get(i) - aabbHalfExtent.get(i) * normSign) / r.get(i);
						if (lambdaEnter <= lambda) {
							lambdaEnter = lambda;
							hitNormal.set(0, 0, 0);
							hitNormal.set(i, normSign);
						}
					} else if ((targetOutcode & bit) != 0) {
						double lambda = (-source.get(i) - aabbHalfExtent.get(i) * normSign) / r.get(i);
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
	
	public static void transformAABB(Vector3D halfExtents, double margin, BTTransform t, Vector3D aabbMinOut, Vector3D aabbMaxOut) {
		Vector3D halfExtentsWithMargin = new Vector3D(halfExtents);
		halfExtentsWithMargin.x += margin;
		halfExtentsWithMargin.y += margin;
		halfExtentsWithMargin.z += margin;
		Matrix3x3D abs_b = t.basis;
		Vector3D center = t.origin;
	}
	
}
