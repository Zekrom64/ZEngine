package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

public class AVDeviceInfoList extends ZEStruct<AVDeviceInfoList> {
	
	static {
		BridJ.register();
	}
	
	public AVDeviceInfoList() {}
	
	public AVDeviceInfoList(Pointer<AVDeviceInfoList> p) { super(p); }
	
	/** C type : AVDeviceInfo** */
	@Field(0) 
	public Pointer<Pointer<AVDeviceInfo > > devices() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : AVDeviceInfo** */
	@Field(0) 
	public AVDeviceInfoList devices(Pointer<Pointer<AVDeviceInfo > > devices) {
		this.io.setPointerField(this, 0, devices);
		return this;
	}
	@Field(1) 
	public int nb_devices() {
		return this.io.getIntField(this, 1);
	}
	@Field(1) 
	public AVDeviceInfoList nb_devices(int nb_devices) {
		this.io.setIntField(this, 1, nb_devices);
		return this;
	}
	@Field(2) 
	public int default_device() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public AVDeviceInfoList default_device(int default_device) {
		this.io.setIntField(this, 2, default_device);
		return this;
	}

}
