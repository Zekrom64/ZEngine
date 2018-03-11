package com.zekrom_64.ze.bullet3;

import javax.vecmath.Matrix3d;
import javax.vecmath.Quat4d;
import javax.vecmath.Vector3d;

public class BTTransform {

	public final Matrix3d basis;
	public final Vector3d origin;

	public BTTransform(Matrix3d basis, Vector3d origin) {
		this.basis = basis;
		this.origin = origin;
	}
	
	public BTTransform(Quat4d q) {
		this(q, new Vector3d(0,0,0));
	}

	public BTTransform(Quat4d q, Vector3d c) {
		origin = c;
		basis = new Matrix3d();
		BTMath.setRotation(basis, q);
	}

	public BTTransform(BTTransform other) {
		basis = new Matrix3d(other.basis);
		origin = new Vector3d(other.origin);
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
		basis.setIdentity();
		origin.set(0,0,0);
	}

	public void inverse(BTTransform t) {
		Matrix3d inv = new Matrix3d();
		basis.transpose(inv);
		Vector3d negOrigin = new Vector3d();
		origin.negate(negOrigin);
		negOrigin.dot(new Vector3d(inv.m00, inv.m01, inv.m02));
		t.basis.set(inv);
		t.origin.set(negOrigin);
	}
	
	public void inverse() {
		basis.transpose();
		origin.negate();
		origin.dot(new Vector3d(basis.m00, basis.m01, basis.m02));
	}
	
	public void mult(BTTransform t) {
		Vector3d temp = new Vector3d();
		temp.set(t.origin);
		temp.dot(new Vector3d(basis.m00, basis.m01, basis.m02));
		origin.set(temp);
		basis.mul(t.basis);
	}

}

