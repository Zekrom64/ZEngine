package com.zekrom_64.ze.glfw;

import java.awt.Dimension;
import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharModsCallback;
import org.lwjgl.glfw.GLFWCursorEnterCallback;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWDropCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowFocusCallback;
import org.lwjgl.glfw.GLFWWindowIconifyCallback;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import com.zekrom_64.ze.base.image.ZEImage;
import com.zekrom_64.ze.base.image.ZEImageLoader;
import com.zekrom_64.ze.base.input.ZEInputListener;
import com.zekrom_64.ze.base.input.ZEInputSource;
import com.zekrom_64.ze.base.input.ZEWindowListener;
import com.zekrom_64.ze.base.input.ZEWindowSource;
import com.zekrom_64.ze.gl.GLContext;
import com.zekrom_64.ze.nat.StructUtils;

public class GLFWWindow implements GLContext, ZEInputSource, ZEWindowSource {

	private long window = 0;
	
	private final GLFWWindowFocusCallback onFocus = new GLFWWindowFocusCallback() {

		@Override
		public void invoke(long window, boolean focused) {
			for(ZEWindowListener l : windowListeners) l.onFocus(focused);
		}
		
	};
	
	private final GLFWWindowIconifyCallback onIconify = new GLFWWindowIconifyCallback() {

		@Override
		public void invoke(long window, boolean iconified) {
			for(ZEWindowListener l : windowListeners) l.onIconify(iconified);
		}
		
	};
	
	private final GLFWWindowPosCallback onPos = new GLFWWindowPosCallback() {

		@Override
		public void invoke(long window, int xpos, int ypos) {
			for(ZEWindowListener l : windowListeners) l.onMove(xpos, ypos);
		}
		
	};
	
	private final GLFWWindowSizeCallback onSize = new GLFWWindowSizeCallback() {

		@Override
		public void invoke(long window, int width, int height) {
			for(ZEWindowListener l : windowListeners) l.onResize(width, height);
		}
		
	};
	
	private final GLFWCharModsCallback onCharMods = new GLFWCharModsCallback() {

		@Override
		public void invoke(long window, int codepoint, int mods) {
			for(ZEInputListener l : inputListeners) l.onChar((char)codepoint, mods);
		}
		
	};
	
	private double lastx = 0, lasty = 0;
	
	private final GLFWCursorEnterCallback onCursorEnter = new GLFWCursorEnterCallback() {

		private final DoubleBuffer xbuf = BufferUtils.createDoubleBuffer(1);
		private final DoubleBuffer ybuf = BufferUtils.createDoubleBuffer(1);
		
		@Override
		public void invoke(long window, boolean entered) {
			for(ZEInputListener l : inputListeners) l.onMouseEnter(entered);
			if (entered) {
				GLFW.glfwGetCursorPos(window, xbuf, ybuf);
				lastx = xbuf.get();
				lasty = ybuf.get();
				xbuf.rewind();
				ybuf.rewind();
			}
		}
		
	};
	
	private final GLFWCursorPosCallback onCursorPos = new GLFWCursorPosCallback() {
		
		@Override
		public void invoke(long window, double xpos, double ypos) {
			double dx = lastx - xpos;
			double dy = lasty - ypos;
			for(ZEInputListener l : inputListeners) l.onMouseMove(xpos, ypos, dx, dy);
			lastx = xpos;
			lasty = ypos;
		}
		
	};
	
	private final GLFWDropCallback onDrop = new GLFWDropCallback() {

		@Override
		public void invoke(long window, int count, long names) {
			String[] paths = new String[count];
			for(int i = 0; i < count; i++) {
				long strptr = MemoryUtil.memGetAddress(names);
				paths[i] = MemoryUtil.memUTF8(strptr);
				names += POINTER_SIZE;
			}
			for(ZEWindowListener l : windowListeners) l.onFileDrop(paths);
		}
		
	};
	
