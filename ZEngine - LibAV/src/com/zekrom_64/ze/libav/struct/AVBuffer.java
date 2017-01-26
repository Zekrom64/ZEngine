package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.DynamicFunction;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVBuffer extends Struct<AVBuffer> {
	
	static {
		BridJ.register();
	}
	
	public AVBuffer() {}
	
	public AVBuffer(Pointer<AVBuffer> p) { super(p); }

	@Field(0)
	public Pointer<Byte> data() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void data(Pointer<Byte> data) {
		io.setPointerField(this, 0, data);
	}
	
	@Field(1)
	public int size() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void size(int size) {
		io.setIntField(this, 1, size);
	}
	
	@Field(2)
	public int refcount() {
		return io.getIntField(this, 2);
	}
	
	@Field(2)
	public void refcount(int refcount) {
		io.setIntField(this, 2, refcount);
	}
	
	@Field(3)
	public DynamicFunction<Void> free() {
		return io.getNativeObjectField(this, 3);	
	}
	
	@Field(3)
	public void free(DynamicFunction<Void> free) {
		io.setNativeObjectField(this, 3, free);
	}
	
	@Field(4)
	public Pointer<Void> opaque() {
		return io.getPointerField(this, 4);
	}
	
	@Field(4)
	public void opaque(Pointer<Void> opaque) {
		io.setPointerField(this, 4, opaque);
	}
	
	@Field(5)
	public int flags() {
		return io.getIntField(this, 5);
	}
	
	@Field(5)
	public void flags(int flags) {
		io.setIntField(this, 5, flags);
	}
	
}
