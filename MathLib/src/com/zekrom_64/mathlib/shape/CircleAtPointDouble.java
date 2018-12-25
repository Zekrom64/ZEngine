package com.zekrom_64.mathlib.shape;

import com.zekrom_64.mathlib.tuple.VectorNumeric;
import com.zekrom_64.mathlib.tuple.impl.Vector2Double;

/** A {@link Circle} defined by a center point and radius.
 * 
 * @author Zekrom_64
 *
 */
public class CircleAtPointDouble implements Circle {

	/** The center point of the circle. */
	public final Vector2Double center = new Vector2Double();
	/** The radius of the cirlce. */
	public double radius = 0;
	
	@Override
	public void getCenter(VectorNumeric<?> pos) {
		pos.set(pos);
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public void set(Circle other) {
		other.getCenter(center);
		radius = other.getRadius();
	}

}
