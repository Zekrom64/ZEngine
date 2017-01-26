package com.zekrom_64.ze.base.image;

public enum ZEPixelFormat {

	R8G8B8A8(4),
	A8R8G8B8(4);
	
	public final int sizeOf;
	
	private ZEPixelFormat(int sz) {
		sizeOf = sz;
	}
	
}
