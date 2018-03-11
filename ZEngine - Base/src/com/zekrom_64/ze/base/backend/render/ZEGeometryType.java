package com.zekrom_64.ze.base.backend.render;

/** Render backends can use different geometry types. This enumeration contains all cross-compatible
 * geometry types.
 * 
 * @author Zekrom_64
 *
 */
public enum ZEGeometryType {

	/** Geometry where each vertex is a single point */
	POINTS,
	/** Geometry where each pair of vertices is a single line segment */
	LINES,
	/** Geometry where the vertex buffer is a series of connected lines */
	LINE_STRIP,
	/** Geometry where each vertex triplet is a triangle */
	TRIANGLES,
	/** Geometry where the first 3 vertices are a triangle, and each subsequent vertex is the third
	 * point in a triangle using the previous 2 vertices.
	 */
	TRIANGLE_STRIP,
	/** Geometry where the first vertex is the center point of the fan, the following 2 vertices define
	 * the first triangle in the fan, and each subsequent vertex is the third point in a triangle using
	 * the center point and the previous vertex as the other vertices.
	 */
	TRIANGLE_FAN
	
}
