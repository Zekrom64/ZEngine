package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVBufferRef extends ZEStruct<AVBufferRef> {

	static {
		BridJ.register();
	}
	
	public AVBufferRef() {}
	
	public AVBufferRef(Pointer<AVBufferRef> p) { super(p); }
	
	@Field(0)
	public Pointer<AVBuffer> buffer() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void buffer(Pointer<AVBuffer> buffer) {
		io.setPointerField(this, 0, buffer);
	}
	
	@Field(1)
	public Pointer<Byte> data() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void data(Pointer<Byte> data) {
		io.setPointerField(this, 1, data);
	}
	
	@Field(2)
	public int size() {
		return io.getIntField(this, 2);
	}
	
	@Field(2)
	public void size(int size) {
		io.setIntField(this, 2, size);
	}
	
}
