package com.zekrom_64.mathlib.tuple;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

public interface VectorNumeric<T extends Number> extends Vector<T> {

	/** Gets an element of the vector as a <b>double</b> value.
	 * 
	 * @param i Element index
	 * @return Element as double value
	 */
	public default double getDouble(int i) { return get(i).doubleValue(); }
	
	/** Sets an element of the vector to a <b>double</b> value.
	 * 
	 * @param i Element index
	 * @param val Value to set index to
	 */
	public void setDouble(int i, double val);
	
	/** Sets the values in this vector to another vector.
	 * 
	 * @param other Vector to set this to
	 */
	public default void set(VectorNumeric<?> other) {
		int n = Math.min(size(), other.size());
		for(int i = 0; i < n; i++) setDouble(i, other.getDouble(i));
	}
	
	/** Computes the length of the vector.
	 * 
	 * @return Vector length
	 */
	public default double length() {
		double len = 0;
		for(int i = 0; i < size(); i++) {
			double axis = getDouble(i);
			len += axis * axis;
		}
		return Math.sqrt(len);
	}
	
	/** Normalizes the elements of the vector (divides
	 * each element by the length of the initial vector).
	 * This converts the vector to a unit vector.
	 */
	public default void normalize() {
		div(length());
	}
	
	/** Negates each element of the vector.
	 * 
	 */
	public default void negate() {
		for(int i = 0; i < size(); i++) {
			setDouble(i, -getDouble(i));
		}
	}
	
