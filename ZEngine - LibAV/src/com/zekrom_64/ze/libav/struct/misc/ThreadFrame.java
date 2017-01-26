package com.zekrom_64.ze.libav.struct.misc;

import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.struct.AVBufferRef;
import com.zekrom_64.ze.libav.struct.AVCodecContext;
import com.zekrom_64.ze.libav.struct.AVFrame;
import com.zekrom_64.ze.nat.Struct;

public class ThreadFrame extends Struct<ThreadFrame> {

	@Field(0)
	public Pointer<AVFrame> f() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void f(Pointer<AVFrame> f) {
		io.setPointerField(this, 0, f);
	}
	
	@Field(1)
	public Pointer<AVCodecContext> owner() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void owner(Pointer<AVCodecContext> owner) {
		io.setPointerField(this, 1, owner);
	}
	
	@Field(2)
	public Pointer<AVBufferRef> progress() {
		return io.getPointerField(this, 2);
	}
	
	@Field(2)
	public void progress(Pointer<AVBufferRef> progress) {
		io.setPointerField(this, 2, progress);
	}
	
}
