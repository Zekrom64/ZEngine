package com.zekrom_64.mathlib.shape;

import com.zekrom_64.mathlib.tuple.VectorNumeric;
import com.zekrom_64.mathlib.tuple.impl.Vector2D;

/** A {@link Circle} defined by a center point and radius.
 * 
 * @author Zekrom_64
 *
 */
public class CircleAtPointDouble implements Circle {

	/** The center point of the circle. */
	public final Vector2D center = new Vector2D();
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
