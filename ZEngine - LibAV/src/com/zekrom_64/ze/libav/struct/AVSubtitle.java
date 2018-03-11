package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVSubtitle extends ZEStruct<AVSubtitle> {

	static {
		BridJ.register();
	}
	
	public AVSubtitle() {}
	
	public AVSubtitle(Pointer<AVSubtitle> p) { super(p); }
	
	@Field(0)
	public short format() {
		return io.getShortField(this, 0);
	}
	
	@Field(0)
	public void format(short format) {
		io.setShortField(this, 0, format);
	}
	
	@Field(1)
	public int start_display_time() {
		return io.getIntField(this, 1);
	}
	
	@Field(1)
	public void start_display_time(int start_display_time) {
		io.setIntField(this, 1, start_display_time);
	}
	
	@Field(2)
	public int end_display_time() {
		return io.getIntField(this, 2);
	}
	
	@Field(2)
	public void end_display_time(int end_display_time) {
		io.setIntField(this, 2, end_display_time);
	}
	
	@Field(3)
	public int num_rects() {
		return io.getIntField(this, 3);
	}
	
	@Field(3)
	public void num_rects(int num_rects) {
		io.setIntField(this, 3, num_rects);
	}
	
	@Field(4)
	public Pointer<Pointer<AVSubtitleRect>> rects() {
		return io.getPointerField(this, 4);
	}
	
	@Field(4)
	public void rects(Pointer<Pointer<AVSubtitleRect>> rects) {
		io.setPointerField(this, 4, rects);
	}
	
	@Field(5)
	public long pts() {
		return io.getLongField(this, 5);
	}
	
	@Field(5)
	public void pts(long pts) {
		io.setLongField(this, 5, pts);
	}
	
}
