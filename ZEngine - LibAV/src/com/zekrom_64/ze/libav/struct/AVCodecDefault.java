package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVCodecDefault extends ZEStruct<AVCodecDefault> {

	static {
		BridJ.register();
	}
	
	public AVCodecDefault() {}
	
	public AVCodecDefault(Pointer<AVCodecDefault> p) { super(p); }
	
	@Field(0)
	public Pointer<Byte> key() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void key(Pointer<Byte> key) {
		io.setPointerField(this, 0, key);
	}
	
	@Field(1)
	public Pointer<Byte> value() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void value(Pointer<Byte> value) {
		io.setPointerField(this, 1, value);
	}
	
}
