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
public class Bytes {

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
	 * @param s
	 * @return
	 */
	public static byte[] getLittleEndian(short s) {
		return new byte[] {
				(byte)(s & 0xFF),
				(byte)(s >> 8)
		};
	}
	
	public static byte[] get(int i) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return getBigEndian(i);
		else return getLittleEndian(i);
	}
	
	public static byte[] getBigEndian(int i) {
		return new byte[] {
				(byte)(i >> 24),
				(byte)(i >> 16),
				(byte)(i >> 8),
				(byte)(i & 0xFF)
		};
	}
	
	public static byte[] getLittleEndian(int i) {
		return new byte[] {
				(byte)(i & 0xFF),
				(byte)(i >> 8),
				(byte)(i >> 16),
				(byte)(i >> 24)
		};
	}
	
	public static byte[] get(long l) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return getBigEndian(l);
		else return getLittleEndian(l);
	}
	
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
	
	public static byte[] get(float f) {
		return get(Float.floatToRawIntBits(f));
	}
	
	public static byte[] get(double d) {
		return get(Double.doubleToRawLongBits(d));
	}
	
	private static byte[] ensureBytes(byte[] bytes, int count) {
		if (bytes.length < count) bytes = Arrays.copyOf(bytes, count);
		return bytes;
	}
	
	public static short toShort(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toShortBigEndian(bytes);
		else return toShortLittleEndian(bytes);
	}
	
	public static short toShortBigEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return (short)(
				(bytes[0] << 8) |
				(bytes[1])
		);
	}
	
	public static short toShortLittleEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return (short)(
				(bytes[0]) |
				(bytes[1] << 8)
		);
	}
	
	public static int toInt(byte[] bytes) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toIntBigEndian(bytes);
		else return toIntLittleEndian(bytes);
	}
	
	public static int toIntBigEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 4);
		return	(bytes[0] << 24) |
				(bytes[1] << 16) |
				(bytes[2] << 8) |
				(bytes[3]);
	}
	
	public static int toIntLittleEndian(byte[] bytes) {
		bytes = ensureBytes(bytes, 2);
		return	(bytes[3] << 24) |
				(bytes[2] << 16) |
				(bytes[1] << 8) |
				(bytes[0]);
	}
	
	public static long toLong(byte[] bytes) {
		if (NATIVE_ENDIANNESS==ByteOrder.BIG_ENDIAN) return toLongBigEndian(bytes);
		else return toLongLittleEndian(bytes);
	}
	
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
	
	public static float toFloat(byte[] bytes) {
		return Float.intBitsToFloat(toInt(bytes));
	}
	
	public static float toFloatBigEndian(byte[] bytes) {
		return Float.intBitsToFloat(toIntBigEndian(bytes));
	}
	
	public static float toFloatLittleEndian(byte[] bytes) {
		return Float.intBitsToFloat(toIntLittleEndian(bytes));
	}
	
	public static double toDouble(byte[] bytes) {
		return Double.longBitsToDouble(toLong(bytes));
	}
	
	public static double toDoubleBigEndian(byte[] bytes) {
		return Double.longBitsToDouble(toLongBigEndian(bytes));
	}
	
	public static double toDoubleLittleEndian(byte[] bytes) {
		return Double.longBitsToDouble(toLongLittleEndian(bytes));
	}
	
	// Array conversion
	
	public static byte[] get(short ... shorts) {
		return get(NATIVE_ENDIANNESS, shorts);
	}
	
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
	
	public static byte[] get(int ... ints) {
		return get(NATIVE_ENDIANNESS, ints);
	}
	
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
	
	public static byte[] get(long ... longs) {
		return get(NATIVE_ENDIANNESS, longs);
	}
	
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
	
	public static byte[] get(float ... floats) {
		return get(NATIVE_ENDIANNESS, floats);
	}
	
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
	
	public static byte[] get(double ... doubles) {
		return get(NATIVE_ENDIANNESS, doubles);
	}
	
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
	
	public static short[] toShortArray(byte[] bytes) {
		return toShortArray(NATIVE_ENDIANNESS, bytes);
	}
	
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
	
	public static int[] toIntArray(byte[] bytes) {
		return toIntArray(NATIVE_ENDIANNESS, bytes);
	}
	
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
	
	public static long[] toLongArray(byte[] bytes) {
		return toLongArray(NATIVE_ENDIANNESS, bytes);
	}
	
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
	
	public static float[] toFloatArray(byte[] bytes) {
		return toFloatArray(NATIVE_ENDIANNESS, bytes);
	}
	
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
	
	public static double[] toDoubleArray(byte[] bytes) {
		return toDoubleArray(NATIVE_ENDIANNESS, bytes);
	}
	
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
