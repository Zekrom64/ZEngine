package com.zekrom_64.ze.edit.render;

import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.SwingUtilities;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.ze.edit.Editor;
import com.zekrom_64.ze.edit.ui.EditorRender;
import com.zekrom_64.ze.glfw.GLFWWindow;

public class ModelRender {
	
	private static final GLFWErrorCallback cbkError = new GLFWErrorCallback() {

		@Override
		public void invoke(int error, long description) {
			Editor.exceptionFatal("GLFW: " + MemoryUtil.memUTF8(description), null);
		}
		
	};
	
	private static GLFWWindow glfwWindow = new GLFWWindow();
	private static boolean glfwUpdateWindowSize;
	
	private static int renderWidth = 800, renderHeight = 600;
	private static int renderCenterX = 400, renderCenterY = 300;
	public static int getWidth() {
		return renderWidth;
	}
	public static int getHeight() {
		return renderHeight;
	}
	public static int getCenterX() {
		return renderCenterX;
	}
	public static int getCenterY() {
		return renderCenterY;
	}
	
	private static int[] renderOutputPixels = new int[renderWidth * renderHeight];
	private static double renderTimePerFrame = 1.0 / 30.0;
	private static int renderFramerate = 0;
	private static Lock renderLock = new ReentrantLock();
	
	private static final Thread renderThread = new Thread("OpenGL Render") {
		
		@Override
		public void run() {
			glfwWindow.bind();
			GL.createCapabilities();
			
			GL11.glClearColor(0, 0, 0, 0);
			
			double currentTime, lastTime, deltaTime = 0;
			currentTime = lastTime = GLFW.glfwGetTime();
			
			double fpsAccum = 0;
			int fpsCount = 0;
			
			double theta = 0;
			
			while(Editor.running()) {

				renderLock.lock();
				try {
					if (theta == 0) throw new Exception("test exception");
					
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
					GL11.glViewport(0, 0, renderWidth, renderHeight);
					
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glLoadIdentity();
					GL11.glOrtho(0, renderWidth, renderHeight, 0, 0, 1);
					
					GL11.glPushMatrix();
					GL11.glColor3f(1, 0, 0);
					GL11.glTranslatef(renderCenterX, renderCenterY, 0);
					GL11.glRotated(theta, 0, 0, 1);
					GL11.glRectf(-100,-100,100,100);
					GL11.glPopMatrix();
					
					GL11.glReadPixels(0, 0, renderWidth, renderHeight, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, renderOutputPixels);
				} catch (Exception e) {
					Editor.exceptionLog("Uncaught exception in renderer", e);
				} finally {
					renderLock.unlock();
				}
				
				currentTime = GLFW.glfwGetTime();
				deltaTime = currentTime - lastTime;
				lastTime = currentTime;
				
				theta += deltaTime * 60;
				
				fpsAccum += deltaTime;
				if (fpsAccum >= 1) {
					fpsAccum -= 1;
					renderFramerate = fpsCount;
					fpsCount = 0;
				} else fpsCount++;
				
				long waitTimeMillis = (long)((renderTimePerFrame - deltaTime) * 1000);
				if (waitTimeMillis > 0) {
					try {
						Thread.sleep(waitTimeMillis);
					} catch (InterruptedException e) {}
				}
				
				synchronized(EditorRender.renders) {
					for(EditorRender r : EditorRender.renders) SwingUtilities.invokeLater(r::repaint);
				}
			}
		}
		
	};
	
	public static void init() {
		GLFW.glfwSetErrorCallback(cbkError);
		GLFW.glfwInit();
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		glfwWindow.remakeWindow("Render", renderWidth, renderHeight, 0);
		
		renderThread.setDaemon(true);
		renderThread.start();
	}
	
	public static void deinit() {
		try {
			renderThread.join();
		} catch (InterruptedException e) { }
		glfwWindow.destroy();
		GLFW.glfwTerminate();
	}
	
	public static void updateEvent() {
		if (glfwUpdateWindowSize) {
			glfwWindow.setSize(renderWidth, renderHeight);
			glfwUpdateWindowSize = false;
		}
	}
	
	public static int getFramerate() {
		return renderFramerate;
	}
	
	public static void getOutput(BufferedImage bufImg) {
		renderLock.lock();
		try {
			bufImg.setRGB(0, 0, Math.min(bufImg.getWidth(), renderWidth), Math.min(bufImg.getHeight(), renderHeight), renderOutputPixels, 0, renderWidth);
		} finally {
			renderLock.unlock();
		}
	}
	
	public static void setSize(int width, int height) {
		if (width < 1) width = 1;
		if (height < 1) height = 1;
		renderLock.lock();
		try {
			if (renderWidth != width || renderHeight != height) {
				renderOutputPixels = new int[width*height];
				renderWidth = width;
				renderHeight = height;
				renderCenterX = renderWidth / 2;
				renderCenterY = renderHeight / 2;
				glfwUpdateWindowSize = true;
				GLFW.glfwPostEmptyEvent();
			}
		} finally {
			renderLock.unlock();
		}
	}
	
}
