package com.zekrom_64.ze.base.io;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.lwjgl.system.MemoryStack;

/** The Bytes class performs conversion to and from raw bytes for different types.
 * 
 * @author Zekrom_64
 *
 */
public class ZEBytes {

	// Field holding the native byte order
	public static final ByteOrder NATIVE_ENDIANNESS = ByteOrder.nativeOrder();
	
	// Primitive conversion
	
	/** Converts a short to an array of two bytes in the native endianness.
	 * 
	 * @param s Short
	 * @return Short bytes
	 */
	public static byte[] get(short s) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return getBigEndian(s);
		else return getLittleEndian(s);
	}
	
	/** Converts a short to an array of two bytes in big endian-order.
	 * 
	 * @param s Short
	 * @return Short bytes
	 */
	public static byte[] getBigEndian(short s) {
		return new byte[] {
				(byte)(s >> 8),
				(byte)(s & 0xFF)
		};
	}
	
	/** Converts a short array to an array of two bytes in little-endian order.
	 * 
	 * @param s Short
	 * @return Short bytes
	 */
	public static byte[] getLittleEndian(short s) {
		return new byte[] {
				(byte)(s & 0xFF),
				(byte)(s >> 8)
		};
	}
	
	/** Converts an int to an array of 4 bytes in the native endianness
	 * 
	 * @param i Integer
	 * @return Integer bytes
	 */
	public static byte[] get(int i) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return getBigEndian(i);
		else return getLittleEndian(i);
	}
	
	/** Converts an int to an array of 4 bytes in big-endian order.
	 * 
	 * @param i Integer
	 * @return Integer bytes
	 */
	public static byte[] getBigEndian(int i) {
		return new byte[] {
				(byte)(i >> 24),
				(byte)(i >> 16),
				(byte)(i >> 8),
				(byte)(i & 0xFF)
		};
	}

	/** Converts an int to an array of 4 bytes in little-endian order.
	 * 
	 * @param i Integer
	 * @return Integer bytes
	 */
	public static byte[] getLittleEndian(int i) {
		return new byte[] {
				(byte)(i & 0xFF),
				(byte)(i >> 8),
				(byte)(i >> 16),
				(byte)(i >> 24)
		};
	}

	/** Converts a long to an array of 8 bytes in the native endianness.
	 * 
	 * @param l Long
	 * @return Integer bytes
	 */
	public static byte[] get(long l) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return getBigEndian(l);
		else return getLittleEndian(l);
	}
	
	/** Converts a long to an array of 8 bytes in big-endian order.
	 * 
	 * @param l Long
	 * @return Long bytes
	 */
	public static byte[] getBigEndian(long l) {
		return new byte[] {
				(byte)(l >> 56),
				(byte)(l >> 48),
				(byte)(l >> 40),
				(byte)(l >> 32),
				(byte)(l >> 24),
				(byte)(l >> 16),
				(byte)(l >> 8),
				(byte)(l & 0xFF)
		};
	}

	/** Converts a long to an array of 8 bytes in little-endian order.
	 * 
	 * @param l Long
	 * @return Long bytes
	 */
	public static byte[] getLittleEndian(long l) {
		return new byte[] {
				(byte)(l & 0xFF),
				(byte)(l >> 8),
				(byte)(l >> 16),
				(byte)(l >> 24),
				(byte)(l >> 32),
				(byte)(l >> 40),
				(byte)(l >> 48),
				(byte)(l >> 56),
		};
	}
	
	/** Converts a float to an array of 4 bytes in the native endianness.
	 * 
	 * @param f Float
	 * @return Float bytes
	 */
	public static byte[] get(float f) {
		return get(Float.floatToRawIntBits(f));
	}
	
	/** Converts a double to an array of 8 bytes in the native endianness.
	 * 
	 * @param d Double
	 * @return Double bytes
	 */
	public static byte[] get(double d) {
		return get(Double.doubleToRawLongBits(d));
	}
	
	private static byte[] ensureBytes(byte[] bytes, int count) {
		if (bytes.length < count) bytes = Arrays.copyOf(bytes, count);
		return bytes;
	}
	
	/** Converts a byte array to a short in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Short
	 */
	public static short toShort(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toShortBigEndian(bytes);
		else return toShortLittleEndian(bytes);
	}
	
	/** Converts a byte array to a short in big-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Short
	 */
	public static short toShortBigEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return (short)(
				(bytes[0] << 8) |
				(bytes[1])
		);
	}
	
	/** Converts a byte array to a short in little-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Short
	 */
	public static short toShortLittleEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return (short)(
				(bytes[0]) |
				(bytes[1] << 8)
		);
	}
	
	/** Converts a byte array to an int in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Integer
	 */
	public static int toInt(byte[] bytes) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toIntBigEndian(bytes);
		else return toIntLittleEndian(bytes);
	}
	
	/** Converts a byte array to an int in big-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Integer
	 */
	public static int toIntBigEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 4);
		return	(bytes[0] << 24) |
				(bytes[1] << 16) |
				(bytes[2] << 8) |
				(bytes[3]);
	}
	
	/** Converts a byte array to an int in little-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Integer
	 */
	public static int toIntLittleEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return	(bytes[3] << 24) |
				(bytes[2] << 16) |
				(bytes[1] << 8) |
				(bytes[0]);
	}
	
	/** Converts a byte array to a long in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Long
	 */
	public static long toLong(byte[] bytes) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toLongBigEndian(bytes);
		else return toLongLittleEndian(bytes);
	}
	
	/** Converts a byte array to a long in big-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Long
	 */
	public static long toLongBigEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 8);
		return	(bytes[0] << 56) |
				(bytes[1] << 48) |
				(bytes[2] << 40) |
				(bytes[3] << 32) |
				(bytes[4] << 24) |
				(bytes[5] << 16) |
				(bytes[6] << 8) |
				bytes[7];
	}
	
	/** Converts a byte array to a long in little-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Long
	 */
	public static long toLongLittleEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 8);
		return	(bytes[7] << 56) |
				(bytes[6] << 48) |
				(bytes[5] << 40) |
				(bytes[4] << 32) |
				(bytes[3] << 24) |
				(bytes[2] << 16) |
				(bytes[1] << 8) |
				bytes[0];
	}
	
	/** Converts a byte array to a float in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Float
	 */
	public static float toFloat(byte[] bytes) {
		return Float.intBitsToFloat(toInt(bytes));
	}
	
	/** Converts a byte array to a float in big-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Float
	 */
	public static float toFloatBigEndian(byte[] bytes) {
		return Float.intBitsToFloat(toIntBigEndian(bytes));
	}
	
	/** Converts a byte array to a float in little-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Float
	 */
	public static float toFloatLittleEndian(byte[] bytes) {
		return Float.intBitsToFloat(toIntLittleEndian(bytes));
	}
	
	/** Converts a byte array to a double in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Double
	 */
	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}
	
	/** Converts a byte array to a double in big-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Double
	 */
	public static double toDoubleBigEndian(byte[] bytes) {
		return Double.longBitsToDouble(toLongBigEndian(bytes));
	}
	
	/** Converts a byte array to a double in little-endian order.
	 * 
	 * @param bytes Byte array
	 * @return Double
	 */
	public static double toDoubleLittleEndian(byte[] bytes) {
		return Double.longBitsToDouble(toLongLittleEndian(bytes));
	}
	
	// Array conversion
	
	/** Converts an array of shorts to an array of bytes in the native endianness.
	 * 
	 * @param shorts Short array
	 * @return Byte array
	 */
	public static byte[] get(short ... shorts) {
		return get(NATIVE_ENDIANNESS, shorts);
	}
	
	/** Converts an array of shorts to an array of bytes in the given endianness.
	 * 
	 * @param order Endianness
	 * @param shorts Short array
	 * @return Byte array
	 */
	public static byte[] get(ByteOrder order, short ... shorts) {
		MemoryStack sp = MemoryStack.stackGet();
		boolean useStack = sp.getSize() - sp.getPointer() >= shorts.length * 2;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(shorts.length * 2) : ByteBuffer.allocate(shorts.length * 2).order(order);
		buf.asShortBuffer().put(shorts).rewind();
		if (useStack) {
			byte[] array = new byte[shorts.length * 2];
			buf.get(array);
			sp.setPointer(bp);
			return array;
		} else return buf.array();
	}
	
	/** Converts an array of ints to an array of bytes in the native endianness.
	 * 
	 * @param ints Int array
	 * @return Byte array
	 */
	public static byte[] get(int ... ints) {
		return get(NATIVE_ENDIANNESS, ints);
	}
	
	/** Converts an array of ints to an array of bytes in the given endianness.
	 * 
	 * @param order Endianness
	 * @param ints Int array
	 * @return Byte array
	 */
	public static byte[] get(ByteOrder order, int ... ints) {
		MemoryStack sp = MemoryStack.stackGet();
		boolean useStack = sp.getSize() - sp.getPointer() >= ints.length * 4;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(ints.length * 4) : ByteBuffer.allocate(ints.length * 4).order(order);
		buf.asIntBuffer().put(ints).rewind();
		if (useStack) {
			byte[] array = new byte[ints.length * 4];
			buf.get(array);
			sp.setPointer(bp);
			return array;
		} else return buf.array();
	}
	
	/** Converts an array of longs to an array of bytes in the native endianness.
	 * 
	 * @param longs Long array
	 * @return Byte array
	 */
	public static byte[] get(long ... longs) {
		return get(NATIVE_ENDIANNESS, longs);
	}
	
	/** Converts an array of longs to an array of bytes in the given endianness.
	 * 
	 * @param order Endianness
	 * @param longs Long array
	 * @return Byte array
	 */
	public static byte[] get(ByteOrder order, long ... longs) {
		MemoryStack sp = MemoryStack.stackGet();
		boolean useStack = sp.getSize() - sp.getPointer() >= longs.length * 8;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(longs.length * 8) : ByteBuffer.allocate(longs.length * 8).order(order);
		buf.asLongBuffer().put(longs).rewind();
		if (useStack) {
			byte[] array = new byte[longs.length * 8];
			buf.get(array);
			sp.setPointer(bp);
			return array;
		} else return buf.array();
	}
	
	/** Converts an array of floats to an array of bytes in the native endianness.
	 * 
	 * @param floats Float array
	 * @return Byte array
	 */
	public static byte[] get(float ... floats) {
		return get(NATIVE_ENDIANNESS, floats);
	}
	
	/** Converts an array of floats to an array of bytes in the given endianness.
	 * 
	 * @param order Endianness
	 * @param floats Float array
	 * @return Byte array
	 */
	public static byte[] get(ByteOrder order, float ... floats) {
		MemoryStack sp = MemoryStack.stackGet();
		boolean useStack = sp.getSize() - sp.getPointer() >= floats.length * 4;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(floats.length * 4) : ByteBuffer.allocate(floats.length * 4).order(order);
		buf.asFloatBuffer().put(floats).rewind();
		if (useStack) {
			byte[] array = new byte[floats.length * 4];
			buf.get(array);
			sp.setPointer(bp);
			return array;
		} else return buf.array();
	}
	
	/** Converts an array of doubles to an array of bytes in the native endianness.
	 * 
	 * @param doubles Double array
	 * @return Byte array
	 */
	public static byte[] get(double ... doubles) {
		return get(NATIVE_ENDIANNESS, doubles);
	}
	
	/** Converts an array of doubles to an array of bytes in the given endianness.
	 * 
	 * @param order Endianness
	 * @param doubles Double array
	 * @return Byte array
	 */
	public static byte[] get(ByteOrder order, double ... doubles) {
		MemoryStack sp = MemoryStack.stackGet();
		boolean useStack = sp.getSize() - sp.getPointer() >= doubles.length * 8;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(doubles.length * 8) : ByteBuffer.allocate(doubles.length * 8).order(order);
		buf.asDoubleBuffer().put(doubles).rewind();
		if (useStack) {
			byte[] array = new byte[doubles.length * 8];
			buf.get(array);
			sp.setPointer(bp);
			return array;
		} else return buf.array();
	}
	
	/** Converts an array of bytes to an array of shorts  in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Short array
	 */
	public static short[] toShortArray(byte[] bytes) {
		return toShortArray(NATIVE_ENDIANNESS, bytes);
	}
	
	/** Converts an array of bytes to an array of shorts in the given endianness.
	 * 
	 * @param order Endianness
	 * @param bytes Byte array
	 * @return Short array
	 */
	public static short[] toShortArray(ByteOrder order, byte[] bytes) {
		MemoryStack sp = MemoryStack.stackGet();
		int size = bytes.length / 2, sizeBytes = size * 2;
		boolean useStack = sp.getSize() - sp.getPointer() >= sizeBytes;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(sizeBytes) : ByteBuffer.allocate(sizeBytes);
		buf.order(order).put(bytes).rewind();
		short[] array = new short[size];
		buf.asShortBuffer().get(array);
		if (useStack) sp.setPointer(bp);
		return array;
	}
	
	/** Converts an array of bytes to an array of ints in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Integer array
	 */
	public static int[] toIntArray(byte[] bytes) {
		return toIntArray(NATIVE_ENDIANNESS, bytes);
	}
	
	/** Converts an array of bytes to an array of ints in the given endianness.
	 * 
	 * @param order Endianness
	 * @param bytes Byte array
	 * @return Integer array
	 */
	public static int[] toIntArray(ByteOrder order, byte[] bytes) {
		MemoryStack sp = MemoryStack.stackGet();
		int size = bytes.length / 4, sizeBytes = size * 4;
		boolean useStack = sp.getSize() - sp.getPointer() >= sizeBytes;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(sizeBytes) : ByteBuffer.allocate(sizeBytes);
		buf.order(order).put(bytes).rewind();
		int[] array = new int[size];
		buf.asIntBuffer().get(array);
		if (useStack) sp.setPointer(bp);
		return array;
	}
	
	/** Converts an array of bytes to an array of longs in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Long array
	 */
	public static long[] toLongArray(byte[] bytes) {
		return toLongArray(NATIVE_ENDIANNESS, bytes);
	}
	
	/** Converts an array of bytes to an array of longs in the given endianness.
	 * 
	 * @param order Endianness
	 * @param bytes Byte array
	 * @return Long array
	 */
	public static long[] toLongArray(ByteOrder order, byte[] bytes) {
		MemoryStack sp = MemoryStack.stackGet();
		int size = bytes.length / 8, sizeBytes = size * 8;
		boolean useStack = sp.getSize() - sp.getPointer() >= sizeBytes;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(sizeBytes) : ByteBuffer.allocate(sizeBytes);
		buf.order(order).put(bytes).rewind();
		long[] array = new long[size];
		buf.asLongBuffer().get(array);
		if (useStack) sp.setPointer(bp);
		return array;
	}
	
	/** Converts an array of bytes to an array of floats in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Float array
	 */
	public static float[] toFloatArray(byte[] bytes) {
		return toFloatArray(NATIVE_ENDIANNESS, bytes);
	}
	
	/** Converts an array of bytes to an array of floats in the given endianness.
	 * 
	 * @param order Endianness
	 * @param bytes Byte array
	 * @return Float array
	 */
	public static float[] toFloatArray(ByteOrder order, byte[] bytes) {
		MemoryStack sp = MemoryStack.stackGet();
		int size = bytes.length / 4, sizeBytes = size * 4;
		boolean useStack = sp.getSize() - sp.getPointer() >= sizeBytes;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(sizeBytes) : ByteBuffer.allocate(sizeBytes);
		buf.order(order).put(bytes).rewind();
		float[] array = new float[size];
		buf.asFloatBuffer().get(array);
		if (useStack) sp.setPointer(bp);
		return array;
	}
	
	/** Converts an array of bytes to an array of doubles in the native endianness.
	 * 
	 * @param bytes Byte array
	 * @return Double array
	 */
	public static double[] toDoubleArray(byte[] bytes) {
		return toDoubleArray(NATIVE_ENDIANNESS, bytes);
	}
	
	/** Converts an array of bytes to an array of doubles in the given endianness.
	 * 
	 * @param order Endianness
	 * @param bytes Byte array
	 * @return Double array
	 */
	public static double[] toDoubleArray(ByteOrder order, byte[] bytes) {
		MemoryStack sp = MemoryStack.stackGet();
		int size = bytes.length / 8, sizeBytes = size * 8;
		boolean useStack = sp.getSize() - sp.getPointer() >= sizeBytes;
		int bp = sp.getPointer();
		ByteBuffer buf = useStack ? sp.malloc(sizeBytes) : ByteBuffer.allocate(sizeBytes);
		buf.order(order).put(bytes).rewind();
		double[] array = new double[size];
		buf.asDoubleBuffer().get(array);
		if (useStack) sp.setPointer(bp);
		return array;
	}
	
}
