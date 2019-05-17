package com.zekrom_64.mathlib.matrix.impl;

import com.zekrom_64.mathlib.matrix.Matrix4x4;

public class Matrix4x4D extends Matrix3x3D implements Matrix4x4<Double> {
	
	public double m0x3, m1x3, m2x3, m3x0, m3x1, m3x2, m3x3;
	
	public Matrix4x4D() {}
	
	public Matrix4x4D(Matrix4x4D m) {
		m0x0 = m.m0x0;
		m0x1 = m.m0x1;
		m0x2 = m.m0x2;
		m0x3 = m.m0x3;
		m1x0 = m.m1x0;
		m1x1 = m.m1x1;
		m1x2 = m.m1x2;
		m1x3 = m.m1x3;
		m2x0 = m.m2x0;
		m2x1 = m.m2x1;
		m2x2 = m.m2x2;
		m2x3 = m.m2x3;
		m3x0 = m.m3x0;
		m3x1 = m.m3x1;
		m3x2 = m.m3x2;
		m3x3 = m.m3x3;
	}
	
	public Matrix4x4D(
			double m0x0, double m0x1, double m0x2, double m0x3,
			double m1x0, double m1x1, double m1x2, double m1x3,
			double m2x0, double m2x1, double m2x2, double m2x3,
			double m3x0, double m3x1, double m3x2, double m3x3
			) {
		this.m0x0 = m0x0;
		this.m0x1 = m0x1;
		this.m0x2 = m0x2;
		this.m0x3 = m0x3;
		
		this.m1x0 = m1x0;
		this.m1x1 = m1x1;
		this.m1x2 = m1x2;
		this.m1x3 = m1x3;
		
		this.m2x0 = m2x0;
		this.m2x1 = m2x1;
		this.m2x2 = m2x2;
		this.m2x3 = m2x3;

		this.m3x0 = m3x0;
		this.m3x1 = m3x1;
		this.m3x2 = m3x2;
		this.m3x3 = m3x3;
	}
	
	public Matrix4x4D(double[] values) {
		m0x0 = values[0];
		m0x1 = values[1];
		m0x2 = values[2];
		m0x3 = values[3];
		
		m1x0 = values[4];
		m1x1 = values[5];
		m1x2 = values[6];
		m1x3 = values[7];
		
		m2x0 = values[8];
		m2x1 = values[9];
		m2x2 = values[10];
		m2x3 = values[11];
		
		m3x0 = values[12];
		m3x1 = values[13];
		m3x2 = values[14];
		m3x3 = values[15];
	}
	
	public Matrix4x4D(double[][] values) {
		m0x0 = values[0][0];
		m0x1 = values[0][1];
		m0x2 = values[0][2];
		m0x3 = values[0][3];
		
		m1x0 = values[1][0];
		m1x1 = values[1][1];
		m1x2 = values[1][2];
		m1x3 = values[1][3];
		
		m2x0 = values[2][0];
		m2x1 = values[2][1];
		m2x2 = values[2][2];
		m2x3 = values[2][3];
		
		m3x0 = values[3][0];
		m3x1 = values[3][1];
		m3x2 = values[3][2];
		m3x3 = values[3][3];
	}

	@Override
	public int rows() { return 4; }

	@Override
	public int columns() { return 4; }

	@Override
	public void setDouble(int row, int column, double val) {
		switch(row) {
		case 0:
			switch(column) {
			case 0: m0x0 = val; return;
			case 1: m0x1 = val; return;
			case 2: m0x2 = val; return;
			case 3: m0x3 = val; return;
			}
			break;
		case 1:
			switch(column) {
			case 0: m1x0 = val; return;
			case 1: m1x1 = val; return;
			case 2: m1x2 = val; return;
			case 3: m1x3 = val; return;
			}
			break;
		case 2:
			switch(column) {
			case 0: m2x0 = val; return;
			case 1: m2x1 = val; return;
			case 2: m2x2 = val; return;
			case 3: m2x3 = val; return;
			}
			break;
		case 3:
			switch(column) {
			case 0: m3x0 = val; return;
			case 1: m3x1 = val; return;
			case 2: m3x2 = val; return;
			case 3: m3x3 = val; return;
			}
			break;
		}
	}

	@Override
	public double getDouble(int row, int column) {
		switch(row) {
		case 0:
			switch(column) {
			case 0: return m0x0;
			case 1: return m0x1;
			case 2: return m0x2;
			case 3: return m0x3;
			}
			break;
		case 1:
			switch(column) {
			case 0: return m1x0;
			case 1: return m1x1;
			case 2: return m1x2;
			case 3: return m1x3;
			}
			break;
		case 2:
			switch(column) {
			case 0: return m2x0;
			case 1: return m2x1;
			case 2: return m2x2;
			case 3: return m2x3;
			}
			break;
		case 3:
			switch(column) {
			case 0: return m3x0;
			case 1: return m3x1;
			case 2: return m3x2;
			case 3: return m3x3;
			}
			break;
		}
		return 0;
	}

}
