package com.zekrom_64.ze.edit.ui.util;

import java.io.File;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class EditorAsyncTasks {
	
	public static void saveFile(String title, FileFilter[] filters, File initPath, Consumer<File> saveCbk) {
		Thread t = new Thread("Async Task - Save File") {
			
			@Override
			public void run() {
				JFileChooser jfc = new JFileChooser();
				File dir = EditorPersistentData.lastScreenshotPath;
				if (!dir.isDirectory()) dir = dir.getParentFile();
				jfc.setCurrentDirectory(dir);
				jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jfc.setMultiSelectionEnabled(false);
				jfc.setDialogTitle(title);
				jfc.setAcceptAllFileFilterUsed(true);
				for(FileFilter f : filters) jfc.addChoosableFileFilter(f);
				int ret = jfc.showSaveDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) saveCbk.accept(jfc.getSelectedFile());
			}
			
		};
		t.setDaemon(true);
		t.start();
	}
	
}
