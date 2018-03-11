package com.zekrom_64.mathlib.shape;

/** A rectangle is defined by an origin point, and either an
 * extent vector or an extent point.
 * 
 * @author Zekrom_64
 *
 */
public interface Rectangle {

	/** Gets the width of the rectangle.
	 * 
	 * @return Rectangle width
	 */
	public double getWidth();
	
	/** Gets the height of the rectangle
	 * 
	 * @return Rectangle height
	 */
	public double getHeight();
	
	/** Gets the origin X coordinate
	 * 
	 * @return Origin X
	 */
	public double getPositionX();
	
	/** Gets the origin Y coordinate.
	 * 
	 * @return Origin Y
	 */
	public double getPositionY();
	
	/** Gets the extent point X coordinate.
	 * 
	 * @return Extent X
	 */
	public double getPositionX2();
	
	/**  Gets the extent point Y coordinate
	 * 
	 * @return Extent Y
	 */
	public double getPositionY2();
	
	/** Sets the value of this rectangle to another rectangle.
	 * 
	 * @param other Other rectangle
	 */
	public void set(Rectangle other);
	
}
