package com.zekrom_64.mathlib.shape;

import com.zekrom_64.mathlib.tuple.VectorNumeric;

/** A circle is defined by its center point and a radius.
 * 
 * @author Zekrom_64
 *
 */
public interface Circle {

	/** Gets the center of the circle.
	 * 
	 * @param pos Vector to set to center position
	 */
	public void getCenter(VectorNumeric<?> pos);
	
	/** Gets the radius of the circle.
	 * 
	 * @return Circle radius
	 */
	public double getRadius();
	
	/** Sets the value of this circle to another circle.
	 * 
	 * @param other Circle to set to
	 */
	public void set(Circle other);
	
}
