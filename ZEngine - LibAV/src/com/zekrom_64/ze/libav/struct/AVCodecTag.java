package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVCodecID;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVCodecTag extends Struct<AVCodecTag> {

	static {
		BridJ.register();
	}
	
	public AVCodecTag() {}
	
	public AVCodecTag(Pointer<AVCodecTag> p) { super(p); }
	
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
	public int tag() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void tag(int tag) {
		io.setIntField(this, 1, tag);
	}
	
}
