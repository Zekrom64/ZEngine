package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVDeviceInfo extends Struct<AVDeviceInfo> {

	static {
		BridJ.register();
	}
	
	public AVDeviceInfo() {}
	
	public AVDeviceInfo(Pointer<AVDeviceInfo> p) { super(p); }
	
	@Field(0)
	public Pointer<Byte> device_name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void device_name(Pointer<Byte> device_name) {
		io.setPointerField(this, 0, device_name);
	}
	
	@Field(1)
	public Pointer<Byte> device_description() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void device_description(Pointer<Byte> device_description) {
		io.setPointerField(this, 1, device_description);
	}
	
}
