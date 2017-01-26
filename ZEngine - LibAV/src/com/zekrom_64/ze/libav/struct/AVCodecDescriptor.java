package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.libav.enums.AVMediaType;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVCodecDescriptor extends Struct<AVCodecDescriptor> {

	static {
		BridJ.register();
	}
	
	public AVCodecDescriptor() {}
	
	public AVCodecDescriptor(Pointer<AVCodecDescriptor> p) { super(p); }
	
	@Field(0)
	public AVCodecID id() {
		IntValuedEnum<AVCodecID> id = io.getEnumField(this, 0);
		return (AVCodecID)id;
	}
	
	@Field(0)
	public void id(AVCodecID id) {
		io.setEnumField(this, 0, id);
	}
	
	@Field(1)
	public AVMediaType type() {
		IntValuedEnum<AVMediaType> type = io.getEnumField(this, 1);
		return (AVMediaType)type;
	}
	
	@Field(1)
	public void type(AVMediaType type) {
		io.setEnumField(this, 1, type);
	}
	
	@Field(2)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 2);
	}
	
	@Field(2)
	public void name(Pointer<Byte> name) {
		io.setPointerField(this, 2, name);
	}
	
	@Field(3)
	public Pointer<Byte> long_name() {
		return io.getPointerField(this, 2);
	}
	
	@Field(3)
	public void long_name(Pointer<Byte> long_name) {
		io.setPointerField(this, 2, long_name);
	}
	
	@Field(4)
	public int props() {
		return io.getIntField(this, 4);
	}
	
	@Field(4)
	public void props(int props) {
		io.setIntField(this, 4, props);
	}
	
	@Field(5)
	public Pointer<Pointer<Byte>> mime_types() {
		return io.getPointerField(this, 5);
	}
	
	@Field(5)
	public void mime_types(Pointer<Pointer<Byte>> mime_types) {
		io.setPointerField(this, 5, mime_types);
	}
	
}
