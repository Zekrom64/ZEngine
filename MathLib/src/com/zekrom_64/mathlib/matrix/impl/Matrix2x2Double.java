package com.zekrom_64.mathlib.matrix.impl;

import com.zekrom_64.mathlib.matrix.Matrix2x2;
import com.zekrom_64.mathlib.matrix.MatrixDouble;

public class Matrix2x2Double implements Matrix2x2<Double>, MatrixDouble {

	public double m0x0, m0x1, m1x0, m1x1;
	
	public Matrix2x2Double() {}
	
	public Matrix2x2Double(
			double m0x0, double m0x1,
			double m1x0, double m1x1
			) {
		this.m0x0 = m0x0;
		this.m0x1 = m0x1;
		
		this.m1x0 = m1x0;
		this.m1x1 = m1x1;
	}
	
	public Matrix2x2Double(double[] values) {
		m0x0 = values[0];
		m0x1 = values[1];
		
		m1x0 = values[2];
		m1x1 = values[3];
	}
	
	public Matrix2x2Double(double[][] values) {
		m0x0 = values[0][0];
		m0x1 = values[0][1];
		
		m1x0 = values[1][0];
		m1x1 = values[1][1];
	}

	@Override
	public void setDouble(int row, int column, double val) {
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
	public double getDouble(int row, int column) {
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

}
