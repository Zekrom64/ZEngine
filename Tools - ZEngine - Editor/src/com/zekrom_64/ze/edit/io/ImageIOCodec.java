package com.zekrom_64.ze.edit.io;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.swing.filechooser.FileFilter;

import com.zekrom_64.ze.base.util.ZEIOUtil;
import com.zekrom_64.ze.edit.io.IOImages.Image;
import com.zekrom_64.ze.edit.io.IOImages.ImageCodec;
import com.zekrom_64.ze.edit.text.NameMapping;
import com.zekrom_64.ze.edit.ui.util.FileFilterExtension;

public class ImageIOCodec implements ImageCodec {
	
	private Set<String> readFormats = new HashSet<>();
	private Set<String> writeFormats = new HashSet<>();
	
	private Map<String, FileFilterExtension> readFilters = new HashMap<>();
	private Map<String, FileFilterExtension> writeFilters = new HashMap<>();
	
	public ImageIOCodec() {
		Set<String> formats = new HashSet<>();
		List<String> rawFormats = Arrays.asList(ImageIO.getReaderFormatNames());
		rawFormats.replaceAll((String s) -> s.toLowerCase());
		formats.addAll(rawFormats);
		
		Map<Set<String>,FileFilterExtension> readFiltersByFormats = new HashMap<>();
		readFormats.addAll(formats);
		for(String format : formats) {
			Set<String> extensions = new HashSet<>();
			Iterator<ImageReader> itr = ImageIO.getImageReadersByFormatName(format);
			while(itr.hasNext()) {
				List<String> suffixes = Arrays.asList(itr.next().getOriginatingProvider().getFileSuffixes());
				suffixes.replaceAll((String s) -> s.toLowerCase());
				extensions.addAll(suffixes);
			}
			readFiltersByFormats.put(extensions, new FileFilterExtension(NameMapping.getFileFormatName(format), extensions.toArray(new String[0])));
		}
		for(Entry<Set<String>,FileFilterExtension> e : readFiltersByFormats.entrySet()) {
			readFilters.put(e.getKey().iterator().next(), e.getValue());
		}

		formats.clear();
		rawFormats = Arrays.asList(ImageIO.getReaderFormatNames());
		rawFormats.replaceAll((String s) -> s.toLowerCase());
		formats.addAll(rawFormats);
		
		Map<Set<String>,FileFilterExtension> writeFiltersByFormats = new HashMap<>();
		writeFormats.addAll(formats);
		for(String format : formats) {
			Set<String> extensions = new HashSet<>();
			Iterator<ImageWriter> itr = ImageIO.getImageWritersByFormatName(format);
			while(itr.hasNext()) {
				List<String> suffixes = Arrays.asList(itr.next().getOriginatingProvider().getFileSuffixes());
				suffixes.replaceAll((String s) -> s.toLowerCase());
				extensions.addAll(suffixes);
			}
			writeFiltersByFormats.put(extensions, new FileFilterExtension(NameMapping.getFileFormatName(format), extensions.toArray(new String[0])));
		}
		for(Entry<Set<String>,FileFilterExtension> e : writeFiltersByFormats.entrySet()) {
			writeFilters.put(e.getKey().iterator().next(), e.getValue());
		}
	}
	
	@Override
	public String name() {
		return "ImageIO";
	}

	@Override
	public Map<String, ? extends FileFilter> getReadFileFilters() {
		return readFilters;
	}
	
	@Override
	public String[] getCodecReadFileFormats() {
		return readFormats.toArray(new String[0]);
	}

	@Override
	public Map<String, ? extends FileFilter> getWriteFileFilters() {
		return writeFilters;
	}
	
	@Override
	public String[] getCodecWriteFileFormats() {
		return writeFormats.toArray(new String[0]);
	}

	@Override
	public Image readImage(File f) throws IOException {
		return new Image(ImageIO.read(f));
	}

	@Override
	public void writeImage(Image img, File f) throws IOException {
		ImageIO.write(img.getBufferedImage(), ZEIOUtil.getFileExtension(f.getName()), f);
	}

}