	/** Performs the unary operation on each element of the vector.
	 * 
	 * @param op Unary operation
	 */
	public default void unary(DoubleUnaryOperator op) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, op.applyAsDouble(getDouble(i)));
		}
	}
	
	/** Adds the vector by a scalar.
	 * 
	 * @param scalar Scalar number
	 */
	public default void add(double scalar) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, getDouble(i) + scalar);
		}
	}
	
	/** Adds the vector by a scalar numeric value.
	 * 
	 * @param scalar Scalar number
	 */
	public default void add(ScalarNumeric<?> scalar) {
		add(scalar.getDouble());
	}
	
	/** Adds the vector by another vector value
	 * 
	 * @param tuple Vector number
	 */
	public default void add(VectorNumeric<?> tuple) {
		int count = Math.min(size(), tuple.size());
		for(int i = 0; i < count; i++) setDouble(i, getDouble(i) + tuple.getDouble(i));
	}
	
	/** Subtracts the vector by a scalar.
	 * 
	 * @param scalar Scalar number
	 */
	public default void sub(double scalar) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, getDouble(i) - scalar);
		}
	}
	
	/** Subtracts the vector by a scalar numeric value.
	 * 
	 * @param scalar Scalar number
	 */
	public default void sub(ScalarNumeric<?> scalar) {
		sub(scalar.getDouble());
	}
	
	/** Subtracts the vector by another vector value
	 * 
	 * @param tuple Vector number
	 */
	public default void sub(VectorNumeric<?> tuple) {
		int count = Math.min(size(), tuple.size());
		for(int i = 0; i < count; i++) setDouble(i, getDouble(i) - tuple.getDouble(i));
	}
	
	/** Multiplies the vector by a scalar.
	 * 
	 * @param scalar Scalar number
	 */
	public default void mul(double scalar) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, getDouble(i) * scalar);
		}
	}
	
	/** Multiplies the vector by a scalar numeric value.
	 * 
	 * @param scalar Scalar number
	 */
	public default void mul(ScalarNumeric<?> scalar) {
		mul(scalar.getDouble());
	}

	/** Multiplies the vector by another vector value
	 * 
	 * @param tuple Vector number
	 */
	public default void mul(VectorNumeric<?> tuple) {
		int count = Math.min(size(), tuple.size());
		for(int i = 0; i < count; i++) setDouble(i, getDouble(i) * tuple.getDouble(i));
	}
	
	/** Divides the vector by a scalar.
	 * 
	 * @param scalar Scalar number
	 */
	public default void div(double scalar) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, getDouble(i) / scalar);
		}
	}
	
	/** Divides the vector by a scalar numeric value.
	 * 
	 * @param scalar Scalar number
	 */
	public default void div(ScalarNumeric<?> scalar) {
		div(scalar.getDouble());
	}
	
	/** Divides the vector by another vector value
	 * 
	 * @param tuple Vector number
	 */
	public default void div(VectorNumeric<?> tuple) {
		int count = Math.min(size(), tuple.size());
		for(int i = 0; i < count; i++) setDouble(i, getDouble(i) / tuple.getDouble(i));
	}
	
	/** Computes the modulus of this vector and a scalar.
	 * 
	 * @param scalar Scalar number
	 */
	public default void mod(double scalar) {
		for(int i = 0; i < size(); i++) {
			setDouble(i, getDouble(i) % scalar);
		}
	}
	
	/** Computes the modulus of this vector and a scalar numeric value.
	 * 
	 * @param scalar Scalar number
	 */
	public default void mod(ScalarNumeric<?> scalar) {
		mod(scalar.getDouble());
	}
	
	/** Computes the modulus of this vector and another vector.
	 * 
	 * @param tuple Vector number
	 */
	public default void mod(VectorNumeric<?> tuple) {
		int count = Math.min(size(), tuple.size());
		for(int i = 0; i < count; i++) setDouble(i, getDouble(i) % tuple.getDouble(i));
	}
	
	/** Performs the binary operation on each element of the vector, using values
	 * from another vector. Will only compute from the beginning to the <u>minimum</u>
	 * number of elements in each vector.
	 * 
	 * @param op Binary operation
	 * @param other Other vector
	 */
	public default void binary(DoubleBinaryOperator op, VectorNumeric<?> other) {
		binary(op, other, false);
	}
	
	/** Performs the binary operation on each element of the vector, using values
	 * from another vector.
	 * 
	 * @param op Binary operation
	 * @param other Other vector
	 * @param toMax If the operation should continue to the maximum number of elements
	 */
	public default void binary(DoubleBinaryOperator op, VectorNumeric<?> other, boolean toMax) {
		int count = toMax ? Math.max(size(), other.size()) : Math.min(size(), other.size());
		for(int i = 0; i < count; i++) {
			setDouble(i, op.applyAsDouble(getDouble(i), other.getDouble(i)));
		}
	}
	
	/** Version of {@link #binary(DoubleBinaryOperator, VectorNumeric) binary()} that swaps the
	 * arguments to the operation.
	 * 
	 * @param op Binary operation
	 * @param other Other vector
	 */
	public default void binaryReverse(DoubleBinaryOperator op, VectorNumeric<?> other) {
		binaryReverse(op, other, false);
	}
	
	/** Version of {@link #binary(DoubleBinaryOperator, VectorNumeric, boolean) binary()} that swaps the
	 * arguments to the operation.
	 * 
	 * @param op Binary operation
	 * @param other Other vector
	 * @param toMax If the operation should continue to the maximum number of elements
	 */
	public default void binaryReverse(DoubleBinaryOperator op, VectorNumeric<?> other, boolean toMax) {
		int count = toMax ? Math.max(size(), other.size()) : Math.min(size(), other.size());
		for(int i = 0; i < count; i++) {
			setDouble(i, op.applyAsDouble(other.getDouble(i), getDouble(i)));
		}
	}
	
	/** Computes the dot-product of this vector and another vector.
	 * 
	 * @param other Other vector
	 * @return Dot-product of vectors
	 */
	public default double dot(VectorNumeric<?> other) {
		double sum = 0;
		int count = Math.min(size(), other.size());
		for(int i = 0; i < count; i++) sum += getDouble(i) * other.getDouble(i);
		return sum;
	}
	
	/** Computes the cross-product of this vector and another vector, storing
	 * the result in this vector. This only computes the cross products of
	 * the first three elements.
	 * 
	 * @param other Other vector
	 */
	public default void cross(VectorNumeric<?> other) {
		double a1 = getDouble(0), a2 = getDouble(1), a3 = getDouble(2);
		double b1 = other.getDouble(0), b2 = other.getDouble(1), b3 = other.getDouble(2);
		setDouble(0, (a2 * b3) - (a3 * b2));
		setDouble(1, (a3 * b1) - (a1 * b3));
		setDouble(2, (a1 * b2) - (a2 * b3));
	}
	
	public default void clear() {
		for(int i = 0; i < size(); i++) setDouble(i,0);
	}
	
}
