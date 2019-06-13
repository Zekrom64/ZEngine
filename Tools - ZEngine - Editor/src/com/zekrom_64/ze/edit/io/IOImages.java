package com.zekrom_64.ze.edit.io;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

import com.zekrom_64.ze.base.image.ZEImage;
import com.zekrom_64.ze.base.image.ZEPixelFormat;

public class IOImages {

	public static class Image {
		
		private ZEImage zeimage;
		private BufferedImage bufimage;
		
		public Image(ZEImage img) {
			zeimage = img;
		}
		
		public Image(BufferedImage img) {
			bufimage = img;
		}
		
		public ZEImage getZEImage() {
			if (zeimage == null) {
				switch(bufimage.getType()) {
				case BufferedImage.TYPE_INT_ARGB:
				case BufferedImage.TYPE_4BYTE_ABGR: {
					zeimage = new ZEImage(bufimage.getWidth(), bufimage.getHeight(), ZEPixelFormat.R8G8B8A8_UINT);
					int[] pixels = new int[zeimage.width * zeimage.height];
					bufimage.getRGB(0, 0, zeimage.width, zeimage.height, pixels, 0, zeimage.width);
					IntBuffer ib = zeimage.buffer.slice().asIntBuffer();
					for(int i = 0; i < pixels.length; i++) {
						int pixel = pixels[i];
						pixel = (pixel << 8) | (pixel >> 24);
						ib.put(pixel);
					}
				} break;
				case BufferedImage.TYPE_INT_BGR:
				case BufferedImage.TYPE_INT_RGB:
				case BufferedImage.TYPE_3BYTE_BGR: {
					zeimage = new ZEImage(bufimage.getWidth(), bufimage.getHeight(), ZEPixelFormat.R8G8B8_UINT);
					int[] pixels = new int[zeimage.width * zeimage.height];
					ByteBuffer bb = zeimage.buffer.slice();
					bufimage.getRGB(0, 0, zeimage.width, zeimage.height, pixels, 0, zeimage.width);
					for(int i = 0; i < pixels.length; i++) {
						int pixel = pixels[i];
						bb.put((byte)(pixel & 0xFF));
						bb.put((byte)((pixel >> 8) & 0xFF));
						bb.put((byte)((pixel >> 16) & 0xFF));
					}
				} break;
				case BufferedImage.TYPE_BYTE_GRAY: {
					zeimage = new ZEImage(bufimage.getWidth(), bufimage.getHeight(), ZEPixelFormat.R8_UINT);
					zeimage.buffer.slice().put(((DataBufferByte)bufimage.getRaster().getDataBuffer()).getData());
				} break;
				default: throw new IllegalArgumentException("Cannot convert BufferedImage (" + bufimage.getType() + ") to ZEImage");
				}
			}
			return zeimage;
		}
		
		public BufferedImage getBufferedImage() {
			if (bufimage == null) {
				switch(zeimage.format) {
				case R8G8B8A8_UINT: {
					bufimage = new BufferedImage(zeimage.width, zeimage.height, BufferedImage.TYPE_INT_ARGB);
					int[] pixels = new int[zeimage.width * zeimage.height];
					IntBuffer ib = zeimage.buffer.slice().asIntBuffer();
					for(int i = 0; i < pixels.length; i++) {
						int pixel = ib.get();
						pixel = (pixel >> 8) | (pixel << 24);
						pixels[i] = pixel;
					}
					bufimage.setRGB(0, 0, zeimage.width, zeimage.height, pixels, 0, zeimage.width);
				} break;
				case R8G8B8_UINT: {
					bufimage = new BufferedImage(zeimage.width, zeimage.height, BufferedImage.TYPE_INT_RGB);
					ByteBuffer bb = zeimage.buffer.slice();
					int[] pixels = new int[zeimage.width * zeimage.height];
					for(int i = 0; i < pixels.length; i++) {
						int r = bb.get() & 0xFF;
						int g = bb.get() & 0xFF;
						int b = bb.get() & 0xFF;
						pixels[i] = (r << 16) | (g << 8) | b;
					}
					bufimage.setRGB(0, 0, zeimage.width, zeimage.height, pixels, 0, zeimage.width);
				} break;
				case R8_UINT: {
					bufimage = new BufferedImage(zeimage.width, zeimage.height, BufferedImage.TYPE_BYTE_GRAY);
					DataBufferByte dbb = (DataBufferByte)bufimage.getRaster().getDataBuffer();
					zeimage.buffer.slice().get(dbb.getData());
				} break;
				default: throw new IllegalArgumentException("Cannot convert ZEImage (" + zeimage.format + ") to BufferedImage");
				}
			}
			return bufimage;
		}
		
	}
	
	public static interface ImageCodec {
		
		public String name();
		
		public Map<String, ? extends FileFilter> getReadFileFilters();
		
		public String[] getCodecReadFileFormats();
		
		public Map<String, ? extends FileFilter> getWriteFileFilters();
		
		public String[] getCodecWriteFileFormats();
		
		public Image readImage(File f) throws IOException;
		
		public void writeImage(Image img, File f) throws IOException;
		
	}
	
	public static final List<ImageCodec> codecs = new ArrayList<>();
	
	static {
		codecs.add(new ImageIOCodec());
		codecs.add(new ZEImageCodec());
	}
	
	public static FileFilter[] getReadFileFilters() {
		Map<String,FileFilter> readFilters = new HashMap<>();
		for(ImageCodec c : codecs) {
			Map<String, ? extends FileFilter> filters = c.getReadFileFilters();
			String[] formats = c.getCodecReadFileFormats();
			for(String fmt : formats) {
				if (readFilters.get(fmt) != null) readFilters.put(fmt, filters.get(fmt));
			}
		}
		Set<FileFilter> filters = new HashSet<>();
		filters.addAll(readFilters.values());
		return filters.toArray(new FileFilter[0]);
	}
	
	public static FileFilter[] getWriteFileFilters() {
		Map<String,FileFilter> writeFilters = new HashMap<>();
		for(ImageCodec c : codecs) {
			Map<String, ? extends FileFilter> filters = c.getWriteFileFilters();
			String[] formats = c.getCodecWriteFileFormats();
			for(String fmt : formats) {
				if (writeFilters.get(fmt) == null) writeFilters.put(fmt, filters.get(fmt));
			}
		}
		Set<FileFilter> filters = new HashSet<>();
		filters.addAll(writeFilters.values());
		return filters.toArray(new FileFilter[0]);
	}
	
	public static Image read(File f) throws IOException {
		for(ImageCodec c : codecs) {
			Map<String,? extends FileFilter> readFilters = c.getReadFileFilters();
			for(FileFilter ff : readFilters.values()) if (ff.accept(f)) return c.readImage(f);
		}
		throw new IOException("No codec found supporting image");
	}
	
	public static void write(Image img, File f) throws IOException {
		for(ImageCodec c : codecs) {
			Map<String,? extends FileFilter> readFilters = c.getReadFileFilters();
			for(FileFilter ff : readFilters.values()) {
				if (ff.accept(f)) {
					c.writeImage(img, f);
					return;
				}
			}
		}
		throw new IOException("No codec found supporting image");
	}
	
}
