package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.nat.Struct;

@org.bridj.ann.Struct
public class AVResampleContext extends Struct<AVResampleContext> {
	
	static {
		BridJ.register();
	}
	
	public AVResampleContext() {}
	
	public AVResampleContext(Pointer<AVResampleContext> p) { super(p); }

	@Field(0) 
	public Pointer<AVClass> av_class() {
		return this.io.getPointerField(this, 0);
	}
	
	@Field(0) 
	public void av_class(Pointer<AVClass> av_class) {
		this.io.setPointerField(this, 0, av_class);
	}
	
	@Field(1) 
	public Pointer<Short> filter_bank() {
		return this.io.getPointerField(this, 1);
	}
	
	@Field(1) 
	public void filter_bank(Pointer<Short> filter_bank) {
		this.io.setPointerField(this, 1, filter_bank);
	}
	
	@Field(2) 
	public int filter_length() {
		return this.io.getIntField(this, 2);
	}
	
	@Field(2) 
	public void filter_length(int filter_length) {
		this.io.setIntField(this, 2, filter_length);
	}
	
	@Field(3) 
	public int ideal_dst_incr() {
		return this.io.getIntField(this, 3);
	}
	
	@Field(3) 
	public void ideal_dst_incr(int ideal_dst_incr) {
		this.io.setIntField(this, 3, ideal_dst_incr);
	}
	
	@Field(4) 
	public int dst_incr() {
		return this.io.getIntField(this, 4);
	}
	
	@Field(4) 
	public void dst_incr(int dst_incr) {
		this.io.setIntField(this, 4, dst_incr);
	}
	
	@Field(5) 
	public int index() {
		return this.io.getIntField(this, 5);
	}
	
	@Field(5) 
	public void index(int index) {
		this.io.setIntField(this, 5, index);
	}
	
	@Field(6) 
	public int frac() {
		return this.io.getIntField(this, 6);
	}
	
	@Field(6) 
	public void frac(int frac) {
		this.io.setIntField(this, 6, frac);
	}
	
	@Field(7) 
	public int src_incr() {
		return this.io.getIntField(this, 7);
	}
	
	@Field(7) 
	public void src_incr(int src_incr) {
		this.io.setIntField(this, 7, src_incr);
	}
	
	@Field(8) 
	public int compensation_distance() {
		return this.io.getIntField(this, 8);
	}
	
	@Field(8) 
	public void compensation_distance(int compensation_distance) {
		this.io.setIntField(this, 8, compensation_distance);
	}
	
	@Field(9) 
	public int phase_shift() {
		return this.io.getIntField(this, 9);
	}
	
	@Field(9) 
	public void phase_shift(int phase_shift) {
		this.io.setIntField(this, 9, phase_shift);
	}
	
	@Field(10) 
	public int phase_mask() {
		return this.io.getIntField(this, 10);
	}
	
	@Field(10) 
	public void phase_mask(int phase_mask) {
		this.io.setIntField(this, 10, phase_mask);
	}
	
	@Field(11) 
	public int linear() {
		return this.io.getIntField(this, 11);
	}
	
	@Field(11)
	public void linear(int linear) {
		this.io.setIntField(this, 11, linear);
	}
	
}
