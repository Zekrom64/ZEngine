package com.zekrom_64.ze.base.image;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

/** An image contains raw image data, along with some extra information for width, height, and pixel format. Although
 * images can be in any format, the default format used is 8-bit RGBA.
 * 
 * @author Zekrom_64
 *
 */
public class ZEImage {

	public final ByteBuffer buffer;
	public final int width;
	public final int height;
	public final ZEPixelFormat format;
	
	public ZEImage(int w, int h) {
		this(w, h, ZEPixelFormat.R8G8B8A8);
	}
	
	public ZEImage(int w, int h, ZEPixelFormat fmt) {
		width = w;
		height = h;
		format = fmt;
		buffer = BufferUtils.createByteBuffer(w * h * fmt.sizeOf);
	}
	
	public ZEImage(ByteBuffer data, int w, int h) {
		buffer = data;
		width = w;
		height = h;
		format = ZEPixelFormat.R8G8B8A8;
	}
	
	public ZEImage(ByteBuffer data, int w, int h, ZEPixelFormat fmt) {
		buffer = data;
		width = w;
		height = h;
		format = fmt;
	}
	
	public ZEImage(BufferedImage image) {
		width = image.getWidth();
		height = image.getHeight();
		buffer = BufferUtils.createByteBuffer(width * height * 4);
		int[] pixels = image.getRaster().getPixels(0, 0, width, height, (int[])null);
		buffer.asIntBuffer().put(pixels).rewind();
		ZEImageLoader.argbToRgbaNoCopy(buffer);
		format = ZEPixelFormat.R8G8B8A8;
	}
	
	public ZEImage(BufferedImage image, ZEPixelFormat fmt) {
		width = image.getWidth();
		height = image.getHeight();
		// TODO: Implement transcoding
		buffer = null;
		format = fmt;
	}
	
}
