package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.DynamicFunction;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVClass extends Struct<AVClass> {

	static {
		BridJ.register();
	}
	
	public AVClass() {}
	
	public AVClass(Pointer<AVClass> p) { super(p); }
	
	@Field(0)
	public Pointer<Byte> class_name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(1)
	public Pointer<DynamicFunction<Pointer<Byte>>> item_name() {
		return io.getPointerField(this, 1);
	}
	
	@Field(2)
	public Pointer<AVOption> option() {
		return io.getPointerField(this, 2);
	}
	
	@Field(3)
	public int version() {
		return io.getIntField(this, 3);
	}
	
	@Field(4)
	public int log_level_offset_offset() {
		return io.getIntField(this, 4);
	}
	
	@Field(5)
	public int parent_log_context_offset() {
		return io.getIntField(this, 5);
	}
	
	@Field(6)
	public Pointer<DynamicFunction<Pointer<Void>>> child_next() {
		return io.getPointerField(this, 6);
	}
	
	@Field(7)
	public Pointer<DynamicFunction<Pointer<AVClass>>> child_class_next() {
		return io.getPointerField(this, 7);
	}
	
}
