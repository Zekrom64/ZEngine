package com.zekrom_64.ze.edit;

import java.awt.Window;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.glfw.GLFW;

import com.zekrom_64.ze.edit.render.ModelRender;
import com.zekrom_64.ze.edit.ui.EditorErrorLog;
import com.zekrom_64.ze.edit.ui.EditorErrorPanel;
import com.zekrom_64.ze.edit.ui.EditorUI;

public class Editor {

	public static final List<Window> uiWindows = new ArrayList<>();
	
	public static EditorUI editorUI;
	
	private static boolean running = true;
	public static boolean running() {
		return running;
	}
	
	public static void main(String[] args) {
		System.out.println("Starting up...");
		try {
			preinit();
			init();
			
			while(running) {
				ModelRender.updateEvent();
				GLFW.glfwWaitEventsTimeout(0.1);
			}
			
			deinit();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Uncaught exception: " + e.toString(), "Uncaught exception", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void preinit() {
		/*
		try {
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_LWJGL);
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_GLFW);
			LWJGLU3.extractAndLoadNatives(LWJGLU3.NATIVES_STB);
		} catch (RuntimeException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Failed to load natives: " + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		*/
		ModelRender.init();
	}

	private static void init() {
		uiWindows.add(editorUI = new EditorUI());
		editorUI.setVisible(true);
		editorUI.setExtendedState(editorUI.getExtendedState() | JFrame.MAXIMIZED_BOTH);
	}
	
	private static void deinit() {
		for(Window w : uiWindows) w.dispose();
		
		ModelRender.deinit();
	}
	
	public static void signalShutdown() {
		running = false;
	}
	
	public static void exceptionFatal(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) cause.printStackTrace();
		JOptionPane.showMessageDialog(null, new EditorErrorPanel(message, cause), "Fatal Error", JOptionPane.ERROR_MESSAGE);
		running = false;
	}
	
	public static void exceptionWarn(String message, Throwable cause) {
		Thread t = new Thread(() -> {
			JOptionPane.showMessageDialog(null, new EditorErrorPanel(message, cause), "Error", JOptionPane.ERROR_MESSAGE);
		}, "Error Warning");
		t.setDaemon(true);
		t.start();
		exceptionLog(message, cause);
	}
	
	public static void exceptionLog(String message, Throwable cause) {
		String errstr = message + System.lineSeparator();
		if (cause != null) {
			StringWriter sw = new StringWriter();
			cause.printStackTrace(new PrintWriter(sw));
			errstr += sw.toString() + System.lineSeparator();
		}
		for(EditorErrorLog errlog : EditorErrorLog.logs) errlog.append(errstr);
		System.err.print(errstr);
	}
	
}
