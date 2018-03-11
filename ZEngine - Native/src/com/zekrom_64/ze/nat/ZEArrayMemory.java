package com.zekrom_64.ze.nat;

import java.io.Closeable;

import org.lwjgl.system.Configuration;

/** <p>An array memory maps the native memory of a Java array. It implements the {@link java.io.Closeable Closeable}
 * interface, and is inteded to be used in a try-with-resources statement to ensure the native memory is released.
 * </p>
 * <p>Example:<br/>
 * <tt></br>
 * try (ZEArrayMemory mem = ZEArrayMemory.map(byteArray, true)) {<br/>
 * &nbsp;&nbsp;&nbsp;&nbsp;MemoryUtil.memSet(mem.getAddress(), 0, byteArray.length);</br>
 * }</br>
 * </tt>
 * </p>
 * 
 * @author Zekrom_64
 *
 */
public class ZEArrayMemory implements Closeable, ZENativeMemory {
	
	private static final boolean CHECKS = !Configuration.DISABLE_CHECKS.get();
	
	private Object array;
	private long address;
	private boolean writeBack;
	private int type;
	
	private ZEArrayMemory() {}
	
	/** Maps an array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 * @throws IllegalArgumentException If the object passed is not a mappable array
	 */
	public static native ZEArrayMemory map(Object array, boolean writeBack);
	
	/** Maps a boolean array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(boolean[] array, boolean writeBack);
	
	/** Maps a byte array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(byte[] array, boolean writeBack);
	
	/** Maps a short array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(short[] array, boolean writeBack);
	
	/** Maps a char array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(char[] array, boolean writeBack);
	
	/** Maps an int array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(int[] array, boolean writeBack);
	
	/** Maps a long array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(long[] array, boolean writeBack);
	
	/** Maps a float array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(float[] array, boolean writeBack);
	
	/** Maps a double array to native memory.
	 * 
	 * @param array Array to map
	 * @param writeBack If the native data should be written back to the array
	 * @return The mapped array memory
	 * @throws NullPointerException If the passed array is <b>null</b>
	 */
	public static native ZEArrayMemory map(double[] array, boolean writeBack);

	@Override
	public native void close();

	@Override
	public long getAddress() {
		return address;
	}
	
	@Override
	protected void finalize() {
		if (address != 0) close();
	}
}
