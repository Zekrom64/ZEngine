package com.zekrom_64.ze.libav.struct;

import org.bridj.BridJ;
import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.bridj.ann.Field;

import com.zekrom_64.ze.libav.enums.AVDiscard;
import com.zekrom_64.ze.nat.Struct;

public class AVProgram extends Struct<AVProgram> {
	static {
		BridJ.register();
	}
	@Field(0) 
	public int id() {
		return this.io.getIntField(this, 0);
	}
	@Field(0) 
	public void id(int id) {
		this.io.setIntField(this, 0, id);
		
	}
	@Field(1) 
	public int flags() {
		return this.io.getIntField(this, 1);
	}
	@Field(1) 
	public void flags(int flags) {
		this.io.setIntField(this, 1, flags);
		
	}
	/** C type : AVDiscard */
	@Field(2) 
	public IntValuedEnum<AVDiscard > discard() {
		return this.io.getEnumField(this, 2);
	}
	/** C type : AVDiscard */
	@Field(2) 
	public void discard(IntValuedEnum<AVDiscard > discard) {
		this.io.setEnumField(this, 2, discard);
		
	}
	/** C type : unsigned int* */
	@Field(3) 
	public Pointer<Integer > stream_index() {
		return this.io.getPointerField(this, 3);
	}
	/** C type : unsigned int* */
	@Field(3) 
	public void stream_index(Pointer<Integer > stream_index) {
		this.io.setPointerField(this, 3, stream_index);
		
	}
	@Field(4) 
	public int nb_stream_indexes() {
		return this.io.getIntField(this, 4);
	}
	@Field(4) 
	public void nb_stream_indexes(int nb_stream_indexes) {
		this.io.setIntField(this, 4, nb_stream_indexes);
		
	}
	/** C type : AVDictionary* */
	@Field(5) 
	public Pointer<AVDictionary > metadata() {
		return this.io.getPointerField(this, 5);
	}
	/** C type : AVDictionary* */
	@Field(5) 
	public void metadata(Pointer<AVDictionary > metadata) {
		this.io.setPointerField(this, 5, metadata);
		
	}
	@Field(6) 
	public int program_num() {
		return this.io.getIntField(this, 6);
	}
	@Field(6) 
	public void program_num(int program_num) {
		this.io.setIntField(this, 6, program_num);
		
	}
	@Field(7) 
	public int pmt_pid() {
		return this.io.getIntField(this, 7);
	}
	@Field(7) 
	public void pmt_pid(int pmt_pid) {
		this.io.setIntField(this, 7, pmt_pid);
		
	}
	@Field(8) 
	public int pcr_pid() {
		return this.io.getIntField(this, 8);
	}
	@Field(8) 
	public void pcr_pid(int pcr_pid) {
		this.io.setIntField(this, 8, pcr_pid);
		
	}
	@Field(9) 
	public long start_time() {
		return this.io.getLongField(this, 9);
	}
	@Field(9) 
	public void start_time(long start_time) {
		this.io.setLongField(this, 9, start_time);
		
	}
	@Field(10) 
	public long end_time() {
		return this.io.getLongField(this, 10);
	}
	@Field(10) 
	public void end_time(long end_time) {
		this.io.setLongField(this, 10, end_time);
		
	}
	@Field(11) 
	public long pts_wrap_reference() {
		return this.io.getLongField(this, 11);
	}
	@Field(11) 
	public void pts_wrap_reference(long pts_wrap_reference) {
		this.io.setLongField(this, 11, pts_wrap_reference);
		
	}
	@Field(12) 
	public int pts_wrap_behavior() {
		return this.io.getIntField(this, 12);
	}
	@Field(12) 
	public void pts_wrap_behavior(int pts_wrap_behavior) {
		this.io.setIntField(this, 12, pts_wrap_behavior);
		
	}
	
	public AVProgram() {}
	
	public AVProgram(Pointer<AVProgram> p) { super(p); }

}
