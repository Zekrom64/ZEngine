package com.zekrom_64.ze.base.image;

import java.awt.image.BufferedImage;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.function.Consumer;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.ze.base.ZEEnvironment;
import com.zekrom_64.ze.base.util.ZEBitBuffer;
import com.zekrom_64.ze.base.util.ZEPrimitiveType;

/** An image contains raw image data, along with some extra information for width, height, and pixel format. Although
 * images can be in any format, the default format used is 8-bit RGBA.
 * 
 * @author Zekrom_64
 *
 */
public class ZEImage {

	/** Buffer containing the pixel values */
	public final ByteBuffer buffer;
	/** The width of the image */
	public final int width;
	/** The height of the image */
	public final int height;
	/** The pixel format of the image */
	public final ZEPixelFormat format;
	
	private Consumer<ByteBuffer> releaser;
	
	/** Creates a new image of the given width and height with the {@link ZEPixelFormat#R8G8B8A8_UINT 8-bit RGBA} format.
	 * 
	 * @param w Image width
	 * @param h Image height
	 */
	public ZEImage(int w, int h) {
		this(w, h, ZEPixelFormat.R8G8B8A8_UINT);
	}
	
	/** Creates a new image of the given width, height, and pixel format.
	 * 
	 * @param w Image width
	 * @param h Image height
	 * @param fmt Pixel format
	 */
	public ZEImage(int w, int h, ZEPixelFormat fmt) {
		width = w;
		height = h;
		format = fmt;
		if (ZEEnvironment.optimizationZEImageUnsafeMemory) {
			buffer = LibCStdlib.calloc(w * h, fmt.sizeOf);
			releaser = LibCStdlib::free;
		} else {
			buffer = BufferUtils.createByteBuffer(w * h * fmt.sizeOf);
		}
	}
	
	/** Creates an image using an existing buffer, with the given width and height, with
	 * the {@link ZEPixelFormat#R8G8B8A8_UINT 8-bit RGBA} format.
	 * 
	 * @param data Image data buffer
	 * @param w Image width
	 * @param h Image height
	 */
	public ZEImage(ByteBuffer data, int w, int h) {
		buffer = data;
		width = w;
		height = h;
		format = ZEPixelFormat.R8G8B8A8_UINT;
	}
	
	/** Creates an image using an existing buffer, with the given width, height, and pixel format.
	 * 
	 * @param data Image data buffer
	 * @param w Image width
	 * @param h Image height
	 * @param fmt Pixel format
	 */
	public ZEImage(ByteBuffer data, int w, int h, ZEPixelFormat fmt) {
		buffer = data;
		width = w;
		height = h;
		format = fmt;
	}
	
	/** Creates an image using an existing buffer, with the given width, height, and pixel format
	 * and a custom buffer releaser.
	 * 
	 * @param data Image data buffer
	 * @param w Image width
	 * @param h Image height
	 * @param fmt Pixel format
	 * @param release Custom releaser
	 */
	public ZEImage(ByteBuffer data, int w, int h, ZEPixelFormat fmt, Consumer<ByteBuffer> release) {
		buffer = data;
		width = w;
		height = h;
		format = fmt;
		releaser = release;
	}
	
	/** Creates an image using an AWT {@link BufferedImage}, with a pixel format of
	 * {@link ZEPixelFormat#R8G8B8A8_UINT 8-bit RGBA}.
	 * 
	 * @param image Buffered image
	 */
	public ZEImage(BufferedImage image) {
		this(image, ZEPixelFormat.R8G8B8A8_UINT);
	}
	
	/** Creates an image using an AWT {@link BufferedImage}, converting it to the given format.
	 * 
	 * @param image Buffered image
	 * @param fmt Pixel format
	 */
	public ZEImage(BufferedImage image, ZEPixelFormat fmt) {
		this(image.getWidth(), image.getHeight());
		
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		boolean stackalloc = sp.getSize() - bp >= buffer.capacity();
		ByteBuffer argb = stackalloc ? sp.malloc(buffer.capacity()) : LibCStdlib.malloc(buffer.capacity());
		
		argb.order(ByteOrder.BIG_ENDIAN);
		argb.asIntBuffer().put(image.getRGB(0, 0, width, height, null, 0, width));
		transcodeInto(argb, buffer.duplicate(), width, height, ZEPixelFormat.A8R8G8B8_UINT, fmt);
		
		if (stackalloc) sp.setPointer(bp);
		else LibCStdlib.free(argb);
	}
	
