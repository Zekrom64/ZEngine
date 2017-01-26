package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVProfile extends Struct<AVProfile> {

	static {
		BridJ.register();
	}
	
	public AVProfile() {}
	
	public AVProfile(Pointer<AVProfile> p) { super(p); }
	
	@Field(0)
	public int profile() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void profile(int profile) {
		io.setIntField(this, 0, profile);
	}
	
	@Field(1)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void name(Pointer<Byte> name) {
		io.setPointerField(this, 1, name);
	}
	
}
