package com.zekrom_64.ze.edit.io;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileFilter;

import com.zekrom_64.ze.base.image.ZEImageLoader;
import com.zekrom_64.ze.edit.io.IOImages.Image;
import com.zekrom_64.ze.edit.io.IOImages.ImageCodec;
import com.zekrom_64.ze.edit.text.NameMapping;
import com.zekrom_64.ze.edit.ui.util.FileFilterExtension;

public class ZEImageCodec implements ImageCodec {

	private static Map<String,FileFilterExtension> readFormats = new HashMap<>();
	static {
		readFormats.put("jpeg", new FileFilterExtension(NameMapping.getFileFormatName("jpeg"), "jpeg", "jpg", "jpe", "jif", "jfif", "jfi"));
		readFormats.put("png", new FileFilterExtension(NameMapping.getFileFormatName("png"), "png"));
		readFormats.put("tga", new FileFilterExtension(NameMapping.getFileFormatName("tga"), "tga", "icb", "vda", "vst"));
		readFormats.put("bmp", new FileFilterExtension(NameMapping.getFileFormatName("bmp"), "bmp", "dib"));
		readFormats.put("psd", new FileFilterExtension(NameMapping.getFileFormatName("psd"), "psd"));
		readFormats.put("gif", new FileFilterExtension(NameMapping.getFileFormatName("gif"), "gif"));
		readFormats.put("hdr", new FileFilterExtension(NameMapping.getFileFormatName("hdr"), "hdr"));
		readFormats.put("pic", new FileFilterExtension(NameMapping.getFileFormatName("pic"), "pic"));
		readFormats.put("pnm", new FileFilterExtension(NameMapping.getFileFormatName("pnm"), "pgm", "ppm"));
	}
	
	@Override
	public String name() {
		return "ZEImageLoader";
	}

	@Override
	public Map<String, ? extends FileFilter> getReadFileFilters() {
		return readFormats;
	}

	@Override
	public String[] getCodecReadFileFormats() {
		return new String[] {
			"jpeg",
			"png",
			"tga",
			"bmp",
			"psd",
			"gif",
			"hdr",
			"pic",
			"pnm"
		};
	}

	@Override
	public Map<String, ? extends FileFilter> getWriteFileFilters() {
		return Collections.emptyMap();
	}

	@Override
	public String[] getCodecWriteFileFormats() {
		return new String[0];
	}

	@Override
	public Image readImage(File f) throws IOException {
		return new Image(ZEImageLoader.loadImage(f));
	}

	@Override
	public void writeImage(Image img, File f) throws IOException {
		throw new IOException("Codec does not support writing");
	}

}
