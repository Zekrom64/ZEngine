package com.zekrom_64.ze.edit;

import javax.swing.JOptionPane;

import com.zekrom_64.ze.lwjgl.LWJGLU3;

public class Editor {

	public static void main(String[] args) {
		System.out.println("Starting up...");
		try {
			preinit();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Uncaught exception: " + e.toString(), "Uncaught exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void preinit() {
		try {
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_LWJGL);
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_GLFW);
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_STB);
		} catch (RuntimeException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load natives: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}

}
