package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVDictionary extends Struct<AVDictionary> {
	
	static {
		BridJ.register();
	}
	
	public AVDictionary() {}
	
	public AVDictionary(Pointer<AVDictionary> p) { super(p); }

	@Field(0)
	public int count() {
		return io.getIntField(this, 0);
	}
	
	@Field(0)
	public void count(int count) {
		io.setIntField(this, 0, count);
	}
	
	@Field(1)
	public Pointer<AVDictionaryEntry> elems() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void elems(Pointer<AVDictionaryEntry> elems) {
		io.setPointerField(this, 1, elems);
	}
	
}
