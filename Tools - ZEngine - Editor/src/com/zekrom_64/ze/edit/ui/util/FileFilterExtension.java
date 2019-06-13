package com.zekrom_64.ze.edit.ui.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import com.zekrom_64.ze.base.util.ZEIOUtil;

public class FileFilterExtension extends FileFilter {

	public static final FileFilterExtension FILTER_TXT = new FileFilterExtension("Text File", "txt");
	
	public final String[] extensions;
	public final String name;
	
	public FileFilterExtension(String name, String ... exts) {
		this.name = name;
		extensions = exts;
	}

	@Override
	public boolean accept(File f) {
		String ext = ZEIOUtil.getFileExtension(f.getName());
		for(String e : extensions) if (ext.equalsIgnoreCase(e)) return true;
		return false;
	}

	@Override
	public String getDescription() {
		return name;
	}
	
}
