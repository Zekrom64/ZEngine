package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVChapter extends Struct<AVChapter> {
	
	static {
		BridJ.register();
	}

	public AVChapter() {}
	public AVChapter(Pointer<AVChapter> p) { super(p); }
	
	@Field(0)
	public int id() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void id(int id) {
		io.setIntField(this, 0, id);
	}
	
	@Field(1)
	public AVRational time_base() {
		return io.getNativeObjectField(this, 1);
	}
	
	@Field(1)
	public void time_base(AVRational time_base) {
		io.setNativeObjectField(this, 1, time_base);
	}
	
	@Field(2)
	public long start() {
		return io.getLongField(this, 2);
	}
	
	@Field(2)
	public void start(long start) {
		io.setLongField(this, 2, start);
	}
	
	@Field(3)
	public long end() {
		return io.getLongField(this, 3);
	}
	
	@Field(3)
	public void end(long end) {
		io.setLongField(this, 3, end);
	}
	
	@Field(4)
	public Pointer<AVDictionary> metadata() {
		return io.getPointerField(this, 4);
	}
	
	@Field(4)
	public void metadata(Pointer<AVDictionary> metadata) {
		io.setPointerField(this, 4, metadata);
	}
	
}
