package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;
import org.bridj.ann.Union;

import com.zekrom_64.ze.libav.enums.AVOptionType;
import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVOption extends Struct<AVOption> {

	static {
		BridJ.register();
	}
	
	public AVOption() {}
	
	public AVOption(Pointer<AVOption> p) { super(p); }
	
	@Field(0)
	public Pointer<Byte> name() {
		return io.getPointerField(this, 0);
	}
	
	@Field(0)
	public void name(Pointer<Byte> name) {
		io.setPointerField(this, 0, name);
	}
	
	@Field(1)
	public Pointer<Byte> help() {
		return io.getPointerField(this, 1);
	}
	
	@Field(1)
	public void help(Pointer<Byte> help) {
		io.setPointerField(this, 1, help);
	}
	
	@Field(2)
	public int offset() {
		return io.getIntField(this, 2);
	}
	
	@Field(2)
	public void offset(int offset) {
		io.setIntField(this, 2, offset);
	}
	
	@Field(3)
	public AVOptionType type() {
		IntValuedEnum<AVOptionType> e = io.getEnumField(this, 3);
		return (AVOptionType)e;
	}
	
	@Field(3)
	public void type(AVOptionType type) {
		io.setEnumField(this, 3, type);
	}
	
	@Union
	public class AVOption_union extends Struct<AVOption_union> {
		
		@Field(0)
		public long i64() {
			return io.getLongField(this, 0);
		}
		
		@Field(0)
		public void i64(long i64) {
			io.setLongField(this, 0, i64);
		}
		
		@Field(1)
		public double dbl() {
			return io.getDoubleField(this, 1);
		}
		
		@Field(1)
		public void dbl(double dbl) {
			io.setDoubleField(this, 1, dbl);
		}
		
		@Field(2)
		public Pointer<Byte> str() {
			return io.getPointerField(this, 2);
		}
		
		@Field(2)
		public void str(Pointer<Byte> str) {
			io.setPointerField(this, 2, str);
		}
		
		@Field(3)
		public AVRational q() {
			return io.getNativeObjectField(this, 3);
		}
		
		@Field(3)
		public void q(AVRational q) {
			io.setNativeObjectField(this, 3, q);
		}
		
	}
	
	@Field(4)
	public AVOption_union default_val() {
		return io.getNativeObjectField(this, 4);
	}
	
	@Field(4)
	public void default_val(AVOption_union default_val) {
		io.setNativeObjectField(this, 4, default_val);
	}
	
	@Field(5)
	public double min() {
		return io.getDoubleField(this, 5);
	}
	
	@Field(5)
	public void min(double min) {
		io.setDoubleField(this, 5, min);
	}
	
	@Field(6)
	public double max() {
		return io.getDoubleField(this, 6);
	}
	
	@Field(6)
	public void max(double max) {
		io.setDoubleField(this, 6, max);
	}
	
	@Field(7)
	public int flags() {
		return io.getIntField(this, 7);
	}
	
	@Field(7)
	public void flags(int flags) {
		io.setIntField(this, 7, flags);
	}
	
	@Field(8)
	public Pointer<Byte> unit() {
		return io.getPointerField(this, 8);
	}
	
	@Field(8)
	public void unit(Pointer<Byte> unit) {
		io.setPointerField(this, 8, unit);
	}
	
}
