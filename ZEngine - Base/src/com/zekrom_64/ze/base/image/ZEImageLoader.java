package com.zekrom_64.ze.base.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import com.zekrom_64.ze.base.io.ZEInput;
import com.zekrom_64.ze.base.util.ZEIOUtil;

/** Class with methods to load images from compressed formats and convert between different encodings.
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
			pixel  = (pixel << 24) | (pixel >> 8);
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
			pixel = (pixel >> 24) | (pixel << 8);
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
			rgba.putInt(position + (i << 2), (pixel << 24) | (pixel >> 8));
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
			argb.putInt(position + (i << 2), (pixel >> 24) | (pixel << 8));
		}
		
		argb.position(position);
	}
	
	/** Attempts to load an image from an input using the {@link javax.imageio.ImageIO ImageIO} class.
	 * If a problem occurs when loading an image, an IOException is thrown.
	 * <p>Currently known supported formats:<ul>
	 * <li>JPEG</li>
	 * <li>PNG</li>
	 * <li>BMP</li>
	 * <li>WBMP</li>
	 * <li>GIF</li>
	 * </ul></p>
	 * <p><b>This method should never return null!</b></p>
	 * 
	 * <p>Note: Support for TIFF images is proposed in Java 9, and may be
	 * available via javax or another library in the future.</p>
	 * 
	 * @param input Input to decode image from
	 * @return Decoded image
	 * @throws IOException If an error occurred
	 */
	public static ZEImage loadImage(ZEInput input) throws IOException {
		return loadImage(input.openInput());
	}
	
	public static ZEImage loadImage(InputStream stream) throws IOException {
		byte[] data = ZEIOUtil.readFully(stream);
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
		buf.put(data).rewind();
		return loadImage(buf);
	}
	
	public static ZEImage loadImage(ByteBuffer memory) throws IOException {
		IntBuffer w = BufferUtils.createIntBuffer(1), h = BufferUtils.createIntBuffer(1);
		ByteBuffer data = STBImage.stbi_load_from_memory(memory, w, h, null, STBImage.STBI_rgb_alpha);
		if (data==null) {
			int pos = memory.position();
			byte[] buf = new byte[memory.remaining()];
			memory.get(buf);
			memory.position(pos);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(buf)) {
				BufferedImage img = ImageIO.read(bais);
				if (img==null) throw new IOException("Failed to read image");
				return new ZEImage(img);
			}
		}
		return new ZEImage(data, w.get(), h.get());
	}
	
	public static ZEImage loadImage(File file) throws IOException {
		BufferedImage img = ImageIO.read(file);
		if (img!=null) return new ZEImage(img);
		IntBuffer w = BufferUtils.createIntBuffer(1), h = BufferUtils.createIntBuffer(1), comp = BufferUtils.createIntBuffer(1);
		ByteBuffer buf = STBImage.stbi_load(file.getPath(), w, h, comp, STBImage.STBI_rgb_alpha);
		if (buf==null) throw new IOException("Failed to read image");
		return new ZEImage(buf, w.get(), h.get());
	}
	
	public static ZEImage loadImage(String respath) throws IOException {
		try (InputStream stream = ClassLoader.getSystemResourceAsStream(respath)) {
			return loadImage(stream);
		}
	}
	
	public static ZEImage loadImage(String respath, ClassLoader loader) throws IOException {
		try (InputStream stream = loader.getResourceAsStream(respath)) {
			return loadImage(stream);
		}
	}
	
	public static ZEImage loadImage(URL url) throws IOException {
		try (InputStream stream = url.openStream()) {
			return loadImage(stream);
		}
	}
	
}
