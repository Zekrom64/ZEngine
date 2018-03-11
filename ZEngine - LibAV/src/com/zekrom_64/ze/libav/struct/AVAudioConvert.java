package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

@org.bridj.ann.Struct
public class AVAudioConvert extends ZEStruct<AVAudioConvert> {

	static {
		BridJ.register();
	}
	
	public AVAudioConvert() { }
	
	public AVAudioConvert(Pointer<AVAudioConvert> p) { super(p); }
	
	@Field(0)
	public int in_channels() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void in_channels(int in_channels) {
		io.setIntField(this, 0, in_channels);
	}

	@Field(1)
	public int out_channels() {
		return io.getIntField(this, 0);
	}
	
	@Field(1)
	public void out_channels(int out_channels) {
		io.setIntField(this, 0, out_channels);
	}

	@Field(2)
	public int fmt_pair() {
		return io.getIntField(this, 0);
	}
	
	@Field(2)
	public void fmt_pair(int fmt_pair) {
		io.setIntField(this, 0, fmt_pair);
	}
	
}
