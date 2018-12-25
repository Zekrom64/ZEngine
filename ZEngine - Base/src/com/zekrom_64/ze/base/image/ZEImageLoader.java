package com.zekrom_64.ze.base.image;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.libc.LibCStdlib;

import com.zekrom_64.ze.base.io.ZEInput;
import com.zekrom_64.ze.base.util.ZEIOUtil;

/** Class with methods to load images from compressed formats. This currently uses bindings
 * from the STB Image library. The currently supported list of formats is:
 * <ul>
 * <li>JPEG</li>
 * <li>PNG (16 bpc not supported)</li>
 * <li>TGA</li> 
 * <li>BMP (non-1bpp, non-RLE)</li>
 * <li>PSD (composited view only, no extra channels, 8/16 bit-per-channel)</li>
 * <li>GIF</li>
 * <li>HDR</li>
 * <li>PIC</li>
 * <li>PNM (PPM and PGM binary only)</li>
 * </ul>
 * 
 * @author Zekrom_64
 *
 */
public class ZEImageLoader {

	/** Converts RGBA encoded data to ARGB encoded data at the current
	 * position in the supplied buffer.
	 * 
	 * @param rgba RGBA data
	 * @return ARGB data
	 */
	public static ByteBuffer rgbaToArgb(ByteBuffer rgba) {
		// Remaining number of ints
		int remainingPixels = rgba.remaining() & ~0x3;
		ByteBuffer out = BufferUtils.createByteBuffer(remainingPixels * 4);
		int position = rgba.position();
		
		for(int i = 0; i < remainingPixels; i++) {
			int pixel = rgba.getInt();
			pixel  = Integer.rotateRight(8, pixel);
			out.putInt(pixel);
		}
		
		rgba.position(position);
		out.rewind();
		return out;
	}
	
	/** Converts ARGB encoded data to RGBA encoded data at the current
	 * position in the supplied buffer.
	 * 
	 * @param argb ARGB data
	 * @return RGBA data
	 */
	public static ByteBuffer argbToRgba(ByteBuffer argb) {
		int remainingPixels = argb.remaining() & ~0x3;
		ByteBuffer out = BufferUtils.createByteBuffer(remainingPixels * 4);
		int position = argb.position();
		for(int i = 0; i < remainingPixels; i++) {
			int pixel = argb.getInt();
			pixel = Integer.rotateLeft(8, pixel);
			out.putInt(pixel);
		}
		argb.position(position);
		out.rewind();
		return out;
	}
	
	/** Version of {@link #rgbaToArgb(ByteBuffer)} that stores the converted
	 * data in the input buffer.
	 * 
	 * @param rgba RGBA in, ARGB out
	 */
	public static void rgbaToArgbNoCopy(ByteBuffer rgba) {
		// Remaining number of ints
		int remainingPixels = rgba.remaining() & ~0x3;
		
		int position = rgba.position();
		
		for(int i = 0; i < remainingPixels; i++) {
			int pixel = rgba.getInt();
			rgba.putInt(position + (i << 2), Integer.rotateRight(8, pixel));
		}
		
		rgba.position(position);
	}
	
	/** Version of {@link #argbToRgba(ByteBuffer)} that stores the converted
	 * data in the input buffer.
	 * 
	 * @param argb ARGB in, RGBA out
	 */
	public static void argbToRgbaNoCopy(ByteBuffer argb) {
		int remainingPixels = argb.remaining() & ~0x3;
		
		int position = argb.position();
		
		for(int i = 0; i < remainingPixels; i++) {
			int pixel = argb.getInt();
			argb.putInt(position + (i << 2), Integer.rotateLeft(8, pixel));
		}
		
		argb.position(position);
	}
	
	/** Interface for providing custom image readers. When loading an image,
	 * instances of this will be called with a memory buffer containing the
	 * image data.
	 * 
	 * @author Zekrom_64
	 *
	 */
	@FunctionalInterface
	public static interface ZEImageReader {
		
