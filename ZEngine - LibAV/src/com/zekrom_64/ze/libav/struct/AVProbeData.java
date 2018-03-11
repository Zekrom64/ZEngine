package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

public class AVProbeData extends ZEStruct<AVProbeData> {
	static {
		BridJ.register();
	}
	/** C type : const char* */
	@Field(0) 
	public Pointer<Byte > filename() {
		return this.io.getPointerField(this, 0);
	}
	/** C type : const char* */
	@Field(0) 
	public AVProbeData filename(Pointer<Byte > filename) {
		this.io.setPointerField(this, 0, filename);
		return this;
	}
	/** C type : unsigned char* */
	@Field(1) 
	public Pointer<Byte > buf() {
		return this.io.getPointerField(this, 1);
	}
	/** C type : unsigned char* */
	@Field(1) 
	public AVProbeData buf(Pointer<Byte > buf) {
		this.io.setPointerField(this, 1, buf);
		return this;
	}
	@Field(2) 
	public int buf_size() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public AVProbeData buf_size(int buf_size) {
		this.io.setIntField(this, 2, buf_size);
		return this;
	}
	/** C type : const char* */
	@Field(3) 
	public Pointer<Byte > mime_type() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : const char* */
	@Field(3) 
	public AVProbeData mime_type(Pointer<Byte > mime_type) {
		this.io.setPointerField(this, 3, mime_type);
		return this;
	}
	public AVProbeData() {
		super();
	}
	public AVProbeData(Pointer<AVProbeData> pointer) {
		super(pointer);
	}

}