	private final GLFWKeyCallback onKey = new GLFWKeyCallback() {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			for(ZEInputListener l : inputListeners) l.onKey(key, action, mods);
		}
		
	};
	
	private final GLFWMouseButtonCallback onMouseButton = new GLFWMouseButtonCallback() {

		private final DoubleBuffer xbuf = BufferUtils.createDoubleBuffer(1);
		private final DoubleBuffer ybuf = BufferUtils.createDoubleBuffer(1);
		
		@Override
		public void invoke(long window, int button, int action, int mods) {
			GLFW.glfwGetCursorPos(window, xbuf, ybuf);
			double xpos = xbuf.get(), ypos = ybuf.get();
			for(ZEInputListener l : inputListeners) l.onMouseButton(xpos, ypos, button, action, mods);
			xbuf.rewind();
			ybuf.rewind();
		}
		
	};
	
	private final GLFWScrollCallback onScroll = new GLFWScrollCallback() {

		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			for(ZEInputListener l : inputListeners) l.onMouseScroll(xoffset, yoffset);
		}
		
	};
	
	/** Creates a window from a handle. This will overwrite several of the callbacks for the
	 * window.
	 * 
	 * @param handle GLFW window handle
	 */
	public GLFWWindow(long handle) {
		window = handle;
		setCallbacks(handle);
	}
	
	/** Creates an uninitialized window. */
	public GLFWWindow() { }
	
	private void setCallbacks(long window) {
		GLFW.glfwSetCharModsCallback(window, onCharMods);
		GLFW.glfwSetCursorEnterCallback(window, onCursorEnter);
		GLFW.glfwSetCursorPosCallback(window, onCursorPos);
		GLFW.glfwSetDropCallback(window, onDrop);
		GLFW.glfwSetWindowFocusCallback(window, onFocus);
		GLFW.glfwSetWindowIconifyCallback(window, onIconify);
		GLFW.glfwSetKeyCallback(window, onKey);
		GLFW.glfwSetMouseButtonCallback(window, onMouseButton);
		GLFW.glfwSetWindowPosCallback(window, onPos);
		GLFW.glfwSetWindowSizeCallback(window, onSize);
		GLFW.glfwSetScrollCallback(window, onScroll);
	}
	
	@Override
	public void bind() {
		GLFW.glfwMakeContextCurrent(window);
	}

	@Override
	public void swapBuffers() {
		if (window!=0) GLFW.glfwSwapBuffers(window);
	}
	
	private final List<ZEInputListener> inputListeners = new ArrayList<ZEInputListener>();
	private final List<ZEWindowListener> windowListeners = new ArrayList<ZEWindowListener>();

	@Override
	public void addInputListener(ZEInputListener l) {
		if (l!=null&&!inputListeners.contains(l)) inputListeners.add(l);
	}

	@Override
	public void removeInputListener(ZEInputListener l) {
		inputListeners.remove(l);
	}

	@Override
	public void addWindowListener(ZEWindowListener l) {
		if (l!=null&!windowListeners.contains(l)) windowListeners.add(l);
	}

	@Override
	public void removeWindowListener(ZEWindowListener l) {
		windowListeners.remove(l);
	}
	
	@Override
	protected void finalize() {
		if (window!=0) GLFW.glfwDestroyWindow(window);
	}
	
	/** Sets if the window is visible.
	 * 
	 * @param visible If the window should be visible
	 */
	public void setVisible(boolean visible) {
		if (window!=0) {
			if (visible) GLFW.glfwShowWindow(window);
			else GLFW.glfwHideWindow(window);
		}
	}
	
	/** Recreates the window with the same size and fullscreen settings as the previous one, or
	 * uses a default size of 800 x 600 and fullscreen setting of windowed. <s>A new title must be
	 * supplied, as there is no way in GLFW to retrieve a window's title.</s> (This can be provided
	 * from an internal field of the last known title. It may still be inaccurate if the title is
	 * set through the native GLFW interface). This method does not
	 * care if a window has already been created, or if the window has not been created.
	 * 
	 * @param title The new window's title, or null to use the last known title
	 */
	public void remakeWindow(String title) {
		if (title == null) title = this.title;
		long monitor = 0;
		int w = 800, h = 600;
		if (window!=0) {
			MemoryStack stack = MemoryStack.stackGet();
			int sp = stack.getPointer();
			IntBuffer wb = stack.callocInt(1), hb = stack.callocInt(1);
			GLFW.glfwGetWindowSize(window, wb, hb);
			w = wb.get(0);
			h = hb.get(0);
			stack.setPointer(sp);
			monitor = GLFW.glfwGetWindowMonitor(window);
			GLFW.glfwDestroyWindow(window);
		}
		window = GLFW.glfwCreateWindow(w, h, title, monitor, 0);
		setCallbacks(window);
	}
	
	/** Alternate version of {@link #remakeWindow(String)} that allows width, height, and fullscreen
	 * monitor to be specified.
	 * 
	 * @param title The new window's title
	 * @param width The new window's width
	 * @param height The new window's height
	 * @param monitor The new window's fullscreen monitor, or 0
	 */
	public void remakeWindow(String title, int width, int height, long monitor) {
		long newwnd = GLFW.glfwCreateWindow(width, height, title, monitor, window);
		if (window!=0) GLFW.glfwDestroyWindow(window);
		window = newwnd;
		setCallbacks(window);
	}
	
	/** Determines if the window is visible.
	 * 
	 * @return If the window is visible
	 */
	public boolean isVisible() {
		return window == 0 ? false : (GLFW.glfwGetWindowAttrib(window, GLFW.GLFW_VISIBLE) == GLFW.GLFW_TRUE);
	}
	
	/** Makes the window fullscreen if the window is in windowed mode or the window is fullscreen on another monitor.
	 * This function destroys the old window and creates a new fullscreen one at the same resolution as the monitor
	 * it is created on. Keeping the same resolution as the native display can provide better performance sometimes,
	 * as no mode sitching needs to be performed.
	 * 
	 * @param monitor The monitor to make fullscreen on
	 */
	public void makeFullscreen(long monitor) {
		GLFWVidMode mode = GLFW.glfwGetVideoMode(monitor);
		makeFullscreen(monitor, mode.width(), mode.height());
	}
	
	/** Does the same function as {@link #makeFullscreen(long)}, but allows a custom resolution to be set. This makes
	 * changing between fullscreen and desktop slower, but allows custom resolutions.
	 * 
	 * @param monitor The monitor to make fullscreen on
	 * @param width The new window width
	 * @param height The new window height
	 */
	public void makeFullscreen(long monitor, int width, int height) {
		GLFW.glfwSetWindowMonitor(window, monitor, 0, 0, width, height, GLFW.GLFW_DONT_CARE);
	}
	
	/** Changes back to windowed mode by destroying the old window and creating a new one with the last know window
	 * size, or the fullscreen size if the window was created in fullscreen mode.
	 * 
	 */
	public void unmakeFullscreeen() {
		if (window==0) return;
		if (GLFW.glfwGetWindowMonitor(window)==0) return;
	}
	
	/** Returns true if the window is fullscreen on any monitor.
	 * 
	 * @return If the window is fullscreen
	 */
	public boolean isFullscreen() {
		return window == 0 ? false : (GLFW.glfwGetWindowMonitor(window) != 0);
	}
	
	private String title = "";
	
	/** Sets the window title.
	 * 
	 * @param title New window title
	 */
	public void setTitle(String title) {
		if (window!=0) {
			GLFW.glfwSetWindowTitle(window, title);
			this.title = title;
		}
	}
	
	/** Gets the last known title for the window.
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/** Sets the window cursor from a native cursor.
	 * 
	 * @param cursor Native cursor
	 */
	public void setCursor(long cursor) {
		if (window!=0) GLFW.glfwSetCursor(window, cursor);
	}
	
	/** Sets the window cursor.
	 * 
	 * @param cursor GLFW cursor
	 */
	public void setCursor(GLFWCursor cursor) {
		if (window!=0) GLFW.glfwSetCursor(window, cursor.glfwcursor);
	}
	
	/** Returns true if the close flag of the window is set.
	 * 
	 * @return If the window is requested to close
	 */
	public boolean isCloseRequested() {
		return window == 0 ? false : GLFW.glfwWindowShouldClose(window);
	}
	
	/** Sets the close flag of the window.
	 * 
	 * @param shouldClose Window close flag
	 */
	public void setCloseRequested(boolean shouldClose) {
		if (window!=0) GLFW.glfwSetWindowShouldClose(window, shouldClose);
	}
	
	/** Gets the native context for the current window
	 * 
	 * @return
	 */
	public long getNativeContext() {
		return GLFWU.glfwGetPlatformContext(window);
	}
	
	/** Destroys the GLFW window stored.
	 * 
	 */
	public void destroy() {
		GLFW.glfwDestroyWindow(window);
	}
	
	/** Gets the size of the current window.
	 * 
	 * @return Window size
	 */
	public Dimension getSize() {
		MemoryStack stack = MemoryStack.stackGet();
		int sp = stack.getPointer();
		IntBuffer bufW = stack.callocInt(1);
		IntBuffer bufH = stack.callocInt(1);
		GLFW.glfwGetWindowSize(window, bufW, bufH);
		stack.setPointer(sp);
		Dimension dim = new Dimension(bufW.get(0), bufH.get(0));
		return dim;
	}
	
	/** Iconifies (minimizes) the window.
	 * 
	 */
	public void iconify() {
		if (window!=0) GLFW.glfwIconifyWindow(window);
	}
	
	/** Restores the window from an iconified or maximized state to a windowed state.
	 * 
	 */
	public void restore() {
		if (window!=0) GLFW.glfwRestoreWindow(window);
	}
	
	/** Sets the usable window icons for this window.
	 * 
	 * @param images Icon images
	 */
	public void setIcons(ZEImage[] images) {
		if (window!=0) {
			GLFWImage.Buffer buf = StructUtils.createStructBuffer(images.length, GLFWImage.class);
			for(ZEImage img : images) {
				GLFWImage image = buf.get();
				image.width(img.width);
				image.height(img.height);
				image.pixels(ZEImageLoader.rgbaToArgb(img.buffer));
			}
			buf.rewind();
			GLFW.glfwSetWindowIcon(window, buf);
		}
	}
	
	/** Maximizes the window.
	 * 
	 */
	public void maximize() {
		if (window!=0) GLFW.glfwMaximizeWindow(window);
	}
	
	/** Sets the size limits for this window. If a parameter is set as {@link GLFW#GLFW_DONT_CARE GLFW_DONT_CARE},
	 * the limit is ignored. Undefined behavior may occur if a conflicting aspect ratio is set.
	 * 
	 * @param minw Minimum width
	 * @param minh Minimum height
	 * @param maxw Maximum width
	 * @param maxh Maximum height
	 */
	public void setSizeLimit(int minw, int minh, int maxw, int maxh) {
		if (window!=0) GLFW.glfwSetWindowSizeLimits(window, minw, minh, maxw, maxh);
	}
	
	/** Sets the aspect ratio for this window. If both parameters are set as {@link GLFW#GLFW_DONT_CARE GLFW_DONT_CARE},
	 * the required ratio is ignored. Undefined behavior may occur if a conflicting size limit is set.
	 * 
	 * @param numer Ratio numerator
	 * @param denom Ratio denominator
	 */
	public void setAspectRatio(int numer, int denom) {
		if (window!=0) GLFW.glfwSetWindowAspectRatio(window, numer, denom);
	}
	
	/** Focuses the window for input and brings it to the front.
	 * 
	 */
	public void focus() {
		if (window!=0) GLFW.glfwFocusWindow(window);
	}
	
	/** Gets the GLFW monitor this window is fullscreen on, or 0 if the window is not fullscreen or not created.
	 * 
	 * @return GLFW monitor the window is fullscreen on
	 */
	public long getGLFWMonitor() {
		if (window==0) return 0;
		return GLFW.glfwGetWindowMonitor(window);
	}

}
