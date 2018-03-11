package com.zekrom_64.mathlib.matrix;

import java.util.ArrayList;

public interface MatrixNumeric<T extends Number> extends Matrix<T> {

	public default double getDouble(int row, int column) { return get(row, column).doubleValue(); }
	
	public void setDouble(int row, int column, double val);
	
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
	
}
