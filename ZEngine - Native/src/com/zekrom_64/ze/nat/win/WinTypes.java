package com.zekrom_64.ze.nat.win;

import org.bridj.BridJ;

import com.zekrom_64.ze.nat.Struct;

public class WinTypes {

	static {
		BridJ.register();
	}
	
	public static class HANDLE<T extends HANDLE<?>> extends Struct<T> { }
	
	public static class HGDIOBJ<T extends HGDIOBJ<?>> extends Struct<T> { }
	
	
	
	public static class HDC extends HANDLE<HDC> { }
	
	public static class HGLRC extends HANDLE<HGLRC> { }
	
	public static class HICON extends HANDLE<HICON> { }
	
	public static class HWND extends HANDLE<HWND> { }
	
	
	public static class HBITMAP extends HGDIOBJ<HBITMAP> { }
}
