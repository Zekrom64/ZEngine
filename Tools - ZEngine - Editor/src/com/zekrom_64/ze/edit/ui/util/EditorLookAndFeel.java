package com.zekrom_64.ze.edit.ui.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class EditorLookAndFeel {

	private static ArrayList<LookAndFeel> looksAndFeels = new ArrayList<>();
	
	static {
		LookAndFeelInfo[] lafis = UIManager.getInstalledLookAndFeels();
		for(LookAndFeelInfo lafi : lafis) {
			try {
				Class<?> clazz = Class.forName(lafi.getClassName());
				looksAndFeels.add((LookAndFeel)clazz.newInstance());
			} catch (Exception e) { }
		}
	}
	
	public static List<LookAndFeelInfo> getLookAndFeelInfos() {
		ArrayList<LookAndFeelInfo> infos = new ArrayList<>(Arrays.asList(UIManager.getInstalledLookAndFeels()));
		return infos;
	}
	
	public static List<LookAndFeel> getLookAndFeels() {
		return Collections.unmodifiableList(looksAndFeels);
	}
	
}
