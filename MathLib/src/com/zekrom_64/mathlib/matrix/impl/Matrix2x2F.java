package com.zekrom_64.mathlib.matrix.impl;

import com.zekrom_64.mathlib.matrix.Matrix2x2;
import com.zekrom_64.mathlib.matrix.MatrixFloat;

public class Matrix2x2F implements Matrix2x2<Float>, MatrixFloat {

	public float m0x0, m0x1, m1x0, m1x1;
	
	public Matrix2x2F() {}
	
	public Matrix2x2F(Matrix2x2F m) {
		m0x0 = m.m0x0;
		m0x1 = m.m0x1;
		m1x0 = m.m1x0;
		m1x1 = m.m1x1;
	}
	
	public Matrix2x2F(
			float m0x0, float m0x1,
			float m1x0, float m1x1
			) {
		this.m0x0 = m0x0;
		this.m0x1 = m0x1;
		
		this.m1x0 = m1x0;
		this.m1x1 = m1x1;
	}
	
	public Matrix2x2F(float[] values) {
		m0x0 = values[0];
		m0x1 = values[1];
		
		m1x0 = values[2];
		m1x1 = values[3];
	}
	
	public Matrix2x2F(float[][] values) {
		m0x0 = values[0][0];
		m0x1 = values[0][1];
		
		m1x0 = values[1][0];
		m1x1 = values[1][1];
	}


	@Override
	public void setFloat(int row, int column, float val) {
		switch(row) {
		case 0:
			switch(column) {
			case 0: m0x0 = val; return;
			case 1: m0x1 = val; return;
			}
			break;
		case 1:
			switch(column) {
			case 0: m1x0 = val; return;
			case 1: m1x1 = val; return;
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
			}
			break;
		case 1:
			switch(column) {
			case 0: return m1x0;
			case 1: return m1x1;
			}
			break;
		}
		return 0;
	}
	
	public void set(Matrix2x2F m) {
		m0x0 = m.m0x0; m0x1 = m.m0x1;
		m1x0 = m.m1x0; m1x1 = m.m1x1;
	}

}