		/** Attempt to read an image from the memory buffer. The memory buffer will have
		 * its position set at the beginning of the image data. If an image was successfully
		 * read it must be returned. If there is an error, <b>null</b> should be returned.
		 * 
		 * @param mem Image data memory buffer
		 * @return Image read, or <b>null</b>
		 */
		public ZEImage attemptRead(ByteBuffer mem);
		
	}
	
	private static Set<ZEImageReader> customReaders = Collections.newSetFromMap(new ConcurrentHashMap<>());
	
	/** Adds a custom image reader to the image loader.
	 * 
	 * @param reader Custom reader
	 */
	public static void addCustomReader(ZEImageReader reader) {
		if (reader != null) customReaders.add(reader);
	}
	
	/** Attempts to read an image from an input. The stream is decoded using
	 * {@link #loadImage(InputStream)}.
	 * 
	 * @param input Input
	 * @return Decoded image
	 * @throws IOException If an IOException occurs reading the stream
	 */
	public static ZEImage loadImage(ZEInput input) throws IOException {
		return loadImage(input.openInput());
	}
	
	/** Attempts to read an image from an input stream. The stream is first read fully
	 * into memory, then decoded using {@link #loadImage(byte[])}.
	 * 
	 * @param stream Input stream
	 * @return Decoded image
	 * @throws IOException If an IOException occurs reading the stream
	 */
	public static ZEImage loadImage(InputStream stream) throws IOException {
		byte[] data = ZEIOUtil.readFully(stream);
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
		buf.put(data).rewind();
		return loadImage(buf);
	}

	/** Attempts to read an image from a memory buffer. Behavior is identical to
	 * {@link #loadImage(ByteBuffer)}, except a byte array is used for memory instead.
	 * 
	 * @param memory Memory buffer
	 * @return Decoded image
	 * @throws IOException If the image cannot be decoded
	 */
	public static ZEImage loadImage(byte[] memory) throws IOException {
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		boolean stackalloc = sp.getSize() - (bp + 12) >= memory.length;
		ByteBuffer pMemory = stackalloc ? sp.bytes(memory) : (ByteBuffer)LibCStdlib.malloc(memory.length).put(memory).rewind();
		
		Optional<ZEImage> custom = customReaders.stream()
				.map((ZEImageReader reader) -> {
					pMemory.rewind();
					return reader.attemptRead(pMemory);
				})
				.filter((ZEImage img) -> img != null)
				.findFirst();
		
		if (custom.isPresent()) {
			sp.setPointer(bp);
			if (!stackalloc) LibCStdlib.free(pMemory);
			return custom.get();
		}
		pMemory.rewind();
		
		IntBuffer w = sp.ints(0), h = sp.ints(0), comp = sp.ints(0);
		
		ByteBuffer buf = STBImage.stbi_load_from_memory(pMemory, w, h, comp, 0);
		int components = comp.get();
		sp.setPointer(bp);
		if (!stackalloc) LibCStdlib.free(pMemory);
		
		if (buf == null) return new ZEImage(ImageIO.read(new ByteArrayInputStream(memory)));
		
		ZEPixelFormat pxfmt = ZEPixelFormat.UNKNOWN;
		switch(components) {
		case STBImage.STBI_grey: pxfmt = ZEPixelFormat.R8_UINT; break;
		case STBImage.STBI_grey_alpha: pxfmt = ZEPixelFormat.R8G8_UINT; break;
		case STBImage.STBI_rgb: pxfmt = ZEPixelFormat.R8G8B8_UINT; break;
		case STBImage.STBI_rgb_alpha: pxfmt = ZEPixelFormat.R8G8B8A8_UINT; break;
		}
		return new ZEImage(buf, w.get(), h.get(), pxfmt);
	}
	
