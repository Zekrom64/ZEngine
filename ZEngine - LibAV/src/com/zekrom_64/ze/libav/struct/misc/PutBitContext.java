package com.zekrom_64.ze.libav.struct.misc;

import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.ZEStruct;

public class PutBitContext extends ZEStruct<PutBitContext> {
	
	@Field(0) 
	public int bit_buf() {
		return this.io.getIntField(this, 0);
	}
	
	@Field(0) 
	public PutBitContext bit_buf(int bit_buf) {
		this.io.setIntField(this, 0, bit_buf);
		return this;
	}
	
	@Field(1) 
	public int bit_left() {
		return this.io.getIntField(this, 1);
	}
	
	@Field(1) 
	public PutBitContext bit_left(int bit_left) {
		this.io.setIntField(this, 1, bit_left);
		return this;
	}
	
	@Field(2) 
	public Pointer<Byte > buf() {
		return this.io.getPointerField(this, 2);
	}
	
	@Field(2) 
	public PutBitContext buf(Pointer<Byte > buf) {
		this.io.setPointerField(this, 2, buf);
		return this;
	}
	
	@Field(3) 
	public Pointer<Byte > buf_ptr() {
		return this.io.getPointerField(this, 3);
	}
	
	@Field(3) 
	public PutBitContext buf_ptr(Pointer<Byte > buf_ptr) {
		this.io.setPointerField(this, 3, buf_ptr);
		return this;
	}
	
	@Field(4) 
	public Pointer<Byte > buf_end() {
		return this.io.getPointerField(this, 4);
	}
	
	@Field(4) 
	public PutBitContext buf_end(Pointer<Byte > buf_end) {
		this.io.setPointerField(this, 4, buf_end);
		return this;
	}
	
	@Field(5) 
	public int size_in_bits() {
		return this.io.getIntField(this, 5);
	}
	
	@Field(5) 
	public PutBitContext size_in_bits(int size_in_bits) {
		this.io.setIntField(this, 5, size_in_bits);
		return this;
	}
	
}