	@Override
	protected void finalize() {
		if (releaser != null) {
			buffer.rewind();
			releaser.accept(buffer);
		}
	}
	
	/** Version of {@link #transcodeInto(ByteBuffer, ByteBuffer, int, int, ZEPixelFormat, ZEPixelFormat) transcodeInto} that creates
	 * a new buffer to hold the transcoded pixels.
	 * 
	 * @param source Source buffer
	 * @param width Image width
	 * @param height Image height
	 * @param srcFormat Source format
	 * @param dstFormat Destination format
	 * @return Transcoded buffer
	 */
	public static ByteBuffer transcode(ByteBuffer source, int width, int height, ZEPixelFormat srcFormat, ZEPixelFormat dstFormat) {
		ByteBuffer newbuf = BufferUtils.createByteBuffer(width * height * dstFormat.sizeOf);
		transcodeInto(source, newbuf, width, height, srcFormat, dstFormat);
		newbuf.rewind();
		return newbuf;
	}
	
	/** Transcodes pixels in one buffer to another buffer in a new format. If the source and destination formats are the same, the memory is
	 * only copied. Otherwise, the individual pixel components are converted to the destination format.
	 * 
	 * @param source Source buffer
	 * @param dest Destination buffer
	 * @param width Image width
	 * @param height Image height
	 * @param srcFormat Source format
	 * @param dstFormat Destination format
	 */
	public static void transcodeInto(ByteBuffer source, ByteBuffer dest, int width, int height, ZEPixelFormat srcFormat, ZEPixelFormat dstFormat) {
		if (srcFormat == dstFormat) {
			int bytes = srcFormat.sizeOf * width * height;
			if (dest.remaining() < bytes) throw new BufferOverflowException();
			MemoryUtil.memCopy(
					MemoryUtil.memAddress(source),
					MemoryUtil.memAddress(dest),
					bytes);
			return;
		}
		// Values for conversion
		int length = width * height;
		int srcElemSize = srcFormat.elementType.size * 8, dstElemSize = dstFormat.elementType.size * 8;
		boolean convertNorm = srcFormat.elementType.isFloating || dstFormat.elementType.isFloating;
		double srcMax = Math.pow(2, srcFormat.elementType.size * 8) - 1, dstMax = Math.pow(2, dstFormat.elementType.size * 8) - 1;
		if (srcFormat.elementType.isFloating) srcMax = 1;
		if (dstFormat.elementType.isFloating) dstMax = 1;
		
		byte[] pixel = new byte[Math.max(srcFormat.sizeOf, dstFormat.sizeOf)];
		ZEBitBuffer srcBitBuffer = new ZEBitBuffer(srcFormat.sizeOf * 8), dstBitBuffer = new ZEBitBuffer(dstFormat.sizeOf * 8);
		
		boolean hasRed = dstFormat.redOffset != -1 && srcFormat.redOffset != -1;
		boolean hasGreen = dstFormat.greenOffset != -1 && dstFormat.greenOffset != -1;
		boolean hasBlue = dstFormat.blueOffset != -1 && srcFormat.blueOffset != -1;
		boolean hasAlpha = dstFormat.alphaOffset != -1 && srcFormat.alphaOffset != -1;
		
		// For every pixel
		for(int i = 0; i < length; i++) {
			
			// Get bits from source pixel
			source.get(pixel, 0, srcFormat.sizeOf);
			srcBitBuffer.load(pixel);
			
			long bits;
			// Convert individual elements
			if (hasRed) {
				bits = convertElement(srcBitBuffer.readBits(srcFormat.redOffset, srcElemSize), convertNorm, srcFormat, dstFormat, srcMax, dstMax);
				dstBitBuffer.writeBits(dstFormat.redOffset, dstElemSize, bits);
			}
			if (hasGreen) {
				bits = convertElement(srcBitBuffer.readBits(srcFormat.greenOffset, srcElemSize), convertNorm, srcFormat, dstFormat, srcMax, dstMax);
				dstBitBuffer.writeBits(dstFormat.greenOffset, dstElemSize, bits);
			}
			if (hasBlue) {
				bits = convertElement(srcBitBuffer.readBits(srcFormat.blueOffset, srcElemSize), convertNorm, srcFormat, dstFormat, srcMax, dstMax);
				dstBitBuffer.writeBits(dstFormat.blueOffset, dstElemSize, bits);
			}
			if (hasAlpha) {
				bits = convertElement(srcBitBuffer.readBits(srcFormat.alphaOffset, srcElemSize), convertNorm, srcFormat, dstFormat, srcMax, dstMax);
				dstBitBuffer.writeBits(dstFormat.alphaOffset, dstElemSize, bits);
			}
			
			// Store bits to destination pixel
			dstBitBuffer.store(pixel);
			dest.put(pixel, 0, dstFormat.sizeOf);
		}
	}
	