	/** Attempts to read an image from a memory buffer. Any custom loader that
	 * accepts the image data will try to load first. The decoding is then attempted using
	 * {@link STBImage#stbi_load_from_memory}. If that fails, it is decoded using
	 * {@link ImageIO#read(InputStream)}. If even that is unable to load the image an
	 * IOException is thrown.
	 * 
	 * @param memory Memory buffer
	 * @return Decoded image
	 * @throws IOException If the image cannot be decoded
	 */
	public static ZEImage loadImage(ByteBuffer memory) throws IOException {
		int memoryOffset = memory.position();
		Optional<ZEImage> custom = customReaders.stream()
				.map((ZEImageReader reader) -> {
					memory.position(memoryOffset);
					return reader.attemptRead(memory);
				})
				.filter((ZEImage img) -> img != null)
				.findFirst();
		if (custom.isPresent()) return custom.get();
		memory.position(memoryOffset);
		
		MemoryStack sp = MemoryStack.stackGet();
		int bp = sp.getPointer();
		IntBuffer w = sp.ints(0), h = sp.ints(0), comp = sp.ints(0);

		ByteBuffer buf = STBImage.stbi_load_from_memory(memory, w, h, comp, 0);
		int components = comp.get();
		sp.setPointer(bp);
		
		if (buf == null) {
			byte[] array = new byte[memory.remaining()];
			int pos = memory.position();
			memory.get(array);
			memory.position(pos);
			return new ZEImage(ImageIO.read(new ByteArrayInputStream(array)));
		}

		ZEPixelFormat pxfmt = ZEPixelFormat.UNKNOWN;
		switch(components) {
		case STBImage.STBI_grey: pxfmt = ZEPixelFormat.R8_UINT; break;
		case STBImage.STBI_grey_alpha: pxfmt = ZEPixelFormat.R8G8_UINT; break;
		case STBImage.STBI_rgb: pxfmt = ZEPixelFormat.R8G8B8_UINT; break;
		case STBImage.STBI_rgb_alpha: pxfmt = ZEPixelFormat.R8G8B8A8_UINT; break;
		}
		return new ZEImage(buf, w.get(), h.get(), pxfmt);
	}
	
	/** Attempts to read an image from a file. The stream is decoded using
	 * {@link #loadImage(ByteBuffer)}.
	 * 
	 * @param file Image file
	 * @return Decoded image
	 * @throws IOException If the image cannot be decoded
	 */
	public static ZEImage loadImage(File file) throws IOException {
		ByteBuffer data = LibCStdlib.malloc(file.length());
		try {
			FileChannel channel = FileChannel.open(file.toPath());
			while(data.hasRemaining()) {
				int read = channel.read(data);
				// Stream hit end of file, but its not done reading
				if (read == -1 && data.hasRemaining()) throw new IOException("Unexpected end of file when reading");
				// Stream is lagging behind, don't waste CPU time trying to read again immediately
				if (read == 0) {
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {}
				}
			}
			return loadImage(data);
		} finally {
			LibCStdlib.free(data);
		}
	}
	
	/** Attempts to read an image from a resource on the classpath. The stream is
	 * decoded using {@link #loadImage(InputStream)}.
	 * 
	 * @param respath Resource path
	 * @return Decoded image
	 * @throws IOException If an IOException occurs reading the stream
	 */
	public static ZEImage loadImage(String respath) throws IOException {
		try (InputStream stream = ClassLoader.getSystemResourceAsStream(respath)) {
			return loadImage(stream);
		}
	}
	/** Attempts to read an image from a resource available through a class loader.
	 * The stream is decoded using {@link #loadImage(InputStream)}.
	 * 
	 * @param respath Resource path
	 * @param loader Class loader
	 * @return Decoded image
	 * @throws IOException If an IOException occurs reading the stream
	 */
	public static ZEImage loadImage(String respath, ClassLoader loader) throws IOException {
		try (InputStream stream = loader.getResourceAsStream(respath)) {
			return loadImage(stream);
		}
	}
	
	/** Attempts to read an image from a URL address. The stream is
	 * decoded using {@link #loadImage(InputStream)}.
	 * 
	 * @param url URL address
	 * @return Decoded image
	 * @throws IOException If an IOException occurs reading the stream
	 */
	public static ZEImage loadImage(URL url) throws IOException {
		try (InputStream stream = url.openStream()) {
			return loadImage(stream);
		}
	}
	
}
