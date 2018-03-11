package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVRational extends ZEStruct<AVRational> {

	static {
		BridJ.register();
	}
	
	public AVRational() {}
	
	public AVRational(int num, int den) {
		num(num);
		den(den);
	}
	
	public AVRational(Pointer<AVRational> p) { super(p); }
	
	@Field(0)
	public int num() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void num(int num) {
		io.setIntField(this, 0, num);
	}
	
	@Field(1)
	public int den() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void den(int den) {
		io.setIntField(this, 1, den);
	}
	
}
