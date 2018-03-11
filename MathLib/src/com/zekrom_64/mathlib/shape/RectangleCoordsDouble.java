package com.zekrom_64.mathlib.shape;

import com.zekrom_64.mathlib.tuple.impl.Vector2Double;

/** A {@link Rectangle} defined by two points.
 * 
 * @author Zekrom_64
 *
 */
public class RectangleCoordsDouble implements Rectangle {

	/** First point */
	public final Vector2Double pos1 = new Vector2Double();
	/** Second point */
	public final Vector2Double pos2 = new Vector2Double();
	
	@Override
	public double getWidth() {
		return Math.abs(pos1.x - pos2.x);
	}

	@Override
	public double getHeight() {
		return Math.abs(pos1.y - pos2.y);
	}
	@Override
	public double getPositionX() {
		return Math.min(pos1.x, pos2.x);
	}

	@Override
	public double getPositionY() {
		return Math.min(pos1.y, pos2.y);
	}

	@Override
	public double getPositionX2() {
		return Math.max(pos1.x, pos2.x);
	}

	@Override
	public double getPositionY2() {
		return Math.max(pos1.y, pos2.y);
	}

	@Override
	public void set(Rectangle other) {
		pos1.x = other.getPositionX();
		pos1.y = other.getPositionY();
		pos2.x = other.getPositionX2();
		pos2.y = other.getPositionY2();
	}

}
