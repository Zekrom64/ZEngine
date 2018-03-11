package com.zekrom_64.ze.nat.win;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Library;

import com.zekrom_64.ze.nat.win.WinTypes.HDC;
import com.zekrom_64.ze.nat.win.WinTypes.HGLRC;

/** A class with WGL functions without relying on GLCapabilities (Because it is so useful to have wglCreateContext that needs to have an
 * OpenGL context in the current thread to be used).
 * 
 * @author Zekrom_64
 *
 */
@Library("opengl32")
public class WGL {

	static {
		BridJ.register();
	}
	
	public static native Pointer<HGLRC> wglCreateContext(Pointer<HDC> hdc);
	
	public static native void wglMakeCurrent(Pointer<HDC> hdc, Pointer<HGLRC> context);
	
	public static native boolean wglShareLists(Pointer<HGLRC> hglrc1, Pointer<HGLRC> hglrc2);
	
	public static native Pointer<HGLRC> wglGetCurrentContext();
	
}
