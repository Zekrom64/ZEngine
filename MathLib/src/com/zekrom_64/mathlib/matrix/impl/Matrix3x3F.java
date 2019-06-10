package com.zekrom_64.mathlib.matrix.impl;

import com.zekrom_64.mathlib.matrix.Matrix2x2;

public class Matrix3x3F extends Matrix2x2F implements Matrix2x2<Float> {
	
	public float m0x2, m1x2, m2x0, m2x1, m2x2;
	
	public Matrix3x3F() {}
	
	public Matrix3x3F(Matrix3x3F m) {
		m0x0 = m.m0x0;
		m0x1 = m.m0x1;
		m0x2 = m.m0x2;
		m1x0 = m.m1x0;
		m1x1 = m.m1x1;
		m1x2 = m.m1x2;
		m2x0 = m.m2x0;
		m2x1 = m.m2x1;
		m2x2 = m.m2x2;
	}
	
	public Matrix3x3F(
			float m0x0, float m0x1, float m0x2,
			float m1x0, float m1x1, float m1x2,
			float m2x0, float m2x1, float m2x2
			) {
		this.m0x0 = m0x0;
		this.m0x1 = m0x1;
		this.m0x2 = m0x2;
		
		this.m1x0 = m1x0;
		this.m1x1 = m1x1;
		this.m1x2 = m1x2;
		
		this.m2x0 = m2x0;
		this.m2x1 = m2x1;
		this.m2x2 = m2x2;
	}
	
	public Matrix3x3F(float[] values) {
		m0x0 = values[0];
		m0x1 = values[1];
		m0x2 = values[2];
		
		m1x0 = values[3];
		m1x1 = values[4];
		m1x2 = values[5];
		
		m2x0 = values[6];
		m2x1 = values[7];
		m2x2 = values[8];
	}
	
	public Matrix3x3F(float[][] values) {
		m0x0 = values[0][0];
		m0x1 = values[0][1];
		m0x2 = values[0][2];
		
		m1x0 = values[1][0];
		m1x1 = values[1][1];
		m1x2 = values[1][2];
		
		m2x0 = values[2][0];
		m2x1 = values[2][1];
		m2x2 = values[2][2];
	}

	@Override
	public int rows() { return 3; }

	@Override
	public int columns() { return 3; }
	
	@Override
	public void setFloat(int row, int column, float val) {
		switch(row) {
		case 0:
			switch(column) {
			case 0: m0x0 = val; return;
			case 1: m0x1 = val; return;
			case 2: m0x2 = val; return;
			}
			break;
		case 1:
			switch(column) {
			case 0: m1x0 = val; return;
			case 1: m1x1 = val; return;
			case 2: m1x2 = val; return;
			}
			break;
		case 2:
			switch(column) {
			case 0: m2x0 = val; return;
			case 1: m2x1 = val; return;
			case 2: m2x2 = val; return;
			}
			break;
		}
	}

	@Override
	public float getFloat(int row, int column) {
		switch(row) {
		case 0:
			switch(column) {
			case 0: return m0x0;
			case 1: return m0x1;
			case 2: return m0x2;
			}
			break;
		case 1:
			switch(column) {
			case 0: return m1x0;
			case 1: return m1x1;
			case 2: return m1x2;
			}
			break;
		case 2:
			switch(column) {
			case 0: return m2x0;
			case 1: return m2x1;
			case 2: return m2x2;
			}
			break;
		}
		return 0;
	}
	
	public void set(Matrix3x3F m) {
		m0x0 = m.m0x0; m0x1 = m.m0x1; m0x2 = m.m0x2;
		m1x0 = m.m1x0; m1x1 = m.m1x1; m1x2 = m.m1x2;
		m2x0 = m.m2x0; m2x1 = m.m2x1; m2x2 = m.m2x2;
	}

}