	private static long convertElement(long bits, boolean convertNorm, ZEPixelFormat srcFormat, ZEPixelFormat dstFormat, double srcMax, double dstMax) {
		// If we have to convert to normalized form
		if (convertNorm) {
			double pixelNorm;
			// Convert source pixel into normalized double, assuming floating values are already normalized
			if (srcFormat.elementType == ZEPrimitiveType.FLOAT) pixelNorm = Float.intBitsToFloat((int)bits);
			else if (srcFormat.elementType == ZEPrimitiveType.DOUBLE) pixelNorm = Double.longBitsToDouble(bits);
			else pixelNorm = bits / srcMax;
			// Store normalized double as destination pixel format
			if (dstFormat.elementType == ZEPrimitiveType.FLOAT) return Float.floatToIntBits((float)pixelNorm) | 0l;
			else if (dstFormat.elementType == ZEPrimitiveType.DOUBLE) return Double.doubleToLongBits(pixelNorm);
			else return (long)(pixelNorm * dstMax);
		// Else it can be done by bit shifting
		} else {
			// If the destination format is smaller, shift right
			if (srcFormat.elementType.size > dstFormat.elementType.size) {
				return bits >> (srcFormat.elementType.size - dstFormat.elementType.size) * 8;
			// Else if the destination format is larger, shift right
			} else if (srcFormat.elementType.size < dstFormat.elementType.size) {
				return bits << (dstFormat.elementType.size - srcFormat.elementType.size) * 8;
			// Else if the sizes are the same, return the original value
			} else return bits;
		}
	}
	
	/** Transcodes this image to a new format. If the format given is the same as the current format,
	 * this image is returned.
	 * 
	 * @param format New format
	 * @return Transcoded image, or <b>this</b>
	 */
	public ZEImage transcode(ZEPixelFormat format) {
		if (format == this.format) return this;
		ZEImage image = new ZEImage(width, height, format);
		transcodeInto(buffer.duplicate(), image.buffer.duplicate(), width, height, this.format, format);
		return image;
	}
	
	/** Performs a block transfer from one image to another. The given values are clamped to  be inside
	 * the image. If there are no pixels to transfer, this returns immediately. If the source and
	 * destination pixel formats are not the same, the source image is converted to the same format
	 * before the block transfer is performed.
	 * 
	 * @param tw Transfer width
	 * @param th Transfer height
	 * @param src Source image
	 * @param srcx Source X coordinate
	 * @param srcy Source Y coordinate
	 * @param dst Destination image
	 * @param dstx Destination X coordinate
	 * @param dsty Destination Y coordinate
	 */
	public static void blit(int tw, int th, ZEImage src, int srcx, int srcy, ZEImage dst, int dstx, int dsty) {
		if (src.format != dst.format) src = src.transcode(dst.format);
		tw = Math.min(tw, Math.min(src.width - srcx, dst.width - dstx));
		th = Math.min(th, Math.min(src.height - srcy, dst.height - dsty));
		if (tw < 0 || th < 0) return;
		long srcptr = MemoryUtil.memAddress(src.buffer);
		long dstptr = MemoryUtil.memAddress(dst.buffer);
		int sizeof = src.format.sizeOf;
		if (tw == src.width && tw == dst.width) {
			MemoryUtil.memCopy(
					srcptr + (srcx + (srcy * src.width)) * src.format.sizeOf,
					dstptr + (dstx + (dsty * dst.width)) * dst.format.sizeOf,
					tw * th * sizeof);
		} else {
			for(int y = 0; y < th; y++) {
				MemoryUtil.memCopy(
						srcptr + (srcx + ((srcy + y) * src.width)) * src.format.sizeOf,
						dstptr + (dstx + ((dsty + y) * dst.width)) * dst.format.sizeOf,
						tw * sizeof);
			}
		}
	}
	
}
