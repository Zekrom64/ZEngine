package com.zekrom_64.mathlib.shape;

import com.zekrom_64.mathlib.tuple.impl.Vector2Double;

/** A {@link Rectangle} defined by an origin point and extent size.
 * 
 * @author Zekrom_64
 *
 */
public class RectanglePosSizeDouble implements Rectangle {

	/** Origin point */
	public final Vector2Double position = new Vector2Double();
	/** Rectangle extent */
	public final Vector2Double extent = new Vector2Double();
	
	@Override
	public double getWidth() {
		return Math.abs(extent.x);
	}

	@Override
	public double getHeight() {
		return Math.abs(extent.y);
	}

	@Override
	public double getPositionX() {
		return position.x;
	}

	@Override
	public double getPositionY() {
		return position.y;
	}

	@Override
	public double getPositionX2() {
		return position.x + extent.x;
	}

	@Override
	public double getPositionY2() {
		return position.y + extent.y;
	}

	@Override
	public void set(Rectangle other) {
		position.x = other.getPositionX();
		position.y = other.getPositionY();
		extent.x = other.getWidth();
		extent.y = other.getHeight();
	}

}
