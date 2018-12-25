package com.zekrom_64.mathlib.matrix;

import java.util.ArrayList;

public interface MatrixNumeric<T extends Number> extends Matrix<T> {

	public default double getDouble(int row, int column) { return get(row, column).doubleValue(); }
	
	public void setDouble(int row, int column, double val);
	
	public default void set(MatrixNumeric<T> other) {
		int w = Math.min(columns(), other.columns());
		int h = Math.min(rows(), other.rows());
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++) setDouble(y, x, other.getDouble(y, x));
	}
	
	static class LeibnizPermutation {
	
		public final int[][] permutations;
		public final boolean[] permSign;
		
		public LeibnizPermutation(int size) {
			int permSize = nPermutations(size);
			permutations = new int[permSize][];
			permSign = new boolean[permSize];
			// TODO: Implement permutation and sign generation
		}
		
		private static int nPermutations(int size) {
			int nPerms = 1;
			for(int i = size; i > 1; i--) nPerms *= i;
			return nPerms;
		}
		
	}
	
	static ArrayList<LeibnizPermutation> leibnizPermutations = new ArrayList<>();
	
	static double leibniz(MatrixNumeric<?> mat) {
		int nRows = mat.rows();
		leibnizPermutations.ensureCapacity(nRows);
		LeibnizPermutation leibniz = leibnizPermutations.get(nRows-1);
		if (leibniz == null) {
			leibniz = new LeibnizPermutation(nRows);
			leibnizPermutations.set(nRows, leibniz);
		}
		double sum = 0;
		for(int i = 0; i < leibniz.permutations.length; i++) {
			int[] perm = leibniz.permutations[i];
			boolean sign = leibniz.permSign[i];
			
			double prod = 1;
			for(int j = 1; j < perm[j]; j++) prod *= mat.getDouble(j, i);
			sum += sign ? -prod : prod;
		}
		return sum;
	}
	
	public default double determinate() {
		int size = rows();
		if (size != columns()) return 0; // Cannot compute determinate on non-square matrix
		if (size < 1) return 0; // Cannot compute non-existant matrix
		switch(size) {
		case 1: return getDouble(0,0); // Determinate of a single value is the value
		case 2: return getDouble(0,0) * getDouble(1,1) - getDouble(0,1) * getDouble(1,0); // det(2x2) = ad-bc
		case 3: { // det(3x3) = a(ei-fh) - b(di-fg) + c(dh-eg)
			double a = getDouble(0,0), b = getDouble(0,1), c = getDouble(0,2);
			double d = getDouble(1,0), e = getDouble(0,1), f = getDouble(0,2);
			double g = getDouble(2,0), h = getDouble(0,1), i = getDouble(0,2);
			return a*(e*i - f*h) - b*(d*i - f*g) + c*(d*h - e*g);
		}
		default: return leibniz(this); // For larger matrices, resort to algorithm
		}
	}
	
	public default boolean invert() {
		int w = columns(), h = rows();
		if (w != h) return false;
		int size = w;
		if (size < 1) return true; // Inverse of zero-size matrix is itself
		if (size == 1) {
			double x = getDouble(0,0);
			if (x == 0) return false; // Cannot divide by 0
			setDouble(0,0, 1d/x); // Inverse of a single number is 1/x
			return true;
		}
		// Resort to algorithm for complex inverses
		// Based on lwjgl_util GLU code for 4x4 matrices, extended to NxN
		double[][] temp = new double[w][h], inverse = new double[w][h];
		for(int x = 0; x < size; x++)
			for(int y = 0; y < size; y++) temp[x][y] = getDouble(y,x);
		
		for(int i = 0; i < size; i++) inverse[i][i] = 1;
		
		int swap;
		for(int i = 0; i < size; i++) {
			swap = i;
			// Find largest element in column
			for(int j = i + 1; j < size; j++) {
				if (Math.abs(temp[i][j]) > Math.abs(temp[j][i])) {
					swap = j;
				}
			}
			if (swap != i) {
				// Swap rows
				for(int k = 0; k < size; k++) {
					double t = temp[k][i];
					temp[k][i] = temp[k][swap];
					temp[k][swap] = t;
					t = inverse[k][i];
					inverse[k][i] = inverse[k][swap];
					inverse[k][swap] = t;
				}
			}
			double t = temp[i][i];
			// Check if matrix is singular
			if (t == 0) return false;
			// Do computation for inverse
			for(int k = 0; k < size; k++) {
				temp[k][i] = temp[k][i] / t;
				inverse[k][i] = inverse[k][i] / t;
			}
			for(int j = 0; j < size; j++) {
				if (j != i) {
					t = temp[i][j];
					for(int k = 0; k < size; k++) {
						temp[k][j] -= temp[k][i] * t;
						inverse[k][j] -= inverse[k][i] * t;
					}
				}
			}
		}
		// Set self to inverse
		for(int x = 0; x < size; x++)
			for(int y = 0; y < size; y++) setDouble(y,x,inverse[x][y]);
		return true;
	}
	
	public default void mul(MatrixNumeric<?> other) {
		int w = Math.min(columns(), other.columns());
		int h = Math.min(rows(), other.rows());
		for(int x = 0; x < w; x++)
			for(int y = 0; y < h; y++) setDouble(y,x, getDouble(y,x) * other.getDouble(y, x));
	}
	
}
