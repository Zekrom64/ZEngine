package com.zekrom_64.ze.nat.lin;

import org.bridj.BridJ;
import org.bridj.CLong;
import org.bridj.Pointer;
import org.bridj.ann.Library;

import com.zekrom_64.ze.nat.ZEStruct;

@Library("x11")
public class X11 {
	
	static {
		BridJ.register();
	}
	
	public class Display extends ZEStruct<Display> { }

	public static final int
		True = 1,
		False = 0,
		None = 0,
		ParentRelative = 1,
		CopyFromPreant = 0,
		PointerWindow = 0,
		InputFocus = 1,
		PointerRoot = 1,
		AnyPropertyType = 0,
		AnyKey = 0,
		AnyButton = 0,
		AllTemporary = 0,
		CurrentTime = 0,
		NoSymbol = 0;
	
	public static final int
		InputOutput = 1,
		InputOnly = 2;
	
	public static final int
		PropModeReplace = 0,
		PropModePrepend = 1,
		PropModeAppend = 2;
	
	public static native Pointer<Display> XOpenDisplay(Pointer<Byte> display_name);
	
	public static Pointer<Display> XOpenDisplay(String display_name) {
		@SuppressWarnings("unchecked")
		Pointer<Byte> dn = (Pointer<Byte>) Pointer.NULL;
		if (display_name!=null) dn = Pointer.pointerToCString(display_name);
		Pointer<Display> d = XOpenDisplay(dn);
		if (display_name!=null) dn.release();
		return d;
	}
	
	public static long XOpenDisplayl(Pointer<Byte> display_name) {
		return XOpenDisplay(display_name).getPeer();
	}
	
	public static long XOpenDisplayl(String display_name) {
		return XOpenDisplay(display_name).getPeer();
	}
	
	public static native int XCloseDisplay(Pointer<Display> display);
	
	public static int XCloseDisplay(long display) {
		@SuppressWarnings({ "deprecation", "unchecked" })
		Pointer<Display> dp = (Pointer<Display>)Pointer.pointerToAddress(display);
		return XCloseDisplay(dp);
	}
	
	public static native CLong XInternAtom(Pointer<Display> display, Pointer<Byte> atom_name, int only_if_exists);
	
	public static CLong XInternAtom(Pointer<Display> display, String atom_name, int only_if_exists) {
		Pointer<Byte> patom_name = Pointer.pointerToCString(atom_name);
		CLong ret = XInternAtom(display, patom_name, only_if_exists);
		patom_name.release();
		return ret;
	}
	
	public static native CLong XCreateWindow(
			Pointer<Display> display,
			CLong parent,
			int x, int y,
			int width, int height,
			int border_width,
			int depth,
			int _class,
			Pointer<Visual> visual,
			CLong valuemask,
			Pointer<XSetWindowAttributes> attributes
	);
	
	public static native CLong XCreateSimpleWindow(
			Pointer<Display> display,
			CLong parent,
			int x, int y,
			int width, int height,
			int border_width,
			CLong border,
			CLong background
	);
	
	public static native void XChangeProperty(
			Pointer<Display> display,
			CLong w,
			CLong property, CLong type,
			int format,
			int mode,
			Pointer<Byte> data,
			int nelements
	);
	
	public static native void XMapWindow(Pointer<Display> display, CLong w);
	
}
