package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVFrameSideDataType;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVFrameSideData extends Struct<AVFrameSideData> {
	
	static {
		BridJ.register();
	}
	
	public AVFrameSideData() {}
	
	public AVFrameSideData(Pointer<AVFrameSideData> p) { super(p); }
	
	@Field(0)
	public AVFrameSideDataType type() {
		IntValuedEnum<AVFrameSideDataType> e = io.getEnumField(this, 0);
		return (AVFrameSideDataType)e;
	}
	
	@Field(0)
	public void type(AVFrameSideDataType type) {
		io.setEnumField(this, 0, type);
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
	
	@Field(3)
	public Pointer<AVDictionary> metadata() {
		return io.getPointerField(this, 3);
	}
	
	@Field(3)
	public void metadata(Pointer<AVDictionary> metadata) {
		io.setPointerField(this, 3, metadata);
	}
	
	@Field(4)
	public Pointer<AVBufferRef> buf() {
		return io.getPointerField(this, 4);
	}
	
	@Field(4)
	public void buf(Pointer<AVBufferRef> buf) {
		io.setPointerField(this, 4, buf);
	}
	
}
