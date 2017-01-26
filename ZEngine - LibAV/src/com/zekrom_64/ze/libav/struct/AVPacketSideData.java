package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVPacketSideDataType;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVPacketSideData extends Struct<AVPacketSideData> {
	
	static {
		BridJ.register();
	}
	
	public AVPacketSideData() {}
	
	public AVPacketSideData(Pointer<AVPacketSideData> p) { super(p); }

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
	public AVPacketSideDataType type() {
		IntValuedEnum<AVPacketSideDataType> e = io.getEnumField(this, 2);
		return (AVPacketSideDataType)e;
	}
	
	@Field(2)
	public void type(AVPacketSideDataType type) {
		io.setEnumField(this, 2, type);
	}
	
}
