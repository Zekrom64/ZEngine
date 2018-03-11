package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVIOInterruptCB extends ZEStruct<AVIOInterruptCB> {

	static {
		BridJ.register();
	}
	
	public AVIOInterruptCB() {}
	public AVIOInterruptCB(Pointer<AVIOInterruptCB> p) { super(p); }
	
	@Field(0)
	public Pointer<?> callback() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void callback(Pointer<?> callback) {
		io.setPointerField(this, 0, callback);
	}
	
	@Field(1)
	public Pointer<?> opaque() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void opaque(Pointer<?> opaque) {
		io.setPointerField(this, 1, opaque);
	}
	
}
