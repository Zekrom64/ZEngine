package com.zekrom_64.ze.libav.struct.misc;

import org.bridj.Pointer;
import org.bridj.ann.Array;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

public class ScanTable extends Struct<ScanTable> {

	@Field(0)
	public Pointer<Byte> scantable() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void scantable(Pointer<Byte> scantable) {
		io.setPointerField(this, 0, scantable);
	}
	
	@Array({64})
	@Field(1)
	public Pointer<Byte> permutated() {
		return io.getPointerField(this, 1);
	}
	
	@Array({64})
	@Field(2)
	public Pointer<Byte> raster_end() {
		return io.getPointerField(this, 2);
	}
	
}
