package com.zekrom_64.ze.bullet3;

import com.zekrom_64.mathlib.matrix.impl.Matrix3x3D;
import com.zekrom_64.mathlib.tuple.impl.Vector3D;
import com.zekrom_64.mathlib.tuple.impl.Vector4D;

public class BTTransform {

	public final Matrix3x3D basis;
	public final Vector3D origin;
	
	public BTTransform(Vector4D q) {
		this(q, new Vector3D(0,0,0));
	}

	public BTTransform(Vector4D q, Vector3D c) {
		origin = c;
		basis = new Matrix3x3D();
		BTMath.setRotation(basis, q);
	}

	public BTTransform(Matrix3x3D basis, Vector3D origin) {
		this.basis = basis;
		this.origin = origin;
	}

	public BTTransform(BTTransform other) {
		basis = new Matrix3x3D(other.basis);
		origin = new Vector3D(other.origin);
	}
	
	public void mult(BTTransform t1, BTTransform t2) {
		basis.set(t1.basis);
		basis.mul(t2.basis);
		
	}

	public void set(BTTransform other) {
		basis.set(other.basis);
		origin.set(other.origin);
	}

	public void setIdentity() {
		basis.identity();
		origin.clear();
	}

	public void inverse(BTTransform t) {
		Matrix3x3D inv = new Matrix3x3D(basis);
		inv.transpose();
		Vector3D negOrigin = new Vector3D(origin);
		origin.negate();
		negOrigin.dot(new Vector3D(inv.m0x0, inv.m0x1, inv.m0x2));
		t.basis.set(inv);
		t.origin.set(negOrigin);
	}
	
	public void inverse() {
		basis.transpose();
		origin.negate();
		origin.dot(new Vector3D(basis.m0x0, basis.m0x1, basis.m0x2));
	}
	
	public void mult(BTTransform t) {
		Vector3D temp = new Vector3D(t.origin);
		temp.dot(new Vector3D(basis.m0x0, basis.m0x1, basis.m0x2));
		origin.set(temp);
		basis.mul(t.basis);
	}

}

