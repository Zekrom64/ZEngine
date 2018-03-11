package com.zekrom_64.ze.gl;

import java.awt.Canvas;
import java.nio.IntBuffer;

import org.bridj.Pointer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLX;
import org.lwjgl.opengl.WGL;
import org.lwjgl.system.Platform;
import org.lwjgl.system.linux.XVisualInfo;
import org.lwjgl.system.windows.GDI32;
import org.lwjgl.system.windows.PIXELFORMATDESCRIPTOR;
import org.lwjgl.system.windows.User32;

import com.zekrom_64.ze.nat.lin.X11;
import com.zekrom_64.ze.nat.win.WinTypes.HDC;
import com.zekrom_64.ze.nat.win.WinTypes.HGLRC;

import sun.awt.X11.XComponentPeer;
import sun.awt.windows.WComponentPeer;

/** Replacement for LWJGL 2 AWTGLCanvas that can render in OpenGL in an AWT canvas. It attempts
 * to create a double buffered 32bpp RGBA OpenGL context in the canvas.
 * 
 * @author Zekrom_64
 *
 */
@SuppressWarnings("serial" )
public class GLCanvas extends Canvas implements GLContext {
	
	private long glcontext = 0;
	private long hdc = 0;
	private long dpy = 0;
	private long win = 0;
	
	private Platform platform = Platform.get();
	
	/** <p>Creates a new OpenGL context for this canvas. If a context
	 * already exists, it is destroyed.</p>
	 * 
	 * <p>The peer object for this canvas must be created before this method is called, and
	 * a simple wait loop testing {@link java.awt.Canvas#getPeer() getPeer()} to be non-null
	 * is recommended before a call to this function.</p>
	 */
	public void initGL() {
		initGLShared(0);
	}
	
	/** Version of {@link #initGL()} that allows sharing resources with another OpenGL context.
	 * 
	 * @param ctx
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void initGLShared(long ctx) {
		if (platform==Platform.WINDOWS) {
			WComponentPeer peer = (WComponentPeer)this.getPeer();
			long hWnd = peer.getHWnd();
			
			if (glcontext!=0) {
				WGL.wglDeleteContext(glcontext);
				User32.ReleaseDC(hWnd, hdc);
			}
			
			hdc = User32.GetDC(hWnd);
			
			PIXELFORMATDESCRIPTOR pfd = PIXELFORMATDESCRIPTOR.malloc();
			pfd.nSize((short)40);
			pfd.nVersion((short)1);
			pfd.dwFlags(0x25);
			pfd.cColorBits((byte)32);
			pfd.cDepthBits((byte)16);
			
			int pfdIndex = GDI32.ChoosePixelFormat(hdc, pfd);
			GDI32.SetPixelFormat(hdc, pfdIndex, pfd);
			
			Pointer<HDC> phdc = (Pointer<HDC>)Pointer.pointerToAddress(hdc);
			Pointer<HGLRC> phglrc = com.zekrom_64.ze.nat.win.WGL.wglCreateContext(phdc);
			glcontext = phglrc.getPeer();
			if (ctx!=0) com.zekrom_64.ze.nat.win.WGL.wglShareLists(phglrc, (Pointer<HGLRC>)Pointer.pointerToAddress(ctx));
		} else {
			if (glcontext!=0) {
				GLX.glXMakeCurrent(dpy, 0, 0);
				GLX.glXDestroyContext(dpy, glcontext);
				X11.XCloseDisplay(dpy);
			}
			
			IntBuffer att = BufferUtils.createIntBuffer(5);
			att.put(new int[] {GLX.GLX_RGBA, GLX.GLX_DEPTH_SIZE, 24, GLX.GLX_DOUBLEBUFFER, 0}).rewind();
			
			dpy = X11.XOpenDisplayl((Pointer<Byte>)Pointer.NULL);
			
			XVisualInfo vi = GLX.glXChooseVisual(dpy, 0, att);
			if (vi.address()==0) return;
			glcontext = GLX.glXCreateContext(dpy, vi, ctx, true);
			
			XComponentPeer peer = (XComponentPeer)this.getPeer();
			win = peer.getContentWindow();
		}
	}
	
	/** Destroys the OpenGL context.
	 * 
	 */
	public void deinitGL() {
		if (platform==Platform.WINDOWS) {
			WGL.wglMakeCurrent(hdc, 0);
			WGL.wglDeleteContext(glcontext);
			
			@SuppressWarnings("deprecation")
			WComponentPeer peer = (WComponentPeer)this.getPeer();

			User32.ReleaseDC(peer.getHWnd(), hdc);
			hdc = 0;
		} else {
			GLX.glXMakeCurrent(dpy, 0, 0);
			GLX.glXDestroyContext(dpy, glcontext);
			X11.XCloseDisplay(dpy);
			dpy = 0;
		}
		glcontext = 0;
	}
	
	@Override
	public void swapBuffers() {
		if (platform==Platform.WINDOWS) {
			GDI32.SwapBuffers(hdc);
		} else {
			GLX.glXSwapBuffers(dpy, win);
		}
	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public void bind() {
		if (platform==Platform.WINDOWS) {
			com.zekrom_64.ze.nat.win.WGL.wglMakeCurrent(
					(Pointer<HDC>)Pointer.pointerToAddress(hdc),
					(Pointer<HGLRC>)Pointer.pointerToAddress(glcontext)
				);
		} else {
			GLX.glXMakeCurrent(dpy, win, glcontext);
		}
	}
	
	/** Returns the platform-specific OpenGL context pointer.
	 * 
	 * @return Raw OpenGL context
	 */
	public long getContext() {
		return glcontext;
	}
	
	@Override
	protected void finalize() {
		deinitGL();
	}

	@Override
	public boolean isBound() {
		if (platform==Platform.WINDOWS) return WGL.wglGetCurrentContext() == glcontext;
		else return GLX.glXGetCurrentContext() == glcontext;
	}

}
