package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.LibAVCodec;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVPicture extends Struct<AVPicture> {
	
	static {
		BridJ.register();
	}
	
	public AVPicture() {}
	
	public AVPicture(Pointer<AVPicture> p) { super(p); }

	@Array(LibAVCodec.AV_NUM_DATA_POINTERS)
	@Field(0)
	public Pointer<Pointer<Byte>> data() {
		return io.getPointerField(this, 0);
	}
	
	@Array(LibAVCodec.AV_NUM_DATA_POINTERS)
	@Field(1)
	public Pointer<Integer> linesize() {
		return io.getPointerField(this, 1);
	}
	
}
